package jesg;

import jesg.avro.Doc;
import jesg.avro.Payload;
import jesg.avro.TermDocPair;

import org.apache.avro.Schema;
import org.apache.avro.mapred.AvroKey;
import org.apache.avro.mapred.AvroValue;
import org.apache.avro.mapreduce.AvroJob;
import org.apache.avro.mapreduce.AvroKeyInputFormat;
import org.apache.avro.mapreduce.AvroKeyValueOutputFormat;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class IndexDriver extends Configured implements Tool {

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
		job.setJobName("Inverse index");
		job.setJarByClass(getClass());
		
		AvroJob.setInputKeySchema(job, Doc.getClassSchema());
		AvroJob.setMapOutputKeySchema(job, TermDocPair.getClassSchema());
		AvroJob.setOutputValueSchema(job, Schema.createArray(Payload.getClassSchema()));
		
		job.setInputFormatClass(AvroKeyInputFormat.class);
		job.setOutputFormatClass(AvroKeyValueOutputFormat.class);

	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    
	    job.setMapperClass(IndexMapper.class);
	    job.setReducerClass(IndexReducer.class);
	    
	    job.setMapOutputKeyClass(AvroKey.class);
	    job.setMapOutputValueClass(IntWritable.class);
	    
	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(AvroValue.class);
	    
	    
		return job.waitForCompletion(true) ? 0 : 1;
	}
	
	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new IndexDriver(), args);
		System.exit(exitCode);
	}

}
