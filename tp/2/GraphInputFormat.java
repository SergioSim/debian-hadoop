package org.mbds;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import java.io.IOException;

public class GraphInputFormat extends FileInputFormat<Text, GraphNodeWritable> {
	// The purpose of this class is to provide a RecordReader for the GraphNodeWritable type: a GraphRecordReader.
	public RecordReader<Text, GraphNodeWritable> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
		return new GraphRecordReader();
	}
}
