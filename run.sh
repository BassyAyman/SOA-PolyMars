#!/bin/bash

tmux kill-session -t mysession 2>/dev/null

tmux new-session -d -s mysession

# Top left pane for log_display_igor.sh
tmux send-keys -t mysession './scripts/log_displayIgor.sh' C-m

# Split top pane horizontally for log_webcaster.sh
tmux split-window -h
tmux send-keys -t mysession './scripts/log_webcaster.sh' C-m

# Select the first pane to split the bottom part
tmux select-pane -t mysession:0.0

# Create a new pane at the bottom for follow-metrics.sh
tmux split-window -v
tmux resize-pane -t mysession:0.2 -y 1 # Resize to one line height
tmux send-keys -t mysession:0.2 './scripts/follow-metrics.sh' C-m

# Create another pane at the very bottom for log_astronaute.sh
# This will be the last pane at index 0.3
tmux split-window -v
tmux resize-pane -t mysession:0.3 -y 1 # Resize to one line height
tmux send-keys -t mysession:0.3 './scripts/log_astronaute.sh' C-m

# Start the hidden session
tmux new-session -d -s hidden_session
tmux send-keys -t hidden_session './scripts/scenario.sh' C-m
tmux set -t hidden_session pane-border-status off

tmux select-pane -t mysession:0.0 # Focus on Igor logs by default
tmux attach -t mysession
