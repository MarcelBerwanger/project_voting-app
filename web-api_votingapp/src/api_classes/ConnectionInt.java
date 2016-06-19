package api_classes;

import java.sql.ResultSet;
import javax.jws.*;
import javax.jws.soap.SOAPBinding;

@WebService(name="ConnectionService", endpointInterface="api_classes.Connection")
@SOAPBinding(style= SOAPBinding.Style.RPC)
public interface ConnectionInt {
	
	@WebMethod ResultSet requestData(String ustmt);
	@WebMethod(operationName="writeData") boolean writeData(String ustmt);
	void runService();
	void stopService();
}