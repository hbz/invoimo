package invoimo.invoices

import invoimo.invoices.items.InvoiceItem
import invoimo.organizations.Organization

class Invoice {

    String identifier
    Organization issuer
    Organization recipient
    BankDetails bankDetails

    static hasMany=[items:InvoiceItem, notes:String]
    float sum = 0.0f

    InvoiceStatus status
    Currency currency
    Date maturity

    Date progressDate = new Date()
    Date finalizeDate

    static constraints = {
        identifier unique: true
    }

    enum InvoiceType{
        DEFAULT,
        ADDITIONAL_CLAIM,
        CANCELLATION,
        PARTIAL_INVOICE,
        RETRANSFER
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
