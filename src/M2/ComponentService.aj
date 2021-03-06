package M2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import M2.exceptions.ComponentServiceException;
import M2.exceptions.ConfigurationException;

/**
 * Defines a component service.
 * <p>
 * In HADL, a component service is associated to a set of required ports and
 * a set of provided ports. Those ports can be accessed by dedicated methods.
 * </p>
 * <p>
 * Component service also provides runtime methods to flush required and provided
 * ports. Those methods are internally called by the runtime after and before 
 * service execution to propagate its results.
 * </p>
 * <p>
 * Client services must define the run method (which contains the base execution
 * of the service). To call a service client must use the call() method, which 
 * ensure consistent runtime execution (by flushing required and provided ports).
 * </p>
 * @author CaterpillarTeam
 */
public abstract class ComponentService extends ComponentInterface {

	public pointcut createService(String name,Component parent, ComponentPort[] reqPorts, ComponentPort[] provPorts) :
		execution( ComponentService.new(..)) &&
		args(name,parent,reqPorts,provPorts);
	
	public pointcut runService(ComponentService service) :
		call( * ComponentService.run(..)) && target(service);
	
	public pointcut callService(ComponentService service) :
		call( * ComponentService.call(..)) && target(service);
	
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
	public ComponentService(String name, Component parent, ComponentPort[] reqPorts, ComponentPort[] provPorts) {
		super(name,parent);
		this.reqPorts = new ArrayList<ComponentPort>(Arrays.asList(reqPorts));
		this.provPorts = new ArrayList<ComponentPort>(Arrays.asList(provPorts));
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
	 * <p>
	 * Call the user-defined method run and define the basic flush policy.
	 * </p>
	 * @return the object returned by the run() method.
	 */
	public final Object call() throws ConfigurationException {
		Object runResult = run();
		return runResult;
	}
	
	/**
	 * User-defined run method.
	 * @return the result of the service execution.
	 */
	protected abstract Object run();
	
	@Override
	public String toString() {
		return "component service " + this.name;
	}
	
}
