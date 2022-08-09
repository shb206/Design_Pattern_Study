package pattern_study.Memento;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Gamer {
	
	private int money;
	private List<String> fruits;
	private Random random;
	private static String[] fruitsname = {"사과", "포도", "바나나", "귤" };
	
	public Gamer(int money) {
		fruits = new ArrayList<String>();
		random = new Random();
		this.money = money;
	}
	public int getMoney() {
		return money;
	}
	public void bet() {
		int dice = random.nextInt(6) + 1;
		
		if(dice == 1) {
			money += 100;
			System.out.println("소지금이 증가했습니다.");
		}
		else if (dice == 2) {
			money /= 2;
			System.out.println("소지금이 절반이 되었습니다.");
		}
		else if (dice == 6) {
			String f = getFruit();
			System.out.println("과일(" + f + ")를 받았습니다.");
			fruits.add(f);
		}
		else {
			System.out.println("변한 것이 없습니다.");
		}
	}
	public Memento createMemento() {
		Memento m = new Memento(money);
		Iterator<String> it = fruits.iterator();
		while(it.hasNext()) {
			String f = it.next();
			if(f.startsWith("맛있는 ")) {
				m.addFruit(f);
			}
		}
		return m;
	}
	public void restoreMemento(Memento memento) {
		this.money = memento.money;
		this.fruits = memento.getFruits();
	}
	public String toString() {
		return "[Money = " + money + ", fruits = " + fruits + "]";
	}
	private String getFruit() {
		String prefix = "";
		if(random.nextBoolean()) {
			prefix = "맛있는 ";
		}
		return prefix + fruitsname[random.nextInt(fruitsname.length)];
	}
}
