var option = {
    title : {
        text: '2011年-2019年大数据解决方案同行业对比',
        subtext: '纯属虚构'
    },
    tooltip : {
        trigger: 'axis'
    },
    legend: {
        data:['同行业','本系统']
    },
    calculable : true,
    xAxis : [
        {
            type : 'category',
            data : ['2011','2012','2013','2014','2015','2016','2017','2018','2019']
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    series : [
        {
            name:'同行业',
            type:'bar',
            data:[25, 133, 256, 278, 320, 360, 400, 530, 640],
            markPoint : {
                data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            markLine : {
                data : [
                    {type : 'average', name: '平均值'}
                ]
            }
        },
        {
            name:'本系统',
            type:'bar',
            data:[29, 133, 251, 279, 360, 480, 630, 756, 879],
            markPoint : {
                data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            markLine : {
                data : [
                    {type : 'average', name: '平均值'}
                ]
            }
        }
    ]
};

var myChart = echarts.init(document.getElementById('report'), 'macarons');
myChart.setOption(option);