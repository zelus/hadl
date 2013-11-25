package M1.server.serverDetails.connectionManager;

import M2.Component;
import M2.ComponentPort;
import M2.ComponentService;

public class DelegateCheckingService extends ComponentService {

	public DelegateCheckingService(Component parent, ComponentPort[] reqPorts, ComponentPort[] provPorts) {
		super("DelegateCheckingService",parent, reqPorts, provPorts);
	}
	
	@Override
	protected Object run() {
		this.provPorts.get(0).updateFrom(this.reqPorts.get(0));
		return null;
	}

}
