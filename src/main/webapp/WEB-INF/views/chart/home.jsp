<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>CRM | 统计</title>
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
    <%@include file="../include/mainHeader.jsp"%>
    <!-- Left side column. contains the logo and sidebar -->
    <jsp:include page="../include/leftSide.jsp">
        <jsp:param name="menu" value="chart"/>
    </jsp:include>


    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <section class="content">
            <div class="box box-primary">
                <div class="box-header">
                    <h3 class="box-title">统计区间</h3>
                </div>
                <div class="box-body">
                </div>
            </div>

            <div class="row">
                <div class="col-md-4">
                    <div class="info-box">
                        <span class="info-box-icon bg-aqua"><i class="fa fa-plus"></i></span>
                        <div class="info-box-content">
                            <span class="info-box-text">本月新增客户数量</span>
                            <span class="info-box-number">${newCustCount}</span>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="info-box">
                        <span class="info-box-icon bg-green"><i class="fa fa-flag"></i></span>
                        <div class="info-box-content">
                            <span class="info-box-text">本月完成交易数量</span>
                            <span class="info-box-number">${salesCount}</span>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="info-box">
                        <span class="info-box-icon bg-red"><i class="fa fa-money"></i></span>
                        <div class="info-box-content">
                            <span class="info-box-text">本月完成交易总额</span>
                            <span class="info-box-number">￥<fmt:formatNumber value="${salesPrice}"/></span>
                        </div>
                    </div>
                </div>
            </div>

            <div class="box box-primary">
                <div class="box-header">
                    <h3 class="box-title">销售机会统计</h3>
                </div>
                <div class="box-body">
                    <div id="pieChart" style="width: 100%;height:300px;"></div>
                </div>
            </div>
            <div class="box box-primary">
                <div class="box-header">
                    <h3 class="box-title">员工业绩图</h3>
                </div>
                <div class="box-body">
                    <div id="barChart" style="width: 100%;height:300px;"></div>
                </div>
            </div>
        </section>
    </div>
</div>
<!-- ./wrapper -->

<!-- jQuery 2.2.3 -->
<script src="/static/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="/static/bootstrap/js/bootstrap.min.js"></script>
<script src="/static/plugins/chartjs/echarts.min.js"></script>
<script>
    $(function () {
        var pieChart = echarts.init($("#pieChart")[0]);
        var pieOption=({
            tooltip:{
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            roseType: 'angle',
            series : [
                {
                    name: '销售机会',
                    type: 'pie',
                    data:[],
                    itemStyle: {
                        emphasis: {
                            // 阴影的大小
                            shadowBlur: 20,
                            // 阴影水平方向上的偏移
                            shadowOffsetX: 0,
                            // 阴影垂直方向上的偏移
                            shadowOffsetY: 0,
                            // 阴影颜色
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        });
        // 异步加载数据
        pieChart.setOption(pieOption);
        $.get('/chart/progress/data').done(function (data) {
            pieChart.setOption({
                series: [{
                    data: data
                }]
            });
        });

        //柱状图
        var barChart = echarts.init($("#barChart")[0]);
        var barOption=({
            tooltip: {},
            xAxis: {
                data: []
            },
            yAxis: {},
            series: [{
                name: '业绩',
                type: 'bar',
                data: []
            }]
        });

        // 异步加载数据
        barChart.setOption(barOption);
        $.get('/chart/user/price').done(function (data) {
            // 填入数据
            barChart.setOption({
                xAxis: {
                    data: data.names
                },
                series: [{
                    name: '业绩',
                    data: data.values
                }]
            });
        });

    });
</script>
</body>
</html>

