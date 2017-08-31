package invoimo

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = false)
class LicenseController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond License.list(params), model:[licenseCount: License.count()]
    }

    def show(License license) {
        respond license
    }

    def create() {
        respond new License(params)
    }

    @Transactional
    def save(License license) {
        if (license == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (license.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond license.errors, view:'create'
            return
        }

        license.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'license.label', default: 'License'), license.id])
                redirect license
            }
            '*' { respond license, [status: CREATED] }
        }
    }

    def edit(License license) {
        respond license
    }

    @Transactional
    def update(License license) {
        if (license == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (license.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond license.errors, view:'edit'
            return
        }

        license.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'license.label', default: 'License'), license.id])
                redirect license
            }
            '*'{ respond license, [status: OK] }
        }
    }

    @Transactional
    def delete(License license) {

        if (license == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        license.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'license.label', default: 'License'), license.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'license.label', default: 'License'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
