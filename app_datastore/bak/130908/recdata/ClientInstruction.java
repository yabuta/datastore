
/**
 * クライアントが送サーバーに信するデータの先頭に着ける識別子
 */

public enum ClientInstruction {
	
	INST_QUIT	(0),		//接続終了要求
	INST_INSERT	(1),		//DBへのインサート
	INST_CREATE	(2),		//テーブルの作成
	INST_DROP	(3),		//テーブルの削除
	INST_COUNT	(4);		//命令数
	
	
	
	private int num;
	
	private ClientInstruction(int n)
	{
		num = n;
	}
	
	public int getNum()
	{
		return num;
	}
	
}
