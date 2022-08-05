package pattern_study.Template;

public class StringDisplay extends AbstractDisplay {

	private String str;
	private int length;
	
	public StringDisplay(String str) {
		this.str = str;
		this.length = str.length();
	}
	
	@Override
	public void open() {
		drawline();
	}

	@Override
	public void print() {
		System.out.println("|" + str + "|");
	}

	@Override
	public void close() {
		drawline();
	}
	private void drawline() {
		System.out.print("+");
		for(int i = 0; i < this.length; i++) {
			System.out.print('-');
		}
		System.out.print("+\n");
	}
}
