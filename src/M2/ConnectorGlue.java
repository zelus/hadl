package M2;

import M2.exceptions.GlueException;

public class ConnectorGlue {
	
	protected ConnectorRole fromRole;
	protected ConnectorRole toRole;
	
	public ConnectorGlue(ConnectorRole fromRole, ConnectorRole toRole) throws GlueException {
		/*
		 *  TODO handle multiple from/to roles
		 */
		if(fromRole == null || toRole == null) {
			throw new GlueException("Cannot create glue from null");
		}
		Connector fromParent = fromRole.getParent();
		Connector toParent = toRole.getParent();
		if(fromParent.equals(toParent)) {
			if(fromParent.getFromRole(fromRole.getName()) != null &&
					toParent.getToRole(toRole.getName()) != null) {
				this.fromRole = fromRole;
				this.toRole = toRole;
			}
			else {
				throw new GlueException("Cannot create glue : please ensure that one of the given role is a from role and the other is a to role");
			}
		}
		else {
			throw new GlueException("Cannot create glue : please ensure that from and to role parents are the same");
		}
	}
	
	public final void callGlue() {
		System.out.println("[HADL-RUNTIME] Starting glue ...");
		this.runGlue();
		this.toRole.flush();
	}
	
	/**
	 * User-defined run method.
	 * Default glue operation is identity, if an other behavior is wanted
	 * this method has to be override.
	 */
	public void runGlue() {
		/*
		 * Default glue operation : identity.
		 */
		System.out.println("[HADL-RUNTIME] Running default glue operation");
		this.toRole.setValue(this.fromRole.getValue());
	}
}
