package M2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import M2.exceptions.ConfigurationException;

public class Configuration extends Element{
	private ArrayList<Component> components;
	private ArrayList<Connector> connectors;
	private ArrayList<Attachment> attachments;
	private ArrayList<Binding> bindings;
	private ArrayList<ConfigurationInterface> configurationInterfaces;
	
	public Configuration(String name, int level) {
		super(name,level);
		components = new ArrayList<Component>();
		connectors = new ArrayList<Connector>();
		attachments = new ArrayList<Attachment>();
		bindings = new ArrayList<Binding>();
		configurationInterfaces = new ArrayList<ConfigurationInterface>();
	}

	/**
	 * Add the given component to the sub-component list.
	 * 
	 * The given component level is updated to the current one + 1,
	 * to ensure level values consistency.
	 * @param component the component to add.
	 */
	public final void addComponent(Component component) {
		component.setLevel(this.getLevel()+1);
		components.add(component);
	}
	
	/**
	 * Add the given component to the sup-connector list.
	 * 
	 * The given connector level is updated to the current one + 1,
	 * to ensure level values consistency.
	 * @param connector
	 */
	public final void addConnector(Connector connector) {
		connector.setLevel(this.getLevel() + 1);
		connectors.add(connector);
	}
	
	public final void addAttachment(Attachment attachment) {
		attachments.add(attachment);
	}
	
	public final void addBinding(Binding binding) {
		bindings.add(binding);
	}
	
	public final void addInterface(ConfigurationInterface configInterface) {
		configurationInterfaces.add(configInterface);
	}
	
	public final Component getComponent(String name) throws ConfigurationException {
		Iterator<Component> it = components.iterator();
		while(it.hasNext()) {
			Component currentComponent = it.next();
			if(currentComponent.getName().equals(name)) {
				return currentComponent;
			}
		}
		throw new ConfigurationException("No sub-component corresponding to the given name " + name);
	}
	
	public final Collection<Component> getComponents() {
		return this.components;
	}
	
	public final Connector getConnector(String name) throws ConfigurationException {
		Iterator<Connector> it = connectors.iterator();
		while(it.hasNext()) {
			Connector currentConnector = it.next();
			if(currentConnector.getName().equals(name)) {
				return currentConnector;
			}
		}
		throw new ConfigurationException("No sub-connector corresponding to the given name " + name);
	}
	
	public final Collection<Connector> getConnectors() {
		return this.connectors;
	}
	
	/**
	 * Runtime function : process the flush of the ports required by the service.
	 * @param componentService the service calling the runtime function.
	 */
	public final void flushReqPorts(ComponentService componentService) {
		// TODO Process the flush (by iterating through the attachments and bindings).
	}
	
	/**
	 * Runtime function : process the flush of the ports used by the service.
	 * @param componentService the service calling the runtime function.
	 */
	public final void flushProvPorts(ComponentService componentService) {
		// TODO Process the flush (by iterating through the attachments and bindings).
	}
	
}

