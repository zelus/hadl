package M2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import M2.exceptions.ConfigurationException;

public class Runner {

	private static Runner RUNNER_INSTANCE = null; 
	public static Runner getInstance() {
		if(RUNNER_INSTANCE == null) {
			RUNNER_INSTANCE = new Runner();
		}
		return RUNNER_INSTANCE;
	}
	
	public pointcut bindDelegation(Configuration configuration, Interface iface) :
		call(* flushRec(..)) &&
		withincode(* Runner.bind(..)) &&
		args(iface,configuration,..);
	
	public pointcut flushPropagate(Interface in, Interface out) :
		call( * updateFrom(..)) &&
		withincode(* Runner.flushRec(..)) &&
		target(out) &&
		args(in);
	
	public pointcut bindPropagate(Interface in, Interface out) :
		call( * updateFrom(..)) &&
		withincode(* Runner.bind(..)) &&
		target(out) &&
		args(in);
	
	public pointcut flush(Configuration configuration, Interface iface) : 
		call(* flushRec(..)) && args(iface,configuration,..);

	private Runner() {
		
	}
	
	/**
	 * Runtime function : process the flush of the given interface.
	 * @param elementInterface the interface to flush.
	 * @throws ConfigurationException if flush is called for a service.
	 */
	public final void flushInterface(Interface elementInterface) throws ConfigurationException{
		this.flushRec(elementInterface,null, new ArrayList<Interface>());
	}
	
	/**
	 * Runtime function : process the flush of the given interface.
	 * @param elementInterface the interface to flush.
	 * @param flushedInterfaces the interfaces already flushed in the current flush
	 * execution (this avoid infinite flush loop).
	 * @throws ConfigurationException if flush is called for a service.
	 */
	private final void flushRec(Interface elementInterface,Configuration context, Collection<Interface> flushedInterfaces) throws ConfigurationException {
		if(elementInterface == null) {
			return;
		}
		if(elementInterface instanceof ComponentService) {
			throw new ConfigurationException("Cannot flush a service");
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
			if(currentAttachment.getComponentPort().equals(elementInterface)) {
				currentAttachment.getConnectorRole().updateFrom(currentAttachment.getComponentPort());
				if(currentAttachment.getConnectorRole().getParent().getSubConfig() != null) {
					this.flushRec(currentAttachment.getConnectorRole(),currentAttachment.getConnectorRole().getParent().getSubConfig(), flushedInterfaces);
				}
				else {
					/*
					 * Glue call will call recursively flush operation.
					 */
					ConnectorRole toFlush = currentAttachment.getConnectorRole().getParent().getGlue().callGlue();
					this.flushRec(toFlush,null,flushedInterfaces);
				}
			}
			/*
			 * The flush concerns a connector role.
			 */
			if(currentAttachment.getConnectorRole().equals(elementInterface)) {
				currentAttachment.getComponentPort().updateFrom(currentAttachment.getConnectorRole());
				if(currentAttachment.getComponentPort().getParent().getSubConfig() != null) {
					this.flushRec(currentAttachment.getComponentPort(), currentAttachment.getComponentPort().getParent().getSubConfig(),flushedInterfaces);
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
	
	/**
	 * Runtime function : process the binding of the given interface.
	 * 
	 * The binding process is done on the current configuration context.
	 * @param elementInterface the interface to bind.
	 * @param flushedInterface the interfaces already flushed in the current flush
	 * execution (this avoid infinite flush loop).
	 */
	private final void bind(Interface elementInterface,Configuration context, Collection<Interface> flushedInterface) {
		ArrayList<Interface> bindedInterfaces = new ArrayList<Interface>();
		Iterator<Binding> it = context.getBindings().iterator();
		/*
		 * Search in the current bindings if there is some matching
		 * the given interface.
		 */
		while(it.hasNext()) {
			Binding currentBinding = it.next();
			if(elementInterface.equals(currentBinding.getComponentPort())) {
				if(!flushedInterface.contains(currentBinding.getConfigurationPort())) {
					currentBinding.getConfigurationPort().updateFrom(currentBinding.getComponentPort());
					bindedInterfaces.add(currentBinding.getConfigurationPort());
				}
			}
			if(elementInterface.equals(currentBinding.getConfigurationPort())) {
				if(!flushedInterface.contains(currentBinding.getComponentPort())) {
					currentBinding.getComponentPort().updateFrom(currentBinding.getConfigurationPort());
					bindedInterfaces.add(currentBinding.getComponentPort());
				}
			}
			if(elementInterface.equals(currentBinding.getConnectorRole())) {
				if(!flushedInterface.contains(currentBinding.getConfigurationRole())) {
					currentBinding.getConfigurationRole().updateFrom(currentBinding.getConnectorRole());
					bindedInterfaces.add(currentBinding.getConfigurationRole());
				}
			}
			if(elementInterface.equals(currentBinding.getConfigurationRole())) {
				if(!flushedInterface.contains(currentBinding.getConnectorRole())) {
					currentBinding.getConnectorRole().updateFrom(currentBinding.getConfigurationRole());
					bindedInterfaces.add(currentBinding.getConnectorRole());
				}
			}
		}
		/*
		 * No binding found.
		 */
		if(bindedInterfaces.isEmpty()) {
			return;
		}
		Iterator<Interface> binded_it = bindedInterfaces.iterator();
		/*
		 * Process the binded interfaces and flush them.
		 */
		while(binded_it.hasNext()) {
			Interface currentInterface = binded_it.next();
			try {
				if(currentInterface.getParent() instanceof Configuration) {
					this.flushRec(currentInterface,(Configuration)currentInterface.getParent(), flushedInterface);
				}
				else if(currentInterface.getParent() instanceof Component) {
					this.flushRec(currentInterface, currentInterface.getParent().getParentConfig(), flushedInterface);	
				}
				else if(currentInterface.getParent() instanceof Connector) {
					this.flushRec(currentInterface, currentInterface.getParent().getParentConfig(), flushedInterface);
				}
			}catch(Exception e) {
				System.out.println("Error in binding " + elementInterface.getName() + " with " + context.getName() + " : " + e.getMessage());
			}
		}
	}

}
