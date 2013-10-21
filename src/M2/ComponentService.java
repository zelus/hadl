package M2;

import java.util.ArrayList;

public abstract class ComponentService extends ComponentInterface {

	private ArrayList<ComponentPort> usedPorts;
	
	public ComponentService(String name, Component parent, ArrayList<ComponentPort> usedPorts) {
		super(name,parent);
		this.usedPorts = usedPorts;
	}
	
	public abstract Object run();
	
}
