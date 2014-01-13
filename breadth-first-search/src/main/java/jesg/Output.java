package jesg;

import java.io.File;
import java.io.IOException;

import jesg.avro.Node;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.io.DatumReader;
import org.apache.avro.specific.SpecificDatumReader;

public class Output {

	public static void main(String[] args) throws IOException {
		DatumReader<Node> reader = new SpecificDatumReader<Node>(Node.class);
		DataFileReader<Node> fileReader = new DataFileReader<Node>(new File("output0/part-r-00000.avro"), reader);
		
		for(Node node : fileReader){
			System.out.println(node);
		}
		fileReader.close();
	}

}
