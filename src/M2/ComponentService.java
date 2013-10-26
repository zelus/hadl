package M2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import M2.exceptions.ComponentServiceException;

public abstract class ComponentService extends ComponentInterface {

	protected ArrayList<ComponentPort> reqPorts;
	protected ArrayList<ComponentPort> provPorts;
	
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
	
	/**
	 * @return the ports required by the current service.
	 */
	public Collection<ComponentPort> getReqPorts() {
		return reqPorts;
	}
	
	/**
	 * @return the ports used by the current service.
	 */
	public Collection<ComponentPort> getProvPorts() {
		return provPorts;
	}
	
	/**
	 * External service call method.
	 * 
	 * Call the user-defined method run and define the basic flush policy.
	 * @return the object returned by the run() method.
	 */
	public final Object call() {
		/*
		 * Tell to the configuration that a ComponentService is going to be
		 * launch and it needs reqPorts values. The runtime will compute the request
		 * and ensure that values are correctly set.
		 */
		System.out.println("[HADL-RUNTIME] Starting " + this.name + " service ...");
		flushReqPorts();
		Object runResult = run();
		/*
		 * Tell to the configuration that the service ended and the provPorts need to
		 * be flush to update other component's required ports.
		 */
		flushProvPorts();
		System.out.println("[HADL-RUNTIME] " + this.name + " service finish without error");
		return runResult;
	}
	
	/**
	 * Ask the runtime to flush all the required ports associated to the current service.
	 * 
	 * The flush ensure that getValue() method backward has been executed.
	 */
	public final void flushReqPorts() {
		this.getParent().getParentConfig().flushReqPorts(this);
	}
	
	/**
	 * Ask the runtime to flush all the provided ports associated to the current service.
	 * 
	 * The flush ensure that all the setValue() method forwards has been executed.
	 */
	public final void flushProvPorts() {
		this.getParent().getParentConfig().flushProvPorts(this);
		
	}
	
	/**
	 * User-defined run method.
	 * @return the result of the service execution.
	 */
	protected abstract Object run();
	
	/**
	 * Check if all the given ports belongs to the same component as the current service
	 * @param ports the ports to check to.
	 * @throws ComponentServiceException if at least one of the given ports doesn't belongs to the service component.
	 */
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
