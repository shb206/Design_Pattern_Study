package pattern_study.Template;

public class CharDisplay extends AbstractDisplay {
	
	private char c;
	
	public CharDisplay(char ch) {
		this.c = ch;
	}
	
	@Override
	public void open() {
		System.out.print("<<");
	}

	@Override
	public void print() {
		System.out.print(c);
	}

	@Override
	public void close() {
		System.out.print(">>");
	}

}
