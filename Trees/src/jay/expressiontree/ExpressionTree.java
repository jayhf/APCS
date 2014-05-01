package jay.expressiontree;

import java.io.StringReader;

/**
 * Write a description of class ExpressionTree here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class ExpressionTree {
	private ExpressionTree left;
	private ExpressionTree right;
	private Object value; // handles operators and digits
	
	/**
	 * only single digit numeric input operators to support are + - / *
	 */
	public ExpressionTree(String prefix) {
		this(new StringReader(prefix));
	}
	
	private ExpressionTree(StringReader prefix) {
		try {
			value = Integer.parseInt("" + prefix.read());
		} catch (Exception e) {
			left = new ExpressionTree(prefix);
			right = new ExpressionTree(prefix);
		}
	}
	
	// return the result of evaluating the tree
	public double evaluate() {
		if (value instanceof String) {
			double l = left.evaluate(), r = right.evaluate();
			if (value.equals("+"))
				return l + r;
			else if (value.equals("-"))
				return l - r;
			else if (value.equals("/"))
				return l / r;
			else
				return l * r;
		} else
			return (Integer) value;
	}
	
	// output an infix version (with parenthesis) of the expression
	@Override
	public String toString() {
		if (value instanceof String) {
			String l = left.toString(), r = right.toString();
			if (value.equals("+"))
				return "(" + l + "+" + r + ")";
			else if (value.equals("-"))
				return "(" + l + "-" + r + ")";
			else if (value.equals("/"))
				return l + "/" + r;
			else
				return l + "*" + r;
		} else
			return value.toString();
	}
	
}
