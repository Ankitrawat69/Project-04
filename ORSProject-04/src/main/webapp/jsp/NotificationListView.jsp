<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.controller.NotificationListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.NotificationBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>
<head>
<title>Notification List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>
	<%@include file="Header.jsp"%>

	<div align="center">

		<h1 align="center" style="margin-bottom: -15; color: navy;">
			Notification List
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

		<form action="<%=ORSView.NOTIFICATION_LIST_CTL%>" method="post">

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

				@SuppressWarnings("unchecked")
				List<NotificationBean> list = (List<NotificationBean>) ServletUtility.getList(request);
				Iterator<NotificationBean> it = list.iterator();

				if (list.size() != 0) {
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>">
			<input type="hidden" name="pageSize" value="<%=pageSize%>">

			<table style="width: 100%">
				<tr>
					<td align="center">

						<label><b>Notification Code :</b></label>
						<input type="text" name="notificationCode"
							placeholder="Enter Code"
							value="<%=ServletUtility.getParameter("notificationCode", request)%>">&emsp;

						<label><b>Sent To :</b></label>
						<input type="text" name="sentTo"
							placeholder="Enter Recipient"
							value="<%=ServletUtility.getParameter("sentTo", request)%>">&emsp;

						<label><b>Status :</b></label>
						<input type="text" name="notificationStatus"
							placeholder="Enter Status"
							value="<%=ServletUtility.getParameter("notificationStatus", request)%>">&emsp;

						<input type="submit" name="operation"
							value="<%=NotificationListCtl.OP_SEARCH%>">
						<input type="submit" name="operation"
							value="<%=NotificationListCtl.OP_RESET%>">

					</td>
				</tr>
			</table>

			<br>

			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th width="5%"><input type="checkbox" id="selectall" /></th>
					<th width="5%">S.No</th>
					<th width="15%">Notification Code</th>
					<th width="25%">Message</th>
					<th width="15%">Sent To</th>
					<th width="10%">Sent Date</th>
					<th width="10%">Status</th>
					<th width="5%">Edit</th>
				</tr>

				<%
					while (it.hasNext()) {
						NotificationBean bean = it.next();
				%>

				<tr>
					<td style="text-align: center;">
						<input type="checkbox" class="case" name="ids"
							value="<%=bean.getId()%>">
					</td>

					<td style="text-align: center;"><%=index++%></td>

					<td style="text-align: center;">
						<%=bean.getNotificationCode()%>
					</td>

					<td style="text-align: center;">
						<%=bean.getMessage()%>
					</td>

					<td style="text-align: center;">
						<%=bean.getSentTo()%>
					</td>

					<%
						SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
						String date = "";
						if (bean.getSentTime() != null) {
							date = sdf.format(bean.getSentTime());
						}
					%>

					<td style="text-align: center;"><%=date%></td>

					<td style="text-align: center;">
						<%=bean.getNotificationStatus()%>
					</td>

					<td style="text-align: center;">
						<a href="<%=ORSView.NOTIFICATION_CTL%>?id=<%=bean.getId()%>">Edit</a>
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
							value="<%=NotificationListCtl.OP_PREVIOUS%>"
							<%=pageNo > 1 ? "" : "disabled"%>>
					</td>

					<td align="center" style="width: 25%">
						<input type="submit" name="operation"
							value="<%=NotificationListCtl.OP_NEW%>">
					</td>

					<td align="center" style="width: 25%">
						<input type="submit" name="operation"
							value="<%=NotificationListCtl.OP_DELETE%>">
					</td>

					<td style="width: 25%" align="right">
						<input type="submit" name="operation"
							value="<%=NotificationListCtl.OP_NEXT%>"
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
							value="<%=NotificationListCtl.OP_BACK%>">
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