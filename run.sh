#!/bin/bash

set -f
tmux kill-session -t mysession 2>/dev/null

tmux new-session -d -s mysession

tmux split-window -v
tmux split-window -h
tmux resize-pane -y 1
tmux set -g status off

tmux send-keys -t mysession:0.0 './follow-logs.sh' C-m
tmux send-keys -t mysession:0.1 C-l
tmux send-keys -t mysession:0.1 './follow-metrics.sh' C-m
tmux send-keys -t mysession:0.2 C-l
tmux send-keys -t mysession:0.2 './scenario.sh' C-m

tmux attach -t mysession
