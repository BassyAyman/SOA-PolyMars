#!/bin/bash

tmux kill-session -t mysession 2>/dev/null

tmux new-session -d -s mysession

# Split the top half horizontally
tmux split-window -h

# Start log_displayIgor.sh in the left pane (pane 0)
tmux send-keys -t 0 './scripts/log_displayIgor.sh' C-m

# Start log_webcaster.sh in the right pane (pane 1)
tmux send-keys -t 1 './scripts/log_webcaster.sh' C-m

# Split the whole window horizontally to create a space for follow-metrics.sh
tmux select-pane -t 0
tmux split-window -v

# Resize the bottom pane to create a space for log_astronaute.sh
tmux select-pane -t 2
tmux resize-pane -D 1

# Start follow-metrics.sh in the new bottom pane (pane 2)
tmux send-keys -t 2 './scripts/follow-metrics.sh' C-m

# Split the bottom pane horizontally to create a space for log_astronaute.sh
tmux split-window -v

# Resize the bottom pane to be one line high
tmux resize-pane -D 1

# Start log_astronaute.sh in the new bottom pane (pane 3)
tmux send-keys -t 3 './scripts/log_astronaute.sh' C-m

# Start the hidden session
tmux new-session -d -s hidden_session
tmux send-keys -t hidden_session './scripts/scenario.sh' C-m
tmux set -t hidden_session pane-border-status off

# Select the top left pane to start
tmux select-pane -t 0

tmux attach -t mysession
