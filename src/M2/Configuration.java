package M2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import M2.exceptions.ConfigurationException;
import M2.exceptions.ConnectorException;

/**
 * Base element of architecture description, defines ports and services or roles.
 * <p>
 * In HADL, a configuration can represent either a component and a connector.
 *  <ul>
 *   <li>
 *    If a configuration represents a component then it defines provided and 
 *    required ports and services.
 *   </li>
 *   <li>
 *    If a configuration represents a connector then it defines from and to
 *    roles.
 *   </li>
 *  </ul>
 * </p>
 * <p>
 * Both configuration representation are represented with the same class to 
 * unify links. Concrete configuration type is checked at runtime.
 * </p>
 * @author CaterpillarTeam
 */
public class Configuration extends Element {
	
	private ArrayList<Component> components;
	private ArrayList<Connector> connectors;
	private ArrayList<Attachment> attachments;
	private ArrayList<Binding> bindings;
	
	protected Element parent;
	private ArrayList<ConfigurationPort> reqPorts;
	private ArrayList<ConfigurationPort> provPorts;
	private ArrayList<ConfigurationRole> fromRoles;
	private ArrayList<ConfigurationRole> toRoles;
	
	/**
	 * Create a configuration with the given name and architectural level.
	 * @param name the name of the configuration.
	 * @param parent the parent of the configuration.
	 */
	public Configuration(String name, Element parent) throws ConfigurationException {
		super(name,parent);
		components = new ArrayList<Component>();
		connectors = new ArrayList<Connector>();
		attachments = new ArrayList<Attachment>();
		bindings = new ArrayList<Binding>();
		this.parent = parent;
		if(parent instanceof Component) {
			provPorts = new ArrayList<ConfigurationPort>();
			reqPorts = new ArrayList<ConfigurationPort>();
			((Component) parent).setSubConfig(this);
		}
		else if(parent instanceof Connector) {
			fromRoles = new ArrayList<ConfigurationRole>();
			toRoles = new ArrayList<ConfigurationRole>();
			try {
				((Connector) parent).setSubConfig(this);
			}catch(ConnectorException e) {
				System.out.println("Error in configuration creation : " + e.getMessage());
			}
		}
		else if(parent == null) {
			/*
			 * Top level Configuration, doesn't provides any port, services and
			 * roles. If such a behavior is needed put this in appropriate 
			 * Element.
			 */
		}
		else {
			throw new ConfigurationException("Invalid configuration type");
		}
	}
	
	public final void setParent(Element parent) {
		this.parent = parent;
	}

	/**
	 * Add the given component to the sub-component list.
	 * <p>
	 * The given component level is updated to the current one + 1,
	 * to ensure level values consistency.
	 * </p>
	 * @param component the component to add.
	 */
	public final void addComponent(Component component) {
		component.setLevel(this.getLevel()+1);
		components.add(component);
	}
	
	/**
	 * Add the given component to the sup-connector list.
	 * <p>
	 * The given connector level is updated to the current one + 1,
	 * to ensure level values consistency.
	 * </p>
	 * @param connector
	 */
	public final void addConnector(Connector connector) {
		connector.setLevel(this.getLevel() + 1);
		connectors.add(connector);
	}
	
	/**
	 * Add the given attachment to the configuration attachments.
	 * @param attachment the attachment to add.
	 */
	public final void addAttachment(Attachment attachment) {
		attachments.add(attachment);
	}
	
	/**
	 * Add the given binding to the configuration bindings.
	 * @param binding the binding to add.
	 */
	public final void addBinding(Binding binding) {
		bindings.add(binding);
	}
	
	public final void addProvPort(ConfigurationPort i) throws ConfigurationException {
		if(representComponent()) {
			provPorts.add(i);
		}
		else {
			throw new ConfigurationException("Cannot add provided port in a configuration which doesn't represent a component");
		}
	}
	
	public final void addReqPort(ConfigurationPort i) throws ConfigurationException {
		if(representComponent()) {
			reqPorts.add(i);
		}
		else {
			throw new ConfigurationException("Cannot add required port in a configuration which doesn't represent a component");
		}
	}
	
	public final void addFromRole(ConfigurationRole i) throws ConfigurationException {
		if(representConnector()) {
			fromRoles.add(i);
		}
		else {
			throw new ConfigurationException("Cannot add from role in a configuratio which doesn't represent a connector");
		}
	}
	
	public final void addToRole(ConfigurationRole i) throws ConfigurationException {
		if(representConnector()) {
			toRoles.add(i);
		}
		else {
			throw new ConfigurationException("Cannot add to role in a configuration which doesn't represent a connector");
		}
	}
	
	/**
	 * Search in the sub-components for a component with the given name.
	 * @param name the name of the wanted component.
	 * @return the component matching the given name if it exists, null otherwise.
	 */
	public final Component getComponent(String name) {
		Iterator<Component> it = components.iterator();
		while(it.hasNext()) {
			Component currentComponent = it.next();
			if(currentComponent.getName().equals(name)) {
				return currentComponent;
			}
		}
		return null;
	}
	
	/**
	 * @return all the sub-components of the configuration.
	 */
	public final Collection<Component> getComponents() {
		return this.components;
	}
	
	/**
	 * Search in the sub-connectors for a connector with the given name.
	 * @param name the name of the wanted connector.
	 * @return the connector matching the given name if it exists, null otherwise.
	 */
	public final Connector getConnector(String name) {
		Iterator<Connector> it = connectors.iterator();
		while(it.hasNext()) {
			Connector currentConnector = it.next();
			if(currentConnector.getName().equals(name)) {
				return currentConnector;
			}
		}
		return null;
	}
	
	/**
	 * @return all the sub-connectors of the configuration.
	 */
	public final Collection<Connector> getConnectors() {
		return this.connectors;
	}
	
	/**
	 * Search in the provided ports for the given name.
	 * @param portName the name of the wanted port.
	 * @return the port corresponding to the given name if it exists,
	 * null otherwise.
	 * @throws ConfigurationException if the current configuration doesn't 
	 * represent a component.
	 */
	public final ConfigurationPort getProvPort(String portName) throws ConfigurationException {
		if(!representComponent()) {
			throw new ConfigurationException("Cannot get provided port : " + name + " doesn't represent a component");
		}
		return this.getPort(portName,provPorts);
	}

	/**
	 * @return all the provided ports of the configuration.
	 * @throws ConfigurationException if the current configuration doesn't 
	 * represent a component.
	 */
	public final Collection<ConfigurationPort> getProvPorts() throws ConfigurationException {
		if(!representComponent()) {
			throw new ConfigurationException("Cannot get provided ports : " + name + " doesn't represent a component");
		}
		return provPorts;
	}
	
	/**
	 * Search in the required ports for the given name.
	 * @param portName the name of the wanted port.
	 * @return the port corresponding to the given name if it exists,
	 * null otherwise.
	 * @throws ConfigurationException if the current configuration doesn't 
	 * represent a component.
	 */
	public final ConfigurationPort getReqPort(String portName) throws ConfigurationException {
		if(!representComponent()) {
			throw new ConfigurationException("Cannot get required port : " + name + " doesn't represent a component");
		}
		return this.getPort(portName,reqPorts);
	}
	
	/**
	 * @return all the required ports of the component.
	 * @throws ConfigurationException if the current configuration doesn't 
	 * represent a component.
	 */
	public final Collection<ConfigurationPort> getReqPorts() throws ConfigurationException {
		if(!representComponent()) {
			throw new ConfigurationException("Cannot get required ports : " + name + " doesn't represent a component");
		}
		return reqPorts;
	}
	
	/**
	 * Search in the from roles for the given name.
	 * @param roleName the name of the wanted role.
	 * @return the role corresponding to the given name if it exists,
	 * null otherwise.
	 * @throws ConfigurationException if the current configuration doesn't 
	 * represent a connector.
	 */
	public ConfigurationRole getFromRole(String roleName) throws ConfigurationException {
		if(!representConnector()) {
			throw new ConfigurationException("Cannot get from role : " + name + " doesn't represent a connector");
		}
		return this.getRole(roleName,fromRoles);
	}
	
	/**
	 * @return all the from roles of the configuration.
	 * @throws ConfigurationException if the current configuration doesn't 
	 * represent a connector.
	 */
	public Collection<ConfigurationRole> getFromRoles() throws ConfigurationException {
		if(!representConnector()) {
			throw new ConfigurationException("Cannot get from roles : " + name + " doesn't represent a connector");
		}
		return fromRoles;
	}
	
	/**
	 * Search in the to roles for the given name.
	 * @param roleName the name of the wanted role.
	 * @return the role corresponding to the given name if it exists,
	 * null otherwise.
	 * @throws ConfigurationException if the current configuration doesn't 
	 * represent a connector.
	 */
	public ConfigurationRole getToRole(String roleName) throws ConfigurationException {
		if(!representConnector()) {
			throw new ConfigurationException("Cannot get to role : " + name + " doesn't represent a connector");
		}
		return this.getRole(roleName,toRoles);
	}

	/**
	 * @return all the to roles of the connector.
	 * @throws ConfigurationException if the current configuration doesn't 
	 * represent a connector.
	 */
	public Collection<ConfigurationRole> getToRoles() throws ConfigurationException {
		if(!representConnector()) {
			throw new ConfigurationException("Cannot get to roles : " + name + " doesn't represent a connector");
		}
		return toRoles;
	}
	
	/**
	 * Runtime function : process the flush of the given interface.
	 * @param elementInterface the interface to flush.
	 * @throws ConfigurationException if flush is called for a service.
	 */
	public final void flush(Interface elementInterface) throws ConfigurationException {
		this.flushRec(elementInterface, new ArrayList<Interface>());
	}
	
	/**
	 * Runtime function : process the flush of the given interface.
	 * @param elementInterface the interface to flush.
	 * @param flushedInterfaces the interfaces already flushed in the current flush
	 * execution (this avoid infinite flush loop).
	 * @throws ConfigurationException if flush is called for a service.
	 */
	private final void flushRec(Interface elementInterface,Collection<Interface> flushedInterfaces) throws ConfigurationException {
		if(elementInterface == null) {
			return;
		}
		if(elementInterface instanceof ComponentService) {
			throw new ConfigurationException("Cannot flush a service");
		}
		if(flushedInterfaces.contains(elementInterface)) {
			return;
		}
		System.out.println("[HADL-RUNTIME] Configuration " + this.name + " flushing " + elementInterface.getName());
		flushedInterfaces.add(elementInterface);
		/*
		 * First run the local bindings
		 */
		this.bind(elementInterface,flushedInterfaces);
		/*
		 * Search in the attachment list if there is any attachment matching
		 * the interface asking for a flush.
		 */
		Iterator<Attachment> it = attachments.iterator();
		while(it.hasNext()) {
			Attachment currentAttachment = it.next();
			/*
			 * The flush concerns a component port.
			 */
			if(currentAttachment.getComponentPort().equals(elementInterface)) {
				System.out.println("[HADL-RUNTIME] Propagating port " + currentAttachment.getComponentPort().getName() + " value to role " + currentAttachment.getConnectorRole().getName());
				currentAttachment.getConnectorRole().setValue(currentAttachment.getComponentPort().getValue());
				if(currentAttachment.getConnectorRole().getParent().getSubConfig() != null) {
					currentAttachment.getConnectorRole().getParent().getSubConfig().flushRec(currentAttachment.getConnectorRole(), flushedInterfaces);
				}
				else {
					System.out.println("[HADL-RUNTIME] Calling connector glue operation");
					/*
					 * Glue call will call recursively flush operation.
					 */
					currentAttachment.getConnectorRole().getParent().getGlue().callGlue();
				}
			}
			/*
			 * The flush concerns a connector role.
			 */
			if(currentAttachment.getConnectorRole().equals(elementInterface)) {
				System.out.println("[HADL-RUNTIME] Propagating role " + currentAttachment.getConnectorRole().getName() + " value to port " + currentAttachment.getComponentPort().getName());
				currentAttachment.getComponentPort().setValue(currentAttachment.getConnectorRole().getValue());
				if(currentAttachment.getComponentPort().getParent().getSubConfig() != null) {
					currentAttachment.getComponentPort().getParent().getSubConfig().flushRec(currentAttachment.getComponentPort(), flushedInterfaces);
				}
			}
			/*
			 * In HADL, configuration cannot be attached to other elements. To
			 * attach a configuration it needs to be the sub-configuration of an
			 * other element. In that case attachments are done on the parent
			 * element.
			 */
		}
	}
	
	/**
	 * Runtime function : process the binding of the given interface.
	 * 
	 * The binding process is done on the current configuration context.
	 * @param elementInterface the interface to bind.
	 * @param flushedInterface the interfaces already flushed in the current flush
	 * execution (this avoid infinite flush loop).
	 */
	private final void bind(Interface elementInterface, Collection<Interface> flushedInterface) {
		ArrayList<Interface> bindedInterfaces = new ArrayList<Interface>();
		Iterator<Binding> it = this.bindings.iterator();
		/*
		 * Search in the current bindings if there is some matching
		 * the given interface.
		 */
		while(it.hasNext()) {
			Binding currentBinding = it.next();
			if(elementInterface.equals(currentBinding.getComponentPort())) {
				if(!flushedInterface.contains(currentBinding.getConfigurationPort())) {
					System.out.println("[HADL-RUNTIME] Binding port " + elementInterface.getName() + " to port " + currentBinding.getConfigurationPort().getName());
					currentBinding.getConfigurationPort().setValue(currentBinding.getComponentPort().getValue());
					bindedInterfaces.add(currentBinding.getConfigurationPort());
				}
			}
			if(elementInterface.equals(currentBinding.getConfigurationPort())) {
				if(!flushedInterface.contains(currentBinding.getComponentPort())) {
					System.out.println("[HADL-RUNTIME] Binding port " + elementInterface.getName() + " to port " + currentBinding.getComponentPort().getName());
					currentBinding.getComponentPort().setValue(currentBinding.getConfigurationPort().getValue());
					bindedInterfaces.add(currentBinding.getComponentPort());
				}
			}
			if(elementInterface.equals(currentBinding.getConnectorRole())) {
				if(!flushedInterface.contains(currentBinding.getConfigurationRole())) {
					System.out.println("[HADL-RUNTIME] Binding role " + elementInterface.getName() + " to role " + currentBinding.getConfigurationRole().getName());
					currentBinding.getConfigurationRole().setValue(currentBinding.getConnectorRole().getValue());
					bindedInterfaces.add(currentBinding.getConfigurationRole());
				}
			}
			if(elementInterface.equals(currentBinding.getConfigurationRole())) {
				if(!flushedInterface.contains(currentBinding.getConnectorRole())) {
					System.out.println("[HADL-RUNTIME] Binding role " + elementInterface.getName() + " to role " + currentBinding.getConnectorRole().getName());
					currentBinding.getConnectorRole().setValue(currentBinding.getConfigurationRole().getValue());
					bindedInterfaces.add(currentBinding.getConnectorRole());
				}
			}
		}
		/*
		 * No binding found.
		 */
		if(bindedInterfaces.isEmpty()) {
			return;
		}
		Iterator<Interface> binded_it = bindedInterfaces.iterator();
		/*
		 * Process the binded interfaces and flush them.
		 */
		while(binded_it.hasNext()) {
			Interface currentInterface = binded_it.next();
			try {
				if(currentInterface.getParent() instanceof Configuration) {
					System.out.println("[HADL-RUNTIME] Delegating " + currentInterface.getName() + " flush to Configuration " + ((Configuration)currentInterface.getParent()).getName());
					((Configuration)currentInterface.getParent()).flushRec(currentInterface,flushedInterface);
				}
				else if(currentInterface.getParent() instanceof Component) {
					Component bindedInterfaceParent = (Component)currentInterface.getParent();
					System.out.println("[HADL-RUNTIME] Delegating " + currentInterface.getName() + " flush to Configuration " + bindedInterfaceParent.getParentConfig().getName());
					bindedInterfaceParent.getParentConfig().flushRec(currentInterface,flushedInterface);	
				}
				else if(currentInterface.getParent() instanceof Connector) {
					Connector bindedInterfaceParent = (Connector)currentInterface.getParent();
					System.out.println("[HADL-RUNTIME] Delegating " + currentInterface.getName() + "flush to Configuration " + bindedInterfaceParent.getParentConfig().getName());
					bindedInterfaceParent.getParentConfig().flushRec(currentInterface,flushedInterface);
				}
			}catch(Exception e) {
				System.out.println("Error in binding " + elementInterface.getName() + " with " + this.getName() + " : " + e.getMessage());
			}
		}
	}
	
	/**
	 * Runtime helper.
	 * @return true if the current configuration is the sub-configuration
	 * of a component.
	 */
	private boolean representComponent() {
		return(parent instanceof Component);
	}
	
	/**
	 * Runtime helper.
	 * @return true if the current configuration is the sub-configuration
	 * of a connector. 
	 */
	private boolean representConnector() {
		return(parent instanceof Connector);
	}
	
	/**
	 * Unified port search method, search through the given port list for the 
	 * given name.
	 * @param portName the name of the wanted port.
	 * @param portCollection the port list to search in.
	 * @return the port corresponding to the given name if it exists,
	 * null otherwise.
	 */
	private ConfigurationPort getPort(String portName, Collection<ConfigurationPort> portCollection) {
		Iterator<ConfigurationPort> it = portCollection.iterator();
		while(it.hasNext()) {
			ConfigurationPort currentPort = it.next();
			if(currentPort.getName().equals(portName)) {
				return currentPort;
			}
		}
		return null;
	}
	
	/**
	 * Unified role search method, search through the given role list for the 
	 * given name.
	 * @param roleName the name of the wanted role.
	 * @param roleCollection the role list to search in.
	 * @return the role corresponding to the given name if it exists,
	 * null otherwise.
	 */
	private ConfigurationRole getRole(String roleName, Collection<ConfigurationRole> roleCollection) {
		Iterator<ConfigurationRole> it = roleCollection.iterator();
		while(it.hasNext()) {
			ConfigurationRole currentRole = it.next();
			if(currentRole.getName().equals(roleName)) {
				return currentRole;
			}
		}
		return null;
	}
	
}

