package jesg;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

class JoinReducer extends Reducer<LongWritable, Text, LongWritable, Text> {
	
	@Override
	public void reduce(LongWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		
		final StringBuilder builder = new StringBuilder();
		
		for(Text text : values){
			builder.append(text.toString());
			builder.append(',');
		}
		
		builder.deleteCharAt( builder.length() - 1 ); // delete the tail comma
		
		context.write(key, new Text(builder.toString()));
	}		
}
