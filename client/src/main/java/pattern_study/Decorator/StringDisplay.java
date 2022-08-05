package pattern_study.Decorator;

public class StringDisplay extends Display {

	private String string;
	
	public StringDisplay(String string) {
		super();
		this.string = string;
	}

	@Override
	public int getColumns() {
		return string.getBytes().length;
	}

	@Override
	public int getRows() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public String getRowText(int row) {
		if(row == 0) {
			return string;
		}
		else {
			return null;
		}
	}
	
}
