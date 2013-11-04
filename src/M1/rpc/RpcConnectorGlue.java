package M1.rpc;

import M2.ConnectorGlue;
import M2.ConnectorRole;
import M2.exceptions.GlueException;

public class RpcConnectorGlue extends ConnectorGlue {

	public RpcConnectorGlue(ConnectorRole fromRole, ConnectorRole toRole)
			throws GlueException {
		super(fromRole, toRole);
	}
	
	@Override
	protected void runGlue() {
		this.toRole.setValue(this.fromRole.getValue()+" pouet");
	}

}
