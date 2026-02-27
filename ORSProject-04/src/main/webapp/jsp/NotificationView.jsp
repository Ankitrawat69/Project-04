<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.NotificationCtl"%>
<%@page import="in.co.rays.proj4.bean.NotificationBean"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<html>
<head>
<title>Add Notification</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

<form action="<%=ORSView.NOTIFICATION_CTL%>" method="POST">

	<%@ include file="Header.jsp"%>

	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.NotificationBean"
		scope="request"></jsp:useBean>

	<div align="center">

		<h1 align="center" style="margin-bottom: -15; color: navy">
			<%
				if (bean != null && bean.getId() > 0) {
			%>Update<%
				} else {
			%>Add<%
				}
			%>
			Notification
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
		<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
		<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
		<input type="hidden" name="createdDatetime"
			value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
		<input type="hidden" name="modifiedDatetime"
			value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

		<table>

			<tr>
				<th align="left">Notification Code<span style="color: red">*</span></th>
				<td>
					<input type="text" name="notificationCode"
						placeholder="Enter Notification Code"
						value="<%=DataUtility.getStringData(bean.getNotificationCode())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("notificationCode", request)%>
					</font>
				</td>
			</tr>

			<tr>
				<th align="left">Message<span style="color: red">*</span></th>
				<td>
					<textarea name="message" rows="3" cols="22"
						placeholder="Enter Message"><%=DataUtility.getStringData(bean.getMessage())%></textarea>
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("message", request)%>
					</font>
				</td>
			</tr>

			<tr>
				<th align="left">Sent To<span style="color: red">*</span></th>
				<td>
					<input type="text" name="sentTo"
						placeholder="Enter Recipient"
						value="<%=DataUtility.getStringData(bean.getSentTo())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("sentTo", request)%>
					</font>
				</td>
			</tr>

			<tr>
				<th align="left">Sent Date<span style="color: red">*</span></th>
				<td>
					<input type="text" id="udate" name="sentTime"
						placeholder="Select Sent Date"
						value="<%=DataUtility.getDateString(bean.getSentTime())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("sentTime", request)%>
					</font>
				</td>
			</tr>

			<tr>
				<th align="left">Notification Status<span style="color: red">*</span></th>
				<td>
					<input type="text" name="notificationStatus"
						placeholder="Enter Status (Sent/Pending/Failed)"
						value="<%=DataUtility.getStringData(bean.getNotificationStatus())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("notificationStatus", request)%>
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
						value="<%=NotificationCtl.OP_UPDATE%>">
					<input type="submit" name="operation"
						value="<%=NotificationCtl.OP_CANCEL%>">
				</td>

				<%
					} else {
				%>

				<td align="left" colspan="2">
					<input type="submit" name="operation"
						value="<%=NotificationCtl.OP_SAVE%>">
					<input type="submit" name="operation"
						value="<%=NotificationCtl.OP_RESET%>">
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