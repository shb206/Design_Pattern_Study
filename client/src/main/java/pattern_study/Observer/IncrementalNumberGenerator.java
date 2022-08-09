package pattern_study.Observer;

public class IncrementalNumberGenerator extends NumberGenerator {
	
	private int number;
	private int size;
	private int end;
	
	public IncrementalNumberGenerator(int number, int end, int size) {
		this.number = number;
		this.end = end;
		this.size = size;
	}

	@Override
	public int getNumber() {
		return number;
	}

	@Override
	public void execute() {
		for(int i = number; i < end; i += size) {
			number = i;
			notifyObservers();
		}
	}

}
