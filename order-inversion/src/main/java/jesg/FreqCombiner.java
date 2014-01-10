package jesg;

import java.io.IOException;

import jesg.avro.Pair;

import org.apache.avro.mapred.AvroKey;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

class FreqCombiner extends Reducer<
	AvroKey<Pair>, IntWritable,
	AvroKey<Pair>, IntWritable> {

	@Override
	protected void reduce(AvroKey<Pair> key, Iterable<IntWritable> values,Context context)
			throws IOException, InterruptedException {
		int count = 0;
		for(IntWritable i : values){
			count += i.get();
		}
		context.write(key, new IntWritable(count));
	}
}
