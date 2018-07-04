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
		// 这个funtion 叫做匿名函数 ，在页面加载后执行 
		// messager 控件使用 
		// 1 alert 
		// $.messager.alert("标题","内容","info");
		
		// 2 confirm
		/*
		$.messager.confirm('标题','确认删除吗？',function(isConfirm){
			if(isConfirm){
				alert("执行删除");
			}else{
				alert("取消...");
			}
		});
		*/
		
		// 3、show 
		$.messager.show({
			title : '标题',
			msg : '<a href="#">传智播客</a>',
			timeout : 5000 , // 5秒后自动消失
		});
	});
</script>
</head>
<body>
</body>
</html>