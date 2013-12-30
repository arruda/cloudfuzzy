var app = app || {};

$(function() {

    // The view for the entire app.
    app.AppView = Backbone.View.extend({
        el: ".main",

        initialize: function() {
            new app.SystemListView();
        },
    });

    new app.AppView();
});
