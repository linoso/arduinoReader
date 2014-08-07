
public class ScrittoreMySql extends Thread {
	 java.sql.Connection conn;
	 String user = "lino";
	 String pass = "pass";
	 String host = "localhost";
	public  ScrittoreMySql() {

        System.out.println("SQL Test");

        try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                conn = java.sql.DriverManager.getConnection("jdbc:mysql://"+host+"?user="+user+"&password="+pass);

        }
        catch (Exception e) {
                System.out.println("ERROR: Connection whit Database not avaible");

                }
	}
	
	public void insert_db() {
		
	}
}
