<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.mrjc.cn/tag" prefix="mrjc" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>区域设置</title>
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
<!-- 导入一键上传 js -->
<script type="text/javascript" src="${pageContext.request.contextPath }/js/ocupload/jquery.ocupload-1.1.2.js"></script>
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
		$('#id').val('');
		$('#province').val('');
		$('#city').val('');
		$('#district').val('');
		$('#postcode').val('');
		$('#shortcode').val('');
		$('#citycode').val('');
		$('#addRegionWindow').panel({title:"新增区域"});
		$('#addRegionWindow').window("open");
	}
	
	function doView(){
		$('#searchWindow').window("open");
	}
	
	function doDelete(){
		// 先判断 用户是否选择
		var array = $('#grid').datagrid('getSelections'); 
		if(array.length == 0){
			$.messager.alert('警告','删除前必须选择！','warning');
		}else{
			$('#delForm').submit();
		}
	}
	
	//工具栏
	var toolbar = [
	<mrjc:privilege value="区域设置查询">
	{
		id : 'button-edit',	
		text : '查询',
		iconCls : 'icon-edit',
		handler : doView
	}, 
	</mrjc:privilege>
	<mrjc:privilege value="区域设置增加">
	{
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	},
	</mrjc:privilege>
	<mrjc:privilege value="区域设置删除">
	{
		id : 'button-delete',
		text : '删除',
		iconCls : 'icon-cancel',
		handler : doDelete
	},
	</mrjc:privilege>
	<mrjc:privilege value="区域设置批量导入">
	{
		id : 'button-import',
		text : '批量导入',
		iconCls : 'icon-save'
	},
	</mrjc:privilege>
	];
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	},{
		field : 'province',
		title : '省',
		width : 120,
		align : 'center'
	}, {
		field : 'city',
		title : '市',
		width : 120,
		align : 'center'
	}, {
		field : 'district',
		title : '区',
		width : 120,
		align : 'center'
	}, {
		field : 'postcode',
		title : '邮编',
		width : 120,
		align : 'center'
	}, {
		field : 'shortcode',
		title : '简码',
		width : 120,
		align : 'center'
	}, {
		field : 'citycode',
		title : '城市编码',
		width : 200,
		align : 'center'
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
			pageList: [10,20,50],
			pagination : true,
			toolbar : toolbar,
			url : "${pageContext.request.contextPath}/region_pageQuery.action",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		// 添加、修改区域窗口
		$('#addRegionWindow').window({
	        title: '添加修改区域',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		// 查询区域
		$('#searchWindow').window({
	        title: '查询区域',
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
		
		
		// 对批量导入添加一键上传效果 
		$('#button-import').upload({
			name : 'upload' , // <input type="file" name="upload" 
			action : '${pageContext.request.contextPath}/region_importXls.action', // 表单提交路径
			onComplete : function(response){
				var data = eval("("+response+")");
				$.messager.alert('信息',data.msg,'info');
				// 使datagrid 数据刷新
				$('#grid').datagrid('reload');
			}
		});
		
	});

	function doDblClickRow(rowIndex, rowData){
		// form回显
		$('#id').val(rowData.id);
		$('#province').val(rowData.province);
		$('#city').val(rowData.city);
		$('#district').val(rowData.district);
		$('#postcode').val(rowData.postcode);
		$('#shortcode').val(rowData.shortcode);
		$('#citycode').val(rowData.citycode);
		// 弹出窗口
		$('#addRegionWindow').panel({title:"修改区域"});
		$('#addRegionWindow').window('open');
		
	}
	
	// 点击保存按钮，提交标准form
	function commitStandardForm(){
		// 先判断form 是否通过校验，如果通过 ，提交表单 
		if($('#regionForm').form('validate')){// 执行EasyUI 校验方法
			// 通过校验 
			$('#regionForm').submit();
		}else{
			// 没通过校验
			$.messager.alert('警告','表单存在非法数据，请重新输入','warning');
		}
	}
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<form id="delForm" action="${pageContext.request.contextPath }/region_delBatch.action" method="post">
		<div region="center" border="false">
	    	<table id="grid"></table>
		</div>
	</form>
	<div class="easyui-window" title="区域添加修改" id="addRegionWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="javascript:commitStandardForm();" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="regionForm" action="${pageContext.request.contextPath }/region_saveOrUpdate.action" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">区域信息
							<input type="hidden" name="id" id="id" />
						</td>
					</tr>
					<tr>
						<td>省</td>
						<td><input type="text" id="province" name="province" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>市</td>
						<td><input type="text" id="city" name="city" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>区</td>
						<td><input type="text" id="district" name="district" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>邮编</td>
						<td><input type="text" id="postcode" name="postcode" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>简码</td>
						<td><input type="text" id="shortcode" name="shortcode" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>城市编码</td>
						<td><input type="text" id="citycode" name="citycode" class="easyui-validatebox" required="true"/></td>
					</tr>
					</table>
			</form>
		</div>
	</div>
	
	
		<!-- 查询区域-->
	<div class="easyui-window" title="查询区域窗口" id="searchWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div style="overflow:auto;padding:5px;" border="false">
			<form id="searchForm">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">查询条件</td>
					</tr>
					
					<tr>
						<tr>
						<td>省</td>
						<!-- 将省份信息 封装 model的region属性的province属性 -->
						<td><input type="text" name="province" /></td>
					</tr>
					<tr>
						<td>市</td>
						<td><input type="text" name="city"/></td>
					</tr>
					<tr>
						<td>区（县）</td>
						<td><input type="text" name="district" /></td>
					</tr>
					<tr>
						<td>邮编</td>
						<td><input type="text" name="postcode" /></td>
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