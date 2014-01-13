package jesg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import jesg.avro.Node;

import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;

public class Input {

	public static void main(String[] args) throws IOException {

		Node node1 = new Node(1, 0, new ArrayList<Integer>()); // source node
		Node node2 = new Node(2, Integer.MAX_VALUE, new ArrayList<Integer>());
		Node node3 = new Node(3, Integer.MAX_VALUE, new ArrayList<Integer>());
		Node node4 = new Node(4, Integer.MAX_VALUE, new ArrayList<Integer>());
		Node node5 = new Node(5, Integer.MAX_VALUE, new ArrayList<Integer>());
		
		node1.getAdjacencyList().addAll(Arrays.asList(2, 4));
		node2.getAdjacencyList().addAll(Arrays.asList(3, 5));
		node3.getAdjacencyList().addAll(Arrays.asList(4));
		node4.getAdjacencyList().addAll(Arrays.asList(5));
		node5.getAdjacencyList().addAll(Arrays.asList(1, 2, 3));
		
		DatumWriter<Node> writer = new SpecificDatumWriter<Node>(Node.class);
		DataFileWriter<Node> fileWriter = new DataFileWriter<Node>(writer);
		
		fileWriter.create(Node.getClassSchema(), new File("input.avro"));
		fileWriter.append(node1);
		fileWriter.append(node2);
		fileWriter.append(node3);
		fileWriter.append(node4);
		fileWriter.append(node5);
		
		fileWriter.close();
		
	}

}
