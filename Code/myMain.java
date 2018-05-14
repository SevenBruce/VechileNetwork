import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

import Messages.Message1;
import Messages.Message2;
import Messages.PublicInfo;
import Messages.RegBack;
import Messages.RegMessage;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;

public class myMain {

	static Out out = new Out("running.times");
	static final int[] times = { 25, 50, 75, 100, 125, 150 };
//	static int count = 1000;
	static int numbers = 150;
	static int countF = 1000000;
//	static final int[] times = { 5, 5, 7, 10 };
	static int count = 1;
//	static int numbers = 10;
//	static int countF = 100;
	static BigInteger q;
	
	private static long sl;
	private static long el;

	public static void main(String[] args) throws IOException {

		NetManager net = new NetManager();
		PublicInfo pi = net.getPublicInfo();
		Vehicle vj = new Vehicle(pi);
		
		RegMessage reg = vj.getRegMessage();
		RegBack back = net.getReg(reg);
		vj.getRegBack(back);
				

		double time = 0;
		System.out.println("Vechile array initialization");
		Vehicle[] vi = new Vehicle[numbers];
		for (int i = 0; i < numbers; i++) {
			vi[i] = new Vehicle(pi);
		}
				
		for (int i : times) {
			System.out.println("reg " + i + " times : ");
			for (int k = 0; k < count; k++) {
				time = vehicleRegTime(vi, net, i);
			}
			System.out.println(time/count);
			out.println("auth " + i + " times : " + time/count);
		}
		
		for (int i : times) {
			System.out.println("auth " + i + " times : ");
			for (int k = 0; k < count; k++) {
				time = vehicleAuthTime(vi, vj, i);
			}
			System.out.println(time/count);
			out.println("auth " + i + " times : " + time/count);
		}

		
		//calculating the operation times
//		Pairing pairing = net.getPublicInfo().getPairing();
//		q = pairing.getG1().getOrder();
//		G1MulTime(pairing);
//		G1AddTime(pairing);
//		hashTimes(pairing);
		
		out.close();
//		Runtime.getRuntime().exec("shutdown -s");
	}

	private static double vehicleRegTime(Vehicle[] vi, NetManager net, int array) {
		double total = 0;

		sl = System.nanoTime();
		for (int i = 0; i < array; i++) {
			RegMessage reg = vi[i].getRegMessage();
			RegBack back = net.getReg(reg);
			vi[i].getRegBack(back);
		}
		el = System.nanoTime();
		total = total + (el - sl);
		total = total / 1000000;
		return total;
	}
	
	
	private static double vehicleAuthTime(Vehicle[] vi, Vehicle vj, int array) throws IOException {
		double total = 0;
		sl = System.nanoTime();
		for (int i = 0; i < array; i++) {
			Message1 m1 = vi[i].genM1();
			Message2 m2 = vj.getM1(m1);
			vi[i].getM2(m2);
		}
		el = System.nanoTime();
		total = total + (el - sl);
		total = total / 1000000;
		return total;
	}
	
	private static void hashTimes(Pairing pairing) {
		try {
			Hash2ZrTime(pairing);
			SHA256Time();
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void Hash2ZrTime(Pairing pairing) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		String originalString = "9810847176601250040732131758618403807280467641315754149066787497228603585337261175294069350502269140056356772596227";
		double total = 0;
		int k = originalString.length();

		for (int i = 0; i < countF; i++) {
			total = total + Hash2ZrTime(pairing, originalString);
			String a = "" + Utils.randomInt(10);
			String b = "" + Utils.randomInt(10);
			originalString.replace(a, b);
		}
		total = total / 1000000;
		System.out.println("Hash2ZrTime time is " + total / countF);
		out.println("Hash2ZrTime time is " + total / countF);
	}


	private static void SHA256Time() throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		String originalString = "6285369419593790154527882701723616215700819147796849568352951792525528084536505650082386569658891477104234842836067417038302062368613224883244562852558979788634466430833364528035462411043474785589027518879546851947414104980211245359069608637866717645700695079328704701970645721669891019152081866987281578844";
		double total = 0;
		for (int i = 0; i < countF; i++) {
			sl = System.nanoTime();
			Utils.sha256(originalString);
			el = System.nanoTime();
			total = total + (el - sl);
		}
		total = total / 1000000;
		System.out.println("General Hash time is " + total / countF);
		out.println("General Hash time is " + total / countF);
	}

	private static void G1AddTime(Pairing pairing) {
		// this function is used to estimate the time needed to
		// conduct an multiplication operation in group G1
		double total = 0;

		for (int i = 0; i < countF; i++) {
			total = total + calculateG1AddTime(pairing);
		}
		total = total / 1000000;
		System.out.println("G1Add time is " + total / countF);
		out.println("G1Add time is " + total / countF);
	}

	private static long calculateG1AddTime(Pairing pairing) {
		Element G_1 = pairing.getG1().newRandomElement().getImmutable();
		Element G_1_p = pairing.getG1().newRandomElement().getImmutable();;

		sl = System.nanoTime();
		G_1.add(G_1_p);
		el = System.nanoTime();
		return (el - sl);

	}

	private static void G1MulTime(Pairing pairing) {
		// this function is used to estimate the time needed to
		// conduct an multiplication operation in group G1
		double total = 0;

		for (int i = 0; i < countF; i++) {
			total = total + calculateG1MulTime(pairing);
		}
		total = total / 1000000;
		System.out.println("G1Mul time is " + total / countF);
		out.println("G1Mul time is " + total / countF);
	}

	private static long calculateG1MulTime(Pairing pairing) {
		Element G_1 = pairing.getG1().newRandomElement().getImmutable();
		BigInteger z = Utils.randomBig(q);

		sl = System.nanoTime();
		G_1.mul(z);
		el = System.nanoTime();
		return (el - sl);
	}

	private static long Hash2ZrTime(Pairing pairing, String originalString)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		sl = System.nanoTime();
		BigInteger bi = new BigInteger(originalString.getBytes(StandardCharsets.UTF_8));
		bi = bi.mod(q);
		el = System.nanoTime();
		return (el - sl);
	}

	
	

}
