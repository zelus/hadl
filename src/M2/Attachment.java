package M2;

public class Attachment extends Link{

	private ComponentPort componentPort;
	private ConnectorRole connectorRole;
	
	/**
	 * Create a new attachment between the given component and the given connector.
	 * 
	 * In HADL, attachments are not explicitly oriented, it is the runtime that
	 * go through them in the needed direction when services are called.
	 * @param name the name of the attachment.
	 * @param componentPort the component of the attachment.
	 * @param connectorRole the connector of the attachment.
	 */
	public Attachment(String name, ComponentPort componentPort, ConnectorRole connectorRole) throws AttachmentException {
		super(name);
		Component parentComponent = componentPort.getParent();
		Connector parentConnector = connectorRole.getParent();
		/*
		 * Check if the given configuration is acceptable. An acceptable configuration
		 * must be one of the following :
		 * 	- The component port is a provided port AND the connector role is a from role.
		 * 	- The component port is a required port AND the connector role is a to role.
		 */
		this.componentPort = componentPort;
		this.connectorRole = connectorRole;		
	}
	
	/**
	 * @return the component of the attachment.
	 */
	public ComponentPort getComponentPort() {
		return this.componentPort;
	}
	
	/**
	 * @return the connector of the attachment.
	 */
	public Interface getConnectorRole() {
		return this.connectorRole;
	}
}
