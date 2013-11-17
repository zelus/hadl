package M2.aspects;

import java.util.Iterator;

import M2.ComponentPort;
import M2.ComponentService;

public aspect RuntimeEngine {

	after(ComponentService service) returning : ComponentService.runService(service) {
		Iterator<ComponentPort> it = service.getProvPorts().iterator();
		while(it.hasNext()) {
			Runner.getInstance().flushInterface(it.next());
		}
	}
	
}
