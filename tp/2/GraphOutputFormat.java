package org.mbds;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.fs.FSDataOutputStream;
import java.io.IOException;

public class GraphOutputFormat extends FileOutputFormat<Text, GraphNodeWritable> {
    // The GraphOutputFormat serves two purposes here: it opens the output file on HDFS, and returns a RecordWriter.
    // The RecordWriter will then perform the writing to the output file.
	public RecordWriter<Text, GraphNodeWritable> getRecordWriter(TaskAttemptContext context) throws IOException, InterruptedException {
    // We get the output directory on HDFS.
    Path path = FileOutputFormat.getOutputPath(context);
    // We open the file.
    // To determine its name, we use the getUniqueFile function of the FileOutputFormat class; it takes as parameters
    // the context, a prefix, and an extension (here .txt) and returns a unique file name to store the results that will
    // be written by our OutputFormat.
    // As a result, it will return filenames following the RESULTATS-r-XXXX.txt model (e.g. RESULTATS-r-0000.txt);
    // Note that if a static filename were used here instead, the data in one block (e.g. 0000) would likely be
    // overwritten by another, later block (e.g. 0001).
    Path fullPath = new Path(path, FileOutputFormat.getUniqueFile(context, "RESULTATS", ".txt"));
    FileSystem fs = path.getFileSystem(context.getConfiguration());
    FSDataOutputStream fileOut = fs.create(fullPath, context);
    // We return the custom RecordWriter, to which we pass the stream to our newly opened file.
    return new GraphRecordWriter(fileOut);
	}
}
