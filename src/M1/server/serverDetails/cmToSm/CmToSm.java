package M1.server.serverDetails.cmToSm;

import M2.Configuration;
import M2.Connector;

public class CmToSm extends Connector {

	public CmToSm(Configuration parentConfig) {
		super("CmToSm",parentConfig);
		AuthenticatorRole authenticatorRole = new AuthenticatorRole(this);
		this.addFromRole(authenticatorRole);
		ConnecterRole connecterRole = new ConnecterRole(this);
		this.addFromRole(connecterRole);
		AuthenticatedRole authenticatedRole = new AuthenticatedRole(this);
		this.addToRole(authenticatedRole);
		ConnectedRole connectedRole = new ConnectedRole(this);
		this.addToRole(connectedRole);
	}
	
}