package jesg;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import jesg.avro.Payload;
import jesg.avro.TermDocPair;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData.Array;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.mapred.AvroKey;
import org.apache.avro.mapred.AvroValue;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.google.common.collect.Iterables;

class IndexReducer extends Reducer<AvroKey<TermDocPair>, IntWritable, Text, AvroValue<Array<Payload>>> {
	private static final IntWritable ZERO = new IntWritable(0);
	private List<Payload> payloads;
	private String prevTerm;
	
	@Override
	protected void setup(Context context)
			throws IOException, InterruptedException {
		payloads = new LinkedList<Payload>();
	}
	
	@Override
	public void reduce(AvroKey<TermDocPair> key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
		GenericRecord record = key.datum();
		String term = (String) record.get("term");
		
		if( prevTerm != null && !prevTerm.equals(term)){
			flush(context);
			payloads.clear();
		}
		
		payloads.add(new Payload((int)record.get("docId"), Iterables.getFirst(values, ZERO).get()));
		prevTerm = term;
	}
	
	@Override
	protected void cleanup(Context context)
			throws IOException, InterruptedException {
		flush(context);
	}

	private void flush(Context context) throws IOException, InterruptedException {
		context.write(new Text(prevTerm), new AvroValue<Array<Payload>>(
				new Array<Payload>(Schema.createArray(Payload.getClassSchema()), payloads)));
	}
}
