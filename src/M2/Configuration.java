package M2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import M2.exceptions.ConfigurationException;

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
	
	public static int COMPONENT_TYPE = 1;
	public static int CONNECTOR_TYPE = 2;
	
	private ArrayList<Component> components;
	private ArrayList<Connector> connectors;
	private ArrayList<Attachment> attachments;
	private ArrayList<Binding> bindings;
	
	private int configurationType;
	protected Element parent;
	private ArrayList<ComponentPort> reqPorts;
	private ArrayList<ComponentPort> provPorts;
	private ArrayList<ConnectorRole> fromRoles;
	private ArrayList<ConnectorRole> toRoles;
	
	/**
	 * Create a configuration with the given name and architectural level.
	 * @param name the name of the configuration.
	 * @param level the architectural level of the configuration.
	 */
	public Configuration(String name, int level, int configurationType) throws ConfigurationException {
		super(name,level);
		components = new ArrayList<Component>();
		connectors = new ArrayList<Connector>();
		attachments = new ArrayList<Attachment>();
		bindings = new ArrayList<Binding>();
		this.configurationType = configurationType;
		if(configurationType == COMPONENT_TYPE) {
			provPorts = new ArrayList<ComponentPort>();
			reqPorts = new ArrayList<ComponentPort>();
		}
		else if(configurationType == CONNECTOR_TYPE) {
			fromRoles = new ArrayList<ConnectorRole>();
			toRoles = new ArrayList<ConnectorRole>();
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
	
	public final void addProvPort(ComponentPort i) throws ConfigurationException {
		if(representComponent()) {
			provPorts.add(i);
		}
		else {
			throw new ConfigurationException("Cannot add provided port in a configuration which doesn't represent a component");
		}
	}
	
	public final void addReqPort(ComponentPort i) throws ConfigurationException {
		if(representComponent()) {
			reqPorts.add(i);
		}
		else {
			throw new ConfigurationException("Cannot add required port in a configuration which doesn't represent a component");
		}
	}
	
	public final void addFromRole(ConnectorRole i) throws ConfigurationException {
		if(representConnector()) {
			fromRoles.add(i);
		}
		else {
			throw new ConfigurationException("Cannot add from role in a configuratio which doesn't represent a connector");
		}
	}
	
	public final void addToRole(ConnectorRole i) throws ConfigurationException {
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
	 * Runtime function : process the flush of the ports used by the service.
	 * @param componentService the service calling the runtime function.
	 */
	public final void flush(Interface elementInterface) throws ConfigurationException {
		System.out.println("[HADL-RUNTIME] Configuration " + this.name + " flushing " + elementInterface.getName());
		if(elementInterface instanceof ComponentService) {
			throw new ConfigurationException("Cannot flush a service");
		}
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
				boolean binded = this.bind(currentAttachment.getConnectorRole());
				if(!binded) {
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
				/*
				 * Bind the port if needed, but this has no more effects, there is
				 * no glue to call.
				 */
				this.bind(currentAttachment.getComponentPort());
			}
		}
	}
	
	public final boolean bind(Interface elementInterface) {
		boolean result = false;
		Iterator<Binding> it = bindings.iterator();
		while(it.hasNext()) {
			Binding currentBinding = it.next();
			if(elementInterface.equals(currentBinding.getComponentPort1())) {
				System.out.println("[HADL-RUNTIME] Binding port " + elementInterface.getName() + " to port " + currentBinding.getComponentPort2());
				currentBinding.getComponentPort2().setValue(currentBinding.getComponentPort1().getValue());
				result = true;
			}
			if(elementInterface.equals(currentBinding.getComponentPort2())) {
				System.out.println("[HADL-RUNTIME] Binding port " + elementInterface.getName() + " to port " + currentBinding.getComponentPort1());
				currentBinding.getComponentPort1().setValue(currentBinding.getComponentPort2().getValue());
				result = true;
			}
			if(elementInterface.equals(currentBinding.getConnectorRole1())) {
				System.out.println("[HADL-RUNTIME] Binding role " + elementInterface.getName() + " to role " + currentBinding.getConnectorRole2());
				currentBinding.getConnectorRole2().setValue(currentBinding.getConnectorRole1().getValue());
				result = true;
			}
			if(elementInterface.equals(currentBinding.getConnectorRole2())) {
				System.out.println("[HADL-RUNTIME] Binding role " + elementInterface.getName() + " to role " + currentBinding.getConnectorRole1());
				currentBinding.getConnectorRole1().setValue(currentBinding.getConnectorRole2().getValue());
				result = true;
			}
		}
		return result;
	}
	
	private boolean representComponent() {
		return(configurationType == COMPONENT_TYPE);
	}
	
	private boolean representConnector() {
		return(configurationType == CONNECTOR_TYPE);
	}
	
}

