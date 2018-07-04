<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
		//导入自定义 jquery 函数，将form 数据转换为 json
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


	// 工具栏
	var toolbar = [ {
		id : 'button-view',	
		text : '查看',
		iconCls : 'icon-search',
		handler : doView
	}, {
		id : 'button-add',
		text : '新增',
		iconCls : 'icon-add',
		handler : doAdd
	}, {
		id : 'button-delete',
		text : '删除',
		iconCls : 'icon-cancel',
		handler : doDelete
	}];
	//定义冻结列
	var frozenColumns = [ [ {
		field : 'id',
		checkbox : true,
		rowspan : 2
	}, {
		field : 'username',
		title : '名称',
		width : 80,
		rowspan : 2
	} ] ];


	// 定义标题栏
	var columns = [ [ {
		field : 'gender',
		title : '性别',
		width : 60,
		rowspan : 2,
		align : 'center'
	}, {
		field : 'birthday',
		title : '生日',
		width : 120,
		rowspan : 2,
		align : 'center'
	}, {
		title : '其他信息',
		colspan : 2
	}, {
		field : 'telephone',
		title : '电话',
		width : 120,
		rowspan : 2
	} ], [ {
		field : 'station',
		title : '单位',
		width : 80,
		align : 'center'
	}, {
		field : 'salary',
		title : '工资',
		width : 80,
		align : 'right'
	},{
		field : 'role',
		title :'角色',
		width : 400 ,
		align : 'left',
		rowspan : 2,
		formatter : function(value,rowData,rowIndex){
			if(value == null){
				return "";
			}else{
				return value.name;
			}
		}
	}] ];
	$(function(){
		// 初始化 datagrid
		// 创建grid
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			toolbar : toolbar,
			url : "${pageContext.request.contextPath}/user_list.action",
			idField : 'id', 
			frozenColumns : frozenColumns,
			columns : columns,
			onClickRow : onClickRow,
			onDblClickRow : doDblClickRow,
			onRowContextMenu: function(e, rowIndex, rowData){ 
				// e 代表事件对象
				// rowIndex 行号
				// rowData 行数据 
				e.preventDefault(); // 阻止默认事件 (屏蔽默认菜单)
				// 弹出自定义菜单
				$('#mm').menu('show', { 
					left: e.pageX, 
					top: e.pageY
				});
				
				// 将右键点击数据行，记录form 中
				$('#showUserId').val(rowData.id);
				$('#showUserName').html(rowData.username);
			}
		});
		
		// 查询分区
		$('#searchWindow').window({
	        title: '查询分区',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
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
		
		$("body").css({visibility:"visible"});
		
		// 为授予角色 保存按钮添加 点击事件
		$('#save').click(function(){
			if($('#grantForm').form('validate')){
				$('#grantForm').submit();
			}
		});
	});
	// 双击
	function doDblClickRow(rowIndex, rowData) {
		var items = $('#grid').datagrid('selectRow',rowIndex);
		doView();
	}
	// 单击
	function onClickRow(rowIndex){

	}
	
	function doAdd() {
		location.href="${pageContext.request.contextPath}/page_admin_userinfo.action";
	}

	function doView() {
		
			$('#searchWindow').window("open");
		
	}

	function doDelete() {
		var array = $('#grid').datagrid('getSelections'); 
		if(array.length == 0){
			$.messager.alert('警告','删除前必须选择！','warning');
		}else{
			$('#delForm').submit();
		}
		$('#grid').datagrid('reload');
		$('#grid').datagrid('uncheckAll');
	}
	
</script>		
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<form id="delForm" action="${pageContext.request.contextPath }/user_delete.action" method="post">
		<div region="center" border="false">
	    	<table id="grid"></table>
		</div>
	</form>
	<!-- 自定义菜单 -->
	<div id="mm" class="easyui-menu" style="width:120px;"> 
		<div onclick="$('#grantRoleWindow').window('open');">为用户授予角色</div>
		<div>菜单二</div>
	</div>
	
	<div class="easyui-window" title="授予角色" id="grantRoleWindow" closed="true" collapsible="false" minimizable="false" maximizable="false" style="top:100px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="grantForm" action="${pageContext.request.contextPath }/user_grantRole.action" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">功能权限信息
							<input type="hidden" name="id" id="showUserId"/>
						</td>
					</tr>
					<tr>
						<td width="200">授予用户</td>
						<td id="showUserName"></td>
					</tr>
					<tr>
						<td>角色列表</td>
						<td>
							<input type="text" class="easyui-combobox" name="role.id" data-options="valueField:'id',textField:'name',url:'${pageContext.request.contextPath }/role_list.action',required:true"/>
						</td>
					</tr>
					</table>
			</form>
		</div>
	</div>
	
	
	
	<!-- 查询分用户-->
	<div class="easyui-window" title="查询分区窗口" id="searchWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div style="overflow:auto;padding:5px;" border="false">
			<form id="searchForm">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">查询条件</td>
					</tr>
					<tr>
						<td>姓名</td>
						<!-- 将省份信息 封装 model的region属性的province属性 -->
						<td><input type="text" name="username" /></td>
					</tr>
					<tr>
						<td>性别</td>
						<!-- 将省份信息 封装 model的region属性的province属性 -->
					<td>	
					<select id="gender" name="gender" style="width:100px">
					    <option value=""></option>
					    <option value="女">女</option>
					    <option value="男">男</option>
					</select>
					</td>
					</tr>
					<tr>
						<td>电话</td>
						<!-- 将省份信息 封装 model的region属性的province属性 -->
						<td><input type="text" name="telephone" /></td>
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