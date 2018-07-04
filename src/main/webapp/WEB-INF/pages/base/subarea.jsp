<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.mrjc.cn/tag" prefix="mrjc" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理分区</title>
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
<script src="${pageContext.request.contextPath }/js/highcharts/highcharts.js"></script>
<script src="${pageContext.request.contextPath }/js/highcharts/modules/exporting.js"></script>
<script type="text/javascript">
	/*
		[
			{name:'name',value:'张三'},
			{name:'name',value:'李四'},
			{name:'age',value:'20'}
		]
		转换过程 
		serializeObj = {	
			name : [张三,李四],
			age : 20
		}
	*/

	// 导入自定义 jquery 函数，将form 数据转换为 json
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
		$('#addSubareaWindow').window("open");
	}
	
	function doEdit(){
		alert("修改...");
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
	
	function doSearch(){
		$('#searchWindow').window("open");
	}
	
	function doExport(){
		location.href="${pageContext.request.contextPath}/subarea_exportXls.action";
	}
	function doshowHighcharts(){
		$('#showSubareaWindow').window("open");
		//页面加载完成后，动态创建图表
		$.post("${pageContext.request.contextPath}/subarea_findSubareasGroupByProvince.action",function(data){
			$("#test").highcharts({
				title: {
		            text: '区域分区分布图'
		        },
		        series: [{
		            type: 'pie',
		            name: '区域分区分布图',
		            data: data
		        }]
			});
		});
	}
	
	
	//工具栏
	var toolbar = [
	<mrjc:privilege value="管理分区查询">
	{
		id : 'button-search',	
		text : '查询',
		iconCls : 'icon-search',
		handler : doSearch
	}, 
	</mrjc:privilege>
	<mrjc:privilege value="管理分区增加">
	{
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	},
	</mrjc:privilege>
	<mrjc:privilege value="管理分区修改">
	{
		id : 'button-edit',	
		text : '修改',
		iconCls : 'icon-edit',
		handler : doEdit
	},
	</mrjc:privilege>
	<mrjc:privilege value="管理分区删除">
	{
		id : 'button-delete',
		text : '删除',
		iconCls : 'icon-cancel',
		handler : doDelete
	},
	</mrjc:privilege>
	<mrjc:privilege value="管理分区导入">
	{
		id : 'button-import',
		text : '导入',
		iconCls : 'icon-redo',
	
	},
	</mrjc:privilege>
	<mrjc:privilege value="管理分区导出">
	{
		id : 'button-export',
		text : '导出',
		iconCls : 'icon-undo',
		handler : doExport
	},
	</mrjc:privilege>
	<mrjc:privilege value="管理分区查询">
	{
		id : 'button-showHighcharts',
		text : '显示区域分布图',
		iconCls : 'icon-chart',
		handler : doshowHighcharts
	}
	</mrjc:privilege>
	];
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	}, {
		field : 'showid',
		title : '分拣编号',
		width : 120,
		align : 'center',
		formatter : function(data,row ,index){
			return row.id;
		}
	},{
		field : 'province',
		title : '省',
		width : 120,
		align : 'center',
		formatter : function(data,row ,index){
			if(row.region==null){
				return "";
			}
			return row.region.province;
		}
	}, {
		field : 'city',
		title : '市',
		width : 120,
		align : 'center',
		formatter : function(data,row ,index){
			if(row.region==null){
				return "";
			}
			return row.region.city;
		}
	}, {
		field : 'district',
		title : '区',
		width : 120,
		align : 'center',
		formatter : function(data,row ,index){
			if(row.region==null){
				return "";
			}
			return row.region.district;
		}
	}, {
		field : 'addresskey',
		title : '关键字',
		width : 120,
		align : 'center'
	}, {
		field : 'startnum',
		title : '起始号',
		width : 100,
		align : 'center'
	}, {
		field : 'endnum',
		title : '终止号',
		width : 100,
		align : 'center'
	} , {
		field : 'single',
		title : '单双号',
		width : 100,
		align : 'center'
	} , {
		field : 'position',
		title : '位置',
		width : 200,
		align : 'center'
	} ] ];
	
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 管理分区数据表格
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : true,
			border : true,
			rownumbers : true,
			striped : true,
			pageList: [30,50,100],
			pagination : true,
			toolbar : toolbar,
			url : "${pageContext.request.contextPath}/subarea_pageQuery.action",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		// 添加、修改分区
		$('#addSubareaWindow').window({
	        title: '添加修改分区',
	        width: 600,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		$('#showSubareaWindow').window({
	        title: '查看分区分布',
	        width: 800,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 600,
	        resizable:false
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
		
		// 为添加分区 ，保存按钮添加点击 事件
		$('#save').click(function(){
			// 判断form 校验是否通过
			if($('#subareaForm').form('validate')){
				// 通过校验
				$('#subareaForm').submit();
			}else{
				// 提示
				$.messager.alert('警告','表单存在非法数据项，请重新输入！','warning');
			}
		});
		
		// 对批量导入添加一键上传效果 
		$('#button-import').upload({
			name : 'upload' , // <input type="file" name="upload" 
			action : '${pageContext.request.contextPath}/subarea_importXls.action', // 表单提交路径
			onComplete : function(response){
				var data = eval("("+response+")");
				$.messager.alert('信息',data.msg,'info');
				// 使datagrid 数据刷新
				$('#grid').datagrid('reload');
			}
		});
		
	});

	function doDblClickRow(rowIndex, rowData){
		if(rowData.region==null){
			$('#id').val(rowData.id);
			$('#id').attr('readonly','readonly');
			$('#addresskey').val(rowData.addresskey);
			$('#startnum').val(rowData.startnum);
			$('#endnum').val(rowData.endnum);
			$('#position').val(rowData.position);
			$('#single').combobox('setValue',rowData.single);
			$('#addSubareaWindow').window('open');
		}else{
			var str = rowData.region.province+","+
			rowData.region.city+","+
			rowData.region.district;
			// form回显
			$('#id').val(rowData.id);
			$('#id').attr('readonly','readonly');
			$('#addresskey').val(rowData.addresskey);
			$('#startnum').val(rowData.startnum);
			$('#endnum').val(rowData.endnum);
			$('#position').val(rowData.position);
			// 弹出窗口
			$('#single').combobox('setValue',rowData.single);
			
			$('#regionId').combobox('setValue', str); 
			$('#addSubareaWindow').panel({title:"修改区域"});
			$('#addSubareaWindow').window('open');
		}
		
	}
	
	
	// 点击保存按钮，提交标准form
	function commitStandardForm(){
		// 先判断form 是否通过校验，如果通过 ，提交表单 
		if($('#subareaForm').form('validate')){// 执行EasyUI 校验方法
			// 通过校验 
			$('#subareaForm').submit();
		}else{
			// 没通过校验
			$.messager.alert('警告','表单存在非法数据，请重新输入','warning');
		}
	}
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<form id="delForm" action="${pageContext.request.contextPath }/subarea_delBatch.action" method="post">
		<div region="center" border="false">
	    	<table id="grid"></table>
		</div>
	</form>
	<!-- 添加 修改分区 -->
	<div class="easyui-window" title="分区添加修改" id="addSubareaWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		
		<div style="overflow:auto;padding:5px;" border="false">
			<form id="subareaForm" action="${pageContext.request.contextPath }/subarea_saveOrUpdate.action" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">分区信息</td>
					</tr>
					<tr>
						<td>分拣编码</td>
						<td><input type="text" id="id" name="id" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>选择区域</td>
						<td>
							<!-- 提交区域的编号，封装 Subarea分区对象 region属性的 id中 -->
							<input class="easyui-combobox" name="region.id" id="regionId"
							data-options="valueField:'id', textField:'info', 
							url:'${pageContext.request.contextPath }/region_ajaxlist.action', required:true"/>
						</td>
					</tr>
					<tr>
						<td>关键字</td>
						<td><input type="text" id="addresskey" name="addresskey" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>起始号</td>
						<td><input type="text" id="startnum" name="startnum" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>终止号</td>
						<td><input type="text" id="endnum" name="endnum" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>单双号</td>
						<td>
							<select class="easyui-combobox" id="single" name="single" style="width:150px;">  
							    <option value="0">单双号</option>  
							    <option value="1">单号</option>  
							    <option value="2">双号</option>  
							</select> 
						</td>
					</tr>
					<tr>
						<td>位置信息</td>
						<td><input type="text" id="position" name="position" class="easyui-validatebox" required="true" style="width:250px;"/></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<!-- 查询分区 -->
	<div class="easyui-window" title="查询分区窗口" id="searchWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div style="overflow:auto;padding:5px;" border="false">
			<form id="searchForm">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">查询条件</td>
					</tr>
					<tr>
						<td>省</td>
						<!-- 将省份信息 封装 model的region属性的province属性 -->
						<td><input type="text" name="region.province" /></td>
					</tr>
					<tr>
						<td>市</td>
						<td><input type="text" name="region.city"/></td>
					</tr>
					<tr>
						<td>区（县）</td>
						<td><input type="text" name="region.district" /></td>
					</tr>
					<tr>
						<td>定区编码</td>
						<td><input type="text" name="decidedZone.id" /></td>
					</tr>
					<tr>
						<td>关键字</td>
						<td><input type="text" name="addresskey" /></td>
					</tr>
					<tr>
						<td colspan="2"><a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> </td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	
	<!-- 查看区域分布 -->
	<!-- 添加 修改分区 -->
	<div class="easyui-window" title="区域分布展示" id="showSubareaWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div id="test" split="false" border="false" >
		
		</div>
	</div>
</body>
</html>