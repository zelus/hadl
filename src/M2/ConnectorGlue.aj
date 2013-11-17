package M2;

import M2.exceptions.GlueException;

/**
 * Connection process of a connector.
 * <p>
 * Glue is a function which transform 'from' roles to 'to' roles.
 * The default glue function is the identity, which set 'to' roles to the same
 * value as 'from' ones.
 * </p>
 * @author CaterpillarTeam
 */
public class ConnectorGlue {
	
	public pointcut glueCalled():
		call(* callGlue(..));
	
	public pointcut defaultGlueCalled():
		execution(* defaultGlueOperation(..));
	
	protected ConnectorRole fromRole;
	protected ConnectorRole toRole;
	
	/**
	 * Create a glue between the given from and to roles.
	 * @param fromRole the roles to compute from.
	 * @param toRole the roles to compute to.
	 * @throws GlueException if the given from and to roles are not contained in
	 * the same connector.
	 */
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
	
	/**
	 * External glue call method.
	 * <p>
	 * Call the user-defined method runGlue() and define the basic flush policy.
	 * </p>
	 */
	public final ConnectorRole callGlue() {
		this.runGlue();
		return this.toRole;
	}
	
	/**
	 * User-defined runGlue method.
	 * <p>
	 * Default glue operation is identity, if an other behavior is wanted
	 * this method has to be override.
	 * </p>
	 */
	protected void runGlue() {
		/*
		 * Default glue operation : identity.
		 */
		defaultGlueOperation();
	}
	
	private void defaultGlueOperation() {
		this.toRole.setValue(this.fromRole.getValue());
	}
}
