package invoimo.invoices.items

class ObjectItem extends InvoiceItem{

    String name

    static constraints = {
        name(nullable:false, size:3..100)
    }

    ObjectItem(final float aAmount, final Currency aCurrency, final String aName) {
        this(aAmount, aCurrency, aName, 1)
    }

    ObjectItem(float aAmount, Currency aCurrency, final String aName, float aCount) {
        super(aAmount, aCurrency, aCount)
        name = aName
    }
}
