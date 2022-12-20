package org.mbds;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

// Driver class (implements the main method).
public class WordCount {
	
	// Mapper class.
	// The 4 Generic types correspond to:
	// 1 - Object: the input key (a line number - not used)
	// 2 - Text: the input value (a line of text).
	// 3 - Text: the output key (a word).
	// 4 - IntWritable: the output value (the number 1).
	public static class WordCountMap extends Mapper<Object, Text, Text, IntWritable> {

		private static IntWritable ONE = new IntWritable(1);
		private Text word = new Text();

		// The map method receives one line of text at the time (by default).
		// The `key` argument consists of the line number (not used).
		// The `value` argument consists of the line of text.
		// The `context` argument let us emit key/value pairs.
		@Override
		protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			// We split the line by space and iterate over words.
			StringTokenizer tok = new StringTokenizer(value.toString(), " ");
			while(tok.hasMoreTokens()) {
				word.set(tok.nextToken());
				// We emit the key/value pair (key=word, value=1).
				// Note that the types match the 3th and 4th generic types of the Mapper class.
				context.write(word, ONE);
			}
		}
	}
	
	// Reducer class.
	// The 4 Generic types correspond to:
	// 1 - Text: the input key (a word)
	// 2 - IntWritable: the input value (the number 1).
	// 3 - Text: the output key (a word).
	// 4 - IntWritable: the output value (the number of occurrences).
	public static class WordCountReduce extends Reducer<Text, IntWritable, Text, IntWritable> {

		private IntWritable result = new IntWritable();

		@Override
		protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable value: values) {
				sum += value.get();
			}
			result.set(sum);
			context.write(key, result);
		}
	}
	

	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
		// Instantiate the Hadoop Configuration.
		Configuration conf = new Configuration();

		// Parse command-line arguments.
		// The GenericOptionParser takes care of Hadoop-specific arguments.
		String[] ourArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		// Check input arguments.
		if (ourArgs.length != 2) {
			System.err.println("Usage: wordcount <in> <out>");
		    System.exit(2);
		}

		// Get a Job instance.
		Job job = Job.getInstance(conf, "WordCount");
		// Setup the Driver/Mapper/Reducer classes.
		job.setJarByClass(WordCount.class);
        job.setMapperClass(WordCountMap.class);
        job.setReducerClass(WordCountReduce.class);
        // Indicate the key/value output types we are using in our Mapper & Reducer.
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        
        // Indicate from where to read input data from HDFS.
        FileInputFormat.addInputPath(job, new Path(ourArgs[0]));
        // Indicate where to write the results on HDFS.
        FileOutputFormat.setOutputPath(job, new Path(ourArgs[1]));

        // We start the MapReduce Job execution (synchronous approach).
        // If it completes with success we exit with code 0, else with code 1.
        System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
