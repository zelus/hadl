package M2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Base element of architecture description, defines provided and required 
 * services and ports.
 * <p>
 * In HADL, a components can be atomic or composite.
 * 	<ul> 
 * 	 <li>
 *    A composite component contains a configuration that represents it 
 * with a finer granularity.
 *   </li>
 * 	 <li>
 *    Atomic components are the finest unity of composition in HADL. They don't
 * contain configuration.
 *    </li>
 *   </ul>
 * </p>
 * <p>
 * Both component types are represented with the same class because one of the 
 * main point in HADL is the component encapsulation : atomic and composite
 * component are processed the same way to allow architecture overview (without
 * going through the sub-configurations) as well as detailed architecture 
 * (including all or part of the sub-configurations).
 * </p>
 * @author CaterpillarTeam
 */
public abstract class Component extends Element {
	
	private ArrayList<ComponentPort> provPorts;
	private ArrayList<ComponentPort> reqPorts;
	private ArrayList<ComponentService> provServices;
	private ArrayList<ComponentService> reqServices;
	private Configuration parentConfig;
	private Configuration subConfig;
	
	/**
	 * Create a new component with the given name.
	 * @param name the name of the component.
	 * @param parentConfig the configuration handling the component.
	 */
	public Component(String name, Configuration parentConfig) {
		super(name,parentConfig);
		
		this.provPorts 		= new ArrayList<ComponentPort>();
		this.reqPorts 		= new ArrayList<ComponentPort>();
		this.provServices 	= new ArrayList<ComponentService>();
		this.reqServices 	= new ArrayList<ComponentService>();
		
		this.parentConfig = parentConfig;
		parentConfig.addComponent(this);
		subConfig = null;
	}
	
	/**
	 * Add the given port to the provided ports.
	 * @param i the port to add.
	 */
	public final void addProvPort(ComponentPort i) {
		provPorts.add(i);
	}
	
	/**
	 * Add the given port to the required ports.
	 * @param i the port to add.
	 */
	public final void addReqPort(ComponentPort i) {
		reqPorts.add(i);
	}
	
	/**
	 * Add the given service to the provided services.
	 * @param i the service to add.
	 */
	public final void addProvService(ComponentService i) {
		provServices.add(i);
	}
	
	/**
	 * Add the given service to the required services.
	 * @param i the service to add.
	 */
	public final void addReqService(ComponentService i) {
		reqServices.add(i);
	}
	
	/**
	 * Set the configuration representing the component.
	 * <p>
	 * If this method is used then the component became implicitly
	 * a composite component.
	 * </p>
	 * @param config the configuration.
	 */
	public final void setSubConfig(Configuration config) {
		subConfig = config;
		subConfig.setLevel(level+1);
	}
	
	/**
	 * Search in the provided ports for the given name.
	 * @param portName the name of the wanted port.
	 * @return the port corresponding to the given name if it exists,
	 * null otherwise.
	 */
	public final ComponentPort getProvPort(String portName) {
		return this.getPort(portName,provPorts);
	}
	/**
	 * @return all the provided ports of the component.
	 */
	public final Collection<ComponentPort> getProvPorts() {
		return provPorts;
	}
	
	/**
	 * Search in the required ports for the given name.
	 * @param portName the name of the wanted port.
	 * @return the port corresponding to the given name if it exists,
	 * null otherwise.
	 */
	public final ComponentPort getReqPort(String portName) {
		return this.getPort(portName,reqPorts);
	}
	
	/**
	 * @return all the required ports of the component.
	 */
	public final Collection<ComponentPort> getReqPorts() {
		return reqPorts;
	}
	
	/**
	 * Search in the provided services for the given name.
	 * @param serviceName the name of the wanted service.
	 * @return the service corresponding to the given name if it exists,
	 * null otherwise.
	 */
	public final ComponentService getProvService(String serviceName) {
		return getService(serviceName,provServices);
	}
	
	/**
	 * @return all the provided services of the component.
	 */
	public final Collection<ComponentService> getProvServices() {
		return provServices;
	}
	
	/**
	 * Search in the required services for the given name.
	 * @param serviceName the name of the wanted service.
	 * @return the service corresponding to the given name if it exists,
	 * null otherwise.
	 */
	public final ComponentService getReqService(String serviceName) {
		return getService(serviceName,reqServices);
	}
	
	/**
	 * @return all the required services of the component.
	 */
	public final Collection<ComponentService> getReqServices() {
		return reqServices;
	}
	
	/**
	 * @return the configuration handling the component.
	 */
	@Override
	public final Configuration getParentConfig() {
		return parentConfig;
	}
	
	/**
	 * @return the configuration representing the component.
	 */
	public final Configuration getSubConfig() {
		return subConfig;
	}
	
	/**
	 * Unified port search method, search through the given port list for the 
	 * given name.
	 * @param portName the name of the wanted port.
	 * @param portCollection the port list to search in.
	 * @return the port corresponding to the given name if it exists,
	 * null otherwise.
	 */
	private ComponentPort getPort(String portName, Collection<ComponentPort> portCollection) {
		Iterator<ComponentPort> it = portCollection.iterator();
		while(it.hasNext()) {
			ComponentPort currentPort = it.next();
			if(currentPort.getName().equals(portName)) {
				return currentPort;
			}
		}
		return null;
	}
	
	/**
	 * Unified service search method, search through the given service list for the 
	 * given name.
	 * @param serviceName the name of the wanted service.
	 * @param serviceCollection the service list to search in.
	 * @return the service corresponding to the given name if it exists,
	 * null otherwise.
	 */
	private ComponentService getService(String serviceName, Collection<ComponentService> serviceCollection) {
		Iterator<ComponentService> it = serviceCollection.iterator();
		while(it.hasNext()) {
			ComponentService currentService = it.next();
			if(currentService.getName().equals(serviceName)) {
				return currentService;
			}
		}
		return null;
	}

}
