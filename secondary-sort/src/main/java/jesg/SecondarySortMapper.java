package jesg;

import java.io.IOException;
import java.util.regex.Pattern;

import jesg.avro.SecondarySortKey;

import org.apache.avro.mapred.AvroKey;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

class SecondarySortMapper extends Mapper<LongWritable, Text, AvroKey<SecondarySortKey>, LongWritable> {
	private static final Pattern COMMA = Pattern.compile(",");
	
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String[] data = COMMA.split(value.toString());
		
		SecondarySortKey secondarySortKey = new SecondarySortKey(Long.valueOf(data[0]), Long.valueOf(data[1]));
		
		context.write(new AvroKey<SecondarySortKey>(secondarySortKey), new LongWritable(Long.valueOf(data[2])));
	}
}
