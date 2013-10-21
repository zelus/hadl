package M2;

public abstract class Interface {
	
	private String name;
	
	public Interface(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public abstract Element getParent();

}
