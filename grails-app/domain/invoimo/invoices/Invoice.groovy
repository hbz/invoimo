package invoimo.invoices

import invoimo.invoices.items.InvoiceItem
import invoimo.organizations.Organization

class Invoice {

    private Organization issuer
    private Organization recipient
    private BankingInfo bankingInfo

    static hasMany=[items:InvoiceItem, notes:String]
    private float sum

    private InvoiceStatus status
    private Currency currency
    private Date maturity

    private Date progressDate
    private Date finalizeDate

    static constraints = {
    }

    protected Invoice(){
        progressDate = new Date()
        sum = 0.0f
    }

    enum InvoiceType{
        DEFAULT,
        ADDITIONAL_CLAIM,
        CANCELLATION,
        PARTIAL_INVOICE,
        RETRANSFER
    }

    InvoiceStatus getStatus() {
        return status;
    }

    boolean isFinalized() {
        return finalizeDate != null
    }

    boolean setFinalized() {
        if (isFinalized()) {
            return false
        }
        finalizeDate = new Date()
        for (InvoiceItem item : items){
            item.setFinalized()
        }
        return true
    }

    Date getProgressDate(){
        return progressDate
    }

    Date getFinalizeDate(){
        return finalizeDate
    }

    void addItem(InvoiceItem aInvoiceItem){
        items.add(aInvoiceItem)
    }

    float calculateSum(){
        for (InvoiceItem item : items){
            sum += item.getAmount()
        }
        return sum
    }

    void check(){
        // TODO
    }
}
