package M1.server.serverDetails.connectionManager;

import M2.Component;
import M2.ComponentPort;

public class SecurityCheckPort extends ComponentPort {

	private Boolean checked;
	
	public SecurityCheckPort(Component parent) {
		super("SecurityCheckPort",parent);
		this.checked = false;
	}
	
	@Override
	public void setValue(Object object) {
		this.checked = (Boolean)object;
	}

	@Override
	public Boolean getValue() {
		return this.checked;
	}

}
