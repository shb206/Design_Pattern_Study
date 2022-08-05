package com.itman.HIOX.Controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itman.HIOX.service.HioxService;
import com.itman.HIOX.util.Paging;
import com.itman.main.util.Student;

@Controller
public class HioxController {
	@Autowired
	private HioxService service;
	private Paging paging;
	long test1;
	long test2;
	
	@RequestMapping(value="/hiox")
	public String main(Model model) {
		if(paging == null) {
			// 첫 페이지는 아무런 조건 없는 빈 맵 객체 전달
			paging = new Paging(service.getTotalCount(new HashMap<String, Object>()));
			//paging = new Paging(1300);
		}
		
		// 공통 코드를 통해 가져온 재질/두께/크기유형 리스트
		Map<String, List<String>> cdList = getCdList();
		model.addAttribute("texture_list", cdList.get("texture_list"));
		model.addAttribute("thickness_list", cdList.get("thickness_list"));
		model.addAttribute("size_list", cdList.get("size_list"));
		
		return "hioxMain";
	}

	@RequestMapping(value="selectHiox", method=RequestMethod.POST, headers="Accept=application/json",produces = "application/json")
	@ResponseBody
	public Map<String,Object> select(@RequestBody Map<String, Object> params, Model model) {
		Map<String,Object> msg = new HashMap<String, Object>();
		try {
			params.put("pageSize", paging.getPageSize());
			// 검색 조건 추가 시 조건에 맞는 row수 재계산
			paging.setTotalRecord(service.getTotalCount(params));
			//paging.setTotalRecord(1300);
			// 범위 밖 페이지 입력시 예외처리
			paging.setCurrentPage(Integer.parseInt(String.valueOf(params.get("page"))));
			params.put("page", paging.getCurrentPage());

			// 조회한 데이터 전달
			msg.put("SUCC", service.select(params));
			// 현재 위치 기준 화면에 보여줄 첫 페이지와 마지막 페이지(ex. 현재 페이지가 5라면 1, 10)
			msg.put("pageList", paging.pageList());
			// 예외처리한 현재 페이지를 반환
			msg.put("page", paging.getCurrentPage());
		} catch (Exception e) {
			e.printStackTrace();
			msg.put("FAIL", "데이터가 없습니다.");
		}
		return msg;
	}
	@RequestMapping(value="releaseHiox", method=RequestMethod.POST, headers="Accept=application/json",produces = "application/json")
	@ResponseBody
	public Map<String,Object> releaseHiox(@RequestBody Map<String, Object> params, Model model) {
		Map<String,Object> msg = new HashMap<String, Object>();
		List<Integer> list = (ArrayList<Integer>) params.get("Message");
		try {
			// 업데이트하려는 작업이 출고라면
			if(params.get("release").equals("release")) {
				service.releaseHiox(list);
			}
			// 업데이트하려는 작업이 출고 취소라면
			else if(params.get("release").equals("cancel")){
				service.releaseCancelHiox(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg.put("FAIL", "non_data");
		}
		return msg;
	}
	@RequestMapping(value="deleteHiox", method=RequestMethod.POST, headers="Accept=application/json",produces = "application/json")
	@ResponseBody
	public Map<String,Object> deleteHiox(@RequestBody Map<String, Object> params, Model model) {
		Map<String,Object> msg = new HashMap<String, Object>();
		List<Integer> list = (ArrayList<Integer>) params.get("Message");
		try {
			// id 기반 지우는 쿼리 작성
			service.deleteHiox(list);
		} catch (Exception e) {
			e.printStackTrace();
			msg.put("FAIL", "non_data");
		}
		return msg;
	}
	
	@RequestMapping(value="insertHiox", method=RequestMethod.POST, headers="Accept=application/json",produces = "application/json")
	@ResponseBody 
	public Map<String, Object> insertHiox(@RequestBody Map<String, Object> params) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			service.insertHiox(params);
			map.put("SUCC", params);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("FAIL", "삽입 실패");
		}
		return map;
	}
	
	@RequestMapping(value="insertTestHiox", method=RequestMethod.POST, headers="Accept=application/json",produces = "application/json")
	@ResponseBody 
	public Map<String, Object> insertTestHiox(@RequestBody Map<String, Object> params) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			long start = System.currentTimeMillis();
			for(int i = 10000; i < 20000; i++) {
				map.put("choose", i);
				try {
					service.insertHiox(map);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			long end = System.currentTimeMillis();
			System.out.println("test1 완료");
			test1 = (end - start);
			map.put("SUCC", "성공");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("FAIL", "삽입 실패");
		}
		return map;
	}
	@RequestMapping(value="insertTestHiox2", method=RequestMethod.POST, headers="Accept=application/json",produces = "application/json")
	@ResponseBody 
	public Map<String, Object> insertTestHiox2(@RequestBody Map<String, Object> params) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			
			for(int i = 20000; i < 30000; i++) {
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("choose", i);
				map2.put("texture", "texture");
				map2.put("thickness", "thickness");
				map2.put("size", "size");
				list.add(map2);
			}
			System.out.println(list);
			long start = System.currentTimeMillis();
			service.multiInsertHiox(list);
			
			long end = System.currentTimeMillis();
			
			test2 = end - start;
			
			System.out.println("test1 시행 시간 : " + test1 + "ms");
			System.out.println("test2 시행 시간 : " + test2 + "ms");
			
			map.put("SUCC", "성공");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("FAIL", "삽입 실패");
		}
		return map;
	}
	
	private Map<String, List<String>> getCdList() {
		Map<String, List<String>> cdList = new HashMap<String, List<String>>();
		
		List<Map<String, Object>> list = service.cd_List();

		List<String> texture_list = new ArrayList<String>();
		List<String> thickness_list = new ArrayList<String>();
		List<String> size_list = new ArrayList<String>();
		
		for(int i = 0; i < list.size(); i++) {
			switch ((String)list.get(i).get("COMM_GRP_CD")) {
			case "GTexture":
				texture_list.add((String)list.get(i).get("COMM_CD_NM"));
				break;
			case "GThickness":
				thickness_list.add((String)list.get(i).get("COMM_CD_NM"));
				break;
			case "GSize":
				size_list.add((String)list.get(i).get("COMM_CD_NM"));
				break;
			}
		}
		cdList.put("texture_list", texture_list);
		cdList.put("thickness_list", thickness_list);
		cdList.put("size_list", size_list);
		
		return cdList;
	}
}
