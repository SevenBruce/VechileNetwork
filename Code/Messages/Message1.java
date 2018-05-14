package Messages;

import it.unisa.dia.gas.jpbc.Element;

public class Message1 {
	
	Element ri1;
	Element rin;
	long idi;
	String hi;
	public Message1(Element rin, Element ri1, long idi, String hi) {
		super();
		this.rin = rin;
		this.ri1 = ri1;
		this.idi = idi;
		this.hi = hi;
	}
	public Element getRi1() {
		return ri1;
	}
	public void setRi1(Element ri1) {
		this.ri1 = ri1;
	}
	public Element getRin() {
		return rin;
	}
	public void setRin(Element rin) {
		this.rin = rin;
	}
	public long getIdi() {
		return idi;
	}
	public void setIdi(long idi) {
		this.idi = idi;
	}
	public String getHi() {
		return hi;
	}
	public void setHi(String hi) {
		this.hi = hi;
	}
}
