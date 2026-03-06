<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.SecurityStaffCtl"%>
<%@page import="in.co.rays.proj4.bean.SecurityStaffBean"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Security Staff</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

</head>

<body>

<form action="<%=ORSView.SECURITY_STAFF_CTL%>" method="POST">

<%@ include file="Header.jsp"%>

<jsp:useBean id="bean" class="in.co.rays.proj4.bean.SecurityStaffBean" scope="request"></jsp:useBean>

<div align="center">

<h1 align="center" style="margin-bottom:-15; color:navy">

<% if(bean != null && bean.getId() > 0){ %>

Update

<% } else { %>

Add

<% } %>

Security Staff

</h1>

<div style="height:15px; margin-bottom:12px">

<h3 align="center">
<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
</h3>

<h3 align="center">
<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
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

<th align="left">Staff Name<span style="color:red">*</span></th>

<td>
<input type="text" name="staffName"
placeholder="Enter Staff Name"
value="<%=DataUtility.getStringData(bean.getStaffName())%>">
</td>

<td style="position:fixed;">
<font color="red">
<%=ServletUtility.getErrorMessage("staffName", request)%>
</font>
</td>

</tr>

<tr>

<th align="left">Shift<span style="color:red">*</span></th>

<td>

<%
HashMap<String,String> map = new HashMap<String,String>();

map.put("Morning","Morning");
map.put("Evening","Evening");
map.put("Night","Night");

String htmlList = HTMLUtility.getList("shift", bean.getShift(), map);
%>

<%=htmlList%>

</td>

<td style="position:fixed;">
<font color="red">
<%=ServletUtility.getErrorMessage("shift", request)%>
</font>
</td>

</tr>

<tr>

<th align="left">Salary<span style="color:red">*</span></th>

<td>
<input type="text" name="salary"
placeholder="Enter Salary"
value="<%=DataUtility.getStringData(bean.getSalary())%>">
</td>

<td style="position:fixed;">
<font color="red">
<%=ServletUtility.getErrorMessage("salary", request)%>
</font>
</td>

</tr>

<tr>
<th></th>
<td></td>
</tr>

<tr>

<th></th>

<% if(bean != null && bean.getId() > 0){ %>

<td align="left" colspan="2">

<input type="submit" name="operation" value="<%=SecurityStaffCtl.OP_UPDATE%>">

<input type="submit" name="operation" value="<%=SecurityStaffCtl.OP_CANCEL%>">

<% } else { %>

<td align="left" colspan="2">

<input type="submit" name="operation" value="<%=SecurityStaffCtl.OP_SAVE%>">

<input type="submit" name="operation" value="<%=SecurityStaffCtl.OP_RESET%>">

<% } %>

</td>

</tr>

</table>

</div>

</form>

<%@ include file="Footer.jsp"%>

</body>
</html>