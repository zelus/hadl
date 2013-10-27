package M2;

import M2.exceptions.BindingException;

public class Binding extends Link{

	private ComponentPort componentPort1;
	private ComponentPort componentPort2;
	private ConnectorRole connectorRole1;
	private ConnectorRole connectorRole2;
	
	
	// Binding between configuration and component
	public Binding(String name, ComponentPort componentPort, ComponentPort configurationPort) throws BindingException {
		super(name);
		
		if(!checkLevel(componentPort, configurationPort)) {
			throw new BindingException("Cannot create binding on non-adjacent levels");
		}
		
		if((componentPort.isReqPort() && configurationPort.isReqPort()) ||
				componentPort.isProvPort() && configurationPort.isProvPort()) {
			this.componentPort1 = componentPort;
			this.componentPort2 = configurationPort;
		}
		else {
			throw new BindingException("Cannot create binding, please ensure that given ports are both provided or required");
		}
	}
	
	public Binding(String name, ConnectorRole connectorRole, ConnectorRole configurationRole) throws BindingException {
		super(name);
		
		if(!checkLevel(connectorRole, configurationRole)) {
			throw new BindingException("Cannot create binding on non-adjacent levels");
		}
		
		if((connectorRole.isFromRole() && configurationRole.isFromRole()) ||
				connectorRole.isToRole() && configurationRole.isToRole()) {
			this.connectorRole1 = connectorRole;
			this.connectorRole2 = configurationRole;
		}
		else {
			throw new BindingException("Cannot create binding, please ensure that given roles are both from or to");
		}
	}
	
	public ComponentPort getComponentPort1() {
		return this.componentPort1;
	}
	
	public ComponentPort getComponentPort2() {
		return this.componentPort2;
	}
	
	public ConnectorRole getConnectorRole1() {
		return this.connectorRole1;
	}
	
	public ConnectorRole getConnectorRole2() {
		return this.connectorRole2;
	}
	
	private boolean checkLevel(Interface i1, Interface i2) {
		int i1Level = i1.getParent().getLevel();
		int i2Level = i2.getParent().getLevel();
		return(i1Level == i2Level+1 || i1Level == i2Level-1);
	}

}
