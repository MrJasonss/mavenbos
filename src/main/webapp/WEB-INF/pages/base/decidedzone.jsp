<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://www.mrjc.cn/tag" prefix="mrjc" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理定区/调度排班</title>
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
		$('#addDecidedzoneWindow').window("open");
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
	
	// 在关联客户的方法 
	function doAssociations(){
		// 先判断用户是否选择 定区数据 
		var rowData = $('#grid').datagrid('getSelected');
		if(rowData == null){
			// 未选中
			$.messager.alert('警告','定区关联客户前，必须要先选中一个定区','warning');
		}else{
			// 已经选中
			
			// 清除原来选项
			$('#noassociationSelect').html('');
			$('#associationSelect').html('');
			
			// 发起两次Ajax请求 
			$.post("${pageContext.request.contextPath}/decidedzone_findNoAssociationCustomers.action", function(data){
				$(data).each(function(){
					var option = $("<option value='"+this.id+"'>"+this.name+"("+this.address+")</option>");
					$('#noassociationSelect').append(option);
				});
			});
			
			$.post("${pageContext.request.contextPath}/decidedzone_findHasAssociationCustomers",{id : rowData.id}, function(data){
				$(data).each(function(){
					var option = $("<option value='"+this.id+"'>"+this.name+"("+this.address+")</option>");
					$('#associationSelect').append(option);
				});
			});
			
			// 显示关联客户的窗口
			$('#customerWindow').window('open');
		}
	}
	
	//工具栏
	var toolbar = [ 
	<mrjc:privilege value="管理定区查询">          
	{
		id : 'button-search',	
		text : '查询',
		iconCls : 'icon-search',
		handler : doSearch
	}, 
	</mrjc:privilege>
	<mrjc:privilege value="管理定区增加">
	{
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	},
	</mrjc:privilege>
	<mrjc:privilege value="管理定区修改">
	{
		id : 'button-edit',	
		text : '修改',
		iconCls : 'icon-edit',
		handler : doEdit
	},
	</mrjc:privilege>
	<mrjc:privilege value="管理定区删除">
	{
		id : 'button-delete',
		text : '删除',
		iconCls : 'icon-cancel',
		handler : doDelete
	},
	</mrjc:privilege>
	<mrjc:privilege value="管理定区关联客户">
	{
		id : 'button-association',
		text : '关联客户',
		iconCls : 'icon-sum',
		handler : doAssociations
	},
	</mrjc:privilege>
	];
	// 定义列
	var columns = [ [ {
		field : 'id',
		title : '定区编号',
		width : 120,
		align : 'center',
		checkbox : true,
	},{
		field : 'name',
		title : '定区名称',
		width : 120,
		align : 'center'
	}, {
		field : 'staff.name',
		title : '负责人',
		width : 120,
		align : 'center',
		formatter : function(data,row ,index){
			return row.staff.name;
		}
	}, {
		field : 'staff.telephone',
		title : '联系电话',
		width : 120,
		align : 'center',
		formatter : function(data,row ,index){
			return row.staff.telephone;
		}
	}, {
		field : 'staff.station',
		title : '所属公司',
		width : 120,
		align : 'center',
		formatter : function(data,row ,index){
			return row.staff.station;
		}
	} ] ];
	
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 收派标准数据表格
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : true,
			border : true,
			rownumbers : true,
			striped : true,
			pageList: [30,50,100],
			pagination : true,
			toolbar : toolbar,
			url : "${pageContext.request.contextPath}/decidedzone_pageQuery.action",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow,
			singleSelect:true
		});
		//DataGrid右键菜单代码： 
		$("#grid").datagrid({ 
		 onRowContextMenu: function (e, rowIndex, rowData) { //右键时触发事件 
		 //三个参数：e里面的内容很多，真心不明白，rowIndex就是当前点击时所在行的索引，rowData当前行的数据 
		 e.preventDefault(); //阻止浏览器捕获右键事件 
		 $(this).datagrid("clearSelections"); //取消所有选中项 
		 $(this).datagrid("selectRow", rowIndex); //根据索引选中该行 
		 $('#menu').menu('show', { 
		  //显示右键菜单 
		  left: e.pageX,//在鼠标点击处显示菜单 
		  top: e.pageY 
		 }); 
		 e.preventDefault(); //阻止浏览器自带的右键菜单弹出 
		 } 
		}); 
		
		// 添加、修改定区
		$('#addDecidedzoneWindow').window({
	        title: '添加修改定区',
	        width: 600,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		
		// 查询定区
		$('#searchWindow').window({
	        title: '查询定区',
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
			// 调用datagrid 执行查询，在查询是，缓存条件 
			$('#grid').datagrid('load',params);// 重新加载datagrid指定url
			
			// 窗口关闭
			$('#searchWindow').window('close');
		});
		
		// 为添加修改定区 ，保存按钮 添加 click事件
		$('#save').click(function(){
			// 提交前，先校验 form 输入
			if($('#decidedzoneForm').form('validate')){
				$('#decidedzoneForm').submit();
			}else{
				$.messager.alert('警告','表单存在非法数据项，请重新输入','warning');
			}
		});
		
		// 添加左右 移动关联客户事件
		$('#toRight').click(function(){
			// 将未关联 移到 已经关联
			$('#associationSelect').append($('#noassociationSelect option:selected'));
		});
		$('#toLeft').click(function(){
			$('#noassociationSelect').append($('#associationSelect option:selected'));
		});
		
		// 点击关联客户，提交表单
		$('#associationBtn').click(function(){
			// 关联select 中所有option 选中
			$('#associationSelect option').attr('selected','selected');
			// 提交表单
			$('#customerDecidedZoneId').val($('#grid').datagrid('getSelected').id);
			$('#customerForm').submit();
		});
	});

	function doDblClickRow(rowIndex,rowData){
	
		$('#association_subarea').datagrid( {
			fit : true,
			border : true,
			rownumbers : true,
			striped : true,
			url : "${pageContext.request.contextPath}/subarea_pageQuery.action",
			queryParams : {"decidedZone.id": rowData.id},
			columns : [ [{
				field : 'id',
				title : '分拣编号',
				width : 120,
				align : 'center'
				
			},{
				field : 'province',
				title : '省',
				width : 120,
				align : 'center',
				formatter : function(data,row ,index){
					return row.region.province;
				}
			}, {
				field : 'city',
				title : '市',
				width : 120,
				align : 'center',
				formatter : function(data,row ,index){
					return row.region.city;
				}
			}, {
				field : 'district',
				title : '区',
				width : 120,
				align : 'center',
				formatter : function(data,row ,index){
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
			} ] ]
		});
		$('#association_customer').datagrid( {
			fit : true,
			border : true,
			rownumbers : true,
			striped : true,
			url : "${pageContext.request.contextPath}/decidedzone_findHasAssociationCustomers.action?id="+rowData.id,
			columns : [[{
				field : 'id',
				title : '客户编号',
				width : 120,
				align : 'center'
			},{
				field : 'name',
				title : '客户名称',
				width : 120,
				align : 'center'
			}, {
				field : 'station',
				title : '所属单位',
				width : 120,
				align : 'center'
			}]]
		});
		
	}
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">

		
		<form id="delForm" action="${pageContext.request.contextPath }/decidedzone_delBatch.action" method="post">
		<div region="center" border="false" >
	    	<table id="grid"></table>
		</div>
	

	<div region="south" border="false" style="height:150px">
		<div id="tabs" fit="true" class="easyui-tabs">
			<div title="关联分区" id="subArea"
				style="width:100%;height:50%;overflow:hidden">
				<table id="association_subarea"></table>
			</div>	
			<div title="关联客户" id="customers"
				style="width:100%;height:0%;overflow:hidden">
				<table id="association_customer"></table>
			</div>	
		</div>
	</div>
	</form>
	<!-- 添加 修改分区 -->
	<div class="easyui-window" title="定区添加修改" id="addDecidedzoneWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		
		<div style="overflow:auto;padding:5px;" border="false">
			<form id="decidedzoneForm" 
				action="${pageContext.request.contextPath }/decidedzone_saveOrUpdate.action" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">定区信息</td>
					</tr>
					<tr>
						<td>定区编码</td>
						<td><input type="text" name="id" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>定区名称</td>
						<td><input type="text" name="name" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>选择负责人</td>
						<td>
							<input class="easyui-combobox" name="staff.id"  
    							data-options="valueField:'id',textField:'name',url:'${pageContext.request.contextPath }/staff_ajaxlist.action'" />  
						</td>
					</tr>
					<tr height="300">
						<td valign="top">关联分区</td>
						<td>
							<table id="subareaGrid"  class="easyui-datagrid" border="false" style="width:300px;height:300px" 
							data-options="url:'${pageContext.request.contextPath }/subarea_findnoassociations.action',fitColumns:true,singleSelect:false">
								<thead>  
							        <tr>  
							            <th data-options="field:'subareaId',width:30,checkbox:true">编号</th>  
							            <th data-options="field:'addresskey',width:150">关键字</th>  
							            <th data-options="field:'position',width:200,align:'right'">位置</th>  
							        </tr>  
							    </thead> 
							</table>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>

	
	<!-- 关联客户窗口 -->
	<div class="easyui-window" title="关联客户窗口" id="customerWindow" collapsible="false" closed="true" minimizable="false" maximizable="false" style="top:20px;left:200px;width: 400px;height: 300px;">
		<div style="overflow:auto;padding:5px;" border="false">
			<form id="customerForm" 
				method="post" action="${pageContext.request.contextPath }/decidedzone_assignedCustomerToDecidedZone.action">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="3">关联客户</td>
					</tr>
					<tr>
						<td>
							<!-- 定区id 域 -->
							<input type="hidden" name="id" id="customerDecidedZoneId" />
							<select id="noassociationSelect" multiple="multiple" size="10"></select>
						</td>
						<td>
							<input type="button" value="》》" id="toRight"><br/>
							<input type="button" value="《《" id="toLeft">
						</td>
						<td>
							<select id="associationSelect" name="customerIds" multiple="multiple" size="10"></select>
						</td>
					</tr>
					<tr>
						<td colspan="3"><a id="associationBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">关联客户</a> </td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	
	
	
		<!-- 查询定区 -->
	<div class="easyui-window" title="查询定区窗口" id="searchWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div style="overflow:auto;padding:5px;" border="false">
			<form id="searchForm">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">查询条件</td>
					</tr>
					<tr>
						<td>定区编号</td>
						<td><input type="text" name="id" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>定区名称</td>
						<td><input type="text" name="name" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>所属公司</td>
						<td><input type="text" name="staff.station" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>负责人</td>
						<td><input type="text" name="staff.name" class="easyui-validatebox" required="true"/></td>
					</tr>
					
					<tr>
						<td colspan="2"><a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> </td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	
	
<!-- 右击菜单 -->
<div id="menu" class="easyui-menu" style="width: 30px; display: none;"> 
 <!--放置一个隐藏的菜单Div-->
 	<div id="btn_Delete" data-options="iconCls:'icon-remove'" onclick="doDelete()">删除 </div> 
 <!--具体的菜单事件请自行添加，跟toolbar的方法是基本一样的-->
 <div id="btn_Modify" data-options="iconCls:'icon-edit'">编辑</div> 
 </div> 
</body>
</html>