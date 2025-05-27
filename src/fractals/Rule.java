package fractals;

public class Rule {
	protected char symbol;
	protected String string;

	public Rule(char symbol, String string) {
		this.symbol = symbol;
		this.string = string; 
	}
	
	public char getSymbol() {
		return symbol;
	}

	public String getString() {
		return string;
	}
}



