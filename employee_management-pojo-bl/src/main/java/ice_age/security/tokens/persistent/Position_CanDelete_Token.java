package ice_age.security.tokens.persistent;

import static java.lang.String.format;

import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.security.tokens.Template;
import ice_age.Position.Position;
import ice_age.security.tokens.UsersAndPersonnelModuleToken;

/**
 * A security token for entity {@link Position} to guard Delete.
 *
 * @author Developers
 *
 */
public class Position_CanDelete_Token extends UsersAndPersonnelModuleToken {
    public final static String TITLE = format(Template.DELETE.forTitle(), Position.ENTITY_TITLE);
    public final static String DESC = format(Template.DELETE.forDesc(), Position.ENTITY_TITLE);
}