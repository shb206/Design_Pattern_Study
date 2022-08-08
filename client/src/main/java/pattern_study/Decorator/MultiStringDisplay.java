package pattern_study.Decorator;

import java.util.ArrayList;
import java.util.List;

public class MultiStringDisplay extends Display {

	private List<String> stringArr;
	private int maxCol = 0;
	
	public MultiStringDisplay() {
		stringArr = new ArrayList<String>();
	}
	
	@Override
	public int getColumns() {
		return maxCol;
	}

	@Override
	public int getRows() {
		return stringArr.size();
	}

	@Override
	public String getRowText(int row) {
		StringBuffer buf = new StringBuffer();
		
		for(int i = 0; i < maxCol - stringArr.get(row).length(); i++)
			buf.append(" ");
			
		
		return stringArr.get(row) + buf.toString();
	}
	
	public void add(String str) {
		stringArr.add(str);
		
		if(maxCol < str.length())
			maxCol = str.length();
	}
	public void getMax() {
		System.out.println(maxCol);
	}
}
