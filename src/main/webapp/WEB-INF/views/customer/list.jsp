<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>CRM客户管理</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="/static/plugins/fontawesome/css/font-awesome.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="/static/dist/css/AdminLTE.min.css">

    <link rel="stylesheet" href="/static/dist/css/skins/skin-blue.min.css">
    <link rel="stylesheet" href="/static/plugins/datatables/css/dataTables.bootstrap.min.css">
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
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                客户管理
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">

            <div class="box box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">客户列表</h3>
                    <div class="box-tools">
                        <button class="btn btn-xs btn-success" id="newBtn">
                            <i class="fa fa-plus"></i> 新增客户
                        </button>
                    </div>
                </div>
                <div class="box-body">
                    <table class="table" id="customerTable">
                        <thead>
                        <tr>
                            <th>客户类型</th>
                            <th>客户名称</th>
                            <th>电话</th>
                            <th>电子邮箱</th>
                            <th>等级</th>
                            <th>创建时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tboby></tboby>
                    </table>
                </div>
            </div>

        </section>
    </div>
</div>

<%--新增客户弹出框--%>
<div class="modal fade" id="newModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">新增客户</h4>
            </div>
            <div class="modal-body">
                <form id="newForm">
                    <div class="form-group">
                        <label>客户类型</label>
                        <div>
                            <label class="radio-inline">
                                <input type="radio" name="type" value="person" id="radioPerson" checked>个人
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="type" value="company" id="radioCompany">公司
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label>客户名称</label>
                        <input type="text" name="name" class="form-control">
                    </div>
                    <div class="form-group">
                        <label>电话</label>
                        <input type="text" name="tel" class="form-control">
                    </div>
                    <div class="form-group">
                        <label>微信</label>
                        <input type="text" name="weixin" class="form-control">
                    </div>
                    <div class="form-group">
                        <label>地址</label>
                        <input type="text" name="address" class="form-control">
                    </div>
                    <div class="form-group">
                        <label>电子邮箱</label>
                        <input type="text" name="email" class="form-control">
                    </div>

                    <div class="form-group">
                        <label>客户等级</label>
                        <select class="form-control" name="level">
                            <option value=""></option>
                            <option value="★">★</option>
                            <option value="★★">★★</option>
                            <option value="★★★">★★★</option>
                            <option value="★★★★">★★★★</option>
                            <option value="★★★★★">★★★★★</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>所属公司</label>
                        <select class="form-control" name="companyid">
                            <option value=""></option>
                            <c:forEach items="${companyList}" var="company">
                                <option value="${company.id}">${company.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="saveBtn">保存</button>
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
<script src="/static/plugins/datatables/js/jquery.dataTables.min.js"></script>
<script src="/static/plugins/datatables/js/dataTables.bootstrap.min.js"></script>
<script src="/static/plugins/moment/moment.js"></script>
<script src="/static/plugins/validate/jquery.validate.min.js"></script>

<script>
    $(function () {
        var dataTable = $("#customerTable").DataTable({
            serverSide: true,
            ordering: false,
            "autoWidth": false,
            ajax: "/customer/list/load",
            columns: [
                {
                    "data": function (row) {
                        if (row.type == "company") {
                            return "<i class='fa fa-compass'></i>";
                        }
                        return "<i class='fa fa-user'></i>";
                    }
                },
                {
                    "data": function (row) {
                        if (row.company) {
                            return row.name + "-" + row.company;
                        }
                        return row.name;
                    }
                },
                {"data": "tel"},
                {"data": "email"},
                {"data": "level"},
                {
                    "data": function (row) {
                        var timestamp = row.createtime;
                        var day = moment(timestamp).format("YYYY-MM-DD HH:mm");
                        return day;
                    }
                }
            ],
            "language": {
                "search": "请输入客户名称或电话:",
                "zeroRecords": "没有匹配的数据",
                "lengthMenu": "显示 _MENU_ 条数据",
                "info": "显示_START_ 到 _END_ 条数据,共 _TOTAL_ 条数据",
                "infoFiltered": "(从 _MAX_ 条数据筛选)",
                "loadingRecords": "加载中...",
                "processing": "处理中...",
                "paginate": {
                    "first": "首页",
                    "last": "末页",
                    "next": "下一页",
                    "previous": "上一页"
                }
            }
        });

        //新增客户信息
        $("#newForm").validate({
            errorClass:"text-danger",
            errorElement:"span",
            rules:{
                name:{
                    required:true
                },
                tel:{
                    required:true
                }
            },
            messages:{
                name:{
                    required:"请输入客户名称"
                },
                tel:{
                    required:"请输入电话"
                }
            },
            submitHandler:function(form){
                $.post("/customer/list/new",$(form).serialize()).done(function(data){
                    if(data=="success"){
                        $("#newModal").modal("hide");
                        dataTable.ajax.reload();
                    }
                }).fail(function () {
                    alert("服务器异常")
                });
            }
        });
        $("#newBtn").click(function(){
            $("#newForm")[0].reset();
            $("#newModal").modal({
                show:true,
                backdrop:'static',
                keyboard:false
            });
        });

        $("#saveBtn").click(function(){
            $("#newForm").submit();
        });

    });

</script>
</body>
</html>

