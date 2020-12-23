package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Writer {
	
	FileWriter fw;
	PrintWriter pw;

	public Writer(String path) throws IOException {
		fw = new FileWriter(new File(path));
		pw = new PrintWriter(fw);
	}
	
	public void write(String text) {
		pw.println(text);
	}
	
	public void close() throws IOException {
		fw.close();
		pw.close();
	}

}
