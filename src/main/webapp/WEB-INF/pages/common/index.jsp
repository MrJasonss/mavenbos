<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	   <%@taglib uri="http://www.mrjc.cn/tag" prefix="mrjc" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>校园物流管理项目主界面</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link id="easyuiTheme" rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/metro/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<!-- 导入ztree类库 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/js/ztree/metroStyle.css"
	type="text/css" />
<script
	src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script type="text/javascript">
	// 初始化ztree菜单
	$(function() {
		var setting = {
			view: {
				
				showLine: false
			},
			data : {
				key : {
					title : "t" // 鼠标在停留在菜单上提示
				},
				simpleData : { // 简单数据 
					enable : true,
					pIdKey: "parentId",
				}
			},
			callback : {
				onClick : onClick
			}
		};
		
		// 基本功能菜单加载
		$.ajax({
			url : '${pageContext.request.contextPath}/function_menu.action',
			type : 'POST',
			dataType : 'text',
			success : function(data) {
				var zNodes = eval("(" + data + ")");
				$.fn.zTree.init($("#treeMenu"), setting, zNodes);
			},
			error : function(msg) {
				alert('菜单加载异常!');
			}
		});
		
		var setting2 = {
				data : {
					key : {
						title : "t" // 鼠标在停留在菜单上提示
					},
					simpleData : { // 简单数据 
						enable : true
					}
				},
				callback : {
					onClick : onClick
				}
		};
		<mrjc:privilege value="系统管理权限">    
		// 系统管理菜单加载
		$.ajax({
			url : '${pageContext.request.contextPath}/json/admin.json',
			type : 'POST',
			dataType : 'text',
			success : function(data) {
				var zNodes = eval("(" + data + ")");
				$.fn.zTree.init($("#adminMenu"), setting2, zNodes);
			},
			error : function(msg) {
				alert('菜单加载异常!');
			}
		});
		</mrjc:privilege>
		// 页面加载后 右下角 弹出窗口
		/**************/
		window.setTimeout(function(){
			$.messager.show({
				title:"消息提示",
				msg:'欢迎登录，${user.username}！ <a href="javascript:void" onclick="top.showAbout();">联系管理员</a>',
				timeout:5000
			});
		},2000);
		window.setTimeout(function(){
			$.messager.show({
				title:"校园招聘",
				msg:'<img src="${pageContext.request.contextPath }/images/1.jpg" style="width:275px;height:370px"/>',
				timeout:5000,
				width:300,
				height:400,
			});
		},5000);
		/*************/
		
		$("#btnCancel").click(function(){
			$('#editPwdWindow').window('close');
		});
		
		$("#btnEp").click(function(){
			// 获得新密码和确认密码的输入内容 
			var newPass = $("#txtNewPass").val(); // document.getElementById("txtNewPass").value; 
			var rePass = $("#txtRePass").val();
			
			// 进行校验
			// 新密码是否为空
			if($.trim(newPass)==""){ // 也可以写为 jQuery.trim
				// 新密码输入为空 
				$.messager.alert('警告','新密码不能为空或者空白字符！','warning');
				return ;
			}
			// 两次密码是否一致
			if($.trim(newPass) != $.trim(rePass)){
				$.messager.alert('警告','两次密码输入不一致！','warning');
				return ;
			}
			
			// 通过 Ajax 将新密码发送到服务器 
			$.post("${pageContext.request.contextPath}/user_editpassword.action", {password: newPass}, function(data){
				if(data.result == "success"){
					$.messager.alert("信息", data.msg, "info");
				}else{
					$.messager.alert("信息", data.msg, "info");
				}
				// 窗口关闭 
				$("#editPwdWindow").window('close');
			});
		});
		
		

		$("#btnemailEp").click(function(){
			// 获得新密码和确认密码的输入内容 
			var newPass = $("#txtemail").val(); // document.getElementById("txtNewPass").value; 
			var rePass = $("#context").val();
			if(newPass.length<=0 || rePass.length<=0){
				alert("邮箱地址或者内容为空");
				return "";
			}else{
				// 通过 Ajax 将新密码发送到服务器 
				$.post("${pageContext.request.contextPath}/user_findPassWd.action", {email: newPass,context:rePass}, function(data){
					if(data.result == "success"){
						$.messager.alert("信息", data.msg, "info");
					}else{
						$.messager.alert("信息", data.msg, "info");
					}
					// 窗口关闭 
					$("#emailWindow").window('close');
				});
			}
		});
		
		$("#btnemailCancel").click(function(){
			$('#emailWindow').window('close');
		});
	});

	function onClick(event, treeId, treeNode, clickFlag) {
		if (treeNode.click != false) {
			open(treeNode.name, treeNode.page);
		}
	}

	// 开启一个新的tab页面
	function open(text, url) {
		if ($("#tabs").tabs('exists', text)) {
			$('#tabs').tabs('select', text);
		} else {
			var content = '<div style="width:100%;height:100%;overflow:hidden;">'
					+ '<iframe src="'
					+ url
					+ '" scrolling="auto" style="width:100%;height:100%;border:0;" ></iframe></div>';

			$('#tabs').tabs('add', {
				title : text,
				content : content,
				//href : url
				closable : true
			});
		}
	}

	/*******顶部特效 *******/
	/**
	 * 更换EasyUI主题的方法
	 * @param themeName
	 * 主题名称
	 */
	changeTheme = function(themeName) {
		var $easyuiTheme = $('#easyuiTheme');
		var url = $easyuiTheme.attr('href');
		var href = url.substring(0, url.indexOf('themes')) + 'themes/'
				+ themeName + '/easyui.css';
		$easyuiTheme.attr('href', href);
		var $iframe = $('iframe');
		if ($iframe.length > 0) {
			for ( var i = 0; i < $iframe.length; i++) {
				var ifr = $iframe[i];
				$(ifr).contents().find('#easyuiTheme').attr('href', href);
			}
		}
	};
	// 退出登录
	function logoutFun() {
		// 询问 用户是否确认退出
		$.messager.confirm("确认窗口","你确定退出系统吗？", function(isConfirm){
			if(isConfirm){
				// 确认退出 
				location.href = "${pageContext.request.contextPath}/invalidate.jsp";
			}
		});
	}
	// 修改密码
	function editPassword() {
		$('#editPwdWindow').window('open');
	}
	// 版权信息
	function showAbout(){
		$.messager.alert("校园快递","设计: Mr_Jc<br/> 管理员邮箱:845886914@qq.cn <br/> QQ: 845886914");
	}
	// 版权信息
	function emailAbout(){
		$('#emailWindow').window('open');
	}
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false"
		style="height:80px;padding:5px;background:url('./images/header_bg.png') no-repeat right;">
		<div>
			<img src="${pageContext.request.contextPath }/images/logo.jpg"
				border="0">
		</div>
		<div id="sessionInfoDiv"
			style="position: absolute;right: 5px;top:10px;">
			[<strong>${user.username }</strong>]，欢迎你！您使用[<strong>${pageContext.request.remoteAddr }</strong>]IP登录！
		</div>
		<div style="position: absolute; right: 5px; bottom: 10px; ">
			<a href="javascript:void(0);" class="easyui-menubutton"
				data-options="menu:'#layout_north_pfMenu',iconCls:'icon-ok'">更换皮肤</a>
			<a href="javascript:void(0);" class="easyui-menubutton"
				data-options="menu:'#layout_north_kzmbMenu',iconCls:'icon-help'">控制面板</a>
		</div>
		<div id="layout_north_pfMenu" style="width: 120px; display: none;">
			<div onclick="changeTheme('material');">material</div>
			<div onclick="changeTheme('insdep');">insdep</div>
			<div onclick="changeTheme('default');">default</div>
			<div onclick="changeTheme('gray');">gray</div>
			<div onclick="changeTheme('black');">black</div>
			<div onclick="changeTheme('bootstrap');">bootstrap</div>
			<div onclick="changeTheme('metro');">metro</div>
			<div onclick="changeTheme('metro-blue');">metro-blue</div>
			<div onclick="changeTheme('metro-gray');">metro-gray</div>
			<div onclick="changeTheme('metro-green');">metro-green</div>
			<div onclick="changeTheme('metro-orange');">metro-orange</div>
			<div onclick="changeTheme('metro-red');">metro-red</div>
			<div onclick="changeTheme('ui-cupertino');">ui-cupertino</div>
			<div onclick="changeTheme('ui-dark-hive');">ui-dark-hive</div>
			<div onclick="changeTheme('ui-pepper-grinder');">ui-pepper-grinder</div>
		</div>
		<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
			<div onclick="editPassword();">修改密码</div>
			<div onclick="showAbout();">联系管理员</div>
			<div onclick="emailAbout();">发送邮件</div>
			<div class="menu-sep"></div>
			<div onclick="logoutFun();">退出系统</div>
		</div>
	</div>
	<div data-options="region:'west',split:true,title:'菜单导航'"
		style="width:200px">
		<div class="easyui-accordion" fit="true" border="false">
			<div title="基本功能" data-options="iconCls:'icon-mini-add'" style="overflow:auto">
				<ul id="treeMenu" class="ztree"></ul>
			</div>
			<div title="系统管理" data-options="iconCls:'icon-mini-add'" style="overflow:auto">  
				<ul id="adminMenu" class="ztree"></ul>
			</div>
		</div>
	</div>
	<div data-options="region:'center'">
		<div id="tabs" fit="true" class="easyui-tabs" border="false">
			<div title="消息中心" id="subWarp"
				style="width:100%;height:100%;overflow:hidden">
				<iframe src="page_common_home.action"
					style="width:100%;height:100%;border:0;"></iframe>
				<%--				这里显示公告栏、预警信息和代办事宜--%>
			</div>
		</div>
	</div>
	<div data-options="region:'south',border:false"
		style="height:50px;padding:10px;background:url('./images/header_foot.png') no-repeat right;">
		<table style="width: 100%;">
			<tbody>
				<tr>
					<td style="width: 300px;">
						<div style="color: #999; font-size: 8pt;">
							青岛滨海学院 | Powered by <a href="www.qdbhu.edu.cn"></a>
						</div>
					</td>
					<td style="width: *;" class="co1"><span id="online"
						style="background: url(${pageContext.request.contextPath }/images/online.png) no-repeat left;padding-left:18px;margin-left:3px;font-size:8pt;color:#005590;">在线人数:1</span>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<!--修改密码窗口-->
    <div id="editPwdWindow" class="easyui-window" title="修改密码" collapsible="false" minimizable="false" modal="true" closed="true" resizable="false"
        maximizable="false" icon="icon-save"  style="width: 300px; height: 160px; padding: 5px;
        background: #fafafa">
        <div class="easyui-layout" fit="true">
            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
                <table cellpadding=3>
                    <tr>
                        <td>新密码：</td>
                        <td><input id="txtNewPass" type="Password" class="txt01" /></td>
                    </tr>
                    <tr>
                        <td>确认密码：</td>
                        <td><input id="txtRePass" type="Password" class="txt01" /></td>
                    </tr>
                </table>
            </div>
            <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
                <a id="btnEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" >确定</a> 
                <a id="btnCancel" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)">取消</a>
            </div>
        </div>
    </div>
    
    
    <!--修改密码窗口-->
    <div id="emailWindow" class="easyui-window" title="发送邮件" collapsible="false" minimizable="false" modal="true" closed="true" resizable="false"
        maximizable="false" icon="icon-save"  style="width: 500px; height: 500px; padding: 5px;
        background: #fafafa">
        <div class="easyui-layout" fit="true">
            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
                <table cellpadding=3>
                    <tr>
                        <td>收件人:</td>
                        <td><input id="txtemail" type="text" name="email" class="txt01" /></td>
                    </tr>
                    <tr>
                        <td>内容:</td>
                        <td><textarea id="context" name="context" class="txt01" rows="30" cols="50"></textarea></td>
                    </tr>
                </table>
            </div>
            <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
                <a id="btnemailEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" >确定</a> 
                <a id="btnemailCancel" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)">取消</a>
            </div>
        </div>
    </div>
</body>
</html>