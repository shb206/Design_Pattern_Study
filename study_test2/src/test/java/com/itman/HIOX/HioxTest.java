package com.itman.HIOX;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.itman.HIOX.service.HioxService;
import com.itman.HIOX.util.Paging;

//@RunWith(SpringJUnit4ClassRunner.class)
public class HioxTest {
	//private static final Logger logger = LoggerFactory.getLogger(Paging.class);
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@After
	public void restoreStreams() {
	    System.setOut(originalOut);
	    System.setErr(originalErr);
	}

	@Autowired
	private HioxService service;
	Map<String, Object> map = new HashMap<String, Object>();
	Map<String, Object> map2 = new HashMap<String, Object>();
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	
	@Before
	public void init() {
		map.put("choose", 1);
		map.put("texture", "texture");
		map.put("thickness", "thickness");
		map.put("size", "size");
		
		for(int i = 2000; i < 3000; i++) {
			map2.put("choose", i);
			map2.put("texture", "texture");
			map2.put("thickness", "thickness");
			map2.put("size", "size");
			
			list.add(map2);
		}
	}
	
	@Test
	@Transactional
	public void test1() {
		long start = System.currentTimeMillis();
		for(int i = 1000; i < 2000; i++) {
			map.put("choose", i);
			try {
				service.insertHiox(map);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		long end = System.currentTimeMillis();
		System.out.print("test1 시행 시간 : " + (end - start));
		assertEquals(20, outContent.toString());
	}
	
	@Test
	@Transactional
	public void test2() {
//		System.out.print(list.size());
//		assertEquals(20, outContent.toString());
	}
}
