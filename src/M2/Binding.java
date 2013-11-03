package M2;

import M2.exceptions.BindingException;

/**
 * Represents a link between a component and its sub-configuration or
 * a configuration and one of its sub-component/connector. Bindings can
 * only be done one adjacent hierarchical level.
 * <p>
 * In HADL bindings are not oriented, the runtime deduces the direction
 * when flush operations are called.
 * </p>
 * @author CaterpillarTeam
 *
 */
public class Binding extends Link{

	private ComponentPort componentPort;
	private ConfigurationPort configurationPort;
	private ConnectorRole connectorRole;
	private ConfigurationRole configurationRole;
	
	
	/**
	 * Create a new binding between the given component and the given configuration.
	 * <p>
	 * In HADL, bindings are not explicitly oriented, it is the runtime that
	 * go through them in the needed direction when services are called.
	 * </p>
	 * @param name the name of the binding.
	 * @param componentPort the component port of the binding.
	 * @param configurationPort the configuration port of the binding.
	 * @throws BindingException if given component and configuration are not
	 * on adjacent hierarchical level or if the given ports doesn't have the same
	 * type (required or provided).
	 */
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
	
	/**
	 * Convenience constructor : create a binding between the given configuration port
	 * and the given component port.
	 * <p>
	 * In HADL, bindings are not explicitly oriented, but to create an understandable
	 * client code it may be useful to call this constructor.
	 * </p>
	 * @param name the name of the binding.
	 * @param configurationPort the configuration port of the binding.
	 * @param componentPort the component port of the binding.
	 * @throws BindingException if given component and configuration are not
	 * on adjacent hierarchical level or if the given ports doesn't have the same
	 * type (required or provided).
	 */
	public Binding(String name, ConfigurationPort configurationPort, ComponentPort componentPort) throws BindingException {
		this(name, componentPort, configurationPort);
	}
	
	/**
	 * Create a new binding between the given connector and the given configuration.
	 * <p>
	 * In HADL, bindings are not explicitly oriented, it is the runtime that
	 * go through them in the needed direction when services are called.
	 * </p>
	 * @param name the name of the binding.
	 * @param connectorRole the connector role of the binding.
	 * @param configurationRole the configuration role of the binding.
	 * @throws BindingException if given connector and configuration are not
	 * on adjacent hierarchical level or if the given roles doesn't have the same
	 * type (required or provided).
	 */
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
	
	/**
	 * Convenience constructor : create a binding between the given configuration role
	 * and the given connector role.
	 * <p>
	 * In HADL, bindings are not explicitly oriented, but to create an understandable
	 * client code it may be useful to call this constructor.
	 * </p>
	 * @param name the name of the binding.
	 * @param configurationRole the configuration role of the binding.
	 * @param connectorRole the connector role of the binding.
	 * @throws BindingException if given connector and configuration are not
	 * on adjacent hierarchical level or if the given ports doesn't have the same
	 * type (required or provided).
	 */
	public Binding(String name, ConfigurationRole configurationRole, ConnectorRole connectorRole) throws BindingException {
		this(name, connectorRole, configurationRole);
	}
	
	/**
	 * @return the component port involved in the binding if there is one,
	 * null otherwise.
	 */
	public ComponentPort getComponentPort() {
		return this.componentPort;
	}
	
	/**
	 * @return the configuration port involved in the binding if there is one,
	 * null otherwise.
	 */
	public ConfigurationPort getConfigurationPort() {
		return this.configurationPort;
	}
	
	/**
	 * @return the connector role involved in the binding if there is one,
	 * null otherwise.
	 */
	public ConnectorRole getConnectorRole() {
		return this.connectorRole;
	}
	
	/**
	 * @return the configuration role involved in the binding if there is one,
	 * null otherwise.
	 */
	public ConfigurationRole getConfigurationRole() {
		return this.configurationRole;
	}
	
	/**
	 * Check if the given interfaces are on adjacent hierarchical levels.
	 * @param i1 first interface.
	 * @param i2 second interface.
	 * @return true if the interfaces are on adjacent levels, false otherwise.
	 */
	private boolean checkLevel(Interface i1, Interface i2) {
		int i1Level = i1.getParent().getLevel();
		int i2Level = i2.getParent().getLevel();
		return(i1Level == i2Level+1 || i1Level == i2Level-1);
	}

}
