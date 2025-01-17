package ice_age.config;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ice_age.personnel.Person;
import ua.com.fielden.platform.basic.config.IApplicationDomainProvider;
import ua.com.fielden.platform.domain.PlatformDomainTypes;
import ua.com.fielden.platform.entity.AbstractEntity;
import ice_age.Position.Position;
import ice_age.contract.Contract;
import ice_age.inventory.Inventory;
import ice_age.inventory.InventoryType;
import ice_age.employee.Employee;
import ice_age.employee.ui_actions.OpenEmployeeMasterAction;
import ice_age.employee.master.menu.actions.EmployeeMaster_OpenMain_MenuItem;
import ice_age.employee.master.menu.actions.EmployeeMaster_OpenInventory_MenuItem;
import ice_age.employee.master.menu.actions.EmployeeMaster_OpenContract_MenuItem;

/**
 * A class to register domain entities.
 *
 * @author Generated
 */
public class ApplicationDomain implements IApplicationDomainProvider {

    private static final Set<Class<? extends AbstractEntity<?>>> entityTypes = new LinkedHashSet<>();
    private static final Set<Class<? extends AbstractEntity<?>>> domainTypes = new LinkedHashSet<>();

    static {
        entityTypes.addAll(PlatformDomainTypes.types);
        add(Person.class);
        add(Position.class);
        add(Contract.class);
        add(Inventory.class);
        add(InventoryType.class);
        add(Employee.class);
        add(OpenEmployeeMasterAction.class);
        add(EmployeeMaster_OpenMain_MenuItem.class);
        add(EmployeeMaster_OpenInventory_MenuItem.class);
        add(EmployeeMaster_OpenContract_MenuItem.class);
    }

    private static void add(final Class<? extends AbstractEntity<?>> domainType) {
        entityTypes.add(domainType);
        domainTypes.add(domainType);
    }

    @Override
    public List<Class<? extends AbstractEntity<?>>> entityTypes() {
        return Collections.unmodifiableList(entityTypes.stream().collect(Collectors.toList()));
    }

    public List<Class<? extends AbstractEntity<?>>> domainTypes() {
        return Collections.unmodifiableList(domainTypes.stream().collect(Collectors.toList()));
    }

}
