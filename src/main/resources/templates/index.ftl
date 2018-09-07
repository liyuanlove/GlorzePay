<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title}</title>
<meta name="keywords" content="支付宝微信在线支付,扫码支付,支付宝扫码付款,微信扫码支付,赞赏源码">
<link rel="stylesheet" href="/layui/css/layui.css">
<meta name="description" content="Java实现的支付宝微信在线支付系统,支持支付宝或者微信扫码付款,赞赏。高老四博客。">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black"> 
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">
</head>
<body>

<div class="layui-container">  
	<#include "/common/head.ftl">
	<div class="layui-row">
		<div class="layui-col-md12">
			<fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
				<legend>在线支付</legend>
			</fieldset>
			
			<form class="layui-form" method="post">
				<div class="layui-form-item">
					<label class="layui-form-label">支付金额:</label>
					<div class="layui-input-block">
						<input type="radio" name="goodsNo" value="1" title="08元-请四哥喝杯饮料">
			    	</div>
			    	<div class="layui-input-block">
			      		<input type="radio" name="goodsNo" value="2" title="18元-给四哥买盒烟" checked="">
			    	</div>
			    	<div class="layui-input-block">
			      		<input type="radio" name="goodsNo" value="3" title="28元-给四哥买盒杜蕾斯">
			    	</div>
				</div>
				
				<div class="layui-form-item">
					<label class="layui-form-label">支付人:</label>
			    	<div class="layui-input-block">
			      		<input type="text" name="nickName" lay-verify="required" placeholder="请输入您的姓名或昵称" autocomplete="off" class="layui-input">
			    	</div>
				</div>
				
				<div class="layui-form-item">
					<label class="layui-form-label">QQ:</label>
			    	<div class="layui-input-block">
			      		<input type="text" name="qq" lay-verify="required|number" placeholder="请输入您的QQ号码" autocomplete="off" class="layui-input">
			    	</div>
				</div>
				
				<div class="layui-form-item layui-form-text">
					<label class="layui-form-label">留言:</label>
			    	<div class="layui-input-block">
			      		<textarea name="message" placeholder="你的语言可以一语中的,直击人心!" class="layui-textarea"></textarea>
			    	</div>
				</div>
				
				<div class="layui-form-item">
					<label class="layui-form-label">支付方式:</label>
			    	<div class="layui-input-block">
			    		<input type="radio" name="way" value="微信" title="微信" checked="">
			      		<input type="radio" name="way" value="支付宝" title="支付宝">
			    	</div>
				</div>
				
				<div class="layui-form-item">
					<div class="layui-input-block">
			      		<button class="layui-btn" lay-submit="" lay-filter="paySubmit">走你</button>
			    	</div>
				</div>
			</form>
		</div>
	</div>
</div>

<script src="/layui/layui.js"></script> 

<script>
	layui.use('element', function(){
		var element = layui.element;
	});
</script>

<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
<script>
	layui.use(['form'], function(){
		var form = layui.form;
		//监听提交
		form.on('submit(paySubmit)', function(data){
			if(data.field.way=='支付宝'){
		    	data.form.action="/aliPay/pay";
		    }else if(data.field.way=='微信'){
		    	data.form.action="/weixinPay/pay";
		    }
		    return true;
		});
	});
</script>
</body>