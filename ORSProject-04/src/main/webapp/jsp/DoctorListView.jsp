<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.bean.BaseBean"%>
<%@page import="in.co.rays.proj4.controller.DoctorListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.proj4.bean.DoctorBean"%>

<html>
<head>
<title>Doctor List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>
	<%@include file="Header.jsp"%>

	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.DoctorBean"
		scope="request"></jsp:useBean>

	<div align="center">
		<h1 align="center" style="margin-bottom: -15; color: navy;">
			Doctor List</h1>

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

		<form action="<%=ORSView.DOCTOR_LIST_CTL%>" method="post">

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

				List<DoctorBean> list = (List<DoctorBean>) ServletUtility.getList(request);
				List<String> expertiseList = (List<String>) request.getAttribute("expertiseList");

				Iterator<DoctorBean> it = list.iterator();

				if (list.size() != 0) {
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>">
			<input type="hidden" name="pageSize" value="<%=pageSize%>">

			<!-- Search Panel -->
			<table style="width: 100%">
				<tr>
					<td align="center">

						<label><b>Name :</b></label>
						<input type="text" name="name"
							placeholder="Enter Name"
							value="<%=ServletUtility.getParameter("name", request)%>">

						&emsp;

						<label><b>Mobile :</b></label>
						<input type="text" name="mobileNo"
							placeholder="Enter Mobile No"
							value="<%=ServletUtility.getParameter("mobileNo", request)%>">

						&emsp;

						<label><b>Expertise :</b></label>
				<select name="expertise">
    <option value="">--Select--</option>

<%
HashMap<String,String> map =
    (HashMap<String,String>) request.getAttribute("map");

if(map != null){
    for(String key : map.keySet()){
%>
        <option value="<%=key%>"
        <%= key.equals(bean.getExpertise()) ? "selected" : "" %>>
        <%=map.get(key)%>
        </option>
<%
    }
}
%>
</select>
				
						&emsp;

						<input type="submit" name="operation"
							value="<%=DoctorListCtl.OP_SEARCH%>">
						&nbsp;
						<input type="submit" name="operation"
							value="<%=DoctorListCtl.OP_RESET%>">
					</td>
				</tr>
			</table>

			<br>

			<!-- List Table -->
			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th width="5%"><input type="checkbox" id="selectall" /></th>
					<th width="5%">S.No</th>
					<th width="20%">Name</th>
					<th width="15%">DOB</th>
					<th width="20%">Mobile</th>
					<th width="20%">Expertise</th>
					<th width="5%">Edit</th>
				</tr>

				<%
					while (it.hasNext()) {
						bean = it.next();
				%>

				<tr>
					<td style="text-align: center;">
						<input type="checkbox" name="ids" class="case"
							value="<%=bean.getId()%>">
					</td>
					<td style="text-align: center;"><%=index++%></td>
					<td style="text-align: center;"><%=bean.getName()%></td>
					<td style="text-align: center;"><%=DataUtility.getDateString(bean.getDob())%></td>
					<td style="text-align: center;"><%=bean.getMobileNo()%></td>
					<td style="text-align: center;"><%=bean.getExpertise()%></td>
					<td style="text-align: center;">
						<a href="<%=ORSView.DOCTOR_CTL%>?id=<%=bean.getId()%>">
							Edit
						</a>
					</td>
				</tr>

				<%
					}
				%>
			</table>

			<!-- Pagination & Actions -->
			<table style="width: 100%">
				<tr>
					<td style="width: 25%">
						<input type="submit" name="operation"
							value="<%=DoctorListCtl.OP_PREVIOUS%>"
							<%=pageNo > 1 ? "" : "disabled"%>>
					</td>

					<td align="center" style="width: 25%">
						<input type="submit" name="operation"
							value="<%=DoctorListCtl.OP_NEW%>">
					</td>

					<td align="center" style="width: 25%">
						<input type="submit" name="operation"
							value="<%=DoctorListCtl.OP_DELETE%>">
					</td>

					<td style="width: 25%" align="right">
						<input type="submit" name="operation"
							value="<%=DoctorListCtl.OP_NEXT%>"
							<%=nextListSize != 0 ? "" : "disabled"%>>
					</td>
				</tr>
			</table>

			<%
				} else {
			%>

			<table>
				<tr>
					<td align="right">
						<input type="submit" name="operation"
							value="<%=DoctorListCtl.OP_BACK%>">
					</td>
				</tr>
			</table>

			<%
				}
			%>

		</form>
	</div>
</body>
</html>
