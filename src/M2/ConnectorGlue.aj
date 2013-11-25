package M2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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
	
	protected ArrayList<ConnectorRole> fromRole;
	protected ArrayList<ConnectorRole> toRole;
	
	/**
	 * Create a glue between the given from and to roles.
	 * @param fromRole the roles to compute from.
	 * @param toRole the roles to compute to.
	 * @throws GlueException if the given from and to roles are not contained in
	 * the same connector.
	 */
	public ConnectorGlue(Collection<ConnectorRole> fromRole, Collection<ConnectorRole> toRole) throws GlueException {
		/*
		 *  TODO handle multiple from/to roles
		 */
		if(fromRole == null || toRole == null) {
			throw new GlueException("Cannot create glue from null");
		}
		Iterator<ConnectorRole> fromIt = fromRole.iterator();
		Iterator<ConnectorRole> toIt = toRole.iterator();
		while(fromIt.hasNext()) {
			ConnectorRole currentFrom = fromIt.next();
			while(toIt.hasNext()) {
				ConnectorRole currentTo = toIt.next();
				if(currentFrom.getParent().equals(currentTo.getParent())) {
					if(currentFrom.getParent().getFromRole(currentFrom.getName()) == null ||
							currentTo.getParent().getToRole(currentTo.getName()) == null) {
						throw new GlueException("Cannot create glue : please ensure that one of the given role is a from role and the other is a to role");
					}
				}
				else {
					throw new GlueException("Cannot create glue : please ensure that from and to role parents are the same");
				}
			}
		}
		this.fromRole = new ArrayList<ConnectorRole>(fromRole);
		this.toRole = new ArrayList<ConnectorRole>(toRole);
	}
	
	/**
	 * External glue call method.
	 * <p>
	 * Call the user-defined method runGlue() and define the basic flush policy.
	 * </p>
	 */
	public final Collection<ConnectorRole> callGlue(ConnectorRole role) {
		return this.runGlue(role);
	}
	
	/**
	 * User-defined runGlue method.
	 * <p>
	 * Default glue operation is identity, if an other behavior is wanted
	 * this method has to be override.
	 * </p>
	 */
	protected Collection<ConnectorRole> runGlue(ConnectorRole role) {
		/*
		 * Default glue operation : identity.
		 */
		return defaultGlueOperation(role);
	}
	
	private Collection<ConnectorRole> defaultGlueOperation(ConnectorRole role) {
		ArrayList<ConnectorRole> processedRoles = new ArrayList<ConnectorRole>();
		if(role.isFromRole()) {
			for(int i = 0; i < fromRole.size(); i++) {
				if(fromRole.get(i).equals(role)) {
					toRole.get(i).setValue(role.getValue());
					processedRoles.add(toRole.get(i));
				}
			}
		}
		else {
			System.out.println("Glue error !");
		}
		return processedRoles;
	}
}
