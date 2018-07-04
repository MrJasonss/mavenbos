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
		field : 'name',
		title : '标准名称',
		width : 120,
		align : 'center'
	}, {
		field : 'minweight',
		title : '最小重量',
		width : 120,
		align : 'center'
	}, {
		field : 'maxweight',
		title : '最大重量',
		width : 120,
		align : 'center'
	}, {
		field : 'user.username',
		title : '操作人',
		width : 120,
		align : 'center',
		formatter : function(value,rowData, rowIndex){ 
			// value 表示匹配了当前属性的值
			// rowData 代表整行数据 
			// rowIndex 代表行号 
			if(rowData.user!=null){
				return rowData.user.username;
			}
		}
	}, {
		field : 'updatetime',
		title : '操作时间',
		width : 160,
		align : 'center',
		formatter : function(value ,rowData, rowIndex){
			return value.replace("T"," ");
		}
	}, {
		field : 'user.station',
		title : '操作单位',
		width : 200,
		align : 'center',
		formatter : function(value,rowData, rowIndex){ 
			// value 表示匹配了当前属性的值
			// rowData 代表整行数据 
			// rowIndex 代表行号 
			if(rowData.user!=null){
				return rowData.user.station;
			}
		}
	} ] ];
	
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
			url : "${pageContext.request.contextPath}/standard_pageQuery.action",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		// 添加收派标准窗口
		$('#addStandardWindow').window({
            title: '添加收派标准',
            width: 400,
            modal: true,
            shadow: true,
            closed: true,
            height: 400,
            resizable:false
        });
		
		//新增
		// 查询取派标准
		$('#searchWindow').window({
	        title: '查询取派标准',
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
		
	});
	
	// 修改数据 
	function doDblClickRow(rowIndex, rowData){ // rowIndex行号，rowData 双击行数据
		// form回显
		$('#name').val(rowData.name);
		$('#minweight').numberbox('setValue', rowData.minweight); 	
		$('#maxweight').numberbox('setValue', rowData.maxweight); 	
		$('#id').val(rowData.id) ;
	
		// 弹出修改窗口
		$('#addStandardWindow').window('open');
	}
	
	// 点击保存按钮，提交标准form
	function commitStandardForm(){
		// 先判断form 是否通过校验，如果通过 ，提交表单 
		if($('#standardForm').form('validate')){// 执行EasyUI 校验方法
			// 通过校验 
			$('#standardForm').submit();
		}else{
			// 没通过校验
			$.messager.alert('警告','表单存在非法数据，请重新输入','warning');
		}
	}
		
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
    <form id="delForm" action="${pageContext.request.contextPath }/standard_delBatch.action" method="post">
	    <div region="center" border="false">
	    		<table id="grid"></table>
		</div>
    </form>
	
	<div class="easyui-window" title="添加收派标准" id="addStandardWindow" collapsible="false" minimizable="false" maximizable="false" style="top:100px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="javascript:commitStandardForm();" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="standardForm" action="${pageContext.request.contextPath }/standard_save.action" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">收派标准信息
							<input type="hidden" name="id" id="id" />
						</td>
					</tr>
					<tr>
						<td>标准名称</td>
						<td><input id="name" name="name" type="text" class="easyui-validatebox" data-options="required:true" /></td>
					</tr>
					<tr>
						<td>最小重量</td>
						<td><input id="minweight" name="minweight" type="text" class="easyui-numberbox"  /></td>
					</tr>
					<tr>
						<td>最大重量</td>
						<td><input id="maxweight" name="maxweight" type="text" class="easyui-numberbox" /></td>
					</tr>
					</table>
			</form>
		</div>
	</div>
	
	
	
	<!-- 查询收派标准 -->
	<div class="easyui-window" title="查询窗口" id="searchWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div style="overflow:auto;padding:5px;" border="false">
			<form id="searchForm">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">查询条件</td>
					</tr>
					<tr>
						<td>标准名称</td>
						<!-- 将省份信息 封装 model的standard属性的name属性 -->
						<td><input type="text" id="name" name="name" class="easyui-validatebox" required="true"/></td>
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