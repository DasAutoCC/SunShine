var isLogin;
var editor;
  $(function() {
      editor = editormd("test-editor", {
			width  : "100%",
			height :520,
			saveHTMLToTextarea : true, 
			path   : "./editor/lib/",
			imageUpload : true,
			emoji : true,
			imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
			imageUploadURL : "/upload-image"
     });
	 $.get("/isLogin",{},function(data){
	 	if(data.respCode==200){
	 		isLogin = data.data;
	 	}else{
			alert("您未认证，您将无法提交博客")
		}
	 });
  })
  //{"articleHeader":"标题","articleContent":"内容",
  // "isPublic":"是否公开","preview":"预览文字"}
  function sendMarkdownToServer(instence){
  	$.post("/blog/addArticle",instence,function(data){
  		if(data.respCode== 200){
  			alert("提交成功");
  		}else{
  			alert("提交失败:"+data.message);
  		}
  	})
  }

 $("#submit").click(function(){
	 if(isLogin == null){
		 alert("您未认证，无法提交!")
		 return;
	 }
	 
	 var instenceff = {
	 		 "articleHeader" : $("#blogHeader").val(),
	 		 "articleContent" :editor.getMarkdown(),
	 		 "isPublic" : $("select").val(),
	 		 "preview" : $("#preview").val()
	 }
	 if(instenceff.articleHeader == "" ||instenceff.articleContent=="" || instenceff.preview==""){
		 alert("内容不完整，请填写完整")
		 return;
	 }
	 var connn = confirm("确认提交么？");
	 if(!connn){
		 return;
	 }
	 sendMarkdownToServer(instenceff);
		// alert(  instenceff.articleHeader+"\n"+
		// 		instenceff.articleContent+"\n"+
		// 		instenceff.isPublic+"\n"+
		// 		instenceff.preview
		// )
 })
