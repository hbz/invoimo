package invoimo.organizations

class Organization {

    private Address address
    private String contactPerson
    private String costCentre

    static constraints = {
    }

    Address getAddress() {
        return address
    }

    String getContactPerson() {
        return contactPerson
    }

    String getCostCentre() {
        return costCentre
    }

    void setContactPerson(String aContactPerson) {
        this.contactPerson = aContactPerson
    }

    void setCostCentre(String aCostCentre) {
        this.costCentre = aCostCentre
    }

    void setAddress(Address aAddress){
        address = aAddress
    }
}
