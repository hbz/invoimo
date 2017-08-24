package invoimo.invoices

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class OutgoingInvoiceController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond OutgoingInvoice.list(params), model:[outgoingInvoiceCount: OutgoingInvoice.count()]
    }

    def show(OutgoingInvoice outgoingInvoice) {
        respond outgoingInvoice
    }

    def create() {
        respond new OutgoingInvoice(params)
    }

    @Transactional
    def save(OutgoingInvoice outgoingInvoice) {
        if (outgoingInvoice == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (outgoingInvoice.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond outgoingInvoice.errors, view:'create'
            return
        }

        outgoingInvoice.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'outgoingInvoice.label', default: 'OutgoingInvoice'), outgoingInvoice.id])
                redirect outgoingInvoice
            }
            '*' { respond outgoingInvoice, [status: CREATED] }
        }
    }

    def edit(OutgoingInvoice outgoingInvoice) {
        respond outgoingInvoice
    }

    @Transactional
    def update(OutgoingInvoice outgoingInvoice) {
        if (outgoingInvoice == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (outgoingInvoice.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond outgoingInvoice.errors, view:'edit'
            return
        }

        outgoingInvoice.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'outgoingInvoice.label', default: 'OutgoingInvoice'), outgoingInvoice.id])
                redirect outgoingInvoice
            }
            '*'{ respond outgoingInvoice, [status: OK] }
        }
    }

    @Transactional
    def delete(OutgoingInvoice outgoingInvoice) {

        if (outgoingInvoice == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        outgoingInvoice.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'outgoingInvoice.label', default: 'OutgoingInvoice'), outgoingInvoice.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'outgoingInvoice.label', default: 'OutgoingInvoice'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
