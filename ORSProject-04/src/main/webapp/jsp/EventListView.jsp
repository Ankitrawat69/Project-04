<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.controller.EventListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.EventBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>
<head>
<title>Event List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

<%@include file="Header.jsp"%>

<div align="center">

	<h1 align="center" style="margin-bottom: -15; color: navy;">Event List</h1>

	<div style="height: 15px; margin-bottom: 12px">
		<h3>
			<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
		</h3>
		<h3>
			<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
		</h3>
	</div>

	<form action="<%=ORSView.EVENT_LIST_CTL%>" method="post">

	<%
		int pageNo = ServletUtility.getPageNo(request);
		int pageSize = ServletUtility.getPageSize(request);
		int index = ((pageNo - 1) * pageSize) + 1;
		int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

		@SuppressWarnings("unchecked")
		List<EventBean> list = (List<EventBean>) ServletUtility.getList(request);
		Iterator<EventBean> it = list.iterator();

		if (list.size() != 0) {
	%>

	<input type="hidden" name="pageNo" value="<%=pageNo%>">
	<input type="hidden" name="pageSize" value="<%=pageSize%>">

	<table style="width: 100%">
		<tr>
			<td align="center">

				<label><b>Event Code :</b></label>
				<input type="text" name="eventCode"
					placeholder="Enter Event Code"
					value="<%=ServletUtility.getParameter("eventCode", request)%>">

				&emsp;

				<label><b>Event Name :</b></label>
				<input type="text" name="eventName"
					placeholder="Enter Event Name"
					value="<%=ServletUtility.getParameter("eventName", request)%>">

				&emsp;

				<label><b>Organizer :</b></label>
				<input type="text" name="organizer"
					placeholder="Enter Organizer"
					value="<%=ServletUtility.getParameter("organizer", request)%>">

				&emsp;

				<input type="submit" name="operation"
					value="<%=EventListCtl.OP_SEARCH%>">

				&nbsp;

				<input type="submit" name="operation"
					value="<%=EventListCtl.OP_RESET%>">

			</td>
		</tr>
	</table>

	<br>

	<table border="1" style="width: 100%; border: groove;">

		<tr style="background-color: #e1e6f1e3;">
			<th width="5%"><input type="checkbox" id="selectall" /></th>
			<th width="5%">S.No</th>
			<th width="15%">Event Code</th>
			<th width="25%">Event Name</th>
			<th width="20%">Organizer</th>
			<th width="15%">Event Date</th>
			<th width="10%">Edit</th>
		</tr>

		<%
			while (it.hasNext()) {
				EventBean bean = it.next();
		%>

		<tr>
			<td style="text-align: center;">
				<input type="checkbox" class="case" name="ids"
					value="<%=bean.getId()%>">
			</td>

			<td style="text-align: center;"><%=index++%></td>

			<td style="text-align: center;"><%=bean.getEventCode()%></td>

			<td style="text-align: center; text-transform: capitalize;">
				<%=bean.getEventName()%>
			</td>

			<td style="text-align: center; text-transform: capitalize;">
				<%=bean.getOrganizer()%>
			</td>

			<%
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				String date = sdf.format(bean.getEventDate());
			%>

			<td style="text-align: center;"><%=date%></td>

			<td style="text-align: center;">
				<a href="<%=ORSView.EVENT_CTL%>?id=<%=bean.getId()%>">Edit</a>
			</td>
		</tr>

		<%
			}
		%>

	</table>

	<table style="width: 100%">
		<tr>
			<td style="width: 25%">
				<input type="submit" name="operation"
					value="<%=EventListCtl.OP_PREVIOUS%>"
					<%=pageNo > 1 ? "" : "disabled"%>>
			</td>

			<td align="center" style="width: 25%">
				<input type="submit" name="operation"
					value="<%=EventListCtl.OP_NEW%>">
			</td>

			<td align="center" style="width: 25%">
				<input type="submit" name="operation"
					value="<%=EventListCtl.OP_DELETE%>">
			</td>

			<td style="width: 25%" align="right">
				<input type="submit" name="operation"
					value="<%=EventListCtl.OP_NEXT%>"
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
					value="<%=EventListCtl.OP_BACK%>">
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