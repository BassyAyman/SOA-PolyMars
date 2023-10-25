#!/bin/bash

gnome-terminal -- bash -c "./scripts/log_all.sh; exec bash"
# gnome-terminal -- bash -c "./log_displayIgor.sh; exec bash"
gnome-terminal -- bash -c "./scripts/log_webcaster.sh; exec bash"

sleep 1
gnome-terminal -- bash -c "./run.sh; exec bash"