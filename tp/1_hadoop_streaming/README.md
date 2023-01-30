# TP 1 - WordCount

(Updated version from the original author: Benjamin Renaut)

The goal of this exercise is to get a first hands-on experience in writing and running
a Hadoop MapReduce Streaming application.

## Getting started

1. Make sure you have met the [Prerequisites](../../README.md#prerequisites).
2. Open a terminal in the project root folder (`debian-hadoop`) and start the VM:
   ```{bash}
   vagrant up
   ```
3. Check that the VM's state is `Running`:
   ```bash
   vagrant status
   ```
4. Connect to the machine via SSH:
   ```bash
   vagrant ssh
   ```
5. Check if Hadoop processes are running, see [Health check](../../README.md#health-check).
6. Inspect the HDFS status report by running:
   ```bash
   vagrant@bullseye:~$ hdfs dfsadmin -report
   ```
   The command should show some basic file-system information and statistics about HDFS.

## Executing the word count example

Once your have completed the "Getting started" section, you should move the
example `poem.txt` file to the HDFS filesystem in order for your program to be able to
read it.

```bash
$ hadoop fs -put /vagrant/tp/1_hadoop_streaming/poeme.txt .
```

Then check it is indeed available on HDFS:

```bash
$ hadoop fs -ls
```

> (the `poeme.txt` file should appear)

Next, let's inspect the example word count mapper and reducer implementation in the
`example` directory (`wordcount_map.py` & `wordcount_red.py`).

We will also allow the scripts to be executed with the following command:

```bash
chmod a+x /vagrant/tp/1_hadoop_streaming/example/wordcount_map.py
chmod a+x /vagrant/tp/1_hadoop_streaming/example/wordcount_red.py
```

Then, execute your program:

```bash
$ hadoop jar /usr/local/hadoop/share/hadoop/tools/lib/hadoop-streaming-3.3.4.jar \
    -input poeme.txt \
    -output results \
    -mapper /vagrant/tp/1_hadoop_streaming/example/wordcount_map.py \
    -reducer /vagrant/tp/1_hadoop_streaming/example/wordcount_red.py
```

> Note that we use `results` as the target directory to store our final results on HDFS,
> and if that directory already exists the execution will fail.

Finally, we can check our results on HDFS, for example:

```bash
$ hadoop fs -ls results
$ hadoop fs -cat results/*
```


# TP 1 - Anagrams

Now, it's up to you to write your own Hadoop application.

## Objective

You are provided with a list of common English words (`common_words_en_subset.txt`).
We wish to pinpoint which words are anagrams of one another.

As a reminder, two words are anagrams if their letters are the same but in a
different order (such as « melon » and « lemon », for example).

## Remarks

- Apply the map/reduce methodology as described during the course.
- As yourself: what should my key be ? What is the common factors between anagrams, and how could that help ?
- Do not forget that you will need to copy the input data file to HDFS for
processing.

# TP 1 - Sales analysis

Now, we will work now on a sales record file (`sales_world_10k.csv`).

It is a CSV file. Please check its format; it has many columns. Also note it has a
header line, which may be problematic when loading the file from your Hadoop
implementation.

We are interested in specifically five columns: the sales region (« Region »), the
sales country (« Country »), the type of item bought (« Item Type »), the sales
channel (« Sales channel » : online or offline), and finally the total sale profit
(« Total profit »).

Your program should be able to perform various analysis tasks.

It is **requested** to allow your program to adapt its behavior / pick an
analysis task depending on a command line argument.

## Objective

Your Hadoop Map Reduce application should be able to perform the following tasks:

1. Obtain the total profit for any given world region.
2. Obtain the total profit for any given country.
3. Obtain the total profit for any given item type.
4. For each item type, provide:
   - How many sales were performed online.
   - How many sales were performed offline.
   
   And for each of those quantities, how much the combined total profit for those sales was.
