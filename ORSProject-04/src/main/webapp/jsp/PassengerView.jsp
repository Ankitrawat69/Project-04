<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.bean.PassengerBean"%>
<%@page import="in.co.rays.proj4.controller.PassengerCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Passenger</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
	<form action="<%=ORSView.PASSENGER_CTL%>" method="POST">
		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean"
			class="in.co.rays.proj4.bean.PassengerBean"
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
				Passenger
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
					<th align="left">Name<span style="color: red">*</span></th>
					<td>
						<input type="text" name="name"
							placeholder="Enter Name"
							value="<%=DataUtility.getStringData(bean.getName())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("name", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Age<span style="color: red">*</span></th>
					<td>
						<input type="text" name="age"
							placeholder="Enter Age"
							value="<%=DataUtility.getStringData(String.valueOf(bean.getAge() == null ? "" : bean.getAge()))%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("age", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Passport Number<span style="color: red">*</span></th>
					<td>
						<input type="text" name="passportNumber"
							placeholder="Enter Passport Number"
							value="<%=DataUtility.getStringData(bean.getPassportNumber())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("passportNumber", request)%>
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
							value="<%=PassengerCtl.OP_UPDATE%>">
						<input type="submit" name="operation"
							value="<%=PassengerCtl.OP_CANCEL%>">
					<%
						} else {
					%>
					<td align="left" colspan="2">
						<input type="submit" name="operation"
							value="<%=PassengerCtl.OP_SAVE%>">
						<input type="submit" name="operation"
							value="<%=PassengerCtl.OP_RESET%>">
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