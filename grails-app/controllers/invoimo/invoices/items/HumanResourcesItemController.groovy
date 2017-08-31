package invoimo.invoices.items

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = false)
class HumanResourcesItemController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond HumanResourcesItem.list(params), model:[humanResourcesItemCount: HumanResourcesItem.count()]
    }

    def show(HumanResourcesItem humanResourcesItem) {
        respond humanResourcesItem
    }

    def create() {
        respond new HumanResourcesItem(params)
    }

    @Transactional
    def save(HumanResourcesItem humanResourcesItem) {
        if (humanResourcesItem == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (humanResourcesItem.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond humanResourcesItem.errors, view:'create'
            return
        }

        humanResourcesItem.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'humanResourcesItem.label', default: 'HumanResourcesItem'), humanResourcesItem.id])
                redirect humanResourcesItem
            }
            '*' { respond humanResourcesItem, [status: CREATED] }
        }
    }

    def edit(HumanResourcesItem humanResourcesItem) {
        respond humanResourcesItem
    }

    @Transactional
    def update(HumanResourcesItem humanResourcesItem) {
        if (humanResourcesItem == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (humanResourcesItem.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond humanResourcesItem.errors, view:'edit'
            return
        }

        humanResourcesItem.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'humanResourcesItem.label', default: 'HumanResourcesItem'), humanResourcesItem.id])
                redirect humanResourcesItem
            }
            '*'{ respond humanResourcesItem, [status: OK] }
        }
    }

    @Transactional
    def delete(HumanResourcesItem humanResourcesItem) {

        if (humanResourcesItem == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        humanResourcesItem.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'humanResourcesItem.label', default: 'HumanResourcesItem'), humanResourcesItem.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'humanResourcesItem.label', default: 'HumanResourcesItem'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
