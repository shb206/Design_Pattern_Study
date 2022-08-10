package pattern_study.Command;

import java.util.Iterator;
import java.util.Stack;

public class MacroCommand implements Command {

	private Stack<Command> commands = new Stack<Command>();
	
	@Override
	public void execute() {
		Iterator<Command> it = commands.iterator();
		while(it.hasNext()) {
			it.next().execute();
		}
	}
	// 추가
	public void append(Command cmd) {
		if(cmd != this)
			commands.push(cmd);
	}
	// 마지막 명령 삭제
	public void undo() {
		if(!commands.empty())
			commands.pop();
	}
	// 전부 삭제
	public void clear() {
		commands.clear();
	}
}
