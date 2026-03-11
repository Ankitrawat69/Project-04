<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.HolidayCtl"%>
<%@page import="in.co.rays.proj4.bean.HolidayBean"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<title>Add Holiday</title>

<link rel="icon" type="image/png"
href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

</head>

<body>

<form action="<%=ORSView.HOLIDAY_CTL%>" method="POST">

<%@ include file="Header.jsp"%>

<jsp:useBean id="bean"
class="in.co.rays.proj4.bean.HolidayBean"
scope="request"></jsp:useBean>

<div align="center">

<h1 align="center" style="margin-bottom:-15; color:navy">

<%
if(bean!=null && bean.getId()>0){
%>
Update
<%
}else{
%>
Add
<%
}
%>

Holiday

</h1>

<div style="height:15px;margin-bottom:12px">

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

<th align="left">Holiday Code<span style="color:red">*</span></th>

<td>

<input type="text"
name="holidayCode"
placeholder="Enter Holiday Code"
value="<%=DataUtility.getStringData(bean.getHolidayCode())%>">

</td>

<td style="position:fixed">

<font color="red">

<%=ServletUtility.getErrorMessage("holidayCode",request)%>

</font>

</td>

</tr>


<tr>

<th align="left">Holiday Name<span style="color:red">*</span></th>

<td>

<input type="text"
name="holidayName"
placeholder="Enter Holiday Name"
value="<%=DataUtility.getStringData(bean.getHolidayName())%>">

</td>

<td style="position:fixed">

<font color="red">

<%=ServletUtility.getErrorMessage("holidayName",request)%>

</font>

</td>

</tr>


<tr>

<th align="left">Holiday Date<span style="color:red">*</span></th>

<td>

<input type="text"
id="udate"
name="holidayDate"
placeholder="Select Holiday Date"
value="<%=DataUtility.getDateString(bean.getHolidayDate())%>">

</td>

<td style="position:fixed">

<font color="red">

<%=ServletUtility.getErrorMessage("holidayDate",request)%>

</font>

</td>

</tr>


<tr>

<th align="left">Holiday Type<span style="color:red">*</span></th>

<td>

<%

HashMap map = new HashMap();

map.put("National","National");
map.put("Festival","Festival");
map.put("Optional","Optional");
map.put("Company","Company");

String htmlList = HTMLUtility.getList("holidayType",bean.getHolidayType(),map);

%>

<%=htmlList%>

</td>

<td style="position:fixed">

<font color="red">

<%=ServletUtility.getErrorMessage("holidayType",request)%>

</font>

</td>

</tr>


<tr>

<th></th>

<%

if(bean!=null && bean.getId()>0){

%>

<td>

<input type="submit"
name="operation"
value="<%=HolidayCtl.OP_UPDATE%>">

<input type="submit"
name="operation"
value="<%=HolidayCtl.OP_CANCEL%>">

</td>

<%

}else{

%>

<td>

<input type="submit"
name="operation"
value="<%=HolidayCtl.OP_SAVE%>">

<input type="submit"
name="operation"
value="<%=HolidayCtl.OP_RESET%>">

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