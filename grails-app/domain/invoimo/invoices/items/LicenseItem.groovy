package invoimo.invoices.items

import invoimo.License

class LicenseItem extends InvoiceItem{

    static constraints = {
    }

    private License mLicense

    LicenseItem(final float aAmount, final Currency aCurrency, final License aLicense) {
        super(aAmount, aCurrency)
        mLicense = aLicense
    }
}
