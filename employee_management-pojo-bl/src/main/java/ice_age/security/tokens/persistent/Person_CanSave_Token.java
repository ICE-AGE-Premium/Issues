package ice_age.security.tokens.persistent;

import static java.lang.String.format;
import static ua.com.fielden.platform.reflection.TitlesDescsGetter.getEntityTitleAndDesc;

import ice_age.security.tokens.UsersAndPersonnelModuleToken;
import ice_age.personnel.Person;
import ua.com.fielden.platform.security.tokens.Template;

/**
 * A security token for entity {@link Person} to guard SAVE.
 *
 * @author Generated
 */
public class Person_CanSave_Token extends UsersAndPersonnelModuleToken {

    private final static String ENTITY_TITLE = getEntityTitleAndDesc(Person.class).getKey();
    public final static String TITLE = format(Template.SAVE.forTitle(), ENTITY_TITLE);
    public final static String DESC = format(Template.SAVE.forDesc(), ENTITY_TITLE);

}
