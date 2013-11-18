package M1.server.serverDetails.securityCheck;

import M2.Component;
import M2.ComponentPort;

public class SecurityAuthPort extends ComponentPort {

	private Boolean authenticated;
	
	public SecurityAuthPort(Component parent) {
		super("SecurityAuthPort",parent);
		this.authenticated = false;
	}
	
	@Override
	public void setValue(Object object) {
		this.authenticated = (Boolean)object;
	}

	@Override
	public Boolean getValue() {
		return this.authenticated;
	}

}
