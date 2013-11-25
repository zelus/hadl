package M1.server.serverDetails.securityCheck;

import M2.Component;
import M2.ComponentPort;
import M2.ComponentService;

public class ProvideAuthentificationService extends ComponentService {

	public ProvideAuthentificationService(Component parent, ComponentPort[] reqPorts, ComponentPort[] provPorts) {
		super("ProvideAuthentificationService",parent,reqPorts,provPorts);
	}
	
	@Override
	protected Object run() {
		// default value, authenticate all requests
		this.provPorts.get(0).setValue(true);
		return null;
	}

}
