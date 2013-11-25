package M1.server.serverDetails.connectionManager;

import M2.Component;
import M2.ComponentPort;
import M2.Configuration;

public class ConnectionManager extends Component {

	public ConnectionManager(Configuration parentConfig) {
		super("ConnectionManager", parentConfig);
		ReadSocketPort readSocketPort = new ReadSocketPort(this);
		this.addReqPort(readSocketPort);
		WriteSocketPort writeSocketPort = new WriteSocketPort(this);
		this.addProvPort(writeSocketPort);
		SecurityCheckPort securityCheckPort = new SecurityCheckPort(this);
		this.addReqPort(securityCheckPort);
		DbQueryPort dbQueryPort = new DbQueryPort(this);
		this.addReqPort(dbQueryPort);
		NameToCheckPort nameToCheck = new NameToCheckPort(this);
		this.addProvPort(nameToCheck);
		
		// create component services
		DelegateCheckingService delegateCheckingService = new DelegateCheckingService(this, new ComponentPort[]{readSocketPort}, new ComponentPort[]{nameToCheck});
		this.addProvService(delegateCheckingService);
	}

}
