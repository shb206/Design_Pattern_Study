package client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;

import static java.nio.charset.StandardCharsets.*;

public class MainClient2 {
	private static final String URL = "http://localhost:8080";
	// private static final String URL = "https://www.google.com";
	private static final String POST = "POST";
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36";
	// private static final String DATA = "?tesVal=3";
	// POST 요청시
//	protected boolean doOutput = false;

	public static void main(String[] args) throws IOException {
		//getRequestTest();
		//postRequestTest();
		//ApacheTest apTest = new ApacheTest();
		// apTest.apacheGet();
		//apTest.apachePost();
		testSample();
	}

	// 기본 URL 클래스
	public static void getRequestTest() {
		try {
			URL url = new URL(URL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", USER_AGENT);
			connection.setDoOutput(true);
			
			OutputStream os = connection.getOutputStream();
			/*
			 * DataOutputStream dos = new DataOutputStream(os);
			 * 
			 * 
			 * dos.writeUTF("111테스트 할게영~~~~~~"); dos.flush(); dos.close();
			 */
			
			os.write("test테스트".getBytes());
			os.flush();
			os.close();


			// Send Request
//			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
//			wr.writeBytes(DATA);
//			wr.flush();			wr.close();

			// Get Response
			int responseCode = connection.getResponseCode();
			connection.setDoInput(true);
			InputStream ios = connection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
			StringBuffer stringBuffer = new StringBuffer();
			String inputLine;

			while ((inputLine = bufferedReader.readLine()) != null) {
				stringBuffer.append(inputLine);
			}
			bufferedReader.close();

			String response = stringBuffer.toString();

			System.out.println("응답 : " + response);
			System.out.println("응답 코드 : " + responseCode);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void postRequestTest() {
		try {
			URL url = new URL(URL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			
			connection.setRequestMethod("POST");
			connection.setRequestProperty("User-Agent", USER_AGENT);
			connection.setDoOutput(true);

			OutputStream os = connection.getOutputStream();
			os.write("test테스트".getBytes("UTF-8"));
			os.flush();
			os.close();	
			System.out.println("os "+ os);
			
			int responseCode = connection.getResponseCode();
			

			// Get Response
			Charset charset = Charset.forName("UTF-8");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			//DataInputStream in = new DataInputStream(connection.getErrorStream());
			System.out.println("현재 연결 인코딩 : "+bufferedReader.readLine());
			StringBuffer stringBuffer = new StringBuffer();
			String inputLine = "";

			
			  while ((inputLine = bufferedReader.readLine()) != null) {
				  stringBuffer.append(inputLine); 
				  System.out.println("test333 : "+inputLine);
			  
			  }
			 
			bufferedReader.close();

			String response = stringBuffer.toString();
			
			System.out.println("응답 : " + response);
			System.out.println("응답 코드 : " + responseCode);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 아파치 API 사용
	public static class ApacheTest {
		// GET 요청
		public void apacheGet() throws IOException {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			String data = "한글 테스트";

			try {
				HttpUriRequest httpget = RequestBuilder.get().setUri(new URI(URL)).addParameter("tesVal", data).build();

				// 클래스 정보 찾아볼것
				CloseableHttpResponse response = httpclient.execute(httpget);
				try {
					System.out.println(EntityUtils.toString(response.getEntity()));
				} finally {
					response.close();
				}
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				httpclient.close();
			}
		}

		// POST 요청
		public void apachePost() throws IOException {
			// Map<String, Object> data = new HashMap<String, Object>();
			// data.put("TestVal", "~~~~~~~~~~JSON~~~~~~~~~~~"); JSONObject jsonOb = new
			// JSONObject();
			// jsonOb.put("testVal", "~~~~~~~~~~JSON~~~~~~~~~~~");

			try {
				HttpClient client = HttpClientBuilder.create().build();
				HttpPost postRequest = new HttpPost(URL); // POST 메소드 URL 새성
				postRequest.setHeader("Accept", "application/json");
				postRequest.setHeader("Connection", "keep-alive");
				postRequest.setHeader("Content-Type", "application/json");

				// `postRequest.addHeader("x-api-key", RestTestCommon.API_KEY); //KEY 입력 //
				// postRequest.addHeader("Authorization", token); // token 이용시
				// postRequest.setEntity(new StringEntity(jsonOb)); //json 메시지 입력
				HttpResponse response = client.execute(postRequest); // Response 출력
				if (response.getStatusLine().getStatusCode() == 200) {
					ResponseHandler<String> handler = new BasicResponseHandler();
					String body = handler.handleResponse(response);
					System.out.println(body);
				} else {
					System.out.println("response is error : " + response.getStatusLine().getStatusCode());
				}
			} catch (Exception e) {
				System.err.println(e.toString());
			}

			/*
			 * CloseableHttpClient httpclient = HttpClients.createDefault(); try {
			 * HttpUriRequest httppost = RequestBuilder.post() .setUri(new URI(URL))
			 * .addParameter("testVal", "~~~~~~~~~~JSON~~~~~~~~~~~") .build();
			 * 
			 * CloseableHttpResponse response = httpclient.execute(httppost); try {
			 * System.out.println(EntityUtils.toString(response.getEntity())); } finally {
			 * response.close(); } } catch (URISyntaxException e) { e.printStackTrace(); }
			 * finally { httpclient.close(); }
			 */
		}
		
		

		

	}
	public static void testSample() throws IOException {
	    final String URL_TEST = "https://www.google.com";
	    final String POST = "POST";
	    final String USER_AGENT = "Mozilla/5.0";
	    final String DATA = "test data 테스트";

	    	System.out.println("테스트 합쉬다~~");
	        URL url = new URL(URL);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        

	        connection.setRequestMethod(POST);
	        connection.setRequestProperty("User-Agent", USER_AGENT);
	        connection.setDoOutput(true);
	        connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=*****;charset=utf-8");

	        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
	        
	        outputStream.writeUTF(DATA);
	        outputStream.flush();
	        outputStream.close();

	        int responseCode = connection.getResponseCode();

	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
	        StringBuffer stringBuffer = new StringBuffer();
	        String inputLine;

	        while ((inputLine = bufferedReader.readLine()) != null)  {
	            stringBuffer.append(inputLine);
	        }
	        bufferedReader.close();

	        String response = stringBuffer.toString();
	        System.out.println(response);
	    }
}
