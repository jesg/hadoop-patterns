package jesg;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class JoinDriver extends Configured implements Tool {

	@Override
	public int run(String[] args) throws Exception {
		if (args.length != 3) {
			System.err.printf(
					"Usage: %s [generic options] <input1> <input1> <output> \n",
					getClass().getSimpleName());
			ToolRunner.printGenericCommandUsage(System.err);
			return -1;
		}
		
		Job job = new Job(getConf());
		job.setJobName("Reduce side join: one to one");
		job.setJarByClass(getClass());

		Path t = new Path(args[0]);
		Path s = new Path(args[1]);
		Path output = new Path(args[2]);
	    
		MultipleInputs.addInputPath(job, t, TextInputFormat.class);
		MultipleInputs.addInputPath(job, s, TextInputFormat.class);
		FileOutputFormat.setOutputPath(job, output);
		
	    job.setMapperClass(JoinMapper.class);
	    job.setReducerClass(JoinReducer.class);
	    
	    job.setOutputKeyClass(LongWritable.class);
	    job.setOutputValueClass(Text.class);
	    
		return job.waitForCompletion(true) ? 0 : 1;
	}
	
	public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new JoinDriver(), args);
		System.exit(exitCode);
	}

}
