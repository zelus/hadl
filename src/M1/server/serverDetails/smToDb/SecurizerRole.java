package M1.server.serverDetails.smToDb;

import M2.Connector;
import M2.ConnectorRole;

public class SecurizerRole extends ConnectorRole {

	private Boolean securized;
	
	public SecurizerRole(Connector parent) {
		super("SecurizerRole",parent);
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
