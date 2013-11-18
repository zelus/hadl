package M1.server.serverDetails.connectionManager;

import M2.Component;
import M2.ComponentPort;

public class DbQueryPort extends ComponentPort {

	private String queryResult;
	
	public DbQueryPort(Component parent) {
		super("DbQueryPort",parent);
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
