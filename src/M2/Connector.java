package M2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import M2.exceptions.ConnectorException;

/**
 * Base element of architecture description, defines from and to roles and a glue.
 * <p>
 * In HADL, connectors can be atomic or composite.
 *  <ul>
 *   <li>
 *    Atomic connectors are the finest unity of composition in HADL. They contain
 *    a glue that process the connection. Atomic connectors don't contain a 
 *    sub-configuration.
 *   </li>
 *   <li>
 *    Composite connectors contain a sub-configuration that represents it with a 
 *    finer granularity. Composite connectors cannot contain a glue, the 
 *    sub-configuration process the connection.
 *   </li>
 *  </ul>
 * </p>
 * <p>
 * Both connector types are represented with the same class because one of the main
 * point in HADL is the connector encapsulation : atomic and composite connectors
 * are processed the same way to allow architecture overview (without going through
 * the sub-configurations) as well as detailed architectures (including all or part
 * of the sub-configurations).
 * @author CaterpillarTeam
 */
public class Connector extends Element {

	private ArrayList<ConnectorRole> fromRoles;
	private ArrayList<ConnectorRole> toRoles;
	private Configuration parentConfig;
	private Configuration subConfig;
	private ConnectorGlue connectorGlue;
	
	/**
	 * Create a new connector with the given name and parent.
	 * @param name the name of the connector.
	 * @param parentConfig the configuration handling the connector.
	 */
	public Connector(String name, Configuration parentConfig) {
		super(name,parentConfig);
		
		this.fromRoles = new ArrayList<ConnectorRole>();
		this.toRoles = new ArrayList<ConnectorRole>();
		
		this.parentConfig = parentConfig;
		parentConfig.addConnector(this);
		subConfig = null;
		connectorGlue = null;
	}
	
	/**
	 * Add the given role to the from roles.
	 * @param i the role to add.
	 */
	public void addFromRole(ConnectorRole i) {
		fromRoles.add(i);
	}
	
	/**
	 * Add the given role to the to roles.
	 * @param i the role to add.
	 */
	public void addToRole(ConnectorRole i) {
		toRoles.add(i);
	}
	
	/**
	 * Set the configuration representing the connector.
	 * <p>
	 * If this method is used then the connector became implicitly
	 * a composite connector.
	 * </p>
	 * @param config the configuration.
	 * @throws ConnectorException if the connector is already represented by a
	 * glue.
	 */
	public void setSubConfig(Configuration config) throws ConnectorException {
		if(connectorGlue != null) {
			throw new ConnectorException("Cannot add subConfig to the connector : the connector already has a glue");
		}
		subConfig = config;
		subConfig.setLevel(level+1);
	}
	
	/**
	 * Set the glue representing the connector.
	 * <p>
	 * If this method is used then the connector became implicitly
	 * an atomic connector.
	 * </p>
	 * @param glue the glue.
	 * @throws ConnectorException if the connector is already represented by
	 * a sub-configuration.
	 */
	public void setGlue(ConnectorGlue glue) throws ConnectorException {
		/*
		 * TODO handle multiple glue
		 */
		if(subConfig != null) {
			throw new ConnectorException("Cannot add glue to the connector : the connector already has a subConfig");
		}
		connectorGlue = glue;
	}
	
	/**
	 * Search in the from roles for the given name.
	 * @param roleName the name of the wanted role.
	 * @return the role corresponding to the given name if it exists,
	 * null otherwise.
	 */
	public ConnectorRole getFromRole(String roleName) {
		return this.getRole(roleName,fromRoles);
	}
	
	/**
	 * @return all the from roles of the connector.
	 */
	public Collection<ConnectorRole> getFromRoles() {
		return fromRoles;
	}
	
	/**
	 * Search in the to roles for the given name.
	 * @param roleName the name of the wanted role.
	 * @return the role corresponding to the given name if it exists,
	 * null otherwise.
	 */
	public ConnectorRole getToRole(String roleName) {
		return this.getRole(roleName,toRoles);
	}
	
	/**
	 * @return all the to roles of the connector.
	 */
	public Collection<ConnectorRole> getToRoles() {
		return toRoles;
	}
	
	/**
	 * @return the configuration handling the connector.
	 */
	@Override
	public Configuration getParentConfig() {
		return parentConfig;
	}
	
	/**
	 * @return the configuration representing the connector if the connector
	 * is composite, null otherwise.
	 */
	public Configuration getSubConfig() {
		return subConfig;
	}
	
	/**
	 * @return the glue representing the connector if the connector is atomic,
	 * null otherwise.
	 */
	public ConnectorGlue getGlue() {
		return connectorGlue;
	}
	
	@Override
	public boolean equals(Object object) {
		if(object instanceof Connector) {
			Connector connector = (Connector)object;
			return connector.getName().equals(name);
		}
		return false;
	}
	
	/**
	 * Unified role search method, search through the given role list for the 
	 * given name.
	 * @param roleName the name of the wanted role.
	 * @param roleCollection the role list to search in.
	 * @return the role corresponding to the given name if it exists,
	 * null otherwise.
	 */
	private ConnectorRole getRole(String roleName, Collection<ConnectorRole> roleCollection) {
		Iterator<ConnectorRole> it = roleCollection.iterator();
		while(it.hasNext()) {
			ConnectorRole currentRole = it.next();
			if(currentRole.getName().equals(roleName)) {
				return currentRole;
			}
		}
		return null;
	}
}
