package pattern_study.Interpreter;

import turtle.Turtle;

// <command> ::= <repeat command> | <primitive command>
public class CommandNode extends Node {
	
	private Node node;
	private Turtle gerbuk;

	public CommandNode(Turtle gerbuk) {
		this.gerbuk = gerbuk;
	}
	@Override
	public void parse(Context context) throws ParseException {
		if(context.currentToken().equals("repeat")) {
			node = new RepeatCommandNode(gerbuk);
			node.parse(context);
		}
		else {
			node = new PrimitiveCommandNode(gerbuk);
			node.parse(context);
		}
	}
	public String toString() {
		return node.toString();
	}
}
