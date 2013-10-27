package M2;

import M2.exceptions.BindingException;

public class Binding extends Link{

	private ComponentPort componentPort;
	private ConfigurationPort configurationPort;
	private ConnectorRole connectorRole;
	private ConfigurationRole configurationRole;
	
	
	// Binding between configuration and component
	public Binding(String name, ComponentPort componentPort, ConfigurationPort configurationPort) throws BindingException {
		super(name);
		
		if(!checkLevel(componentPort, configurationPort)) {
			throw new BindingException("Cannot create binding on non-adjacent levels");
		}
		
		if((componentPort.isReqPort() && configurationPort.isReqPort()) ||
				componentPort.isProvPort() && configurationPort.isProvPort()) {
			this.componentPort = componentPort;
			this.configurationPort = configurationPort;
		}
		else {
			throw new BindingException("Cannot create binding, please ensure that given ports are both provided or required");
		}
	}
	
	public Binding(String name, ConnectorRole connectorRole, ConfigurationRole configurationRole) throws BindingException {
		super(name);
		
		if(!checkLevel(connectorRole, configurationRole)) {
			throw new BindingException("Cannot create binding on non-adjacent levels");
		}
		
		if((connectorRole.isFromRole() && configurationRole.isFromRole()) ||
				connectorRole.isToRole() && configurationRole.isToRole()) {
			this.connectorRole = connectorRole;
			this.configurationRole = configurationRole;
		}
		else {
			throw new BindingException("Cannot create binding, please ensure that given roles are both from or to");
		}
	}
	
	public ComponentPort getComponentPort() {
		return this.componentPort;
	}
	
	public ConfigurationPort getConfigurationPort() {
		return this.configurationPort;
	}
	
	public ConnectorRole getConnectorRole() {
		return this.connectorRole;
	}
	
	public ConfigurationRole getConfigurationRole() {
		return this.configurationRole;
	}
	
	private boolean checkLevel(Interface i1, Interface i2) {
		int i1Level = i1.getParent().getLevel();
		int i2Level = i2.getParent().getLevel();
		return(i1Level == i2Level+1 || i1Level == i2Level-1);
	}

}
