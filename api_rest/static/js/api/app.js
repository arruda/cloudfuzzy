var app = app || {};

$(function() {

    // The view for the entire app.
    app.AppView = Backbone.View.extend({
        el: "#content_area",

        initialize: function() {
            // // TastyPie requires us to use a ?format=json param, so we'll
            // // set that as a default.
            // $.ajaxPrefilter(function(options) {
            //     _.extend(options, {format: "json"});
            // });


            // Get our todos from the API!
            // app.Todos.fetch();
            new app.SystemListView();
        },
    });

    // And we're off.
    new app.AppView();
});
