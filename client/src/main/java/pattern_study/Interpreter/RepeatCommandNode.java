package pattern_study.Interpreter;

import java.util.StringTokenizer;

import turtle.Turtle;

// <repeat command> ::= repeat <number> <command list>
public class RepeatCommandNode extends Node {

	private int number;
	private Node commandListNode;
	private Turtle gerbuk;
	
	public RepeatCommandNode(Turtle gerbuk) {
		this.gerbuk = gerbuk;
	}
	@Override
	public void parse(Context context) throws ParseException {
		context.skipToken("repeat");
		number = context.currentNumber();
		context.nextToken();
	
		commandListNode = new CommandListNode(gerbuk);
		commandListNode.parse(context);
	}
	public String toString() {
		return "[repeat " + number + " " + commandListNode + "]";
	}
}
