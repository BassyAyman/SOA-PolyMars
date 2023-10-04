#!/bin/bash

set -f
tmux kill-session -t mysession 2>/dev/null

# Create a new session
tmux new-session -d -s mysession

# Split the window into three panes
tmux split-window -v
tmux split-window -h
tmux resize-pane -y 1
tmux set -g status off

# Send commands to the first two panes
tmux send-keys -t mysession:0.0 './follow-logs.sh' C-m
tmux send-keys -t mysession:0.1 C-l
tmux send-keys -t mysession:0.1 './follow-metrics.sh' C-m

# Create a new hidden session and move the third pane to it
tmux new-session -d -s hidden_session
tmux set -t mysession:0.2 pane-border-status off
tmux move-pane -s mysession:0.2 -t hidden_session:0.0



# Attach to the main session
tmux attach -t mysession
