package pattern_study.Strategy;

public class Main {
	public static void main(String[] args) {
		if(args.length != 2) {
			System.out.println("Usage : java Main randomseed1 randomseed2");
			System.out.println("Example : java Main 314 15");
			System.exit(0);
		}
		int seed1 = Integer.parseInt(args[0]);
		int seed2 = Integer.parseInt(args[1]);
//		int seed1 = 314;
//		int seed2 = 1;
//		Player player1 = new Player("두리", new WinningStrategy(seed1));
//		Player player2 = new Player("하나", new ProbStrategy(seed2));
		Player player1 = new Player("두리", new RandomStrategy(seed1));
		Player player2 = new Player("하나", new RandomStrategy(seed2));
		
		for(int i = 0; i < 10000; i++) {
			Hand nexthand1 = player1.nextHand();
			Hand nexthand2 = player1.nextHand();
			
			if(nexthand1.isStrongerThan(nexthand2)) {
				System.out.println("Winner : " + player1);
				player1.win();
				player2.lose();
			}
			else if(nexthand2.isStrongerThan(nexthand1)) {
				System.out.println("Winner : " + player2);
				player1.lose();
				player2.win();
			}
			else {
				System.out.print("Even...");
				player1.even();
				player2.even();
			}
		}
		System.out.println("Total result : ");
		System.out.println(player1.toString());
		System.out.println(player2.toString());
	}
}
