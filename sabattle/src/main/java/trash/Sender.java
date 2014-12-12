package trash;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.DefaultClientConnection;

public class Sender {
	public static void main (String[] args) throws IOException{

		System.out.println("Sender");
		
		HttpPost post = new HttpPost("http://169.254.84.57:9095");
		post.setHeader("Headerrr", "sdfsdf");
		HttpClient client = new DefaultHttpClient();
		client.execute(post);
	}
}
