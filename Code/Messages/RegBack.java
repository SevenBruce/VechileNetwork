package Messages;

import java.math.BigInteger;
import it.unisa.dia.gas.jpbc.Element;

public class RegBack {
	
	private BigInteger si;
	private Element rn;
	public RegBack(BigInteger si, Element rn) {
		super();
		this.si = si;
		this.rn = rn;
	}
	public BigInteger getSi() {
		return si;
	}
	public void setSi(BigInteger si) {
		this.si = si;
	}
	public Element getRn() {
		return rn;
	}
	public void setRn(Element rn) {
		this.rn = rn;
	}
	
	

}
