package M1.server;

import M1.server.serverDetails.ServerDetailsConfiguration;
import M2.Component;
import M2.Configuration;

public class ServerComponent extends Component {

	public ServerComponent(Configuration parent) throws Exception {
		super("Server", parent);
		
		ServerReceiveRequestPort serverReceiveRequestPort = new ServerReceiveRequestPort(this);
		this.addReqPort(serverReceiveRequestPort);
		ServerSendRequestPort serverSendRequestPort = new ServerSendRequestPort(this);
		this.addProvPort(serverSendRequestPort);
		
		//this.setSubConfig(new ServerDetailsConfiguration(this));
		new ServerDetailsConfiguration(this);
	}

}
