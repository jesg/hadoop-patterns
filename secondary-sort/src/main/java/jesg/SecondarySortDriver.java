package jesg;

import jesg.avro.SecondarySortKey;

import org.apache.avro.mapred.AvroKey;
import org.apache.avro.mapreduce.AvroJob;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class SecondarySortDriver extends Configured implements Tool {

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
		job.setJobName("Secondary Sort");
		job.setJarByClass(getClass());
		
		AvroJob.setMapOutputKeySchema(job, SecondarySortKey.getClassSchema());
		AvroJob.setOutputKeySchema(job, SecondarySortKey.getClassSchema());

	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    
	    job.setMapperClass(SecondarySortMapper.class);
	    job.setReducerClass(Reducer.class); // identity reducer
	    job.setPartitionerClass(SecondarySortPartitioner.class);
	    
	    job.setMapOutputKeyClass(AvroKey.class);
	    job.setMapOutputValueClass(LongWritable.class);
	    
	    job.setOutputKeyClass(AvroKey.class);
	    job.setOutputValueClass(LongWritable.class);
	    
	    
		return job.waitForCompletion(true) ? 0 : 1;
	}
	
	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new SecondarySortDriver(), args);
		System.exit(exitCode);
	}

}
