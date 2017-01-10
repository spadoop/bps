<c:choose>
	<c:when test="${empty (pageCond)}">
		<input type="hidden" name="pageCond.begin" value="0"/>
		<input type="hidden" name="pageCond.length" value="10"/>
		<input type="hidden" name="pageCond.isCount" value="true"/>
		<input type="hidden" name="pageCond.count" value="0"/>
	</c:when>
	<c:otherwise>
		<input type="hidden" name="pageCond.begin" value="${pageCond.begin}"/>
		<input type="hidden" name="pageCond.length" value="${pageCond.length}"/>
		<input type="hidden" name="pageCond.isCount" value="${pageCond.isCount}"/>
		<input type="hidden" name="pageCond.count" value="${pageCond.count}"/>
		
		<c:choose>
			<c:when test="${pageCond.isCount}">		
				<bc:message key='Pagination.count'/>&nbsp;<c:out value="${pageCond.count}"/>&nbsp;<bc:message key='Pagination.record'/>&nbsp;&nbsp;
				<bc:message key='Pagination.currentpage'/>&nbsp;<c:out value="${pageCond.currentPage}"/>&nbsp;/&nbsp;<c:out value="${pageCond.totalPage}"/>&nbsp;<bc:message key='Pagination.page'/>
				
				<c:if test="${pageCond.first}">
					&nbsp;
					<span style="color: #999999; text-decoration: none;">
						<span style="color: #999999; text-decoration: none;"><bc:message key='Pagination.head'/>&nbsp;</span>|
						<span style="color: #999999; text-decoration: none;"><bc:message key='Pagination.previous'/>&nbsp;</span>|
					</span>
				</c:if> 
				
				<c:if test="${pageCond.first == false}">			
					<a href="javascript:firstPagination('${action}')"><bc:message key='Pagination.head'/>&nbsp;</a>|
					<a href="javascript:previousPagination('${action}')"><bc:message key='Pagination.previous'/>&nbsp;</a>|
				</c:if>
				
				<c:if test="${pageCond.last}">
					<span style="color: #999999; text-decoration: none;">
						<span style="color: #999999; text-decoration: none;"><bc:message key='Pagination.next'/>&nbsp;</span>|
						<span style="color: #999999; text-decoration: none;"><bc:message key='Pagination.tail'/>&nbsp;</span>
					</span>
				</c:if>
				<c:if test="${pageCond.last == false}">			
					<a href="javascript:nextPagination('${action}')"><bc:message key='Pagination.next'/>&nbsp;</a>|
					<a href="javascript:lastPagination('${action}')"><bc:message key='Pagination.tail'/>&nbsp;</a>
				</c:if>			
			</c:when>
			
			<c:otherwise>
				<c:if test="${pageCond.begin == 0}">
					&nbsp;
					<span style="color: #999999; text-decoration: none;">
						<span style="color: #999999; text-decoration: none;"><bc:message key='Pagination.head'/>&nbsp;</span>|
						<span style="color: #999999; text-decoration: none;"><bc:message key='Pagination.previous'/>&nbsp;</span>|
					</span>
				</c:if> 
				
				<c:if test="${pageCond.begin >= 1}">			
					<a href="javascript:firstPagination('${action}')"><bc:message key='Pagination.head'/>&nbsp;</a>|
					<a href="javascript:previousPagination('${action}')"><bc:message key='Pagination.previous'/>&nbsp;</a>|
				</c:if>
				
				<c:if test="${pageCond.last == false}">
					<a href="javascript:nextPagination('${action}')"><bc:message key='Pagination.next'/>&nbsp;</a>|
				</c:if>
			</c:otherwise>
		</c:choose>
		
		<input type='text' id='pageno' size='3' value="${pageCond.currentPage}" class="textbox" style="width:30px" onkeypress="keypressPageNo(event,this);"/>
		<input type="button" value="GO" onclick="goPage();" style="width:38px;font-size:12" class="button"/>
	</c:otherwise>
</c:choose>

<script>

	NS4 = (document.layers) ? true : false;
	
	//回车跳转页面
	function keypressPageNo(event,element){
		var code = 0;
	    if (NS4)
	        code = event.which;
	    else
        	code = event.keyCode;
        	
        if (code==13){
        	goPage();
        }
	}
	
	//页面跳转
	function goPage(){	
		var num = document.getElementById("pageno").value; 
		var total = 0;
		<c:if test="${!empty (pageCond)}">
			total = ${pageCond.totalPage};
		</c:if>
		
		var numRegExp = /^[0-9]+$/;
      	if(numRegExp.test(num)){
      		if(total > 0 && num > total) {
      			num = total;
      		}      			
			gotoPagination(num, '${action}');
		} else{
			var message='<bc:message key='Pagination.alertmessage'/>';
			alertValidateMessage(message);
		}
	}	
</script>