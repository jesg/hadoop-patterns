package jesg;

import java.io.IOException;

import jesg.avro.JoinKey;
import jesg.constants.JoinConstants;

import org.apache.avro.mapred.AvroKey;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

class JoinMapper extends Mapper<LongWritable, Text, AvroKey<JoinKey>, Text> {
	
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String valueAsString = value.toString();
		int firstComma = valueAsString.indexOf(',');
		Long keyAsLong = Long.valueOf(valueAsString.substring(0, firstComma));
		
		final JoinKey joinKey;
		switch (valueAsString.charAt( ++firstComma )) {
			case 's':
				joinKey = new JoinKey(keyAsLong, JoinConstants.S);
				break;
	
			default:
				joinKey = new JoinKey(keyAsLong, JoinConstants.DEFAULT_TYPE);
				break;
		}
		
		value.set(valueAsString.substring( firstComma + 3 )); // skip 2 commas and one character
				
		context.write(new AvroKey<JoinKey>(joinKey), value);
	}

}
