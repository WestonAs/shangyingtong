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

		<f:css href="/css/jquery.autocomplete.css"/>
		<f:js src="/js/jquery.autocomplete.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			//Selector.selectBranch('idBranchCode_sel', 'idBranchCode', true, '00,01,11,20,21');
			if('${loginRoleType}' == '00'){
				Selector.selectBranch('idManageBranch_sel', 'idManageBranch', true, '00,01');
			} else if('${loginRoleType}' == '01'){
				Selector.selectBranch('idManageBranch_sel', 'idManageBranch', true, '01', '', '', '${loginBranchCode}');
			}
		});
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="addMerchFile.do" id="inputForm" enctype="multipart/form-data" cssClass="validate">
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>文件方式添加商户</caption>
						
						<s:if test="centerOrCenterDeptRoleLogined">
							<tr>
								<td width="80" height="30" align="right">商户编号规则  </td>
								<td height="30">
									<label><input type="checkbox" id="isMerch898" name="merchInfoReg.merch898" value="true" /> 898商户</label>
									<span class="field_tipinfo">商户编号前缀及生成规则</span>
								</td>
							</tr>
						</s:if>
						<tr>
							<s:hidden name="merchInfoReg.cardBranch" />
							<td width="80" height="30" align="right">管理机构</td>
							<s:if test="showModifyManage">
							<td>
								<s:hidden name="merchInfoReg.manageBranch" />
								<s:textfield name="manageBranch" cssClass="{required:true} readonly" readonly="true"/>
							</td>
							</s:if>
							<s:else>
							<td>
								<s:hidden id="idManageBranch" name="merchInfoReg.manageBranch" />
								<s:textfield id="idManageBranch_sel" name="manageBranch" cssClass="{required:true}"/>
								<span class="field_tipinfo">请选择管理机构</span>
							</td>
							</s:else>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">开通标志</td>
							<td>
								<s:select name="merchInfoReg.openFlag" list="openFlagList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择开通标志</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">货币代码</td>
							<td>
								<s:select name="merchInfoReg.currCode" list="currCodeList" listKey="currCode" listValue="currName" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择货币代码</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">商户文件</td>
							<td >
								<s:file name="upload" cssClass="{required:true}" contenteditable="false"></s:file>
								<span class="field_tipinfo">请选择文件</span>
							</td>
						</tr>
						
						<tr>	
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2" />
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm');" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/merch/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_MERCH_FILE_ADD"/>
					</div>
					
					<span class="note_div">注释</span>
					<ul class="showli_div">
						<li class="showli_div">商户文件必须为<span class="redfont">文本格式文件。</span>即后缀名必须为：<span class="redfont">“csv”或“txt”</span>，下载 <b><f:link href="/pages/merch/addMerchTmpl.txt">新增商户模板</f:link></b></li>
						<li class="showli_div">文件第一行标题行：<span class="redfont">商户名称,商户简称,商户类型代码,地区码,联系人,联系电话,联系地址,账户类型代码,开户行号,账户户名,账号,使用密码标志,清算资金是否轧差,管理员用户名,风险等级,营业执照,营业执照有效期,组织机构号,组织机构代码有效期,税务登记号,法人姓名,法人身份证号码,法人身份证有效期,单位经营范围,备注</span></li>
						<li class="showli_div">文件第二行开始为数据行。</li>
						<li class="showli_div">注意事项：
							<ul class="showCicleLi_div">
								<li class="showCicleLi_div">由于数据行的各字段是以英文半角逗号（,）分隔，所以<span class="redfont">字段内容不要包含英文半角逗号</span>；</li>
								<li class="showCicleLi_div"><span class="redfont">“商户类型代码”</span>请在<span class="redfont">“商户类型”</span>菜单处查询取得；</li>
								<li class="showCicleLi_div"><span class="redfont">“开户行号”</span>从<span class="redfont">“基础数据管理”->“银行代码”</span>处查询取得；</li>
								<li class="showCicleLi_div"><span class="redfont">“地区码”</span>从<span class="redfont">“基础数据管理”->“地区代码”</span>处查询。请确保数据的正确；</li>
								<li class="showCicleLi_div"><span class="redfont">“账户类型”</span>字段值为：<span class="redfont">0-私人账户；1-公司账户</span>；</li>
								<li class="showCicleLi_div"><span class="redfont">“使用密码标志”</span>字段值为：<span class="redfont">0-不使用密码，1-使用密码；2-根据卡的设置</span>；</li>
								<li class="showCicleLi_div"><span class="redfont">“清算资金是否轧差”</span>字段值为：<span class="redfont">0-否，1-是</span>；</li>
								<li class="showCicleLi_div">请确保<span class="redfont">“管理员用户名”</span>在系统中<span class="redfont">没有重复的</span>；</li>
								<li class="showCicleLi_div"><span class="redfont">“风险等级”</span>字段值为：<span class="redfont">0-低，1-中，2-高</span>；</li>
								<li class="showCicleLi_div"><span class="redfont">“营业执照有效期”、“组织机构代码有效期”、“法人身份证有效期”</span>的格式为yyyy-MM-dd，如2099-12-31；</li>
							</ul>
						</li>
					</ul>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>