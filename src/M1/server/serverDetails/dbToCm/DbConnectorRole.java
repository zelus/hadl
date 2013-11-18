package M1.server.serverDetails.dbToCm;

import M2.Connector;
import M2.ConnectorRole;

public class DbConnectorRole extends ConnectorRole {

	private String queryResult;
	
	public DbConnectorRole(Connector parent) {
		super("DbConnectorRole",parent);
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
