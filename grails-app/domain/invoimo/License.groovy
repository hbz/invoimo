package invoimo

class License {

    String license_id
    String licenser

    Date startDate
    Date endDate

    static constraints = {
        license_id(nullable:false)
        licenser(size:3..100)
        startDate(nullable:false)
        endDate(validator:{
            value, reference ->
                return !(value.before(reference.startDate))
        })
    }

}
