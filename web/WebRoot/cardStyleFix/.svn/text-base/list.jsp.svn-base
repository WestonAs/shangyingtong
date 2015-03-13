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
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>
		
		<f:css href="/css/multiselctor.css"/>
		<f:js src="/js/plugin/jquery.multiselector.js"/>
		<script>
			$(function(){
				if('${loginRoleTypeCode}' == '00'){
					Selector.selectBranch('id_branchCodeName', 'id_branchCode', true, '20');
				} else if('${loginRoleTypeCode}' == '01'){
					Selector.selectBranch('id_branchCodeName', 'id_branchCode', true, '20', '', '', '${loginBranchCode}');
				}
				
				$("#id_branchCode").change(changeCardIssuer);
			});
			
			function changeCardIssuer(){
				var cardIssuer = $("#id_branchCode").val();
				$("#cardSubClassSel").val("").empty(); //清空卡子类型
				if (isEmpty(cardIssuer)){
					return ;
				}
				$.getJSON(CONTEXT_PATH + "/ajax/ajaxFindCardSubClasses.do",
						{'formMap.cardIssuer':cardIssuer, 'callId':callId()}, 
						function(json){
							$("#cardSubClassSel").append("<option value=''>--请选择--</option>");
							var cardSubClassDefs = json.cardSubClassDefs;
							if(cardSubClassDefs!=null && cardSubClassDefs.length>0){
								
								for(i in cardSubClassDefs){
									var option = "<option value='"+cardSubClassDefs[i].cardSubclass +"'> "
										+cardSubClassDefs[i].cardSubclassName+"</option>";
									$("#cardSubClassSel").append(option);
								}
							}
						}
					);
			}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/layout/location.jsp"></jsp:include>
		
		<!-- 查询功能区 -->
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<s:form action="list.do" id="searchForm" cssClass="validate-tip">
					<table id="form_grid" class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td align="right">卡样ID</td>
							<td>
								<s:textfield name="makeCardReg.makeId" cssClass="{digitOrLetter:true}"/>
								<span class="error_tipinfo">ID只能数字或字母</span>
							</td>
							<td align="right">卡样名称</td>
							<td>
								<s:textfield name="makeCardReg.makeName" />
							</td>
							<td align="right">当前卡样图案状态</td>
							<td>
								<s:select name="makeCardReg.picStatus" list="makeCardRegStateList" headerKey="" headerValue="--请选择--" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<tr>
							<s:if test="centerOrCenterDeptRoleLogined || fenzhiRoleLogined">
								<td align="right">发卡机构</td>
								<td>
									<s:hidden id="id_branchCode" name="makeCardReg.branchCode"/>
									<s:textfield id="id_branchCodeName" name="formMap.cardBranchName"/> <!-- 名称只是简单在页面给用户显示而已 -->
								</td>
							</s:if>
							<s:elseif test="cardOrCardDeptRoleLogined">
								<td align="right">发卡机构</td>
								<td>
									<s:select id="id_branchCode" name="makeCardReg.branchCode" list="myCardBranch"
										 headerKey="" headerValue="--请选择--" 
										 listKey="branchCode" listValue="branchName" 
										 onmouseover="FixWidth(this);" />
								</td>
							</s:elseif>
							<s:else>
								<td align="right">发卡机构编号</td>
								<td>
									<s:textfield id="id_branchCode"  name="makeCardReg.branchCode"/>
								</td>
							</s:else>
							
							<td align="right">卡子类型</td>
							<s:if test="cardOrCardDeptRoleLogined">
								<td>
									<s:select id="cardSubClassSel" name="makeCardReg.cardSubtype" list="cardSubTypeList" 
											headerKey="" headerValue="--请选择--" 
											listKey="cardSubclass" listValue="cardSubclassName" />
								</td>
							</s:if>
							<s:else>
								<td>
									<s:textfield name="makeCardReg.cardSubtype"/>
								</td>
							</s:else>
							
							<td height="30" colspan="4">
								<input type="submit" value="查询" id="input_btn2"  name="ok" />
								<input style="margin-left:10px;" type="button" value="清除" onclick="FormUtils.clearFormFields('searchForm')" name="escape" />
								<f:pspan pid="makecardmgr_cardfix_add">
									<input style="margin-left:10px;" type="button" value="新增" onclick="javascript:gotoUrl('/cardStyleFix/preShowAdd.do');" id="input_btn3"  name="escape" />
								</f:pspan>
							</td>
						</tr>
					</table>
					<s:token name="_TOKEN_CARDSTYLEFIX_LIST"/>
				</s:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			<tr>
			   <td align="center" nowrap class="titlebg">卡样ID</td>
			   <td align="center" nowrap class="titlebg">卡样名称</td>
			   <td align="center" nowrap class="titlebg">卡子类型</td>
			   <td align="center" nowrap class="titlebg">发卡机构</td>
			   <td align="center" nowrap class="titlebg">制卡厂商</td>
			   <td align="center" nowrap class="titlebg">卡样状态</td>
			   <td align="center" nowrap class="titlebg">是否定版</td>
			   <td align="center" nowrap class="titlebg">操作</td>
			</tr>
			</thead>
			<s:iterator value="page.data">
			<tr>
			  <td align="center" nowrap>${makeId}</td>
			  <td align="center" nowrap>${makeName}</td>
			  <td align="center" nowrap>${cardTypeName}</td>
			  <td align="center" nowrap>${branchCode}-${fn:branch(branchCode) }</td>
			  <td nowrap>${makeUser}-${fn:branch(makeUser)}</td>
			  <td align="center" nowrap>${picStatusName}</td>
			  <td align="center" nowrap>${okFlagName}</td>
			  <td align="center" nowrap>
			  	<span class="redlink">
			  		<f:link href="/cardStyleFix/detail.do?makeCardReg.makeId=${makeId}">明细</f:link>
			  		<s:if test="picStatus==00 || picStatus==20">
			  		<f:pspan pid="makecardmgr_cardfix_download">
			  			<f:link href="/cardStyleFix/showDownload.do?makeId=${makeId}">下载</f:link>
			  		</f:pspan>
			  		<f:pspan pid="makecardmgr_cardfix_cancel">
			  			<f:link href="/cardStyleFix/showCancel.do?makeId=${makeId}">取消</f:link>
			  		</f:pspan>
			  		</s:if>
			  		<s:elseif test="picStatus==10">
			  		<f:pspan pid="makecardmgr_cardfix_pass">
			  			<a href="javascript:submitUrl('searchForm', '/cardStyleFix/pass.do?makeId=${makeId}', '确定要定版吗？');" />定版</a>
			  		</f:pspan>
			  		<f:pspan pid="makecardmgr_cardfix_cancel">
			  			<f:link href="/cardStyleFix/showCancel.do?makeId=${makeId}">取消</f:link>
			  		</f:pspan>
			  		<f:pspan pid="makecardmgr_cardfix_download">
			  			<f:link href="/cardStyleFix/showDownload.do?makeId=${makeId}">下载</f:link>
			  		</f:pspan>
			  		</s:elseif>
			  	</span>
			</tr>
			</s:iterator>
			</table>
			<f:paginate name="page"/>
		</div>

		<jsp:include flush="true" page="/layout/copyright.jsp"></jsp:include>
	</body>
</html>