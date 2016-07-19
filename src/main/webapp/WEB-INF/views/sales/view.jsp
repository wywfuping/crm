<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>CRM销售机会|${sales.name}</title>
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
    <link rel="stylesheet" href="/static/plugins/webuploader/webuploader.css">
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
        <section class="content-header">
            <h1>
                销售机会
                <small>${sales.name}</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="/sales"><i class="fa fa-list"></i> 销售机会列表</a></li>
                <li class="active">${sales.name}</li>
            </ol>
        </section>
        <section class="content">
            <div class="box box-primary">
                <h3 class="box-header">${sales.name}</h3>
                <shiro:hasRole name="经理">
                    <div class="box-tools pull-right">
                        <button class="btn btn-danger btn-xs" id="delSalesBtn">
                            <i class="fa fa-trash-o"></i> 删除</button>
                    </div>
                </shiro:hasRole>
                <div class="box-body">
                    <table class="table">
                        <tr>
                            <td style="width: 100px">关联客户：</td>
                            <td style="width: 200px">
                                <a href="/customer/${sales.custid}">${sales.custname}</a></td>
                            <td style="width: 100px">销售价值：</td>
                            <td style="width: 200px">￥<fmt:formatNumber value="${sales.price}"/> </td>
                            <td style="width: 100px">销售进度：</td>
                            <td style="width: 200px">${sales.progress}
                                <a href="#" id="editProgress">修改</a></td>
                        </tr>
                        <td style="width: 100px">所属员工：</td>
                        <td style="width: 200px">${sales.username}</td>
                        <td style="width: 130px">最后跟进时间：</td>
                        <td style="width: 200px">${empty sales.lasttime?'无':sales.lasttime}</td>
                    </table>
                </div>
            </div>
            <%--销售机会记录--%>
            <div class="row">
                <div class="col-md-8">
                    <div class="box box-info">
                        <div class="box-header with-border">
                            <h3 class="box-title"><i class="fa fa-list"></i> 跟进记录</h3>
                            <div class="box-tools">
                                <button class="btn btn-success btn-xs" id="newLogBtn">
                                    <i class="fa fa-plus"> 新增记录</i></button>
                            </div>
                        </div>
                        <div class="box-body">
                            <ul class="timeline">
                                <c:forEach items="${salesLogList}" var="log">
                                    <li>
                                        <c:choose>
                                            <c:when test="${log.type=='auto'}">
                                                <i class="fa fa-history bg-yellow"></i>
                                            </c:when>
                                            <c:otherwise>
                                                <i class="fa fa-commenting bg-aqua"></i>
                                            </c:otherwise>
                                        </c:choose>
                                        <div class="timeline-item">
                                            <span class="time"><i class="fa fa-clock-o"></i> <span class="timeago" title="${log.createtime}"></span></span>
                                            <h3 class="timeline-header no-border">
                                                    ${log.context}
                                            </h3>
                                        </div>
                                    </li>
                                </c:forEach>
                                <li>
                                    <i class="fa fa-clock-o bg-gray"></i>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <%--相关资料--%>
                <div class="col-md-4">
                    <div class="box box-default">
                        <div class="box-header with-border">
                            <h3 class="box-title"><i class="fa fa-file-o"></i> 相关资料</h3>
                            <div class="box-tools pull-right">
                                <div id="uploadBtn"><span class="text"><i class="fa fa-upload"></i> 上传文件</span></div>
                            </div>
                        </div>

                        <div class="box-body" style="text-align: left">
                            <ul class="list-unstyled files">
                                <c:if test="${empty salesFileList}">
                                    <li>暂无资料</li>
                                </c:if>
                                <c:forEach items="${salesFileList}" var="file">
                                    <li><a href="/sales/file/${file.id}/download">${file.name}</a></li>
                                </c:forEach>
                            </ul>
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

<%--新增跟进记录--%>
<div class="modal fade" id="newLogModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">新增记录</h4>
            </div>
            <div class="modal-body">
                <form id="newLogForm" action="/sales/log/new" method="post">
                    <input type="hidden" value="${sales.id}" name="salesid">
                    <div class="form-group">
                       <textarea id="context" name="context"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="saveLogBtn">保存</button>
            </div>
        </div>
    </div>
</div>
<%--修改销售进度--%>
<div class="modal fade" id="progressModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">修改销售进度</h4>
            </div>
            <div class="modal-body">
                <form id="progressForm" action="/sales/progress/edit" method="post">
                    <input type="hidden" value="${sales.id}" name="id">
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
                <button type="button" class="btn btn-primary" id="saveProgressBtn">保存</button>
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
<script src="/static/plugins/timeago/timeago.js"></script>
<script src="/static/plugins/timeago/timeago_zh_cn.js"></script>
<script src="/static/plugins/simditor/scripts/module.min.js"></script>
<script src="/static/plugins/simditor/scripts/hotkeys.min.js"></script>
<script src="/static/plugins/simditor/scripts/uploader.min.js"></script>
<script src="/static/plugins/simditor/scripts/simditor.min.js"></script>
<script src="/static/plugins/webuploader/webuploader.min.js"></script>

<script>
    $(function(){

        $(".timeago").timeago();

        //在线编辑器
        var edit = new Simditor({
            textarea:$("#context"),
            placeholder: '请输入跟进内容',
            toolbar:false
        });

        //新增跟进记录
        $("#newLogBtn").click(function(){
            $("#newLogModal").modal({
                show:true,
                backdrop:'static'
            });
        });
        $("#saveLogBtn").click(function(){
            if(edit.getValue()) {
                $("#newLogForm").submit();
            } else {
                edit.focus();
            }
        });

        //修改当前进度
        $("#editProgress").click(function(){
            $("#progressModal").modal({
                show:true,
                backdrop:'static'
            });
        });
        $("#saveProgressBtn").click(function(){
            $("#progressForm").submit();
        });

        //上传文件
        var uploader = WebUploader.create({
            swf:"/static/plugins/webuploader/Uploader.swf",
            pick:"#uploadBtn",
            server:"/sales/file/upload",
            fileValL:"file",
            formData:{"salesid":"${sales.id}"},
            auto:true //选择文件后直接上传
        });

        //上传文件成功
        uploader.on("startUpload",function(){
            $("#uploadBtn .text").html('<i class="fa fa-spinner fa-spin"></i>');
        });
        uploader.on('uploadSuccess', function( file,data ) {
            if(data._raw == "success") {
                window.history.go(0);
            }
        });

        uploader.on( 'uploadError', function( file ) {
            alert("文件上传失败");
        });

        uploader.on( 'uploadComplete', function( file ) {
            $("#uploadBtn .text").html('<i class="fa fa-upload"></i>').removeAttr("disabled");
        });

        <shiro:hasRole name="经理">
        //删除销售机会
        $("#delSalesBtn").click(function(){
            if(confirm("确定要删除该销售机会吗")) {
                window.location.href = "/sales/del/${sales.id}";
            }
        });
        </shiro:hasRole>

    });
</script>
</body>
</html>

