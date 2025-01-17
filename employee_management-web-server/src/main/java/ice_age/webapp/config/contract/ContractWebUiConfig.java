package ice_age.webapp.config.contract;

import static ice_age.common.StandardScrollingConfigs.standardStandaloneScrollingConfig;
import static java.lang.String.format;
import static ua.com.fielden.platform.web.PrefDim.mkDim;

import java.util.Optional;

import com.google.inject.Injector;

import ice_age.common.LayoutComposer;
import ice_age.common.StandardActions;
import ice_age.contract.Contract;
import ice_age.employee.Employee;
import ice_age.main.menu.contract.MiContract;
import metamodels.MetaModels;
import ua.com.fielden.platform.web.PrefDim.Unit;
import ua.com.fielden.platform.web.action.CentreConfigurationWebUiConfig.CentreConfigActions;
import ua.com.fielden.platform.web.app.config.IWebUiBuilder;
import ua.com.fielden.platform.web.centre.EntityCentre;
import ua.com.fielden.platform.web.centre.api.EntityCentreConfig;
import ua.com.fielden.platform.web.centre.api.actions.EntityActionConfig;
import ua.com.fielden.platform.web.centre.api.impl.EntityCentreBuilder;
import ua.com.fielden.platform.web.interfaces.ILayout.Device;
import ua.com.fielden.platform.web.view.master.EntityMaster;
import ua.com.fielden.platform.web.view.master.api.IMaster;
import ua.com.fielden.platform.web.view.master.api.actions.MasterActions;
import ua.com.fielden.platform.web.view.master.api.impl.SimpleMasterBuilder;

/**
 * {@link Contract} Web UI configuration.
 *
 * @author Developers
 *
 */
public class ContractWebUiConfig {

    public final EntityCentre<Contract> centre;
    public final EntityMaster<Contract> master;

    public static ContractWebUiConfig register(final Injector injector, final IWebUiBuilder builder) {
        return new ContractWebUiConfig(injector, builder);
    }

    private ContractWebUiConfig(final Injector injector, final IWebUiBuilder builder) {
        centre = createCentre(injector, builder);
        builder.register(centre);
        master = createMaster(injector);
        builder.register(master);
    }

    /**
     * Creates entity centre for {@link Contract}.
     *
     * @param injector
     * @return created entity centre
     */
    private EntityCentre<Contract> createCentre(final Injector injector, final IWebUiBuilder builder) {
        final String layout = LayoutComposer.mkGridForCentre(2, 1);

        final EntityActionConfig standardNewAction = StandardActions.NEW_ACTION.mkAction(Contract.class);
        final EntityActionConfig standardDeleteAction = StandardActions.DELETE_ACTION.mkAction(Contract.class);
        final EntityActionConfig standardExportAction = StandardActions.EXPORT_ACTION.mkAction(Contract.class);
        final EntityActionConfig standardEditAction = StandardActions.EDIT_ACTION.mkAction(Contract.class);
        final EntityActionConfig standardSortAction = CentreConfigActions.CUSTOMISE_COLUMNS_ACTION.mkAction();
        builder.registerOpenMasterAction(Contract.class, standardEditAction);

        final EntityCentreConfig<Contract> ecc = EntityCentreBuilder.centreFor(Contract.class)
                //.runAutomatically()
                .addFrontAction(standardNewAction)
                .addTopAction(standardNewAction).also()
                .addTopAction(standardDeleteAction).also()
                .addTopAction(standardSortAction).also()
                .addTopAction(standardExportAction)
                .addCrit(MetaModels.Contract_.startdate()).asRange().date().also()
                .addCrit(MetaModels.Contract_.money()).asRange().decimal().also()
                .addCrit(MetaModels.Contract_.employee()).asMulti().autocompleter(Employee.class)
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .withScrollingConfig(standardStandaloneScrollingConfig(0))
                .addProp(MetaModels.Contract_).order(1).asc().minWidth(100)
                    .withSummary("total_count_", "COUNT(SELF)", format("Count:The total number of matching %ss.", Contract.ENTITY_TITLE))
                    .withAction(standardEditAction).also()
                    .addProp(MetaModels.Contract_.employee()).minWidth(100).also()
                    .addProp(MetaModels.Contract_.money()).minWidth(100).also()
                .addProp(MetaModels.Contract_.startdate()).minWidth(100).also()
                .addProp(MetaModels.Contract_.enddate()).minWidth(100)
                //.addProp("prop").minWidth(100).withActionSupplier(builder.getOpenMasterAction(Entity.class)).also()
                .addPrimaryAction(standardEditAction)
                .build();

        return new EntityCentre<>(MiContract.class, ecc, injector);
    }

    /**
     * Creates entity master for {@link Contract}.
     *
     * @param injector
     * @return created entity master
     */
    private EntityMaster<Contract> createMaster(final Injector injector) {
        final String layout = LayoutComposer.mkVarGridForMasterFitWidth(2, 2, 1, 1);

        final IMaster<Contract> masterConfig = new SimpleMasterBuilder<Contract>().forEntity(Contract.class)
                .addProp(MetaModels.Contract_.contractid()).asMultilineText().also()
                .addProp(MetaModels.Contract_.employee()).asAutocompleter().also()
                .addProp(MetaModels.Contract_.startdate()).asDatePicker().also()
                .addProp(MetaModels.Contract_.enddate()).asDatePicker().also()
                .addProp(MetaModels.Contract_.money()).asMoney().also()
                .addProp(MetaModels.Contract_.desc()).asMultilineText().also()
                .addAction(MasterActions.REFRESH).shortDesc("Cancel").longDesc("Cancel action")
                .addAction(MasterActions.SAVE)
                .setActionBarLayoutFor(Device.DESKTOP, Optional.empty(), LayoutComposer.mkActionLayoutForMaster())
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .withDimensions(mkDim(LayoutComposer.SIMPLE_ONE_COLUMN_MASTER_DIM_WIDTH, 480, Unit.PX))
                .done();

        return new EntityMaster<>(Contract.class, masterConfig, injector);
    }
}