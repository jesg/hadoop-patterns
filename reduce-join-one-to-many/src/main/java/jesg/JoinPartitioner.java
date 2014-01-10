package jesg;

import jesg.avro.JoinKey;

import org.apache.avro.mapred.AvroKey;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class JoinPartitioner extends Partitioner<AvroKey<JoinKey>, Text> {

	@Override
	public int getPartition(AvroKey<JoinKey> key, Text value, int n) {
		return (key.datum().get(0).hashCode() * 127) % n;
	}

}
