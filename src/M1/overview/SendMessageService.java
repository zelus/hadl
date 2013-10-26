package M1.overview;

import M2.Component;
import M2.ComponentPort;
import M2.ComponentService;
import M2.exceptions.ComponentServiceException;

public class SendMessageService extends ComponentService {

	public SendMessageService(Component parent,
			ComponentPort[] reqPorts, ComponentPort[] provPorts)
			throws ComponentServiceException {
		super("SendMessageService", parent, reqPorts, provPorts);
	}

	@Override
	protected Object run() {
		this.provPorts.get(0).setValue(this.reqPorts.get(0).getValue());
		return null;
	}

}
