package fractals;

public class LSystem {
	protected String sentence;
	protected Rule[] ruleset;
	protected int generation;

	public LSystem(String axiom, Rule[] ruleset) {
		sentence = axiom;
		this.ruleset = ruleset;
		generation = 0;
	}

	public String getSentence()
	{
		return sentence;
	}
	
	public int getGeneration()
	{
		return generation;
	}
	
	public void nextGeneration() {
		generation++;

		String nextgen = "";
		for(int i=0;i<sentence.length();i++) {
			char c = sentence.charAt(i);
			String replace = "" + c;
			for (Rule rule : ruleset) {
				if (c == rule.getSymbol()) {
					replace = rule.getString();
					break;
				}
			}
			nextgen += replace;
		}
		sentence = nextgen;
	}
}