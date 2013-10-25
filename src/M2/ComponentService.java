package M2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import M2.exceptions.ComponentServiceException;

public abstract class ComponentService extends ComponentInterface {

	private ArrayList<ComponentPort> reqPorts;
	private ArrayList<ComponentPort> provPorts;
	
	/**
	 * Create a new service associated to the given Component.
	 * 
	 * @param name the name of the service.
	 * @param parent the Component the service belongs to.
	 * @param reqPorts the required ports used by the service.
	 * @param provPorts the provided ports used by the service.
	 * @throws ComponentServiceException when at least one port doesn't belong to 
	 * the same parent component.
	 */
	public ComponentService(String name, Component parent, ComponentPort[] reqPorts, ComponentPort[] provPorts) throws ComponentServiceException {
		super(name,parent);
		/*
		 * Check if all the given ports belong to the same
		 * parent as the current service
		 */
		this.reqPorts = new ArrayList<ComponentPort>(Arrays.asList(reqPorts));
		this.provPorts = new ArrayList<ComponentPort>(Arrays.asList(provPorts));
		checkPorts(this.reqPorts);
		checkPorts(this.provPorts);
	}
	
	public final Object call() {
		/*
		 * Prepare the required ports by calling their
		 * prepareValue method. The runtime will process the
		 * request and ensure all the reqPorts have their value accessible.
		 */
		return new Object();
	}
	
	protected abstract Object run(Collection<ComponentPort> reqPorts, Collection<ComponentPort> provPorts);
	
	
	private void checkPorts(Collection<ComponentPort> ports) throws ComponentServiceException {
		Iterator<ComponentPort> it = ports.iterator();
		while(it.hasNext()) {
			String portParentName = it.next().getParent().getName();
			if(!portParentName.equals(this.getParent().getName())) {
				throw new ComponentServiceException("At least one of the used ports isn't associated to the service parent component");
			}
		}
	}
	
}
