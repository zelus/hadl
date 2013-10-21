package M2;

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
		configurationInterfaces = ArrayList<ConfigurationInterfaces>();
	}

	public void addComponent(Component component) {
		components.add(component);
	}
	public void addConnector(Connector connector) {
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

