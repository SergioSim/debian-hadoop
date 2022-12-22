package org.mbds;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader;

public class GraphRecordReader extends RecordReader<Text, GraphNodeWritable> {
	private LineRecordReader lineRecordReader = null;
	private Text key = null;
	private GraphNodeWritable value = null;

	// Initializes the RecordReader.
	// We use the Hadoop LineRecordReader, which allows to easily read a file line by line.
	public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
		close();
		lineRecordReader = new LineRecordReader();
		lineRecordReader.initialize(split, context);
	}

  // Returns the current key (the one that was read during the last read operation performed in nextKeyValue()).
  public Text getCurrentKey() throws IOException, InterruptedException {
    return key;
  }

    // Returns the current value (the one that was read during the last read operation performed in nextKeyValue()).
	public GraphNodeWritable getCurrentValue() throws IOException, InterruptedException {
		return value;
	}

	// Returns a progress indicator. Here we use the LineRecordReader helper
	// (which indicates the reading progress within the file split).
	public float getProgress() throws IOException, InterruptedException {
		return lineRecordReader.getProgress();
	}

    // Closing the file (again via the LineRecordReader helper).
	public void close() throws IOException {
		if(lineRecordReader != null)
		{
			lineRecordReader.close();
			lineRecordReader = null;
		}
		key = null;
		value = null;
	}

	// Reads a line of text, then stores a key value pair which can be accessed via getCurrentKey and getCurrentValue.
	// Returns true if the line has been read successfully else false (end of file, for example)
	public boolean nextKeyValue() throws IOException, InterruptedException {
		if(!lineRecordReader.nextKeyValue()) {
			key = null;
			value = null;
			return false;
		}

		// Reading logic (splitting the key and value by '\t').
		Text line = lineRecordReader.getCurrentValue();
		String str = line.toString();
		String[] arr = str.split("\\t");
		key = new Text(arr[0]);
		value = new GraphNodeWritable(arr[1]);
		return true;
	}

}
