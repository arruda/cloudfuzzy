pre_start_action() {
    pip install -r $PROJ_DIR/requirements/local.txt
    $PROJ_DIR/manage.py syncdb --noinput
}

post_start_action() {
  touch $DATA_DIR/firstrun_django_dev
}
