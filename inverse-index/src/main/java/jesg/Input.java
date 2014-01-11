package jesg;

import java.io.File;
import java.io.IOException;

import jesg.avro.Doc;

import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;

public class Input {

	public static void main(String[] args) throws IOException {
		DatumWriter<Doc> writer = new SpecificDatumWriter<>(Doc.class);
		DataFileWriter<Doc> fileWriter = new DataFileWriter<Doc>(writer);
		
		fileWriter.create(Doc.getClassSchema(), new File("input.avro"));
		fileWriter.append(new Doc(1, "This is my first large document."));
		fileWriter.append(new Doc(2, "This is my second large document. And another sentence in the second document!"));
		fileWriter.append(new Doc(3, "Not a third document."));
		fileWriter.close();
	}

}
