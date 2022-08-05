package pattern_study.Bridge;

public class RandomDisplay extends Display {

	public RandomDisplay(DisplayImpl impl) {
		super(impl);
	}
	
	public void randomDisplay() {
		int ran = (int)(Math.random()*10);
		randomDisplay(ran);
	}
	
	private void randomDisplay(int times) {
		open();
		for(int i = 0; i <= times; i++) {
			print();
		}
		close();
	}
}
