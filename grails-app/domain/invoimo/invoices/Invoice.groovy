package invoimo.invoices

import invoimo.invoices.items.InvoiceItem
import invoimo.organizations.Organization

class Invoice {

    private Organization mIssuer
    private Organization mRecipient
    private BankingInfo mBankingInfo

    private List<InvoiceItem> mItems
    private float mSum
    private List<String> mNotes

    private InvoiceStatus mStatus
    private Currency mCurrency
    private Date mMaturity

    private Date mProgressDate
    private Date mFinalizeDate

    static constraints = {
    }

    protected Invoice(){
        mProgressDate = new Date()
        mSum = 0.0f
    }

    enum InvoiceType{
        DEFAULT,
        ADDITIONAL_CLAIM,
        CANCELLATION,
        PARTIAL_INVOICE,
        RETRANSFER
    }

    InvoiceStatus getStatus() {
        return mStatus;
    }

    boolean isFinalized() {
        return mFinalizeDate != null;
    }

    boolean setFinalized() {
        if (isFinalized()) {
            return false
        }
        mFinalizeDate = new Date()
        for (InvoiceItem item : mItems){
            item.setFinalized()
        }
        return true
    }

    Date getProgressDate(){
        return mProgressDate
    }

    Date getFinalizeDate(){
        return mFinalizeDate
    }

    void addItem(InvoiceItem aInvoiceItem){
        mItems.add(aInvoiceItem)
    }

    float calculateSum(){
        for (InvoiceItem item : mItems){
            mSum += item.getAmount()
        }
        return mSum
    }

    void check(){
        // TODO
    }
}
