package pattern_study.Interpreter;

import java.io.BufferedReader;
import java.io.FileReader;

import turtle.Turtle;

public class Main {
	
	private static Turtle gerbuk = new Turtle();

	public static void main(String[] args) {
		// 거북이 시작 방향이 오른쪽이라 위를 향하게 회전시킴
		gerbuk.left(90);
//		String path = Main.class.getResource("").getPath();
//		try {
//			String text;
//			BufferedReader reader = new BufferedReader(new FileReader(path + "program.txt"));
//			
//			while((text = reader.readLine()) != null) {
//				System.out.println("text = \"" + text + "\"");
//				Node node = new ProgramNode();
//				node.parse(new Context(text));
//				System.out.println("node = " + node);
//			}
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
		String context = "program go left go repeat 3 right go end end";
		Node node = new ProgramNode(gerbuk);
		try {
			node.parse(new Context(context));
			System.out.println("node = " + node);
			System.out.println(new Context(context));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
