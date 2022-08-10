package pattern_study.Interpreter;

import turtle.Turtle;

// <primitive command> ::= go | right | left
public class PrimitiveCommandNode extends Node {

	private String name;
	private Turtle gerbuk;
	
	public PrimitiveCommandNode(Turtle gerbuk) {
		this.gerbuk = gerbuk;
	}
	@Override
	public void parse(Context context) throws ParseException {
		name = context.currentToken();
		context.skipToken(name);
		
		if(!name.equals("go") && !name.equals("right") && !name.equals("left")) {
			throw new ParseException(name + "is undefined");
		}
		else if(name.equals("go")) {
			gerbuk.forward(20);
		}
		else if(name.equals("right")) {
			gerbuk.right(90);
		}
		else if(name.equals("left")) {
			gerbuk.left(90);
		}
	}
	public String toString() {
		return name;
	}
}
