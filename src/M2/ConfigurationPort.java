package M2;

import M2.exceptions.ConfigurationException;

/**
 * Defines a configuration port.
 * <p>
 * In HADL, a configuration port is associated to a value. This value can be accessed
 * and modified by dedicated methods.
 * It also defines runtime helpers to check if a port is a provided or required 
 * port.
 * </p>
 * @author CaterpillarTeam
 */
public abstract class ConfigurationPort extends ConfigurationInterface {
	
	/**
	 * Create a new port for the given configuration.
	 * @param name the name of the port.
	 * @param parent the configuration the port belongs to.
	 */
	public ConfigurationPort(String name, Configuration parent) {
		super(name, parent);
	}
	
	/**
	 * @return true if the port is registered as a provided port to its configuration,
	 * false otherwise.
	 */
	public final boolean isProvPort() {
		try {
			if(parent.getProvPort(name) != null) {
				return true;
			}
		}catch(ConfigurationException e) {
			
		}
		return false;
	}
	
	/**
	 * @return true if the port is registered as a required port to its configuration,
	 * false otherwise.
	 */
	public final boolean isReqPort() {
		try {
			if(parent.getReqPort(name) != null) {
				return true;
			}
		}catch(ConfigurationException e) {
			
		}
		return false;
	}
	
	/**
	 * Set the value associated to the current port.
	 * @param object the new value to set.
	 */
	public abstract void setValue(Object object);
	
	/**
	 * Returns the value associated to the current port.
	 * @return the value associated to the port.
	 */
	public abstract Object getValue();

}
