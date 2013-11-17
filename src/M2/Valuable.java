package M2;

public interface Valuable {
	
	public abstract void setValue(Object object);
	
	public abstract Object getValue();

	public void updateFrom(Valuable iface);

}
