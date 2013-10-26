package M1.overview;

import M2.Connector;
import M2.ConnectorRole;

public class ClientReceiverRole extends ConnectorRole {

	String receiverBuffer;
	
	public ClientReceiverRole(Connector parent) {
		super("ClientReceiverRole", parent);
		receiverBuffer = new String();
	}

	@Override
	public void setValue(Object obj) {
		receiverBuffer = (String)obj;
	}

	@Override
	public String getValue() {
		return receiverBuffer;
	}

}
