package data_classes;

import javax.jws.*;
import javax.jws.soap.SOAPBinding;

@WebService(name="ConnectionService", endpointInterface="data_classes.Connection")
@SOAPBinding(style= SOAPBinding.Style.RPC)
public interface ConnectionInt {
	
	@WebMethod String requestData();
	@WebMethod(operationName="writeData") void writeData();
	void runService();
	void stopService();
}