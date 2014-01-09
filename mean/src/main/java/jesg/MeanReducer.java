package jesg;

import java.io.IOException;

import jesg.avro.PartialMean;

import org.apache.avro.generic.GenericRecord;
import org.apache.avro.mapred.AvroValue;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

class MeanReducer extends Reducer<Text, AvroValue<PartialMean>, Text, IntWritable> {
	
	@Override
	public void reduce(Text key, Iterable<AvroValue<PartialMean>> values, Context context) throws IOException, InterruptedException {
		
		long sum = 0;
		long count = 0;
		
		for (AvroValue<PartialMean> partialMean : values) {
			
			GenericRecord genericRecord = partialMean.datum();
			sum += (Long)genericRecord.get("sum");
			count += (Long)genericRecord.get("count");
			
		}
		
		context.write(key, new IntWritable( Math.round( sum/count ) ));
	}		
}
