package M1.rpc;

import M2.Configuration;
import M2.Connector;
import M2.ConnectorGlue;

public class RpcConnector extends Connector {

	public RpcConnector(Configuration parent) throws Exception {
		super("RPC", parent);
		
		ClientSenderRole clientSenderRole = new ClientSenderRole(this);
		this.addFromRole(clientSenderRole);
		
		ClientReceiverRole clientReceiverRole = new ClientReceiverRole(this);
		this.addToRole(clientReceiverRole);
		
		ServerSenderRole serverSenderRole = new ServerSenderRole(this);
		this.addFromRole(serverSenderRole);
		
		ServerReceiverRole serverReceiverRole = new ServerReceiverRole(this);
		this.addToRole(serverReceiverRole);
		
		this.setGlue(new ConnectorGlue(this.getFromRoles(), this.getToRoles()));
		//this.setGlue(new RpcConnectorGlue(clientSenderRole, clientReceiverRole));
	}

}
