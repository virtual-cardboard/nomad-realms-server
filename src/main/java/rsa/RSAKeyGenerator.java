package rsa;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;
import static java.math.BigInteger.probablePrime;

import java.math.BigInteger;
import java.util.Random;

public class RSAKeyGenerator {

	private static final BigInteger e = new BigInteger("65537"); // e is public key exponent

	public static void main(String[] args) {
		BigInteger[] generateKey = generateKey();
		BigInteger n = generateKey[0];
		BigInteger d = generateKey[3];
		BigInteger message = new BigInteger("101238947345747");
		BigInteger cipher = message.modPow(e, n);
		BigInteger decoded = cipher.modPow(d, n);
		System.out.println(n);
		System.out.println(message);
		System.out.println(cipher);
		System.out.println(decoded);
	}

	public static BigInteger[] generateKey() {
		BigInteger p = generatePrime();
		BigInteger q = generatePrime();
		BigInteger n = p.multiply(q);
		BigInteger phiN = p.subtract(ONE).multiply(q.subtract(ONE));
		BigInteger d = null; // d is private key exponent
		for (int k = 0; true; k++) {
			BigInteger[] divideAndRemainder = ONE.add(new BigInteger(k + "").multiply(phiN)).divideAndRemainder(e);
			if (divideAndRemainder[1].equals(ZERO)) {
				d = divideAndRemainder[0];
				break;
			}
		}
		return new BigInteger[] { n, p, q, d };
	}

	public static BigInteger generatePrime() {
		return probablePrime(24, new Random());
	}

}
