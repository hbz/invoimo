package invoimo.invoices.items

import grails.gorm.transactions.Transactional
import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class FeeItemController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond FeeItem.list(params), model:[feeItemCount: FeeItem.count()]
    }

    def show(FeeItem feeItem) {
        respond feeItem
    }

    def create() {
        respond new FeeItem(params)
    }

    @Transactional
    def save(FeeItem feeItem) {
        if (feeItem == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (feeItem.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond feeItem.errors, view:'create'
            return
        }

        feeItem.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'feeItem.label', default: 'FeeItem'), feeItem.id])
                redirect feeItem
            }
            '*' { respond feeItem, [status: CREATED] }
        }
    }

    def edit(FeeItem feeItem) {
        respond feeItem
    }

    @Transactional
    def update(FeeItem feeItem) {
        if (feeItem == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (feeItem.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond feeItem.errors, view:'edit'
            return
        }

        feeItem.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'feeItem.label', default: 'FeeItem'), feeItem.id])
                redirect feeItem
            }
            '*'{ respond feeItem, [status: OK] }
        }
    }

    @Transactional
    def delete(FeeItem feeItem) {

        if (feeItem == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        feeItem.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'feeItem.label', default: 'FeeItem'), feeItem.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'feeItem.label', default: 'FeeItem'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
