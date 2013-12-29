// load the following using JQuery's document ready function
$(function(){

    // System model
    var System = Backbone.Model.extend({
        base_url: function() {
          var temp_url = Backbone.Model.prototype.url.call(this);
          return (temp_url.charAt(temp_url.length - 1) == '/' ? temp_url : temp_url+'/');
        },

        // url: function() {
        //   return this.base_url();
        // }

        url: function() {
            var id = this.id || '';
            return "/api/system/"+id;
        },
    });

    // set up the view for a system
    var SystemView = Backbone.View.extend({
        render: function () {
            // template with ICanHaz.js (ich)
            //console.log(this.model.toJSON());
            this.el = ich.systemSubContentTmpl(this.model.toJSON());
            return this;
        }
    });

    // define the collection of passwords
    var SystemCollection = Backbone.Collection.extend({
        model: System,
        // A catcher for the meta object TastyPie will return.
        meta: {},

        url: '/api/system',
        // Our API will return an object with meta, then objects list.
        parse: function(response) {
            this.meta = response.meta;
            return response.objects || response;
        }
    });

    // main app
    var AppView = Backbone.View.extend({
        // tagName: 'div',
        // id: 'content_area',

        initialize: function() {
            // instantiate a system collection
            this.systems = new SystemCollection();
            this.systems.bind('all', this.render, this);
            this.systems.fetch();
            console.log("initialize");
            console.log(this.systems);
        },

        render: function () {
            console.log("render");
            console.log(this.systems);
            // template with ICanHaz.js (ich)
            $(this.el).html("")
            this.systems.each(function (system) {
                console.log(system);
                $(this.el).append(new SystemView({model: system}).render().el);
            }, this);

            return this;
        }
    });

    var app = new AppView();
    $('#content_area').append(app.render().el);
});
