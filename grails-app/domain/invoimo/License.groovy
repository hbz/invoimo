package invoimo

class License {

    String license_id
    String licenser

    Date startDate
    Date endDate

    static constraints = {
        licenser(size:3..100)
        endDate(validator:{
            value, reference ->
                return !(value.before(reference.startDate))
        })
    }

    @Override
    String toString() {
        StringBuilder result = new StringBuilder()
        result.append(licenser)
        result.append(", ").append(startDate)
        result.append(", ").append(endDate)
        result.append(" (").append(license_id).append(")")
        return result.toString()
    }
}
