<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人工调度</title>
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
	$(function(){
		$("#grid").datagrid({
			singleSelect: true,
			toolbar : [
				{
					id : 'diaodu',
					text : '人工调度',
					iconCls : 'icon-edit',
					handler : function(){
						var row = $("#grid").datagrid('getSelected'); // 获得选中行
						if(row == null){
							// 未选中
							$.messager.alert('警告','进行调度前，必须先选中一条通知单记录','warning');
						}else{
							// 选中， 将数据显示
							$("#noticebillId").val(row.id);
							$("#noticebillIdView").val(row.noticebillId);
							$("#telephone").val(row.telephone);
							$("#telephone").attr('readonly','readonly');
							$("#customerId").val(row.customerId);
							$("#customerId").attr('readonly','readonly');
							$("#customerName").val(row.customerName);
							$("#customerName").attr('readonly','readonly');
							$("#delegater").val(row.delegater);
							$("#delegater").attr('readonly','readonly');
							$("#product").val(row.product);
							$("#product").attr('readonly','readonly');
							$("#num").val(row.num);
							$("#num").attr('readonly','readonly');
							$("#weight").val(row.weight);
							$("#weight").attr('readonly','readonly');
							$("#volume").val(row.volume);
							$("#volume").attr('readonly','readonly');
							$("#pickaddress").val(row.pickaddress);
							$("#pickaddress").attr('readonly','readonly');
							$("#arrivecity").val(row.arrivecity);
							$("#arrivecity").attr('readonly','readonly');
							$("#pickdate").val(row.pickdate);
							$("#pickdate").attr('readonly','readonly');
							$("#remark").val(row.remark);
							$("#remark").attr('readonly','readonly');
							// 弹出窗口
							$("#diaoduWindow").window('open');
						}
					}
				}
			],
			columns : [[
				{
					field : 'id',
					title : '编号',
					width : 100
				},
				{
					field : 'delegater',
					title : '联系人',
					width : 100
				},
				{
					field : 'telephone',
					title : '电话',
					width : 100
				} ,
				{
					field : 'pickaddress',
					title : '取件地址',
					width : 100
				} ,
				{
					field : 'product',
					title : '商品名称',
					width : 100
				} ,
				{
					field : 'pickdate',
					title : '取件日期',
					width : 100,
					formatter : function(data,row,index){
						return data.replace("T"," ");						
					}
				} 
			]],
			url : "${pageContext.request.contextPath}/noticebill_findnoassociations.action"
		});
		
		// 点击保存按钮，为通知单 进行分单 --- 生成工单
		$("#save").click(function(){
			$("#diaoduForm").form('submit',{
				url : '${pageContext.request.contextPath}/noticebill_diaodu.action',
				success : function(data){
					$("#diaoduWindow").window('close');
					$("#noticebillId").val('');
					$("#noticebillIdView").html('');
					$("#grid").datagrid('reload');
				}
			});
		});
	});
</script>
</head>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
	<table id="grid"></table>
</div>
<div class="easyui-window" title="人工调度" id="diaoduWindow" closed="true" collapsible="false" minimizable="false" maximizable="false" style="top:100px;left:200px;width: 800px; height: 300px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="diaoduForm" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">人工调度</td>
					</tr>
					<tr>
						<td>通知单编号</td>
						<td>
						<input type="hidden" name="id" id="noticebillId"/>
						<span id="noticebillIdView"></span>
						</td>
					</tr>
					<tr>
						<td>选择取派员</td>
						<td>
							<input class="easyui-combobox"  editable="false" required="true" name="staff.id"  
    							data-options="valueField:'id',textField:'name',url:'${pageContext.request.contextPath }/staff_ajaxlist.action'" />
						</td>
					</tr>
					<tr>
					<td>来电号码:</td>
						<td>
						<input type="text" class="easyui-validatebox"  id="telephone" name="telephone"   editable="false" />			
						</td>
						<td>客户编号:</td>
						<td>
						<input type="text" class="easyui-validatebox" id="customerId"  name="customerId"  editable="false"  />
						</td>
					</tr>
					
					<tr>
					<td>客户姓名:</td>
					<td><input type="text" class="easyui-validatebox" name="customerName" id="customerName"  editable="false"
						 /></td>
					<td>联系人:</td>
					<td><input type="text" class="easyui-validatebox" name="delegater" id="delegater"  editable="false"
						 /></td>
				</tr>
				<tr class="title">
					<td colspan="4">货物信息</td>
				</tr>
				<tr>
					<td>品名:</td>
					<td><input type="text" class="easyui-validatebox" name="product"  editable="false"
						 id="product" /></td>
					<td>件数:</td>
					<td><input type="text" class="easyui-validatebox" name="num" id="num"  editable="false"
						 /></td>
				</tr>
				<tr>
					<td>重量:</td>
					<td><input type="text" class="easyui-validatebox" name="weight"
						required="true"  id="weight"   editable="false"/></td> 
					<td>体积:</td>
					<td><input type="text" class="easyui-validatebox" name="volume"
						required="true" id="volume"  editable="false" /></td>
				</tr>
				<tr>
					<td>取件地址</td>
					<td colspan="3"><input type="text" class="easyui-validatebox" name="pickaddress"
						id="pickaddress"  size="144"  editable="false"/></td>
				</tr>
				<tr>
					<td>到达城市:</td>
					<td><input type="text" class="easyui-validatebox" name="arrivecity"
						id="arrivecity" editable="false" /></td>
				</tr>
				<tr>
					<td>备注:</td>
					<td colspan="3"><textarea rows="5" cols="80" type="text" class="easyui-validatebox" name="remark" id="remark"
						 ></textarea></td>
				</tr>
					</table>
			</form>
		</div>
	</div>
</body>
</html>