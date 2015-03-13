<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/common/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/meta.jsp" %>
		<%@ include file="/common/sys.jsp" %>
		<title>${ACT.name}</title>
		
		<f:css href="/css/page.css"/>
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="addBatFile.do" id="inputForm" enctype="multipart/form-data" cssClass="validate">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>文件方式批量新增卡补账</caption>
						<tr>
							<td width="80" height="30" align="right">补账文件</td>
							<td >
								<s:file name="upload" cssClass="{required:true}" contenteditable="false"></s:file>
								<span class="field_tipinfo">请选择文件</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">明细数量</td>
							<td>
								<s:textfield name="formMap.detailCnt" cssClass="{required:true, digits:true, min:1}" maxlength="5"/>
								<span class="field_tipinfo">请输入正整数（补账记录数）</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">总金额</td>
							<td>
								<s:textfield name="formMap.totalAmt" cssClass="{required:true, num:true}" maxlength="10"/>
								<span class="field_tipinfo">元</span>
								<span class="error_tipinfo">请输入总金额</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">备注</td>
							<td>
								<s:textfield name="formMap.remark" value="文件方式批量新增卡补账" maxlength="100"/>
								<span class="field_tipinfo"></span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm');" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/retransCardReg/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_RETRANS_CARD_ADDBATFILE"/>
					
					<span class="note_div">注释</span>
					<ul class="showli_div">
						<li class="showli_div">补账文件必须为<span class="redfont">文本格式文件。</span>即后缀名必须为：<span class="redfont">“csv”或“txt”</span>，下载 <b><a href="cardRetransTmpl.txt">卡补账模板</a></b></li>
						<li class="showli_div">文件第一行固定为标题行：<span class="redfont">卡号,金额,商户编号,终端号,不先用赠券子账户</span></li>
						<li class="showli_div">文件第二行开始为数据行，例如：2086666660188888888,0.50,208444488888888,88888888,0</li>
						<li class="showli_div">不先用赠券子账户：1 表示不先用赠券账户；0 表示先用赠券账户</li>
						<li class="showli_div">导入文件最大明细数为1000；字段值不能有空格；</li>
					</ul>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>