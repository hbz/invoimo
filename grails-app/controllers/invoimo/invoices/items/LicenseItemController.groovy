package invoimo.invoices.items

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = false)
class LicenseItemController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond LicenseItem.list(params), model:[licenseItemCount: LicenseItem.count()]
    }

    def show(LicenseItem licenseItem) {
        respond licenseItem
    }

    def create() {
        respond new LicenseItem(params)
    }

    @Transactional
    def save(LicenseItem licenseItem) {
        if (licenseItem == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (licenseItem.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond licenseItem.errors, view:'create'
            return
        }

        licenseItem.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'licenseItem.label', default: 'LicenseItem'), licenseItem.id])
                redirect licenseItem
            }
            '*' { respond licenseItem, [status: CREATED] }
        }
    }

    def edit(LicenseItem licenseItem) {
        respond licenseItem
    }

    @Transactional
    def update(LicenseItem licenseItem) {
        if (licenseItem == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (licenseItem.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond licenseItem.errors, view:'edit'
            return
        }

        licenseItem.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'licenseItem.label', default: 'LicenseItem'), licenseItem.id])
                redirect licenseItem
            }
            '*'{ respond licenseItem, [status: OK] }
        }
    }

    @Transactional
    def delete(LicenseItem licenseItem) {

        if (licenseItem == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        licenseItem.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'licenseItem.label', default: 'LicenseItem'), licenseItem.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'licenseItem.label', default: 'LicenseItem'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
