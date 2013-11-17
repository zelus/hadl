package M2.aspects;

import M2.ComponentService;
import M2.Configuration;
import M2.ConnectorGlue;
import M2.Runner;
import M2.Valuable;

public aspect Tracer {

	private String hadlPrefix = "[HADL-RUNTIME] ";
	
	before(ComponentService service) : ComponentService.callService(service) {
		System.out.println(hadlPrefix + "Starting " + service.getName() + " service ...");
	}
	
	after(ComponentService service): ComponentService.callService(service) {
		System.out.println(hadlPrefix + service.getName() + " service finish without error");
	}
	
	before(Valuable in, Valuable out) : Runner.flushPropagate(in, out) {
		System.out.println(hadlPrefix + "Propagating " + in.toString() + " value to " + out.toString());
	}
	
	before(Valuable in, Valuable out) : Runner.bindPropagate(in, out) {
		System.out.println(hadlPrefix + "Binding " + in.toString() + " to " + out.toString());
	}
	
	before() : ConnectorGlue.glueCalled() {
		System.out.println(hadlPrefix + "Calling connector glue operation");
	}
	
	before(Configuration configuration, Valuable iface) : Runner.flush(configuration, iface) { 
		if(configuration == null) {
			configuration = iface.getParent().getParentConfig();
		}
		System.out.println(hadlPrefix + "Configuration " + configuration.getName() + " flushing " + iface.toString());
	}
	
	after() : ConnectorGlue.defaultGlueCalled() {
		System.out.println(hadlPrefix + "Running default glue operation");
	}
}
