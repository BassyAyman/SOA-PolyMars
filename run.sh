#!/bin/bash

tmux kill-session -t mysession 2>/dev/null

tmux new-session -d -s mysession

# First top left pane for log_display_igor.sh
tmux send-keys -t mysession:0.0 './scripts/log_displayIgor.sh' C-m

# Split top pane horizontally for log_webcaster.sh
tmux split-window -h
tmux send-keys -t mysession:0.1 './scripts/log_webcaster.sh' C-m

# Create a new horizontal pane at the bottom for follow-metrics.sh
tmux select-pane -t mysession:0.0
tmux split-window -v
tmux resize-pane -D 10 # Resize to make it small enough for follow-metrics.sh
tmux send-keys -t mysession:0.2 'sleep 1; ./scripts/follow-metrics.sh' C-m

# Split the bottom pane horizontally for log_astronaute.sh
tmux select-pane -t mysession:0.2
tmux split-window -v
tmux resize-pane -D 1 # Resize to make it one line in height
tmux send-keys -t mysession:0.3 './scripts/log_astronaute.sh' C-m

# Start the hidden session
tmux new-session -d -s hidden_session
tmux send-keys -t hidden_session:0.0 './scripts/scenario.sh' C-m
tmux set -t hidden_session:0.0 pane-border-status off

tmux select-pane -t mysession:0.0

tmux attach -t mysession
