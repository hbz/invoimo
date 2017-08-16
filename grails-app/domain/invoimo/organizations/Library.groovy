package invoimo.organizations

import invoimo.helpers.SettingsChecker

class Library {

    private String isil
    private String dbsId

    static constraints = {
        isil(nullable:false)
        dbsId(nullable:false)
    }

    // TODO: libraries without isil AND dbsid? (foreign?)

    Library (String aIsil, String aDbsId){
        isil = aIsil
        dbsId = aDbsId
    }

    void setIsil(String aIsil){
        if (SettingsChecker.canBeOverwrittenByString(isil, aIsil)){
            isil = aIsil
        }
    }

    void setDbsId(String aDbsId){
        if (SettingsChecker.canBeOverwrittenByString(dbsId, aDbsId)){
            dbsId = aDbsId
        }
    }
}
