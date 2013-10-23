import M1.overview.ClientComponent;
import M1.overview.ClientServerConfiguration;
import M1.overview.ServerComponent;

public class App {

	public static void main(String[] args) {
		ClientServerConfiguration csConfiguration = new ClientServerConfiguration();
		ClientComponent clientComponent = new ClientComponent();
		ServerComponent serverComponent = new ServerComponent();
		
		csConfiguration.addComponent(clientComponent);
		csConfiguration.addComponent(serverComponent);
		
		System.out.println("csConfiguration sub-components : " + csConfiguration.getComponents().size());
	}

}
