package M2;

import java.util.ArrayList;
import java.util.Collection;

public class Component extends Element {
	
	private ArrayList<ComponentPort> provPorts;
	private ArrayList<ComponentPort> reqPorts;
	private ArrayList<ComponentService> provServices;
	private ArrayList<ComponentService> reqServices;
	private Configuration subConfig;
	
	public Component(String name, int level) {
		super(name,level);
		subConfig = null;
	}
	
	public void addProvPort(ComponentPort i) {
		provPorts.add(i);
	}
	
	public void addReqPort(ComponentPort i) {
		reqPorts.add(i);
	}
	
	public void addProvService(ComponentService i) {
		provServices.add(i);
	}
	
	public void addReqService(ComponentService i) {
		reqServices.add(i);
	}
	
	public void setSubConfig(Configuration config) {
		subConfig = config;
	}
	
	public Collection<ComponentPort> getProvPorts() {
		return provPorts;
	}
	
	public Collection<ComponentPort> getReqPorts() {
		return reqPorts;
	}
	
	public Collection<ComponentService> getProvServices() {
		return provServices;
	}
	
	public Collection<ComponentService> getReqServices() {
		return reqServices;
	}
	
	public Configuration getSubConfig() {
		return subConfig;
	}

}
