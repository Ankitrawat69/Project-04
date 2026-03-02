<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.controller.PassengerListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.PassengerBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>
<head>
<title>Passenger List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
	<%@include file="Header.jsp"%>

	<div align="center">

		<h1 align="center" style="margin-bottom: -15; color: navy;">
			Passenger List
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

		<jsp:useBean id="bean"
			class="in.co.rays.proj4.bean.PassengerBean"
			scope="request"></jsp:useBean>

		<form action="<%=ORSView.PASSENGER_LIST_CTL%>" method="post">

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextPageSize = DataUtility.getInt(
					request.getAttribute("nextListSize").toString());

				List<PassengerBean> list =
					(List<PassengerBean>) ServletUtility.getList(request);

				Iterator<PassengerBean> it = list.iterator();
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>">
			<input type="hidden" name="pageSize" value="<%=pageSize%>">

			<table style="width: 100%">
				<tr>
					<td align="center">
						<label><b>Name :</b></label>
						<input type="text" name="name"
							value="<%=DataUtility.getStringData(bean.getName())%>">

						&emsp;

						<label><b>Passport No :</b></label>
						<input type="text" name="passportNumber"
							value="<%=DataUtility.getStringData(bean.getPassportNumber())%>">

						&emsp;

						<input type="submit" name="operation"
							value="<%=PassengerListCtl.OP_SEARCH%>">

						<input type="submit" name="operation"
							value="<%=PassengerListCtl.OP_RESET%>">
					</td>
				</tr>
			</table>

			<br>

			<%
				if (list.size() != 0) {
			%>

			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th width="5%">
						<input type="checkbox" id="selectall" />
					</th>
					<th width="5%">S.No</th>
					<th width="30%">Name</th>
					<th width="20%">Age</th>
					<th width="30%">Passport Number</th>
					<th width="10%">Edit</th>
				</tr>

				<%
					while (it.hasNext()) {
						bean = it.next();
				%>

				<tr>
					<td align="center">
						<input type="checkbox" class="case"
							name="ids"
							value="<%=bean.getId()%>">
					</td>

					<td align="center"><%=index++%></td>

					<td align="center"
						style="text-transform: capitalize;">
						<%=bean.getName()%>
					</td>

					<td align="center">
						<%=bean.getAge()%>
					</td>

					<td align="center">
						<%=bean.getPassportNumber()%>
					</td>

					<td align="center">
						<a href="<%=ORSView.PASSENGER_CTL%>?id=<%=bean.getId()%>">
							Edit
						</a>
					</td>
				</tr>

				<%
					}
				%>

			</table>

			<br>

			<table style="width: 100%">
				<tr>
					<td style="width: 25%">
						<input type="submit" name="operation"
							value="<%=PassengerListCtl.OP_PREVIOUS%>"
							<%=pageNo > 1 ? "" : "disabled"%>>
					</td>

					<td align="center" style="width: 25%">
						<input type="submit" name="operation"
							value="<%=PassengerListCtl.OP_NEW%>">
					</td>

					<td align="center" style="width: 25%">
						<input type="submit" name="operation"
							value="<%=PassengerListCtl.OP_DELETE%>">
					</td>

					<td align="right" style="width: 25%">
						<input type="submit" name="operation"
							value="<%=PassengerListCtl.OP_NEXT%>"
							<%=nextPageSize != 0 ? "" : "disabled"%>>
					</td>
				</tr>
			</table>

			<%
				} else {
			%>

			<table>
				<tr>
					<td align="center">
						<input type="submit" name="operation"
							value="<%=PassengerListCtl.OP_BACK%>">
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