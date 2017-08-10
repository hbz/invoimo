package invoimo.organizations

class Organization {

    private Address mAddress
    private String mContactPerson
    private String mCostCentre

    static constraints = {
    }

    Address getAddress() {
        return mAddress
    }

    String getContactPerson() {
        return mContactPerson
    }

    String getCostCentre() {
        return mCostCentre
    }

    void setContactPerson(String aContactPerson) {
        this.mContactPerson = aContactPerson
    }

    void setCostCentre(String aCostCentre) {
        this.mCostCentre = aCostCentre
    }

    void setAddress(Address aAddress){
        mAddress = aAddress
    }
}
