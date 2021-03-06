package M2;

public interface Valuable {
	
	public abstract void setValue(Object object);
	public abstract Object getValue();
	
	public Element getParent();
	
	public Configuration getParentConfig();

	public void updateFrom(Valuable iface);
	
	public boolean equals(Object obj);

}
