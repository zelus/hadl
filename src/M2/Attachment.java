package M2;

import M2.exceptions.AttachmentException;

/**
 * Represents a link between a component and a connector in the same
 * configuration and at the same hierarchical level.
 * <p>
 * In HADL attachments are not oriented, the runtime deduces the direction
 * when flush operations are called.
 * </p>
 * @author CaterpillarTeam
 */
public class Attachment extends Link{

	private ComponentPort componentPort;
	private ConnectorRole connectorRole;
	
	/**
	 * Create a new attachment between the given component and the given connector.
	 * <p>
	 * In HADL, attachments are not explicitly oriented, it is the runtime that
	 * go through them in the needed direction when services are called.
	 * </p>
	 * @param name the name of the attachment.
	 * @param componentPort the component of the attachment.
	 * @param connectorRole the connector of the attachment.
	 * @throws AttachmentException if the attachment is not semantically correct (
	 * attachments must be between provPort and fromRole or toRole and reqPort).
	 */
	public Attachment(String name, ComponentPort componentPort, ConnectorRole connectorRole) throws AttachmentException {
		super(name);
		/*
		 * Check if the given element to attach are acceptable. An acceptable 
		 * case must be one of the following :
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
	 * Convenience constructor : create an attachment between the given connector
	 * and the given component.
	 * <p>
	 * In HADL, attachment are not explicitly oriented, but to create an understandable
	 * client code it may be useful to call this constructor.
	 * </p>
	 * @param name the name of the attachment.
	 * @param componentPort the component of the attachment.
	 * @param connectorRole the connector of the attachment.
	 * @throws AttachmentException if the attachment is not semantically correct (
	 * attachments must be between provPort and fromRole or toRole and reqPort).
	 */
	public Attachment(String name, ConnectorRole connectorRole, ComponentPort componentPort) throws AttachmentException {
		this(name,componentPort,connectorRole);
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
