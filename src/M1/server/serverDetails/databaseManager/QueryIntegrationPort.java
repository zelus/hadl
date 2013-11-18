package M1.server.serverDetails.databaseManager;

import M2.Component;
import M2.ComponentPort;

public class QueryIntegrationPort extends ComponentPort {

	private String queryResult;
	
	public QueryIntegrationPort(Component parent) {
		super("QueryIntegrationPort",parent);
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
