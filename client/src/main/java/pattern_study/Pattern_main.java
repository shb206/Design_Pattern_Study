package pattern_study;

import java.io.IOException;

import pattern_study.Template.AbstractDisplay;
import pattern_study.Template.CharDisplay;
import pattern_study.Template.StringDisplay;

public class Pattern_main {

	public static void main(String[] args) {
		//AbstractDisplay ab = new CharDisplay('M');
		//ab.display();
		AbstractDisplay ab = new StringDisplay("abcde");
		ab.display();
		
	}

}
