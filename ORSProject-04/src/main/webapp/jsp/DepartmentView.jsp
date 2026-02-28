<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.bean.DepartmentBean"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.controller.DepartmentCtl"%>
<html>
<head>
<title>Add Department</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
	<form action="<%=ORSView.DEPARTMENT_CTL%>" method="POST">
		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.DepartmentBean"
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
				Department
			</h1>

			<div style="height: 15px; margin-bottom: 12px">
				<H3 align="center">
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</H3>
				<H3 align="center">
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font>
				</H3>
			</div>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table>

				<tr>
					<th align="left">Department Code<span style="color: red">*</span></th>
					<td><input type="text" name="departmentCode" maxlength="7"
						pattern="369[0-9]{4}"
						title="Department Code must start with 369 and be exactly 7 digits"
						placeholder="Enter Department Code"
						value="<%=DataUtility.getStringData(bean.getDepartmentCode())%>">
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("departmentCode", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Department Name<span style="color: red">*</span></th>
					<td><input type="text" name="departmentName"
						placeholder="Enter Department Name"
						value="<%=DataUtility.getStringData(bean.getDepartmentName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("departmentName", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Department Head<span style="color: red">*</span></th>
					<td><input type="text" name="departmentHead"
						placeholder="Enter Department Head"
						value="<%=DataUtility.getStringData(bean.getDepartmentHead())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("departmentHead", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Location<span style="color: red">*</span></th>
					<td><input type="text" name="location"
						placeholder="Enter Location"
						value="<%=DataUtility.getStringData(bean.getLocation())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("location", request)%>
					</font></td>
				</tr>

				<tr>
					<th align="left">Status<span style="color: red">*</span></th>
					<td><input type="text" name="status"
						placeholder="Enter Status (Active/Inactive)"
						value="<%=DataUtility.getStringData(bean.getStatus())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("status", request)%>
					</font></td>
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
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=DepartmentCtl.OP_UPDATE%>"> <input
						type="submit" name="operation"
						value="<%=DepartmentCtl.OP_CANCEL%>"> <%
 	} else {
 %>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=DepartmentCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=DepartmentCtl.OP_RESET%>">
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