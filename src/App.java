import M1.client.ClientComponent;
import M1.client.ClientUserInputPort;
import M1.client.SendMessageService;
import M1.overview.ClientServerConfiguration;
import M1.server.ServerComponent;
import M2.Component;
import M2.ComponentService;
import M2.Configuration;

public class App {

	public static void main(String[] args) {		
		try {
			System.out.println("Launching cs");
			ClientServerConfiguration csConfiguration = new ClientServerConfiguration();
			System.out.println("Main configuration created");
			
			ClientComponent clientComponent = (ClientComponent)csConfiguration.getComponent("Client");
			ServerComponent serverComponent = (ServerComponent)csConfiguration.getComponent("Server");
			
			ClientUserInputPort clientUserInputPort = (ClientUserInputPort)clientComponent.getReqPort("ClientUserInputPort");
			clientUserInputPort.setValue("Test request");
			
			SendMessageService clientSendMessage = (SendMessageService)clientComponent.getProvService("SendMessageService");
			clientSendMessage.call();
			
			// call the sub-component of the server to continue the propagation
			Configuration serverConfig = serverComponent.getSubConfig();
			Component connectionManager = serverConfig.getComponent("ConnectionManager");
			ComponentService delegateChecking = connectionManager.getProvService("DelegateCheckingService");
			delegateChecking.call();
			
			Component securityManager = serverConfig.getComponent("SecurityManager");
			ComponentService provideAuthService = securityManager.getProvService("ProvideAuthentificationService");
			provideAuthService.call();
			
			Component dbManager = serverConfig.getComponent("DatabaseManager");
			ComponentService provideQueryService = dbManager.getProvService("ProvideQueryIntegrationService");
			provideQueryService.call();
			
			ComponentService computeResponse = connectionManager.getProvService("ComputeResponseService");
			computeResponse.call();
			
		}catch(Exception e) {
			System.out.println("Exception during service execution");
			System.out.println(e.getMessage());
		}
	}
}
