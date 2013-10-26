package M2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import M2.exceptions.ConnectorException;

public class Connector extends Element {

	private ArrayList<ConnectorRole> fromRoles;
	private ArrayList<ConnectorRole> toRoles;
	private Configuration parentConfig;
	private Configuration subConfig;
	private ConnectorGlue connectorGlue;
	
	public Connector(String name, int level, Configuration parentConfig) {
		super(name,level);
		
		this.fromRoles = new ArrayList<ConnectorRole>();
		this.toRoles = new ArrayList<ConnectorRole>();
		
		this.parentConfig = parentConfig;
		subConfig = null;
		connectorGlue = null;
	}
	
	public void addFromRole(ConnectorRole i) {
		fromRoles.add(i);
	}
	
	public void addToRole(ConnectorRole i) {
		toRoles.add(i);
	}
	
	public void setSubConfig(Configuration config) throws ConnectorException {
		if(connectorGlue != null) {
			throw new ConnectorException("Cannot add subConfig to the connector : the connector already has a glue");
		}
		subConfig = config;
	}
	
	public void setGlue(ConnectorGlue glue) throws ConnectorException {
		/*
		 * TODO handle multiple glue
		 */
		if(subConfig != null) {
			throw new ConnectorException("Cannot add glue to the connector : the connector already has a subConfig");
		}
		connectorGlue = glue;
	}
	
	public ConnectorRole getFromRole(String roleName) {
		return this.getRole(roleName,fromRoles);
	}
	
	public Collection<ConnectorRole> getFromRoles() {
		return fromRoles;
	}
	
	public ConnectorRole getToRole(String roleName) {
		return this.getRole(roleName,toRoles);
	}
	
	public Collection<ConnectorRole> getToRoles() {
		return toRoles;
	}
	
	public Configuration getParentConfig() {
		return parentConfig;
	}
	
	public Configuration getSubConfig() {
		return subConfig;
	}
	
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
