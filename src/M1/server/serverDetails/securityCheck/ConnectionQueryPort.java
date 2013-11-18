package M1.server.serverDetails.securityCheck;

import M2.Component;
import M2.ComponentPort;

public class ConnectionQueryPort extends ComponentPort {

	private String queryResult;
	
	public ConnectionQueryPort(Component parent) {
		super("ConnectionQueryPort",parent);
		this.queryResult = new String();
	}
	
	@Override
	public void setValue(Object object) {
		this.queryResult = (String)object;

	}

	@Override
	public String getValue() {
		return this.queryResult;
	}

}
