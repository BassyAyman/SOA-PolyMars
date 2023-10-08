#!/bin/bash

set -f
tmux kill-session -t mysession 2>/dev/null

# Create a new session
tmux new-session -d -s mysession

# Split the window into three panes
tmux split-window -v
tmux split-window -h
tmux resize-pane -y 0
tmux set -g status off

# Send commands to the first two panes
tmux send-keys -t mysession:0.0 './follow-logs.sh' C-m
tmux send-keys -t mysession:0.1 C-l
tmux send-keys -t mysession:0.1 './follow-metrics.sh' C-m

# Create a detached hidden session for the third pane
tmux new-session -d -s hidden_session
tmux send-keys -t hidden_session:0.0 './scenario.sh' C-m
tmux set -t hidden_session:0.0 pane-border-status off

# Adjust focus to the second pane
tmux select-pane -t mysession:0.1

# Attach to the main session
tmux attach -t mysession
