package jesg;

import java.io.IOException;
import java.util.StringTokenizer;

import jesg.avro.Doc;
import jesg.avro.TermDocPair;

import org.apache.avro.mapred.AvroKey;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Mapper;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;

class IndexMapper extends Mapper<AvroKey<Doc>, NullWritable, AvroKey<TermDocPair>, IntWritable> {
	
	@Override
	public void map(AvroKey<Doc> key, NullWritable value, Context context) throws IOException, InterruptedException {
		Doc record = key.datum();
		
		Multiset<String> map = HashMultiset.create();
		StringTokenizer tokenizer = new StringTokenizer((String) record.getContent());
		while(tokenizer.hasMoreTokens()){
			map.add(tokenizer.nextToken());
		}
		
		TermDocPair pair = new TermDocPair("", (int) record.get("id"));
		for (Entry<String> entry : map.entrySet()) {
			pair.setTerm(entry.getElement());
			context.write(new AvroKey<TermDocPair>(pair), new IntWritable(entry.getCount()));
		}
	}
	
}
