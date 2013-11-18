package M1.overview;

import java.util.Iterator;

import M2.Attachment;
import M2.Component;
import M2.ComponentPort;
import M2.Configuration;
import M2.Element;
import M2.Property;

public class OneServerAttachment extends Property {

	public OneServerAttachment() {
		super("OneServerAttachment","Only one attachment to the server is present in the architecture");
	}
	
	@Override
	public boolean checkInvariant(Element element) {
		Configuration config = (Configuration)element;
		Iterator<Attachment> it = config.getAttachments().iterator();
		Component server = config.getComponent("Server");
		ComponentPort serverReqPort = server.getReqPort("ServerReceiveRequestPort");
		int attachmentNumber = 0;
		while(it.hasNext()) {
			Attachment attachment = it.next();
			if(attachment.getAttachmentOf(serverReqPort) != null) {
				attachmentNumber++;
			}
		}
		return(attachmentNumber == 1);
	}

}
