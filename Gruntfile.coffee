module.exports = (grunt) ->
    grunt.initConfig(
        pkg: grunt.file.readJSON('package.json')
        coffee:
            files:
                src: ['cloudfuzzy/src/js/**/*.coffee']
                dest: 'cloudfuzzy/static/js/script.js'
    )

    grunt.loadNpmTasks('grunt-contrib-coffee')

    grunt.registerTask('default', ['coffee'])
