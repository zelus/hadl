package M2;

public abstract class ConfigurationRole extends ConfigurationInterface {

	public ConfigurationRole(String name, Configuration parent) {
		super(name, parent);
	}

	@Override
	public boolean equals(Object object) {
		if(object instanceof ConfigurationRole) {
			ConfigurationRole role = (ConfigurationRole)object;
			return name.equals(role.name);
		}
		return false;
	}
	
	public final boolean isFromRole() {
		if(parent.getFromRole(name) != null) {
			return true;
		}
		return false;
	}

	public final boolean isToRole() {
		if(parent.getToRole(name) != null) {
			return true;
		}
		return false;
	}
	
	public abstract void setValue(Object object);

	public abstract Object getValue();
}
