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
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/date/WdatePicker.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
        function check(){
        	if(confirm("将进行证书更新，是否继续？")){
        		$("#loadingBarDiv").css("display","inline");
				$("#contentDiv").css("display","none");
				return true;
            }
            return false;
        }
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		<div id="loadingBarDiv"	style="display: none; width: 100%; height: 100%">
			<table width=100% height=100%>
				<tr>
					<td align=center >
						<center>
							<img src="${CONTEXT_PATH}/images/ajax_saving.gif" align="middle">
							<div id="processInfoDiv"
								style="font-size: 15px; font-weight: bold">
								正在处理中，请稍候...
							</div>
							<br>
							<br>
							<br>
						</center>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="contentDiv" class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="update.do" id="inputForm" cssClass="validate" method="post" enctype="multipart/form-data">
					<div>
					<s:hidden name="userCertificate.seqNo" />
					<s:hidden name="userCertificate.dnNo" />
					<s:hidden name="userCertificate.startDate"/>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80" height="30" align="right">所属机构</td>
							<td>
							    <s:hidden name="userCertificate.branchCode"  cssClass="{required:true} readonly"  readonly="true"/>
								<s:textfield name="userCertificate.branchName"  cssClass="{required:true} readonly"  readonly="true"/>
								<span class="field_tipinfo">请输入所属机构</span>
							</td>
						<tr>
						</tr>
							<td width="80" height="30" align="right">绑定机构</td>
							<td>
							    <s:hidden name="userCertificate.bndBranch"  cssClass="{required:true} readonly"  readonly="true"/>
								<s:textfield name="userCertificate.bndBranchName"  cssClass="{required:true} readonly"  readonly="true"/>
								<span class="field_tipinfo">请输入绑定机构</span>
							</td>
						</tr>
						</tr>
							<td width="80" height="30" align="right">绑定用户</td>
							<td>
								<s:textfield name="userCertificate.userId"  cssClass="{required:true} readonly"  readonly="true"/>
								<span class="field_tipinfo">请输入绑定用户</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">证书文件</td>
							<td>
								<s:file name="upload" cssClass="{required:true}" contenteditable="false" ></s:file>
								<span class="field_tipinfo">请选择上传文件(单个证书)</span>
							</td>
						</tr>
<!-- 						<tr> -->
<!-- 							<td width="80" height="30" align="right">用户编号</td> -->
<!-- 							<td> -->
<!-- 								<s:textfield name="userCertificate.userId" maxlength="15" cssClass="{required:true, digitOrLetter:true}"/> -->
<!-- 								<span class="field_tipinfo">请输入用户编号</span> -->
<!-- 							</td> -->
<!-- 							<td width="80" height="30" align="right">证书DN</td> -->
<!-- 							<td> -->
<!-- 								<s:textfield name="userCertificate.dnNo" maxlength="50" cssClass="{required:true, digitOrLetter:true}"/> -->
<!-- 								<span class="field_tipinfo">请输入证书DN</span> -->
<!-- 							</td> -->
<!-- 						</tr> -->
<!-- 						<tr> -->
<!-- 							<td width="80" height="30" align="right">证书序列号</td> -->
<!-- 							<td> -->
<!-- 								<s:textfield name="userCertificate.seqNo" maxlength="32" cssClass="{required:true}"/> -->
<!-- 								<span class="field_tipinfo">请输入证书序列号</span> -->
<!-- 							</td> -->
<!-- 							<td width="80" height="30" align="right">证书启用日期</td> -->
<!-- 							<td> -->
<!-- 								<s:textfield name="userCertificate.startDate" maxlength="8" cssClass="{required:true}" onclick="WdatePicker()"/> -->
<!-- 								<span class="field_tipinfo">请选择证书启用日期</span> -->
<!-- 							</td> -->
<!-- 						</tr> -->
						
						<tr>	
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="更新" id="input_btn2"  name="ok" onclick="return check();"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/userCert/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>