package M1.overview;

import M2.Attachment;
import M2.Configuration;

public class ClientServerConfiguration extends Configuration {

	public ClientServerConfiguration() throws Exception {
		super("ClientServerConfiguration", 0, ClientServerConfiguration.COMPONENT_TYPE);
		ClientComponent clientComponent = new ClientComponent(this);
		this.addComponent(clientComponent);
		ServerComponent serverComponent = new ServerComponent(this);
		this.addComponent(serverComponent);
		RpcConnector rpcConnector = new RpcConnector(this);
		this.addConnector(rpcConnector);
		this.addAttachment(new Attachment("ClientToRpc", clientComponent.getProvPort("SendRequestPort"), rpcConnector.getFromRole("ClientSenderRole")));
		this.addAttachment(new Attachment("RpcToServer", rpcConnector.getToRole("ClientReceiverRole"), serverComponent.getReqPort("ServerReceiveRequestPort")));
	}

}
