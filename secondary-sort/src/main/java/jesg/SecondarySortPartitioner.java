package jesg;

import jesg.avro.SecondarySortKey;

import org.apache.avro.mapred.AvroKey;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * 
 * Partition by station.
 * 
 */
class SecondarySortPartitioner extends
        Partitioner<AvroKey<SecondarySortKey>, LongWritable> {

    @Override
    public int getPartition(AvroKey<SecondarySortKey> key, LongWritable value,
            int n) {
        return key.datum().get("station").hashCode() % n;
    }

}
