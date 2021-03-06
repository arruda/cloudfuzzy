#!/bin/bash
# Stop on error
set -e


# where is the data dir (has to be a volume to persist the info if is the first run or not)
DATA_DIR=/data

PROJ_DIR=/project


if [[ -e $DATA_DIR/firstrun ]]; then
  source /scripts/normal_run.sh
else
  source /scripts/first_run.sh
fi

pre_start_action

post_start_action

# Start runserver
echo "Running ${MANAGE_CMD} in Django Dev..."

$PROJ_DIR/manage.py ${MANAGE_CMD}
#runserver 0.0.0.0:8000