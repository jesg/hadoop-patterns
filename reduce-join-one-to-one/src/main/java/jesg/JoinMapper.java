package jesg;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

class JoinMapper extends Mapper<LongWritable, Text, LongWritable, Text> {
	
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String valueAsString = value.toString();
		int firstComma = valueAsString.indexOf(',');
		String joinKey = valueAsString.substring(0, firstComma);
		
		value.set(valueAsString.substring(++firstComma));
				
		context.write(new LongWritable(Long.valueOf(joinKey)), value);
	}

}
