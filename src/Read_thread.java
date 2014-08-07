import java.io.*;

import javax.comm.*;

import java.util.*;


public class Read_thread extends CommPortOpen implements Runnable {
	
	LinkedList<GruppoDati> buffer;
	Long offset;
	public Read_thread(LinkedList<GruppoDati> buffer, Long offset) throws IOException, NoSuchPortException, PortInUseException, UnsupportedCommOperationException {
	super(null);	// TODO Auto-generated constructor stub
	this.buffer = buffer;
	this.offset = offset;
	}
	Long target, now;
	public void setOffset(Long newoffset)
	{
		synchronized (offset) {
		
			offset = newoffset;
		}
	}
	@Override
	public void run() {
		
		now =System.currentTimeMillis();
		target = now +offset;
		GruppoDati attuale = new GruppoDati();
		//System.out.println("target = "+target.toString()+" now ="+now.toString());
		while(true)
		{
			now = System.currentTimeMillis();
			//System.out.println("target = "+target.toString()+" now ="+now.toString());
			if(target> now)
			{
				try {
					//System.out.println("sono qui e leggo");
					String temp = this.converse();
					attuale.aggiungi_dati(temp);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("ERROR: Data stram from COM interrupted");
				}
			}
			else
			{
			synchronized (buffer) {
				
				buffer.add(attuale);
				buffer.notify();
				//System.out.println("ho svegliato scrittore");
			}
			target = now +offset;
			}
		}
	}

}
