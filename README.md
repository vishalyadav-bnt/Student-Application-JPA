# Complete WSL2 and Docker Setup Guide

## Table of Contents
1. [Prerequisites](#prerequisites)
2. [Installing WSL2](#installing-wsl2)
3. [Installing Ubuntu/Other Distros](#installing-ubuntuother-distros)
4. [Setting Up Username and Password](#setting-up-username-and-password)
5. [Verifying Installation](#verifying-installation)
6. [Docker Desktop Integration](#docker-desktop-integration)
7. [Configuring Docker Permissions](#configuring-docker-permissions)
8. [GitHub Setup](#github-setup)
9. [Installing Development Tool](#installing-development-tool)
10. [Building Docker Images](#building-docker-images)
11. [EssentialCommands](#essentialcommands)

---

## Prerequisites

### System Requirements
- Windows 10 version 2004 and higher (Build 19041 and higher) or Windows 11
- Virtualization enabled in BIOS/UEFI
- At least 4GB RAM (8GB recommended)

### Check Windows Version
```powershell
# Run in PowerShell
winver
```

---

## Installing WSL2

### Method 1: One Command Install (Windows 10/11 - Recommended)
```powershell
# Run PowerShell as Administrator
wsl --install
```

This command will:
- Enable WSL feature
- Enable Virtual Machine Platform
- Install WSL2 Linux kernel
- Set WSL2 as default version (if not showing 2 then use this command to set 2)
```
wsl --set-default-version 2
```
- Install Ubuntu (default distro)

### Method 2: Manual Install (If Method 1 fails)

#### Step 1: Enable WSL Feature
```powershell
# Run PowerShell as Administrator
dism.exe /online /enable-feature /featurename:Microsoft-Windows-Subsystem-Linux /all /norestart
```

#### Step 2: Enable Virtual Machine Platform
```powershell
dism.exe /online /enable-feature /featurename:VirtualMachinePlatform /all /norestart
```

#### Step 3: Restart Computer
```powershell
Restart-Computer
```

#### Step 4: Download and Install WSL2 Kernel Update
1. Download from: https://wslstorestorage.blob.core.windows.net/wslblob/wsl_update_x64.msi
2. Run the installer

#### Step 5: Set WSL2 as Default
```powershell
wsl --set-default-version 2
```

---

## Installing Ubuntu/Other Distros

### Check Available Distros
```powershell
wsl --list --online
# or
wsl -l -o
```

### Install Ubuntu (Recommended)
```powershell
wsl --install -d Ubuntu
```

### Install Other Distros
```powershell
# Install specific versions
wsl --install -d Ubuntu-20.04
wsl --install -d Ubuntu-22.04
wsl --install -d Debian
wsl --install -d openSUSE-Leap-15.4
```

### Alternative: Install from Microsoft Store
1. Open Microsoft Store
2. Search for "Ubuntu" or your preferred distro
3. Click "Get" or "Install"

---

## Setting Up Username and Password

### First Launch Setup
1. **Launch Ubuntu** (search "Ubuntu" in Start Menu)
2. **Wait for installation** to complete (first launch takes time)
3. **Create username:**
   ```bash
   # Enter a username (lowercase, no spaces)
   Enter new UNIX username: yourUsername
   ```
4. **Create password:**
   ```bash
   # Enter password (won't show while typing)
   Enter new UNIX password: 
   Retype new UNIX password:
   ```

### Change Password Later (if needed)
```bash
# Inside Ubuntu terminal
passwd
```

### Change Username Later (if needed)
```bash
# Create new user
sudo adduser newusername

# Add to sudo group
sudo usermod -aG sudo newusername

# Set as default (from Windows PowerShell)
ubuntu config --default-user newusername
```

---

## Verifying Installation

### Check WSL Version
```powershell
# From Windows PowerShell
wsl --list --verbose
# or
wsl -l -v
```

**Expected Output:**
```
  NAME                   STATE           VERSION
* Ubuntu                 Running         2
  docker-desktop         Running         2
  docker-desktop-data    Running         2
```

### Check Ubuntu is Running
```powershell
# Check status
wsl --list --running

# Start Ubuntu if stopped
wsl -d Ubuntu

# Check Ubuntu version from inside Ubuntu
lsb_release -a
```

### System Update Commands (Ubuntu)
```bash
sudo apt update             # Update package lists
sudo apt upgrade -y         # Upgrade packages
sudo apt autoremove -y      # Remove unused packages
```
### Test File System Access
```bash
# From Ubuntu terminal
# Access Windows C: drive
ls /mnt/c/

# Access Windows D: drive
ls /mnt/d/

# Check current directory
pwd
```

---

## Docker Desktop Integration

### Prerequisites
- Docker Desktop for Windows installed
- WSL2 properly installed and running

### Enable Docker Integration

#### Step 1: Open Docker Desktop Settings
1. Right-click Docker icon in system tray
2. Click "Settings"

#### Step 2: Enable WSL2 Backend
1. Go to **General** tab
2. Check ✅ **"Use the WSL 2 based engine"**
3. Click **"Apply & Restart"**

#### Step 3: Enable WSL Integration
1. Go to **Resources** → **WSL Integration**
2. Enable ✅ **"Enable integration with my default WSL distro"**
3. Enable ✅ **"Ubuntu"** (and any other distros you want)
4. Click **"Apply & Restart"**

### Verify Docker Integration
```bash
# From Ubuntu terminal
docker --version
docker ps
docker info
```

**Expected Output:**
```bash
ubuntu@VISHALYADAV:~$ docker --version
Docker version 24.0.x, build xxxxx

ubuntu@VISHALYADAV:~$ docker ps
CONTAINER ID   IMAGE     COMMAND   CREATED   STATUS    PORTS     NAMES

ubuntu@VISHALYADAV:~$ docker info
# Should show Docker information without errors
```

---

## Configuring Docker Permissions

### Add User to Docker Group (if needed)
```bash
# Check current groups
groups

# Add user to docker group (usually not needed with Docker Desktop)
sudo usermod -aG docker $USER

# Apply group changes
newgrp docker

# Verify
groups
```

### Test Docker Permissions
```bash
# Test without sudo
docker run hello-world
```

**Expected Output:**
```
Hello from Docker!
This message shows that your installation appears to be working correctly.
```

---
## GitHub Setup
### Install Git (if not already installed)

bash

```bash
# Git should be installed from previous step, but verify
git --version

# If not installed
sudo apt install -y git
```
---
### Configure Git User Information

```bash
# Set your name (use your actual name)
git config --global user.name "Your Full Name"

# Set your email (use your GitHub email)
git config --global user.email "your.email@example.com"

# Verify configuration
git config --global --list
```
---
### Method 1: HTTPS with Personal Access Token (Recommended)

#### Step 1: Create Personal Access Token

1. Go to GitHub.com → Settings → Developer settings → Personal access tokens → Tokens (classic)
2. Click "Generate new token (classic)"
3. Give it a name (e.g., "WSL2 Ubuntu")
4. Select scopes: `repo`, `workflow`, `write:packages`, `delete:packages`
5. Click "Generate token"
6. **Copy the token immediately** (you won't see it again)

#### Step 2: Configure Git Credential Helper

```bash
# Configure credential helper to store credentials
git config --global credential.helper store

# Configure credential helper timeout (optional)
git config --global credential.helper 'cache --timeout=3600'
```

#### Step 3: Test GitHub Connection

```bash
# Clone a repository (it will prompt for username and token)
git clone https://github.com/yourusername/your-repo.git

# When prompted:
# Username: your-github-username
# Password: paste-your-personal-access-token
```
---
## Installing Development Tools (JDK, Node.js, Angular CLI)
##### 📌 To download any application in Ubuntu use:

`sudo apt install <package-name>`
##### 🔹 1. Update system first

`sudo apt update && sudo apt upgrade -y`
##### 🔹 2. Install Node.js & npm
(Node 20 LTS is stable for Angular)

`curl -fsSL https://deb.nodesource.com/setup_20.x | sudo -E bash - sudo apt install -y nodejs`

Check versions:

`node -v npm -v`
##### 🔹 3. Install Angular CLI (globally via npm)

`sudo npm install -g @angular/cli`

Check version:

`ng version`
### 🔹 4. Install JDK 21

`sudo apt install -y openjdk-21-jdk`

Check version:

`java -version`
##### 🔹 5. (Optional) Set JAVA_HOME & update PATH

Add these lines to your `~/.bashrc` or `~/.zshrc` file:

`export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64 export PATH=$JAVA_HOME/bin:$PATH`

Then reload:

`source ~/.bashrc`

Check:

`echo $JAVA_HOME`

---
#### Setting Permissions for Maven and Gradle

```
chmod +x mvnw         # Make Maven wrapper executable

chmod +x gradlew      # Make Gradle wrapper executable
```

---
## Building Docker Images (Sample Example)

### Basic Commands

#### Navigate to Project Directory
```bash
# Navigate to Windows D: drive project
cd /mnt/d/MyProject

# Or C: drive
cd /mnt/c/Users/YourName/MyProject

# List files
ls -la
```

#### Build Docker Image
```bash
# Basic build
docker build .

# Build with tag
docker build -t myapp:latest .

# Build with specific Dockerfile
docker build -f Dockerfile.prod .

# Build with build arguments
docker build --build-arg NODE_VERSION=18 .
```

#### Run Docker Container
```bash
# Run built image
docker run myapp:latest

# Run with port mapping
docker run -p 3000:3000 myapp:latest

# Run in background
docker run -d myapp:latest

# Run with volume mounting
docker run -v /mnt/d/MyProject:/app myapp:latest
```

---
## EssentialCommands
```bash
# WSL Commands (PowerShell)
wsl -l -v                    # List distros
wsl -d Ubuntu               # Start Ubuntu
wsl --shutdown              # Stop all WSL

# Navigation (Ubuntu)
cd /mnt/c/                  # Go to C: drive
cd /mnt/d/MyProject         # Go to D: drive project
ls -la                      # List files
pwd                         # Current directory

# System Update Commands (Ubuntu)
sudo apt update             # Update package lists
sudo apt upgrade -y         # Upgrade packages
sudo apt autoremove -y      # Remove unused packages

# Git Commands (Ubuntu)
git config --global user.name "Name"     # Set git username
git config --global user.email "email"   # Set git email
git clone https://github.com/user/repo   # Clone repository
git status                              # Check status
git add .                               # Stage changes
git commit -m "message"                 # Commit changes
git push                                # Push to remote

# Development Tools (Ubuntu)
java -version                           # Check Java version
node --version                          # Check Node version
npm --version                           # Check npm version
ng version                             # Check Angular CLI version
mvn --version                          # Check Maven version
gradle --version                       # Check Gradle version
./mvnw clean install                   # Build Maven project
./gradlew build                        # Build Gradle project
chmod +x mvnw                          # Make Maven wrapper executable
chmod +x gradlew                       # Make Gradle wrapper executable

# Docker Commands (Ubuntu)
docker --version            # Check Docker
docker build .              # Build image
docker build -t name .      # Build with tag
docker images               # List images
docker ps                   # Running containers
docker run image_name       # Run container
docker system prune         # Clean up unused resources

```

---

This guide covers everything you need to set up WSL2 with Ubuntu, update the system, configure GitHub credentials, and integrate with Docker for building images from your Windows-stored projects!
