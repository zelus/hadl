package M1.overview;

import java.util.ArrayList;

import M2.Component;
import M2.ComponentPort;
import M2.exceptions.ComponentServiceException;

public class ClientComponent extends Component {

	public ClientComponent() throws ComponentServiceException {
		super("Client", 0);
		
		SendRequestPort sendRequestPort = new SendRequestPort(this);
		this.addProvPort(sendRequestPort);

		ReceiveRequestPort receiveRequestPort = new ReceiveRequestPort(this);
		this.addReqPort(receiveRequestPort);
		
		// TODO update signature to allow easy service creation
		// and maybe differenciate req to prov 
		ArrayList<ComponentPort> sendMessagePorts = new ArrayList<ComponentPort>();
		sendMessagePorts.add(sendRequestPort);
		sendMessagePorts.add(receiveRequestPort);
		
		this.addProvService(new SendMessageService(this,sendMessagePorts));
	}

}
