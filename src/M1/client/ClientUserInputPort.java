package M1.client;

import M2.Component;
import M2.ComponentPort;

public class ClientUserInputPort extends ComponentPort {

	private String userInputBuffer;
	
	public ClientUserInputPort(Component parent) {
		super("ClientUserInputPort", parent);
		userInputBuffer = new String();
	}

	@Override
	public void setValue(Object object) {
		userInputBuffer = (String)object;
		System.out.println("\t[M1] " + name + " updated with the value \"" + userInputBuffer + "\"" );
	}

	@Override
	public String getValue() {
		return userInputBuffer;
	}

}
