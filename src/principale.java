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


public class principale  {

	//i thread
	static Read_thread read;
	

	
	public static void main(String[] args) {
		
		
		Container_file file = new Container_file();
		LinkedList<GruppoDati> buffer = new LinkedList<GruppoDati>();
		log_thread log = new log_thread(file);

		Write_thread write = new Write_thread(file,buffer);

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

}
