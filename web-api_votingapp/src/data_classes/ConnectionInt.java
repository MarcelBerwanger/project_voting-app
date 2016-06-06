package data_classes;

import java.util.ArrayList;

import javax.jws.*;
import javax.jws.soap.SOAPBinding;

@WebService(name="ConnectionService", endpointInterface="data_classes.Connection")
@SOAPBinding(style= SOAPBinding.Style.RPC)
public interface ConnectionInt {
	
	@WebMethod ArrayList<String> requestData(String statement);
	@WebMethod(operationName="writeData") boolean writeData(String statement);
	void runService();
	void stopService();
}