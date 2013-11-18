package M1.server.serverDetails.dbToCm;

import M2.Configuration;
import M2.Connector;

public class DbToCm extends Connector {

	public DbToCm(Configuration parentConfig) {
		super("DbToCm",parentConfig);
		DbConnectorRole dbConnectorRole = new DbConnectorRole(this);
		this.addFromRole(dbConnectorRole);
		DbConnectedRole dbConnectedRole = new DbConnectedRole(this);
		this.addToRole(dbConnectedRole);
	}
	
}
