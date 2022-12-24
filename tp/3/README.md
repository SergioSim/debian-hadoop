# TP 3 - Apache Spark

(Updated version from the original author: Benjamin Renaut)

## Getting started

1. Start the Virtual Machine and make sure Hadoop is running
   (we will read the input data from HDFS).
   See [TP1#getting_started](../1/README.md#getting-started).
2. In the Virtual Machine, navigate to the `/vagrant` folder and launch a Jupyter
   Notebook:
   ```bash
   cd /vagrant
   jupyter notebook --ip=0.0.0.0
   ``` 
   The commands output should give you a link similar to:
   ```
   http://127.0.0.1:8888/?token=a9193773ff2cc64bbacfc6df94cd1005c965d09cff17be793
   ```
3. Open the Jupyter Notebook link in a browser on the host machine.
   (The port `8888` is configured to be forwarded to the host machine in the `Vagrant`
   file)
   You should see the Jupyter Notebook start page listing files & directories at the
   vagrant project root directory `debian-hadoop`.

## TP 3 - Anagrams (Demo)

In this exercise we reiterate over the previous Anagrams example from TP1.
We run a local Spark program to solve the exercise.

Once you have completed the [Getting Started](#getting-started) section, open the
`tp/3/Anagrams.ipynb` Notebook file from the Jupyter Notebook page in your browser.

The next steps are described directly in the Notebook.

For a quick introduction on Jupyter Notebooks you could have a look at
[Jupyter Notebook: An Introduction by Mike Driscoll](https://realpython.com/jupyter-notebook-introduction/#creating-a-notebook)
and [Jupyter Notebook CheatCheet by DataCamp.com](https://cdn-images-1.medium.com/max/2000/1*_nFAOrPMxYwE7cBt-ryqZA.png)


## TP 3 - Common friends

It's now up to you to implement a Spark program to solve the common friends problem
presented during the first course.

The input data is `friends.txt`.

Remember that each line describes a social network user followed by his list
of friends; your objective is to pinpoint all common friends in the social
network (for all possible couples of distinct users).

## TP 3 - Graph Breadth-first search

