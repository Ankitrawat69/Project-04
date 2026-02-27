<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.controller.DoctorCtl"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Doctor</title>
<link rel="icon" type="image/png"
    href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>
<form action="<%=ORSView.DOCTOR_CTL%>" method="post">

<%@ include file="Header.jsp"%>

<jsp:useBean id="bean" class="in.co.rays.proj4.bean.DoctorBean"
    scope="request"></jsp:useBean>

<%
    HashMap<String, String> map =
        (HashMap<String, String>) request.getAttribute("map");
%>

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
Doctor
</h1>

<div style="height: 15px; margin-bottom: 12px">
<h3 align="center">
<font color="red">
<%=ServletUtility.getErrorMessage(request)%>
</font>
</h3>

<h3 align="center">
<font color="green">
<%=ServletUtility.getSuccessMessage(request)%>
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
<th align="left">Name<span style="color: red">*</span></th>
<td>
<input type="text" name="name"
    placeholder="Enter Doctor Name"
    value="<%=DataUtility.getStringData(bean.getName())%>">
</td>
<td style="position: fixed;">
<font color="red">
<%=ServletUtility.getErrorMessage("name", request)%>
</font>
</td>
</tr>

<tr>
<th align="left">Date of Birth<span style="color: red">*</span></th>
<td>
<input type="date" name="dob"
    value="<%=DataUtility.getStringData(
        bean.getDob() != null ?
        new java.text.SimpleDateFormat("yyyy-MM-dd").format(bean.getDob())
        : "")%>">

</td>
<td style="position: fixed;">
<font color="red">
<%=ServletUtility.getErrorMessage("dob", request)%>
</font>
</td>
</tr>

<tr>
<th align="left">Mobile No<span style="color: red">*</span></th>
<td>
<input type="text" name="mobileNo"
    placeholder="Enter Mobile Number"
    value="<%=DataUtility.getStringData(bean.getMobileNo())%>">
</td>
<td style="position: fixed;">
<font color="red">
<%=ServletUtility.getErrorMessage("mobileNo", request)%>
</font>
</td>
</tr>

<tr>
<th align="left">Expertise<span style="color: red">*</span></th>
<td>
<select name="expertise">
<option value="">--Select--</option>

<%
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
</td>
<td style="position: fixed;">
<font color="red">
<%=ServletUtility.getErrorMessage("expertise", request)%>
</font>
</td>
</tr>

<tr>
<th></th>
<%
if (bean != null && bean.getId() > 0) {
%>
<td align="left" colspan="2">
<input type="submit" name="operation"
    value="<%=DoctorCtl.OP_UPDATE%>">
<input type="submit" name="operation"
    value="<%=DoctorCtl.OP_CANCEL%>">
</td>
<%
} else {
%>
<td align="left" colspan="2">
<input type="submit" name="operation"
    value="<%=DoctorCtl.OP_SAVE%>">
<input type="submit" name="operation"
    value="<%=DoctorCtl.OP_RESET%>">
</td>
<%
}
%>
</tr>

</table>
</div>
</form>
</body>
</html>
