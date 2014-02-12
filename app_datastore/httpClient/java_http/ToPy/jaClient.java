import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;



public class jaClient { 

    private static final String TARGET_HOST = "http://db1.ertl.jp/cgi-bin/getData/exesql.py"; 



    private static boolean sendSQL(String sql){
	DefaultHttpClient httpclient = null; 

	HttpPost post = null; 

	HttpEntity entity = null; 


	try { 


	    httpclient = new DefaultHttpClient(); 


	    HttpParams httpParams = httpclient.getParams(); 

	    //接続確立のタイムアウトを設定（単位：ms） 
	    HttpConnectionParams.setConnectionTimeout(httpParams, 500*1000); 


	    //接続後のタイムアウトを設定（単位：ms） 
	    HttpConnectionParams.setSoTimeout(httpParams, 500*1000); 

	    post = new HttpPost(TARGET_HOST); 


	    post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"); 

	    ArrayList<NameValuePair> params = new ArrayList<NameValuePair>(); 

	    params.add(new BasicNameValuePair("sql", sql)); 

            post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8)); 


	    final HttpResponse response = httpclient.execute(post); 


	    // レスポンスヘッダーの取得(ファイルが無かった場合などは404) 


	    System.out.println("StatusCode=" + response.getStatusLine().getStatusCode()); 

	    

	    if(response.getStatusLine().getStatusCode() != 200 ){ 

		System.out.println("StatusCode:" + response.getStatusLine().getStatusCode()); 


		return false; 


	    } 


	    entity = response.getEntity(); 



	    // entityが取れなかった場合は、Connectionのことは心配しないでもOK 


	    if (entity != null) { 


		System.out.println(EntityUtils.toString(entity)); 


		System.out.println("length: " + entity.getContentLength()); 


		EntityUtils.consume(entity); 

		//depriciated 


		entity.consumeContent(); 

		post.abort(); 

             } 


	    System.out.println("結果を取得しました。"); 


	}catch (Exception e) { 
	    e.printStackTrace(); 
	    return false;

	}finally { 


	    httpclient.getConnectionManager().shutdown(); 
	    return true;

	}


    } 

    public final static void main(String[] args) throws Exception { 

	
	if(args[0].equals("")){
	    System.err.printf("need argument.(ex:select * from test;");
	    System.exit(1);
	    
	}else{

	    if(!sendSQL(args[0])){
		System.err.println("sending message is failed.");
		System.exit(1);

	    }
	}

    }

}
