package M1.overview;

import M2.Configuration;
import M2.Connector;

public class RpcConnector extends Connector {

	public RpcConnector(Configuration parent) {
		super("RPC", 0, parent);
		
		ClientSenderRole clientSenderRole = new ClientSenderRole(this);
		this.addFromRole(clientSenderRole);
		
		ClientReceiverRole clientReceiverRole = new ClientReceiverRole(this);
		this.addToRole(clientReceiverRole);
	}

}
