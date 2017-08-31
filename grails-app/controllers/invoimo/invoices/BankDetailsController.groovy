package invoimo.invoices

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = false)
class BankDetailsController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond BankDetails.list(params), model:[bankDetailsCount: BankDetails.count()]
    }

    def show(BankDetails bankDetails) {
        respond bankDetails
    }

    def create() {
        respond new BankDetails(params)
    }

    @Transactional
    def save(BankDetails bankDetails) {
        if (bankDetails == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (bankDetails.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond bankDetails.errors, view:'create'
            return
        }

        bankDetails.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'bankDetails.label', default: 'BankDetails'), bankDetails.id])
                redirect bankDetails
            }
            '*' { respond bankDetails, [status: CREATED] }
        }
    }

    def edit(BankDetails bankDetails) {
        respond bankDetails
    }

    @Transactional
    def update(BankDetails bankDetails) {
        if (bankDetails == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (bankDetails.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond bankDetails.errors, view:'edit'
            return
        }

        bankDetails.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'bankDetails.label', default: 'BankDetails'), bankDetails.id])
                redirect bankDetails
            }
            '*'{ respond bankDetails, [status: OK] }
        }
    }

    @Transactional
    def delete(BankDetails bankDetails) {

        if (bankDetails == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        bankDetails.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'bankDetails.label', default: 'BankDetails'), bankDetails.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'bankDetails.label', default: 'BankDetails'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
