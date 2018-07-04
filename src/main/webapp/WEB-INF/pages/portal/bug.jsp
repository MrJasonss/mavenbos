<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/semantic.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/zyComment.css" type="text/css" />
<style type="text/css" media="print, screen">  
	label {
	    font-weight: bold;
	}
	
	a {
		font-family: Microsoft YaHei;
	}
	
	#articleComment {
		width: 600px;
		height: 1500px;
		overflow: auto;
	}
</style>
	
<div id="articleComment"></div>
	
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/zyComment.js"></script>
	
	<script type="text/javascript">
	
		var agoComment = [
		                  {"id":1,"userName":"游客1","time":"2014-04-04","sortID":0,"content":"第一条评论"},
		                  {"id":2,"userName":"游客2","time":"2014-04-04","sortID":0,"content":"第二条评论"},
		                  {"id":3,"userName":"站长","time":"2014-04-04","sortID":1,"content":"第一条评论的回复"},
		                  {"id":4,"userName":"站长","time":"2014-04-04","sortID":2,"content":"第二条评论的回复"},
		                  {"id":5,"userName":"游客3","time":"2014-04-04","sortID":0,"content":"第三条评论"},
		                  {"id":6,"userName":"游客2","time":"2014-04-04","sortID":4,"content":"第二条评论的回复的回复"},
		                  ];
		$("#articleComment").zyComment({
			"width":"355",
			"height":"33",
			"agoComment":agoComment,
			"callback":function(comment){
				console.info("填写内容返回值：");
				console.info(comment);

				// 添加新的评论
				$("#articleComment").zyComment("setCommentAfter",{"id":123, "name":"name", "content":"content", "time":"2014-04-14"});

			}
		});
		
		
	
	</script>	
<!--  
	
	
<div style="padding:10px;">
<div style="margin-bottom:8px;">您的意见会让系统做得更好<br /></div>
<form id="bug_form" method="post" enctype="application/x-www-form-urlencoded">
<textarea name="bug_c" id="bug_c" cols="50" rows="7" style=" border:solid 3px #E2E2E2;line-height:16px; padding:5px;"></textarea>
<br />
<div style="margin-top:8px;"><a href="javascript:void(0);" onclick="$('#bug_form').submit();" id="bug_form_but" class="easyui-linkbutton">提交</a></div>
</form>
</div>
<script>
//功能相对独立，应该单独放置
$('#bug_form').form({  
	url:"post.html",  
	onSubmit: function(){  
		if($('#bug_c').val()==""){
			$.messager.alert('Warning',"内容太少");	
			return false;
		}
	}, 
	success:function(data){  
		$.messager.alert('Warning',"提交成功"); 
		$('#bug_c').val("data");
	}  
});   
</script>

-->