package pattern_study.Builder;

public class TextBuilder extends Builder {

	private StringBuffer buffer = new StringBuffer();
	
	@Override
	public void makeTitle(String title) {
		buffer.append("========================================\n");
		buffer.append("『" + title + "』" + "\n\n");
	}

	@Override
	public void makeString(String str) {
		buffer.append("▶ " + str + "\n");
		buffer.append("\n");
	}

	@Override
	public void makeItems(String[] items) {
		for(String str : items) {
			buffer.append("■ " + str + "\n");
		}
	}

	@Override
	public void close() {
		buffer.append("========================================\n");
	}
	
	public String getResult() {
		return buffer.toString();
	}
	
}
