package org.mbds;

import org.apache.hadoop.io.Text;
import java.util.List;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.fs.FSDataOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

// Writes the final key/value pairs (after the reduce step).
public class GraphRecordWriter extends RecordWriter<Text, GraphNodeWritable> {
	private DataOutputStream out;

	// Sets the output stream that will be used to write to HDFS.
	public GraphRecordWriter(DataOutputStream stream) {
		out = stream;
	}

	// Called for each key/value pair. Writes it to the output stream (out).
	public void write(Text key, GraphNodeWritable value) throws IOException, InterruptedException {
		out.writeBytes(key.toString() + "\t" + value.get_serialized() + "\n");
	}

	// Called at the end; closes the HDFS output stream.
	public void close(TaskAttemptContext arg0) throws IOException, InterruptedException {
		out.close();
	}
}
