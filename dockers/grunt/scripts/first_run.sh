
pre_start_action() {
    npm install -g grunt-cli
    npm install
    bower install
}

post_start_action() {

    touch /home/grunt/project/.grunt_firstrun
}
