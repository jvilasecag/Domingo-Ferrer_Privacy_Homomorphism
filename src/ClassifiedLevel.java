import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class ClassifiedLevel {
	
	/**
	 * Build an m from a predetermined size multiplying smaller integers
	 * @param crearTextSize is the approx size of clear Text dominion
	 * @return the array of m and mprima
	 */	
	public BigInteger[] mBuilder (int clearTextSize) {
		Random rand = new Random();
//		Tools tools = new Tools();
		BigInteger[] mAndmprima = new BigInteger[2];
		BigInteger m = new BigInteger("1");
		BigInteger mprima = new BigInteger("0");
		BigInteger bound = new BigDecimal("1E200").toBigInteger();
	///	BigInteger bigClearTextSize = BigInteger.valueOf(clearTextSize);
		ArrayList<BigInteger> divisorsList = new ArrayList<BigInteger>();
		while(m.compareTo(bound) < 0) {
			BigInteger bigDivisor = new BigInteger(clearTextSize, rand);
	//		BigInteger bigDivisor = BigInteger.valueOf(divisor);
			m = m.multiply(bigDivisor);
			divisorsList.add(bigDivisor);
		//	tools.printPolynomial(divisorsList);
		}
		int i = 0;
		while (mprima.compareTo(BigInteger.valueOf(2).pow(clearTextSize-1)) < 0) {
			mprima = divisorsList.get(i);
			i = i + 1;
			if (i == divisorsList.size()) {
				break;
			}
		}
		mAndmprima[0] = m;
		mAndmprima[1] = mprima;
		return mAndmprima;
	}

	/**
	 * Find an r of a predetermided size that is invertible in modulo m
	 * @param m is the modulo that r must be invertible
	 * @return r
	 */	
	public BigInteger rBuilder(BigInteger m) {
		Random rand = new Random();
		int r = rand.nextInt(1000);
		BigInteger bigR = BigInteger.valueOf(r);
		while (!(BigInteger.valueOf(1).compareTo(bigR.gcd(m)) == 0)) {
			r = rand.nextInt(1000);
			bigR = BigInteger.valueOf(r);
		}
		return bigR;
	}
}
