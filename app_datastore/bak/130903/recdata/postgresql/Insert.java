package postgresql;

public class Insert {

	
	public static boolean insertToTable(String data)
	{
		String query = "INSERT INTO " + data;
		return DbBase.getInstance().executeQuery(query);
	}

	/**
	 * テーブル「test」へのデータのインサート
	 * @param id
	 * @param name
	 * @return
	 */
	public static boolean insertToTest(String id, String name)
	{
		String query = "INSERT INTO test(id, name) VALUES(" + id + ",'" + name + "');";
		return DbBase.getInstance().executeQuery(query);
	}
	
}
