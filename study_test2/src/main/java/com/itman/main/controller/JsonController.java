package com.itman.main.controller;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itman.main.service.JsonService;
import com.itman.main.util.json.JsonIO;
import com.itman.main.util.json.JsonParse;

@Controller
//@RequestMapping(value="/json")
public class JsonController {
	@Autowired
	private JsonService service;
	
	@RequestMapping(value="/json")
	public String main(Model model) throws Exception {
		return "json";
	}
	
	@RequestMapping(value="TestJson", produces = "application/json")
	@ResponseBody
	public JSONObject test() {
		Map<String,Object> msg = new HashMap<String, Object>();
		try {
			//System.out.println(">>>> test >>>");
			
			ClassPathResource resource = new ClassPathResource("file/jTest.json");
			InputStreamReader jInput = new InputStreamReader(resource.getInputStream(), "UTF-8");
			JSONObject jobj = (JSONObject) new JSONParser().parse(jInput);
			return jobj;
		} catch (Exception e) {
			e.printStackTrace();
			//msg.put("FAIL", "non_data");
		}
		return null;
	}
	
	@RequestMapping(value="selectJsonAll", method=RequestMethod.POST, headers="Accept=application/json",produces = "application/json")
	@ResponseBody
	public Map<String,Object> selectJsonAll(@RequestBody Map<String, Object> params, Model model) {
		Map<String,Object> msg = new HashMap<String, Object>();
		try {
			//System.out.println(service.selectAll());
			msg.put("SUCC", service.selectAll());
		} catch (Exception e) {
			e.printStackTrace();
			msg.put("FAIL", "non_data");
		}
		return msg;
	}
	
	@RequestMapping(value="deleteJson", method=RequestMethod.POST, headers="Accept=application/json",produces = "application/json")
	@ResponseBody 
	public Map<String, Object> deleteJson(@RequestBody Map<String, Object> params) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Integer> list = new ArrayList<Integer>(); 
		try {
			list = (ArrayList<Integer>) params.get("Message");
			for(int i : list) {
				service.deleteJson(i);
				//System.out.println("????????? : " + i);
			}
			map.put("SUCC", params.get("Message"));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("FAIL", "?????? ????????? ???????????? ????????????.");
		}
		return map;
	}
	
	@RequestMapping(value="insertJson", method=RequestMethod.POST, headers="Accept=application/json",produces = "application/json")
	@ResponseBody
	public Map<String, Object> insertJson(@RequestBody Map<String, Object> params) {
		Map<String,Object> msg = new HashMap<String, Object>();
		
		String url = "http://api.odcloud.kr/api/EvInfoServiceV2/v1/getEvSearchList";
		String serviceKey = "QKTa2omkRMLG0WLnt7eoxnH2PIzxUP2pnPQC2l1ntLMpJkTgESfd9Bx2I5Cm2AaXYgcDNGJt6rBH8I61TzY1TA%3D%3D";
		String path = "C:\\jsonTest\\";
		
		try {
		JsonParse jsonParse = new JsonParse(url, serviceKey);
		JsonIO jsonIo = new JsonIO();
		JSONObject jObj = new JSONObject();
		// ?????? ?????? ??? ????????? ???
		jsonParse.addParams("perPage", (String)params.get("perPage"));
		
		// ????????? ??????
		List<JSONObject> jList = jsonParse.getJson(
				Integer.parseInt((String) params.get("start")), 
				Integer.parseInt((String) params.get("end")));
		
		// ~~~~~~~~~~~~~~~~~~ ?????? ????????? ~~~~~~~~~~~~~~~~~~~~~~~~~
		/*
		for(int i = 0; i < jList.size(); i++) {
			jsonIo.writeJson(jList.get(i), path, "testJson_" + i);
		}
		
		for(int i = 0; i< jList.size(); i++) {
			try {
				jObj = jsonIo.readJson(path+"testJson_" + i + ".json");
			} catch (ParseException e1) {
				System.out.println("?????? ?????? ??????");
			}
			try {
				service.insertParseJson(jObj);
			} catch (Exception e) {
				System.out.println("Duplicate PRIMARY key");
			}
		}
		*/
		// ~~~~~~~~~~~~~~~~~~ ?????? ????????? ?????? ?????? ~~~~~~~~~~~~~~~~~~~~~~~
		
		for(int i = 0; i< jList.size(); i++) {
			try {
				service.insertParseJson(jList.get(i));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		msg.put("SUCC", "????????? ?????? = " + Integer.toString(jList.size()*10));
		
		} catch (IOException e) {
			msg.put("FAIL", "non_data");
			e.printStackTrace();
		}
		
		return msg;
	}
}
