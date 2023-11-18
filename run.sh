#!/bin/bash

tmux kill-session -t mysession 2>/dev/null

tmux new-session -d -s mysession

# Create top left pane for log_display_igor.sh
tmux send-keys -t mysession:0.0 './scripts/log_displayIgor.sh' C-m

# Split top pane horizontally for log_webcaster.sh
tmux split-window -h
tmux send-keys -t mysession:0.1 './scripts/log_webcaster.sh' C-m

# Select the first pane to split for the bottom panes
tmux select-pane -t mysession:0.0

# Create the first bottom pane for follow-metrics.sh
tmux split-window -v
tmux select-layout even-vertical # This will even out the vertical splits, making room for the last split
tmux send-keys -t mysession:0.2 './scripts/follow-metrics.sh' C-m

# Resize the pane to the smallest possible, effectively making it one line
tmux resize-pane -t mysession:0.2 -y 1

# Split the bottom pane horizontally for log_astronaute.sh
tmux split-window -h
tmux send-keys -t mysession:0.3 './scripts/log_astronaute.sh' C-m

# Resize the last pane to the smallest possible, effectively making it one line
tmux resize-pane -t mysession:0.3 -y 1

# Start the hidden session
tmux new-session -d -s hidden_session
tmux send-keys -t hidden_session:0.0 './scripts/scenario.sh' C-m
tmux set -t hidden_session:0.0 pane-border-status off

tmux select-pane -t mysession:0.0

tmux attach -t mysession
