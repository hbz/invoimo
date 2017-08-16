package invoimo.invoices.items

import grails.test.mixin.*
import spock.lang.*

@TestFor(PercentualItemController)
@Mock(PercentualItem)
class PercentualItemControllerSpec extends Specification {

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
            !model.percentualItemList
            model.percentualItemCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.percentualItem!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def percentualItem = new PercentualItem()
            percentualItem.validate()
            controller.save(percentualItem)

        then:"The create view is rendered again with the correct model"
            model.percentualItem!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            percentualItem = new PercentualItem(params)

            controller.save(percentualItem)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/percentualItem/show/1'
            controller.flash.message != null
            PercentualItem.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def percentualItem = new PercentualItem(params)
            controller.show(percentualItem)

        then:"A model is populated containing the domain instance"
            model.percentualItem == percentualItem
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def percentualItem = new PercentualItem(params)
            controller.edit(percentualItem)

        then:"A model is populated containing the domain instance"
            model.percentualItem == percentualItem
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/percentualItem/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def percentualItem = new PercentualItem()
            percentualItem.validate()
            controller.update(percentualItem)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.percentualItem == percentualItem

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            percentualItem = new PercentualItem(params).save(flush: true)
            controller.update(percentualItem)

        then:"A redirect is issued to the show action"
            percentualItem != null
            response.redirectedUrl == "/percentualItem/show/$percentualItem.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/percentualItem/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def percentualItem = new PercentualItem(params).save(flush: true)

        then:"It exists"
            PercentualItem.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(percentualItem)

        then:"The instance is deleted"
            PercentualItem.count() == 0
            response.redirectedUrl == '/percentualItem/index'
            flash.message != null
    }
}
