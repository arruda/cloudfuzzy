#!/bin/bash

set -e


if [[ -e /home/grunt/project/.grunt_firstrun ]]; then
  source /scripts/normal_run.sh
else
  source /scripts/first_run.sh
fi

pre_start_action

post_start_action

echo "starting grunt watch"
grunt --gruntfile/home/grunt/project/Gruntfile.coffee