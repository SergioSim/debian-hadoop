# TP 1 - WordCount

(Updated version from the original author: Benjamin Renaut)

The goal of this exercise is to get a first hands-on experience in compiling and running
a Hadoop MapReduce application.

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

## Compiling

To run your Hadoop application, you will first need to generate a jar file, and
then copy it to the virtual machine to execute it.

For the development itself and to generate the jar, you have several options:

1. Manual compilation (`vim or nano` to edit + `javac` to compile + `jar` to package)
2. Using a Java IDE (`IntelliJ`, `Eclipse` or other IDE with `Maven` or `Gradle`)

### Compiling using a IDE

Import one of the following example projects (depending on your Java IDE preference):

- To use `example/wordcount_maven_eclipse`, see [importing with Eclipse](https://www.admfactory.com/how-to-import-and-export-java-projects-in-eclipse/).
- To use `example/wordcount_maven_intellij`, see [importing with Intellij](https://www.jetbrains.com/help/idea/import-project-or-module-wizard.html#open-project).
- To use `example/wordcount_gradle_intellij`, see [importing with Intellij](https://www.jetbrains.com/help/idea/import-project-or-module-wizard.html#open-project).

Then compile the project using your IDE and move the resulting `wordcount-0.0.1.jar` file
to the VM by moving it under the vagrant project root directory `debian-hadoop`.

### Compiling manually

1. Navigate to the example directory:
   ```bash
   vagrant@bullseye:~$ cd /vagrant/tp/1/example
   ```
2. Compile using `javac`:
   ```bash
   $ javac WordCount.java
   ```
3. Create the classpath hierarchy and move the class files into it:
   ```bash
   $ mkdir -p org/mbds
   $ mv WordCount*.class org/mbds
   ```
4. Generate the jar and clean up:
   ```bash
   $ jar -cvf wordcount-0.0.1.jar -C . org
   $ rm -rf org
   ```

## Executing

Once your jar file has been moved to the VM, you should move the example `poem.txt` file
to the HDFS filesystem in order for your program to be able to read it.

```bash
$ hadoop fs -put /vagrant/tp/1/poeme.txt .
```

Then check it is indeed available on HDFS:

```bash
$ hadoop fs -ls
```

> (the `poeme.txt` file should appear)

Then, execute your program:

> Note: We assume your jar file is named `wordcount-0.0.1.jar` and placed in the current
> directory where your terminal is; adjust the command if required.

```bash
$ hadoop jar wordcount-0.0.1.jar org.mbds.WordCount poeme.txt results
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

> Note: you can use Hadoop Streaming (as seen during the course) if you feel more
> comfortable using another language instead of Java. The main purpose here is to
> implement an efficient MapReduce algorithm to solve the problem.

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

It is **highly advised** to allow your program to adapt its behaviour / pick an
analysis task depending on a command line argument.

Alternatively and if you don’t see how to perform this, you can implement a
separate application for each of the required tasks.

> Note: the Configuration object that we create in the Driver class can also be
> obtained later on from the Map and Reduce classes; and it also allows passing
> values between those classes, which may prove useful for this program. You are
> advised to look for the way to obtain the Configuration instance from the
> context object in the map and reduce classes, as well as looking up the getter
> and setter methods for the Configuration class.

## Objective

Your Hadoop Map Reduce application should be able to perform the following tasks:

1. Obtain the total profit for any given world region.
2. Obtain the total profit for any given country.
3. Obtain the total profit for any given item type.
4. For each item type, provide:
   - How many sales were performed online.
   - How many sales were performed offline.
   
   And for each of those quantities, how much the combined total profit for those sales was.
