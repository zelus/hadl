package M1.server.serverDetails;

import M1.server.serverDetails.cmToSm.CmToSm;
import M1.server.serverDetails.connectionManager.ConnectionManager;
import M1.server.serverDetails.databaseManager.DatabaseManager;
import M1.server.serverDetails.dbToCm.DbToCm;
import M1.server.serverDetails.securityCheck.SecurityManager;
import M1.server.serverDetails.smToDb.SmToDb;
import M2.Attachment;
import M2.Binding;
import M2.Component;
import M2.Configuration;

public class ServerDetailsConfiguration extends Configuration {

	public ServerDetailsConfiguration(Component parent) throws Exception {
		super("ServerDetailsConfiguration", parent);
		ConnectionManager connectionManager = new ConnectionManager(this);
		SecurityManager securityManager = new SecurityManager(this);
		DatabaseManager databaseManager = new DatabaseManager(this);
		CmToSm cmToSm = new CmToSm(this);
		SmToDb smToDb = new SmToDb(this);
		DbToCm dbToCm = new DbToCm(this);		
		
		ServerDetailsReceivePort sdReceivePort = new ServerDetailsReceivePort(this);
		this.addReqPort(sdReceivePort);
		ServerDetailsSendPort sdSendPort = new ServerDetailsSendPort(this);
		this.addProvPort(sdSendPort);
		
		this.addAttachment(new Attachment("NameToCheckToConnector",connectionManager.getProvPort("NameToCheckPort"),cmToSm.getFromRole("ConnecterRole")));
		this.addAttachment(new Attachment("ConnectedToConnectionQuery",securityManager.getReqPort("ConnectionQueryPort"),cmToSm.getToRole("ConnectedRole")));
		this.addAttachment(new Attachment("AuthenticatedToSecurityCheck",connectionManager.getReqPort("SecurityCheckPort"),cmToSm.getToRole("AuthenticatedRole")));
		this.addAttachment(new Attachment("SecurityAuthToSecurizer",securityManager.getProvPort("SecurityAuthPort"),smToDb.getFromRole("SecurizerRole")));
		this.addAttachment(new Attachment("SecurityAuthToAuthenticator",securityManager.getProvPort("SecurityAuthPort"), cmToSm.getFromRole("AuthenticatorRole")));
		this.addAttachment(new Attachment("SecurizedToSecurityManagement",databaseManager.getReqPort("SecurityManagementPort"),smToDb.getToRole("SecurizedRole")));
		this.addAttachment(new Attachment("QueryIntegrationToDbConnecter",databaseManager.getProvPort("QueryIntegrationPort"),dbToCm.getFromRole("DbConnectorRole")));
		this.addAttachment(new Attachment("DbConnectedToDbQuery", connectionManager.getReqPort("DbQueryPort"), dbToCm.getToRole("DbConnectedRole")));
		
		this.addBinding(new Binding("ReceiveRequestToReceive",parent.getReqPort("ServerReceiveRequestPort"),sdReceivePort));
		this.addBinding(new Binding("ReceiveToReadSocket",connectionManager.getReqPort("ReadSocketPort"),sdReceivePort));
		
		this.addBinding(new Binding("SendRequestToSend",parent.getProvPort("ServerSendRequestPort"),sdSendPort));
		this.addBinding(new Binding("SendToWriteSocket", connectionManager.getProvPort("WriteSocketPort"),sdSendPort));
	}

}
