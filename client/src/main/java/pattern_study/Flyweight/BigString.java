package pattern_study.Flyweight;

public class BigString {
	// '큰 문자'의 배열
	private BigChar[] bigchars;
	
	public BigString(String string, Boolean shared) {
		bigchars = new BigChar[string.length()];
		BigCharFactory factory = BigCharFactory.getInstance();
		
		if(shared) {
			for(int i = 0; i < bigchars.length; i++) {
				bigchars[i] = factory.getBigChar(string.charAt(i));
			}
		}
		else {
			for(int i = 0; i < bigchars.length; i++) {
				bigchars[i] = new BigChar(string.charAt(i));
			}
		}
		
	}
	public void print() {
		for(int i = 0; i < bigchars.length; i++) {
			bigchars[i].print();
		}
	}
}
