package M1.server.serverDetails;

import M2.Configuration;
import M2.ConfigurationPort;

public class ServerDetailsSendPort extends ConfigurationPort {

	private String sendBuffer;
	
	public ServerDetailsSendPort(Configuration parent) {
		super("ServerDetailsSendPort",parent);
		this.sendBuffer = new String();
	}
	
	@Override
	public void setValue(Object object) {
		this.sendBuffer = (String)object;
	}

	@Override
	public String getValue() {
		return this.sendBuffer;
	}

}
