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
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		
		<f:js src="/js/date/WdatePicker.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			$('#id_signingTime').val($('#id_signingTime_hidden').val());
			//Selector.selectBranch('idBranchCode_sel', 'idBranchCode', true, '00,01,11,20,21');
			if('${loginRoleType}' == '00' || '${loginRoleType}' == '02'){ // 运营中心或部门登录
				Selector.selectBranch('idManageBranch_sel', 'idManageBranch', true, '00,01');
				Selector.selectBranch('idBranchName', 'idBranchCode', true, '20');
			} else if('${loginRoleType}' == '01'){
				<%--
				Selector.selectBranch('idManageBranch_sel', 'idManageBranch', true, '01', '', '', '${loginBranchCode}');
				--%>
				Selector.selectBranch('idBranchName', 'idBranchCode', true, '20', '', '', '${loginBranchCode}');
			}
			//Selector.selectMerch('idParent_sel', 'idParent', true);
			Selector.selectArea('idArea_sel', 'idArea', true);
			Selector.selectBank('idBank_sel', 'idBank', true, function(){
					loadAreaCode($('#idBank').val());
				});
			
			$(':radio[name="addAdmin"]').click(function(){
				var value = $(this).val();
				if (value == 2) {
					$('td[id^="idAddAdmin_"]').hide();
					$('#merchAdmin').attr('disabled', 'true');
				} else {
					$('td[id^="idAddAdmin_"]').show();
					$('#merchAdmin').removeAttr('disabled');
				}
			});
			
			// 输入完商户简称，自动生成管理员用户
			$('#id_merchAbb').blur(function(){
				var value = $(this).val();
				if(!isEmpty(value)){
					$.post(CONTEXT_PATH + '/pages/branch/getAdminUserId.do', {'branchAbbname':value, 'callId':callId()}, function(data){
						$('#id_merchAdmin').val(data.userId);
					}, 'json');
				}
			});
			
			// 检查用户名是否已经存在
			$('#id_merchAdmin').blur(function(){
				$('#idmerchAdmin_field').html('用户名为数字字符');
				checkUserId();
			});
		});
		// 根据银行的行号来取得地区码，作为开户行地区码
		function loadAreaCode(bankNo){
			$.post(CONTEXT_PATH + '/pages/branch/loadAccAreaCode.do', {'bankNo':bankNo, 'callId':callId()}, 
				function(data){
					$('#idAccAreaCode_sel').val(data.accAreaName);
					$('#idAccAreaCode').val(data.accAreaCode);
				}, 'json');
		}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="modify.do" id="inputForm" cssClass="validate">
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<!-- 以后再改 
						<s:hidden name="merchInfo.cardBranch" />
						-->
						<tr>
							<td width="80" height="30" align="right">商户编号</td>
							<td>
								<s:textfield name="merchInfo.merchId" cssClass="{required:true} readonly" readonly="true"/>
							</td>
							
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">商户名称</td>
							<td>
								<s:textfield name="merchInfo.merchName" cssClass="{required:true}" maxlength="100" onblur="$('#idAccName').val(this.value)"/>
								<span class="field_tipinfo">请输入商户名称</span>
							</td>
							<td width="80" height="30" align="right">管理机构</td>
							<td>
								<s:hidden id="idManageBranch" name="merchInfo.manageBranch" />
								<s:if test="centerOrCenterDeptRoleLogined"> <%--运营中心或部门可以修改管理机构 --%>
									<s:textfield id="idManageBranch_sel" name="manageBranch" cssClass="{required:true}"/>
								</s:if>
								<s:else>
									<s:textfield id="idManageBranch_sel" name="manageBranch" cssClass="{required:true}  readonly" readonly="true" />
								</s:else>
								<span class="field_tipinfo">请选择管理机构</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">开通标志</td>
							<td>
								<s:select name="merchInfo.openFlag" list="openFlagList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择开通标志</span>
							</td>
							<td width="80" height="30" align="right">商户类型代码</td>
							<td>
								<s:hidden name="merchInfo.merchType" />
								<s:textfield name="merchTypeName" cssClass="{required:true} readonly" readonly="true"/>
								<span class="field_tipinfo">商户类型不为空</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">地区</td>
							<td>
								<s:hidden id="idArea" name="merchInfo.areaCode" />
								<s:textfield id="idArea_sel" name="areaName" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入地区</span>
							</td>
							<td width="80" height="30" align="right">货币代码</td>
							<td>
								<s:select name="merchInfo.currCode" list="currCodeList" headerKey="" headerValue="--请选择--" listKey="currCode" listValue="currName" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择货币代码</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">商户简称</td>
							<td>
								<s:textfield id="id_merchAbb" name="merchInfo.merchAbb" maxlength="15" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入商户简称</span>
							</td>
							
							<td width="80" height="30" align="right">英文名称</td>
							<td>
								<s:textfield name="merchInfo.merchEn" maxlength="200"/>
								<span class="field_tipinfo">请输入英文名称</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">联系人</td>
							<td>
								<s:textfield name="merchInfo.linkMan" maxlength="30" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入联系人</span>
							</td>
							<td width="80" height="30" align="right">联系电话</td>
							<td>
								<s:textfield name="merchInfo.telNo" cssClass="{required:true, digitOrAllowChars:'-'}" maxlength="30"/>
								<span class="field_tipinfo">请输入联系电话</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">联系地址</td>
							<td>
								<s:textfield name="merchInfo.merchAddress" maxlength="120" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入联系地址</span>
							</td>
							<td width="80" height="30" align="right">传真</td>
							<td>
								<s:textfield name="merchInfo.faxNo" cssClass="{digitOrAllowChars:'-'}" maxlength="30"/>
								<span class="field_tipinfo">请输入传真</span>
							</td>
						</tr>
						<tr>
							
							<td width="80" height="30" align="right">E-mail</td>
							<td>
								<s:textfield name="merchInfo.email" cssClass="{email:true}" maxlength="60"/>
								<span class="field_tipinfo">请输入邮件地址</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">是否单机产品机构</td>
							<td>
								<s:select name="merchInfo.singleProduct" list="yesOrNoFlagList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" 
									cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择</span>
							</td>

							<td width="80" height="30" align="right">客户经理</td>
							<td>
								<s:textfield name="merchInfo.unPay" maxlength="30"/>
								<span class="field_tipinfo">请输入客户经理</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">账户介质类型</td>
							<td>
								<s:select id="id_acctMediaType" name="merchInfo.acctMediaType" list="acctMediaTypeList" headerKey="" headerValue="--请选择--" 
									listKey="value" listValue="name" cssClass="{required:true}" />
								<span class="field_tipinfo">请选择</span>
							</td>

							<td width="80" height="30" align="right">直属发卡机构</td>
							<td>
								<s:hidden id="idBranchCode" name="merchInfo.cardBranch"/>
								<s:textfield id="idBranchName" name="cardBranchName" />
								<span class="field_tipinfo">点击选择机构</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">开户行名称</td>
							<td>
								<s:textfield id="idBank_sel" name="merchInfo.bankName" cssClass="{required:true}"/>
								<span class="field_tipinfo">点击选择</span>
							</td>
							
							<td width="80" height="30" align="right">账户类型</td>
							<td>
								<s:select id="id_acctType" name="merchInfo.acctType" list="acctTypeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择账户类型</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">账户地区码</td>
							<td>
								<s:hidden id="idAccAreaCode" name="merchInfo.accAreaCode" />
								<s:textfield id="idAccAreaCode_sel" name="accAreaName" cssClass="{required:true} readonly" readonly="true"/>
								<span class="field_tipinfo">请点击开户行名</span>
							</td>
						
							<td width="80" height="30" align="right">开户行号</td>
							<td>
								<s:textfield id="idBank" name="merchInfo.bankNo" cssClass="{required:true, digit:true, minlength:12} readonly" maxlength="12" readonly="true"/>
								<span class="field_tipinfo">请点击开户行名</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">账户户名</td>
							<td>
								<s:textfield id="idAccName" name="merchInfo.accName" maxlength="30" cssClass="{required:true}" />
								<span class="field_tipinfo">请输入户名</span>
							</td>
							
							<td width="80" height="30" align="right">账号</td>
							<td>
								<s:textfield name="merchInfo.accNo" cssClass="{required:true, digitOrAllowChars:'-'}" maxlength="32"/>
								<span class="field_tipinfo">请输入账号</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">是否使用密码标志</td>
							<td>
								<s:select name="merchInfo.usePwdFlag" list="usePwdFlagList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择密码标志</span>
							</td>

							<td width="80" height="30" align="right">清算资金是否轧差</td>
							<td>
								<s:select name="merchInfo.isNetting" list="isNettingList" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">是否轧差？</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">签约时间</td>
							<td>
								<s:textfield id="id_signingTime" name="merchInfo.signingTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
								<span class="field_tipinfo">请选择签约时间</span>
								<input id="id_signingTime_hidden" type="hidden" value="<s:date name="merchInfo.signingTime" format="yyyy-MM-dd HH:mm:ss"/>"/>
							</td>
							<td width="80" height="30" align="right">风险级别</td>
							<td>
								<s:select name="merchInfo.riskLevel" list="riskLevelTypeList"  
									headerKey="" headerValue="--请选择--" listKey="value" listValue="name" />
								<span class="field_tipinfo">选择风险级别</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">营业执照</td>
							<td>
								<s:textfield name="merchInfo.merchCode" maxlength="60" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入营业执照</span>
							</td>
							<td width="80" height="30" align="right" >营业执照有效期</td>
							<td>
								<input type="text" name="merchInfo.licenseExpDate" 
									value="<s:date name="merchInfo.licenseExpDate" format="yyyy-MM-dd" />"
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',autoPickDate:true,isShowOthers:false,readOnly:true})"/>
								<span class="field_tipinfo">请选择日期</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">组织机构号</td>
							<td>
								<s:textfield name="merchInfo.organization" maxlength="32" cssClass="{digitOrLetter:true}"/>
								<span class="field_tipinfo">输入组织机构号</span>
							</td>
							<td width="80" height="30" align="right" >组织机构代码有效期</td>
							<td>
								<s:textfield name="merchInfo.organizationExpireDate" 
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',autoPickDate:true,isShowOthers:false})"/>
								<span class="field_tipinfo">请选择日期yyyy-MM-dd</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right" >税务登记号</td>
							<td>
								<s:textfield name="merchInfo.taxRegCode" size="32"/>
							</td>
							<td width="80" height="30" align="right">法人姓名</td>
							<td>
								<s:textfield name="merchInfo.legalPersonName" maxlength="64"/>
								<span class="field_tipinfo"></span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">法人身份证号码</td>
							<td>
								<s:textfield name="merchInfo.legalPersonIdcard" />
								<span class="field_tipinfo"></span>
							</td>
							<td width="80" height="30" align="right" >法人身份证有效期</td>
							<td>
								<input type="text" name="merchInfo.legalPersonIdcardExpDate" 
									value="<s:date name="merchInfo.legalPersonIdcardExpDate" format="yyyy-MM-dd" />"
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',autoPickDate:true,isShowOthers:false,readOnly:true})"/>
								<span class="field_tipinfo">请选择日期</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">单位经营范围</td>
							<td>
								<s:textfield name="merchInfo.companyBusinessScope" cssClass="{maxlength:100}"/>
								<span class="field_tipinfo"></span>
							</td>
							<td width="80" height="30" align="right">易航宝虚拟帐号</td>
							<td>
								<s:textfield name="merchInfo.yhbVirtualAcct" />
								<span class="field_tipinfo"></span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">备注</td>
							<td>
								<s:textfield name="merchInfo.remark" />
								<span class="field_tipinfo"></span>
							</td>
							<s:if test='"20".equals(merchInfo.status)'>
								<td width="80" height="30" align="right" >管理员用户名</td>
								<td>
									<s:textfield id="id_merchAdmin" name="merchInfo.adminId" cssClass="{required:true, digitOrLetter:true}" />
									<span id="idmerchAdmin_field" class="field_tipinfo">用户名为数字字符</span>
								</td>
							</s:if>
							<s:else>
								<td width="80" height="30" align="right"></td>
								<td>
									<s:hidden name="merchInfo.adminId" />
								</td>
							</s:else>
						</tr>
						
						<tr>

						</tr>

						
						<tr>	
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="submit" value="提交" id="input_btn2"  name="ok"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm')" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/merch/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_MERCH_MODIFY"/>
					</div>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>