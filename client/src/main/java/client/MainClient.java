package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MainClient {
	private static final String URL = "http://localhost:8080";
	// private static final String URL = "https://www.google.com";
	private static final String POST = "POST";
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36";
	// private static final String DATA = "?tesVal=3";
	// POST 요청시
//	protected boolean doOutput = false;

	public static void main(String[] args) throws IOException, URISyntaxException, ParseException {
		//getURLTest();
		readJson();
		//postRequestTest();
		
		
		//ApacheTest apTest = new ApacheTest();
		//apTest.apacheGet();
		//apTest.apachePost();
	}

	// 기본 URL 클래스
	public static void getURLTest() throws UnsupportedEncodingException {
		
		StringBuilder urlBuilder = new StringBuilder("http://api.odcloud.kr/api/EvInfoServiceV2/v1/getEvSearchList");
		String serviceKey = "QKTa2omkRMLG0WLnt7eoxnH2PIzxUP2pnPQC2l1ntLMpJkTgESfd9Bx2I5Cm2AaXYgcDNGJt6rBH8I61TzY1TA%3D%3D";
		String page = "1";
		String perPage = "10";
		//각각의 정보를 넣어줍니다.
		
		urlBuilder.append("?" + URLEncoder.encode("page", "UTF-8") + "=" + URLEncoder.encode(page, "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("perPage", "UTF-8") + "=" + URLEncoder.encode(perPage, "UTF-8"));
		urlBuilder.append("&" + "serviceKey" + "=" + serviceKey);
		
		try {
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", USER_AGENT);
			
			// 응답 코드
			//int responseCode = connection.getResponseCode();

			// Get Response
			// ~~~~~~~~~~~~~ JSON 변환 ~~~~~~~~~~~~~
			Object obj = JSONValue.parse(new InputStreamReader(connection.getInputStream()));
			JSONObject jObj = (JSONObject) obj;
			// ~~~~~~~~~~~~~ JSON 파일 쓰기 ~~~~~~~~~~~~~
			FileWriter file = new FileWriter("c://jsonTest/test.json");
			file.write(jObj.toJSONString());
			file.flush();
			file.close();
			
			//System.out.println(jObj);
			//List<JSONObject> jList = new ArrayList<JSONObject>();
			//jList = (List<JSONObject>) jObj.get("data");
			//System.out.println(jList.get(0).get("addr"));
			
			// ~~~~~~~~~~~~~~~~~~~~~~ 전체 데이터 출력 ~~~~~~~~~~~~~~~~~~~~~~~~~~
//			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//			StringBuffer stringBuffer = new StringBuffer();
//			String inputLine;
//
//			while ((inputLine = bufferedReader.readLine()) != null) {
//				stringBuffer.append(inputLine);
//			}
//			bufferedReader.close();
//
//			String response = stringBuffer.toString();
//
//			System.out.println("응답 : " + response);
//			System.out.println("응답 코드 : " + responseCode);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// json 파일 읽기
	public static void readJson() throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		Reader reader = new FileReader("C:\\jsonTest\\test.json");
		JSONObject jsonObject = (JSONObject) parser.parse(reader);
		System.out.println(jsonObject);
	}
	public static void postURLTest() {
		try {
			URL url = new URL(URL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod(POST);
			connection.setRequestProperty("User-Agent", USER_AGENT);
			connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=*****;charset=utf-8");
			connection.setDoOutput(true);

			DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
			outputStream.writeUTF("~~~~asdf~~~~");
			outputStream.flush();
			outputStream.close();

			int responseCode = connection.getResponseCode();

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuffer stringBuffer = new StringBuffer();
			String inputLine;

			while ((inputLine = bufferedReader.readLine()) != null) {
				stringBuffer.append(inputLine + "\n");
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

	public static boolean postRequestTest() {
		try {
			// HttpClient httpClient = new DefaultHttpClient();
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost httpPost = new HttpPost(URL);

			HashMap<String, Object> params = new HashMap<>();
			params.put("test", "~~~한글 공백~~~");

			ObjectMapper paramMapper = new ObjectMapper();
			String json = paramMapper.writeValueAsString(params);
			httpPost.setHeader("Content-Type", "application/json");
			httpPost.setEntity(new StringEntity(json, "UTF-8"));

			// http 요청
			HttpResponse response = httpClient.execute(httpPost);

			// 응답 코드 확인
			int responseCode = response.getStatusLine().getStatusCode();
			if (responseCode != 200) {
				System.out.println("응답코드 : " + responseCode);
				System.out.println("통신 실패!!!!!!!!!!!!!!!!!!!>_0b");
				return false;
			}

			// 응답 내용 출력
			HttpEntity entity = response.getEntity();

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
			StringBuffer stringBuffer = new StringBuffer();
			String inputLine;

			while ((inputLine = bufferedReader.readLine()) != null) {
				stringBuffer.append(inputLine + "\n");
			}
			bufferedReader.close();

			System.out.println("응답 : " + stringBuffer);
		} catch (Exception e) {
			System.out.println("~~~~오류~~~~");
			e.printStackTrace();
		}
		return true;

	}

	// 아파치 API 사용
	public static class ApacheTest {
		// GET 요청
		public void apacheGet() throws IOException, URISyntaxException {
			
			HttpClient httpclient = HttpClients.createDefault();
			
			// GET 요청 파라미터를 추가하고 싶다면 URL 뒤에 추가
			//String sub_url = addGetParams(URL, "tesVal", "한글_테스트");
			HttpGet getRequest = new HttpGet(URL);
			getRequest.addHeader("Content-Type", "multipart/form-data;boundary=*****;charset=utf-8");
			//getRequest.addHeader("x-api-key", "RestTestCommon.API_KEY");
			
			HttpResponse response = httpclient.execute(getRequest);
			//Response 출력			
			if (response.getStatusLine().getStatusCode() == 200) {				
				ResponseHandler<String> handler = new BasicResponseHandler();				
				String body = handler.handleResponse(response);				
				System.out.println(body);			
				} 
			else {				
				System.out.println("response is error : " + response.getStatusLine().getStatusCode());			
			}
		}
		// POST 요청
		public void apachePost() throws IOException {
			
			Map<String, Object> data = new HashMap<String, Object>();
			Map<String, Object> buf = new HashMap<String, Object>();
			List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
			
			buf.put("Line1", "test1-1");
			maps.add(buf);
			buf.put("Line1", "test1-2");
			maps.add(buf);
			buf.put("Line1", "test1-3");
			maps.add(buf);
			buf.put("Line1", "test1-4");
			maps.add(buf);
			data.put("test", maps);
			
			try {
				HttpClient client = HttpClientBuilder.create().build();
				HttpPost postRequest = new HttpPost(URL); // POST 메소드 URL 새성
				postRequest.setHeader("Accept", "application/json");
				postRequest.setHeader("Connection", "keep-alive");
				postRequest.setHeader("Content-Type", "application/json");
				// postRequest.addHeader("x-api-key", RestTestCommon.API_KEY); //KEY 입력 //
				// postRequest.addHeader("Authorization", token); // token 이용시
				
				ObjectMapper paramMapper = new ObjectMapper();
				String json = paramMapper.writeValueAsString(data);
				postRequest.setEntity(new StringEntity(json, "UTF-8")); //json 메시지 입력
				
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
		}
		// GET 파라미터 추가
		public String addGetParams(String url, String key, String val) {
			return url + "?" + key + "=" + val;
		}
	}
}
