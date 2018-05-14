import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;

public class Utils {

	/**
	 * Hashing with SHA256
	 *
	 * @param input
	 *            String to hash
	 * @return String hashed
	 */
	public static String sha256(String input) {
		String sha256 = null;
		try {
			MessageDigest msdDigest = MessageDigest.getInstance("SHA-256");
			msdDigest.update(input.getBytes("UTF-8"), 0, input.length());
			sha256 = DatatypeConverter.printHexBinary(msdDigest.digest());
		} catch (Exception e) {
			Logger.getLogger(sha256).log(Level.SEVERE, null, e);
		}
		return sha256;
	}

	/**
	 * Hashing 2 a big number mod by a public parameter
	 *
	 * @param input
	 *            String id, BigInteger xi, Element ci, BigInteger di, long ti
	 * @return BigInteger
	 */
	public static BigInteger hash2Big(String orgStr, BigInteger q) {
		BigInteger bi = new BigInteger(orgStr.getBytes());
		bi = bi.mod(q);
		return bi;
	}

	/**
	 * generate a random long identity
	 *
	 * @param input
	 *            null
	 * @return long
	 */
	public static long randomlong() {
		Random rnd = new Random();
		long seed = System.currentTimeMillis();
		rnd.setSeed(seed);
		BigInteger result = BigInteger.probablePrime(1000, rnd);
		long res = result.longValue();
		while(Long.toString(res).length() != 20){
			result = BigInteger.probablePrime(1000, rnd);
			res = result.longValue();
		}
		if(res < 0){
			res = -res;
		}
		return res;
	}
	
	/**
	 * generate a random long identity
	 *
	 * @param input
	 *            null
	 * @return long
	 */
	public static long randomInt(int b) {
		Random rnd = new Random();
		long seed = System.currentTimeMillis();
		rnd.setSeed(seed);
		rnd.nextInt(b);
		return rnd.nextInt(b);
	}
	
	
	public static String twoStringXor(String str1, String str2) {
		byte b1[] = str1.getBytes();
		byte b2[] = str2.getBytes();
		byte longbytes[],shortbytes[];
		
		if(b1.length>=b2.length){
			longbytes = b1;
			shortbytes = b2;
		}else{
			longbytes = b2;
			shortbytes = b1;
		}
		
		byte xorstr[] = new byte[longbytes.length];
		int i = 0;
		
		for (; i < shortbytes.length; i++) {
			xorstr[i] = (byte)(shortbytes[i]^longbytes[i]);
		}
		
		for (;i<longbytes.length;i++){
			xorstr[i] = longbytes[i];
		}
		return new String(xorstr);
	}
	
	/**
	 * generate a random BigInteger that is the mod of q
	 *
	 * @param input
	 *            BigInteger q
	 * @return BigInteger
	 */
	public static BigInteger randomBig(BigInteger q) {
		Random rnd = new Random();
		long seed = System.nanoTime();
		rnd.setSeed(seed);
		BigInteger ranBig = new BigInteger(512, rnd);
		return ranBig.mod(q);
	}
	

}
