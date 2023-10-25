#!/bin/bash

# if apt package manager, then install xdotool
if [ -f /etc/arch-release ]; then
    if ! command -v xdotool &> /dev/null; then
        sudo pacman -S xdotool || { echo "xdotool installation failed."; exit 1; }
        echo "xdotool installed."
    else
        echo "xdotool is already installed."
    fi
    if ! command -v wmctrl &> /dev/null; then
        sudo pacman -S wmctrl || { echo "wmctrl installation failed."; exit 1; }
        echo "wmctrl installed."
    else
        echo "wmctrl is already installed."
    fi
else
    if ! command -v xdotool &> /dev/null; then
        sudo apt install -y xdotool || { echo "xdotool installation failed."; exit 1; }
        echo "xdotool installed."
    else
        echo "xdotool is already installed."
    fi
    if ! command -v wmctrl &> /dev/null; then
        sudo apt install -y wmctrl || { echo "wmctrl installation failed."; exit 1; }
        echo "wmctrl installed."
    else
        echo "wmctrl is already installed."
    fi
fi

# Open terminals running the specific scripts
gnome-terminal --title="Webcaster" -- bash -c "./scripts/log_webcaster.sh; exec bash" &
sleep 1
gnome-terminal --title="IGOR_Corporation" -- bash -c "./run.sh; exec bash" &


sleep 2   # Give terminals some time to launch

screen_width=$(xdpyinfo | awk '/dimensions/{print $2}' | awk -Fx '{print $1}')
screen_height=$(xdpyinfo | awk '/dimensions/{print $2}' | awk -Fx '{print $2}')

wmctrl -r "Webcaster" -e 0,0,0,$((screen_width*40/100)),"$screen_height"
wmctrl -r "IGOR_Corporation" -e 0,$((screen_width*40/100)),0,$((screen_width*60/100)),"$screen_height"

# Move the terminals to top
wmctrl -r "Webcaster" -b add,above
wmctrl -r "IGOR_Corporation" -b add,above
