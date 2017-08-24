package invoimo.invoices

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class IncomingInvoiceController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond IncomingInvoice.list(params), model:[incomingInvoiceCount: IncomingInvoice.count()]
    }

    def show(IncomingInvoice incomingInvoice) {
        respond incomingInvoice
    }

    def create() {
        respond new IncomingInvoice(params)
    }

    @Transactional
    def save(IncomingInvoice incomingInvoice) {
        if (incomingInvoice == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (incomingInvoice.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond incomingInvoice.errors, view:'create'
            return
        }

        incomingInvoice.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'incomingInvoice.label', default: 'IncomingInvoice'), incomingInvoice.id])
                redirect incomingInvoice
            }
            '*' { respond incomingInvoice, [status: CREATED] }
        }
    }

    def edit(IncomingInvoice incomingInvoice) {
        respond incomingInvoice
    }

    @Transactional
    def update(IncomingInvoice incomingInvoice) {
        if (incomingInvoice == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (incomingInvoice.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond incomingInvoice.errors, view:'edit'
            return
        }

        incomingInvoice.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'incomingInvoice.label', default: 'IncomingInvoice'), incomingInvoice.id])
                redirect incomingInvoice
            }
            '*'{ respond incomingInvoice, [status: OK] }
        }
    }

    @Transactional
    def delete(IncomingInvoice incomingInvoice) {

        if (incomingInvoice == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        incomingInvoice.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'incomingInvoice.label', default: 'IncomingInvoice'), incomingInvoice.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'incomingInvoice.label', default: 'IncomingInvoice'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
