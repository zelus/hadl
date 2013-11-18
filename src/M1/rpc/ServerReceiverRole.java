package M1.rpc;

import M2.Connector;
import M2.ConnectorRole;

public class ServerReceiverRole extends ConnectorRole {

	private String message;
	
	public ServerReceiverRole(Connector parent) {
		super("ServerReceiverRole",parent);
		this.message = new String();
	}
	
	@Override
	public void setValue(Object object) {
		this.message = (String)object;
	}

	@Override
	public String getValue() {
		return this.message;
	}

}
