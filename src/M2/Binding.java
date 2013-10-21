package M2;

import java.util.Collection;

public class Binding extends Link{

	private Interface topInterface;
	private Interface configurationInterface;
	
	
	// Binding between configuration and component
	public Binding(String name, ComponentInterface componentInterface, ConfiguratonInterface configurationInterface) {
		super(name);
		
		int componentLevel = componentInterface.getParent().getLevel();
		int configurationLevel = configurationInterface.getParent().getLevel();
		Collection<Component> configurationComponents = configurationInterface.getParent().getComponents();
		Configuration componentSubConfiguration = componentInterface.getParent().getSubConfiguration();
		
		if(componentLevel == (configurationLevel + 1)) {
			//configuration is top level
			
		}
		else if(configurationLevel == componentLevel + 1) {
			//component is top level
		}
		else {
			//Impossible to bind
		}
		this.topInterface = componentInterface;
		this.configurationInterface = configurationInterface;
	}
	
	public Binding(String name, ConnectorInterface connectorInterface, ConfigurationInterface configurationInterface {
		super(name);
		this.topInterface = connectorInterface;
		this.configurationInterface = configurationInterface;
	}
	
	public Interface getComponentInterface() {
		return this.ComponentInterface;
	}
	
	public Interface getBottomInterface() {
		return this.bottomInterface;
	}

}
