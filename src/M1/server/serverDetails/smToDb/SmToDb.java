package M1.server.serverDetails.smToDb;

import M2.Configuration;
import M2.Connector;
import M2.ConnectorGlue;

public class SmToDb extends Connector {
	
	public SmToDb(Configuration parentConfig) throws Exception {
		super("CmToDb",parentConfig);
		SecurizerRole securizerRole = new SecurizerRole(this);
		this.addFromRole(securizerRole);
		SecurizedRole securizedRole = new SecurizedRole(this);
		this.addToRole(securizedRole);
		
		this.setGlue(new ConnectorGlue(this.getFromRoles(),this.getToRoles()));
	}

}
