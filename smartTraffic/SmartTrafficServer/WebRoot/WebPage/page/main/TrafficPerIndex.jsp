<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>智慧出行-城市交通</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<link rel="shortcut icon" href="<%=basePath%>favicon.ico" type="image/x-icon" />
 <link rel="stylesheet" href="<%=basePath%>WebPage/css/trafficNews/trafficNews.css" type="text/css"></link>
  <link rel="stylesheet" href="<%=basePath%>WebPage/css/taxiInformation/taxiOverview.css" type="text/css"></link></head>
  <script type="text/javascript" src="<%=basePath%>WebPage/js/XMap/jquery-1.10.2.min.js"></script>
  <script type="text/javascript" src="WebPage/js/common/Operation.js"></script>
<script type="text/javascript" src="<%=basePath%>WebPage/js/taxi/taxiInfor.js"></script>
  
  <body>
 <div class="middle">
    <div class="main_left">
    <div class="main_left_one"><a class="main_left_select" onclick="SelectItem_main_left(this,'WebPage/page/main/TrafficPerformanceIndex.jsp')">交通指数</a></div>
    <div class="main_left_2" ><a class="main_left_select_no" onclick="SelectItem_main_left(this,'WebPage/page/main/TrafficInformation.jsp')">路况信息</a></div>
    <div class="main_left_2"><a  class="main_left_select_no" onclick="SelectItem_main_left(this,'WebPage/page/main/RoadVideo.jsp')" >道路视频</a></div>
     </div>
      <div class="main_right" >
     <iframe class="right" src="WebPage/page/main/TrafficPerformanceIndex.jsp" width="780px" height="690px" scrolling="no" frameborder="0"></iframe>
    </div>
   </div>
 
  </body>
</html>
