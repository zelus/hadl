package M1.overview;

import M1.client.ClientComponent;
import M1.rpc.RpcConnector;
import M1.server.ServerComponent;
import M2.Attachment;
import M2.Configuration;

public class ClientServerConfiguration extends Configuration {

	public ClientServerConfiguration() throws Exception {
		super("ClientServerConfiguration", null);
		ClientComponent clientComponent = new ClientComponent(this);
		this.addComponent(clientComponent);
		ServerComponent serverComponent = new ServerComponent(this);
		this.addComponent(serverComponent);
		RpcConnector rpcConnector = new RpcConnector(this);
		this.addConnector(rpcConnector);
		
		this.addAttachment(new Attachment("ClientToRpc", clientComponent.getProvPort("SendRequestPort"), rpcConnector.getFromRole("ClientSenderRole")));
		this.addAttachment(new Attachment("RpcToServer", rpcConnector.getToRole("ClientReceiverRole"), serverComponent.getReqPort("ServerReceiveRequestPort")));
		this.addAttachment(new Attachment("RpcToClient", rpcConnector.getToRole("ServerReceiverRole"), clientComponent.getReqPort("ReceiveRequestPort")));
		this.addAttachment(new Attachment("ServerToRpc", serverComponent.getProvPort("ServerSendRequestPort"), rpcConnector.getFromRole("ServerSenderRole")));
		
		this.addProperty(new OneServerAttachment());
	}

}
