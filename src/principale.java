import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.LinkedList;

import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.UnsupportedCommOperationException;


public class principale extends Frame implements WindowListener ,ActionListener {

	Image sfondo;
	public TextField[] etichette_sfondo; 
	static String[] offset_letture = {"1 sec","10 sec","20 sec", "40 sec", "1 min", "10 min", "30 min"};
	
	//i thread
	static Read_thread read;
	
	
	public principale(String s) {
		super("Lettore dati");    //construct Frame part of Gui
	    setBackground(Color.lightGray);
	    setLayout(null); 
	    addWindowListener(this);
	    this.setSize(800, 600);
	    MediaTracker mt = new MediaTracker(this);
	    sfondo = Toolkit.getDefaultToolkit().createImage("SCHERMO_1.gif");
	    mt.addImage(sfondo, 0);
	    TextField[] temp ={new TextField(),new TextField(),new TextField(),new TextField(),new TextField(),new TextField(),new TextField(),new TextField()};
	    etichette_sfondo = temp;
	    initializeTextFields(etichette_sfondo[0], 20, 202); 	//volt
	    initializeTextFields(etichette_sfondo[1], 85, 132); 	//Watt
	    initializeTextFields(etichette_sfondo[2], 146, 202); 	//amp
	    initializeTextFields(etichette_sfondo[3], 351, 402); 	//Tc
	    initializeTextFields(etichette_sfondo[4], 351, 454); 	//Tu
	    initializeTextFields(etichette_sfondo[5], 440, 509); 	//Tw-1
	    initializeTextFields(etichette_sfondo[6], 440, 555); 	//Tw-2
	    initializeTextFields(etichette_sfondo[7], 50,  50 ); 	//pressione
	   
	}

	public void update(Graphics g){
		paint(g);
	}
		  
	public void paint(Graphics g){
		if(sfondo != null)
			g.drawImage(sfondo, 0, 0, this);
		else
	      g.clearRect(0, 0, getSize().width, getSize().height);
	  }
	
	
	private void initializeTextFields(TextArea finestra_testo)
	{
		// add textfield to show color of figure
		finestra_testo.setLocation(5,600);
		finestra_testo.setSize(800,100);
		finestra_testo.setBackground(Color.white);
		add(finestra_testo);	
	}
	private void initializeTextFields(TextField finestra_testo, int x, int y)
	{
		// add textfield to show color of figure
		finestra_testo.setLocation(x,y);
		finestra_testo.setSize(115,25);
		finestra_testo.setBackground(Color.white);
		add(finestra_testo);	
	}
	
	public static void main(String[] args) {
		
		
		Container_file file = new Container_file();
		LinkedList<GruppoDati> buffer = new LinkedList<GruppoDati>();
		log_thread log = new log_thread(file);
		TextArea finestra_testo = new TextArea("Welcome",0,0,TextArea.SCROLLBARS_VERTICAL_ONLY);
		principale finestra = new principale("Lettore Dati"); 
		finestra.initializeTextFields(finestra_testo);
		finestra.initializeMenuComponents();
		
		Write_thread write = new Write_thread(file,buffer, finestra_testo, finestra.etichette_sfondo);
		finestra.setSize(810,710);
		finestra.setVisible(true); 
		
		try {
			read = new Read_thread(buffer,9500L);
		} catch (IOException e) {
			System.out.println("errore");
			e.printStackTrace();
			return; 
		} catch (NoSuchPortException e) {
			System.out.println("errore");
			e.printStackTrace();
			return;
		} catch (PortInUseException e) {
			System.out.println("errore");
			e.printStackTrace();
			return;
		} catch (UnsupportedCommOperationException e) {
			System.out.println("errore");
			e.printStackTrace();
			return;
		}
		
		log.start();
		read.start();
		write.start();

	}
	
	
	private void initializeMenuComponents() //creo i menu
	{
		// add pull-down menu to menu bar
		MenuBar bar = new MenuBar();

		// add colours menu		
		Menu intervalli = new Menu("Sampling interval");
		for (int index=0; index < offset_letture.length; index++)
		{
			intervalli.add(offset_letture[index]);
		}
		bar.add(intervalli);
		intervalli.addActionListener(this);
		setMenuBar(bar);
	}

	
	
	// metodi da implematare per via delle classi in implements
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		
		System.exit(0);
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		String evento = arg0.getActionCommand();
		if(offset_letture[0].equals(evento))
		{
			read.setOffset(1000L);
		}
		else if(offset_letture[1].equals(evento))
		{
			read.setOffset(10000L);
		}
		else if(offset_letture[2].equals(evento))
		{
			read.setOffset(20000L);
		}
		else if(offset_letture[3].equals(evento))
		{
			read.setOffset(40000L);
		}
		else if(offset_letture[4].equals(evento))
		{
			read.setOffset(60000L);
		}
		else if(offset_letture[5].equals(evento))
		{
			read.setOffset(600000L);
		}
		else if(offset_letture[6].equals(evento))
		{
			read.setOffset(1800000L);
		}
		
	}
}
