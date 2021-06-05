import java.util.Random;

public class DomingoFerrerTestAlgorithm {
	private int d;
	private int m;
	private int r;
	private int mprima;
	
	/**
	 * Class Constructor
	 * @param d
	 * @param m
	 * @param r
	 * @param mprima
	 */
	public DomingoFerrerTestAlgorithm(int d, int m, int r, int mprima) {
		this.d = d;
		this.m = m;
		this.r = r;
		this.mprima = mprima;
	}
	
	/**
	 * Encrypts a number following the Domingo-Ferrer protocol
	 * @param numberToEncrypt is the number we want to Encrypt
	 * @return the array of values representing the encrypted number
	 */
	public int[] encrypt(int numberToEncrypt) {
		Random rand = new Random();
		Tools tools = new Tools();
		int[] splittedNumber = new int[d];
		int splittedSum = 0;
		// Split the number to encrypt
		for (int i=0; i < d-1; i++) {
			int split = rand.nextInt(mprima);
			splittedNumber[i] = split;
			splittedSum = splittedSum + split;
		}
		splittedSum = tools.mod(splittedSum, mprima);;
		// The last number
		if (splittedSum <= numberToEncrypt) {
			splittedNumber[d-1] = numberToEncrypt-splittedSum;
		} else {
			splittedNumber[d-1] = mprima - splittedSum + numberToEncrypt;
		}
		// Compute Ek
		for (int i=0; i < d; i++) {
			splittedNumber[i] = splittedNumber[i]*(int)Math.pow(r, i+1);
			splittedNumber[i] = tools.mod(splittedNumber[i], m);
		}
		return splittedNumber;
		/*
		int encrypted1 = rand.nextInt(mprima);
		int encrypted2;
		
		if (encrypted1 < numberToEncrypt) {
			encrypted2 = numberToEncrypt - encrypted1;
		} else {
			encrypted2 = mprima - encrypted1 + numberToEncrypt;
		}
		encrypted1 = encrypted1*r;
		encrypted1 = tools.mod(encrypted1, m);
		encrypted2 = encrypted2*(int)Math.pow(r, 2);
		encrypted2 = tools.mod(encrypted2, m);
		
		int[] encryptedArray = {encrypted1, encrypted2};	
		return encryptedArray;*/
	}
	
	/**
	 * Decrypts a number following the Domingo-Ferrer protocol
	 * @param encryptedData is an array that stores the components of the encrypted number
	 * @return the decrypted number
	 */
	public int decrypt(int[] encryptedData) {
		int decryptedSum = 0;
		Tools tools = new Tools();
		int[] coefficients = new int[2];
		int j = 1;
		coefficients = tools.findInverse(r, m);
		if (r*coefficients[0]%m == 1) {
			for(int coordinatej : encryptedData) {
				decryptedSum = decryptedSum + (int)tools.mod(coordinatej*(long)Math.pow(coefficients[0], j), m);
				j = j + 1;
			}
		} else {
			for(int coordinatej : encryptedData) {
				decryptedSum = decryptedSum + (int)tools.mod(coordinatej*(long)Math.pow(coefficients[1], j), m);
				j = j + 1;
			}
		}
		decryptedSum = tools.mod(decryptedSum, mprima);
		return decryptedSum;
	}
	
	/**
	 * Performs a sum over two encrypted data in the unclassified level
	 * @param operand1 is an array that stores the first encrypted number to sum
	 * @param operand2 is an array that stores the second encrypted number to sum
	 * @return an array that stores the results of the sum as an encrypted number in the unclassified level
	 */
	public int[] sumUnclassified(int[] operand1, int[] operand2) {
		Tools tools = new Tools();
		int[] unClassifiedSum = new int[d];
		for(int i = 0; i < d ; i++) {
			unClassifiedSum[i] = tools.mod(operand1[i] + operand2[i], m);
		}
		return unClassifiedSum;
	}
	
	/**
	 * Performs a product over two encrypted data in the unclassified level
	 * @param operand1 is an array that stores the first encrypted number to multiply
	 * @param operand2 is an array that stores the second encrypted number to multiply
	 * @return an array that stores the results of the product as an encrypted number in the unclassified level
	 */
	public int[] unClassifiedMultiply(int[] operand1, int[] operand2) {
		int[] unClassifiedMultiply = new int[2*d];
		Tools tools = new Tools();
			
		// Initialize the product polynomial	
		for(int i = 0; i < 2*d; i++) {
			unClassifiedMultiply[i] = 0;
		}
		
		// Multiply two polynomials term by term
		for (int i = 0; i < d; i++) {
			//multiply the current term of first polynomial with every term of second
			for (int j = 0; j < d; j++) {
					unClassifiedMultiply[i + j + 1] = tools.mod(unClassifiedMultiply[i+j+1] + operand1[i] * operand2[j], m);	
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
	public int[] unClassifiedConstantProduct(int[] operand, int constant) {
		int[] constantProduct = new int[operand.length];
		for (int i=0; i<operand.length; i++) {
			constantProduct[i] = operand[i]*constant;
		}
		return constantProduct;
	}
	
	/**
	 * Prints an array for testing purposes
	 * @param polynomial is an array where its components represent the coefficients of a polynomial
	 */
	public void printPolynomial(int[] polynomial) {
		System.out.print("(");
		for (int termd : polynomial) {
			System.out.print(termd);
			System.out.print(", ");
		}
		System.out.println(")");
	}
}
