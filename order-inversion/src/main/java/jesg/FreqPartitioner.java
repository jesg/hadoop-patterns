package jesg;

import jesg.avro.Pair;

import org.apache.avro.mapred.AvroKey;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

class FreqPartitioner extends Partitioner<AvroKey<Pair>, IntWritable> {

	@Override
	public int getPartition(AvroKey<Pair> key,
			IntWritable value, int n) {
		
		return key.datum().get("first").hashCode() % n; // partition by first key
	}

}
