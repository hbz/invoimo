package invoimo.invoices.items

class PercentualItem extends InvoiceItem{

    final private static int digits = 2;

    static constraints = {
    }

    PercentualItem(final float aBaseAmount, final float aPercentage, final Currency aCurrency){
        this(aBaseAmount, aPercentage, aCurrency, 1)
    }

    PercentualItem(final float aBaseAmount, final float aPercentage,
                          final Currency aCurrency, final float aCount){
        super(round(aBaseAmount, aPercentage), aCurrency, aCount, aBaseAmount * aPercentage < 0.0)
    }

    private static float round(final float aBaseAmount, final float aPercentage){
        return (float) (Math.round(aBaseAmount * aPercentage * 100.0) / 100.0)
    }
}
