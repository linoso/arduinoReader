import java.io.*;
import javax.comm.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
public class PortChooser extends JDialog implements ItemListener
{
	/** A mapping from names to CommPortIdentifiers. */
	protected HashMap map = new HashMap();
	/** The name of the choice the user made. */
	protected String selectedPortName;
	/** The CommPortIdentifier the user chose. */
	protected CommPortIdentifier selectedPortIdentifier;
	/** The JComboBox for serial ports */
	protected JComboBox serialPortsChoice;
	/** The JComboBox for parallel ports */
	protected JComboBox parallelPortsChoice;
	/** The JComboBox for anything else */
	protected JComboBox other;
	/** The SerialPort object */
	protected SerialPort ttya;
	/** To display the chosen */
	protected JLabel choice;
	/** Padding in the GUI */
	protected final int PAD = 5;


/** This will be called from either of the JComboBoxes when the
* user selects any given item.
*/
public void itemStateChanged(ItemEvent e) {
// Get the name
selectedPortName = (String)((JComboBox)e.getSource()).getSelectedItem();
// Get the given CommPortIdentifier
selectedPortIdentifier = (CommPortIdentifier)map.get(selectedPortName);
// Display the name.
choice.setText(selectedPortName);
}
/* The public "getter" to retrieve the chosen port by name. */
public String getSelectedName() {
return selectedPortName;
}
/* The public "getter" to retrieve the selection by CommPortIdentifier. */
public CommPortIdentifier getSelectedIdentifier() {
return selectedPortIdentifier;
}
/** A test program to show up this chooser. */
public static void main(String[] ap) {
PortChooser c = new PortChooser(null);
c.setVisible(true);// blocking wait
System.out.println("You chose " + c.getSelectedName() +
" (known by " + c.getSelectedIdentifier() + ").");
System.exit(0);
}
/** Construct a PortChooser --make the GUI and populate the ComboBoxes.
*/
public PortChooser(JFrame parent) {
super(parent, "Port Chooser", true);
makeGUI();
populate();
finishGUI();
}

/** Build the GUI. You can ignore this for now if you have not
* yet worked through the GUI chapter. Your mileage may vary.
*/
protected void makeGUI() {
Container cp = getContentPane();
JPanel centerPanel = new JPanel();
cp.add(BorderLayout.CENTER, centerPanel);
centerPanel.setLayout(new GridLayout(0,2, PAD, PAD));
centerPanel.add(new JLabel("Serial Ports", JLabel.RIGHT));
serialPortsChoice = new JComboBox();
centerPanel.add(serialPortsChoice);
serialPortsChoice.setEnabled(false);
centerPanel.add(new JLabel("Parallel Ports", JLabel.RIGHT));
parallelPortsChoice = new JComboBox();
centerPanel.add(parallelPortsChoice);
parallelPortsChoice.setEnabled(false);
centerPanel.add(new JLabel("Unknown Ports", JLabel.RIGHT));
other = new JComboBox();
centerPanel.add(other);
other.setEnabled(false);
centerPanel.add(new JLabel("Your choice:", JLabel.RIGHT));
centerPanel.add(choice = new JLabel());
JButton okButton;
cp.add(BorderLayout.SOUTH, okButton = new JButton("OK"));
okButton.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
PortChooser.this.dispose();
}
});
}
/** Populate the ComboBoxes by asking the Java Communications API
* what ports it has. Since the initial information comes from
* a Properties file, it may not exactly reflect your hardware.
*/
protected void populate() {
// get list of ports available on this particular computer,
// by calling static method in CommPortIdentifier.
Enumeration pList = CommPortIdentifier.getPortIdentifiers();
//Process the list, putting serial and parallel into ComboBoxes
while (pList.hasMoreElements()) {
CommPortIdentifier cpi = (CommPortIdentifier)pList.nextElement();
// System.out.println("Port " + cpi.getName());
map.put(cpi.getName(), cpi);
if (cpi.getPortType() == CommPortIdentifier.PORT_SERIAL) {
serialPortsChoice.setEnabled(true);
serialPortsChoice.addItem(cpi.getName());
} else if (cpi.getPortType() == CommPortIdentifier.PORT_PARALLEL) {
parallelPortsChoice.setEnabled(true);
parallelPortsChoice.addItem(cpi.getName());
} else {
other.setEnabled(true);
other.addItem(cpi.getName());
}
}
serialPortsChoice.setSelectedIndex(-1);
parallelPortsChoice.setSelectedIndex(-1);
}
protected void finishGUI() {
serialPortsChoice.addItemListener(this);
parallelPortsChoice.addItemListener(this);
other.addItemListener(this);
pack();
//addWindowListener(new WindowCloser(this, true));
}
}