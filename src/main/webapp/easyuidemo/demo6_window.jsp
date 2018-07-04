<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 先引入 jquery的 js -->
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 引入 easyui的js -->
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<!-- 引入国际化 js -->
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<!-- 引入 默认样式 css -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css"/>
<!-- 引入 icon图标 css  -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css"/>
<script type="text/javascript">
	$(function(){
		
	});
</script>
</head>
<body>
	<!-- 通过 easyui的 class 将任意 div 变为 窗口 -->
	<!-- 为窗口 设置标题属性 和 宽高  -->
	<div id="mywindow" class="easyui-window" data-options="title:'自定义窗口',maximizable:false,minimizable:false,modal:true,closed:true" 
	style="width:200px;height: 150px">传智播客</div>
	
	<input type="button" value="打开窗口" onclick="$('#mywindow').window('open');"/>
</body>
</html>