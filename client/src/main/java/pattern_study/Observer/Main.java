package pattern_study.Observer;

public class Main {

	public static void main(String[] args) {
		NumberGenerator generator = new RandomNumberGenerator();
//		NumberGenerator generator = new IncrementalNumberGenerator(10, 50, 5);
		Observer ob1 = new DigitObserver();
		Observer ob2 = new GraphObserver();
		Observer ob3 = new ToBinaryObserver();
		Observer ob4 = new CountingObserver();
		generator.addObserver(ob1);
		generator.addObserver(ob2);
		generator.addObserver(ob3);
		generator.addObserver(ob4);
		generator.execute();
	}

}
