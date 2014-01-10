package jesg;

import java.io.IOException;

import jesg.avro.Pair;

import org.apache.avro.generic.GenericRecord;
import org.apache.avro.mapred.AvroKey;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

class FreqReducer extends Reducer<
	AvroKey<Pair>, IntWritable, 
	AvroKey<Pair>, DoubleWritable> {
	
	private CharSequence currentWord;
	private int currentTotal = 0;
	
	@Override
	public void reduce(AvroKey<Pair> key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
		GenericRecord record = key.datum();
		
		if( (Boolean)record.get("special") ) {
			currentTotal = sum(values);
			currentWord = (CharSequence) record.get("first");
			return;
		}
		
		if( !((CharSequence) record.get("first")).equals(currentWord) ){
			System.out.println(">>>>>>>>>>>>>>>>>>>>ERRRRRRROR");
		}
		
		context.write(key, new DoubleWritable((double)sum(values)/currentTotal));
		
	}
	
	private int sum(Iterable<IntWritable> values){
		int sum = 0;
		for (IntWritable i : values) {
			sum += i.get();
		}
		return sum;
	}
}
