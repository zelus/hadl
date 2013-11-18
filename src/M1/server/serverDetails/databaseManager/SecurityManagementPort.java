package M1.server.serverDetails.databaseManager;

import M2.Component;
import M2.ComponentPort;

public class SecurityManagementPort extends ComponentPort {

	private Boolean securized;
	
	public SecurityManagementPort(Component parent) {
		super("SecurityManagementPort",parent);
		this.securized = false;
	}
	
	@Override
	public void setValue(Object object) {
		this.securized = (Boolean)object;
	}

	@Override
	public Boolean getValue() {
		return this.securized;
	}

}
