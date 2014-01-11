package jesg;

import jesg.avro.TermDocPair;

import org.apache.avro.mapred.AvroKey;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

class IndexPartitioner extends Partitioner<AvroKey<TermDocPair>, IntWritable> {

	@Override
	public int getPartition(AvroKey<TermDocPair> key, IntWritable value, int n) {
		return (key.datum().get(0).hashCode() * 127) % n;
	}

}
