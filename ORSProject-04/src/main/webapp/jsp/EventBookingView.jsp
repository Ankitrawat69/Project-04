<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.bean.EventBookingBean"%>
<%@page import="in.co.rays.proj4.controller.EventBookingCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<html>
<head>
<title>Add Event Booking</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

	<form action="<%=ORSView.EVENTBOOKING_CTL%>" method="POST">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean"
			class="in.co.rays.proj4.bean.EventBookingBean"
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
				Event Booking
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
					<th align="left">Event Name<span style="color: red">*</span></th>
					<td>
						<input type="text" name="eventName"
							placeholder="Enter Event Name"
							value="<%=DataUtility.getStringData(bean.getEventName())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("eventName", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Booking Date<span style="color: red">*</span></th>
					<td>
						<input type="text" id="udate" name="bookingDate"
							placeholder="Select Booking Date"
							value="<%=DataUtility.getDateString(bean.getBookingDate())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("bookingDate", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Seats<span style="color: red">*</span></th>
					<td>
						<input type="text" name="seats"
							placeholder="Enter Number of Seats"
							value="<%=DataUtility.getStringData(bean.getSeats())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("seats", request)%>
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
							value="<%=EventBookingCtl.OP_UPDATE%>">
						<input type="submit" name="operation"
							value="<%=EventBookingCtl.OP_CANCEL%>">
					</td>

					<%
						} else {
					%>

					<td align="left" colspan="2">
						<input type="submit" name="operation"
							value="<%=EventBookingCtl.OP_SAVE%>">
						<input type="submit" name="operation"
							value="<%=EventBookingCtl.OP_RESET%>">
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