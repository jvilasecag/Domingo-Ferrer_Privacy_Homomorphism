import java.math.BigInteger;
import java.util.Scanner;

public class DFH {
	public static void main(String[] args) {
		System.out.println("****************************************");
		System.out.println("Initial test of Domingo-Ferrer algorithm");
		System.out.println("****************************************");
		Scanner input = new Scanner(System.in);
		System.out.println("Enter one number");
		int number1 = input.nextInt();
		System.out.println("Enter another number");
		int number2 = input.nextInt();

	
		DomingoFerrerTestAlgorithm DFTest = new DomingoFerrerTestAlgorithm(4, 28, 3, 7);
		int[] encrypted1 = DFTest.encrypt(number1);
		int[] encrypted2 = DFTest.encrypt(number2);
		int[] encryptedSum = DFTest.sumUnclassified(encrypted1, encrypted2);
		int[] encryptedConstantProduct = DFTest.unClassifiedConstantProduct(encrypted1, number2);
		int[] encryptedProduct = DFTest.unClassifiedMultiply(encrypted1, encrypted2);
		
		System.out.print("The encryption of " + number1 + " is ");
		DFTest.printPolynomial(encrypted1);
		System.out.print("The encryption of " + number2 + " is ");
		DFTest.printPolynomial(encrypted2);
		System.out.print("The encrypted product is ");
		DFTest.printPolynomial(encryptedProduct);
		
		int decrypted1 = DFTest.decrypt(encrypted1);
		int decrypted2 = DFTest.decrypt(encrypted2);
		int decryptedSum = DFTest.decrypt(encryptedSum);
		int decryptedConstantProduct = DFTest.decrypt(encryptedConstantProduct);
		int decryptedProduct = DFTest.decrypt(encryptedProduct);
		System.out.println("Decrypted number1: " + decrypted1);
		System.out.println("Decrypted number2: " + decrypted2);
		System.out.println("Decrypted sum: " + decryptedSum);
		System.out.println("Decrypted EncNumber1*number2: " + decryptedConstantProduct);
		System.out.println("Decrypted product: " + decryptedProduct);
		System.out.println();

		System.out.println();
		System.out.println("********************************");
		System.out.println("Construction of classified level");
		System.out.println("********************************");
		System.out.println();
		System.out.println("Enter number of bits of clearText space");
		int clearTSize = input.nextInt();
		System.out.println("Enter the parameter d for Domingo-Ferrer algorithm");
		int d = input.nextInt();
		
		ClassifiedLevel cl = new ClassifiedLevel();
		BigInteger[] mmprima = cl.mBuilder(clearTSize);
		BigInteger r = cl.rBuilder(mmprima[0]);
		DomingoFerrerAlgorithm DFA = new DomingoFerrerAlgorithm(d, mmprima[0], r, mmprima[1]);

		System.out.println();
		System.out.println("****************************************");
		System.out.println("Test of operations on unclassified level");
		System.out.println("****************************************");
		System.out.println();
		System.out.println("Enter number a to sum");
		BigInteger numberaToSum = input.nextBigInteger();
		System.out.println("Enter number b to sum");
		BigInteger numberbToSum = input.nextBigInteger();
		System.out.println("Enter number c to (a+b)*c");
		BigInteger numberc = input.nextBigInteger();
		System.out.println("Enter number k to k(a+b)*c");
		BigInteger numberk = input.nextBigInteger();
		
		BigInteger[] encrypteda = DFA.encrypt(numberaToSum);
		BigInteger[] encryptedb = DFA.encrypt(numberbToSum);
		BigInteger[] encryptedc = DFA.encrypt(numberc);
		BigInteger[] encryptedSumDFA = DFA.sumUnclassified(encrypteda, encryptedb);
		BigInteger[] encryptedProductDFA = DFA.unClassifiedMultiply(encryptedSumDFA, encryptedc);
		BigInteger[] encryptedConstantProductDFA = DFA.unClassifiedConstantProduct(encryptedProductDFA, numberk);
		
		BigInteger decryptedResult = DFA.decrypt(encryptedConstantProductDFA);
		System.out.println(decryptedResult);
		
		input.close();
	}
}
