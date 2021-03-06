<%--
  Created by IntelliJ IDEA.
  User: TWQ
  Date: 2016/7/28
  Time: 16:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>我发布的信息</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link href="../css/common/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
    <!-- jQuery 2.1.4 -->
    <script src="../js/common/jQuery-2.1.4.min.js"></script>
    <script src="../js/bootpaging.js"></script>
    <!-- 页面js -->
    <script src="../js/commenting/myxinxi_list.js" type="text/javascript"></script>
    <style type="text/css">
        .clickword{
            color:rgb(0,104,177);
            cursor: pointer;
        }
    </style>
</head>
<body>
<input type="hidden" value="<%=basePath%>" id="basePath"/>
    <section class="content-header">
        <h1>
            我发布的信息
            <!-- <small>advanced tables</small> -->
        </h1>
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-commenting"></i> 信息发布</a></li>
            <li><a href="#">我发布的信息</a></li>
        </ol>
    </section>

    <section class="content">
        <div class="box">
        <div class="row">
            <div class="col-sm-12">
                <div id="example1_filter" class="dataTables_filter" style="text-align:left;margin-top: 15px;margin-left:10px">
                    <div class="btn-group" style="float: left;width: 90px;">
                        <button type="button" class="btn btn-default dropdown-toggle"
                                data-toggle="dropdown" style="width:100%;height:32px;">
                            <span id="lbnamespan">全部类目</span> <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu " role="menu">
                            <li><a onclick="setlbtype(0)">全部</a></li>
                            <li><a onclick="setlbtype(1)">请假</a></li>
                            <li><a onclick="setlbtype(2)">加班</a></li>
                            <li><a onclick="setlbtype(3)">出差</a></li>
                        </ul>
                    </div>
                    <div class="btn-group" style="float: left;width: 90px;margin-left:10px;">
                        <button type="button" class="btn btn-default dropdown-toggle"
                                data-toggle="dropdown" style="width:100%;height:32px;">
                            <span id="">全部状态</span> <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu " role="menu">
                            <li><a onclick="setlbtype(0)">全部</a></li>
                            <li><a onclick="setlbtype(1)">请假</a></li>
                            <li><a onclick="setlbtype(2)">加班</a></li>
                            <li><a onclick="setlbtype(3)">出差</a></li>
                        </ul>
                    </div>
                        <div style="heigth:100%;width:400px;float: left;margin-left:20px;border-radius:4px;border:solid 1px #cccccc;margin-left: 20px;">
                            <input type="text" class="form-control" style="height:100%;padding:5px;width:330px;border: 0;float: left;"placeholder="请输入申请人姓名" id="listkey"/>
                            <div style="float: left;height:30px;width:28px;">
                                <i class="fa fa-close" style="cursor:pointer;float: left;height:100%;width:100%;line-height: 30px;text-align: center;display: none;" id="clearbtn"></i>
                            </div>
                            <i class="fa fa-search" style="cursor:pointer;float: left;height:30px;width:28px;line-height: 30px;text-align: center;" onclick="showTabledata('LeaveAndOvertimefinish',1)"></i>
                        </div>
                    <script>
                        $(document).ready(function(){
                            $("#clearbtn").bind("click",function(){
                                $("#clearbtn").hide();
                                $("#listkey").val('');
                            })
                            $("#listkey").bind('input propertychange', function() {
                                if($('#listkey').val()!=''&&$('#listkey').val()!=null){
                                    $("#clearbtn").show();
                                }
                            });
                        })
                    </script>
                        <button type="button" class="btn btn-primary"
                                 style="height:32px;float:right;margin-right:10px;">
                            发布信息
                        </button>
                </div>
            </div>
        </div>
        <div id="generalTabContent" class="t  ab-content" style="margin-top:20px;min-height:600px;">
            <!-- 角色列表 -->
            <div id="tab-internet" class="tab-pane fade in active" style="position: relative">
                <table class="table table-hover col-xs-12" id="kqtable" style="border-top:none">
                    <tr style="background-color: rgb(240,245,248);">
                        <th>编号</th>
                        <th>标题</th>
                        <th>类型</th>
                        <th>通知对象</th>
                        <th>发布人</th>
                        <th>发布时间</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    <tr>
                        <td>01</td>
                        <td>嘉兴</td>
                        <td>15000000000</td>
                        <td>PKaqu</td>
                        <td>没什么内容，就是突然想反馈一下</td>
                        <td><%=new Date()%></td>
                        <td><span style="color: rgb(89,191,0)">已发布</span></td>
                        <td><span class="clickword" onclick="window.location.href='<%=basePath%>xinxi_detal'">查看</span></td>
                    </tr>
                </table>
            </div>

            <div class='bootpagediv' style='width:100%;margin-right:20px;'>
                <span style="float: left;margin-left:10px;line-height: 20px;color:#666;" id="pagedetal">
                </span>
                <nav style='float:right;display:none' id='pageshow'>
                    <ul class="pagination" id='pagination'>
                    </ul>
                </nav>
            </div>
            <div id="nulltablediv" style="float: left;width:100%;text-align: center;margin-top:170px;display: none;color: rgb(215,215,215);">
                <i class="fa fa-newspaper-o" style="font-size: 70px;"></i><br><span>暂无任何信息</span>
            </div>
        </div>
        </div>
    </section>
    <input type="hidden" id="infoId"/>
<!-- 删除提示模态框（Modal） -->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="margin-top:300px;">
        <div class="modal-content" style="width: 400px;">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">
                    提示
                </h4>
            </div>
            <div class="modal-body" style="line-height: 20px;">
                <div style="background-color: rgb(255,168,0);height:20px;width:20px;border-radius:10px;text-align: center; color:white;float:left;">!</div>
                &nbsp;您确定要删除么？
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="delbtn">
                    确定
                </button>
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">取消
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div><!-- /.modal -->
</body>
</html>
