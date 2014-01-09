package jesg;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.avro.mapred.AvroValue;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

class CoOccurrenceMapper extends Mapper<
	LongWritable, Text, 
	Text, AvroValue<Map<String, Integer>>> {
	
//	private static final int MAX_CACHE_SIZE = 1000;
//	private Map<String, Integer> cache;
//	
//	@Override
//	protected void setup(Context context)
//			throws IOException, InterruptedException {
//		cache = new HashMap<String, Integer>();
//	}
	
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
	     
		List<String> tokens = textToList(value);
		
		
		for(int i=0; i < tokens.size(); i++){
			String left = tokens.get(i);
			Map<String, Integer> map = Maps.newHashMap();
			
			for(int j=i+1; j < tokens.size(); j++){
				String right = tokens.get(j);
				Integer count = map.get(right);
				
				count = count == null ? 1 : ++count;
				
				map.put(right, count);
			}
			
			context.write(new Text(left), new AvroValue<Map<String, Integer>>(map));
		}
	}
	
//	private void write(String key, int i, Context context) throws IOException, InterruptedException {
//		if( cache.size() > MAX_CACHE_SIZE ) flush(context);
//		
//		Integer count = cache.get(key);
//		
//		if( count == null ){
//			cache.put(key, 1);
//			return;
//		}
//		
//		cache.put(key, ++count);
//	}
//
//	private void flush(Context context) throws IOException, InterruptedException {
//		
//		for (Entry<String, Integer> entry : cache.entrySet()) {
//			context.write(new Text(entry.getKey()), new IntWritable(entry.getValue()));
//		}
//	}

	private List<String> textToList(Text text){
		final ImmutableList.Builder<String> builder = ImmutableList.builder();
		
		StringTokenizer tokenizer = new StringTokenizer(text.toString());
		while(tokenizer.hasMoreTokens()){
			builder.add(tokenizer.nextToken());
		}
		
		return builder.build();
	}
	
//	@Override
//	protected void cleanup(Context context)
//			throws IOException, InterruptedException {
//		flush(context);
//	}
	
}
