package M2.aspects;

import M2.Attachment;
import M2.Binding;
import M2.Component;
import M2.ComponentPort;
import M2.ComponentService;
import M2.ConfigurationPort;
import M2.ConfigurationRole;
import M2.ConnectorRole;
import M2.Interface;
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
	
	Object around(String name, Interface i1, Interface i2) :
		Binding.createBinding(name,i1,i2) {
		if(checkInterfaceLevel(i1,i2,1)) {
			if(isSameInterfaceType(i1, i2)) {
				return proceed(name,i1,i2);
			}
			else {
				System.err.println(ccPrefix + " Cannot create binding " + name + " : ");
				System.err.println("\tEnsure that given interfaces has the same type (provided/required ports | from/to roles)");
				System.err.println("at " + thisJoinPoint.getSourceLocation().getFileName() + " line " + thisJoinPoint.getSourceLocation().getLine());
				System.exit(-1);
				return null;
			}
		}
		System.err.println(ccPrefix + " Cannot create binding " + name + " : ");
		System.err.println("\tEnsure given interfaces are on adjacent levels");
		System.err.println("at " + thisJoinPoint.getSourceLocation().getFileName() + " line " + thisJoinPoint.getSourceLocation().getLine());
		System.exit(-1);
		return null;
	}
	
	Object around(String name, Interface i1, Interface i2) :
		Attachment.createAttachment(name,i1,i2) {
		if(i1 == null || i2 == null) {
			System.err.println(ccPrefix + "Cannot create attachment " + name + " : ");
			System.err.println("\tOne of the given interfaces is null");
			System.err.println("at " + thisJoinPoint.getSourceLocation().getFileName() + " line " + thisJoinPoint.getSourceLocation().getLine());
			System.exit(-1);
			return null;
		}
		if(checkInterfaceComplementarity(i1,i2)) {
			if(checkInterfaceLevel(i1, i2,0)) {
				return proceed(name,i1,i2);
			}
			else {
				System.err.println(ccPrefix + "Cannot create attachment " + name + " : ");
				System.err.println("\tEnsure given interfaces are on the same level");
				System.err.println("at " + thisJoinPoint.getSourceLocation().getFileName() + " line " + thisJoinPoint.getSourceLocation().getLine());
				System.exit(-1);
			}
		}
		else {
			System.err.println(ccPrefix + "Cannot create attachment " + name + " : ");
			System.err.println("\tEnsure given interface are either provided/from or required/to");
			System.err.println("at " + thisJoinPoint.getSourceLocation().getFileName() + " line " + thisJoinPoint.getSourceLocation().getLine());
		}
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
	
	/**
	 * Check if the given interfaces are on adjacent hierarchical levels.
	 * @param i1 first interface.
	 * @param i2 second interface.
	 * @param allowedDiff the allowed level difference between the interfaces.
	 * @return true if the interfaces are on adjacent levels, false otherwise.
	 */
	private boolean checkInterfaceLevel(Interface i1, Interface i2, int allowedDiff) {
		int i1Level = i1.getParent().getLevel();
		int i2Level = i2.getParent().getLevel();
		return(i1Level == i2Level+allowedDiff || i1Level == i2Level-allowedDiff);
	}
	
	/**
	 * Check if the given interfaces have the same subtype, ie. if
	 * they are both provided/required ports or from/to roles.
	 * @param i1
	 * @param i2
	 * @return true if the interfaces have the same subtype, false otherwise.
	 */
	private boolean isSameInterfaceType(Interface i1, Interface i2) {
		return(isSamePortType(i1, i2) || isSameRoleType(i1, i2));
	}
	
	/**
	 * Check if the given interfaces are both provided or required ports.
	 * @param i1
	 * @param i2
	 * @return true if the interfaces have the same subtype, false otherwise.
	 */
	private boolean isSamePortType(Interface i1, Interface i2) {
		ComponentPort port1 = null;
		ConfigurationPort port2 = null;
		if(i1 instanceof ComponentPort) {
			port1 = (ComponentPort)i1;
			port2 = (ConfigurationPort)i2;
		}
		else if(i1 instanceof ConfigurationPort) {
			port1 = (ComponentPort)i2;
			port2 = (ConfigurationPort)i1;
		}
		else {
			return false;
		}
		return(port1.isReqPort() && port2.isReqPort() ||
				port1.isProvPort() && port2.isProvPort());
	}
	
	/**
	 * Check if the given interfaces are both from or to roles.
	 * @param i1
	 * @param i2
	 * @return true if the interfaces have the same subtype, false otherwise.
	 */
	private boolean isSameRoleType(Interface i1, Interface i2) {
		ConnectorRole role1 = null;
		ConfigurationRole role2 = null;
		if(i1 instanceof ConnectorRole) {
			role1 = (ConnectorRole)i1;
			role2 = (ConfigurationRole)i2;
		}
		else if(i1 instanceof ConfigurationRole) {
			role1 = (ConnectorRole)i2;
			role2 = (ConfigurationRole)i1;
		}
		else {
			return false;
		}
		return(role1.isFromRole() && role2.isFromRole() ||
				role1.isToRole() && role2.isToRole());
	}
	
	private boolean checkInterfaceComplementarity(Interface i1, Interface i2) {
		ComponentPort port = null;
		ConnectorRole role = null;
		if(i1 instanceof ComponentPort) {
			port = (ComponentPort)i1;
			role = (ConnectorRole)i2;
		}
		else if(i1 instanceof ConnectorRole) {
			port = (ComponentPort)i2;
			role = (ConnectorRole)i1;
		}
		else {
			return false;
		}
		return((port.isProvPort() && role.isFromRole()) ||
				(port.isReqPort() && role.isToRole()));
	}
	
}