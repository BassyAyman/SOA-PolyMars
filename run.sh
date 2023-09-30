#!/bin/bash

set -f
tmux kill-session -t mysession 2>/dev/null

tmux new-session -d -s mysession

tmux split-window -v
tmux split-window -h
tmux resize-pane -y 1
tmux set -g mouse off
tmux set -g status off

tmux send-keys -t mysession:0.0 './follow-logs.sh' C-m
tmux send-keys -t mysession:0.1 C-l
tmux send-keys -t mysession:0.1 './follow-metrics.sh' C-m
tmux send-keys -t mysession:0.2 C-l

tmux send-keys -t mysession:0.2 'sleep 10' C-m
tmux send-keys -t mysession:0.2 'echo "Test of rocket launch: call Command Service to launch rocket"' C-m
tmux send-keys -t mysession:0.2 'curl http://localhost:8083/launch' C-m
tmux send-keys -t mysession:0.2 'echo "Wait for 15 seconds to simulate a problem with the rocket..."' C-m
tmux send-keys -t mysession:0.2 'sleep 15' C-m
tmux send-keys -t mysession:0.2 'echo "Simulating a problem with the rocket..."' C-m
tmux send-keys -t mysession:0.2 'curl -X PUT http://localhost:8082/mockProblem' C-m

tmux attach -t mysession
