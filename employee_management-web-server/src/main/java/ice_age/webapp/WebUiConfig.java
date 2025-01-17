package ice_age.webapp;

import static ua.com.fielden.platform.reflection.TitlesDescsGetter.getEntityTitleAndDesc;

import org.apache.commons.lang3.StringUtils;

import ice_age.Position.Position;
import ice_age.config.Modules;
import ice_age.config.personnel.PersonWebUiConfig;

import ice_age.contract.Contract;
import ice_age.webapp.config.contract.ContractWebUiConfig;
import ice_age.employee.Employee;
import ice_age.inventory.Inventory;
import ice_age.personnel.Person;
import ice_age.webapp.config.Position.PositionWebUiConfig;
import ice_age.webapp.config.inventory.InventoryWebUiConfig;
import ice_age.inventory.InventoryType;
import ice_age.personnel.Person;
import ice_age.webapp.config.employee.EmployeeWebUiConfig;
import ice_age.webapp.config.employee.EmployeeWebUiConfigComp;
import ice_age.webapp.config.inventory.InventoryTypeWebUiConfig;
import ua.com.fielden.platform.basic.config.Workflows;
import ua.com.fielden.platform.entity.AbstractEntity;
import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.utils.Pair;
import ua.com.fielden.platform.web.app.config.IWebUiBuilder;
import ua.com.fielden.platform.web.interfaces.ILayout.Device;
import ua.com.fielden.platform.web.resources.webui.AbstractWebUiConfig;
import ua.com.fielden.platform.web.resources.webui.SecurityMatrixWebUiConfig;
import ua.com.fielden.platform.web.resources.webui.UserRoleWebUiConfig;
import ua.com.fielden.platform.web.resources.webui.UserWebUiConfig;

/**
 * App-specific {@link IWebApp} implementation.
 *
 * @author Generated
 */
public class WebUiConfig extends AbstractWebUiConfig {

    public static final String WEB_TIME_WITH_MILLIS_FORMAT = "HH:mm:ss.SSS";
    public static final String WEB_TIME_FORMAT = "HH:mm";
    public static final String WEB_DATE_FORMAT_JS = "DD/MM/YYYY";
    public static final String WEB_DATE_FORMAT_JAVA = fromJsToJavaDateFormat(WEB_DATE_FORMAT_JS);

    private final String domainName;
    private final String path;
    private final int port;

    public WebUiConfig(final String domainName, final int port, final Workflows workflow, final String path) {
        super("Employee management system", workflow, new String[] { "ice_age/" });
        if (StringUtils.isEmpty(domainName) || StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException("Both the domain name and application binding path should be specified.");
        }
        this.domainName = domainName;
        this.port = port;
        this.path = path;
    }

    @Override
    public String getDomainName() {
        return domainName;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public int getPort() {
        return port;
    }

    /**
     * Configures the {@link WebUiConfig} with custom centres and masters.
     */
    @Override
    public void initConfiguration() {
        super.initConfiguration();

        final IWebUiBuilder builder = configApp();
        builder.setDateFormat(WEB_DATE_FORMAT_JS).setTimeFormat(WEB_TIME_FORMAT).setTimeWithMillisFormat(WEB_TIME_WITH_MILLIS_FORMAT)
        .setMinTabletWidth(600);

        // Users and Personnel Module
        //final PersonWebUiConfig personWebUiConfig = PersonWebUiConfig.register(injector(), builder);
        final PositionWebUiConfig positionWebUiConfig = PositionWebUiConfig.register(injector(), builder);
        final ContractWebUiConfig contractWebUiConfig = ContractWebUiConfig.register(injector(), builder);
        //final PersonWebUiConfig personWebUiConfig = PersonWebUiConfig.register(injector(), builder);
        final EmployeeWebUiConfigComp employeeWebUiConfigComp = EmployeeWebUiConfigComp.register(injector(), builder);
        final InventoryWebUiConfig inventoryWebUiConfig = InventoryWebUiConfig.register(injector(), builder);
        final InventoryTypeWebUiConfig inventoryTypeWebUiConfig = InventoryTypeWebUiConfig.register(injector(), builder);
        final UserWebUiConfig userWebUiConfig = UserWebUiConfig.register(injector(), builder);
        final UserRoleWebUiConfig userRoleWebUiConfig = UserRoleWebUiConfig.register(injector(), builder);
        final SecurityMatrixWebUiConfig securityConfig = SecurityMatrixWebUiConfig.register(injector(), configApp());

        // Add user-rated masters and centres to the configuration
        configApp()
        .addMaster(userWebUiConfig.master)
        .addMaster(userWebUiConfig.rolesUpdater)
        .addMaster(userRoleWebUiConfig.master)
        .addMaster(userRoleWebUiConfig.tokensUpdater)
        .addCentre(userWebUiConfig.centre)
        .addCentre(userRoleWebUiConfig.centre);

        // Configure application menu
        configDesktopMainMenu()
        .addModule(Modules.USERS_AND_PERSONNEL.title)
            .description(Modules.USERS_AND_PERSONNEL.desc)
            .icon(Modules.USERS_AND_PERSONNEL.icon)
            .detailIcon(Modules.USERS_AND_PERSONNEL.icon)
            .bgColor(Modules.USERS_AND_PERSONNEL.bgColour)
            .captionBgColor(Modules.USERS_AND_PERSONNEL.captionBgColour)
            .menu()
                .addMenuItem(mkMenuItemTitle(Employee.class)).description(mkMenuItemDesc(Employee.class)).centre(employeeWebUiConfigComp.centre).done()
                .addMenuItem(mkMenuItemTitle(Inventory.class)).description(mkMenuItemDesc(Inventory.class)).centre(inventoryWebUiConfig.centre).done()
                .addMenuItem(mkMenuItemTitle(Contract.class)).description(mkMenuItemDesc(Contract.class)).centre(contractWebUiConfig.centre).done()
                //.addMenuItem(mkMenuItemTitle(Person.class)).description(mkMenuItemDesc(Person.class)).centre(personWebUiConfig.centre).done()
                .addMenuItem(mkMenuItemTitle(Position.class)).description(mkMenuItemDesc(Position.class)).centre(positionWebUiConfig.centre).done()
                .addMenuItem(mkMenuItemTitle(InventoryType.class)).description(mkMenuItemDesc(InventoryType.class)).centre(inventoryTypeWebUiConfig.centre).done()
                .addMenuItem("System Users").description("Functionality for managing system users, authorisation, etc.")
                    .addMenuItem("Users").description("User centre").centre(userWebUiConfig.centre).done()
                    .addMenuItem("User Roles").description("User roles centre").centre(userRoleWebUiConfig.centre).done()
                    .addMenuItem("Security Matrix").description("Security Matrix is used to manage application authorisations for User Roles.").master(securityConfig.master).done()
                .done()
            .done().done()
        .setLayoutFor(Device.DESKTOP, null, "[[[]]]")
        .setLayoutFor(Device.TABLET, null, "[[[]]]")
        .setLayoutFor(Device.MOBILE, null, "[[[]]]")
        .minCellWidth(100).minCellHeight(148).done();
    }

    private static String fromJsToJavaDateFormat(final String dateFormatJs) {
        return dateFormatJs.replace("DD", "dd").replace("YYYY", "yyyy"); // UPPERCASE "Y" is "week year" in Java, therefore we prefer lowercase "y"
    }

    public static String mkMenuItemTitle(final Class<? extends AbstractEntity<?>> entityType) {
        return getEntityTitleAndDesc(entityType).getKey();
    }

    public static final String CENTRE_SUFFIX = " Centre";
    public static String mkMenuItemDesc(final Class<? extends AbstractEntity<?>> entityType) {
        final Pair<String, String> titleDesc = TitlesDescsGetter.getEntityTitleAndDesc(entityType);
        // Some @EntityTitle desc are not specified, while the others are worded as whole sentence ending with "." - use value in both cases
        return titleDesc.getValue().isEmpty() || titleDesc.getValue().endsWith(".") ? titleDesc.getKey() + CENTRE_SUFFIX : titleDesc.getValue() + CENTRE_SUFFIX;
    }

}
