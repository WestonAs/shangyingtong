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
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script>
		$(function(){
			var showCenter = $('#id_showCenter').val();
			var showCard = $('#id_showCard').val();
			var showFenZhi = $('#id_showFenZhi').val();
			var parent = $('#id_parent').val();
			
			$('#id_ptDiscntRate').blur(function(){
				var rate = $('#id_ptDiscntRate').val();
				validateRate(rate);
			});
			
			//营运中心、营运中心部门
			if(showCenter=='true' || showFenZhi=='true'){
				$('#id_branchCode_center_tr').show();
				$('#id_branchCode_card_tr').hide();
				$('#branchName').removeAttr('disabled');
				$('#id_cardIssuer').removeAttr('disabled');
				$('#id_cardIssuer_card').attr('disabled', true);
				
				// 营运中心、中心部门
				if(showCenter=='true'){
					Selector.selectBranch('branchName', 'id_cardIssuer', true, '20', loadMerchGroup);
				}
				// 分支机构
				else if(showFenZhi=='true'){
					Selector.selectBranch('branchName', 'id_cardIssuer', true, '20', loadMerchGroup, '', parent);
				}
				$('#branchName').change(function(){loadPtClass();});
			}
			// 发卡机构、发卡机构部门
			else if(showCard=='true'){
				$('#id_branchCode_center_tr').hide();
				$('#id_branchCode_card_tr').show();
				$('#branchName').attr('disabled', true);
				$('#id_cardIssuer').attr('disabled', true);
				$('#id_cardIssuer_card').removeAttr('disabled');
				var branchCode = $('#id_cardIssuer_card').val();
				Selector.selectMerch('merchName', 'idMerchId', true, branchCode);
				loadPtClass();
			}
		});
		
		function loadPtClass(){
			var showCard = $('#id_showCard').val();
			var cardIssuer ;
			if(showCard=='true'){
				cardIssuer = $('#id_cardIssuer_card').val();
			}else{
				cardIssuer = $('#id_cardIssuer').val();
			}
			$("#id_PtClass").load(CONTEXT_PATH + "/cardTypeSet/pointMaintain/loadBranch.do",{'cardIssuer':cardIssuer, 'callId':callId()});
		}
		
		function loadMerchGroup() {
			var branchCode = $('#id_cardIssuer').val();
			if (!isEmpty(branchCode)){
				$('#merchName').unbind().removeMulitselector();
				$('#merchName').val('');
				$('#idMerchId').val('');
				Selector.selectMerch('merchName', 'idMerchId', true, branchCode);
			} else {
				$('#merchName').focus(function(){
					var branchCode = $('#id_cardIssuer').val();
					if (isEmpty(branchCode)){
						showMsg('请先选择发卡机构再选择商户');
					}
				});
			}
		}
		
		function validateRate(rate){
			// 0--100的整数
			if(!isDecimal(rate,"8,2")||rate<0 || rate>100){
				showMsg('请输入0至100的小数。');
				return false;
			} 
			return true;
		}
		
		function check(){
			var rate = $('#id_ptDiscntRate').val();
			return validateRate(rate);
		}
		
		function submitForm(){
			if(!check()){
				return false;
			}
			$("#inputForm").submit();
		}
	</script>
		
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>	
			<div class="contentb">
			<s:form action="add.do" id="inputForm" cssClass="validate">
			    <s:hidden id="id_showCenter" name="showCenter"></s:hidden>
					<s:hidden id="id_showCard" name="showCard"></s:hidden>
					<s:hidden id="id_showFenZhi" name="showFenZhi"></s:hidden>
					<s:hidden id="id_parent" name="parent"></s:hidden>
				<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					<!-- tr>
						<td width="80" height="30" align="right">发卡机构</td>
						<td><s:textfield id="id_cardIssuer"  name="merchPointRate.cardIssuer"  cssClass="{required:true}"/><span class="field_tipinfo">请输入发卡机构</span></td>
						
						<td width="80" height="30" align="right">商户号码</td>
						<td><s:textfield id="id_merNo"  name="merchPointRate.merNo"  cssClass="{required:true}"/><span class="field_tipinfo">请输入商户号码</span></td>
					</tr-->
					<tr id="id_branchCode_center_tr">
						<td width="80" height="30" align="right">发卡机构</td>
						<td>
							<s:hidden id="id_cardIssuer" name="merchPointRate.cardIssuer" disabled="true"></s:hidden>
							<s:textfield  id="branchName" cssClass="{required:true}" disabled="true"></s:textfield>
							<span class="field_tipinfo">请选择发卡机构</span>
						</td>
					</tr>
					<tr id="id_branchCode_card_tr">	
						<td width="80" height="30" align="right">发卡机构</td>
						<td>
						    <s:textfield id="id_cardIssuer_card" name="merchPointRate.cardIssuer" disabled="true" cssClass="readonly" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">商户</td>
						<td> 
							<s:hidden id="idMerchId" name="merchPointRate.merNo" />
							<s:textfield id="merchName" name="merchName" cssClass="{required:true}"/>
							<span class="field_tipinfo">请选择商户</span>
						</td>
					</tr>
					<tr>
					    <td width="80" height="30" align="right">积分类型</td>
							<td>
								<select id="id_PtClass" name="merchPointRate.ptClass" class="{required:true}"></select>
								<span class="field_tipinfo">请选择积分类型</span>
							</td>
					</tr>
					<tr>
					    <td width="80" height="30" align="right">积分返利兑换率</td> 
						<td>
							<s:textfield id="id_ptDiscntRate" name="merchPointRate.ptDiscntRate" cssClass="{required:true, decimal:'8,2'}" ></s:textfield><span>%</span>
							<span class="field_tipinfo">请输入兑换率（最多两位小数）</span>
						</td>
					</tr>
					<tr >
						<td width="80" height="30" align="right">备注</td> 
						<td>
							<s:textfield name="merchPointRate.remark" ></s:textfield>
						</td>
					</tr>
					<tr>
						<td width="80" height="30" align="right">&nbsp;</td>
						<td height="30" colspan="3">
							<input type="submit" value="提交" id="input_btn2"  name="ok" onclick="return submitForm()"/>
							<input type="button" value="清除" name="escape" onclick="FormUtils.reset('inputForm');" class="ml30" />
							<input type="button" value="返回" name="escape" onclick="gotoUrl('/cardTypeSet/pointMaintain/list.do?goBack=goBack')" class="ml30" />
						</td>
					</tr>
				</table>
				<s:token name="_TOKEN_POINTMAINTAIN_ADD"/>
			</s:form>
		</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>