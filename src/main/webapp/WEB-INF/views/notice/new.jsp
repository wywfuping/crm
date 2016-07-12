<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>CRM发表公告</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="/static/plugins/fontawesome/css/font-awesome.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="/static/dist/css/AdminLTE.min.css">

    <link rel="stylesheet" href="/static/dist/css/skins/skin-blue.min.css">
    <link rel="stylesheet" href="/static/plugins/simditor/styles/simditor.css">

</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <!-- Main Header -->
    <%@include file="../include/mainHeader.jsp" %>
    <!-- Left side column. contains the logo and sidebar -->
    <jsp:include page="../include/leftSide.jsp">
        <jsp:param name="menu" value="notice"/>
    </jsp:include>


    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">

        <section class="content">

            <div class="box box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">新增公告</h3>
                </div>
                <div class="box-body">
                    <form id="newForm" method="post">
                        <div class="form-group">
                            <label>公告标题</label>
                            <input type="text" class="form-control" id="title" name="title">
                        </div>
                        <div class="form-group">
                            <label>公告内容</label>
                            <textarea class="form-control" name="context" id="context" rows="15"></textarea>
                        </div>
                    </form>
                </div>
                <div class="box-footer">
                    <button class="btn btn-primary pull-right" id="saveBtn">发表</button>
                </div>
            </div>

        </section>
        <!-- /.content -->
    </div>
    <div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->

<!-- REQUIRED JS SCRIPTS -->

<!-- jQuery 2.2.3 -->
<script src="/static/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="/static/bootstrap/js/bootstrap.min.js"></script>
<!-- AdminLTE App -->
<script src="/static/dist/js/app.min.js"></script>
<script src="/static/plugins/simditor/scripts/module.min.js"></script>
<script src="/static/plugins/simditor/scripts/hotkeys.min.js"></script>
<script src="/static/plugins/simditor/scripts/uploader.min.js"></script>
<script src="/static/plugins/simditor/scripts/simditor.min.js"></script>

<script>
    $(function(){
        var editor = new Simditor({
            textarea: $("#context")
        });
    });


    $("#saveBtn").click(function () {

        if(!$("#title").val()){
            $("#title").focus();
            return '';
        }
        if(!$("#context").val()){
            $("#context").focus();
            return '';
        }
        $("#newForm").submit();
    });
</script>
</body>
</html>

