package M2;

public abstract class ConnectorRole extends ConnectorInterface {

	public ConnectorRole(String name, Connector parent) {
		super(name,parent);
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
	
	public final void flush() {
		this.parent.getParentConfig().flushRole(this);
	}
	
	public abstract void setValue(Object obj);
	public abstract Object getValue();
}
