package M2;

import java.util.ArrayList;
import java.util.Collection;

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
	public void addComponent(Component component) {
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
	public void addConnector(Connector connector) {
		connector.setLevel(this.getLevel() + 1);
		connectors.add(connector);
	}
	
	public void addAttachment(Attachment attachment) {
		attachments.add(attachment);
	}
	
	public void addBinding(Binding binding) {
		bindings.add(binding);
	}
	
	public void addInterface(ConfigurationInterface configInterface) {
		configurationInterfaces.add(configInterface);
	}
	
	public Collection<Component> getComponents() {
		return this.components;
	}
	
	public Collection<Connector> getConnectors() {
		return this.connectors;
	}
	
}

