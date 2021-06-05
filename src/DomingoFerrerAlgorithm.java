import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class DomingoFerrerAlgorithm {
	private int d;
	private BigInteger m;
	private BigInteger r;
	private BigInteger mprima;
	
	/**
	 * Class Constructor
	 * @param d
	 * @param m
	 * @param r
	 * @param mprima
	 */
	public DomingoFerrerAlgorithm(int d, BigInteger m, BigInteger r, BigInteger mprima) {
		this.d = d;
		this.m = m;
		this.r = r;
		this.mprima = mprima;
	}
	
	/**
	 * Encrypts a number following the Domingo-Ferrer algorithm with BigInteger type
	 * @param numberToEncrypt is the number we want to Encrypt
	 * @return the array of values representing the encrypted number
	 */
	public BigInteger[] encrypt(BigInteger numberToEncrypt) {
		Random rand = new Random();
		BigInteger[] splittedNumber = new BigInteger[d];
		BigInteger splittedSum = BigInteger.valueOf(0);
		// Split the number to encrypt
		for (int i=0; i < d-1; i++) {
			BigInteger randomBigInteger;
			do {
				randomBigInteger = new BigInteger(this.m.bitLength(), rand);
			} while (randomBigInteger.compareTo(this.m) >= 0) ;
			splittedNumber[i] = randomBigInteger;
			splittedSum = splittedSum.add(randomBigInteger);
		}
		splittedSum = splittedSum.mod(mprima);
		// The last number
		if (splittedSum.compareTo(numberToEncrypt) <= 0) {
			splittedNumber[d-1] = numberToEncrypt.subtract(splittedSum);
		} else {
			splittedNumber[d-1] = mprima.subtract(splittedSum).add(numberToEncrypt);
		}
		// Compute Ek
		for (int i=0; i < d; i++) {
			splittedNumber[i] = splittedNumber[i].multiply(r.pow(i+1));
			splittedNumber[i] = splittedNumber[i].mod(m);
		}
		return splittedNumber;
	}
	
	/**
	 * Decrypts a number following the Domingo-Ferrer algorithm with BigInteger type
	 * @param encryptedData is an array that stores the components of the encrypted number
	 * @return the decrypted number
	 */
	public BigInteger decrypt(BigInteger[] encryptedData) {
		BigInteger decryptedSum = BigInteger.valueOf(0);
		BigInteger inverseR = r.modInverse(m);
		int j = 1;
		for(BigInteger coordinatej: encryptedData) {
			decryptedSum = decryptedSum.add(coordinatej.multiply(inverseR.pow(j).mod(m)));
			j = j + 1;
		}
		decryptedSum = decryptedSum.mod(mprima);
		return decryptedSum;
	}
	
	/**
	 * Performs a sum over two encrypted data in the unclassified level
	 * @param operand1 is an array that stores the first encrypted number to sum
	 * @param operand2 is an array that stores the second encrypted number to sum
	 * @return an array that stores the results of the sum as an encrypted number in the unclassified level
	 */
	public BigInteger[] sumUnclassified(BigInteger[] operand1, BigInteger[] operand2) {
		BigInteger[] unClassifiedSum = new BigInteger[d];
		for(int i = 0; i < d ; i++) {
			unClassifiedSum[i] = operand1[i].add(operand2[i]).mod(m);
		}
		return unClassifiedSum;
	}
	
	/**
	 * Performs a product over two encrypted data in the unclassified level
	 * @param operand1 is an array that stores the first encrypted number to multiply
	 * @param operand2 is an array that stores the second encrypted number to multiply
	 * @return an array that stores the results of the product as an encrypted number in the unclassified level
	 */
	public BigInteger[] unClassifiedMultiply(BigInteger[] operand1, BigInteger[] operand2) {
		BigInteger[] unClassifiedMultiply = new BigInteger[2*d];
			
		// Initialize the product polynomial	
		for(int i = 0; i < 2*d; i++) {
			unClassifiedMultiply[i] = BigInteger.valueOf(0);
		}
		
		// Multiply two polynomials term by term
		for (int i = 0; i < d; i++) {
			//multiply the current term of first polynomial with every term of second
			for (int j = 0; j < d; j++) {
					unClassifiedMultiply[i + j + 1] = unClassifiedMultiply[i+j+1].add(operand1[i].multiply(operand2[j])).mod(m);	
			}
		}
		return unClassifiedMultiply;
	}
	
	/**
	 * Performs a product over the components of a ciphertext vector in the unclassified level by a cleartext constant.
	 * @param operand is an array that stores the ciphertext vector to multiply
	 * @param operand2 is a cleartext constant
	 * @return an array that stores the results of the product as an encrypted number in the unclassified level
	 */
	public BigInteger[] unClassifiedConstantProduct(BigInteger[] operand, BigInteger constant) {
		BigInteger[] constantProduct = new BigInteger[operand.length];
		for (int i=0; i<operand.length; i++) {
			constantProduct[i] = operand[i].multiply(constant);
		}
		return constantProduct;
	}
	
	/**
	 * Prints an arrayList of BigInteger for testing purposes
	 * @param polynomial is an array where its components represent the coefficients of a polynomial
	 */
	public void printPolynomial(BigInteger[] polynomial) {
		System.out.print("(");
		for (BigInteger termd : polynomial) {
			System.out.print(termd);
			System.out.println(", ");
		}
		System.out.println(")");
	}
	
}
