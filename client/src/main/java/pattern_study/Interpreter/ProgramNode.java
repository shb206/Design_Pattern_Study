package pattern_study.Interpreter;

import turtle.Turtle;

// <program> ::= program <command list>
public class ProgramNode extends Node{

	private Node commandListNode;
	private Turtle gerbuk;
	
	public ProgramNode(Turtle gerbuk) {
		this.gerbuk = gerbuk;
	}
	public void parse(Context context) throws ParseException {
		context.skipToken("program");
		commandListNode = new CommandListNode(gerbuk);
		commandListNode.parse(context);
	}
	public String toString() {
		return "[program " + commandListNode + "]";
	}
}
