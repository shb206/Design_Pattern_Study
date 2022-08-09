package pattern_study.Observer;

public class ToBinaryObserver implements Observer {

	@Override
	public void update(NumberGenerator generator) {
		System.out.print("ToBinaryObserver : ");
		
		System.out.println(Integer.toBinaryString(generator.getNumber()));
		
		try {
			Thread.sleep(100);
		}
		catch (InterruptedException e) {}
	}
}
