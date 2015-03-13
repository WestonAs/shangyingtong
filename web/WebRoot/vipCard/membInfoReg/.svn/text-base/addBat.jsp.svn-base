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
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		<f:js src="/js/date/WdatePicker.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			var showCenter = $('#id_showCenter').val();
			var showCard = $('#id_showCard').val();
			var showFenZhi = $('#id_showFenZhi').val();
			var parent = $('#id_parent').val();
			
			//营运中心、营运中心部门
			if(showCenter=='true' || showFenZhi=='true'){
				// 营运中心、中心部门
				if(showCenter=='true'){
					Selector.selectBranch('branchName', 'id_cardIssuer_1', true, '20');
				}
				// 分支机构
				else if(showFenZhi=='true'){
					Selector.selectBranch('branchName', 'id_cardIssuer_1', true, '20', '', '', parent);
				}
			}
		});
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="addBat.do" id="inputForm" enctype="multipart/form-data" cssClass="validate">
					<s:hidden id="id_showCenter" name="showCenter"></s:hidden>
					<s:hidden id="id_showCard" name="showCard"></s:hidden>
					<s:hidden id="id_showFenZhi" name="showFenZhi"></s:hidden>
					<s:hidden id="id_parent" name="parent"></s:hidden>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="80" height="30" align="right">导入文件</td>
							<td>
								<s:file name="upload" cssClass="{required:true}" contenteditable="false" ></s:file>
								<span class="field_tipinfo">请选择</span>
							</td>
						</tr>
						<tr>	
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/vipCard/membInfoReg/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_FILEBAT_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>	
			<div class="contentb">
				<span class="note_div">注释</span>
				<ul class="showli_div">
					<li class="showli_div">导入文件支持txt格式 。下载 <b><a href="membInfoRegTmpl.txt">会员资料登记模板</a></b></li>
					<li class="showli_div">证件类型填写代码，对应关系如下：01 身份证 02护照 03 驾驶证 04 回乡证(港澳台) 05 军官证 06 户口本 07 企业组织机构代码  09 其他 。</li>
					<li class="showli_div">教育程度填写代码，对应关系如下：00 高中 01 大专 02 本科 03 研究生 04博士 05 其他 。</li>
					<li class="showli_div">性别填写代码，对应关系如下：0 男 1 女。</li>
					<li class="showli_div">导入格式，表头和数据都以“|”分隔，如下所示:
					<ul class="showCicleLi_div">
						<li class="showCicleLi_div">表头：会员类型|会员级别|会员名称|证件类型|证件号|地址|性别|生日|年龄|手机号|座机号|电子邮件|工作|薪水|教育程度|备注</li>
						<li class="showCicleLi_div">数据：A|1|TEST|01|4414124232234343|ADDRESS|0|20110101|11|123454343454|020-80909898|123@163.com|Teacher|1000|00|Test</li>
					</ul>
				</ul>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>