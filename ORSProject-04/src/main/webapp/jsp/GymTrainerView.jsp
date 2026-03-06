<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.GymTrainerCtl"%>
<%@page import="in.co.rays.proj4.bean.GymTrainerBean"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>

<html>
<head>
<title>Add Trainer</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
<form action="<%=ORSView.GYMTRAINER_CTL%>" method="POST">

	<%@ include file="Header.jsp"%>

	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.GymTrainerBean"
		scope="request"></jsp:useBean>

	<div align="center">

		<h1 style="color: navy;">
			<% if (bean != null && bean.getId() > 0) { %>
				Update
			<% } else { %>
				Add
			<% } %>
			Trainer
		</h1>
		<h3 style="color: green;">
			<%=ServletUtility.getSuccessMessage(request)%>
		</h3>

		<h3 style="color: red;">
			<%=ServletUtility.getErrorMessage(request)%>
		</h3>

		<input type="hidden" name="id" value="<%=bean.getId()%>">
		<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
		<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
		<input type="hidden" name="createdDatetime"
			value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
		<input type="hidden" name="modifiedDatetime"
			value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

		<table>

			<tr>
				<th align="left">Trainer Name <span style="color:red">*</span></th>
				<td>
					<input type="text" name="trainerName"
						placeholder="Enter Trainer Name"
						value="<%=DataUtility.getStringData(bean.getTrainerName())%>">
				</td>
				<td><font color="red">
					<%=ServletUtility.getErrorMessage("trainerName", request)%>
				</font></td>
			</tr>

			<tr>
				<th align="left">Specialization <span style="color:red">*</span></th>
				<td>

					<%
						HashMap<String,String> map =
							(HashMap<String,String>) request.getAttribute("specializationMap");

						String htmlList = HTMLUtility.getList(
							"specialization",
							bean.getSpecialization(),
							map
						);
					%>

					<%=htmlList%>

				</td>
				<td><font color="red">
					<%=ServletUtility.getErrorMessage("specialization", request)%>
				</font></td>
			</tr>
			<tr>
				<th align="left">Salary <span style="color:red">*</span></th>
				<td>
					<input type="text" name="salary"
						placeholder="Enter Salary"
						value="<%=DataUtility.getStringData(bean.getSalary())%>">
				</td>
				<td><font color="red">
					<%=ServletUtility.getErrorMessage("salary", request)%>
				</font></td>
			</tr>

			<tr><td></td><td></td></tr>

			<tr>
				<td></td>
				<td>
					<% if (bean != null && bean.getId() > 0) { %>
						<input type="submit" name="operation"
							value="<%=GymTrainerCtl.OP_UPDATE%>">
						<input type="submit" name="operation"
							value="<%=GymTrainerCtl.OP_CANCEL%>">
					<% } else { %>
						<input type="submit" name="operation"
							value="<%=GymTrainerCtl.OP_SAVE%>">
						<input type="submit" name="operation"
							value="<%=GymTrainerCtl.OP_RESET%>">
					<% } %>
				</td>
			</tr>
		</table>
	</div>
</form>

<%@ include file="Footer.jsp"%>

</body>
</html>