package Messages;

import it.unisa.dia.gas.jpbc.Element;

public class Message2 {
		Element rjn;
		Element rj2;
		long idj;
		String hj;
		public Message2(Element rjn, Element rj2, long idj, String hj) {
			super();
			this.rjn = rjn;
			this.rj2 = rj2;
			this.idj = idj;
			this.hj = hj;
		}
		public Element getRjn() {
			return rjn;
		}
		public void setRjn(Element rjn) {
			this.rjn = rjn;
		}
		public Element getRj2() {
			return rj2;
		}
		public void setRj2(Element rj2) {
			this.rj2 = rj2;
		}
		public long getIdj() {
			return idj;
		}
		public void setIdj(long idj) {
			this.idj = idj;
		}
		public String getHj() {
			return hj;
		}
		public void setHj(String hj) {
			this.hj = hj;
		}
}
