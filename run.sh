#!/bin/bash

#if [ -e "follow-logs.sh" ]; then
#  chmod +x follow-logs.sh
#  if command -v gnome-terminal > /dev/null; then
#    gnome-terminal -- bash -c "./follow-logs.sh; exec bash"
#  elif command -v xterm > /dev/null; then
#    xterm -e "./follow-logs.sh; bash"
#  elif command -v konsole > /dev/null; then
#    konsole -e "./follow-logs.sh; bash"
#  elif command -v xfce4-terminal > /dev/null; then
#    xfce4-terminal -e "./follow-logs.sh; bash"
#  # Detect if running in WSL
#  elif grep -q Microsoft /proc/version; then
#    # In WSL, just run the script in the current terminal
#    ./follow-logs.sh
#  # Detect if running in Git Bash
#  elif [ ! -z "$MSYSTEM" ]; then
#    start bash -c "./follow-logs.sh; exec bash"
#  else
#    echo "No known terminal emulator found. Cannot open a new terminal."
#  fi
#else
#  echo "follow-logs.sh not found."
#fi

# if follow-logs.sh exists and is executable
if [ -e "follow-logs.sh" ] && [ -x "follow-logs.sh" ]; then
  tmux send-keys -t mysession:0.0 './follow-logs.sh' Enter
else
  echo "follow-logs.sh not found or not executable."
fi

# if follow-metrics.sh exists and is executable
if [ -e "follow-metrics.sh" ] && [ -x "follow-metrics.sh" ]; then
  tmux send-keys -t mysession:0.1 './follow-metrics.sh' Enter
  tmux send-keys -t mysession:0.1 'Rocket telemetry: ' Enter
else
  echo "follow-metrics.sh not found or not executable."
fi

################################

echo "Test of rocket launch: call Command Service to launch rocket"
curl http://localhost:8083/launch

################################

tmux new-session -d -s mysession

tmux split-window -v
tmux resize-pane -y 1

tmux send-keys -t mysession:0.0 './follow-logs.sh' Enter
tmux send-keys -t mysession:0.1 './follow-metrics.sh' Enter
tmux send-keys -t mysession:0.1 'Rocket telemetry: ' Enter
tmux set -g status off

tmux set -g mouse off
tmux set -g mouse-select-pane off
tmux set -g mouse-select-window off

tmux attach -t mysession