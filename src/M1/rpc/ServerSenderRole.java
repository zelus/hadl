package M1.rpc;

import M2.Connector;
import M2.ConnectorRole;

public class ServerSenderRole extends ConnectorRole {
	
	private String message;
	
	public ServerSenderRole(Connector parent) {
		super("ServerSenderRole",parent);
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
