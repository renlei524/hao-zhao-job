$(function () {
    vm.getProvince();
    var now = new Date(),
        year = now.getFullYear(),
        month = ((now.getMonth() + 1)>9)?(now.getMonth() + 1):("0"+(now.getMonth() + 1)),
        date = translate(now.getDate());
    function translate(prop){
        if(prop <= 9){
            return "0" + prop;
        }else {
            return prop
        }
    }
    var dateString = year+"-"+month+"-"+date;
    $("#startTime-endTime").val(dateString+" - "+dateString);
    vm.q.startTime = dateString;
    vm.q.endTime = dateString;
    $("#jqGrid").jqGrid({
        url: baseURL + 'statistics/salesmanKPI/list',
        datatype: "json",
        gridComplete:function(){
            $.ajax({
                type: "POST",
                url: baseURL + 'statistics/salesmanKPI/show',
                contentType: "application/json",
                success: function(r){
                    vm.salesmanKPIEntity = r.salesmanKPIEntityList;
                    vm.barGraph(r.salesmanKPIEntityList);
                }
            });
        }
    });
});


var vm = new Vue({
    el:'#rrapp',
    data:{
        showList: true,
        title: null,
        q: {
            province:0,
            city:0,
            area:0,
            town:0,
            sysUserName:null,
            startTime:null,
            endTime:null
        },
        salesmanKPIEntity:{
            sysUsetName:null,
            numBers:null
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        reload: function (event) {
            if (document.getElementById ("startTime-endTime").value.length < 5 ) {
                return ;
            }
            vm.showList = true;
            var time = $("#startTime-endTime").val().split(" - ");
            vm.q.startTime = time[0];
            vm.q.endTime = time[1];
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                //添加传送数据
                postData:{
                    "province" :vm.q.province,
                    "city" :vm.q.city,
                    "area" :vm.q.area,
                    "town" :vm.q.town,
                    "sysUserName" :vm.q.sysUserName,
                    "endTime" :vm.q.endTime,
                    "startTime" :vm.q.startTime},
                page:page
            }).trigger("reloadGrid");
        },
        getProvince: function(){
            var parentId=0;
            var group = $("#areaProvince");
            group.empty();
            group.append("<option value='0' selected='selected'>--请选择省--</option>");
            //加载省下拉框
            var url = "sys/twbarea/infoList/"+parentId;
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.twbarea),
                success: function(r){
                    for(var i=0;i<r.length;i++) {
                        group.append("<option value='"+r[i].areaCode+"'>"+r[i].name+"</option>");
                    }
                }
            });
        },
        getCity: function(){
            var parentId=$("#areaProvince").val();
            var group = $("#areaCity");
            group.empty();
            group.append("<option value='0' selected='selected'>--请选择市--</option>");
            if (parentId <=0) {

            }else {
                //加载市下拉框
                var url = "sys/twbarea/infoList/"+parentId;
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.twbarea),
                    success: function(r){
                        for(var i=0;i<r.length;i++) {
                            group.append("<option value='"+r[i].areaCode+"'>"+r[i].name+"</option>");
                        }
                    }
                });
            }
        },
        getArea: function(){
            var parentId=$("#areaCity").val();
            var group = $("#areaArea");
            group.empty();
            group.append("<option value='0' selected='selected'>--请选择区--</option>");
            if (parentId <=0) {

            }else {
                //加载区下拉框
                var url = "sys/twbarea/infoList/"+parentId;
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.twbarea),
                    success: function(r){
                        for(var i=0;i<r.length;i++) {
                            group.append("<option value='"+r[i].areaCode+"'>"+r[i].name+"</option>");
                        }
                    }
                });
            }

        } ,
        getTown: function(){
            var parentId=$("#areaArea").val();
            var group = $("#areaTown");
            group.empty();
            group.append("<option value='0' selected='selected'>--请选择乡镇--</option>");
            if (parentId <=0) {

            }else {
                //加载乡镇下拉框
                var url = "sys/twbarea/infoList/"+parentId;
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.twbarea),
                    success: function(r){
                        for(var i=0;i<r.length;i++) {
                            group.append("<option value='"+r[i].areaCode+"'>"+r[i].name+"</option>");
                        }
                    }
                });
            }

        },
        barGraph: function (list) {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('barGraph'));

            var arr = new Array();//地址
            var arrNum = new Array()//个数

            for (var i = 0; i < list.length; i++) {
                arr.push( list[i].sysUsetName);//存入arr
                arrNum.push(list[i].numBers);//存入arrnum
            }


            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: '业务员KPI'
                },
                grid:{
                    x:25,
                    y:45,
                    x2:5,
                    y2:20,
                    borderWidth:1
                },
                tooltip: {},
                legend: {
                    data:['数量']
                },
                xAxis: {
                    data: arr
                },
                yAxis: {},
                series: [{
                    name: '数量',
                    type: 'bar',
                    data: arrNum
                }]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        }
    }
});


$("#areaProvince").change(function(){
    vm.q.city = 0;
    vm.getCity();
    vm.q.area = 0;
    vm.getArea();
    vm.q.town = 0;
    vm.getTown();
})

$("#areaCity").change(function(){
    vm.q.area = 0;
    vm.getArea();
    vm.q.town = 0;
    vm.getTown();
})

$("#areaArea").change(function(){
    vm.q.town = 0;
    vm.getTown();
})

layui.use('laydate', function(){
    var laydate = layui.laydate;

    //执行一个laydate实例
    laydate.render({
        elem: '#startTime-endTime' //指定元素
        ,range: true
    });
});
