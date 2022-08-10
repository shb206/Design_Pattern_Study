package pattern_study.Flyweight;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {
	private static BigString[] bsarray = new BigString[1000];
	public static void main(String[] args) throws IOException {
		
		// 하나의 긴 문자열 시간 비교
//		Random random = new Random();
//		StringBuffer strB = new StringBuffer();
//		for(int i = 0; i < 10000; i++) {
//			strB.append(random.nextInt(10));
//		}
//		String str = strB.toString();
//		long start1 = System.currentTimeMillis();
//		BigString bs = new BigString(str, true);
//		long end1 = System.currentTimeMillis();
//		long start2 = System.currentTimeMillis();
//		BigString bs2 = new BigString(str, false);
//		long end2 = System.currentTimeMillis();
//		System.out.println("자원 공유 >> " + (end1 - start1));
//		System.out.println("자원 비공유 >> " + (end2 - start2));
		System.out.println("공유한 경우 : ");
		testAllocation(true);
		System.out.println("공유하지 않은 경우 : ");
		testAllocation(false);
	}
	public static void testAllocation(boolean shared) {
		for(int i = 0; i < bsarray.length; i++) {
			bsarray[i] = new BigString("12223112", shared);
		}
		showMemory();
	}
	public static void showMemory() {
		Runtime.getRuntime().gc();
		long used = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		System.out.println("사용 메모리 = " + used);
	}
}
