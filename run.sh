#!/bin/bash

tmux kill-session -t mysession 2>/dev/null

tmux new-session -d -s mysession

# Split top pane horizontally to have log_displayIgor.sh on the left and log_webcaster.sh on the right
tmux send-keys -t mysession:0.0 './scripts/log_displayIgor.sh' C-m
tmux split-window -h
tmux send-keys -t mysession:0.1 './scripts/log_webcaster.sh' C-m

# Create a new window pane at the bottom for follow-metrics.sh
tmux select-pane -t mysession:0.0 # Select the first pane to make sure the split is full width
tmux split-window -v
tmux resize-pane -t mysession:0.2 -y 1 # Make the pane one line in height
tmux send-keys -t mysession:0.2 './scripts/follow-metrics.sh' C-m

# Create a new window pane at the very bottom for log_astronaute.sh
tmux select-pane -t mysession:0.2 # Select the pane above to ensure the split is full width
tmux split-window -v
tmux resize-pane -t mysession:0.3 -y 1 # Make the pane one line in height
tmux send-keys -t mysession:0.3 './scripts/log_astronaute.sh' C-m

# Start the hidden session
tmux new-session -d -s hidden_session
tmux send-keys -t hidden_session:0.0 './scripts/scenario.sh' C-m
tmux set -t hidden_session:0.0 pane-border-status off

tmux select-pane -t mysession:0.0 # Focus on Igor logs by default
tmux attach -t mysession
