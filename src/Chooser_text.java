import java.util.Enumeration;
import java.util.HashMap;

import javax.comm.*;


public class Chooser_text {
	
	
	
	void select_port()
	{
		Enumeration portList = CommPortIdentifier.getPortIdentifiers();
		while (portList.hasMoreElements()) {
			CommPortIdentifier cpi = (CommPortIdentifier)portList.nextElement();
			// System.out.println( cpi.getName());
			if(cpi.getPortType() == CommPortIdentifier.PORT_SERIAL)
			{
				System.out.println("Port " +cpi.getName());
			}
		}
	}
	Chooser_text()
	{}
	

}
