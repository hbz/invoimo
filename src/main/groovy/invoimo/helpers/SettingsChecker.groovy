package invoimo.helpers

import org.apache.commons.lang.StringUtils

/**
 * Created by boeselager on 10.08.17.
 */
class SettingsChecker {

    static boolean canBeOverwrittenByString(final String aOld, final String aNew){
        if (StringUtils.isEmpty(aOld)){
            return true
        }
        if (StringUtils.isEmpty(aNew)){
            throw new IllegalArgumentException("Overwrite library isil with empty argument.")
            // TODO: log warning instead?
        }
        else if (aNew != aOld){
            throw new IllegalArgumentException("Reset library isil with different value.")
            // TODO: log warning instead?
        }
        return true
    }

    static boolean canBeOverwrittenByObject(final Object aOld, final Object aNew){
        // TODO (?)
        return false
    }
}
