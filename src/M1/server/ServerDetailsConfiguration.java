package M1.server;

import M2.Binding;
import M2.Component;
import M2.Configuration;

public class ServerDetailsConfiguration extends Configuration {

	public ServerDetailsConfiguration(Component parent) throws Exception {
		super("ServerDetailsConfiguration", parent);
		ConnectionManager connectionManager = new ConnectionManager(this);
		//this.addComponent(connectionManager);
		ServerDetailsReceivePort sdReceivePort = new ServerDetailsReceivePort(this);
		this.addReqPort(sdReceivePort);
		this.addBinding(new Binding("ReceiveRequestToReceive",parent.getReqPort("ServerReceiveRequestPort"),sdReceivePort));
		this.addBinding(new Binding("ReceiveToReadSocket",connectionManager.getReqPort("ReadSocketPort"),sdReceivePort));
	}

}
