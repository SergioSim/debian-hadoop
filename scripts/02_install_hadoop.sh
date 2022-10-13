#!/bin/bash

# Install Hadoop

# Abort on any error
set -Eeuo pipefail

# Utility functions
function __log() {
    echo -e "[INSTALL HADOOP] $*"
}

__log "HADOOP_VERSION is set to: $HADOOP_VERSION"
__log "HADOOP_DOWNLOAD_URL is set to: $HADOOP_DOWNLOAD_URL"

HADOOP_ARCHIVE="hadoop-$HADOOP_VERSION.tar.gz"
HADOOP_DIR="hadoop-$HADOOP_VERSION"

cd /usr/local
if [[ ! -f $HADOOP_ARCHIVE ]]; then
    __log "Downloading Hadoop installation archive"
    wget "$HADOOP_DOWNLOAD_URL"
fi
if [[ ! -d $HADOOP_DIR ]]; then
    __log "Extracting Hadoop installation archive"
    tar zxvf "$HADOOP_ARCHIVE"
    rm -f hadoop
    ln -sf "$HADOOP_DIR" hadoop
    rm -f hadoop/etc/hadoop/*.cmd hadoop/sbin/*.cmd hadoop/bin/*.cmd
fi

__log "Setting up passphraseless ssh"
rm -f /home/vagrant/.ssh/id_rsa /home/vagrant/.ssh/id_rsa.pub
ssh-keygen -t rsa -P '' -f /home/vagrant/.ssh/id_rsa
cat /home/vagrant/.ssh/id_rsa.pub >> /home/vagrant/.ssh/authorized_keys
chmod 0600 /home/vagrant/.ssh/authorized_keys
ssh-keyscan -t ecdsa-sha2-nistp256 localhost > /home/vagrant/.ssh/known_hosts
chown vagrant:vagrant /home/vagrant/.ssh/*

__log "Updating Hadoop configuration files"
cp /vagrant/config/hadoop/* /usr/local/hadoop/etc/hadoop

__log "Giving vagrant user ownership over Hadoop"
chown -R vagrant:vagrant "/usr/local/$HADOOP_DIR"

if [[ -n "$(pgrep -f 'ResourceManager|NodeManager' || true)" ]]; then
    __log "Stopping YARN"
    su -l vagrant -c "stop-yarn.sh"
fi
if [[ -n "$(pgrep -f 'NameNode|DataNode|SecondaryNameNode' || true)" ]]; then
    __log "Stopping HDFS"
    su -l vagrant -c "stop-dfs.sh"
fi

__log "Removing Hadoop data directory"
rm -rf /tmp/hadoop-vagrant
rm -rf /var/hadoop

__log "Creating Hadoopo data directory"
mkdir /var/hadoop
chown -R vagrant /var/hadoop

__log "Formatting the Hadoop filesystem"
su -l vagrant -c "yes | hdfs namenode -format"

__log "Starting HDFS"
su -l vagrant -c "start-dfs.sh"

__log "Waiting for the NameNode to exit safemode 0/30"
SAFEMODE="ON"
MAX_WAIT_TIME=30
for i in $( seq 1 $MAX_WAIT_TIME )
do
    if [[ "$(su -l vagrant -c 'hdfs dfsadmin -safemode get')" = "Safe mode is OFF" ]]; then
        SAFEMODE="OFF"
        break
    fi
    __log "Waiting for NameNode to exit safemode ${i}/${MAX_WAIT_TIME}"
    sleep 1
done

if [[ $SAFEMODE = "ON" ]]; then
    __log "[CRITICAL ERROR] NameNode did not exit safemode"
    exit 1
fi

__log "Creating vagrant user HDFS home directory"
su -l vagrant -c "hadoop fs -mkdir -p /user/vagrant"

__log "Starting YARN"
su -l vagrant -c "start-yarn.sh"
