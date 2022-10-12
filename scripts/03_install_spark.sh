#!/bin/bash

# Install Spark

# Abort on any error
set -Eeuo pipefail

# Utility functions
function __log() {
    echo -e "[INSTALL SPARK] $*"
}

__log "SPARK_VERSION is set to: $SPARK_VERSION"
__log "SPARK_DOWNLOAD_URL is set to: $SPARK_DOWNLOAD_URL"

SPARK_ARCHIVE="spark-$SPARK_VERSION.tgz"
SPARK_DIR="spark-$SPARK_VERSION"

# Download Spark (if it doesn't exist; 285M)
cd /usr/local
if [[ ! -f $SPARK_ARCHIVE ]]; then
    __log "Downloading Spark installation archive"
    wget "$SPARK_DOWNLOAD_URL"
fi
if [[ ! -d $SPARK_DIR ]]; then
    __log "Extracting Spark installation archive"
    tar zxvf "$SPARK_ARCHIVE"
    rm -f spark
    ln -sf "$SPARK_DIR" spark
fi

__log "Updating Spark configuration files"
cp /vagrant/config/spark/* /usr/local/spark/conf

__log "Giving vagrant user ownership over Spark"
chown -R vagrant:vagrant "/usr/local/$SPARK_DIR"
