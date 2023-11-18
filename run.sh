#!/bin/bash

tmux kill-session -t mysession 2>/dev/null

tmux new-session -d -s mysession

# Top-left pane for log_displayIgor.sh
tmux send-keys -t mysession:0.0 './scripts/log_displayIgor.sh' C-m

# Split the top pane horizontally for log_webcaster.sh
tmux split-window -h
tmux send-keys -t mysession:0.1 './scripts/log_webcaster.sh' C-m

# Move back to the first pane to split for the bottom panes
tmux select-pane -t mysession:0.0

# Create the first bottom pane for follow-metrics.sh, spanning the entire width
tmux split-window -v
tmux resize-pane -t mysession:0.2 -y 1 # Resize to make it one line in height
tmux send-keys -t mysession:0.2 './scripts/follow-metrics.sh' C-m

# Create the second bottom pane for log_astronaute.sh, spanning the entire width
tmux split-window -v
tmux resize-pane -t mysession:0.3 -y 1 # Resize to make it one line in height
tmux send-keys -t mysession:0.3 './scripts/log_astronaute.sh' C-m

# Adjust the layout to ensure the bottom panes are one line in height each
tmux select-layout -t mysession main-horizontal

# Start the hidden session
tmux new-session -d -s hidden_session
tmux send-keys -t hidden_session:0.0 './scripts/scenario.sh' C-m
tmux set -t hidden_session:0.0 pane-border-status off

tmux select-pane -t mysession:0.0

tmux attach -t mysession
