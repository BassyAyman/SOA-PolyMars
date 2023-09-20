#!/bin/bash

if [ -e "follow-logs.sh" ]; then
  chmod +x follow-logs.sh
  if command -v gnome-terminal > /dev/null; then
    gnome-terminal -- bash -c "./follow-logs.sh; exec bash"
  elif command -v xterm > /dev/null; then
    xterm -e "./follow-logs.sh; bash"
  elif command -v konsole > /dev/null; then
    konsole -e "./follow-logs.sh; bash"
  elif command -v xfce4-terminal > /dev/null; then
    xfce4-terminal -e "./follow-logs.sh; bash"
  # Detect if running in WSL
  elif grep -q Microsoft /proc/version; then
    # In WSL, just run the script in the current terminal
    ./follow-logs.sh
  # Detect if running in Git Bash
  elif [ ! -z "$MSYSTEM" ]; then
    start bash -c "./follow-logs.sh; exec bash"
  else
    echo "No known terminal emulator found. Cannot open a new terminal."
  fi
else
  echo "follow-logs.sh not found."
fi


echo "Test of rocket launch: call Command Service to launch rocket"
curl http://localhost:8083/launch