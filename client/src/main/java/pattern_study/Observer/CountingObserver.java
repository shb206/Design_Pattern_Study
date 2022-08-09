package pattern_study.Observer;

public class CountingObserver implements Observer {

	private int count;
	
	public CountingObserver() {
		count = 0;
	}
	
	@Override
	public void update(NumberGenerator generator) {
		System.out.println("CountingObserver : " + ++count + "번째 호출");
	}
}
