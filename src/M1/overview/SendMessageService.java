package M1.overview;

import java.util.ArrayList;

import M2.Component;
import M2.ComponentPort;
import M2.ComponentService;
import M2.exceptions.ComponentServiceException;

public class SendMessageService extends ComponentService {

	public SendMessageService(Component parent,
			ArrayList<ComponentPort> usedPorts)
			throws ComponentServiceException {
		super("SendMessageService", parent, usedPorts);
	}

	@Override
	protected Object run(Object input) {
		return null;
	}

}
