<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.DispatchCtl"%>
<%@page import="in.co.rays.proj4.bean.DispatchBean"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Dispatch</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>

<form action="<%=ORSView.DISPATCH_CTL%>" method="POST">

<%@ include file="Header.jsp"%>

<jsp:useBean id="bean" class="in.co.rays.proj4.bean.DispatchBean"
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
Dispatch

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
<th align="left">
Dispatch ID<span style="color:red">*</span>
</th>

<td>
<input type="text"
name="dispatchId"
placeholder="Enter Dispatch ID"
value="<%=DataUtility.getStringData(bean.getDispatchId())%>">
</td>

<td style="position: fixed;">
<font color="red">
<%=ServletUtility.getErrorMessage("dispatchId", request)%>
</font>
</td>

</tr>


<tr>

<th align="left">
Dispatch Date<span style="color:red">*</span>
</th>

<td>

<input type="text"
id="udate"
name="dispatchDate"
placeholder="Select Dispatch Date"
value="<%=DataUtility.getDateString(bean.getDispatchDate())%>">

</td>

<td style="position: fixed;">
<font color="red">
<%=ServletUtility.getErrorMessage("dispatchDate", request)%>
</font>
</td>

</tr>


<tr>

<th align="left">
Status<span style="color:red">*</span>
</th>

<td>

<%

HashMap<String,String> map = new HashMap<String,String>();

map.put("Pending","Pending");
map.put("Dispatched","Dispatched");
map.put("Delivered","Delivered");

String htmlList = HTMLUtility.getList("status",bean.getStatus(),map);

%>

<%=htmlList%>

</td>

<td style="position: fixed;">
<font color="red">
<%=ServletUtility.getErrorMessage("status", request)%>
</font>
</td>

</tr>


<tr>

<th align="left">
Courier Name<span style="color:red">*</span>
</th>

<td>

<input type="text"
name="courierName"
placeholder="Enter Courier Name"
value="<%=DataUtility.getStringData(bean.getCourierName())%>">

</td>

<td style="position: fixed;">
<font color="red">
<%=ServletUtility.getErrorMessage("courierName", request)%>
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

<input type="submit"
name="operation"
value="<%=DispatchCtl.OP_UPDATE%>">

<input type="submit"
name="operation"
value="<%=DispatchCtl.OP_CANCEL%>">

<%

} else {

%>

<td align="left" colspan="2">

<input type="submit"
name="operation"
value="<%=DispatchCtl.OP_SAVE%>">

<input type="submit"
name="operation"
value="<%=DispatchCtl.OP_RESET%>">

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