<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>CRM客户管理|${customer.name}</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="/static/plugins/fontawesome/css/font-awesome.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="/static/dist/css/AdminLTE.min.css">

    <link rel="stylesheet" href="/static/dist/css/skins/skin-blue.min.css">
</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <!-- Main Header -->
    <%@include file="../include/mainHeader.jsp" %>
    <!-- Left side column. contains the logo and sidebar -->
    <jsp:include page="../include/leftSide.jsp">
        <jsp:param name="menu" value="customer"/>
    </jsp:include>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <section class="content-header">
            <h1>
                客户管理
                <small>${customer.name}</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="/sales"><i class="fa fa-list"></i> 销售机会列表</a></li>
                <li><a href="/customer"><i class="fa fa-list"></i> 客户列表</a></li>
                <li class="active">${customer.name}</li>
            </ol>
        </section>
        <section class="content">
            <div class="box box-primary">
                <div class="box-header">
                    <h3 class="box-title">
                        <c:choose>
                            <c:when test="${customer.type=='person'}">
                                <i class="fa fa-user"></i>
                            </c:when>
                            <c:otherwise>
                                <i class="fa fa-bank"></i>
                            </c:otherwise>
                        </c:choose>
                        ${customer.name}
                    </h3>
                    <div class="box-tools">
                        <button class="btn btn-danger btn-xs" id="openCus">
                            公开客户</button>
                        <button class="btn btn-info btn-xs" id="moveCus">
                            转移客户</button>
                    </div>
                </div>
                <div class="box-body">
                    <table class="table">
                        <tr>
                            <td style="width: 100px">联系电话：</td>
                            <td style="width: 200px">${customer.tel}</td>
                            <td style="width: 100px">微信：</td>
                            <td style="width: 200px">${customer.weixin}</td>
                            <td style="width: 100px">电子邮箱：</td>
                            <td>${customer.email}</td>
                        </tr>
                        <tr>
                            <td>等级：</td>
                            <td style="color: #ff7400">${customer.level}</td>
                            <td>地址：</td>
                            <td colspan="3">${customer.address}</td>
                        </tr>
                        <c:if test="${not empty customer.companyid}">
                            <tr>
                                <td>所属公司：</td>
                                <td colspan="5">${customer.company}</td>
                            </tr>
                        </c:if>
                        <c:if test="${not empty customerList}">
                            <tr>
                                <td>关联客户：</td>
                                <td colspan="5"> <c:forEach items="${customerList}" var="customer">
                                    <a href="/customer/${customer.id}">${customer.name}</a>
                                </c:forEach></td>
                            </tr>
                        </c:if>
                    </table>
                </div>
            </div>
            <%--销售机会--%>
            <div class="row">
                <div class="col-md-8">
                    <div class="box box-info">
                        <div class="box-header with-border">
                            <h3 class="box-title"><i class="fa fa-list"></i> 销售机会</h3>
                        </div>
                        <div class="box-body">
                            <h5>暂无记录</h5>
                        </div>
                    </div>
                </div>
                <%--电子名片--%>
                <div class="col-md-4">
                    <div class="box box-default collapsed-box">
                        <div class="box-header with-border">
                            <h3 class="box-title"><i class="fa fa-qrcode"></i> 电子名片</h3>
                            <div class="box-tools pull-right">
                                <button type="button" class="btn btn-box-tool"  data-widget="collapse"><i class="fa fa-plus"></i>
                                </button>
                            </div>
                        </div>

                        <div class="box-body" style="text-align: center">
                            <img src="/customer/qrcode/${customer.id}.png" alt="">
                        </div>
                    </div>
                </div>
                <%--待办事项--%>
                <div class="col-md-4 pull-right">
                    <div class="box box-default">
                        <div class="box-header with-border">
                            <h3 class="box-title"><i class="fa fa-calendar-check-o"></i> 待办事项</h3>
                        </div>
                        <div class="box-body">
                            <h5>暂无待办事项</h5>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</div>
<%--转移客户--%>
<div class="modal fade" id="moveModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">转移客户</h4>
            </div>
            <div class="modal-body">
                <form id="moveForm" method="post" action="/customer/move">
                    <input type="hidden" name="id" value="${customer.id}">
                    <div class="form-group">
                        <label>选择将要转到的员工姓名</label>
                        <select name="userid" class="form-control">
                            <c:forEach items="${userList}" var="user">
                                <option value="${user.id}">${user.realname}</option>
                            </c:forEach>
                        </select>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="moveBtn">转移</button>
            </div>
        </div>
    </div>
</div>

<!-- jQuery 2.2.3 -->
<script src="/static/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="/static/bootstrap/js/bootstrap.min.js"></script>
<!-- AdminLTE App -->
<script src="/static/dist/js/app.min.js"></script>
<script src="/static/plugins/moment/moment.js"></script>

<script>
    $(function () {

        //转移客户
        $("#moveCus").click(function () {
            $("#moveModal").modal({
                show:true,
                backdrop:"static",
                keyboard:false
            });
        });

        $("#moveBtn").click(function () {
            $("#moveForm").submit();
        });

        //公开客户
        $("#openCus").click(function () {
            if(confirm("确定要将客户公开吗？")){
                var id = ${customer.id};
                window.location.href="/customer/open/" + id;
            }
        });
    });
</script>
</body>
</html>

