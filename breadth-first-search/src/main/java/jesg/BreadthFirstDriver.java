package jesg;

import java.io.File;
import java.util.Iterator;

import jesg.avro.Node;

import org.apache.avro.mapred.AvroKey;
import org.apache.avro.mapred.AvroValue;
import org.apache.avro.mapreduce.AvroJob;
import org.apache.avro.mapreduce.AvroKeyInputFormat;
import org.apache.avro.mapreduce.AvroKeyOutputFormat;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class BreadthFirstDriver extends Configured implements Tool {
	private static final IOFileFilter AVRO_FILE_FILTER = new SuffixFileFilter("avro");
	private static final File WORK_DIR = new File(".");

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
		job.setJobName("breath first search");
		job.setJarByClass(getClass());
		
		AvroJob.setInputKeySchema(job, Node.getClassSchema());
		AvroJob.setMapOutputValueSchema(job, Node.getClassSchema());
		AvroJob.setOutputKeySchema(job, Node.getClassSchema());
		
		job.setInputFormatClass(AvroKeyInputFormat.class);
		job.setOutputFormatClass(AvroKeyOutputFormat.class);

	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    
	    job.setMapperClass(BreadthFirstSearchMapper.class);
	    job.setReducerClass(BreadthFirstSearchReducer.class);
	    
	    job.setMapOutputKeyClass(IntWritable.class);
	    job.setMapOutputValueClass(AvroValue.class);
	    
	    job.setOutputKeyClass(AvroKey.class);
	    job.setOutputValueClass(NullWritable.class);
	    
		int exitCode = job.waitForCompletion(true) ? 0 : 1;
		if(exitCode == 1) System.exit(1);
		
		long count = job.getCounters().findCounter(Distance.INFINITE).getValue();
		System.out.println("count: " + count);
		return (int) count;
	}
	
	public static void main(final String[] args) throws Exception {
		String[] localArgs = args;
		
		int counter = 0;
		while(true){
			int exitCode = ToolRunner.run(new BreadthFirstDriver(), localArgs);
			if( exitCode == 0 ) System.exit(0);
			
			Iterator<File> outputFiles = FileUtils.iterateFiles(new File(args[1]), AVRO_FILE_FILTER, FalseFileFilter.FALSE);
			localArgs[0] = outputFiles.next().toPath().toString(); //assume one file
			localArgs[1] = localArgs[1] + counter;
			
			++counter;
		}
		
	}

}
