package M2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import M2.exceptions.ComponentServiceException;

public abstract class ComponentService extends ComponentInterface {

	private ArrayList<ComponentPort> usedPorts;
	
	/**
	 * Create a new service associated to the given Component.
	 * 
	 * @param name the name of the service.
	 * @param parent the Component the service belongs to.
	 * @param usedPorts the ports (provided and required) used by the service.
	 * @throws ComponentServiceException when at least one port doesn't belong to 
	 * the same parent component.
	 */
	public ComponentService(String name, Component parent, ComponentPort[] usedPorts) throws ComponentServiceException {
		super(name,parent);
		/*
		 * Check if all the given ports belong to the same
		 * parent as the current service
		 */
		this.usedPorts = new ArrayList<ComponentPort>(Arrays.asList(usedPorts));
		Iterator<ComponentPort> it = this.usedPorts.iterator();
		while(it.hasNext()) {
			String portParentName = it.next().getParent().getName();
			if(!portParentName.equals(this.getParent().getName())) {
				throw new ComponentServiceException("At least one of the used ports isn't associated to the service parent component");
			}
		}
	}
	
	public final Object call(Object input) {
		Object preparedObject = this.run(input);
		return new Object();
	}
	
	protected abstract Object run(Object input);
	
}
