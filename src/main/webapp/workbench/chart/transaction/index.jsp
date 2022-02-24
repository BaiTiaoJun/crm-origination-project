<%--
  Created by IntelliJ IDEA.
  User: 27310
  Date: 2022/2/22
  Time: 11:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>Title</title>
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="ECharts/echarts.min.js"></script>
    <script type="text/javascript">
        $(function () {
            getChart();
        })

        function getChart() {

            $.ajax({
                url: "workbench/transaction/getTranCharts.do",
                type: "get",
                dataType: "json",
                success:function (data) {
                    // 基于准备好的dom，初始化echarts实例
                    var myChart = echarts.init(document.getElementById('main'));

                    // 指定图表的配置项和数据
                    var option = {
                        title: {
                            text: '交易阶段统计图'
                        },
                        tooltip: {},
                        legend: {
                            data: ['交易阶段']
                        },
                        xAxis: {
                            data: data.nameList
                        },
                        yAxis: {},
                        series: [
                            {
                                name: '交易阶段',
                                type: 'bar',
                                data: data.valueList
                            }
                        ]
                    };

                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                }
            })
        }
    </script>
</head>
<body>
    <!-- 为 ECharts 准备一个定义了宽高的 DOM -->
    <div id="main" style="width: 600px;height:400px;"></div>
</body>
</html>
