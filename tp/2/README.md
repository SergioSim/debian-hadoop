# TP 2 - Graph Breadth-first search

(Updated version from the original author: Benjamin Renaut)

The goal of this exercise is to implement the graph search algorithm described in the
course.

For this task, you will need to execute the same map/reduce program several times until
a stopping condition (all nodes black), passing its previous output data as input data
for the new run every time.

Please note the format of the initial file.

This format follows the Hadoop standard: one key;value pair per line, with a
tab between the key and the value.

> Note: In order to avoid Hadoop passing you the data with the key being set as the
> line number from the initial text file, you will therefore need to use a specific
> **InputFormat** so that it correctly interprets the keys.
> 
> The InputFormat to be used has been mentioned in the course material;
> you can also easily research it yourself.

If your program works correctly, you should obtain the same final result as seen during
the course.
Please make sure to implement your program so that each intermediary step
(the output of each run) is properly saved on HDFS.
