<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
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
			$('td[id^="idAddAdmin_"]').hide();
			$('#branchAdmin').attr('disabled', 'true');
					
			$(':radio[name="addAdmin"]').click(function(){
				var value = $(this).val();
				if (value == 2) {
					$('td[id^="idAddAdmin_"]').hide();
					$('#branchAdmin').attr('disabled', 'true');
				} else {
					$('td[id^="idAddAdmin_"]').show();
					$('#branchAdmin').removeAttr('disabled');
				}
			});
			
			// 输入完机构简称，自动生成管理员用户
			$('#id_branchAbbname').blur(function(){
				var value = $(this).val();
				if(!isEmpty(value)){
					$.post(CONTEXT_PATH + '/pages/branch/getAdminUserId.do', {'branchAbbname':value, 'callId':callId()}, function(data){
						$('#id_branchAdmin').val(data.userId);
					}, 'json');
				}
			});
			
			var loginRoleType = '${loginRoleType}';
			var loginBranchCode = '${loginBranchCode}';
			if (loginRoleType == '00' || loginRoleType == '02') { // 运营中心或部门登录
					Selector.selectBranch('id_manage_sel', 'id_manage', true, '00,01', loadDevelopList);
			} else {
				<%-- 运营分支不能修改管理方 
				Selector.selectBranch('id_manage_sel', 'id_manage', true, '01', loadDevelopList, '', loginBranchCode);
				--%>
			}
			
			// 检查用户名是否已经存在
			$('#id_branchAdmin').blur(function(){
				$('#idbranchAdmin_field').html('输入数字字符');
				checkUserId();
			});
			
			Selector.selectArea('idArea_sel', 'idArea', true);
			//Selector.selectArea('idAccAreaCode_sel', 'idAccAreaCode', true);
			//Selector.selectArea('idAccAreaCode_sel', 'idAccAreaCode', true);
			Selector.selectBank('idBank_sel', 'idBank', true, function(){
				var bankNo = $('#idBank').val();
				$.post(CONTEXT_PATH + '/pages/branch/loadAccAreaCode.do', {'bankNo':bankNo, 'callId':callId()}, 
					function(data){
						$('#idAccAreaCode_sel').val(data.accAreaName);
						$('#idAccAreaCode').val(data.accAreaCode);
					}, 'json');
			});
		});
		
		// 根据管理机构得到发展机构列表
		function loadDevelopList(){
			var value = $('#id_manage').val();
			$('#id_card_develop').load(CONTEXT_PATH + '/pages/branch/getDevelopList.do', {'manageBranch':value, 'callId':callId()});
		}
		
		function checkUserId() {
			var flag = false;
			var value = $('#id_branchAdmin').val();
			if (isEmpty(value)){
				return;
			}
			$.ajax({
				url: CONTEXT_PATH + '/pages/branch/checkUserId.do',
				data: {'branchAdmin':value},
				cache: false,
				async: false,
				type: 'POST',
				dataType: 'json',
				success: function(result){
					if (result.isExist){
						flag = false;
						$('#idbranchAdmin_field').html('该用户名已存在').addClass('error_tipinfo').show();
						$('#input_btn2').attr('disabled', 'true');
					} else {
						flag = true;
						clearCarBinError();
					}
				}
			});
			return flag;
		}
		
		function clearCarBinError(){
			$('#idbranchAdmin_field').removeClass('error_tipinfo').html('输入数字字符');
			$('#input_btn2').removeAttr('disabled');
		}
		
		function checkAllParams() {
			if(isEmpty($('#id_branchAdmin').val())){
				$('#inputForm').submit();
			}
			if(checkUserId()){
				if($('#inputForm').validate().form()){
					$('#inputForm').submit();
					$('#input_btn2').attr('disabled', 'true');
					$("#loadingBarDiv").css("display","inline");
					$("#contentDiv").css("display","none");
				}
			}
		}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="modify.do" id="inputForm" cssClass="validate">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<s:hidden name="branch.status" />
						<s:hidden name="branch.superBranchCode" />
						<s:hidden name="branch.branchCode"/>
						<caption>${ACT.name}<span class="caption_title"> | <f:link href="/pages/branch/showModifyType.do?branch.branchCode=${branch.branchCode}">要修改机构类型？</f:link><s:if test="showProxy"> | <f:link href="/pages/proxy/list.do?branchProxy.proxyName=${branch.branchCode}">要修改代理关系？</f:link></s:if></span></caption>
						<tr>
							<td width="80" height="30" align="right">机构使用币种</td>
							<td>
								<s:select name="branch.curCode" list="currCodeList" headerKey="" headerValue="--请选择--" listKey="currCode" listValue="currName" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择币种</span>
							</td>
							
							<td width="80" height="30" align="right">机构索引号</td>
							<td>
								<s:textfield name="branch.branchIndex" maxlength="20"/>
								<span class="field_tipinfo">请输入索引号</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">管理机构</td>
							<td>
								<s:hidden id="id_manage" name="branch.parent" ></s:hidden>
								<s:if test="centerOrCenterDeptRoleLogined"> <%--运营中心或部门可以修改管理机构 --%>
									<s:textfield id="id_manage_sel" name="parentName" cssClass="{required:true}" />
								</s:if>
								<s:else>
									<s:textfield id="id_manage_sel"  name="parentName" cssClass="{required:true} readonly" readonly="true" />
								</s:else>
								<span class="field_tipinfo">请选择机构</span>
							</td>
							
							<s:if test="cardBranch">
								<td width="80" height="30" align="right">发展方</td>
								<td colspan="3">
									<s:if test="centerOrCenterDeptRoleLogined"> <%--运营中心或部门可以修改发展方 --%>
										<s:select id="id_card_develop" name="branch.developSide" list="developBranchList" 
											headerKey="" headerValue="--请选择--" listKey="branchCode" listValue="branchName" cssClass="{required:true}" />
									</s:if>
									<s:else>
										<s:select id="id_card_develop" name="branch.developSide" list="developBranchList" 
											headerKey="" headerValue="--请选择--" listKey="branchCode" listValue="branchName" cssClass="{required:true} readonly" 
											disabled="true" />
										<s:hidden name="branch.developSide"/>
									</s:else>
									<span class="field_tipinfo">请选择发展方</span>
								</td>
							</s:if>
						</tr>
						<%--
						<s:if test="showSetMode">
							<tr id="tr_setMode">
								<td width="80" height="30" align="right">清算模式</td>
								<td>
									<s:select id="id_SetMode" name="setMode" list="setModeList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
									<span class="field_tipinfo">管理的发卡机构、营运代理的清算模式随之改变</span>
								</td>
							</tr>
						</s:if>
						--%>
						<s:if test="showSetMode">
							<tr>
								<td width="80" height="30" align="right">清算模式</td>
								<td>
									<s:textfield name="setModeName" cssClass="readonly" readonly="true"/>
									<s:hidden name="setMode"/>
								</td>
	
								<s:if test="branch.status == 20 && cardBranch">
									<td width="80" height="30" align="right">资信额度</td>
									<td>
										<s:textfield name="branch.cardRiskAmt" cssClass="{required:true, num:true, decimal:'10,2'}"/>
										<span class="field_tipinfo">万元</span>
										<span class="error_tipinfo">整数位最多8位</span>
									</td>
								</s:if>
							</tr>
						</s:if>
						<s:if test="showSettle">
							<tr>
								<td width="80" height="30" align="right">刷卡充值是否独立清算</td>
								<td>
									<s:select id="id_sale_setle" list="yesOrNoList" name="branch.isSettle" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
									<span class="field_tipinfo">请选择是或否</span>
								</td>
							</tr>
						</s:if>
						<tr>
							<td width="80" height="30" align="right">机构名称</td>
							<td>
								<s:textfield name="branch.branchName" cssClass="{required:true}" maxlength="100" />
								<span class="field_tipinfo">请输入名称</span>
							</td>
							
							<td width="80" height="30" align="right">机构简称</td>
							<td>
								<s:if test="branch.status!=20">
									<s:textfield id="abbName" name="branch.branchAbbname" cssClass="{required:true}" maxlength="30"/>
								</s:if>
								<s:else>
									<s:textfield id="id_branchAbbname" name="branch.branchAbbname" cssClass="{required:true}" maxlength="30"/>
								</s:else>
								<span class="field_tipinfo">请输入简称</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">地区</td>
							<td>
								<s:hidden id="idArea" name="branch.areaCode" />
								<s:textfield id="idArea_sel" name="areaName" cssClass="{required:true}"/>
								<span class="field_tipinfo">请选择地区</span>
							</td>
							<td width="80" height="30" align="right">地址</td>
							<td>
								<s:textfield name="branch.address" maxlength="30" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入地址</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">联系人</td>
							<td>
								<s:textfield name="branch.contact" maxlength="10" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入联系人</span>
							</td>
							<td width="80" height="30" align="right">联系电话</td>
							<td>
								<s:textfield name="branch.phone" cssClass="{required:true, digitOrAllowChars:'-'}" maxlength="20"/>
								<span class="field_tipinfo">请输入电话号</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">传真号</td>
							<td>
								<s:textfield name="branch.fax" cssClass="{digitOrAllowChars:'-'}" maxlength="20"/>
								<span class="field_tipinfo">请输入传真号</span>
							</td>
							<td width="80" height="30" align="right">邮编</td>
							<td>
								<s:textfield name="branch.zip" cssClass="{digit:true}" maxlength="10"/>
								<span class="field_tipinfo">请输入邮编</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">账户类型</td>
							<td>
								<s:select id="id_acctType" name="branch.acctType" list="acctTypeList" headerKey="" headerValue="--请选择--" 
									listKey="value" listValue="name" cssClass="{required:true}" />
								<span class="field_tipinfo">请选择</span>
							</td>
							<td width="80" height="30" align="right">账户介质类型</td>
							<td>
								<s:select id="id_acctMediaType" name="branch.acctMediaType" list="acctMediaTypeList" headerKey="" headerValue="--请选择--" 
									listKey="value" listValue="name" cssClass="{required:true}" />
								<span class="field_tipinfo">请选择</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">开户行名称</td>
							<td>
								<s:textfield id="idBank_sel" name="branch.bankName" maxlength="100" cssClass="{required:true}"/>
								<span class="field_tipinfo">点击选择</span>
							</td>
							<td width="80" height="30" align="right">开户行号</td>
							<td>
								<s:textfield id="idBank" name="branch.bankNo" cssClass="{required:true, digit:true, minlength:12} readonly" maxlength="12"/>
								<span class="field_tipinfo">点击行名选择</span>
							</td>
						</tr>

						<tr>
							<td width="80" height="30" align="right">账户地区码</td>
							<td>
								<s:hidden id="idAccAreaCode" name="branch.accAreaCode" />
								<s:textfield id="idAccAreaCode_sel" name="accAreaName" cssClass="{required:true} readonly" readonly="true" />
								<span class="field_tipinfo">点击行名选择</span>
							</td>
							<td width="80" height="30" align="right">账号</td>
							<td>
								<s:textfield name="branch.accNo" cssClass="{required:true, digitOrAllowChars:'-'}" maxlength="32" />
								<span class="field_tipinfo">请输入账号</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">账户户名</td>
							<td>
								<s:textfield id="idAccName" name="branch.accName" maxlength="30" cssClass="{required:true}" />
								<span class="field_tipinfo">请输入户名</span>
							</td>
							<td width="80" height="30" align="right">风险级别</td>
							<td>
								<s:select name="branch.riskLevel" list="riskLevelTypeList"  
									headerKey="" headerValue="--请选择--" listKey="value" listValue="name" />
								<span class="field_tipinfo">选择风险级别</span>
							</td>
						</tr>
						
						<tr>
							<td width="80" height="30" align="right">营业执照</td>
							<td>
								<s:textfield name="branch.license" maxlength="32" cssClass="{digitOrLetter:true}"/>
								<span class="field_tipinfo">输入数字字母</span>
							</td>
							<td width="80" height="30" align="right" >营业执照有效期</td>
							<td>
								<input type="text" name="branch.licenseExpDate" 
									value="<s:date name="branch.licenseExpDate" format="yyyy-MM-dd" />"
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',autoPickDate:true,isShowOthers:false,readOnly:true})"/>
								<span class="field_tipinfo">请选择日期</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">组织机构号</td>
							<td>
								<s:textfield name="branch.organization" maxlength="32" cssClass="{digitOrLetter:true}"/>
								<span class="field_tipinfo">输入数字字母</span>
							</td>
							<td width="80" height="30" align="right" >组织机构代码有效期</td>
							<td>
								<input type="text" name="branch.organizationExpireDate" 
									value='<s:property value="branch.organizationExpireDate"/>'
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',autoPickDate:true,isShowOthers:false})"/>
								<span class="field_tipinfo">请选择日期</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right" >税务登记号</td>
							<td>
								<s:textfield name="branch.taxRegCode" size="32"/>
							</td>
							<td width="80" height="30" align="right">法人姓名</td>
							<td>
								<s:textfield name="branch.legalPersonName" maxlength="64"/>
								<span class="field_tipinfo"></span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">法人身份证号码</td>
							<td>
								<s:textfield name="branch.legalPersonIdcard" />
								<span class="field_tipinfo"></span>
							</td>
							<td width="80" height="30" align="right" >法人身份证有效期</td>
							<td>
								<input type="text" name="branch.legalPersonIdcardExpDate" 
									value="<s:date name="branch.legalPersonIdcardExpDate" format="yyyy-MM-dd" />"
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',autoPickDate:true,isShowOthers:false,readOnly:true})"/>
								<span class="field_tipinfo">请选择日期</span>
							</td>
						</tr>
						<s:if test="cardBranch">
							<tr>
								<td width="80" height="30" align="right">是否单机产品机构</td>
								<td>
									<s:select name="branch.singleProduct" list="yesOrNoList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" 
										cssClass="{required:true}"></s:select>
									<span class="field_tipinfo">请选择</span>
								</td>
								<td></td>
								<td></td>
							</tr>
						</s:if>
						<tr>
							<td width="80" height="30" align="right">备注</td>
							<td>
								<s:textfield name="branch.remark" maxlength="80"/>
								<span class="field_tipinfo">请输入备注</span>
							</td>
							<s:if test="branch.status == 20">
								<td width="80" height="30" align="right" >管理员用户名</td>
								<td>
									<s:textfield id="id_branchAdmin" name="branch.adminId" cssClass="{required:true, digitOrLetter:true}" />
									<span id="idbranchAdmin_field" class="field_tipinfo">输入数字字符</span>
								</td>
							</s:if>
							<s:else>
								<td width="80" height="30" align="right"></td>
								<td>
									<s:hidden name="branch.adminId" />
									<s:if test="cardBranch">
										<s:hidden name="branch.cardRiskAmt" />
									</s:if>
								</td>
							</s:else>
						</tr>

						<tr>
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="button" value="提交" id="input_btn2"  name="ok" onclick="checkAllParams();"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm');clearCarBinError();" style="margin-left:30px;" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/branch/list.do?goBack=goBack')" style="margin-left:30px;" />
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_BRANCH_MODIFY"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>