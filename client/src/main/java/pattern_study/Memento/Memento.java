package pattern_study.Memento;

import java.util.ArrayList;
import java.util.List;

public class Memento {
	
	int money;
	List<String> fruits;
	
	public Memento(int money) {
		this.money = money;
		this.fruits = new ArrayList<String>();
	}
	public int getMoney() {
		return money;
	}
	void addFruit(String fruit) {
		fruits.add(fruit);
	}
	List<String> getFruits() {
		return fruits;
	}
}
