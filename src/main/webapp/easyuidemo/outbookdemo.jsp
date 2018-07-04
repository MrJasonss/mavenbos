<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>zTreeStyleDemo-outlookStyle</title>
    <!--<link rel="stylesheet" href="zTreeStyle/demo.css" type="text/css">-->
    <link rel="stylesheet" href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/js/ztree/outlookStyle.css" type="text/css">
    
</head>
<body>
<script type="text/javascript">
  
  var setting = {
          view: {
              showLine: false,
              showIcon: false,
              selectedMulti: false,
              dblClickExpand: false,
              addDiyDom: addDiyDom
          },
          data: {
              simpleData: {
                  enable: true
              }
          },
          callback: {
              beforeClick: beforeClick
          }
      };

      var zNodes =[
          { id:1, pId:0, name:"Folders", open:true},
          { id:11, pId:1, name:"Inbox"},
          { id:111, pId:11, name:"Inbox1"},
          { id:112, pId:111, name:"Inbox2"},
          { id:113, pId:112, name:"Inbox3"},
          { id:114, pId:113, name:"Inbox4"},
          { id:12, pId:1, name:"Junk"},
          { id:13, pId:1, name:"Drafts"},
          { id:14, pId:1, name:"Sent"},
          { id:15, pId:1, name:"Deleted"},
          { id:3, pId:0, name:"Quick views"},
          { id:31, pId:3, name:"Documents"},
          { id:32, pId:3, name:"Photos"}
      ];

      function addDiyDom(treeId, treeNode) {
          var spaceWidth = 5;
          var switchObj = $("#" + treeNode.tId + "_switch"),
          icoObj = $("#" + treeNode.tId + "_ico");
          switchObj.remove();
          icoObj.before(switchObj);

          if (treeNode.level > 1) {
              var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level)+ "px'></span>";
              switchObj.before(spaceStr);
          }
      }

      function beforeClick(treeId, treeNode) {
          if (treeNode.level == 0 ) {
              var zTree = $.fn.zTree.getZTreeObj("treeDemo");
              zTree.expandNode(treeNode);
              return false;
          }
          return true;
      }
      
   
  </script>
    <div class="zTreeDemoBackground left">
        <ul id="treeDemo" class="ztree"></ul>
    </div>
  <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
  <script src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.core.js"></script> 
  <script type="text/javascript">
  
  $(document).ready(function(){
      var treeObj = $("#treeDemo");
      $.fn.zTree.init(treeObj, setting, zNodes);
      
      //这三句根据需要写，我工作中不需要，所以删掉了
      // zTree_Menu = $.fn.zTree.getZTreeObj("treeDemo");
      // curMenu = zTree_Menu.getNodes()[0].children[0].children[0];
      // zTree_Menu.selectNode(curMenu);

      treeObj.hover(function () {
      if (!treeObj.hasClass("showIcon")) {
              treeObj.addClass("showIcon");
          }
       }, function() {
          treeObj.removeClass("showIcon");
       });
  });
  </script>
</html>