<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib uri="http://www.mrjc.cn/tag" prefix="mrjc" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加业务受理单</title>
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
		$("body").css({visibility:"visible"});
		
		// 点击新单按钮，将业务通知单 保存
		$('#save').click(function(){
			if($("#noticebillForm").form('validate')){
				$('#noticebillForm').submit();
			}else{
				$.messager.alert('警告','表单存在非法数据项！','warning');
			}
		});
	});
</script>
<style type="text/css">
	body {
		line-height:1.5;
	}
</style>
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="north" style="height:31px;overflow:hidden;" split="false"
		border="false">
		<div class="datagrid-toolbar">
			<a id="save" icon="icon-save" href="#" class="easyui-linkbutton"
				plain="true">新单</a>
			<a id="edit" icon="icon-edit" href="${pageContext.request.contextPath }/page_qupai_noticebill.action" class="easyui-linkbutton"
				plain="true">工单操作</a>	
		</div>
		
		<div region="center" border="false">
	    	<table id="grid"></table>
		</div>
		
		
		
	</div>
	<div region="center" style="overflow:auto;padding:5px;" border="false">
		<form id="noticebillForm" action="${pageContext.request.contextPath }/vendermaster_save.action" method="post">
			<table class="table-edit" width="95%" align="center">
				<tr class="title">
					<td colspan="4">仓库信息</td>
				</tr>
				<tr>
					<td>供应商ID:</td>
					<td><input type="text" class="easyui-validatebox" name="vendId" required="true" />
					<!--
						<script type="text/javascript">
							$(function(){
								//页面加载完成后，为手机号输入框绑定离焦事件
								$("input[name=telephone]").blur(function(){
									//获取页面输入的手机号
									var telephone = this.value;
									//发送ajax请求，请求Action，在Action中远程掉调用crm服务，获取客户信息，用于页面回显
									$.post("${pageContext.request.contextPath }/noticebill_findCustomerByTelephone.action",{"telephone":telephone},function(data){
										if(data != null){
											$(data).each(function(){
												//查询到了客户信息，可以进行页面回显
												var customerId = this.id;
												var customerName = this.name;
												var address = this.address;
												$("input[name=customerId]").val(customerId);
												$("input[name=customerName]").val(customerName);
												$("input[name=delegater]").val(customerName);
												$("input[name=pickaddress]").val(address);
											});
										}else{
											//没有查询到客户信息，不能进行页面回显
											$("input[name=customerId]").val("");
											$("input[name=customerName]").val("");
											$("input[name=delegater]").val("");
											$("input[name=pickaddress]").val("");
										}
									});
								});
							});
						</script>
						
						  -->
						</td>
					<td>供应商名称:</td>
					<td><input type="text" class="easyui-validatebox" name="vendDesc"
						required="true" /></td>
				</tr>
				<tr>
					<td>类型:</td>
					<td>
					<input class="easyui-combobox"  id="vendtype" name="vendtype.vendtypeId" 
							data-options="url:'${pageContext.request.contextPath}/vendType_ajaxlist.action',valueField:'vendtypeId',textField:'vendtypeDesc',required:true" />
					
					<td>联系地址:</td>
					<td><input type="text" class="easyui-validatebox" name="vendAddr"
						required="true" /></td>
				</tr>
				<tr class="title">
					<td colspan="4">货物信息</td>
				</tr>
				<tr>
					<td>联系电话:</td>
					<td><input type="text" class="easyui-validatebox" name="vendPhone"
						required="true" /></td>
					<td>国家:</td>
					<td><input type="text" class="easyui-validatebox" name="vendNati"
						required="true" /></td>
				</tr>
				<tr>
					<td>城市:</td>
					<td><input type="text" class="easyui-validatebox" name="vendCity"
						required="true" /></td> 
					<td>邮编:</td>
					<td><input type="text" class="easyui-validatebox" name="vendPost"
						required="true" /></td>
				</tr>
				<tr>
					<td>联系人</td>
					<td colspan="3"><input type="text" class="easyui-validatebox" name="contMan"
						required="true" size="144"/></td>
				</tr>
				<tr>
					<td>邮箱地址:</td>
					<td><input type="text" class="easyui-validatebox" name="vendEmail"
						required="true" /></td>
					<td>运输方式:</td>
					<td><input class="easyui-combobox"  id="shipvin" name="shipvin.shipviaId" 
							data-options="url:'${pageContext.request.contextPath}/shipvin_ajaxlist.action',valueField:'shipviaId',textField:'shipviaDesc',required:true" /></td>
				</tr>
				<tr>
					<td>交易额:</td>
					<td colspan="3"><textarea rows="5" cols="80" type="text" class="easyui-validatebox" name="tradeAmount"
						required="true" ></textarea></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>