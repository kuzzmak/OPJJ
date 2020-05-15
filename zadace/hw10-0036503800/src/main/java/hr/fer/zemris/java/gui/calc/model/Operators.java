package hr.fer.zemris.java.gui.calc.model;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

/**
 * Razred koji sadrži moguće unarne i binarne operatore koje nudi {@code Calculator}..
 * 
 * @author Antonio Kuzminski
 *
 */
public class Operators {

	// a/b
	public static DoubleBinaryOperator DIV = (a, b) ->  a / b;
	// a*b
	public static DoubleBinaryOperator MUL = (a, b) -> {return a * b;};
	// a+b
	public static DoubleBinaryOperator ADD = (a, b) -> {return a + b;};
	// a-b
	public static DoubleBinaryOperator SUB = (a, b) -> {return a - b;};
	// a^b
	public static DoubleBinaryOperator XPOWN = (a, b) -> {return Math.pow(a, b);};
	// a^{1/n}
	public static DoubleBinaryOperator XPOW1N = (a, b) -> {return Math.pow(a, 1. / b);};
	// 1/a
	public static DoubleUnaryOperator ONEDIVX = a -> 1 / a;
	// log_10(a)
	public static DoubleUnaryOperator LOG = Math::log10;
	// ln(a)
	public static DoubleUnaryOperator LN = Math::log;
	// sin(a)
	public static DoubleUnaryOperator SIN = Math::sin;
	// cos(a)
	public static DoubleUnaryOperator COS = Math::cos;
	// tan(a)
	public static DoubleUnaryOperator TAN = Math::tan;
	// ctg(a)
	public static DoubleUnaryOperator CTG = a -> 1 / TAN.applyAsDouble(a);
	// 10^a
	public static DoubleUnaryOperator TENPOWX = a -> Math.pow(10, a);
	// e^a
	public static DoubleUnaryOperator EPOWX = Math::exp;
	// arcsin(a)
	public static DoubleUnaryOperator ARCSIN = Math::asin;
	// arccos(a)
	public static DoubleUnaryOperator ARCCOS = Math::acos;
	// arctan(a)
	public static DoubleUnaryOperator ARCTAN = Math::atan;
	// arcctg(a)
	public static DoubleUnaryOperator ARCCTG = a -> Math.PI / 2 - Math.atan(a);
}
