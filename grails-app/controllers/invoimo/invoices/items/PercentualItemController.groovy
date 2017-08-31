package invoimo.invoices.items

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = false)
class PercentualItemController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond PercentualItem.list(params), model:[percentualItemCount: PercentualItem.count()]
    }

    def show(PercentualItem percentualItem) {
        respond percentualItem
    }

    def create() {
        respond new PercentualItem(params)
    }

    @Transactional
    def save(PercentualItem percentualItem) {
        if (percentualItem == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (percentualItem.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond percentualItem.errors, view:'create'
            return
        }

        percentualItem.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'percentualItem.label', default: 'PercentualItem'), percentualItem.id])
                redirect percentualItem
            }
            '*' { respond percentualItem, [status: CREATED] }
        }
    }

    def edit(PercentualItem percentualItem) {
        respond percentualItem
    }

    @Transactional
    def update(PercentualItem percentualItem) {
        if (percentualItem == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (percentualItem.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond percentualItem.errors, view:'edit'
            return
        }

        percentualItem.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'percentualItem.label', default: 'PercentualItem'), percentualItem.id])
                redirect percentualItem
            }
            '*'{ respond percentualItem, [status: OK] }
        }
    }

    @Transactional
    def delete(PercentualItem percentualItem) {

        if (percentualItem == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        percentualItem.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'percentualItem.label', default: 'PercentualItem'), percentualItem.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'percentualItem.label', default: 'PercentualItem'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
