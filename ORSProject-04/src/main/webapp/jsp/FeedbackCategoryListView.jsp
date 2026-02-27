<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.controller.FeedbackCategoryListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.FeedbackCategoryBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>
<head>
<title>Feedback Category List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
	<%@include file="Header.jsp"%>

	<div align="center">

		<h1 align="center" style="margin-bottom: -15; color: navy;">
			Feedback Category List
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
			class="in.co.rays.proj4.bean.FeedbackCategoryBean"
			scope="request"></jsp:useBean>

		<form action="<%=ORSView.FEEDBACK_CATEGORY_LIST_CTL%>" method="post">

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextPageSize = DataUtility.getInt(
						request.getAttribute("nextListSize").toString());

				List<FeedbackCategoryBean> list =
						(List<FeedbackCategoryBean>) ServletUtility.getList(request);

				Iterator<FeedbackCategoryBean> it = list.iterator();

				if (list.size() != 0) {
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>">
			<input type="hidden" name="pageSize" value="<%=pageSize%>">

			<!-- Search Section -->
			<table style="width: 100%">
				<tr>
					<td align="center">

						<label><b>Category Code :</b></label>
						<input type="text" name="categoryCode"
							value="<%=DataUtility.getStringData(bean.getCategoryCode())%>">

						&emsp;

						<label><b>Category Name :</b></label>
						<input type="text" name="categoryName"
							value="<%=DataUtility.getStringData(bean.getCategoryName())%>">

						&emsp;

						<label><b>Status :</b></label>
						<input type="text" name="categoryStatus"
							value="<%=DataUtility.getStringData(bean.getCategoryStatus())%>">

						&emsp;

						<input type="submit" name="operation"
							value="<%=FeedbackCategoryListCtl.OP_SEARCH%>">

						<input type="submit" name="operation"
							value="<%=FeedbackCategoryListCtl.OP_RESET%>">

					</td>
				</tr>
			</table>

			<br>

			<!-- Table Section -->
			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th width="5%">
						<input type="checkbox" id="selectall" />
					</th>
					<th width="5%">S.No</th>
					<th width="15%">Category Code</th>
					<th width="20%">Category Name</th>
					<th width="35%">Description</th>
					<th width="10%">Status</th>
					<th width="10%">Edit</th>
				</tr>

				<%
					while (it.hasNext()) {
						bean = it.next();
				%>

				<tr>
					<td style="text-align: center;">
						<input type="checkbox" class="case"
							name="ids" value="<%=bean.getId()%>">
					</td>

					<td style="text-align: center;">
						<%=index++%>
					</td>

					<td style="text-align: center;">
						<%=bean.getCategoryCode()%>
					</td>

					<td style="text-align: center;">
						<%=bean.getCategoryName()%>
					</td>

					<td style="text-align: center;">
						<%=bean.getDescription()%>
					</td>

					<td style="text-align: center;">
						<%=bean.getCategoryStatus()%>
					</td>

					<td style="text-align: center;">
						<a href="<%=ORSView.FEEDBACK_CATEGORY_CTL%>?id=<%=bean.getId()%>">
							Edit
						</a>
					</td>
				</tr>

				<%
					}
				%>

			</table>

			<br>

			<!-- Pagination Buttons -->
			<table style="width: 100%">
				<tr>
					<td style="width: 25%">
						<input type="submit" name="operation"
							value="<%=FeedbackCategoryListCtl.OP_PREVIOUS%>"
							<%=pageNo > 1 ? "" : "disabled"%>>
					</td>

					<td align="center" style="width: 25%">
						<input type="submit" name="operation"
							value="<%=FeedbackCategoryListCtl.OP_NEW%>">
					</td>

					<td align="center" style="width: 25%">
						<input type="submit" name="operation"
							value="<%=FeedbackCategoryListCtl.OP_DELETE%>">
					</td>

					<td style="width: 25%" align="right">
						<input type="submit" name="operation"
							value="<%=FeedbackCategoryListCtl.OP_NEXT%>"
							<%=nextPageSize != 0 ? "" : "disabled"%>>
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
							value="<%=FeedbackCategoryListCtl.OP_BACK%>">
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