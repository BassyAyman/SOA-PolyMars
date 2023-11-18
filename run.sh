#!/bin/bash

tmux kill-session -t mysession 2>/dev/null

tmux new-session -d -s mysession

# Create top left pane for log_display_igor.sh
tmux send-keys -t mysession:0.0 './scripts/log_displayIgor.sh' C-m

# Split top pane horizontally for log_webcaster.sh
tmux split-window -h
tmux send-keys -t mysession:0.1 './scripts/log_webcaster.sh' C-m

# Create the first bottom pane for follow-metrics.sh
tmux select-pane -t mysession:0.0 # Go back to the first pane
tmux split-window -v
tmux resize-pane -t mysession:0.2 -y 1 # Resize to make it one line in height
tmux send-keys -t mysession:0.2 './scripts/follow-metrics.sh' C-m

# Create the second bottom pane for log_astronaute.sh
tmux select-pane -t mysession:0.2 # Select the pane above to split it again
tmux split-window -v
tmux resize-pane -t mysession:0.3 -y 1 # Resize to make it one line in height
tmux send-keys -t mysession:0.3 './scripts/log_astronaute.sh' C-m

# Start the hidden session
tmux new-session -d -s hidden_session
tmux send-keys -t hidden_session:0.0 './scripts/scenario.sh' C-m
tmux set -t hidden_session:0.0 pane-border-status off

# Attach to the main session
tmux select-pane -t mysession:0.0
tmux attach -t mysession
