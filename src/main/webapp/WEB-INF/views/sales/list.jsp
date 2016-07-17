<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>CRM销售机会</title>
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
        <jsp:param name="menu" value="sales"/>
    </jsp:include>


    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                销售机会
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">

            <div class="box box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">销售列表</h3>
                    <div class="box-tools">
                        <button class="btn btn-xs btn-success" id="newBtn">
                            <i class="fa fa-plus"></i> 新增机会
                        </button>
                    </div>
                </div>
                <div class="box-body">
                    <table class="table" id="salesTable">
                        <thead>
                        <tr>
                            <th>机会名称</th>
                            <th>关联客户</th>
                            <th>销售价值</th>
                            <th>当前进度</th>
                            <th>最后跟进时间</th>
                            <th>所属员工</th>
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
<!-- ./wrapper -->
<%--新增销售机会弹出框--%>
<div class="modal fade" id="newModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">新增销售机会</h4>
            </div>
            <div class="modal-body">
                <form id="newForm">
                    <div class="form-group">
                        <label>机会名称</label>
                        <input type="text" name="name" class="form-control">
                    </div>
                    <div class="form-group" id="custList">
                        <label>关联客户</label>
                        <select class="form-control" name="custid">
                            <option value=""></option>
                            <c:forEach items="${custList}" var="cust">
                                <option value="${cust.id}">${cust.custname}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>销售价值</label>
                        <input type="text" name="price" class="form-control">
                    </div>

                    <div class="form-group">
                        <label>当前进度</label>
                        <select class="form-control" name="progress">
                            <option value="初次接触">初次接触</option>
                            <option value="确认意向">确认意向</option>
                            <option value="提供合同">提供合同</option>
                            <option value="完成交易">完成交易</option>
                            <option value="交易搁置">交易搁置</option>
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
<%--修改销售机会弹出框--%>
<div class="modal fade" id="editModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">修改销售机会</h4>
            </div>
            <div class="modal-body">
                <form id="editForm">
                    <input type="hidden" id="edit_id" name="id">
                    <div class="form-group">
                        <label>机会名称</label>
                        <input type="text" name="name" id="edit_name" class="form-control">
                    </div>
                    <div class="form-group" id="editCustList">
                        <label>关联客户</label>
                        <select class="form-control" name="custid" id="edit_custid">
                           <%-- <option value=""></option>
                            <c:forEach items="${custList}" var="cust">
                                <option value="${cust.id}">${cust.custname}</option>
                            </c:forEach>--%>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>销售价值</label>
                        <input type="text" name="price" id="edit_price" class="form-control">
                    </div>

                    <div class="form-group">
                        <label>当前进度</label>
                        <select class="form-control" id="edit_progress" name="progress">
                            <option value="初次接触">初次接触</option>
                            <option value="确认意向">确认意向</option>
                            <option value="提供合同">提供合同</option>
                            <option value="完成交易">完成交易</option>
                            <option value="交易搁置">交易搁置</option>
                        </select>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="editBtn">修改</button>
            </div>
        </div>
    </div>
</div>
<!-- REQUIRED JS SCRIPTS -->

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
        var dataTable = $("#salesTable").DataTable({
            serverSide: true,
            ordering: false,
            "autoWidth": false,
            ajax: "/sales/load",
            columns: [
                {
                    "data": function (row) {
                        return "<a href='sales/" + row.id + "'>" + row.name + "</a>";
                    }
                },
                {
                    "data": function (row) {
                        return "<a href='customer/" + row.custid + "'>" + row.custname + "</a>";
                    }
                },
                {"data": "price"},
                {"data": "progress"},
                {
                    "data": function (row) {
                        var timestamp = row.createtime;
                        var day = moment(timestamp).format("YYYY-MM-DD HH:mm");
                        return day;
                    }
                },
                {"data": "username"},
                {
                    "data": function (row) {
                        return "<a href='#' class='editLink' rel='" + row.id + "'>修改</a> " <shiro:hasRole name="经理"> +
                                "<a href='#' class='delLink' rel='" + row.id + "'>删除</a>"</shiro:hasRole>;
                    }
                }
            ],
            "language": {
                "search": "请输入机会名称或员工姓名:",
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

        //新增销售机会信息
        $("#newForm").validate({
            errorClass: "text-danger",
            errorElement: "span",
            rules: {
                name: {
                    required: true
                },
                price: {
                    required: true
                }
            },
            messages: {
                name: {
                    required: "请输入销售机会名称"
                },
                price: {
                    required: "请输入价值"
                }
            },
            submitHandler: function (form) {
                $.post("/sales/new", $(form).serialize()).done(function (data) {
                    if (data == "success") {
                        $("#newModal").modal("hide");
                        dataTable.ajax.reload();
                        //alert("客户新增成功");
                    }
                }).fail(function () {
                    alert("服务器异常")
                });
            }
        });

        $("#newBtn").click(function () {
            $("#newForm")[0].reset();
            $.get("/sales/customer.json", "click", function (data) {
                var $select = $("#custList select");
                $select.html("");
                $select.append('<option></option>');
                if (data && data.length) {
                    for (var i = 0; i < data.length; i++) {
                        var cust = data[i];
                        var option = "<option value='" + cust.id + "'>" + cust.name + "</option>";
                        $select.append(option);
                    }
                }
            }).fail(function () {
                alert("服务器运行异常");
            });
            $("#newModal").modal({
                show: true,
                backdrop: 'static',
                keyboard: false
            });
        });

        $("#saveBtn").click(function () {
            $("#newForm").submit();
        });

        //删除客户
        <shiro:hasRole name="经理">
        $(document).delegate(".delLink", "click", function () {
            if (confirm("确定要删除销售机会及关联的数据吗？")) {
                var id = $(this).attr("rel");
                $.get("/sales/del/" + id).done(function (data) {
                    if ("success" == data) {
                        dataTable.ajax.reload();
                    }
                }).fail(function () {
                    alert("服务器异常");
                });
            }
        });
        </shiro:hasRole>

        //修改销售机会
        $("#editForm").validate({
            errorClass: "text-danger",
            errorElement: "span",
            rules: {
                name: {
                    required: true
                },
                price: {
                    required: true
                }
            },
            messages: {
                name: {
                    required: "请输入销售机会名称"
                },
                price: {
                    required: "请输入价值"
                }
            },
            submitHandler: function (form) {
                $.post("/sales/edit", $(form).serialize()).done(function (data) {
                    if (data == "success") {
                        $("#editModal").modal("hide");
                        dataTable.ajax.reload();
                        //alert("客户修改成功");
                    }
                }).fail(function () {
                    alert("服务器异常");
                });
            }
        });

        $(document).delegate(".editLink", "click", function () {
            var id = $(this).attr("rel");
            var $select = $("#editCustList select");
            $select.html("");
            $select.append("<option></option>");

            $.get("/sales/edit/" + id + ".json").done(function (data) {

                if (data.state == "success") {
                    if(data.customerList && data.customerList.length) {
                        for(var i = 0;i < data.customerList.length;i++) {
                            var customer = data.customerList[i];
                            var option = "<option value='"+customer.id+"'>"+customer.name+"</option>";
                            $select.append(option);
                        }
                    }
                    var sales = data.sales;

                    $("#edit_id").val(sales.id);
                    $("#edit_name").val(sales.name);
                    $("#edit_price").val(sales.price);
                    $("#edit_progress").val(sales.progress);

                    $select.val(sales.custid);


                    $("#editModal").modal({
                        show: true,
                        backdrop: 'static',
                        keyboard: false
                    });
                } else {
                    alert(data.message);
                }
            }).fail(function () {
                alert("服务器异常");
            });
        });

        $("#editBtn").click(function () {
            $("#editForm").submit();
        });

    });

</script>
</body>
</html>

