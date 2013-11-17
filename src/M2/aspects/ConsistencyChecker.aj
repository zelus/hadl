package M2.aspects;

import M2.Component;
import M2.ComponentPort;
import M2.ComponentService;
import M2.exceptions.ComponentServiceException;

public aspect ConsistencyChecker {
	
	private String ccPrefix = "[Consistency Error] ";
	
	Object around(String name, Component parent, ComponentPort[] reqPorts, ComponentPort[] provPorts) : 
		ComponentService.createService(name, parent, reqPorts, provPorts) {
		if(checkPortsParent(reqPorts, parent) && checkPortsParent(provPorts, parent)) {
			return proceed(name,parent,reqPorts,provPorts);
		}
		System.err.println(ccPrefix + "Cannot create ComponentService " + name + " : ");
		System.err.println("\tAt least one of the used ports isn't associated to the parent component of the service.");
		System.err.println("at " + thisJoinPoint.getSourceLocation().getFileName() + " line " + thisJoinPoint.getSourceLocation().getLine());
		System.exit(-1);
		return null;
	}
	
	/**
	 * Check if all the given ports belongs to the same component as the current service
	 * @param ports the ports to check to.
	 * @throws ComponentServiceException if at least one of the given ports doesn't belongs to the service component.
	 */
	private boolean checkPortsParent(ComponentPort[] ports, Component parentComponent) {
		for(int i = 0; i < ports.length; i++) {
			String portParentName = ports[i].getParent().getName();
			if(!portParentName.equals(parentComponent.getName())) {
				return false;
			}
		}
		return true;
	}
	
}
