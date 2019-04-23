package software.simple.solutions.framework.core.security;

public class RandomPasswordGeneratorTest {

	public static void main(String[] args) {
		int noOfCAPSAlpha = 1;
		int noOfDigits = 1;
		int noOfSplChars = 1;
		int minLen = 8;
		int maxLen = 12;

		for (int i = 0; i < 10; i++) {
			RandomPasswordGenerator randomPasswordGenerator = new RandomPasswordGenerator();
			String pswd = String.valueOf(randomPasswordGenerator.generatePswd(minLen, maxLen, noOfCAPSAlpha, noOfDigits, noOfSplChars));
			System.out.println("Len = " + pswd + ", " + new String(pswd));
		}
	}
}
