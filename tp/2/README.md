# TP 2 - Graph Breadth-first search

(Updated version from the original author: Benjamin Renaut)

The goal of this exercise is to implement the graph search algorithm described in the
course.

For this task, you will need to execute the same map/reduce program several times until
a stopping condition (all nodes black), passing its previous output data as input data
for the new run every time.

Please note the format of the initial file (`graph_input.txt`).

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


# TP 2 - Graph Breadth-first search with a custom Writable type

In this exercise we reiterate over the previous one, however, instead of using Hadoop's
(`Input`/`Output`)`Format` classes we will be using our own
`GraphNodeInputFormat`/`GraphNodeOutputFormat` classes along with a custom writable
type `GraphNodeWritable`.

To get started quickly, we provide you with the implementation of `GraphNodeInputFormat`
and `GraphNodeOutputFormat`; thus it remains to implement the `GraphNodeWritable` class
and adapt the previously implemented `Driver`, `Mapper` and `Reducer` classes to use the
`GraphNodeInputFormat`, `GraphNodeOutputFormat` and `GraphNodeWritable` classes.

- The `GraphNodeWritable` class must implement the following method:
  ```java
  public String get_serialized()
  ```
  which should return a serialized string describing the graph node (you pick the format);
  this method is called by the provided `GraphNodeOutputFormat` class.
- The `GraphNodeWritable` class must also must have the following constructor:
  ```java
  public GraphNodeWritable(String data)
  ```
  taking a string with the same format and constructing a graph node object accordingly
  (de-serialization).
- It is highly advised to check out the code for the provided InputFormat and
  OutputFormat classes.
