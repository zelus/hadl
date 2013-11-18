package M1.server.serverDetails.smToDb;

import M2.Configuration;
import M2.Connector;

public class SmToDb extends Connector {
	
	public SmToDb(Configuration parentConfig) {
		super("CmToDb",parentConfig);
		SecurizerRole securizerRole = new SecurizerRole(this);
		this.addFromRole(securizerRole);
		SecurizedRole securizedRole = new SecurizedRole(this);
		this.addToRole(securizedRole);
	}

}
