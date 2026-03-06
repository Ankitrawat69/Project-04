<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.controller.GymTrainerListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.GymTrainerBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<html>
<head>
<title>Trainer List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
	<%@include file="Header.jsp"%>

	<div align="center">
		<h1 align="center" style="margin-bottom: -15; color: navy;">
			Trainer List
		</h1>

		<div style="height: 15px; margin-bottom: 12px">
			<h3>
				<font color="red">
					<%=ServletUtility.getErrorMessage(request)%>
				</font>
			</h3>
			<h3>
				<font color="green">
					<%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</h3>
		</div>

		<form action="<%=ORSView.GYMTRAINER_LIST_CTL%>" method="post">

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

				@SuppressWarnings("unchecked")
				List<GymTrainerBean> list = (List<GymTrainerBean>) ServletUtility.getList(request);
				Iterator<GymTrainerBean> it = list.iterator();

				if (list.size() != 0) {
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>">
			<input type="hidden" name="pageSize" value="<%=pageSize%>">

			<!-- 🔎 Search Panel -->
			<table style="width: 100%">
				<tr>
					<td align="center">
						<label><b>Trainer Name :</b></label>
						<input type="text" name="trainerName"
							placeholder="Enter Trainer Name"
							value="<%=ServletUtility.getParameter("trainerName", request)%>">&emsp;

						<label><b>Specialization :</b></label>
						<input type="text" name="specialization"
							placeholder="Enter Specialization"
							value="<%=ServletUtility.getParameter("specialization", request)%>">&emsp;

						<input type="submit" name="operation"
							value="<%=GymTrainerListCtl.OP_SEARCH%>">&nbsp;

						<input type="submit" name="operation"
							value="<%=GymTrainerListCtl.OP_RESET%>">
					</td>
				</tr>
			</table>

			<br>

			<!-- 📋 List Table -->
			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th width="5%"><input type="checkbox" id="selectall" /></th>
					<th width="10%">S.No</th>
					<th width="25%">Trainer Name</th>
					<th width="25%">Specialization</th>
					<th width="20%">Salary</th>
					<th width="15%">Edit</th>
				</tr>

				<%
					while (it.hasNext()) {
						GymTrainerBean bean = it.next();
				%>
				<tr>
					<td style="text-align: center;">
						<input type="checkbox" class="case"
							name="ids" value="<%=bean.getId()%>">
					</td>

					<td style="text-align: center;"><%=index++%></td>

					<td style="text-align: center; text-transform: capitalize;">
						<%=bean.getTrainerName()%>
					</td>

					<td style="text-align: center; text-transform: capitalize;">
						<%=bean.getSpecialization()%>
					</td>

					<td style="text-align: center;">
						<%=bean.getSalary()%>
					</td>

					<td style="text-align: center;">
						<a href="<%=ORSView.GYMTRAINER_CTL%>?id=<%=bean.getId()%>">
							Edit
						</a>
					</td>
				</tr>
				<%
					}
				%>
			</table>

			<!-- 🔄 Pagination Buttons -->
			<table style="width: 100%">
				<tr>
					<td style="width: 25%">
						<input type="submit" name="operation"
							value="<%=GymTrainerListCtl.OP_PREVIOUS%>"
							<%=pageNo > 1 ? "" : "disabled"%>>
					</td>

					<td align="center" style="width: 25%">
						<input type="submit" name="operation"
							value="<%=GymTrainerListCtl.OP_NEW%>">
					</td>

					<td align="center" style="width: 25%">
						<input type="submit" name="operation"
							value="<%=GymTrainerListCtl.OP_DELETE%>">
					</td>

					<td style="width: 25%" align="right">
						<input type="submit" name="operation"
							value="<%=GymTrainerListCtl.OP_NEXT%>"
							<%=(nextPageSize != 0) ? "" : "disabled"%>>
					</td>
				</tr>
			</table>

			<%
				}
				if (list.size() == 0) {
			%>
			<table>
				<tr>
					<td align="right">
						<input type="submit" name="operation"
							value="<%=GymTrainerListCtl.OP_BACK%>">
					</td>
				</tr>
			</table>
			<%
				}
			%>

		</form>
	</div>

	<%@ include file="Footer.jsp"%>
</body>
</html>