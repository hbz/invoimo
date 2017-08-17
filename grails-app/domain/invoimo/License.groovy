package invoimo

class License {

    private String license_id
    private String licenser

    private Date startDate
    private Date endDate

    static constraints = {
        license_id(nullable:false)
        licenser(size:3..100)
        startDate(nullable:false)
        endDate(validator: {
            !endDate.before(startDate)
        })
    }

    Date getStartDate() {
        return startDate
    }

    void setStartDate(Date startDate) {
        this.startDate = startDate
    }

    Date getEndDate() {
        return endDate
    }

    void setEndDate(Date endDate) {
        this.endDate = endDate
    }

}
