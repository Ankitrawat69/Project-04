<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.controller.HolidayListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.HolidayBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<html>
<head>
<title>Holiday List</title>

<link rel="icon" type="image/png"
href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

</head>

<body>

<%@include file="Header.jsp"%>

<div align="center">

<h1 align="center" style="margin-bottom:-15;color:navy">
Holiday List
</h1>

<div style="height:15px;margin-bottom:12px">

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

<form action="<%=ORSView.HOLIDAY_LIST_CTL%>" method="post">

<%

int pageNo = ServletUtility.getPageNo(request);
int pageSize = ServletUtility.getPageSize(request);

int index = ((pageNo-1)*pageSize)+1;

int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

List<HolidayBean> list = (List<HolidayBean>)ServletUtility.getList(request);

Iterator<HolidayBean> it = list.iterator();

if(list.size()!=0){

%>

<input type="hidden" name="pageNo" value="<%=pageNo%>">

<input type="hidden" name="pageSize" value="<%=pageSize%>">

<table style="width:100%">

<tr>

<td align="center">

<label><b>Holiday Code :</b></label>

<input type="text"
name="holidayCode"
placeholder="Enter Holiday Code"
value="<%=ServletUtility.getParameter("holidayCode",request)%>">

&nbsp;&nbsp;

<label><b>Holiday Name :</b></label>

<input type="text"
name="holidayName"
placeholder="Enter Holiday Name"
value="<%=ServletUtility.getParameter("holidayName",request)%>">

&nbsp;&nbsp;

<input type="submit"
name="operation"
value="<%=HolidayListCtl.OP_SEARCH%>">

<input type="submit"
name="operation"
value="<%=HolidayListCtl.OP_RESET%>">

</td>

</tr>

</table>

<br>

<table border="1" style="width:100%;border:groove">

<tr style="background-color:#e1e6f1e3">

<th width="5%">
<input type="checkbox" id="selectall"/>
</th>

<th width="5%">S.No</th>

<th width="15%">Holiday Code</th>

<th width="20%">Holiday Name</th>

<th width="15%">Holiday Type</th>

<th width="20%">Holiday Date</th>

<th width="10%">Edit</th>

</tr>

<%

while(it.hasNext()){

HolidayBean bean = it.next();

%>

<tr>

<td style="text-align:center">

<input type="checkbox"
class="case"
name="ids"
value="<%=bean.getId()%>">

</td>

<td style="text-align:center"><%=index++%></td>

<td style="text-align:center"><%=bean.getHolidayCode()%></td>

<td style="text-align:center"><%=bean.getHolidayName()%></td>

<td style="text-align:center"><%=bean.getHolidayType()%></td>

<%

SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

String date = sdf.format(bean.getHolidayDate());

%>

<td style="text-align:center"><%=date%></td>

<td style="text-align:center">

<a href="<%=ORSView.HOLIDAY_CTL%>?id=<%=bean.getId()%>">

Edit

</a>

</td>

</tr>

<%

}

%>

</table>

<table style="width:100%">

<tr>

<td style="width:25%">

<input type="submit"
name="operation"
value="<%=HolidayListCtl.OP_PREVIOUS%>"
<%=pageNo>1?"":"disabled"%>>

</td>

<td align="center" style="width:25%">

<input type="submit"
name="operation"
value="<%=HolidayListCtl.OP_NEW%>">

</td>

<td align="center" style="width:25%">

<input type="submit"
name="operation"
value="<%=HolidayListCtl.OP_DELETE%>">

</td>

<td align="right" style="width:25%">

<input type="submit"
name="operation"
value="<%=HolidayListCtl.OP_NEXT%>"
<%=(nextPageSize!=0)?"":"disabled"%>>

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