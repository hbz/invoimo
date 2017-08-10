package invoimo.organizations

import invoimo.helpers.SettingsChecker

class Library {

    private String mIsil
    private String mDbsId

    static constraints = {
        mIsil(nullable:false)
        mDbsId(nullable:false)
    }

    // TODO: libraries without isil AND dbsid? (foreign?)

    Library (String aIsil, String aDbsId){
        mIsil = aIsil
        mDbsId = aDbsId
    }

    void setIsil(String aIsil){
        if (SettingsChecker.canBeOverwrittenByString(mIsil, aIsil)){
            mIsil = aIsil
        }
    }

    void setDbsId(String aDbsId){
        if (SettingsChecker.canBeOverwrittenByString(mDbsId, aDbsId)){
            mDbsId = aDbsId
        }
    }
}
