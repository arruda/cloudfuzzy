var app = app || {};

// load the following using JQuery's document ready function
$(function(){

    // System model
    app.System = Backbone.Model.extend({
        base_url: function() {
          var temp_url = Backbone.Model.prototype.url.call(this);
          return (temp_url.charAt(temp_url.length - 1) == '/' ? temp_url : temp_url+'/');
        },

        // url: function() {
        //   return this.base_url();
        // }

        url: function() {
            var id = this.id || '';
            return "/api/v1/system/"+id;
        },
    });

    // define the collection of passwords
    app.SystemCollection = Backbone.Collection.extend({
        model: app.System,
        // A catcher for the meta object TastyPie will return.
        meta: {},

        url: '/api/v1/system',
        // Our API will return an object with meta, then objects list.
        parse: function(response) {
            this.meta = response.meta || {};
            return response.objects || response;
        }
    });

    // set up the view for a system
    app.SystemSmallDetailView = Backbone.View.extend({
        tagName: 'section',
        className: 'sub-content',
        template: ich.systemSmallDetailTmpl,
        events: {
         'click' : 'showAlert'

        },

        showAlert: function(){
            alert("You clicked me");
        },
        // // Bind our events.
        // events: {
        //     "click .destroy": "clear"
        // },

        // Set up our listeners to model events.
        initialize: function() {
        //     // (re)Render the view when the model changes
        //     this.listenTo(this.model, "change", this.render);

            // Remove the view when the model is destroyed
            this.listenTo(this.model, "destroy", this.remove);
        },

        // Just delete the view.
        clear: function() {
            console.log("clear");
            this.model.destroy();
        },

        render: function () {
            // template with ICanHaz.js (ich)
            this.el = ich.systemSmallDetailTmpl(this.model.toJSON());
            return this;
        }
    });

    // SystemList view
    app.SystemListView = Backbone.View.extend({
        menu_el: "#menu",
        el: ".content-area",
        el_header: ".content-header",

        initialize: function() {
            // instantiate a system collection
            this.systems = new app.SystemCollection();
            this.systems.bind('all', this.render, this);
            this.systems.fetch();
            console.log("initialize");
            console.log(this.systems);
        },

        render: function () {

            console.log("render");
            console.log(this.systems);

            // renders the menu:
            $(this.menu_el).html( ich.systemListMenuTmpl() );

            // content header
            $(this.el_header).html( "Fuzzy System Listing" );

            // wrapps all with a content_full div
            var content_full = ich.subcontentFullTmpl();

            this.systems.each(function (system) {
                console.log(system);
                $(content_full).append(new app.SystemSmallDetailView({model: system}).render().el);
            }, this);

            $(this.el).html( content_full );

            return this;
        }
    });

});
