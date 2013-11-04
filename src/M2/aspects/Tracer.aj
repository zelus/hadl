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

public aspect Tracer {

	private String hadlPrefix = "[HADL-RUNTIME] ";
	
	before(ComponentService service) : ComponentService.callService(service) {
		System.out.println(hadlPrefix + "Starting " + service.getName() + " service ...");
	}
	
	before(Configuration configuration, Interface in, Interface out) : Configuration.flushPropagate(configuration, in, out) {
		if(in instanceof ComponentPort) {
			System.out.println(hadlPrefix + "Propagating port " + in.getName() + " value to role " + out.getName());
		}
		if(in instanceof ConnectorRole) {
			System.out.println(hadlPrefix + "Propagating role " + in.getName() + " value to port " + out.getName());
		}
	}
	
	before(Configuration configuration, Interface in, Interface out) : Configuration.bindPropagate(configuration, in, out) {
		if(in instanceof ComponentPort) {
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
		}
	}
	
	before(Configuration configuration, Interface iface) : Configuration.bindDelegation(configuration , iface) {
		if(iface.getParent() instanceof Configuration) {
			System.out.println(hadlPrefix + "Delegating " + iface.getName() + " flush to Configuration " + ((Configuration)iface.getParent()).getName());
		}
		else if(iface.getParent() instanceof Component) {
			Component bindedInterfaceParent = (Component)iface.getParent();
			System.out.println(hadlPrefix + "Delegating " + iface.getName() + " flush to Configuration " + bindedInterfaceParent.getParentConfig().getName());
		}
		else if(iface.getParent() instanceof Connector) {
			Connector bindedInterfaceParent = (Connector)iface.getParent();
			System.out.println(hadlPrefix + "Delegating " + iface.getName() + "flush to Configuration " + bindedInterfaceParent.getParentConfig().getName());
		}
	}
	
	before() : ConnectorGlue.glueCalled() {
		System.out.println(hadlPrefix + "Calling connector glue operation");
	}
	
	before(Configuration configuration, Interface iface) : Configuration.flush(configuration, iface) { 
		System.out.println(hadlPrefix + "Configuration " + configuration.getName() + " flushing " + iface.getName());
	}
	
	after() : ConnectorGlue.defaultGlueCalled() {
		System.out.println(hadlPrefix + "Running default glue operation");
	}
}
