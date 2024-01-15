#!/bin/bash

# Install Jupyter Notebooks

# Abort on any error
set -Eeuo pipefail

# Utility functions
function __log() {
    echo -e "[INSTALL JUPYTER] $*"
}

__log "Installing pip"
apt-get install -y python3-pip

__log "Installing jupyter notebooks"
su -l vagrant -c "pip3 install notebook pyspark"
