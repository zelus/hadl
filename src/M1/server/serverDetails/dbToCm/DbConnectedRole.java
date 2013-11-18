package M1.server.serverDetails.dbToCm;

import M2.Connector;
import M2.ConnectorRole;

public class DbConnectedRole extends ConnectorRole {

	private String queryResult;
	
	public DbConnectedRole(Connector parent) {
		super("DbConnectedRole",parent);
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
