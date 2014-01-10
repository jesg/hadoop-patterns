package jesg;

import java.io.IOException;

import jesg.avro.JoinKey;
import jesg.constants.JoinConstants;

import org.apache.avro.generic.GenericRecord;
import org.apache.avro.mapred.AvroKey;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

class JoinReducer extends Reducer<AvroKey<JoinKey>, Text, LongWritable, Text> {
	private StringBuilder builder;
	private Long currentKey;
	
	@Override
	public void reduce(AvroKey<JoinKey> key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		GenericRecord record = key.datum();
		
		if( builder == null ){
			builder = new StringBuilder();
		}
		if ( (Integer)record.get("type") == JoinConstants.S ){
			if( currentKey != null ) flush(context);
			currentKey = (Long)record.get("joinKey");
		}
		
		for(Text text : values){
			builder.append(text.toString());
			builder.append(',');
		}
		
	}

	private void flush(Context context) throws IOException, InterruptedException {
		builder.deleteCharAt( builder.length() - 1 ); // delete the tail comma
		
		context.write(new LongWritable(currentKey), new Text(builder.toString()));
		
		builder.setLength(0);
	}
	
	@Override
	protected void cleanup(Context context)
			throws IOException, InterruptedException {
		flush(context);
	}
}
