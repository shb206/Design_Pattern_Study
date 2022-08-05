package pattern_study.Adapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class FileProperties implements FileIO {
	Properties properties;
	File file;
	
	public FileProperties() {
		properties = new Properties();
	}
	@Override
	public void readFromFile(String filename) throws IOException {
		this.file = new File("c:\\study\\" + filename);
		FileInputStream fi = new FileInputStream(file);
		properties.load(fi);
		for(Object i : properties.keySet()) {
			System.out.println("key => " + (String)i);
			System.out.println("value => " + properties.getProperty((String)i)); 
		}
	}
	@Override
	public void writeToFile(String filename) throws IOException {
		File nfile = file = new File("c:\\study\\" + filename);
		FileWriter fw = new FileWriter(nfile);
		properties.store(fw, "written by FileProperties");
	}
	@Override
	public void setValue(String key, String value) {
		properties.setProperty(key, value);
	}
	@Override
	public String getValue(String key) {
		return properties.getProperty(key);
	}
}
