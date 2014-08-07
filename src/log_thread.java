public class log_thread extends Thread {
	Long target,after,offset;
	Container_file file;
	
	public void setOffset(Long newoffset)
	{
		synchronized (offset) {
		
			offset = newoffset;
		}
	}
	
	public log_thread(Container_file file) {
		target=0L;
		after=0L;
		offset = 3600000L;
		this.file = file;
	}
	@Override
	public void run() {
		while(true){
			after=0L;
			target = System.currentTimeMillis()+offset;
			after=System.currentTimeMillis();
			while(target>after){
				try {
					Thread.sleep(target-after);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				after=System.currentTimeMillis();
			}
			synchronized (file) {
				if(file.aggiorna())
				{
					System.out.println("New log file created");
				}
				else
				{
					System.out.println("ERROR: Log file not created");
				}
				
			}
		}
		
	}

}
