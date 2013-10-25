import M1.overview.ClientComponent;
import M1.overview.ClientServerConfiguration;
import M1.overview.SendMessageService;
import M1.overview.ServerComponent;

public class App {

	public static void main(String[] args) {		
		try {
			System.out.println("Launching cs");
			ClientServerConfiguration csConfiguration = new ClientServerConfiguration();
			System.out.println("Main configuration created");
			
			ClientComponent clientComponent = (ClientComponent)csConfiguration.getComponent("Client");
			ServerComponent serverComponent = (ServerComponent)csConfiguration.getComponent("Server");
			
			System.out.println("csConfiguration sub-components : " + csConfiguration.getComponents().size());
			
			SendMessageService clientSendMessage = (SendMessageService)clientComponent.getProvService("SendMessageService");
			
			System.out.println(clientSendMessage.getName() + " activated");
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
