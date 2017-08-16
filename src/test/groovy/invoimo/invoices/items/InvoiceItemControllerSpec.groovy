package invoimo.invoices.items

import grails.test.mixin.*
import spock.lang.*

@TestFor(InvoiceItemController)
@Mock(InvoiceItem)
class InvoiceItemControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null

        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
        assert false, "TODO: Provide a populateValidParams() implementation for this generated test suite"
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.invoiceItemList
            model.invoiceItemCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.invoiceItem!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def invoiceItem = new InvoiceItem()
            invoiceItem.validate()
            controller.save(invoiceItem)

        then:"The create view is rendered again with the correct model"
            model.invoiceItem!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            invoiceItem = new InvoiceItem(params)

            controller.save(invoiceItem)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/invoiceItem/show/1'
            controller.flash.message != null
            InvoiceItem.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def invoiceItem = new InvoiceItem(params)
            controller.show(invoiceItem)

        then:"A model is populated containing the domain instance"
            model.invoiceItem == invoiceItem
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def invoiceItem = new InvoiceItem(params)
            controller.edit(invoiceItem)

        then:"A model is populated containing the domain instance"
            model.invoiceItem == invoiceItem
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/invoiceItem/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def invoiceItem = new InvoiceItem()
            invoiceItem.validate()
            controller.update(invoiceItem)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.invoiceItem == invoiceItem

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            invoiceItem = new InvoiceItem(params).save(flush: true)
            controller.update(invoiceItem)

        then:"A redirect is issued to the show action"
            invoiceItem != null
            response.redirectedUrl == "/invoiceItem/show/$invoiceItem.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/invoiceItem/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def invoiceItem = new InvoiceItem(params).save(flush: true)

        then:"It exists"
            InvoiceItem.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(invoiceItem)

        then:"The instance is deleted"
            InvoiceItem.count() == 0
            response.redirectedUrl == '/invoiceItem/index'
            flash.message != null
    }
}
