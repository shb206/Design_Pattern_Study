package pattern_study.Decorator;

public class Main {

	public static void main(String[] args) {
		MultiStringDisplay md = new MultiStringDisplay();
//		Display b1 = new StringDisplay("Hello, world!");
//		Display b2 = new SideBorder(b1, '#');
//		Display b3 = new FullBorder(b2);
//		Display b4 = new UpdownBorder(b1, '%');
//		
//		b1.show();
//		b2.show();
//		b3.show();
//		b4.show();
		md.add("은 아침");
		md.add("좋은 저녁");
		md.add("좋은 안녕히");
		md.add("좋은사람 안녕");
		Display d1 = new SideBorder(md, '#');
		d1.show();
		//md.show();
	}

}
