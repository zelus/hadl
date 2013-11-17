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

	private Valuable componentPort;
	private Valuable configurationPort;
	private Valuable connectorRole;
	private Valuable configurationRole;
	
	public pointcut createBinding(String name, Interface i1, Interface i2):
		execution( Binding.new(..)) &&
		args(name,i1,i2);
	
	/**
	 * Create a new binding between the given component and the given configuration.
	 * <p>
	 * In HADL, bindings are not explicitly oriented, it is the runtime that
	 * go through them in the needed direction when services are called.
	 * </p>
	 * @param name the name of the binding.
	 * @param componentPort the component port of the binding.
	 * @param configurationPort the configuration port of the binding.
	 */
	public Binding(String name, ComponentPort componentPort, ConfigurationPort configurationPort) throws BindingException {
		super(name);
		this.componentPort = componentPort;
		this.configurationPort = configurationPort;
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
	 */
	public Binding(String name, ConnectorRole connectorRole, ConfigurationRole configurationRole) {
		super(name);
		this.connectorRole = connectorRole;
		this.configurationRole = configurationRole;
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
	 */
	public Binding(String name, ConfigurationRole configurationRole, ConnectorRole connectorRole) throws BindingException {
		this(name, connectorRole, configurationRole);
	}
	
	/**
	 * Get the interface related to the given one.
	 * @param iface to search from.
	 * @return the related interface if it is known,
	 * null otherwise.
	 */
	public Valuable getBindingOf(Valuable iface) {
		if(iface.equals(this.componentPort)) {
			return this.configurationPort;
		}
		if(iface.equals(this.configurationPort)) {
			return this.componentPort;
		}
		if(iface.equals(this.connectorRole)) {
			return this.configurationRole;
		}
		if(iface.equals(this.configurationRole)) {
			return this.connectorRole;
		}
		return null;
	}
}
