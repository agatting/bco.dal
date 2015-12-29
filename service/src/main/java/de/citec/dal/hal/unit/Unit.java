/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.citec.dal.hal.unit;

import de.citec.dal.data.Location;
import de.citec.dal.hal.service.MultiService;
import org.dc.jul.iface.Identifiable;
import org.dc.jul.extension.rsb.scope.ScopeProvider;
import org.dc.jul.iface.provider.LabelProvider;
import rst.homeautomation.unit.UnitConfigType;
import rst.homeautomation.unit.UnitTemplateType.UnitTemplate.UnitType;

/**
 *
 * @author mpohling
 */
public interface Unit extends MultiService, LabelProvider, ScopeProvider, Identifiable<String> {

    public UnitType getType();

    public Location getLocation();
    
    public UnitConfigType.UnitConfig getUnitConfig();

}
