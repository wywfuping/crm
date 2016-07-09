<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>CRM用户密码设置</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="/static/dist/css/AdminLTE.min.css">

    <link rel="stylesheet" href="/static/dist/css/skins/skin-blue.min.css">

    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <!-- Main Header -->
    <%@include file="../include/mainHeader.jsp" %>
    <!-- Left side column. contains the logo and sidebar -->
    <%@include file="../include/leftSide.jsp" %>

    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">
            <div class="col-xs-4">
                <div class="box-header with-border">
                    <h3 class="box-title">设置密码</h3>
                </div>
                <div class="box-body">
                    <form method="post" id="changePasswordForm">
                        <div class="form-group">
                            <label>原始密码</label>
                            <input type="text" class="form-control" name="oldpassword">
                        </div>
                        <div class="form-group">
                            <label>新密码</label>
                            <input type="text" id="newpassword" class="form-control" name="newpassword">
                        </div>
                        <div class="form-group">
                            <label>确认密码</label>
                            <input type="text" class="form-control" name="repetpassword">
                        </div>
                    </form>
                </div>
                <div class="form-group">
                    <button id="editBtn" class="btn btn-primary pull-right">修改</button>
                </div>
            </div>
        </section>
    </div>
</div>

<script src="/static/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="/static/bootstrap/js/bootstrap.min.js"></script>
<script src="/static/dist/js/app.min.js"></script>
<script src="/static/plugins/validate/jquery.validate.min.js"></script>

<script>
    $(function () {
        $("#changePasswordForm").validate({
            errorElement: "span",
            errorClass: "text-danger",
            rules: {
                oldpassword: {
                    required: true,
                    remote: "user/validate/password"
                },
                newpassword: {
                    required: true,
                    rangelength: [6, 18]
                },
                repetpassword: {
                    required: true,
                    rangelength: [6, 18],
                    equalTo: "#newpassword"
                }
            },
            messages: {
                oldpassword: {
                    required: "请输入原始密码",
                    remote: "原始密码错误"
                },
                newpassword: {
                    required: "请输入新密码",
                    rangelength: "密码长度6~18位"
                },
                repetpassword: {
                    required: "请输入确认密码",
                    rangelength: "密码长度6~18位",
                    equalTo: "确认密码错误"
                }
            },
            submitHandler: function (form) {
                var password = $("#newpassword").val();
                $.post("user/password", {"password": password})
                        .done(function (data) {
                            if (data == "success") {
                                alert("密码修改成功，请点击确定重新登录！");
                                window.location.href = "/";
                            }
                        })
                        .fail(function () {
                            alert("服务器异常")
                        });
            }
        });

        $("#editBtn").click(function(){
            $("#changePasswordForm").submit();
        });

    });

</script>

</body>
</html>

