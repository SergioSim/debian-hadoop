# Debian-Hadoop

An Debian vagrant box with Hadoop

## Prerequisites

- install [virtualbox 6.1](https://www.virtualbox.org/wiki/Download_Old_Builds_6_1)
  with extension pack (version 7 is not yet fully supported)
- install [vagrant](https://www.vagrantup.com/)

## Getting started

Install & start the vagrant box:

```bash
$ vagrant up
```

Connect to the vagrant box (VM):

```bash
$ vagrant ssh
```

## Useful vagrant commands

Check the vagrant box (VM) status:

```bash
$ vagrant status
```
> You can also check the status of all vagrant boxes you have installed with `vagrant global-status`.

Power-off the vagrant box:

```bash
$ vagrant halt
```

Remove the vagrant box from disk:

```bash
$ vagrant destroy
```

Start jupyer notebook:

```bash
$ jupyter notebook --ip=0.0.0.0
```

## Health check

Once you have installed, started and connected to the vagrant box.

Check if Hadoop processes (HDFS & YARN) are running:

```bash
vagrant@bullseye:~$ jps
```

The command should output something like:

```
4416 DataNode
4978 NodeManager
4580 SecondaryNameNode
5351 Jps
4873 ResourceManager
4287 NameNode
```

> If no Hadoop processes show up, try to start Hadoop:

```bash
vagrant@bullseye:~$ start-dfs.sh
vagrant@bullseye:~$ start-yarn.sh
```

## Troubleshooting

Symptom: the `vagrant up` command succeeded but Hadoop processes didn't start.

> If you are running for the first time:

Try to reinstall the vagrant box:

```bash
vagrant up --provision
```

> Else, if it was working previously - try to start Hadoop.

## Updating configuration files

To apply changes done in `config/vagrant` - run the `prerequisites` provisioner:

```bash
vagrant provision --provision-with prerequisites
```

To apply changes done in `config/hadoop` - run the `install_hadoop` provisioner:

```bash
vagrant provision --provision-with install_hadoop
```

> Warning: this will format HDFS (removing all files on HDFS)
