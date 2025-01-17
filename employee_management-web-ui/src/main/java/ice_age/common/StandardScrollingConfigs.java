package ice_age.common;

import ua.com.fielden.platform.web.centre.api.resultset.scrolling.IScrollConfig;
import ua.com.fielden.platform.web.centre.api.resultset.scrolling.impl.ScrollConfig;

/**
 * Factory methods for creating standrad scrolling configuration for EGI.
 *
 * @author Generated
 */
public class StandardScrollingConfigs {

    private StandardScrollingConfigs() {}

    public static IScrollConfig standardStandaloneScrollingConfig(final int firstPropsToFix) {
        return ScrollConfig.configScroll().withFixedCheckboxesPrimaryActionsAndFirstProps(firstPropsToFix).withFixedSecondaryActions().withFixedHeader().done();
    }

    public static IScrollConfig standardEmbeddedScrollingConfig(final int firstPropsToFix) {
        return ScrollConfig.configScroll().withFixedCheckboxesPrimaryActionsAndFirstProps(firstPropsToFix).withFixedSecondaryActions().withFixedHeader().done();
    }

}
