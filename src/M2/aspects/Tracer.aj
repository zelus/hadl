package M2.aspects;

import M2.Component;
import M2.ComponentPort;
import M2.ComponentService;
import M2.Configuration;
import M2.ConfigurationPort;
import M2.ConfigurationRole;
import M2.Connector;
import M2.ConnectorGlue;
import M2.ConnectorRole;
import M2.Interface;
import M2.Runner;

public aspect Tracer {

	private String hadlPrefix = "[HADL-RUNTIME] ";
	
	before(ComponentService service) : ComponentService.callService(service) {
		System.out.println(hadlPrefix + "Starting " + service.getName() + " service ...");
	}
	
	after(ComponentService service): ComponentService.callService(service) {
		System.out.println(hadlPrefix + service.getName() + " service finish without error");
	}
	
	before(Interface in, Interface out) : Runner.flushPropagate(in, out) {
		System.out.println(hadlPrefix + "Propagating " + in.toString() + " value to " + out.toString());
		/*if(in instanceof ComponentPort) {
			System.out.println(hadlPrefix + "Propagating port " + in.getName() + " value to role " + out.getName());
		}
		if(in instanceof ConnectorRole) {
			System.out.println(hadlPrefix + "Propagating role " + in.getName() + " value to port " + out.getName());
		}*/
	}
	
	before(Interface in, Interface out) : Runner.bindPropagate(in, out) {
		System.out.println(hadlPrefix + "Binding " + in.toString() + " to " + out.toString());
		/*if(in instanceof ComponentPort) {
			System.out.println(hadlPrefix + "Binding component port " + in.getName() + " to configuration port " + out.getName());
		}
		if(in instanceof ConfigurationPort) {
			System.out.println(hadlPrefix + "Binding configuration port " + in.getName() + " to component port " + out.getName());
		}
		if(in instanceof ConnectorRole) {
			System.out.println(hadlPrefix + "Binding connector role " + in.getName() + " to configuration role " + out.getName());
		}
		if(in instanceof ConfigurationRole) {
			System.out.println(hadlPrefix + "Binding configuration role " + in.getName() + " to connector role " + out.getName());
		}*/
	}
	
	before(Configuration configuration, Interface iface) : Runner.bindDelegation(configuration , iface) {
		/*if(iface.getParent() instanceof Configuration) {
			System.out.println(hadlPrefix + "Delegating " + iface.toString() + " flush to Configuration " + ((Configuration)iface.getParent()).getName());
		}
		else {
			System.out.println(hadlPrefix + "Delegating " + iface.toString() + " flush to Configuration " + iface.getParent().getParentConfig().getName());
		}*/
	}
	
	before() : ConnectorGlue.glueCalled() {
		System.out.println(hadlPrefix + "Calling connector glue operation");
	}
	
	before(Configuration configuration, Interface iface) : Runner.flush(configuration, iface) { 
		if(configuration == null) {
			configuration = iface.getParent().getParentConfig();
		}
		System.out.println(hadlPrefix + "Configuration " + configuration.getName() + " flushing " + iface.toString());
	}
	
	after() : ConnectorGlue.defaultGlueCalled() {
		System.out.println(hadlPrefix + "Running default glue operation");
	}
}
