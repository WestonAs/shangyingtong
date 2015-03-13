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
			if('${loginRoleType}' == '00' || '${loginRoleType}' == '11'){
				Selector.selectBranch('idManageBranch_sel', 'idManageBranch', true, '00,01');
				Selector.selectBranch('idBranchName', 'idBranchCode', true, '20', fillInfosFromCardBranch);
			} else if('${loginRoleType}' == '01'){
				Selector.selectBranch('idManageBranch_sel', 'idManageBranch', true, '01', '', '', '${loginBranchCode}');
				Selector.selectBranch('idBranchName', 'idBranchCode', true, '20', fillInfosFromCardBranch, '', '${loginBranchCode}');
			}
			//Selector.selectMerch('idParent_sel', 'idParent', true);
			Selector.selectArea('idArea_sel', 'idArea', true);
			
			//Selector.selectArea('idAccAreaCode_sel', 'idAccAreaCode', true);
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
					$.post(CONTEXT_PATH + '/pages/branch/getAdminUserId.do', {'branchAbbname':value, 'fromMerch':'Y', 'callId':callId()}, function(data){
						$('#id_merchAdmin').val(data.userId);
					}, 'json');
				}
			});
			
			// 检查用户名是否已经存在
			$('#id_merchAdmin').blur(function(){
				$('#idmerchAdmin_field').html('20个以内的字符或数字');
				checkUserId();
			});
			
		});
		
		/** 根据直属发卡机构，充填商户的法人姓名、法人身份证号等信息 */
		function fillInfosFromCardBranch(){
			$.post(CONTEXT_PATH + '/pages/merch/ajaxFindCardBranchInfos.do', {'formMap.cardBranchCode':$('#idBranchCode').val(), 'callId':callId()}, 
				function(json){
					if(json.returnCode=="1"){
						$('[name="merchInfoReg.merchCode"]').val(json.license==null?"":json.license);
						$('[name="merchInfoReg.licenseExpDate"]').val(json.licenseExpDate==null?"":json.licenseExpDate);
						$('[name="merchInfoReg.organization"]').val(json.organization==null?"":json.organization);
						$('[name="merchInfoReg.organizationExpireDate"]').val(json.organizationExpireDate==null?"":json.organizationExpireDate);
						$('[name="merchInfoReg.legalPersonName"]').val(json.legalPersonName==null?"":json.legalPersonName);
						$('[name="merchInfoReg.legalPersonIdcard"]').val(json.legalPersonIdcard==null?"":json.legalPersonIdcard);
						$('[name="merchInfoReg.legalPersonIdcardExpDate"]').val(json.legalPersonIdcardExpDate==null?"":json.legalPersonIdcardExpDate);
						$('[name="merchInfoReg.taxRegCode"]').val(json.taxRegCode==null?"":json.taxRegCode);
					}
				}, 'json');
		}
		
		/** 根据银行的行号来取得地区码，作为开户行地区码*/
		function loadAreaCode(bankNo){
			$.post(CONTEXT_PATH + '/pages/branch/loadAccAreaCode.do', {'bankNo':bankNo, 'callId':callId()}, 
				function(data){
					$('#idAccAreaCode_sel').val(data.accAreaName);
					$('#idAccAreaCode').val(data.accAreaCode);
				}, 'json');
		}
		
		$().ready(function() {
			function format(type) {
				return type.merchType + '|' + type.typeName;
			}
			
		    $("#id_merchType").autocomplete(CONTEXT_PATH + "/pages/merch/merchType.do", {
		    	minChars: 0,
		    	matchContains: 'word',
		    	mustMatch: true,
		    	autoFill: true,
				dataType: "json",
				parse: function(data) {
					return $.map(data, function(row) {
						return {
							data: row,
							value: row.merchType + '|' + row.typeName,
							result: row.merchType + '|' + row.typeName
						}
					});
				},
				formatItem: function(item) {
					return format(item);
				}
		    });
		});
		
		function checkUserId() {
			var flag = false;
			var value = $('#id_merchAdmin').val();
			if (isEmpty(value)){
				return false;
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
						$('#idmerchAdmin_field').html('该用户名已存在').addClass('error_tipinfo').show();
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
			$('#idmerchAdmin_field').removeClass('error_tipinfo').html('20个以内的字符或数字');
			$('#input_btn2').removeAttr('disabled');
		}
		function checkAllParams() {
			if(isEmpty($('#id_merchAdmin').val())){
				$('#inputForm').submit();
			}
			if(checkUserId()){
				$('#inputForm').submit();
			}
		}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="add.do" id="inputForm" cssClass="validate">
					<div>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						
						<%--tr>
							<td width="80" height="30" align="right">商户编号</td>
							<td colspan="3">
								<s:textfield name="merchInfoReg.merchId" cssClass="{required:true, digitOrLetter:true, minlength:15}" maxlength="15"/>
								<span class="field_tipinfo">请输入商户编号</span>
								<span class="error_tipinfo">商户编号必须15位</span>
							</td>
							
							<td width="80" height="30" align="right">发展机构</td>
							<td>
								<s:hidden id="idBranchCode" name="merchInfoReg.branchCode" />
								<s:textfield id="idBranchCode_sel" name="developBranch"/>
								<span class="field_tipinfo">请选择发展机构</span>
							</td >
						</tr--%>
						<s:if test="centerOrCenterDeptRoleLogined">
							<tr>
								<td width="80" height="30" align="right">商户编号规则 </td>
								<td height="30">
									<label><s:checkbox name="merchInfoReg.merch898" fieldValue="true" /> 898商户 </label>
									<span class="field_tipinfo">商户编号前缀及生成规则</span>
								</td>
							</tr>
						</s:if>
						<tr>
							<td width="80" height="30" align="right">商户名称</td>
							<td>
								<s:textfield name="merchInfoReg.merchName" cssClass="{required:true}" maxlength="100" onblur="$('#idAccName').val(this.value)"/>
								<span class="field_tipinfo">请输入商户名称</span>
							</td>
							<td width="80" height="30" align="right">管理机构</td>
							<s:if test="showModifyManage"><%--发卡机构或部门，运营代理角色 --%>
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
							<td width="80" height="30" align="right">商户类型代码</td>
							<td>
								<!-- 
								<s:select name="merchInfoReg.merchType" list="merchTypeList" headerKey="" headerValue="--请选择--" listKey="merchType" listValue="typeName" cssClass="{required:true}"></s:select>
								 -->
								 <s:textfield id="id_merchType" name="merchInfoReg.merchType" cssClass="{required:true}"/>
								<span class="field_tipinfo">请选择商户类型</span>
							</td>
						</tr>
						<%--  
						<tr>
							<td width="80" height="30" align="right">清算周期标志</td>
							<td>
								<s:select name="merchInfoReg.setCycle" list="setCycleList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择清算周期</span>
							</td>
						</tr>
						--%>
						<tr>
							<td width="80" height="30" align="right">地区</td>
							<td>
								<s:hidden id="idArea" name="merchInfoReg.areaCode" />
								<s:textfield id="idArea_sel" name="areaName" cssClass="{required:true}"/>
								<span class="field_tipinfo">请选择地区</span>
							</td>
							<td width="80" height="30" align="right">货币代码</td>
							<td>
								<s:select name="merchInfoReg.currCode" list="currCodeList" listKey="currCode" listValue="currName" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择货币代码</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">商户简称</td>
							<td>
								<s:textfield id="id_merchAbb" name="merchInfoReg.merchAbb" maxlength="15" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入商户简称</span>
							</td>
							
							<td width="80" height="30" align="right">英文名称</td>
							<td>
								<s:textfield name="merchInfoReg.merchEn" maxlength="200"/>
								<span class="field_tipinfo">请输入英文名称</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">联系人</td>
							<td>
								<s:textfield name="merchInfoReg.linkMan" maxlength="30" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入联系人</span>
							</td>
							<td width="80" height="30" align="right">联系电话</td>
							<td>
								<s:textfield name="merchInfoReg.telNo" cssClass="{required:true, digitOrAllowChars:'-'}" maxlength="30"/>
								<span class="field_tipinfo">请输入联系电话</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">联系地址</td>
							<td>
								<s:textfield name="merchInfoReg.merchAddress" maxlength="120" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入联系地址</span>
							</td>
							<td width="80" height="30" align="right">传真</td>
							<td>
								<s:textfield name="merchInfoReg.faxNo" cssClass="{digitOrAllowChars:'-'}" maxlength="30"/>
								<span class="field_tipinfo">请输入传真</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">E-mail</td>
							<td>
								<s:textfield name="merchInfoReg.email" cssClass="{email:true}" maxlength="60"/>
								<span class="field_tipinfo">请输入邮件地址</span>
							</td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">是否单机产品机构</td>
							<td>
								<s:select name="merchInfoReg.singleProduct" list="yesOrNoFlagList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" 
									cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择</span>
							</td>

							<td width="80" height="30" align="right">客户经理</td>
							<td>
								<s:textfield name="merchInfoReg.unPay" maxlength="30"/>
								<span class="field_tipinfo">请输入客户经理</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">账户介质类型</td>
							<td>
								<s:select id="id_acctMediaType" name="merchInfoReg.acctMediaType" list="acctMediaTypeList" headerKey="" headerValue="--请选择--" 
									listKey="value" listValue="name" cssClass="{required:true}" />
								<span class="field_tipinfo">请选择</span>
							</td>

							<td width="80" height="30" align="right">直属发卡机构</td>
							<td>
								<s:hidden id="idBranchCode" name="merchInfoReg.cardBranch"/>
								<s:if test="cardOrCardDeptRoleLogined">
									<s:textfield id="idBranchName" name="cardBranchName" cssClass="{required:true} readonly" readonly="true"/>
								</s:if>
								<s:else>
									<s:textfield id="idBranchName" name="cardBranchName" />
								</s:else>
								<span class="field_tipinfo">选择后，也将会自动充填商户营业执照等信息</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">开户行名称</td>
							<td>
								<s:textfield id="idBank_sel" name="merchInfoReg.bankName" cssClass="{required:true}"/>
								<span class="field_tipinfo">点击选择</span>
							</td>

							<td width="80" height="30" align="right">账户类型</td>
							<td>
								<s:select id="id_acctType" name="merchInfoReg.acctType" list="acctTypeList" headerKey="" headerValue="--请选择--" 
									listKey="value" listValue="name" cssClass="{required:true}" />
								<span class="field_tipinfo">请选择账户类型</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">账户地区</td>
							<td>
								<s:hidden id="idAccAreaCode" name="merchInfoReg.accAreaCode" />
								<s:textfield id="idAccAreaCode_sel" name="accAreaName" cssClass="{required:true} readonly" readonly="true"/>
								<span class="field_tipinfo">请点击开户行名</span>
							</td>
							
							<td width="80" height="30" align="right">开户行号</td>
							<td>
								<s:textfield id="idBank" name="merchInfoReg.bankNo" cssClass="{required:true, digit:true, minlength:12} readonly" maxlength="12" readonly="true"/>
								<span class="field_tipinfo">请点击开户行名</span>
							</td>
							
						</tr>
						<tr>
							<td width="80" height="30" align="right">账户户名</td>
							<td>
								<s:textfield id="idAccName" name="merchInfoReg.accName" maxlength="30" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入户名</span>
							</td>
							<td width="80" height="30" align="right">账号</td>
							<td>
								<s:textfield name="merchInfoReg.accNo" cssClass="{required:true, digitOrAllowChars:'-'}" maxlength="32"/>
								<span class="field_tipinfo">请输入账号</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">是否使用密码标志</td>
							<td>
								<s:select name="merchInfoReg.usePwdFlag" list="usePwdFlagList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">请选择密码标志</span>
							</td>

							<td width="80" height="30" align="right">清算资金是否轧差</td>
							<td>
								<s:select name="merchInfoReg.isNetting" list="isNettingList" listKey="value" listValue="name" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">是否轧差？</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">签约时间</td>
							<td>
								<s:textfield name="merchInfoReg.signingTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
								<span class="field_tipinfo">请选择签约时间</span>
							</td>
							
							<td width="80" height="30" align="right">风险级别</td>
							<td>
								<s:select name="merchInfoReg.riskLevel" list="riskLevelTypeList"  
									headerKey="" headerValue="--请选择--" listKey="value" listValue="name" />
								<span class="field_tipinfo">选择风险级别</span>
							</td>

						</tr>
						
						<tr>
							<td width="80" height="30" align="right">营业执照编号</td>
							<td>
								<s:textfield name="merchInfoReg.merchCode" maxlength="60" cssClass="{required:true}"/>
								<span class="field_tipinfo">请输入编号</span>
							</td>
							
							<td width="80" height="30" align="right" >营业执照有效期</td>
							<td>
								<s:textfield name="merchInfoReg.licenseExpDate" 
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',autoPickDate:true,isShowOthers:false,readOnly:true})"/>
								<span class="field_tipinfo">请选择日期</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">组织机构号</td>
							<td>
								<s:textfield name="merchInfoReg.organization" maxlength="32" cssClass="{digitOrLetter:true}"/>
								<span class="field_tipinfo">输入组织机构号</span>
							</td>
							<td width="80" height="30" align="right" >组织机构代码有效期</td>
							<td>
								<s:textfield name="merchInfoReg.organizationExpireDate" 
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',autoPickDate:true,isShowOthers:false})"/>
								<span class="field_tipinfo">请选择日期yyyy-MM-dd</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right" >税务登记号</td>
							<td>
								<s:textfield name="merchInfoReg.taxRegCode" size="32"/>
							</td>
							<td width="80" height="30" align="right">法人姓名</td>
							<td>
								<s:textfield name="merchInfoReg.legalPersonName" maxlength="64"/>
								<span class="field_tipinfo"></span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">法人身份证号码</td>
							<td>
								<s:textfield name="merchInfoReg.legalPersonIdcard" />
								<span class="field_tipinfo"></span>
							</td>
							<td width="80" height="30" align="right" >法人身份证有效期</td>
							<td>
								<s:textfield name="merchInfoReg.legalPersonIdcardExpDate" 
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',autoPickDate:true,isShowOthers:false,readOnly:true})"/>
								<span class="field_tipinfo">请选择日期</span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">单位经营范围</td>
							<td>
								<s:textfield name="merchInfoReg.companyBusinessScope" cssClass="{maxlength:100}"/>
								<span class="field_tipinfo"></span>
							</td>
							<td width="80" height="30" align="right">易航宝虚拟帐号</td>
							<td>
								<s:textfield name="merchInfoReg.yhbVirtualAcct" />
								<span class="field_tipinfo"></span>
							</td>
						</tr>
						<tr>
							<td width="80" height="30" align="right">备注</td>
							<td>
								<s:textfield name="merchInfoReg.remark" />
								<span class="field_tipinfo"></span>
							</td>
							<td width="80" height="30" align="right" >管理员用户名</td>
							<td>
								<s:textfield id="id_merchAdmin" name="merchAdmin" cssClass="{required:true, digitOrLetter:true, maxlength:20}" maxlength="20"/>
								<span id="idmerchAdmin_field" class="field_tipinfo">20个以内的字符或数字</span>
							</td>
						</tr>
						<!-- 
						<tr>
							<td width="80" height="30" align="right">营业时间</td>
							<td>
								<s:textfield name="merchInfoReg.businessTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
								<span class="field_tipinfo">请选择营业时间</span>
							</td>
							
							<td width="80" height="30" align="right">成立时间</td>
							<td>
								<s:textfield name="merchInfoReg.createTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
								<span class="field_tipinfo">请选择成立时间</span>
							</td>
						</tr>
						 -->
						<tr>
							<!-- 
							<td width="80" height="30" align="right">上级商户编号</td>
							<td>
								<s:hidden id="idParent" name="merchInfoReg.parent" />
								<s:textfield id="idParent_sel" name="parent" />
								<span class="field_tipinfo">请选择上级商户编号</span>
							</td>
							-->
						</tr>
						
						<tr>	
							<td width="80" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type="button" value="提交" id="input_btn2"  onclick="return checkAllParams();"/>
								<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm'); clearCarBinError();" class="ml30" />
								<input type="button" value="返回" name="escape" onclick="gotoUrl('/pages/merch/list.do?goBack=goBack')" class="ml30" />
							</td>
						</tr>
					</table>
					</div>
					<s:hidden name="branchCode" />
					<s:token name="_TOKEN_MERCH_ADD"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>