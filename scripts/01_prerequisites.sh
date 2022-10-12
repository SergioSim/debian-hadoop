#!/bin/bash

# Prerequisites

# Abort on any error
set -Eeuo pipefail

# Utility functions
function __log() {
    echo -e "[PREREQUISITES] $*"
}

__log "Updating repository indexes"
apt-get update

__log "Upgrading installed packages"
apt-get upgrade -y

__log "Installing python3 git JDK-11 vim nano"
apt-get install -y python3 git openjdk-11-jdk vim nano

__log "Copying .bash_profile for vagrant user"
cp /vagrant/config/vagrant/.profile /home/vagrant
