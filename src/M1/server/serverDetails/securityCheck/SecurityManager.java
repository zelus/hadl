package M1.server.serverDetails.securityCheck;

import M2.Component;
import M2.ComponentPort;
import M2.Configuration;

public class SecurityManager extends Component {

	public SecurityManager(Configuration parentConfig) {
		super("SecurityManager",parentConfig);
		SecurityAuthPort securityAuthPort = new SecurityAuthPort(this);
		this.addProvPort(securityAuthPort);
		ConnectionQueryPort connectionQueryPort = new ConnectionQueryPort(this);
		this.addReqPort(connectionQueryPort);
		
		// create component services
		ProvideAuthentificationService provideAuthService = new ProvideAuthentificationService(this, new ComponentPort[]{connectionQueryPort}, new ComponentPort[]{securityAuthPort});
		this.addProvService(provideAuthService);
	}
}
