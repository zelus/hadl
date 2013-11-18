package M2.aspects;

import java.util.Iterator;

import M2.Configuration;
import M2.Element;
import M2.Property;
import M2.Valuable;

public aspect InvariantChecker {

	private String icPrefix = "[Invariant error] ";
	
	after(Configuration config, Valuable iface) : Runner.flush(config,iface) {
		if(config == null) {
			config = iface.getParentConfig();
		}
		checkInvariant(config);
		checkInvariant(iface.getParent());
	}
	
	after(Valuable in, Valuable out) : Runner.flushPropagate(in,out) {
		checkInvariant(in.getParent());
		checkInvariant(out.getParent());
	}
	
	private void checkInvariant(Element el) {
		Property invalidProperty = checkProperties(el);
		if(invalidProperty != null) {
			System.err.println(icPrefix + "An invariant is not respected : ");
			System.err.println("\t" + invalidProperty.toString());
			System.exit(-1);
		}
	}
	
	/**
	 * Check if all the properties of the given element are respected.
	 * @param el the element to check.
	 * @return a property if it hasn't been respected, null otherwise.
	 */
	private Property checkProperties(Element el) {
		Iterator<Property> it = el.getProperties().iterator();
		while(it.hasNext()) {
			Property prop = it.next();
			if(!prop.checkInvariant(el)) {
				return prop;
			}
		}
		return null;
	}
}
