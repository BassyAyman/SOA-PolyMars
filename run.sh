#!/bin/bash

tmux kill-session -t mysession 2>/dev/null

tmux new-session -d -s mysession

# Top left pane for log_display_igor.sh
tmux send-keys -t mysession './scripts/log_displayIgor.sh' C-m

# Split top pane horizontally for log_webcaster.sh
tmux split-window -h
tmux send-keys -t mysession './scripts/log_webcaster.sh' C-m

# Navigate back to the first pane before splitting for bottom panes
tmux select-pane -t mysession:0.0

# Create a new pane for follow-metrics.sh at the bottom
tmux split-window -v -p 20
tmux send-keys -t mysession './scripts/follow-metrics.sh' C-m

# Resize bottom pane to a single line
tmux resize-pane -t mysession:0.2 -y 1

# Create another pane at the very bottom for log_astronaute.sh
# Make sure to select the correct pane to split from, which should be follow-metrics.sh pane
tmux select-pane -t mysession:0.2
tmux split-window -v
tmux send-keys -t mysession './scripts/log_astronaute.sh' C-m

# Resize new bottom pane to a single line
tmux resize-pane -t mysession:0.3 -y 1

# Start the hidden session
tmux new-session -d -s hidden_session
tmux send-keys -t hidden_session './scripts/scenario.sh' C-m
tmux set -t hidden_session pane-border-status off

tmux select-pane -t mysession:0.0 # Focus on Igor logs by default
tmux attach -t mysession
