package invoimo.invoices.items

class FeeItem extends InvoiceItem{

    static constraints = {
    }

    FeeItem(final float aAmount, final Currency aCurrency) {
        this(aAmount, aCurrency, 1f)
    }

    FeeItem(final float aAmount, final Currency aCurrency, final float aCount) {
        super(aAmount, aCurrency, aCount)
        // TODO
    }
}
