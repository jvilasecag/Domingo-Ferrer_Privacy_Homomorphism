import java.math.BigInteger;
import java.util.ArrayList;

public class Tools {
	
	/**
	 * Find the modulo of x in Zy
	 * @param x the number we want to find the modulo
	 * @param y is the modulo
	 * @return x mod(y)
	 */
	public int mod(int x, int y) {
		int result = x % y;
		if (result < 0) {
			result += y;
		}
		return result;
	}
	
	/**
	 * Find the modulo of x in Zy for long numbers
	 * @param x the number we want to find the modulo in long type
	 * @param y is the modulo
	 * @return x mod(y)
	 */
	public long mod(long x, int y) {
		long result = x % y;
		if (result < 0) {
			result += y;
		}
		return result;
	}
	
	
	/**
	 * Find the modulo of x in Zy for double numbers
	 * @param x the number we want to find the modulo in double type
	 * @param y is the modulo
	 * @return x mod(y)
	 */
	public double mod(double x, int y) {
		double result = x % y;
		if (result < 0) {
			result += y;
		}
		return result;
	}
	
	/**
	 * Finds the gcd between a and b
	 * @param a one of the numbers
	 * @param b the other number
	 * @return the gcd
	 */
	public long GCD (int a, int b) {
		long gcd = 0;
		int r = 0;
		
		a = Math.abs(a);
		b = Math.abs(b);
		
		while (true) {
				if (b == 0) {
					gcd = a;
					break;
				} else {
					r = a % b;
					a = b;
					b = r;
				}
		}
		return gcd;
	}
	
	/**
	 * Finds the coefficients of ax+by=d for a (mod b) where d is the gcd(a,b)
	 * @param a is the base of which we want the inverse
	 * @param b is the modulo
	 * @return the array of coefficients
	 */
	public int[] findInverse(int a, int b) {
		int mod = b;
		int x = 0;
		int y = 1;
		int lastx = 1;
		int lasty = 0;
		
		while (b != 0) {
			int quotient = a/b;
			int temp = a;
			a = b;
			b = temp % b;
			
			temp = x;
			x = lastx - quotient*x;
			lastx = temp;
			
			temp = y;
			y = lasty - quotient*y;
			lasty = temp;
		}
		int[] coefficients = {this.mod(lastx,mod), lasty, a};
		return coefficients;
	}
	
	
	/**
	 * Prints an arrayList of BigInteger for testing purposes
	 * @param polynomial is an array where its components represent the coefficients of a polynomial
	 */
	public void printPolynomial(ArrayList<BigInteger> polynomial) {
		System.out.print("(");
		for (BigInteger termd : polynomial) {
			System.out.print(termd);
			System.out.println(", ");
		}
		System.out.println(")");
	}
}
