package invoimo.invoices

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class BankingInfoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond BankingInfo.list(params), model:[bankingInfoCount: BankingInfo.count()]
    }

    def show(BankingInfo bankingInfo) {
        respond bankingInfo
    }

    def create() {
        respond new BankingInfo(params)
    }

    @Transactional
    def save(BankingInfo bankingInfo) {
        if (bankingInfo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (bankingInfo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond bankingInfo.errors, view:'create'
            return
        }

        bankingInfo.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'bankingInfo.label', default: 'BankingInfo'), bankingInfo.id])
                redirect bankingInfo
            }
            '*' { respond bankingInfo, [status: CREATED] }
        }
    }

    def edit(BankingInfo bankingInfo) {
        respond bankingInfo
    }

    @Transactional
    def update(BankingInfo bankingInfo) {
        if (bankingInfo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (bankingInfo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond bankingInfo.errors, view:'edit'
            return
        }

        bankingInfo.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'bankingInfo.label', default: 'BankingInfo'), bankingInfo.id])
                redirect bankingInfo
            }
            '*'{ respond bankingInfo, [status: OK] }
        }
    }

    @Transactional
    def delete(BankingInfo bankingInfo) {

        if (bankingInfo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        bankingInfo.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'bankingInfo.label', default: 'BankingInfo'), bankingInfo.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'bankingInfo.label', default: 'BankingInfo'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
