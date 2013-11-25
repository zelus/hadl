package M1.server.serverDetails.dbToCm;

import M2.Configuration;
import M2.Connector;
import M2.ConnectorGlue;

public class DbToCm extends Connector {

	public DbToCm(Configuration parentConfig) throws Exception {
		super("DbToCm",parentConfig);
		DbConnectorRole dbConnectorRole = new DbConnectorRole(this);
		this.addFromRole(dbConnectorRole);
		DbConnectedRole dbConnectedRole = new DbConnectedRole(this);
		this.addToRole(dbConnectedRole);
		
		this.setGlue(new ConnectorGlue(this.getFromRoles(), this.getToRoles()));
	}
	
}
