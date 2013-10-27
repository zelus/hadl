package M2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.naming.ConfigurationException;

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
	 * Runtime function : process the flush of the ports required by the service.
	 * @param componentService the service calling the runtime function.
	 */
	public final void flushReqPorts(ComponentService componentService) {
		System.out.println("[HADL-RUNTIME] Configuration " + this.name + " flushing required ports for " + componentService.getName() + " service");
		// TODO Process the flush (by iterating through the attachments and bindings).
	}
	
	/**
	 * Runtime function : process the flush of the ports used by the service.
	 * @param componentService the service calling the runtime function.
	 */
	public final void flushProvPorts(ComponentService componentService) {
		System.out.println("[HADL-RUNTIME] Configuration " + this.name + " flushing provided ports for " + componentService.getName() + " service");
		// TODO Process the flush (by iterating through the attachments and bindings).
		/*
		 * Search in the attachment list if there is any attachment matching
		 * the service asking for a flush.
		 */
		Iterator<Attachment> it = attachments.iterator();
		while(it.hasNext()) {
			Attachment currentAttachment = it.next();
			if(currentAttachment.getComponentPort().isProvPort() &&
					currentAttachment.getComponentPort().getParent().equals(componentService.getParent())) {
				System.out.println("[HADL-RUNTIME] Propagating provided port " + currentAttachment.getComponentPort().getName() + " value to from role " + currentAttachment.getConnectorRole().getName());
				currentAttachment.getConnectorRole().setValue(currentAttachment.getComponentPort().getValue());
				System.out.println("[HADL-RUNTIME] Calling connector glue operation");
				currentAttachment.getConnectorRole().getParent().getGlue().callGlue();
			}
		}
		/*
		 * Search in the binding list if there is any binding matching 
		 * one of the provided port of the service asking for a flush.
		 */
		Iterator<Binding> binding_it = bindings.iterator();
		while(binding_it.hasNext()) {
			Binding currentBinding = binding_it.next();
			if(currentBinding.getComponentPort1().getParent().equals(componentService.getParent())) {
				currentBinding.getComponentPort2().setValue(currentBinding.getComponentPort1().getValue());
			}
			if(currentBinding.getComponentPort2().getParent().equals(componentService.getParent())) {
				currentBinding.getComponentPort1().setValue(currentBinding.getComponentPort2().getValue());
			}
		}
	}
	
	/**
	 * Runtime function : process the flush of the given role.
	 * @param role the role calling the runtime function.
	 */
	public final void flushRole(ConnectorRole role) {
		/*
		 * Search in the attachment list if there is any attachment matching
		 * the role asking for a flush.
		 */
		Iterator<Attachment> it = attachments.iterator();
		while(it.hasNext()) {
			Attachment currentAttachment = it.next();
			if(currentAttachment.getConnectorRole().isToRole() &&
					currentAttachment.getConnectorRole().getParent().equals(role.getParent())) {
				System.out.println("[HADL-RUNTIME] Propagating to role " + currentAttachment.getConnectorRole().getName() + " value to required port " + currentAttachment.getComponentPort().getName());
				currentAttachment.getComponentPort().setValue(currentAttachment.getConnectorRole().getValue());
			}
		}
		/*
		 * Search in the binding list if there is any binding matching 
		 * one of the provided port of the service asking for a flush.
		 */
		Iterator<Binding> binding_it = bindings.iterator();
		while(binding_it.hasNext()) {
			Binding currentBinding = binding_it.next();
			if(currentBinding.getConnectorRole1().getParent().equals(role.getParent())) {
				currentBinding.getConnectorRole2().setValue(currentBinding.getConnectorRole1().getValue());
			}
			if(currentBinding.getConnectorRole2().getParent().equals(role.getParent())) {
				currentBinding.getConnectorRole1().setValue(currentBinding.getConnectorRole2().getValue());
			}
		}
	}
	
	private boolean representComponent() {
		return(configurationType == COMPONENT_TYPE);
	}
	
	private boolean representConnector() {
		return(configurationType == CONNECTOR_TYPE);
	}
	
}

