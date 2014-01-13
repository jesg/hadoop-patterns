package jesg;

import java.io.IOException;

import jesg.avro.Node;

import org.apache.avro.mapred.AvroKey;
import org.apache.avro.mapred.AvroValue;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

class BreadthFirstSearchReducer extends Reducer<IntWritable, AvroValue<Node>, AvroKey<Node>, NullWritable> {
	
	@Override
	public void reduce(IntWritable key, Iterable<AvroValue<Node>> values, Context context) throws IOException, InterruptedException {
		int minDistance = Integer.MAX_VALUE;
		Node node = null;
		
		for(AvroValue<Node> localNodeValue : values){
			Node localNode = localNodeValue.datum();
			
			if( localNode.getAdjacencyList() != null ){
				node = Node.newBuilder(localNode).build(); // clone the node
			}
			if ( localNode.getDistance() < minDistance ) {
				minDistance = localNode.getDistance();
			}
		}
		
		if( minDistance == Integer.MAX_VALUE ){
			context.getCounter(Distance.INFINITE).increment(1);
		}
		
		node.setDistance(minDistance);
		context.write(new AvroKey<Node>(node), NullWritable.get());
	}		
}
