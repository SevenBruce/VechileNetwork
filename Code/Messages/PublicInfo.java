package Messages;

import java.math.BigInteger;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;

public class PublicInfo {
	
	private Element rx;
	private BigInteger q;
	private Element g;
	private Pairing pairing;
	public PublicInfo(Element rx, BigInteger q, Element g,Pairing pairing) {
		super();
		this.rx = rx;
		this.q = q;
		this.g = g;
		this.pairing = pairing;
	}
	public Element getRx() {
		return rx;
	}
	public void setRx(Element rx) {
		this.rx = rx;
	}
	public BigInteger getQ() {
		return q;
	}
	public void setQ(BigInteger q) {
		this.q = q;
	}
	public Element getG() {
		return g;
	}
	public void setG(Element g) {
		this.g = g;
	}
	public Pairing getPairing() {
		return pairing;
	}
	public void setPairing(Pairing pairing) {
		this.pairing = pairing;
	}
	

}
