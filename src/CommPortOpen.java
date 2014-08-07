import java.awt.*;
import java.io.*;

import javax.comm.*;
/**
* Open a serial port using Java Communications.
*
*/
public class CommPortOpen extends Thread {
	/** How long to wait for the open to finish up. */
	public static final int TIMEOUTSECONDS = 30;
	/** The baud rate to use. */
	public static final int BAUD = 9600;
	/** The parent Frame, for the chooser. */
	protected Frame parent;
	/** The input stream */
	protected BufferedReader is;
	/** The output stream */

	protected String response;
	/** A flag to control debugging output. */
	protected boolean debug = true;
	
	/** The chosen Port Identifier */
	CommPortIdentifier thePortID;
	/** The chosen Port itself */
	CommPort thePort;
	public static void main(String[] argv)
	throws IOException, NoSuchPortException, PortInUseException,
	UnsupportedCommOperationException {
	new CommPortOpen(null).converse();
	System.exit(0);
	}
	/* Constructor */
	public CommPortOpen(Frame f)
	throws IOException, NoSuchPortException, PortInUseException,
	UnsupportedCommOperationException {
	// Use the PortChooser from before. Pop up the JDialog.
	PortChooser chooser = new PortChooser(null);
	String portName = null;
	do {
	chooser.setVisible(true);
	// Dialog done. Get the port name.
	portName = chooser.getSelectedName();
	if (portName == null)
	System.out.println("No port selected. Try again.\n");
	} while (portName == null);
	// Get the CommPortIdentifier.
	thePortID = chooser.getSelectedIdentifier();
	// Now actually open the port.
	// This form of openPort takes an Application Name and a timeout.
	//
	System.out.println("Trying to open " + thePortID.getName() + "...");
	switch (thePortID.getPortType()) {
	case CommPortIdentifier.PORT_SERIAL:
	thePort = thePortID.open("DarwinSys DataComm",
	TIMEOUTSECONDS * 1000);
	SerialPort myPort = (SerialPort) thePort;
	// set up the serial port
	myPort.setSerialPortParams(BAUD, SerialPort.DATABITS_8,
	SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
	break;
	case CommPortIdentifier.PORT_PARALLEL:
	thePort = thePortID.open("DarwinSys Printing",
	TIMEOUTSECONDS * 1000);
	ParallelPort pPort = (ParallelPort)thePort;
	// Tell API to pick "best available mode" - can fail!
	// myPort.setMode(ParallelPort.LPT_MODE_ANY);
	// Print what the mode is
	int mode = pPort.getMode();
	switch (mode) {
	case ParallelPort.LPT_MODE_ECP:
	System.out.println("Mode is: ECP");
	break;
	case ParallelPort.LPT_MODE_EPP:
	System.out.println("Mode is: EPP");
	break;
	case ParallelPort.LPT_MODE_NIBBLE:
	System.out.println("Mode is: Nibble Mode.");
	break;
	case ParallelPort.LPT_MODE_PS2:
	System.out.println("Mode is: Byte mode.");
	break;
	case ParallelPort.LPT_MODE_SPP:
	System.out.println("Mode is: Compatibility mode.");
	break;
	// ParallelPort.LPT_MODE_ANY is a "set only" mode;
	// tells the API to pick "best mode"; will report the
	// actual mode it selected.
	default:
	throw new IllegalStateException
	("Parallel mode " + mode + " invalid.");
	}
	break;
	default:// Neither parallel nor serial??
	throw new IllegalStateException("Unknown port type " + thePortID);
	}
	// Get the input and output streams
	// Printers can be write-only
	try {
	is = new BufferedReader(new InputStreamReader( thePort.getInputStream()));
	System.out.println("COM port open");
	} catch (IOException e) {
		System.err.println("Can't open input stream: write-only");
		is = null;
		}
		}
		/** This method will be overridden by non-trivial subclasses
		* to hold a conversation.
		 * @return 
		*/
		protected String converse() throws IOException {

		// Input/Output code not written -- must subclass.
		// Finally, clean up.
			//System.out.println(is.readLine());
		return is.readLine();


		}
		void chiudiStream()
		{
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("I can't close the input stream!!");
			}
		}
		}

