package M1.server.serverDetails.cmToSm;

import M2.Connector;
import M2.ConnectorRole;

public class AuthenticatedRole extends ConnectorRole {

	private Boolean authenticated;
	
	public AuthenticatedRole(Connector parent) {
		super("AuthenticatedRole",parent);
		this.authenticated = false;
	}
	
	@Override
	public void setValue(Object object) {
		this.authenticated = (Boolean)object;
	}

	@Override
	public Object getValue() {
		return this.authenticated;
	}

}
