package postgresql;

public class Create {

	
	public static boolean createTable(String tb)
	{
		String query = "CREATE TABLE " + tb + ";";
		return DbBase.getInstance().executeQuery(query);
	}
	
}
