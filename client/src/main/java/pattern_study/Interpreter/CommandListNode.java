package pattern_study.Interpreter;

import java.util.ArrayList;
import java.util.List;

import turtle.Turtle;

// <command list> ::= <command>* end
public class CommandListNode extends Node {

	private List<Node> list = new ArrayList<Node>();
	private Turtle gerbuk;
	
	public CommandListNode(Turtle gerbuk) {
		this.gerbuk = gerbuk;
	}
	@Override
	public void parse(Context context) throws ParseException {
		while(true) {
			if(context.currentToken() == null) {
				throw new ParseException("Missing 'end'");
			}
			else if (context.currentToken().equals("end")) {
				context.skipToken("end");
				break;
			}
			else {
				Node commandNode = new CommandNode(gerbuk);
				commandNode.parse(context);
				list.add(commandNode);
			}
		}
	}
	public String toString() {
		return list.toString();
	}
}
