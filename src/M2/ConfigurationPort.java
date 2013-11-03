package M2;

import M2.exceptions.ConfigurationException;

public abstract class ConfigurationPort extends ConfigurationInterface {

	public ConfigurationPort(String name, Configuration parent) {
		super(name, parent);
	}
	
	public final boolean isProvPort() {
		try {
			if(parent.getProvPort(name) != null) {
				return true;
			}
		}catch(ConfigurationException e) {
			
		}
		return false;
	}
	
	public final boolean isReqPort() {
		try {
			if(parent.getReqPort(name) != null) {
				return true;
			}
		}catch(ConfigurationException e) {
			
		}
		return false;
	}
	
	public abstract void setValue(Object object);

	public abstract Object getValue();

}
