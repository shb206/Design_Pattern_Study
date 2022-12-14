// 전역 변수
var grid = null;
let gridList=[];
let check = true;
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 기본 세팅 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
$(function(){
	initPrams();
	initDatas();
	initEvents();
});

// 전체 리스트 표시
function tui_showInfo() {
	var queryString = {"Message" : "showInfo"};
	gf_Transaction_min("show_info", "/selectAll", "POST", queryString, 1)
}

function initDatas(){
	tui_showInfo();
}

function initPrams(){
		var cols = [
		{ header: '학번', name: 'IDX', align: 'center', sortable: true }
	  , { header: '이름', name: 'NAME', align: 'center', sortable: true}
      , { header: '과목 코드', name: 'CODE', align: 'center', sortable: true}
      , { header: '점수', name: 'SCORE', align: 'center', sortable: true }

	];
	// # 그리드 생성
	grid = new tui.Grid({
	    el: document.getElementById("grid")
	    , scrollX: false
	    , scrollY: true
	    , bodyHeight: 650
		, rowHeaders: ['checkbox','rowNum']
	    //, selectionUnit: "row"
	    , columns: cols
	});
	
}

function initEvents(){
	// ### grid event ###
	// # 클릭 이벤트
	grid.on('focusChange', (e) =>{
		grid.setSelectionRange({
			  start:[e.rowKey, 0]
			, end:[e.rowKey, grid.getColumns().length]
		});
	});
	// # 더블 클릭 이벤트
	grid.on('dblclick', (e) =>{
		var el = grid.getRow(e.rowKey);
		update_Ready(el);
	});
	
	// # 체크박스 체크
	grid.on('check', function(e){
		grid.setSelectionRange({
			  start:[e.rowKey, 0]
			, end:[e.rowKey, grid.getColumns().length]
		});
		gridList.push(grid.getRow(e.rowKey));
	});
	
	// # 체크박스 체크 해제
	grid.on("uncheck", function(e){
		grid.getRow(e.rowKey);
		
		// 체크 해제한 항목을 체크 리스트에서 삭제
		Array.from(gridList).forEach(li => {
		    if(li["rowKey"] === e.rowKey) {
			    var idx = gridList.indexOf(li);
			    if(idx > -1) {
				    gridList.splice(idx, 1);
			    }
		    }})
	});
}

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 사용 함수 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
(function($){
	$.fn.serializeObject = function () {
		"use strict";

		var result = {};
		var extend = function (i, element) {
			var node = result[element.name];

	// If node with same name exists already, need to convert it to an array as it
	// is a multi-value field (i.e., checkboxes)

			if ('undefined' !== typeof node && node !== null) {
				if ($.isArray(node)) {
					node.push(element.value);
				} else {
					result[element.name] = [node, element.value];
				}
			} else {
				result[element.name] = element.value;
			}
		};

		$.each(this.serializeArray(), extend);
		return result;
	};
})(jQuery);
// ajax 통신 함수
var gf_Transaction_min = (id, url, httpMethod, params, callback) => {
	$.ajax({
		url: url,
		type: httpMethod,    
		data: JSON.stringify(params),
		contentType: 'application/json; charset=utf-8',
	    dataType: 'json',
	    success: function(ret) {
	    	if (callback != null) {
	       		f_callback( id, ret );
	    	}
	    	console.log("전송 성공");
	    },
	    error:function(request, status, error) {
	    	alert(request.status + " : " +status.dataType+"/");
	    }
	});
}
function f_callback(trId, data){
	
	if(data["FAIL"]) {
		alert(data["FAIL"]);
	}
	
	switch(trId) {
		case "show_info" :
			grid.resetData(data["SUCC"]);
			console.log(data["SUCC"]);
		case "selectPop" :
			grid.resetData(data["SUCC"]);
			break;
		case "insertPop" :
			tui_showInfo();
			break;
		case "updatePop" :
			tui_showInfo();
			break;
		case "delete" :
			tui_showInfo();
	}
}
// grid 더블클릭 이벤트로 들어온 객체 정보를 hidden 필드에 저장
function update_Ready(el) {
	$("#h_idx").val(el["IDX"]);
	$("#h_name").val(el["NAME"]);
	$("#h_code").val(el["CODE"]);
	$("#h_score").val(el["SCORE"]);
	
	window.open("/update_subView", "update_popup", "width = 500, height = 500");
}

function val_change(data) {
	switch(data) {
		case "학번" :
			return "idx";
		case "이름" :
			return "name";
		case "과목 코드" :
			return "code";
		case "점수" :
			return "score";
		default :
			return "▼ 검색메뉴";
	}
}

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~ 팝업 창 연동 함수 ~~~~~~~~~~~~~~~~~~~~~~~~~~
// 팝업 창으로 넘어온 값이 메인 페이지의 hidden 필드에 저장됨
window.selectPop = function() {
	var queryString = {"Message" : $("#h_idx").val()};
	gf_Transaction_min("selectPop", "/selectTest", "POST", queryString, 1);
}
window.insertPop = function() {
	var data = {
		"idx" : $("#h_idx").val(),
		"name" : $("#h_name").val(),
		"code" : $("#h_code").val(),
		"score" : $("#h_score").val() }
	gf_Transaction_min("insertPop", "/insertTest", "POST", data, 1);
}
window.updatePop = function() {
	var data = {
		"idx" : $("#h_idx").val(),
		"name" : $("#h_name").val(),
		"code" : $("#h_code").val(),
		"score" : $("#h_score").val() }
	gf_Transaction_min("updatePop", "/updateTest", "POST", data, 1);
}
function listMapToJson(list) {
	let csv_str = "";
	csv_str += "idx,name,code,score\n";
	list.forEach(data => {
		csv_str += data["idx"];
		csv_str += ",";
		csv_str += data["name"];
		csv_str += ",";
		csv_str += data["code"];
		csv_str += ",";
		csv_str += data["score"];
		csv_str += "\n";
	})
	return csv_str;
}
// ~~~~~~~~~~~~~~~~~~~~~~~~~ 버튼 클릭 이벤트  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
$(document).ready(function() {
	$("#reset_btn").on("click",function(event){
		event.preventDefault();
		tui_showInfo();
	});
	$("#csv_btn").on("click",function(event){
		grid.checkAll();
		let checkList = grid.getCheckedRows();
		let list = [];
		checkList.forEach(e => {
			var data = {
				"idx" : e["IDX"],
				"name" : e["NAME"],
				"code" : e["CODE"],
				"score" : e["SCORE"] }
			list.push(data);
		});
		let str_csv = listMapToJson(list);
		
		// https://developer.mozilla.org/ko/docs/Web/API/URL/createObjectURL
		var downloadLink = document.createElement("a");
		var blob = new Blob([str_csv], { type: "text/csv;charset=utf-8" });
		var url = URL.createObjectURL(blob);
		downloadLink.href = url;
		downloadLink.download = "data.csv";

		document.body.appendChild(downloadLink);
		downloadLink.click();
		document.body.removeChild(downloadLink);
	});
    $("#select_btn").on("click",function(event){
		event.preventDefault();
		//window.open("/select_subView", "select_popup", "width = 500, height = 500");
		
		var btn = document.getElementById("search_btn");
		var content = $("#search_field").val()
		var data = {
			"type" : val_change(btn.innerText),
			"content" : content
			}
		if(data["type"] === "▼ 검색메뉴") {
			alert("검색할 필드를 선택해주세요");
		}
		else if(data["content"].length === 0) {
			alert("검색할 내용을 입력해주세요");
		}
		else {
			gf_Transaction_min("selectPop", "/selectTest", "POST", data, 1);
		}
	});
    $("#insert_btn").on("click",function(event){
		event.preventDefault();
		window.open("/insert_subView", "insert_popup", "width = 500, height = 500");
	});
	/*
    $("#update_btn").on("click",function(event){
		event.preventDefault();
		window.open("/update_subView", "update_popup", "width = 500, height = 500");
	});
	*/
	$("#delete_btn").on("click",function(event){
		event.preventDefault();
		// 체크를 하나도 안했을 경우
		if(grid.getCheckedRows().length === 0)
			alert("하나 이상의 학생을 체크해주세요");
        
		// 체크리스트(griList)의 값을 index list로 변경
		idxList = [];
		grid.getCheckedRows().forEach(e => idxList.push(e["IDX"]));
		var queryString = {"Message" : idxList};
		
		gf_Transaction_min("delete", "/deleteTest", "POST", queryString, 1);
	});
	$(".dropMenu").on("click",function(event){
		var btn = document.getElementById("search_btn");
		btn.innerText = $(this).val();
	});
})