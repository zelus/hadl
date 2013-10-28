package M1.server;

import M2.Component;
import M2.Configuration;

public class ConnectionManager extends Component {

	public ConnectionManager(int level, Configuration parentConfig) {
		super("ConnectionManager", level, parentConfig);
		ReadSocketPort readSocketPort = new ReadSocketPort(this);
		this.addReqPort(readSocketPort);
	}

}
