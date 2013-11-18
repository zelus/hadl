package M1.server.serverDetails.cmToSm;

import M2.Connector;
import M2.ConnectorRole;

public class AuthenticatorRole extends ConnectorRole {

	private Boolean authenticated;
	
	public AuthenticatorRole(Connector parent) {
		super("AuthenticatorRole",parent);
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
