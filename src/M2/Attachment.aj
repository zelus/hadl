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

	private Valuable componentPort;
	private Valuable connectorRole;
	
	public pointcut createAttachment(String name, Interface i1, Interface i2) :
		execution( Attachment.new(..)) &&
		args(name,i1,i2);
	
	/**
	 * Create a new attachment between the given component and the given connector.
	 * <p>
	 * In HADL, attachments are not explicitly oriented, it is the runtime that
	 * go through them in the needed direction when services are called.
	 * </p>
	 * @param name the name of the attachment.
	 * @param componentPort the component of the attachment.
	 * @param connectorRole the connector of the attachment.
	 */
	public Attachment(String name, ComponentPort componentPort, ConnectorRole connectorRole) {
		super(name);
		this.componentPort = componentPort;
		this.connectorRole = connectorRole;		
	}
	
	/**
	 * Convenience constructor : create an attachment between the given connector
	 * and the given component.
	 * <p>
	 * In HADL, attachments are not explicitly oriented, but to create an understandable
	 * client code it may be useful to call this constructor.
	 * </p>
	 * @param name the name of the attachment.
	 * @param componentPort the component of the attachment.
	 * @param connectorRole the connector of the attachment.
	 */
	public Attachment(String name, ConnectorRole connectorRole, ComponentPort componentPort) throws AttachmentException {
		this(name,componentPort,connectorRole);
	}
	
	/**
	 * Get the interface related to the given one.
	 * @param iface the interface to search from.
	 * @return the related interface if it is known,
	 * null otherwise.
	 */
	public Valuable getAttachmentOf(Valuable iface) {
		if(iface.equals(this.componentPort)) {
			return this.connectorRole;
		}
		if(iface.equals(this.connectorRole)) {
			return this.componentPort;
		}
		return null;
	}
}
