package invoimo.invoices.items

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class InvoiceItemController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond InvoiceItem.list(params), model:[invoiceItemCount: InvoiceItem.count()]
    }

    def show(InvoiceItem invoiceItem) {
        respond invoiceItem
    }

    def create() {
        respond new InvoiceItem(params)
    }

    @Transactional
    def save(InvoiceItem invoiceItem) {
        if (invoiceItem == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (invoiceItem.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond invoiceItem.errors, view:'create'
            return
        }

        invoiceItem.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'invoiceItem.label', default: 'InvoiceItem'), invoiceItem.id])
                redirect invoiceItem
            }
            '*' { respond invoiceItem, [status: CREATED] }
        }
    }

    def edit(InvoiceItem invoiceItem) {
        respond invoiceItem
    }

    @Transactional
    def update(InvoiceItem invoiceItem) {
        if (invoiceItem == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (invoiceItem.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond invoiceItem.errors, view:'edit'
            return
        }

        invoiceItem.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'invoiceItem.label', default: 'InvoiceItem'), invoiceItem.id])
                redirect invoiceItem
            }
            '*'{ respond invoiceItem, [status: OK] }
        }
    }

    @Transactional
    def delete(InvoiceItem invoiceItem) {

        if (invoiceItem == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        invoiceItem.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'invoiceItem.label', default: 'InvoiceItem'), invoiceItem.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'invoiceItem.label', default: 'InvoiceItem'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
