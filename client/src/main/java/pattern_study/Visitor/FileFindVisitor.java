package pattern_study.Visitor;

import java.util.Iterator;

public class FileFindVisitor extends Visitor {
	
	private String findExt = "";
	
	public FileFindVisitor(String findExt) {
		this.findExt = findExt;
	}

	@Override
	public void visit(File file) {
		if(file.getName().indexOf(".") != -1) {
			String ext = file.getName().split("\\.")[1];

			if(ext.equals(findExt))
				System.out.println(file.getName());
		}
	}

	@Override
	public void visit(Directory directory) {
		Iterator it = directory.iterator();
		
		while(it.hasNext()) {
			Entry entry = (Entry) it.next();
			entry.accept(this);
		}
	}
	
}
