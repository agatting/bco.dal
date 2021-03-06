#!/usr/bin/env python2

import logging
import rsb
import rst
import rstsandbox
import rstexperimental
from rst.domotic.unit.dal.PowerConsumptionSensorData_pb2 import PowerConsumptionSensorData
from rst.vision.HSBColor_pb2 import HSBColor
from rst.domotic.state.PowerState_pb2 import PowerState
from rsb.converter import ProtocolBufferConverter, registerGlobalConverter
import time
import traceback
import subprocess as sp
import os


class PowerConsumptionColorFeedback(object):
    def __init__(self):
        """
        plug is the power plug code (e.g. "A3C3")
        threshold is the consumer power consumption threshold in Watts
        minimumDuration is the minimum duration in seconds the consumption has to be upheld to trigger the alarm
        """
        logging.basicConfig()
        registerGlobalConverter(ProtocolBufferConverter(messageClass=PowerConsumptionSensorData))
        registerGlobalConverter(ProtocolBufferConverter(messageClass=HSBColor))
        registerGlobalConverter(ProtocolBufferConverter(messageClass=PowerState))
        rsb.__defaultParticipantConfig = rsb.ParticipantConfig.fromDefaultSources()

        self.hue1 = 240
        self.hue2 = 0

        self.location_id = "f0a71f71-1463-41e3-9c9a-25a02a536001"
        self.location_scope = "/home/kitchen/status"
        self.light_id = "8d310f30-d60a-4627-8884-373c5e2dcbdd"
        self.light_scope = "/home/kitchen/colorablelight/ceilinglamp_1/ctrl"
        self.power_threshold = 1000

    def run(self):
        def power_update(event):
            print("Received event: %s" % event)
            try:
                consumption = event.getData().power_consumption_state.consumption
                print 'new consumption is', consumption, 'W (at time', str(time.time()) + ')'
                if consumption > self.power_threshold:
                    self.update_light_color(consumption)

            except Exception as e:
                print 'received illegal event (' + str(e) + ')'
                traceback.print_exc()

        # with rsb.createListener("/home/control/powerconsumptionsensor/") as listener:
        # print("consumptionscope:", self.consumptionscope, "; plug:", self.plug)
        # print("combined string:", self.consumptionscope % self.plug)
        with rsb.createListener(self.location_scope) as listener:
            listener.addHandler(power_update)
            while True:
                time.sleep(1)

    def update_light_color(self, current_consumption=0):
        with rsb.createRemoteServer(self.light_scope) as server:
            print 'update light color related to power consumption.'

            # compute color value
            consumption_color = HSBColor()
            consumption_color.saturation = 100
            consumption_color.brightness = 100

            lowerhue, higherhue = sorted([self.hue1, self.hue2])
            doeswrap = (lowerhue - higherhue + 360) < (higherhue - lowerhue)

            if doeswrap:
                lowerhue, higherhue = (higherhue, lowerhue + 360)

            consumption_color.hue = self._linmap(current_consumption, [0, 1000], [lowerhue, higherhue], crop=True) % 360

            print 'computed hue related to power consumption:', consumption_color.hue
            print 'setting light color'
            server.setColor.async(consumption_color)

    def _linmap(self, inputvalue, inputrange, outputrange, crop=False):
        """
        Map inputvalue from the inputrange to the outputrange. Convert type (usually int or float) and round if appropriate.
        :param inputvalue: A value within inputrange.
        :param inputrange: A 2-tuple that specifies the input range.
        :param outputrange: A 2-tuple that specifies the output range.
        :param crop: If the output value falls outside the outputrange, crop to legal value.
        :return: The output value within outputrange and of the same type as the outputrange members.
        """
        outputmin, outputmax = outputrange
        inputmin, inputmax = inputrange
        scale = float(outputmax - outputmin) / (inputmax - inputmin)
        ret = (inputvalue - inputmin) * scale + outputmin
        if crop:
            if outputmin > outputmax:
                outputmin, outputmax = (outputmax, outputmin)
            ret = max(ret, outputmin)
            ret = min(ret, outputmax)
        # if the whole outputrange has a certain type, convert the output to that type
        if type(outputmin) == type(outputmax):
            if type(outputmin) == int:  # in case of int, round before casting
                return int(round(ret))
            else:  # otherwise just cast ... should be float but you never know
                return type(outputmin)(ret)
        else:
            return ret


if __name__ == '__main__':
    watcher = PowerConsumptionColorFeedback()
    watcher.run()
