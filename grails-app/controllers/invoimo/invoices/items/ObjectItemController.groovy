package invoimo.invoices.items

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ObjectItemController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ObjectItem.list(params), model:[objectItemCount: ObjectItem.count()]
    }

    def show(ObjectItem objectItem) {
        respond objectItem
    }

    def create() {
        respond new ObjectItem(params)
    }

    @Transactional
    def save(ObjectItem objectItem) {
        if (objectItem == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (objectItem.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond objectItem.errors, view:'create'
            return
        }

        objectItem.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'objectItem.label', default: 'ObjectItem'), objectItem.id])
                redirect objectItem
            }
            '*' { respond objectItem, [status: CREATED] }
        }
    }

    def edit(ObjectItem objectItem) {
        respond objectItem
    }

    @Transactional
    def update(ObjectItem objectItem) {
        if (objectItem == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (objectItem.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond objectItem.errors, view:'edit'
            return
        }

        objectItem.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'objectItem.label', default: 'ObjectItem'), objectItem.id])
                redirect objectItem
            }
            '*'{ respond objectItem, [status: OK] }
        }
    }

    @Transactional
    def delete(ObjectItem objectItem) {

        if (objectItem == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        objectItem.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'objectItem.label', default: 'ObjectItem'), objectItem.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'objectItem.label', default: 'ObjectItem'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
