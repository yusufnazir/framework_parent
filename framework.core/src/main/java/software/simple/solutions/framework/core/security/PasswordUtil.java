package software.simple.solutions.framework.core.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {

	public PasswordUtil() {
		super();
	}

	/**
	 * Get the encrypted version of a password.
	 * 
	 * @param password
	 * @return
	 */
	public String getEncryptedPassword(String password) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder.encode(password);
	}

	/**
	 * Compare plain password with encrypted string.
	 * 
	 * @param password
	 * @param encrypted
	 * @return
	 */
	public Boolean isCorrectPassword(String password, String encrypted) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder.matches(password, encrypted);
	}

	/**
	 * Creates a random password based on the limitations set.
	 * 
	 * @param minLen
	 *            Minimum length of the password
	 * @param maxLen
	 *            Maximum length of the password
	 * @param noOfCAPSAlpha
	 *            Number of uppercase character
	 * @param noOfDigits
	 *            Number of digits
	 * @param noOfSplChars
	 *            Number of special characters
	 * @return The generated password
	 */
	public char[] getRandomPassword(int minLen, int maxLen, int noOfCAPSAlpha, int noOfDigits, int noOfSplChars) {
		return RandomPasswordGenerator.generatePswd(minLen, maxLen, noOfCAPSAlpha, noOfDigits, noOfSplChars);
	}

	public static void main(String[] args) {
		// StandardStringDigester digester = new StandardStringDigester();
		// digester.setAlgorithm("SHA-1");
		// digester.setIterations(50000);
		// String digest = digester.digest("@dm1n");
		// System.out.println(digest);

		PasswordUtil passwordUtil = new PasswordUtil();
		System.out.println(passwordUtil.getEncryptedPassword("@dm1n"));
		System.out.println(passwordUtil.isCorrectPassword("@dm1n",
				"$2a$10$LrA3j31nu1XstnNVe7f0yuBGHcM74FAfoZIA0QWYj4G57PSzmowqe"));
	}
}
