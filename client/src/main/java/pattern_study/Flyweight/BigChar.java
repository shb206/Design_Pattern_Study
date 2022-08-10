package pattern_study.Flyweight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BigChar {
	// 문자 이름
	private char charname;
	// 큰 문자를 표현하는 문자열('#' '.' '\n'의 열)
	private String fontdata;
	// 생성자
	public BigChar(char charname) {
		String path = BigChar.class.getResource("").getPath();
		this.charname = charname;
		try {
			BufferedReader reader = new BufferedReader(
					new FileReader(path + "big" + charname + ".txt"));
			String line;
			StringBuffer buf = new StringBuffer();
			
			while ((line = reader.readLine()) != null) {
				buf.append(line);
				buf.append("\n");
			}
			reader.close();
			this.fontdata = buf.toString();
		}
		catch (IOException e) {
			this.fontdata = charname + "?";
		}
	}
	// 큰 문자 표현
	public void print() {
		System.out.println(fontdata);
	}
}
