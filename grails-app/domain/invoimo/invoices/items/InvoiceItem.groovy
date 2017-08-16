package invoimo.invoices.items

import invoimo.invoices.Invoice

class InvoiceItem {

    final private Currency currency
    final private float amount
    private float number
    private boolean isDiscount = false
    private boolean isTaxable = false
    private float taxRate
    private String description
    private Date progressDate
    private Date finalizeDate
    static belongsTo=[invoice:Invoice]

    static constraints = {
        currency(nullable:false)
        amount(min:0.0, nullable:false)
        number(min:0.0)
        description(size:3..100)
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
        currency = aCurrency
        amount = aAmount
        number = aCount
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
