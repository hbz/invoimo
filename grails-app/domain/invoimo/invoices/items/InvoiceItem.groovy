package invoimo.invoices.items

import invoimo.invoices.Invoice

class InvoiceItem {

    final private Currency currency
    final private float amount
    float num
    String description
    boolean isDiscount = false
    boolean isTaxable = false
    float taxRate
    Date progressDate
    Date finalizeDate
    static belongsTo=[invoice:Invoice]

    static constraints = {
        amount(min:0.0f)
        // num(min:0.0f)
        description(size:3..100)
        invoice nullable: true
    }

    protected InvoiceItem(final float aAmount, final Currency aCurrency) {
        this(aAmount, aCurrency, 1f, false)
    }

    protected InvoiceItem(final float aAmount, final Currency aCurrency, final float aNumber) {
        this(aAmount, aCurrency, aNumber, false)
    }

    protected InvoiceItem(final float aAmount, final Currency aCurrency, final boolean isDiscount) {
        this(aAmount, aCurrency, 1f, isDiscount)
    }

    protected InvoiceItem(final float aAmount, final Currency aCurrency, final float aNumber, final boolean aIsDiscount){
        currency = aCurrency
        amount = aAmount
        num = aNumber
        isDiscount = aIsDiscount
        progressDate = new Date()
    }

    void setDescription(String aDescription) {
        description = aDescription
    }

    boolean isFinalized() {
        return finalizeDate != null
    }

    boolean setFinalized() {
        if (isFinalized()) {
            return false
        }
        finalizeDate = new Date()
        return true
    }

    Date getProgressDate(){
        return progressDate
    }

    Date getFinalizeDate() {
        return finalizeDate
    }

    float getAmount() {
        return amount
    }
}
