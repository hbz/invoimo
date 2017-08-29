package invoimo.organizations

class Organization {

    private String name
    private Address address
    private String contactPerson
    private String costCentre

    static constraints = {
        contactPerson nullable: true
    }

    Address getAddress() {
        return address
    }

    String getContactPerson() {
        return contactPerson
    }

    String getName() {
        return name
    }

    String getCostCentre() {
        return costCentre
    }

    void setContactPerson(String aContactPerson) {
        this.contactPerson = aContactPerson
    }

    void setName(String aName) {
        this.name= aName
    }

    void setCostCentre(String aCostCentre) {
        this.costCentre = aCostCentre
    }

    void setAddress(Address aAddress){
        address = aAddress
    }
}
