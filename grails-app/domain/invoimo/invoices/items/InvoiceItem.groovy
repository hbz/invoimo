package invoimo.invoices.items

abstract class InvoiceItem {

    final private Currency mCurrency
    final private float mAmount
    private float mCount
    private boolean mIsDiscount = false
    private String mDescription
    private Date mProgressDate
    private Date mFinalizeDate

    static constraints = {
        mCurrency(nullable:false)
        mAmount(min:0.0, nullable:false)
        mCount(min:0.0)
        mDescription(size:3..100)
    }

    protected InvoiceItem(final float aAmount, final Currency aCurrency) {
        this(aAmount, aCurrency, 1, false)
    }

    protected InvoiceItem(final float aAmount, final Currency aCurrency, final float aCount) {
        this(aAmount, aCurrency, aCount, false)
    }

    protected InvoiceItem(final float aAmount, final Currency aCurrency, final boolean isDiscount) {
        this(aAmount, aCurrency, 1, isDiscount)
    }

    protected InvoiceItem(final float aAmount, final Currency aCurrency, final float aCount, final boolean aIsDiscount){
        mCurrency = aCurrency
        mAmount = aAmount
        mCount = aCount
        mIsDiscount = aIsDiscount
        mProgressDate = new Date()
    }

    void setDescription(String aDescription) {
        mDescription = aDescription
    }

    boolean isFinalized() {
        return mFinalizeDate != null
    }

    boolean setFinalized() {
        if (isFinalized()) {
            return false
        }
        mFinalizeDate = new Date()
        return true
    }

    Date getProgressDate(){
        return mProgressDate
    }

    Date getFinalizeDate() {
        return mFinalizeDate
    }

    float getAmount() {
        return mAmount
    }
}
