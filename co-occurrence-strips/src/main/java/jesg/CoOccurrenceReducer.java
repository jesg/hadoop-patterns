package jesg;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.avro.mapred.AvroValue;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.google.common.collect.Maps;

class CoOccurrenceReducer extends Reducer<
	Text, AvroValue<Map<String, Integer>>, 
	Text, AvroValue<Map<String, Integer>>> {
	
	@Override
	public void reduce(Text key, Iterable<AvroValue<Map<String, Integer>>> values, Context context) throws IOException, InterruptedException {
		Map<String, Integer> map = Maps.newHashMap();
		
		for (AvroValue<Map<String, Integer>> avroValue : values) {
			merge(map, avroValue.datum());
		}
		
		context.write(key, new AvroValue<Map<String,Integer>>(map));
	}

	private void merge(Map<String, Integer> map, Map<String, Integer> datum) {
		
		for (Entry<String, Integer> entry : datum.entrySet()) {
			Integer currentCount = map.get(entry.getKey());
			currentCount = currentCount == null ? entry.getValue() : currentCount + entry.getValue();
			
			map.put(entry.getKey(), currentCount);
		}
	}
	
}
