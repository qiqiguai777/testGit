$(function(){
	$("#publishBtn").click(publish);
});
function publish() {
	//获取标题和内容
	var title = $("#recipient-name").val();
	var content = $("#message-text").val();
	//发送异步请求
	$.ajax({
		url:CONTEXT_PATH + "/discuss/add",
		type:"POST",
		data:{"title":title,"content":content},
		dataType: "JSON",
		success: function (data) {
			// 在提示框中显示返回消息
			$("#hintBody").text(data.message);
			// 显示提示框
			$("#hintModal").modal("show");
			// 2秒后,自动隐藏提示框
			setTimeout(function(){
				$("#hintModal").modal("hide");
				// 刷新页面
				if(data.code == 200) {
					window.location.reload();
				}
			}, 2000);
		}
	})

}
