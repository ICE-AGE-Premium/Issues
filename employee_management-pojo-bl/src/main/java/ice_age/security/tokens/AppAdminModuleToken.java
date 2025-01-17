package ice_age.security.tokens;

import ice_age.config.Modules;
import ua.com.fielden.platform.security.ISecurityToken;

/**
 * Top level security token for all security tokens that belong to module {@link Modules#APP_ADMIN};
 *
 * @author Generated
 */
public class AppAdminModuleToken implements ISecurityToken {

    public static final String TITLE = Modules.APP_ADMIN.title;
    public static final String DESC = Modules.APP_ADMIN.desc;

}
