package M1.server.serverDetails.databaseManager;

import M2.Component;
import M2.Configuration;

public class DatabaseManager extends Component {

	public DatabaseManager(Configuration parentConfig) {
		super("DatabaseManager",parentConfig);
		QueryIntegrationPort queryIntegrationPort = new QueryIntegrationPort(this);
		this.addProvPort(queryIntegrationPort);
		SecurityManagementPort securityManagementPort = new SecurityManagementPort(this);
		this.addReqPort(securityManagementPort);
	}
	
}
