# Debian-Hadoop

An Debian vagrant box with Hadoop

## Prerequisites

- Install a git client (for example [git SCM](https://git-scm.com/download/win)) and clone this repository: `git clone https://github.com/SergioSim/debian-hadoop.git`
- Install [virtualbox 7.0.10](https://www.virtualbox.org/wiki/Downloads)
  with the [extension pack 7.0.10](https://download.virtualbox.org/virtualbox/7.0.10/Oracle_VM_VirtualBox_Extension_Pack-7.0.10.vbox-extpack)
- Install [vagrant](https://www.vagrantup.com/)

## Getting started

**Install & start the vagrant box using VirtualBox:**

Navigate to the debian-hadoop directory and run the following command:

```bash
$ vagrant up
```

**Install & start the vagrant box using Docker:**

```bash
$ vagrant up --provider docker
```

**Connect to the vagrant box (VM):**

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

Start jupyter notebook:

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

**Symptom: the `vagrant up` command succeeded but Hadoop processes didn't start.**

> If you are running for the first time:

Try to reinstall the vagrant box:

```bash
vagrant up --provision
```

> Else, if it was working previously - try to start Hadoop.

**Symptom: the `vagrant ssh` command fails with `Permission denied (publickey)`.**

1. Get the location of your vagrant ssh private key: `vagrant ssh-config`
   Then copy the path of the IdentityFile 
2. Run the following command instead of `vagrant ssh` to connect to the VM:
   ```bash
   ssh -i path/to/identity/file/private_key -p 2222 vagrant@127.0.0.1
   ```

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
