package jesg;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import jesg.avro.PartialMean;

import org.apache.avro.mapred.AvroValue;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

class MeanMapper extends Mapper<Text, Text, Text, AvroValue<PartialMean>> {
	private Map<String, PartialMean> partialResults;
	
	@Override
	protected void setup(Context context)
			throws IOException, InterruptedException {
		partialResults = new HashMap<String, PartialMean>();
	}
	
	@Override
	public void map(Text key, Text value, Context context) throws IOException, InterruptedException {
		add(key.toString(), Long.valueOf(value.toString()));
		
	}
	
	private void add(String key, long value) {
		PartialMean partialResult = partialResults.get(key);
		
		if( partialResult == null){
			partialResults.put(key, new PartialMean(value, 1L));
			return;
		}
		
		partialResult.setCount( partialResult.getCount() + 1L );
		partialResult.setSum( partialResult.getSum() + value );
	}

	@Override
	protected void cleanup(Context context)
			throws IOException, InterruptedException {
		
		for (Entry<String, PartialMean> entry : partialResults.entrySet()) {
			context.write(new Text(entry.getKey()), new AvroValue<PartialMean>(entry.getValue()));
		}
	}
}
