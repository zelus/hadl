package M2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Connector extends Element {

	private ArrayList<ConnectorRole> fromRoles;
	private ArrayList<ConnectorRole> toRoles;
	private Configuration subConfig;
	
	public Connector(String name, int level) {
		super(name,level);
		subConfig = null;
	}
	
	public void addFromRole(ConnectorRole i) {
		fromRoles.add(i);
	}
	
	public void addToRole(ConnectorRole i) {
		toRoles.add(i);
	}
	
	public void setSubConfig(Configuration config) {
		subConfig = config;
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
	
	public Configuration getSubConfig() {
		return subConfig;
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
