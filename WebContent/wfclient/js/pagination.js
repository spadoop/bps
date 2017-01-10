
/**
 * 页面跳转函数.
 */
function gotoPagination(pageno, action) {
	var formObj = getPaginationFormObj();
	var beginElement = formObj.elements["pageCond.begin"];
	var lengthElement = formObj.elements["pageCond.length"];
	
	var nn = lengthElement.value/1 * (pageno - 1)/1;

	beginElement.value = (nn < 0 ? 0 : nn);
	submitPagination(action);
}

/**
 * 页面跳转函数--第一页.
 */
function firstPagination(action){
	var formObj = getPaginationFormObj();
	var beginElement = formObj.elements["pageCond.begin"];
	beginElement.value = 0;
	submitPagination(action);
}

/**
 * 页面跳转函数--前一页.
 */
function previousPagination(action){
	var formObj = getPaginationFormObj();
	var beginElement = formObj.elements["pageCond.begin"];
	var lengthElement = formObj.elements["pageCond.length"];
	var nn = Number(beginElement.value) - Number(lengthElement.value);
	beginElement.value = nn < 0 ? 0 : nn;
	submitPagination(action);
}

/**
 * 页面跳转函数--后一页.
 */
function nextPagination(action){
	var formObj = getPaginationFormObj();
	var beginElement = formObj.elements["pageCond.begin"];
	var lengthElement = formObj.elements["pageCond.length"];
	beginElement.value = Number(beginElement.value) + Number(lengthElement.value);
	submitPagination(action);
}

/**
 * 页面跳转函数--最末页.
 */
function lastPagination(action){
	var formObj = getPaginationFormObj();
	var beginElement = formObj.elements["pageCond.begin"];
	var lengthElement = formObj.elements["pageCond.length"];
	var countElement = formObj.elements["pageCond.count"];
	
	var totalPage = Math.floor(((countElement.value/1) + (lengthElement.value/1) -1)/(lengthElement.value/1));
	beginElement.value = (totalPage-1)*(lengthElement.value/1);
	submitPagination(action);
}

//提交
function submitPagination(action) {
	var formObj = getPaginationFormObj();
	formObj.target = "_self";
	if (action && trimString(action) != '') {
		formObj.action = action;
	}
	formObj.submit();
}

//取得对象
function getPaginationFormObj() {
	return getParentByTagName(document.getElementById("pageno"), "form");
}