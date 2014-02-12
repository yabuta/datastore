package postgresql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbBase {
	/**	
	 * データベース情報の設定（PostgreSQL）
	 * dbDriver: ドライバ
	 * 	 dbHost: IPアドレス
	 *   dbPort: 接続ポート
	 *   dbName: データベース名
	 *    dbUrl: 接続URL
	 *   dbUser: 接続ユーザ
	 *   dbPass:　接続パスワード
	 */
	private static final String dbDriver = "org.postgresql.Driver";
	private static final String dbHost = "localhost";
	private static final String dbPort = "5432";
	private static final String dbName = "postgres";
	private static final String dbUrl = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;
	private static final String dbUser = "postgres";
	private static final String dbPass = "postin";
	
	//シングルトンで実装 （データベースとの接続は常に１つ）
	private static DbBase db = new DbBase();
	private static Connection con = null;

	private DbBase(){}

	/**
	 * データベースに接続する
	 * @return
	 * 	 	 true: 接続成功 or すでに接続されている
	 *  	false:　接続失敗
	 */
	public boolean connect()
	{
		//con!=nullの場合、すでに接続が確立されているはずなのでtrueを返す
		if (con != null) return true;
		
		try{
			// データベースの指定
		    Class.forName (dbDriver);
		    // データベースとの接続
		    con = DriverManager.getConnection(dbUrl, dbUser, dbPass);
		    System.out.println("データベース\"" + dbName + "\"に接続しました。");
		    return true;
		}
		catch(ClassNotFoundException e){
			System.err.println("ドライバのロードに失敗しました。CLASSPATHの設定を確認してください。");
			e.printStackTrace();
			return false;
		}
		catch(SQLException e){
			System.err.println("データベース\"" + dbName + "\"への接続に失敗しました。");
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * データベースとの接続を終了する
	 * @return
	 *     	 true: 切断成功
	 *    	false: 切断失敗 or すでに接続されていない
	 */
	public boolean disConnect()
	{
		if(con == null) return true;
		
		try{
			con.close();
			con = null;
			return true;
		}
		catch(SQLException e){
			System.err.println("接続の終了に失敗しました。");
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * インスタンスを返す
	 * @return
	 */
	public static DbBase getInstance()
	{
		return db;
	}

	/**
	 * データベースとの接続を返す
	 * @return
	 */
	public Connection getConnection()
	{
		return con;
	}
	
	/**
	 * 引数で指定されたSQLクエリを実行する
	 * @param query
	 * @return
	 */
	public boolean executeQuery(String query)
	{
		try{
			Statement stmt = con.createStatement();
			stmt.executeUpdate(query);
			System.out.println("クエリを実行しました: " + query);
			
			stmt.close();
			return true;
		}
		catch(SQLException e){
			System.out.println("クエリの実行に失敗しました: " + query);
			while(e != null){
				System.err.println(e.getMessage());
				System.err.println(e.getSQLState());
				System.err.println(e.getErrorCode());
				e.getNextException();
			}
			return false;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	
}
