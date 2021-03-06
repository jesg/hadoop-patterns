package jesg;

import jesg.avro.Pair;

import org.apache.avro.mapred.AvroKey;
import org.apache.avro.mapreduce.AvroJob;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class FreqDriver extends Configured implements Tool {

	@Override
	public int run(String[] args) throws Exception {
		if (args.length != 2) {
			System.err.printf(
					"Usage: %s [generic options] <input> <output> \n",
					getClass().getSimpleName());
			ToolRunner.printGenericCommandUsage(System.err);
			return -1;
		}
		Job job = new Job(getConf());
		job.setJobName("freq");
		job.setJarByClass(getClass());
		
		AvroJob.setMapOutputKeySchema(job, Pair.getClassSchema());
		AvroJob.setOutputKeySchema(job, Pair.getClassSchema());
		
	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    
	    job.setMapperClass(FreqMapper.class);
	    job.setReducerClass(FreqReducer.class);
	    job.setCombinerClass(FreqCombiner.class);
	    job.setPartitionerClass(FreqPartitioner.class);
	    
	    job.setMapOutputKeyClass(AvroKey.class);
	    job.setMapOutputValueClass(IntWritable.class);
	    
	    job.setOutputKeyClass(AvroKey.class);
	    job.setOutputValueClass(DoubleWritable.class);
	    
		return job.waitForCompletion(true) ? 0 : 1;
	}
	
	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new FreqDriver(), args);
		System.exit(exitCode);
	}

}
