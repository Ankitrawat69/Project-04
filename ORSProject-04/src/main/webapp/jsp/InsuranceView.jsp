<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.InsuranceCtl"%>
<%@page import="in.co.rays.proj4.bean.InsuranceBean"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Insurance</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>
	<form action="<%=ORSView.INSURANCE_CTL%>" method="POST">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean"
			class="in.co.rays.proj4.bean.InsuranceBean"
			scope="request"></jsp:useBean>

		<div align="center">

			<h1 align="center" style="margin-bottom: -15; color: navy">

				<%
					if (bean != null && bean.getId() > 0) {
				%>
				Update
				<%
					} else {
				%>
				Add
				<%
					}
				%>
				Insurance
			</h1>

			<div style="height: 15px; margin-bottom: 12px">
				<h3 align="center">
					<font color="green">
						<%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</h3>
				<h3 align="center">
					<font color="red">
						<%=ServletUtility.getErrorMessage(request)%>
					</font>
				</h3>
			</div>

			<input type="hidden" name="id" value="<%=bean.getId()%>">
			<input type="hidden" name="createdBy"
				value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>">
			<input type="hidden" name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table>

				<tr>
					<th align="left">Insurance Code<span style="color: red">*</span></th>
					<td>
						<input type="text" name="insuranceCode"
							placeholder="Enter Insurance Code"
							value="<%=DataUtility.getStringData(bean.getInsuranceCode())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("insuranceCode", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Car Name<span style="color: red">*</span></th>
					<td>
						<input type="text" name="carName"
							placeholder="Enter Car Name"
							value="<%=DataUtility.getStringData(bean.getCarName())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("carName", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Provider Name<span style="color: red">*</span></th>
					<td>
						<input type="text" name="providerName"
							placeholder="Enter Provider Name"
							value="<%=DataUtility.getStringData(bean.getProviderName())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("providerName", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Expiry Date<span style="color: red">*</span></th>
					<td>
						<input type="text" id="udate" name="expiryDate"
							placeholder="Select Expiry Date"
							value="<%=DataUtility.getDateString(bean.getExpiryDate())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("expiryDate", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th></th>
					<td></td>
				</tr>

				<tr>
					<th></th>

					<%
						if (bean != null && bean.getId() > 0) {
					%>

					<td align="left" colspan="2">
						<input type="submit" name="operation"
							value="<%=InsuranceCtl.OP_UPDATE%>">
						<input type="submit" name="operation"
							value="<%=InsuranceCtl.OP_CANCEL%>">
					</td>

					<%
						} else {
					%>

					<td align="left" colspan="2">
						<input type="submit" name="operation"
							value="<%=InsuranceCtl.OP_SAVE%>">
						<input type="submit" name="operation"
							value="<%=InsuranceCtl.OP_RESET%>">
					</td>

					<%
						}
					%>

				</tr>

			</table>

		</div>

	</form>

	<%@ include file="Footer.jsp"%>

</body>
</html>