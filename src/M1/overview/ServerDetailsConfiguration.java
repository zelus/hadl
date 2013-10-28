package M1.overview;

import M2.Binding;
import M2.Component;
import M2.Configuration;

public class ServerDetailsConfiguration extends Configuration {

	public ServerDetailsConfiguration(Component parent) throws Exception {
		super("ServerDetailsConfiguration", parent);
		ServerDetailsReceivePort sdReceivePort = new ServerDetailsReceivePort(this);
		this.addReqPort(sdReceivePort);
		this.addBinding(new Binding("ReceiveRequestToReceive",parent.getReqPort("ServerReceiveRequestPort"),sdReceivePort));
	}

}
