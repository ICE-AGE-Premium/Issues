package ice_age.webapp;

import java.util.Properties;

import org.restlet.Component;

import ice_age.config.ApplicationDomain;
import ice_age.dbsetup.HibernateSetup;
import ice_age.filter.NoDataFilter;
import ice_age.ioc.WebApplicationServerModule;
import ice_age.serialisation.SerialisationClassProvider;

import ua.com.fielden.platform.ioc.ApplicationInjectorFactory;
import ua.com.fielden.platform.ioc.NewUserEmailNotifierBindingModule;
import ua.com.fielden.platform.web.app.IWebUiConfig;
import ua.com.fielden.platform.web.factories.webui.LoginCompleteResetResourceFactory;
import ua.com.fielden.platform.web.factories.webui.LoginInitiateResetResourceFactory;
import ua.com.fielden.platform.web.factories.webui.LoginResourceFactory;
import ua.com.fielden.platform.web.factories.webui.LogoutResourceFactory;
import ua.com.fielden.platform.web.resources.RestServerUtil;
import ua.com.fielden.platform.web.resources.webui.LoginCompleteResetResource;
import ua.com.fielden.platform.web.resources.webui.LoginInitiateResetResource;
import ua.com.fielden.platform.web.resources.webui.LoginResource;
import ua.com.fielden.platform.web.resources.webui.LogoutResource;

import com.google.inject.Injector;

/**
 * Configuration point for Employee management. Web Application.
 *
 * @author Generated
 */
public class ApplicationConfiguration extends Component {

    private final Injector injector;

    public ApplicationConfiguration(final Properties props) {
        // /////////////////////////////////////////////////////
        // ////// configure Hibernate and Guice injector ///////
        // /////////////////////////////////////////////////////
        try {
            // create application IoC module and injector
            final ApplicationDomain applicationDomainProvider = new ApplicationDomain();

            final WebApplicationServerModule module = new WebApplicationServerModule(
                    HibernateSetup.getHibernateTypes(),
                    applicationDomainProvider,
                    applicationDomainProvider.domainTypes(),
                    SerialisationClassProvider.class,
                    NoDataFilter.class,
                    props);
            injector = new ApplicationInjectorFactory()
                    .add(module)
                    .add(new NewUserEmailNotifierBindingModule())
                    .getInjector();

            ////////////////////////////////////////////////////////////////
            /////// Create a component with an HTTP server connector ///////
            ////////////////////////////////////////////////////////////////
            // application configuration
            final IWebUiConfig webApp = injector.getInstance(IWebUiConfig.class);

            // attach system resources, which should be beyond the version scope
            // the interactive login page resource is considered one of the system resources, which does not require guarding
            getDefaultHost().attach(LoginResource.BINDING_PATH, new LoginResourceFactory(true, injector.getInstance(RestServerUtil.class), injector));
            getDefaultHost().attach(LoginInitiateResetResource.BINDING_PATH, new LoginInitiateResetResourceFactory(injector));
            getDefaultHost().attach(LoginCompleteResetResource.BINDING_PATH, new LoginCompleteResetResourceFactory(injector, "Imagination is the limit."));
            getDefaultHost().attach(LogoutResource.BINDING_PATH, new LogoutResourceFactory(webApp.getDomainName(), webApp.getPath(), injector));
            // attach a web resource that represents this application
            getDefaultHost().attach(
                    new WebUiResources(
                            getContext().createChildContext(),
                            injector,
                            "Employee management.",
                            "An application server for Employee management.",
                            "ICE_AGE",
                            "Authors",
                            webApp
                            ));

        } catch (final Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public Injector injector() {
        return injector;
    }

}
