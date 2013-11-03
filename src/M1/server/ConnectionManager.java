package M1.server;

import M2.Component;
import M2.Configuration;

public class ConnectionManager extends Component {

	public ConnectionManager(Configuration parentConfig) {
		super("ConnectionManager", parentConfig);
		ReadSocketPort readSocketPort = new ReadSocketPort(this);
		this.addReqPort(readSocketPort);
	}

}
