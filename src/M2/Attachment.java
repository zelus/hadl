package M2;

public class Attachment extends Link{

	private Interface provInterface;
	private Interface reqInterface;
	
	public Attachment(String name, Interface provInterface, Interface reqInterface) {
		super(name);
		this.provInterface = provInterface;
		this.reqInterface = reqInterface;		
	}
	
	public Interface getProvInterface() {
		return this.provInterface;
	}
	
	public Interface getReqInterface() {
		return this.reqInterface;
	}

	
	
}
