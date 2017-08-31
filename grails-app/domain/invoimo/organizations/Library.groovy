package invoimo.organizations

import invoimo.helpers.SettingsChecker

class Library extends Organization {

    String isil
    String dbsId

    // TODO: libraries without isil AND dbsid? (foreign?)
    static constraints = {

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
