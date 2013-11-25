package M1.server.serverDetails.connectionManager;

import M2.Component;
import M2.ComponentPort;
import M2.ComponentService;

public class ComputeResponseService extends ComponentService {

	public ComputeResponseService(Component parent, ComponentPort[] reqPorts, ComponentPort[] provPorts) {
		super("ComputeResponseService",parent,reqPorts,provPorts);
	}
	
	@Override
	protected Object run() {
		if(this.reqPorts.get(0).getValue().equals(true)) {
			this.provPorts.get(0).setValue(this.reqPorts.get(1).getValue());
		}
		else {
			this.provPorts.get(0).setValue("A problem occured, MBT doesn't recognize you !");
		}
		return null;
	}

}
