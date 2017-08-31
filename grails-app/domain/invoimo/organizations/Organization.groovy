package invoimo.organizations

class Organization {

    String name
    Address address
    String contactPerson
    String costCentre

    static constraints = {
        contactPerson nullable: true
        costCentre nullable: true
    }

}
