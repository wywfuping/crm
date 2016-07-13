<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>CRM文档管理</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="/static/plugins/fontawesome/css/font-awesome.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="/static/dist/css/AdminLTE.min.css">

    <link rel="stylesheet" href="/static/dist/css/skins/skin-blue.min.css">
    <link rel="stylesheet" href="/static/plugins/webuploader/webuploader.css">
</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <!-- Main Header -->
    <%@include file="../include/mainHeader.jsp" %>
    <!-- Left side column. contains the logo and sidebar -->
    <jsp:include page="../include/leftSide.jsp">
        <jsp:param name="menu" value="document"/>
    </jsp:include>


    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">

        <section class="content">
            <div class="box box-primary">
                <div class="box-header with-border">
                    <div class="box-title">文档管理-${fid}</div>
                    <div class="box-tools">
                        <span id="uploadBtn"><span class="text"><i class="fa fa-upload"></i> 上传文件</span></span>
                        <button id="newDir" class="btn btn-bitbucket btn-xs"><i class="fa fa-folder"></i> 新建文件夹</button>
                    </div>
                </div>
                <div class="box-body">
                    <table class="table" id="docTable">
                        <thead>
                        <tr>
                            <th>图标</th>
                            <th>文件名称</th>
                            <th>文件大小</th>
                            <th>创建人</th>
                            <th>创建时间</th>
                        </tr>
                        </thead>
                        <tboby>
                            <c:if test="${empty documentList}">
                                <tr>
                                    <td colspan="5">暂时没有数据</td>
                                </tr>
                            </c:if>
                            <c:forEach items="${documentList}" var="doc">
                                <tr>
                                    <c:choose>
                                        <c:when test="${doc.type=='dir'}">
                                            <td><i class="fa fa-folder-o"></i></td>
                                            <td><a href="/doc?fid=${doc.id}">${doc.name}</a></td>
                                        </c:when>
                                        <c:otherwise>
                                            <td><i class="fa fa-file-o"></i></td>
                                            <td><a href="/doc/download/${doc.id}">${doc.name}</a></td>
                                        </c:otherwise>
                                    </c:choose>
                                    <td>${doc.size}</td>
                                    <td>${doc.createuser}</td>
                                    <td><fmt:formatDate value="${doc.createtime}" pattern="y-M-d H:mm"/></td>
                                </tr>
                            </c:forEach>
                        </tboby>
                    </table>
                </div>
            </div>

        </section>
    </div>
</div>
<!-- ./wrapper -->

<%--新建文件夹弹出框--%>
<div class="modal fade" id="newDirModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">新建文件夹</h4>
            </div>
            <div class="modal-body">
                <form id="newDirForm" method="post" action="/doc/dir/new">
                    <input type="hidden" name="fid" value="${fid}">
                    <div class="form-group">
                        <label>文件夹名称</label>
                        <input type="text" name="name" id="dirname" class="form-control">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="saveDirBtn">保存</button>
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
<script src="/static/plugins/webuploader/webuploader.min.js"></script>

<script>
    //新建文件夹
    $(function () {
        $("#newDir").click(function () {
            $("#newDirModal").modal({
                show: true,
                backdrop: 'static',
                keyboard: false
            });
        });
        $("#saveDirBtn").click(function () {
            if (!$("#dirname").val()) {
                $("#dirname").focus();
                return;
            }
            $("#newDirForm").submit();
        });

        //上传文件
        var uploader = WebUploader.create({
            pick: "#uploadBtn",
            swf: "/static/plugins/webuploader/Uploader.swf",
            server: "/doc/file/upload",
            fileVal: "file",
            auto:true,
            formData:{"fid":"${fid}"}
        });
        uploader.on( 'startUpload', function() {
            $("#uploadBtn .text").html('<i class="fa fa-spinner fa-spin"></i>上传中...');
        });
        uploader.on( 'uploadSuccess', function(file,data) {
            if(data._raw=="success"){
                window.location.reload();
            }
        });

        uploader.on( 'uploadError', function(file) {
            alert("文件上传失败");
        });

        uploader.on( 'uploadComplete', function(file) {
            $("#uploadBtn .text").html('<i class="fa fa-upload"></i>上传文件');
        });

    });
</script>
</body>
</html>

