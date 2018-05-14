import java.math.BigInteger;
import java.util.ArrayList;

import Messages.PublicInfo;
import Messages.RegBack;
import Messages.RegMessage;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

public class NetManager {
	
	Pairing pairing;
	BigInteger q;
	long id;
	BigInteger dx;
	Element rx;
	Element g;
	
	public NetManager(){
		pairing = PairingFactory.getPairing("a.properties");
		this.q = pairing.getG1().getOrder();
		this.id = Utils.randomlong();
		
		this.g = pairing.getG1().newRandomElement().getImmutable();
		this.dx = Utils.randomBig(this.q);
		this.rx = this.g.duplicate().mul(this.dx);
//		System.out.println(this.pairing.getG1().getOrder().bitLength());
	}
	
	public PublicInfo getPublicInfo(){
		PublicInfo pi = new PublicInfo(this.rx, this.q, this.g,this.pairing);
		return pi;
	}
	
	public RegBack getReg(RegMessage reg){
		BigInteger kn = Utils.randomBig(this.q);
		Element rn = this.g.duplicate().mul(kn);
		Element rin = rn.duplicate().add(reg.getRu());
		BigInteger ei = Utils.hash2Big(rin.toString() + reg.getId(),this.q);
		BigInteger si = ei.multiply(kn).add(dx);
		RegBack back = new RegBack(si, rn);
		return back;
	}

}
