package invoimo.invoices

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = false)
class OutgoingCreditNoteController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond OutgoingCreditNote.list(params), model:[outgoingCreditNoteCount: OutgoingCreditNote.count()]
    }

    def show(OutgoingCreditNote outgoingCreditNote) {
        respond outgoingCreditNote
    }

    def create() {
        respond new OutgoingCreditNote(params)
    }

    @Transactional
    def save(OutgoingCreditNote outgoingCreditNote) {
        if (outgoingCreditNote == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (outgoingCreditNote.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond outgoingCreditNote.errors, view:'create'
            return
        }

        outgoingCreditNote.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'outgoingCreditNote.label', default: 'OutgoingCreditNote'), outgoingCreditNote.id])
                redirect outgoingCreditNote
            }
            '*' { respond outgoingCreditNote, [status: CREATED] }
        }
    }

    def edit(OutgoingCreditNote outgoingCreditNote) {
        respond outgoingCreditNote
    }

    @Transactional
    def update(OutgoingCreditNote outgoingCreditNote) {
        if (outgoingCreditNote == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (outgoingCreditNote.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond outgoingCreditNote.errors, view:'edit'
            return
        }

        outgoingCreditNote.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'outgoingCreditNote.label', default: 'OutgoingCreditNote'), outgoingCreditNote.id])
                redirect outgoingCreditNote
            }
            '*'{ respond outgoingCreditNote, [status: OK] }
        }
    }

    @Transactional
    def delete(OutgoingCreditNote outgoingCreditNote) {

        if (outgoingCreditNote == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        outgoingCreditNote.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'outgoingCreditNote.label', default: 'OutgoingCreditNote'), outgoingCreditNote.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'outgoingCreditNote.label', default: 'OutgoingCreditNote'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
