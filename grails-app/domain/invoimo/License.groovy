package invoimo

class License {

    private String mId

    private Date mStartDate
    private Date mEndDate

    private String mLicenser

    static constraints = {
        mId(nullable:false)
        mStartDate(nullable:false)
        mEndDate(min:mStartDate++)
        mLicenser(size:3..100)
    }
}
