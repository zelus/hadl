package M2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import M2.exceptions.ComponentException;

public abstract class Component extends Element {
	
	private ArrayList<ComponentPort> provPorts;
	private ArrayList<ComponentPort> reqPorts;
	private ArrayList<ComponentService> provServices;
	private ArrayList<ComponentService> reqServices;
	private Configuration parentConfig;
	private Configuration subConfig;
	
	public Component(String name, int level, Configuration parentConfig) {
		super(name,level);
		
		this.provPorts 		= new ArrayList<ComponentPort>();
		this.reqPorts 		= new ArrayList<ComponentPort>();
		this.provServices 	= new ArrayList<ComponentService>();
		this.reqServices 	= new ArrayList<ComponentService>();
		
		this.parentConfig = parentConfig;
		subConfig = null;
	}
	
	public final void addProvPort(ComponentPort i) {
		provPorts.add(i);
	}
	
	public final void addReqPort(ComponentPort i) {
		reqPorts.add(i);
	}
	
	public final void addProvService(ComponentService i) {
		provServices.add(i);
	}
	
	public final void addReqService(ComponentService i) {
		reqServices.add(i);
	}
	
	public final void setSubConfig(Configuration config) {
		subConfig = config;
	}
	
	public final ComponentPort getProvPort(String portName) {
		return this.getPort(portName,provPorts);
	}
	
	public final Collection<ComponentPort> getProvPorts() {
		return provPorts;
	}
	
	public final ComponentPort getReqPort(String portName) {
		return this.getPort(portName,reqPorts);
	}
	
	public final Collection<ComponentPort> getReqPorts() {
		return reqPorts;
	}
	
	public final ComponentService getProvService(String serviceName) {
		return getService(serviceName,provServices);
	}
	
	public final Collection<ComponentService> getProvServices() {
		return provServices;
	}
	
	public final ComponentService getReqService(String serviceName) {
		return getService(serviceName,reqServices);
	}
	
	public final Collection<ComponentService> getReqServices() {
		return reqServices;
	}
	
	public final Configuration getSubConfig() {
		return subConfig;
	}
	
	public final Configuration getParentConfig() {
		return parentConfig;
	}
	
	private final ComponentPort getPort(String portName, Collection<ComponentPort> portCollection) {
		Iterator<ComponentPort> it = portCollection.iterator();
		while(it.hasNext()) {
			ComponentPort currentPort = it.next();
			if(currentPort.getName().equals(portName)) {
				return currentPort;
			}
		}
		return null;
	}
	
	private final ComponentService getService(String serviceName, Collection<ComponentService> serviceCollection) {
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
