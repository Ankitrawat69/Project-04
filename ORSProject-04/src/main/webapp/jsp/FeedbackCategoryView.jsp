<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.FeedbackCategoryCtl"%>
<%@page import="in.co.rays.proj4.bean.FeedbackCategoryBean"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Feedback Category</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
	<form action="<%=ORSView.FEEDBACK_CATEGORY_CTL%>" method="POST">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean"
			class="in.co.rays.proj4.bean.FeedbackCategoryBean"
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
				Feedback Category
			</h1>

			<div style="height: 15px; margin-bottom: 12px">
				<H3 align="center">
					<font color="green">
						<%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</H3>
				<H3 align="center">
					<font color="red">
						<%=ServletUtility.getErrorMessage(request)%>
					</font>
				</H3>
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
					<th align="left">Category Code<span style="color: red">*</span></th>
					<td>
						<input type="text" name="categoryCode"
							placeholder="Enter Category Code"
							value="<%=DataUtility.getStringData(bean.getCategoryCode())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("categoryCode", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Category Name<span style="color: red">*</span></th>
					<td>
						<input type="text" name="categoryName"
							placeholder="Enter Category Name"
							value="<%=DataUtility.getStringData(bean.getCategoryName())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("categoryName", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Description<span style="color: red">*</span></th>
					<td>
						<input type="text" name="description"
							placeholder="Enter Description"
							value="<%=DataUtility.getStringData(bean.getDescription())%>">
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("description", request)%>
						</font>
					</td>
				</tr>

				<tr>
					<th align="left">Category Status<span style="color: red">*</span></th>
					<td>
						<%
							HashMap<String, String> map = new HashMap<String, String>();
							map.put("Active", "Active");
							map.put("Inactive", "Inactive");

							String htmlList = HTMLUtility.getList(
									"categoryStatus",
									bean.getCategoryStatus(),
									map);
						%>
						<%=htmlList%>
					</td>
					<td style="position: fixed;">
						<font color="red">
							<%=ServletUtility.getErrorMessage("categoryStatus", request)%>
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
							value="<%=FeedbackCategoryCtl.OP_UPDATE%>">
						<input type="submit" name="operation"
							value="<%=FeedbackCategoryCtl.OP_CANCEL%>">
					<%
						} else {
					%>
					<td align="left" colspan="2">
						<input type="submit" name="operation"
							value="<%=FeedbackCategoryCtl.OP_SAVE%>">
						<input type="submit" name="operation"
							value="<%=FeedbackCategoryCtl.OP_RESET%>">
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