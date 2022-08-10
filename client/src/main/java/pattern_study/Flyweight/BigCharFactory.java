package pattern_study.Flyweight;

import java.util.HashMap;

public class BigCharFactory {
	// 이미 만들어진 BigChar 인스턴스 관리
	private HashMap<Character, BigChar> pool = new HashMap<Character, BigChar>();
	// singleton 패턴
	private static BigCharFactory singleton = new BigCharFactory();
	private BigCharFactory() {}
	public static BigCharFactory getInstance() {
		return singleton;
	}
	// BigChar의 인스턴스 생성(공유)
	public synchronized BigChar getBigChar(char charname) {
		BigChar bc = pool.get(charname);
		if(bc == null) {
			bc = new BigChar(charname);
			pool.put(charname, bc);
		}
		return bc;
	}
}
