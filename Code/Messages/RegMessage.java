package Messages;

import it.unisa.dia.gas.jpbc.Element;

public class RegMessage {
	
	private long id;
	private Element ru;
	
	public RegMessage(long id, Element ru) {
		super();
		this.id = id;
		this.ru = ru;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Element getRu() {
		return ru;
	}
	public void setRu(Element ru) {
		this.ru = ru;
	}
}
