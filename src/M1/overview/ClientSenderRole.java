package M1.overview;

import M2.Connector;
import M2.ConnectorRole;

public class ClientSenderRole extends ConnectorRole {

	private String senderBuffer;
	
	public ClientSenderRole(Connector parent) {
		super("ClientSenderRole", parent);
		senderBuffer = new String();
	}

	@Override
	public void setValue(Object obj) {
		senderBuffer = (String)obj;
	}

	@Override
	public String getValue() {
		return senderBuffer;
	}

}
