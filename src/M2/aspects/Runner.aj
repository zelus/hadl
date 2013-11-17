package M2.aspects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import M2.Attachment;
import M2.Binding;
import M2.ComponentPort;
import M2.Configuration;
import M2.Connector;
import M2.ConnectorInterface;
import M2.ConnectorRole;
import M2.Valuable;
import M2.exceptions.ConfigurationException;

public class Runner {

	private static Runner RUNNER_INSTANCE = null; 
	public static Runner getInstance() {
		if(RUNNER_INSTANCE == null) {
			RUNNER_INSTANCE = new Runner();
		}
		return RUNNER_INSTANCE;
	}
	
	public pointcut bindDelegation(Configuration configuration, Valuable iface) :
		call(* flushRec(..)) &&
		withincode(* Runner.bind(..)) &&
		args(iface,configuration,..);
	
	public pointcut flushPropagate(Valuable in, Valuable out) :
		call( * updateFrom(..)) &&
		withincode(* Runner.flushRec(..)) &&
		target(out) &&
		args(in);
	
	public pointcut bindPropagate(Valuable in, Valuable out) :
		call( * updateFrom(..)) &&
		withincode(* Runner.bind(..)) &&
		target(out) &&
		args(in);
	
	public pointcut flush(Configuration configuration, Valuable iface) : 
		call(* flushRec(..)) && args(iface,configuration,..);

	private Runner() {
		
	}
	
	/**
	 * Runtime function : process the flush of the given interface.
	 * @param elementInterface the interface to flush.
	 * @throws ConfigurationException if flush is called for a service.
	 */
	public final void flushInterface(Valuable elementInterface) {
		this.flushRec(elementInterface,null, new ArrayList<Valuable>());
	}
	
	/**
	 * Runtime function : process the flush of the given interface.
	 * @param elementInterface the interface to flush.
	 * @param flushedInterfaces the interfaces already flushed in the current flush
	 * execution (this avoid infinite flush loop).
	 * @throws ConfigurationException if flush is called for a service.
	 */
	private final void flushRec(Valuable elementInterface,Configuration context, Collection<Valuable> flushedInterfaces) {
		if(elementInterface == null) {
			return;
		}
		if(flushedInterfaces.contains(elementInterface)) {
			return;
		}
		if(context == null) {
			context = elementInterface.getParent().getParentConfig();
		}
		
		flushedInterfaces.add(elementInterface);
		/*
		 * First run the local bindings
		 */
		this.bind(elementInterface,context,flushedInterfaces);
		/*
		 * Search in the attachment list if there is any attachment matching
		 * the interface asking for a flush.
		 */
		Iterator<Attachment> it = context.getAttachments().iterator();
		while(it.hasNext()) {
			Attachment currentAttachment = it.next();
			/*
			 * The flush concerns a component port.
			 */
			Valuable attachedInterface = currentAttachment.getAttachmentOf(elementInterface);
			if(attachedInterface != null) {
				if(attachedInterface instanceof ConnectorInterface) {
					attachedInterface.updateFrom(elementInterface);
					if(attachedInterface.getParent().getSubConfig() != null) {
						this.flushRec(attachedInterface,attachedInterface.getParent().getSubConfig(), flushedInterfaces);
					}
					else {
						/*
						 * Glue call will call recursively flush operation.
						 */
						ConnectorRole toFlush = ((Connector)attachedInterface.getParent()).getGlue().callGlue();
						this.flushRec(toFlush,null,flushedInterfaces);
					}
				}
				/*
				 * The flush concerns a connector role.
				 */
				if(attachedInterface instanceof ComponentPort) {
					attachedInterface.updateFrom(elementInterface);
					if(attachedInterface.getParent().getSubConfig() != null) {
						this.flushRec(attachedInterface, attachedInterface.getParent().getSubConfig(),flushedInterfaces);
					}
				}
				/*
				 * In HADL, configuration cannot be attached to other elements. To
				 * attach a configuration it needs to be the sub-configuration of an
				 * other element. In that case attachments are done on the parent
				 * element.
				 */
			}
		}
	}
	
	/**
	 * Runtime function : process the binding of the given interface.
	 * 
	 * The binding process is done on the current configuration context.
	 * @param elementInterface the interface to bind.
	 * @param flushedInterface the interfaces already flushed in the current flush
	 * execution (this avoid infinite flush loop).
	 */
	private final void bind(Valuable elementInterface,Configuration context, Collection<Valuable> flushedInterface) {
		if(elementInterface == null) {
			return;
		}
		ArrayList<Valuable> bindedInterfaces = new ArrayList<Valuable>();
		Iterator<Binding> it = context.getBindings().iterator();
		/*
		 * Search in the current bindings if there is some matching
		 * the given interface.
		 */
		while(it.hasNext()) {
			Binding currentBinding = it.next();
			Valuable bindedInterface = currentBinding.getBindingOf(elementInterface);
			if(bindedInterface != null && !flushedInterface.contains(bindedInterface)) {
				bindedInterface.updateFrom(elementInterface);
				bindedInterfaces.add(bindedInterface);
			}
		}
		/*
		 * No binding found.
		 */
		if(bindedInterfaces.isEmpty()) {
			return;
		}
		Iterator<Valuable> binded_it = bindedInterfaces.iterator();
		/*
		 * Process the binded interfaces and flush them.
		 */
		while(binded_it.hasNext()) {
			Valuable currentInterface = binded_it.next();
			try {
				this.flushRec(currentInterface, currentInterface.getParentConfig(), flushedInterface);
			}catch(Exception e) {
				System.out.println("Error in binding " + elementInterface.toString() + " with " + context.getName() + " : " + e.getMessage());
			}
		}
	}

}
