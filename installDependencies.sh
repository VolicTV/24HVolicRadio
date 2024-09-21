#!/bin/bash

echo "Installing dependencies for 24 Hour Volic Radio project..."

# Function to check if a command exists
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Update package list
sudo apt update

# Install Node.js and npm
if ! command_exists node; then
    echo "Installing Node.js and npm..."
    curl -fsSL https://deb.nodesource.com/setup_14.x | sudo -E bash -
    sudo apt-get install -y nodejs
else
    echo "Node.js is already installed."
fi

# Install Java 17
if ! command_exists java || ! java -version 2>&1 | grep -q "version \"17"; then
    echo "Installing Java 17..."
    sudo apt-get install -y openjdk-17-jdk
else
    echo "Java 17 is already installed."
fi

# Install Gradle
if ! command_exists gradle; then
    echo "Installing Gradle..."
    sudo apt-get install -y gradle
else
    echo "Gradle is already installed."
fi

# Install Azure CLI
if ! command_exists az; then
    echo "Installing Azure CLI..."
    curl -sL https://aka.ms/InstallAzureCLIDeb | sudo bash
else
    echo "Azure CLI is already installed."
fi

# Install Azure Functions Core Tools
if ! command_exists func; then
    echo "Installing Azure Functions Core Tools..."
    npm install -g azure-functions-core-tools@4
else
    echo "Azure Functions Core Tools is already installed."
fi

# Install Azurite
if ! command_exists azurite; then
    echo "Installing Azurite..."
    npm install -g azurite
else
    echo "Azurite is already installed."
fi

# Install yt-dlp
if ! command_exists yt-dlp; then
    echo "Installing yt-dlp..."
    sudo curl -L https://github.com/yt-dlp/yt-dlp/releases/latest/download/yt-dlp -o /usr/local/bin/yt-dlp
    sudo chmod a+rx /usr/local/bin/yt-dlp
else
    echo "yt-dlp is already installed."
fi

# Install ffmpeg
if ! command_exists ffmpeg; then
    echo "Installing ffmpeg..."
    sudo apt-get install -y ffmpeg
else
    echo "ffmpeg is already installed."
fi

echo "All dependencies have been installed or were already present."
echo "Please restart your terminal to ensure all changes take effect."