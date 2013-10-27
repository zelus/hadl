package M1.overview;

import M2.Component;
import M2.Configuration;

public class ServerComponent extends Component {

	public ServerComponent(Configuration parent) throws Exception {
		super("Server", 0, parent);
		
		ServerReceiveRequestPort serverReceiveRequestPort = new ServerReceiveRequestPort(this);
		this.addReqPort(serverReceiveRequestPort);
		
		this.setSubConfig(new ServerDetailsConfiguration(this));
		System.out.println("test");
	}

}
