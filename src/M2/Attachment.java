package M2;

import M2.exceptions.AttachmentException;

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
		/*
		 * Check if the given configuration is acceptable. An acceptable configuration
		 * must be one of the following :
		 * 	- The component port is a provided port AND the connector role is a from role.
		 * 	- The component port is a required port AND the connector role is a to role.
		 */
		if(componentPort == null || connectorRole == null) {
			throw new AttachmentException("Cannot create attachment from null");
		}
		if((componentPort.isProvPort() && connectorRole.isFromRole()) ||
				(componentPort.isReqPort() && connectorRole.isToRole())) {
			this.componentPort = componentPort;
			this.connectorRole = connectorRole;		
		}
		else {
			throw new AttachmentException("Invalid attachment, please connect a prov port to a from role or a req port to a to role");
		}
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
	public ConnectorRole getConnectorRole() {
		return this.connectorRole;
	}
}
