package M1.overview;

import M2.Binding;
import M2.Component;
import M2.Configuration;
import M2.exceptions.ConfigurationException;

public class ServerDetailsConfiguration extends Configuration {

	public ServerDetailsConfiguration() throws ConfigurationException {
		super("ServerDetailsConfiguration", 0, ServerDetailsConfiguration.COMPONENT_TYPE);
		ServerDetailsReceivePort sdReceivePort = new ServerDetailsReceivePort(this);
		this.addReqPort(sdReceivePort);
		this.addBinding(new Binding("ReceiveRequestToReceive",((Component)parent).getReqPort("ReceiveRequestPort"),sdReceivePort));
	}

}
