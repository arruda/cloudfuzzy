
pre_start_action() {
    npm install -g grunt-cli bower
    npm install
    chmod -R 777 /project/cloudfuzzy/static
    bower install
}

post_start_action() {

    touch /home/grunt/project/.grunt_firstrun
}
