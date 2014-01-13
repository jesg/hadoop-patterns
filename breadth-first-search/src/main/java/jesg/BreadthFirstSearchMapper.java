package jesg;

import java.io.IOException;
import java.util.List;

import jesg.avro.Node;

import org.apache.avro.mapred.AvroKey;
import org.apache.avro.mapred.AvroValue;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Mapper;

class BreadthFirstSearchMapper extends Mapper<AvroKey<Node>, NullWritable, IntWritable, AvroValue<Node>> {

	@Override
	public void map(AvroKey<Node> key, NullWritable value, Context context) throws IOException, InterruptedException {
		Node node = key.datum();
		List<Integer> adjacencyList = node.getAdjacencyList();
		int distance = increment(node.getDistance());
		
		IntWritable keyOut = new IntWritable(node.getId());
		AvroValue<Node> valueOut = new AvroValue<Node>(node);
		context.write(keyOut, valueOut);
		
		node.setAdjacencyList(null);
		node.setDistance( distance );
		valueOut.datum(node);
		
		for (Integer localNode : adjacencyList) {
			
			keyOut.set(localNode);
			context.write(keyOut, valueOut);
		}
	}
	
	private int increment(int i){
		return i == Integer.MAX_VALUE ? Integer.MAX_VALUE : ++i;
	}

}
