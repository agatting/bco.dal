/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.dal.bindings.openhab.util.configgen;

import de.citec.jul.extension.rst.processing.MetaConfigVariableProvider;
import static de.citec.dal.bindings.openhab.util.configgen.OpenHABItemConfigGenerator.TAB_SIZE;
import de.citec.jul.exception.CouldNotPerformException;
import de.citec.jul.exception.printer.ExceptionPrinter;
import de.citec.jul.exception.InstantiationException;
import de.citec.jul.exception.NotAvailableException;
import de.citec.jul.exception.printer.LogLevel;
import de.citec.jul.extension.protobuf.ProtobufVariableProvider;
import de.citec.jul.extension.rst.processing.MetaConfigPool;
import de.citec.jul.processing.StringProcessor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.LoggerFactory;
import rst.homeautomation.device.DeviceClassType.DeviceClass;
import rst.homeautomation.device.DeviceConfigType.DeviceConfig;
import rst.homeautomation.service.ServiceConfigType.ServiceConfig;
import rst.homeautomation.service.ServiceTemplateType.ServiceTemplate;
import rst.homeautomation.service.ServiceTemplateType.ServiceTemplate.ServiceType;
import static rst.homeautomation.service.ServiceTemplateType.ServiceTemplate.ServiceType.BATTERY_PROVIDER;
import static rst.homeautomation.service.ServiceTemplateType.ServiceTemplate.ServiceType.BRIGHTNESS_PROVIDER;
import static rst.homeautomation.service.ServiceTemplateType.ServiceTemplate.ServiceType.BRIGHTNESS_SERVICE;
import static rst.homeautomation.service.ServiceTemplateType.ServiceTemplate.ServiceType.BUTTON_PROVIDER;
import static rst.homeautomation.service.ServiceTemplateType.ServiceTemplate.ServiceType.COLOR_SERVICE;
import static rst.homeautomation.service.ServiceTemplateType.ServiceTemplate.ServiceType.DIM_PROVIDER;
import static rst.homeautomation.service.ServiceTemplateType.ServiceTemplate.ServiceType.DIM_SERVICE;
import static rst.homeautomation.service.ServiceTemplateType.ServiceTemplate.ServiceType.HANDLE_PROVIDER;
import static rst.homeautomation.service.ServiceTemplateType.ServiceTemplate.ServiceType.MOTION_PROVIDER;
import static rst.homeautomation.service.ServiceTemplateType.ServiceTemplate.ServiceType.OPENING_RATIO_PROVIDER;
import static rst.homeautomation.service.ServiceTemplateType.ServiceTemplate.ServiceType.POWER_CONSUMPTION_PROVIDER;
import static rst.homeautomation.service.ServiceTemplateType.ServiceTemplate.ServiceType.POWER_PROVIDER;
import static rst.homeautomation.service.ServiceTemplateType.ServiceTemplate.ServiceType.POWER_SERVICE;
import static rst.homeautomation.service.ServiceTemplateType.ServiceTemplate.ServiceType.REED_SWITCH_PROVIDER;
import static rst.homeautomation.service.ServiceTemplateType.ServiceTemplate.ServiceType.SHUTTER_PROVIDER;
import static rst.homeautomation.service.ServiceTemplateType.ServiceTemplate.ServiceType.SHUTTER_SERVICE;
import static rst.homeautomation.service.ServiceTemplateType.ServiceTemplate.ServiceType.TAMPER_PROVIDER;
import static rst.homeautomation.service.ServiceTemplateType.ServiceTemplate.ServiceType.TEMPERATURE_PROVIDER;
import rst.homeautomation.unit.UnitConfigType.UnitConfig;
import rst.homeautomation.unit.UnitTemplateConfigType.UnitTemplateConfig;
import rst.homeautomation.unit.UnitTemplateType.UnitTemplate.UnitType;

/**
 *
 * @author mpohling
 */
public class ItemEntry {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ItemEntry.class);

    public static final String SERVICE_TEMPLATE_BINDING_TYPE = "OPENHAB_BINDING_TYPE";
    public static final String SERVICE_TEMPLATE_BINDING_ICON = "OPENHAB_BINDING_ICON";
    public static final String SERVICE_TEMPLATE_BINDING_COMMAND = "OPENHAB_BINDING_COMMAND";
    public static final String SERVICE_TEMPLATE_BINDING_CONFIG = "OPENHAB_BINDING_CONFIG";
    public static final String SERVICE_TEMPLATE_BINDING_LABEL_DESCRIPTOR = "OPENHAB_SERVICE_LABEL_DESCRIPTOR";
    public static final String OPENHAB_BINDING_ITEM_ID = "OPENHAB_BINDING_ITEM_ID";

    private String commandType;
    private final String itemId;
    private String label;
    private String icon;
    private final List<String> groups;
    private String itemHardwareConfig;
    private final MetaConfigPool configPool;

    private static int maxCommandTypeSize = 0;
    private static int maxItemIdSize = 0;
    private static int maxLabelSize = 0;
    private static int maxIconSize = 0;
    private static int maxGroupSize = 0;
    private static int maxBindingConfigSize = 0;

    public ItemEntry(final DeviceClass deviceClass, final DeviceConfig deviceConfig, final UnitConfig unitConfig, final ServiceConfig serviceConfig) throws InstantiationException {
        try {
            this.groups = new ArrayList<>();

            configPool = new MetaConfigPool();
            configPool.register(new MetaConfigVariableProvider("BindingServiceConfig", serviceConfig.getBindingServiceConfig().getMetaConfig()));
            configPool.register(new MetaConfigVariableProvider("ServiceMetaConfig", serviceConfig.getMetaConfig()));
            configPool.register(new MetaConfigVariableProvider("UnitMetaConfig", unitConfig.getMetaConfig()));
            configPool.register(new MetaConfigVariableProvider("DeviceMetaConfig", deviceConfig.getMetaConfig()));
            configPool.register(new MetaConfigVariableProvider("DeviceBindingConfig", deviceClass.getBindingConfig().getMetaConfig()));
            configPool.register(new MetaConfigVariableProvider("DeviceClassMetaConfig", deviceClass.getMetaConfig()));
            configPool.register(new ProtobufVariableProvider(deviceConfig));
            configPool.register(new ProtobufVariableProvider(unitConfig));
            configPool.register(new ProtobufVariableProvider(serviceConfig));

            if (unitConfig.getLabel().endsWith("Lichtschalter Flur")) {
                System.out.println("..");
            }

            try {
                configPool.register(new MetaConfigVariableProvider("ServiceTemplateMetaConfig", lookupServiceTemplate(deviceClass, unitConfig, serviceConfig).getMetaConfig()));
            } catch (NotAvailableException ex) {
                ExceptionPrinter.printHistory(new CouldNotPerformException("Could not load service template meta config for Service[" + serviceConfig.getType().name() + "] of Unit[" + unitConfig.getId() + "]", ex), logger, LogLevel.ERROR);
            }

            try {
                this.itemId = configPool.getValue(OPENHAB_BINDING_ITEM_ID);
            } catch (NotAvailableException ex) {
                throw new NotAvailableException("itemId", ex);
            }

            try {
                this.label = configPool.getValue(SERVICE_TEMPLATE_BINDING_LABEL_DESCRIPTOR);
            } catch (NotAvailableException ex) {
                this.label = unitConfig.getLabel();
            }

            try {
                this.commandType = configPool.getValue(SERVICE_TEMPLATE_BINDING_COMMAND);
            } catch (NotAvailableException ex) {
                this.commandType = getDefaultCommand(serviceConfig.getType());
            }

            try {
                this.icon = configPool.getValue(SERVICE_TEMPLATE_BINDING_ICON);
            } catch (NotAvailableException ex) {
                this.icon = "";
            }

            // TODO: maybe think of another strategy to name groups
            // Dimmer and Rollershutter are key words in the openhab config and therefor cannot be used in groups
            String templateName = StringProcessor.transformUpperCaseToCamelCase(unitConfig.getType().name());
            if (!(templateName.equals("Dimmer") || templateName.equals("Rollershutter"))) {
                this.groups.add(StringProcessor.transformUpperCaseToCamelCase(unitConfig.getType().name()));
            }
            this.groups.add(StringProcessor.transformUpperCaseToCamelCase(serviceConfig.getType().name()));
            this.groups.add(unitConfig.getPlacementConfig().getLocationId());

            try {
                itemHardwareConfig = generateItemHardwareConfig(deviceConfig, unitConfig, serviceConfig);
            } catch (CouldNotPerformException ex) {
                throw new NotAvailableException("itemHardwareConfig", ex);
            }

            this.calculateGaps();
        } catch (Exception ex) {
            throw new de.citec.jul.exception.InstantiationException(this, ex);
        }
    }

    private String generateItemHardwareConfig(final DeviceConfig deviceConfig, final UnitConfig unitConfig, final ServiceConfig serviceConfig) throws CouldNotPerformException {
        try {
            String config = "";

            config += configPool.getValue(SERVICE_TEMPLATE_BINDING_TYPE);
            config += "=\"";
            config += configPool.getValue(SERVICE_TEMPLATE_BINDING_CONFIG);
            config += "\"";
            return config;
        } catch (Exception ex) {
            throw new CouldNotPerformException("Could not generate item hardware config of Unit[" + unitConfig.getId() + "] !", ex);
        }
    }

    /**
     * Lookups the service template of the given ServiceType out of the unit template.
     *
     * @param unitTemplate to lookup the service template.
     * @param serviceType the service type to resolve the template.
     * @return the related service template for the given service type.
     * @throws NotAvailableException
     */
    private ServiceTemplate lookupServiceTemplate(final DeviceClass deviceClass, final UnitConfig unitConfig, final ServiceConfig serviceConfig) throws NotAvailableException {
        List<UnitTemplateConfig> unitTemplateConfigList = deviceClass.getUnitTemplateConfigList();
        for (UnitTemplateConfig unitTemplateConfig : unitTemplateConfigList) {
            if (unitTemplateConfig.getId().equals(unitConfig.getUnitTemplateConfigId())) {
                List<ServiceTemplate> serviceTemplateList = unitTemplateConfig.getServiceTemplateList();
                for (ServiceTemplate serviceTemplate : serviceTemplateList) {
                    if (serviceTemplate.getServiceType().equals(serviceConfig.getType())) {
                        return serviceTemplate;
                    }
                }
            }
        }
        throw new NotAvailableException("service template for ServiceType[" + serviceConfig.getType().name() + "]");
    }

    private UnitTemplateConfig lookupUnitTemplateConfig(final DeviceClass deviceClass, final UnitConfig unitConfig) throws NotAvailableException {
        return lookupUnitTemplateConfig(deviceClass, unitConfig.getType());
    }

    private UnitTemplateConfig lookupUnitTemplateConfig(final DeviceClass deviceClass, final UnitType unitType) throws NotAvailableException {

        for (UnitTemplateConfig template : deviceClass.getUnitTemplateConfigList()) {
            if (template.getType() == unitType) {
                return template;
            }
        }
        throw new NotAvailableException("unit template config for UnitType[" + unitType.name() + "]");
    }

    private void calculateGaps() {
        maxCommandTypeSize = Math.max(maxCommandTypeSize, getCommandTypeStringRep().length());
        maxItemIdSize = Math.max(maxItemIdSize, getItemIdStringRep().length());
        maxLabelSize = Math.max(maxLabelSize, getLabelStringRep().length());
        maxIconSize = Math.max(maxIconSize, getIconStringRep().length());
        maxGroupSize = Math.max(maxGroupSize, getGroupsStringRep().length());
        maxBindingConfigSize = Math.max(maxBindingConfigSize, getBindingConfigStringRep().length());
    }

    public static void reset() {
        maxCommandTypeSize = 0;
        maxItemIdSize = 0;
        maxLabelSize = 0;
        maxIconSize = 0;
        maxGroupSize = 0;
        maxBindingConfigSize = 0;
    }

    public String getCommandType() {
        return commandType;
    }

    public String getItemId() {
        return itemId;
    }

    public String getLabel() {
        return label;
    }

    public String getIcon() {
        return icon;
    }

    public List<String> getGroups() {
        return Collections.unmodifiableList(groups);
    }

    public String getItemHardwareConfig() {
        return itemHardwareConfig;
    }

    public String getCommandTypeStringRep() {
        return commandType;
    }

    public String getItemIdStringRep() {
        return itemId;
    }

    public String getLabelStringRep() {
        if (label.isEmpty()) {
            return "";
        }
        return "\"" + label + "\"";
    }

    public String getIconStringRep() {
        if (icon.isEmpty()) {
            return "";
        }
        return "<" + icon + ">";
    }

    public String getGroupsStringRep() {
        if (groups.isEmpty()) {
            return "";
        }
        String stringRep = "(";
        boolean firstIteration = true;
        for (String group : groups) {
            if (!firstIteration) {
                stringRep += ",";
            } else {
                firstIteration = false;
            }
            stringRep += group;
        }
        stringRep += ")";
        return stringRep;
    }

    public String getBindingConfigStringRep() {
        return "{ " + itemHardwareConfig + " }";
    }

    public String buildStringRep() {

        String stringRep = "";

        // command type
        stringRep += StringProcessor.fillWithSpaces(getCommandTypeStringRep(), maxCommandTypeSize + TAB_SIZE);

        // unit id
        stringRep += StringProcessor.fillWithSpaces(getItemIdStringRep(), maxItemIdSize + TAB_SIZE);

        // label
        stringRep += StringProcessor.fillWithSpaces(getLabelStringRep(), maxLabelSize + TAB_SIZE);

        // icon
        stringRep += StringProcessor.fillWithSpaces(getIconStringRep(), maxIconSize + TAB_SIZE);

        // groups
        stringRep += StringProcessor.fillWithSpaces(getGroupsStringRep(), maxGroupSize + TAB_SIZE);

        // binding config
        stringRep += StringProcessor.fillWithSpaces(getBindingConfigStringRep(), maxBindingConfigSize + TAB_SIZE);

        return stringRep;
    }

    private String getDefaultCommand(ServiceType type) {
        switch (type) {
        case COLOR_SERVICE:
            return "Color";
        case OPENING_RATIO_PROVIDER:
        case POWER_CONSUMPTION_PROVIDER:
        case TEMPERATURE_PROVIDER:
        case MOTION_PROVIDER:
        case TAMPER_PROVIDER:
        case BRIGHTNESS_PROVIDER:
        case BATTERY_PROVIDER:
        case SMOKE_ALARM_STATE_PROVIDER:
        case SMOKE_STATE_PROVIDER:
        case TEMPERATURE_ALARM_STATE_PROVIDER:
        case TARGET_TEMPERATURE_PROVIDER:
        case TARGET_TEMPERATURE_SERVICE:
            return "Number";
        case SHUTTER_PROVIDER:
        case SHUTTER_SERVICE:
            return "Rollershutter";
        case POWER_SERVICE:
        case POWER_PROVIDER:
        case BUTTON_PROVIDER:
            return "Switch";
        case BRIGHTNESS_SERVICE:
        case DIM_PROVIDER:
        case DIM_SERVICE:
            return "Dimmer";
        case REED_SWITCH_PROVIDER:
            return "Contact";
        case HANDLE_PROVIDER:
            return "String";
        default:
            logger.warn("Unkown Service Type: " + type);
            return "";
        }
    }
}
