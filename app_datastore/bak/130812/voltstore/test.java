
import java.sql.*;

public class test {
	public void dbconnect() {
        int id;
        int name;
        try {
            // JDBCドライバの登録
            String driver = "org.postgresql.Driver";
            // データベースの指定
            String server   = "localhost:5432";   // PostgreSQL サーバ ( IP または ホスト名 )
            String dbname   = "postgres";         // データベース名
            String url = "jdbc:postgresql://" + server + "/" + dbname;
            String dbuser     = "postgres";         //データベース作成ユーザ名
            String dbpassword = "postin";     //データベース作成ユーザパスワード
            Class.forName (driver);
            // データベースとの接続
            System.out.println("Start connecting Database");
            Connection con = DriverManager.getConnection(url, dbuser, dbpassword);
	    System.out.println("Connect to Database");
	    // テーブル照会実行
            Statement stmt = con.createStatement ();
            String sql = "SELECT * FROM test";
            ResultSet rs = stmt.executeQuery (sql);
            // テーブル照会結果を出力
            while(rs.next()){
               id = rs.getInt("id");
               name = rs.getInt("name");
               System.out.println("ID：" + id);
               System.out.println("名前：" + name);
            }
            // データベースのクローズ
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.err.println("SQL failed.");
            e.printStackTrace ();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace ();
        }
    }
}
