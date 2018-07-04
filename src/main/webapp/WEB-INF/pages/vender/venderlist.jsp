	<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@taglib uri="http://www.mrjc.cn/tag" prefix="mrjc" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>收派标准</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">	
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script type="text/javascript">
	function doAdd(){
		//alert("增加...");
		$('#name').val('');
		$('#minweight').numberbox('setValue', null); 	
		$('#maxweight').numberbox('setValue', null); 	
		$('#id').val('') ;
		
		$('#addStandardWindow').window("open");
	}
	
	function doView(){
		$('#searchWindow').window("open");		
	}
	
	function doDelete(){
		// 判断是否选择表格数据 
		var array = $('#grid').datagrid('getSelections');
		if(array.length == 0){
			// 一行也没选
			$.messager.alert('警告','删除数据要先选中！','warning');
			return ;
		}
		
		// 提交删除form 
		$('#delForm').submit();
	}
	//工具栏
	var toolbar = [ 
	<mrjc:privilege value="收派标准查询">
	    {
		id : 'button-view',	
		text : '查询',
		iconCls : 'icon-search',
		handler : doView
	}, 
	</mrjc:privilege>
	//自定义标签  动态控制功能按钮   
	<mrjc:privilege value="收派标准增加">
	{
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	},
	</mrjc:privilege>
	<mrjc:privilege value="收派标准删除">
	{
		id : 'button-delete',
		text : '删除',
		iconCls : 'icon-cancel',
		handler : doDelete
	},
	</mrjc:privilege>
	];
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	},{
		field : 'vendId',
		title : '供应商ID',
		width : 120,
		align : 'center'
	}, {
		field : 'vendDesc',
		title : '供应商名称',
		width : 120,
		align : 'center'
	}, {
		field : 'vendAddr',
		title : '联系地址',
		width : 120,
		align : 'center'
	}, {
		field : 'contMan',
		title : '联系人',
		width : 120,
		align : 'center'
	}, {
		field : 'tradeAmount',
		title : '交易额',
		width : 160,
		align : 'center'
	},{
		field : 'vendtype.vendtypeDesc',
		title : '类型',
		width : 160,
		align : 'center',
		formatter : function(data,row ,index){
			return row.vendtype.vendtypeDesc;
		}
	},
	{
		field : 'shipvin.shipviaDesc',
		title : '运输类型',
		width : 160,
		align : 'center',
		formatter : function(data,row ,index){
			return row.shipvin.shipviaDesc;
		}
	}
	] ];
	
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 收派标准数据表格
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			pageList: [5,10,25],
			pagination : true,
			toolbar : toolbar,
			url : "${pageContext.request.contextPath}/vendermaster_pageQuery.action",
			idField : 'id',
			columns : columns,
		});
		
		
		// 执行条件查询
		$("#btn").click(function(){
			// 将form的数据转换为 json 
			var params = $('#searchForm').serializeJson();
			// 调用datagrid 执行查询，在查询是，缓存条件 
			$('#grid').datagrid('load',params);// 重新加载datagrid指定url
			
			// 窗口关闭
			$('#searchWindow').window('close');
		});
		
	});
		
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
    <form id="delForm" action="${pageContext.request.contextPath }/standard_delBatch.action" method="post">
	    <div region="center" border="false">
	    		<table id="grid"></table>
		</div>
    </form>
	
	
	
	
</body>
</html>