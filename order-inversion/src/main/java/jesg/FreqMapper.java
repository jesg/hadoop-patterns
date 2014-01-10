package jesg;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import jesg.avro.Pair;

import org.apache.avro.mapred.AvroKey;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.google.common.collect.ImmutableList;

class FreqMapper extends Mapper<
	LongWritable, Text, 
	AvroKey<Pair>, IntWritable> {
	
	private static final IntWritable ONE = new IntWritable(1);
	
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
	     
		List<String> tokens = textToList(value);
		
		
		for(int i=0; i < tokens.size(); i++){
			String left = tokens.get(i);
			
			for(int j=i+1; j < tokens.size(); j++){
				String right = tokens.get(j);
				context.write(new AvroKey<Pair>(new Pair(left, true, "*")), ONE);
				context.write(new AvroKey<Pair>(new Pair(left, false, right)), ONE);
			}
		}
	}
	

	private List<String> textToList(Text text){
		final ImmutableList.Builder<String> builder = ImmutableList.builder();
		
		StringTokenizer tokenizer = new StringTokenizer(text.toString());
		while(tokenizer.hasMoreTokens()){
			builder.add(tokenizer.nextToken());
		}
		
		return builder.build();
	}
	
}
