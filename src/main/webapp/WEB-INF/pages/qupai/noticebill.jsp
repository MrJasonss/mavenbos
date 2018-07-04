<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>业务通知单</title>
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

	$.fn.serializeJson=function(){  
	    var serializeObj={};  // 目标对象 
	    var array=this.serializeArray();  // 先将form 转换为 数组 
	    $(array).each(function(){  // 遍历数据的每个 元素
	        if(serializeObj[this.name]){ // 判断json中 参数name是否存在 
	            if($.isArray(serializeObj[this.name])){  // 判断 name对应属性值 是否为一个 数组
	                serializeObj[this.name].push(this.value);   // 将当前值加入数组
	            }else{ // 如果不是数组  
	                serializeObj[this.name]=[serializeObj[this.name],this.value];  // 将原来属性值存储为数组 
	            }  
	        }else{  
	            serializeObj[this.name]=this.value;   // 直接向对象添加一个新属性 
	        }  
	    });  
	    return serializeObj;  
	};
	
	function doRepeat(){
		// 先判断 用户是否选择
		var array = $('#grid').datagrid('getSelections'); 
		if(array.length == 0){
			$.messager.alert('警告','还原前必须选择！','warning');
		}else{
			// 修改原来form 的默认Action
			$('#delForm').attr("action","${pageContext.request.contextPath}/workbill_doRestore.action")
			$('#delForm').submit();
		}
	}
	
	function doCancel(){
		var array = $('#grid').datagrid('getSelections'); 
		if(array.length == 0){
			$.messager.alert('警告','删除前必须选择！','warning');
		}else{
			$('#delForm').submit();
		}
	}
	
	function doSearch(){
		$('#searchWindow').window("open");
	}
	
	//工具栏
	var toolbar = [ {
		id : 'button-search',	
		text : '查询',
		iconCls : 'icon-search',
		handler : doSearch
	}, {
		id : 'button-repeat',
		text : '追单',
		iconCls : 'icon-redo',
		handler : doRepeat
	}, {
		id : 'button-cancel',	
		text : '销单',
		iconCls : 'icon-cancel',
		handler : doCancel
	}];
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	}, {
		field : 'noticebill.id',
		title : '通知单号',
		width : 120,
		align : 'center'
	},{
		field : 'type',
		title : '工单类型',
		width : 120,
		align : 'center'
	}, {
		field : 'pickstate',
		title : '取件状态',
		width : 120,
		align : 'center'
	}, {
		field : 'buildtime',
		title : '工单生成时间',
		width : 120,
		align : 'center'
	}, {
		field : 'attachbilltimes',
		title : '追单次数',
		width : 120,
		align : 'center'
	}, {
		field : 'staff.name',
		title : '取派员',
		width : 100,
		align : 'center',
		formatter : function(data,row, index){
			return row.staff.name;
		}
	}, {
		field : 'staff.telephone',
		title : '联系方式',
		width : 100,
		align : 'center',
		formatter : function(data,row, index){
			return row.staff.telephone;
		}
	} ] ];
	
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 收派标准数据表格
		$('#grid').datagrid( {
			url: "${pageContext.request.contextPath}/workbill_pageQuery.action",
			iconCls : 'icon-forward',
			fit : true,
			border : true,
			rownumbers : true,
			striped : true,
			pageList: [30,50,100],
			pagination : true,
			toolbar : toolbar,
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		
		// 查询分区
		$('#searchWindow').window({
	        title: '查询工单',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		$("#btn").click(function(){
			// 将form的数据转换为 json 
			var params = $('#searchForm').serializeJson();
			console.info(params);
			// 调用datagrid 执行查询，在查询是，缓存条件 
			$('#grid').datagrid('load',params);// 重新加载datagrid指定url
			
			// 窗口关闭
			$('#searchWindow').window('close');
			//$("#searchForm").get(0).reset();// 重置查询表单
		});
	});

	function doDblClickRow(){
		alert("双击表格数据...");
	}
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">

	<form id="delForm" action="${pageContext.request.contextPath }/workbill_delBatch.action" method="post">
		<div region="center" border="false">
	    	<table id="grid"></table>
		</div>
	</form>
	
	<!-- 查询分区 -->
	<div class="easyui-window" title="查询窗口" id="searchWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div style="overflow:auto;padding:5px;" border="false">
			<form id="searchForm">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">查询条件</td>
					</tr>
					<tr>
						<td>客户电话</td>
						<td><input type="text" name="staff.telephone" class="easyui-validatebox" /></td>
					</tr>
					<tr>
						<td>取派员</td>
						<td><input type="text" name="staff.name" class="easyui-validatebox" /></td>
					</tr>
					<tr>
						<td>受理时间</td>
						<td><input type="text" name="buildtime" class="easyui-datebox" /></td>
					</tr>
					<tr>
						<td colspan="2"><a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> </td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>