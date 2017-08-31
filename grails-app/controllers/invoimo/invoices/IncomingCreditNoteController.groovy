package invoimo.invoices

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = false)
class IncomingCreditNoteController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond IncomingCreditNote.list(params), model:[incomingCreditNoteCount: IncomingCreditNote.count()]
    }

    def show(IncomingCreditNote incomingCreditNote) {
        respond incomingCreditNote
    }

    def create() {
        respond new IncomingCreditNote(params)
    }

    @Transactional
    def save(IncomingCreditNote incomingCreditNote) {
        if (incomingCreditNote == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (incomingCreditNote.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond incomingCreditNote.errors, view:'create'
            return
        }

        incomingCreditNote.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'incomingCreditNote.label', default: 'IncomingCreditNote'), incomingCreditNote.id])
                redirect incomingCreditNote
            }
            '*' { respond incomingCreditNote, [status: CREATED] }
        }
    }

    def edit(IncomingCreditNote incomingCreditNote) {
        respond incomingCreditNote
    }

    @Transactional
    def update(IncomingCreditNote incomingCreditNote) {
        if (incomingCreditNote == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (incomingCreditNote.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond incomingCreditNote.errors, view:'edit'
            return
        }

        incomingCreditNote.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'incomingCreditNote.label', default: 'IncomingCreditNote'), incomingCreditNote.id])
                redirect incomingCreditNote
            }
            '*'{ respond incomingCreditNote, [status: OK] }
        }
    }

    @Transactional
    def delete(IncomingCreditNote incomingCreditNote) {

        if (incomingCreditNote == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        incomingCreditNote.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'incomingCreditNote.label', default: 'IncomingCreditNote'), incomingCreditNote.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'incomingCreditNote.label', default: 'IncomingCreditNote'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
