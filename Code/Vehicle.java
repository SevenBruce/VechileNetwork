import java.math.BigInteger;

import Messages.Message1;
import Messages.Message2;
import Messages.PublicInfo;
import Messages.RegBack;
import Messages.RegMessage;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;

public class Vehicle {

	// private information id, private key di, public key Ri;
	private long id;
	private BigInteger di;
	private Element ri;

	// registration phase
	private BigInteger ku;
	private Element ru;
	private Element rin;
	// private Element rie;

	// public information
	private BigInteger q;
	private Element rx;
	private Element g;
	private Pairing pairing;

	// Message 1
	private BigInteger ki1;
	private Element ri2;

	private String skij;

	public Vehicle(PublicInfo pi) {
		super();
		this.id = Utils.randomlong();
		this.rx = pi.getRx();
		this.g = pi.getG();
		this.q = pi.getPairing().getG1().getOrder();
		this.pairing = pi.getPairing();
	}

	public RegMessage getRegMessage() {
		this.ku = Utils.randomBig(this.q);
		ru = this.g.duplicate().mul(this.ku);
		RegMessage reg = new RegMessage(this.id, this.ru);
		return reg;
	}

	public void getRegBack(RegBack back) {
		this.rin = this.ru.duplicate().add(back.getRn());
		BigInteger ei = Utils.hash2Big(rin.toString() + this.id, this.q);
		this.di = ei.multiply(ku).add(back.getSi()).mod(this.q);
		
		this.ri = this.g.duplicate().mul(this.di);
	}

	public Message1 genM1() {
		this.ki1 = Utils.randomBig(q);
		Element ri1 = this.g.duplicate().mul(this.ki1);
		
		StringBuilder sb = new StringBuilder("");
		sb.append(this.rin.toString());
		sb.append(this.id);
		sb.append(ri1.toString());
		String hi = Utils.sha256(sb.toString());
		
		Message1 m1 = new Message1(this.rin, ri1, this.id, hi);
		return m1;
	}

	public Message2 getM1(Message1 m1) {
		StringBuilder sb = new StringBuilder("");
		sb.append(m1.getRin().toString());
		sb.append(m1.getIdi());
		sb.append(m1.getRi1().toString());
		String hi = Utils.sha256(sb.toString());
		if (!hi.equals(m1.getHi())) {
			System.out.println("hi not equalize in m1 at Uj");
			System.exit(1);
		} else {
			// System.out.println("equal");
		}
		
		BigInteger ei = Utils.hash2Big(m1.getRin().toString() + m1.getIdi(), this.q);
		Element ri = m1.getRin().duplicate().mul(ei).add(rx);

		BigInteger kj1 = Utils.randomBig(this.q);
		Element sk = m1.getRi1().duplicate().mul(kj1);
		this.skij = Utils.sha256(sk.toString());
//		System.out.println("Uj accept the shared key : " + this.skij);
		
		
		Element rj1 = ri.duplicate().mul(this.di);
		Element rj2 = ri.duplicate().mul(kj1);
		
		StringBuilder sbhj = new StringBuilder("");
		sbhj.append(this.rin.toString());
		sbhj.append(this.id);
		sbhj.append(rj1.toString());
		sbhj.append(rj2.toString());
		sbhj.append(this.skij);
		String hj = Utils.sha256(sbhj.toString());

		Message2 m2 = new Message2(this.rin, rj2, this.id, hj);
		return m2;
	}

	public void getM2(Message2 m2){
		BigInteger ei = Utils.hash2Big(m2.getRjn().toString() + m2.getIdj(), this.q);
		Element rj = m2.getRjn().duplicate().mul(ei).add(rx);
		BigInteger diInverse = this.di.modInverse(this.q);
		Element sk = m2.getRj2().duplicate().mul(diInverse.multiply(this.ki1));
		
		this.skij = Utils.sha256(sk.toString());
		Element rj1 = rj.duplicate().mul(this.di);

		StringBuilder sbhj = new StringBuilder("");
		sbhj.append(m2.getRjn().toString());
		sbhj.append(m2.getIdj());
		sbhj.append(rj1.toString());//
		sbhj.append(m2.getRj2().toString());
		sbhj.append(this.skij);//
		String hj = Utils.sha256(sbhj.toString());
		if (!hj.equals(m2.getHj())) {
			System.out.println("hj not equalize in m2 at Ui");
			System.exit(1);
		} else {
//			 System.out.println("Ui accept the shared key : " + this.skij);
		}
	}

}
