#!/bin/bash

echo "Starting the demo..."
sleep 2
echo "Okay let's go..."

set -f
tmux kill-session -t mysession 2>/dev/null

tmux new-session -d -s mysession

tmux split-window -v
tmux resize-pane -y 0
tmux set -g status off

tmux send-keys -t mysession:0.0 './scripts/log_displayIgor.sh' C-m
tmux send-keys -t mysession:0.1 C-l
tmux send-keys -t mysession:0.1 './scripts/follow-metrics.sh' C-m

tmux new-session -d -s hidden_session
tmux send-keys -t hidden_session:0.0 './scripts/scenario.sh' C-m
tmux set -t hidden_session:0.0 pane-border-status off

tmux select-pane -t mysession:0.0

tmux attach -t mysession
