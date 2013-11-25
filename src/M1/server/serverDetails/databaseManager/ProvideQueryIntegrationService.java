package M1.server.serverDetails.databaseManager;

import M2.Component;
import M2.ComponentPort;
import M2.ComponentService;

public class ProvideQueryIntegrationService extends ComponentService {

	public ProvideQueryIntegrationService(Component parent, ComponentPort[] reqPorts, ComponentPort[] provPorts) {
		super("ProvideQueryIntegrationService",parent,reqPorts,provPorts);
	}
	
	@Override
	protected Object run() {
		this.provPorts.get(0).setValue("MBT is everywhere !");
		return null;
	}

}
