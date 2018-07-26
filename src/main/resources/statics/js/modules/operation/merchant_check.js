$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'operation/check/list',
        datatype: "json",
        colModel: [
            { label: '商户id', name: 'id', width: 80 , hidden:true},
            { label: '分公司名称', name: 'agentName', width: 80 },
            { label: '商户收款码', name: 'merchantCode', index: 'merchant_code', width: 80 },
            { label: '商户名称(店铺名称)', name: 'merchantName', index: 'merchant_name', width: 80 },
            { label: '商户分类', name: 'typeName', index: 'type_name', width: 80 },
            { label: '本店联系电话', name: 'telphone', index: 'telphone', width: 80 },
            { label: '支付通道', name: 'payChannel', width: 80 , formatter: function(item, index){
                    if(item === 1){
                        return '<span class="label label-primary">原生通道</span>';
                    }
                    if(item === 2){
                        return '<span class="label label-success">微易客</span>';
                    }
                    if(item === 3){
                        return '<span class="label label-warning">点点客</span>';
                    }
                }},
            { label: '状态', name: 'status',index: 'status', align: 'center', valign: 'middle', sortable: true, width: '80px', formatter: function(item, index){
                if(item === 1){
                    return '<span class="label label-primary">创建审核中</span>';
                }
                if(item === 3){
                    return '<span class="label label-warning">修改审核中</span>';
                }
            }}
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList : [10,15,20,30,50],
        rownumbers: true,
        rownumWidth: 50,
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page",
            rows:"limit",
            order: "order"
        },
        gridComplete:function(){
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });

            $('.radio-inline-hide').click(function(){
                $('.audit-state').hide();
            })

            $('.radio-inline-show').click(function(){
                $('.audit-state').show();
            })
        }
    });
});

Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

var vm = new Vue({
	el:'#rrapp',
	data:{
        showList: true,
        title:null,
        roleList:{},
        q:{
            communityName:null,
            tWBUserName:null,
            status:0
        },
        merchant:{
            userId:0,
            status:1,
            agentId:null,
            agentName:null,
            merchantName:null,
            recommend:true,
            photos:null,
            avatar:null,
            typeId:null,
            typeName:null,
            province: -1,
            city: -1,
            area: -1,
            town: -1,
            supportDispatching:0,
            payChannel:1,
            address:null,
            simpleAddress:null,
            loginUsername:null,
            commercialName:null,
            communityName:null,
            latitude:null,
            longitude:null,
            remark:null,
            salesman:null,
            isVoiceFunction:1,
            contractNumber:null
        },
        merchantcategory:{
            categoryId:null,
            className:null
        },
        tWBUser:{
            userId:null,
            realName:null
        },
        //新增时联动菜单重置用省、市、区、街道 d
        selectedProvince: -1,
        selectedCity: -1,
        selectedArea: -1,
        selectedTown: -1
	},
	methods: {
		query: function () {
			vm.reload();
		},
        //获取商户名字信息 d
        getUserInfo:function(userId) {
            var id = vm.merchant.userId;
            $.get(baseURL + "operation/twbuser/info/"+id, function(r){
                vm.tWBUser = r.tWbUser;
                vm.merchant.commercialName = r.tWbUser.realName;
                $("#userName").val(vm.merchant.commercialName);
            });
        },
        //加载社区信息
        getCommunity:function() {
            var id = vm.merchant.communityId;
            $.get(baseURL + "community/community/info/"+id, function(r){
                vm.merchant.communityName = r.community.name;
                $("#communityName").val(vm.merchant.communityName);
            });
        },
		check: function (event) {
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            vm.showList = false;
            vm.title = "审核";
            vm.getInfo(id)
        },
		saveOrUpdate: function (event) {
		    if($("#text1").attr("status") == 'N') return ;
            $("#text1").attr("status", "N");
			var url = "operation/check/check";
			//getMerchantPhotos();
            var provinceName = null;
            var cityName = null;
            var areaName = null;
            var townName = null;

            if ($("#province").val() != 0 && $("#province").val() != 'undefined' && $("#province").val() != 'none' && $("#province").val() != null) {
                var provinceIndex=document.getElementById("province").selectedIndex;
                provinceName = document.getElementById("province").options[provinceIndex].innerHTML;
            }
            if ($("#city").val() != 0 && $("#city").val() != 'undefined' && $("#city").val() != 'none' && $("#city").val() != null) {
                var cityIndex=document.getElementById("city").selectedIndex;
                cityName = document.getElementById("city").options[cityIndex].innerHTML;
            }
            if ($("#area").val() != 0 && $("#area").val() != 'undefined' && $("#area").val() != 'none' && $("#area").val() != null) {
                var areaIndex=document.getElementById("area").selectedIndex;
                areaName = document.getElementById("area").options[areaIndex].innerHTML;
            }
            if ($("#town").val() != 0 && $("#town").val() != 'undefined' && $("#town").val() != 'none' && $("#town").val() != null) {
                var townIndex=document.getElementById("town").selectedIndex;
                townName = document.getElementById("town").options[townIndex].innerHTML;
            }
			vm.merchant.address = (provinceName == null ? "" : provinceName) + (cityName == null ? "" : cityName) +
                (areaName == null ? "" : areaName) +  (townName == null ? "" : townName)+ vm.merchant.simpleAddress;

            vm.merchant.status = $("input[name='status']:checked").val();
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.merchant),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
        getInfo: function(id){
            $.get(baseURL + "operation/check/info/"+id, function(r){
                var imageNginxPath = r.imageNginxPath;
                vm.merchant = r.merchant;
                vm.merchant.remark = null;
                if(vm.merchant.status == 1) {// 创建待审核
                    $('#checkStatus').val(2); // 创建审核不通过
                } else if(vm.merchant.status == 3) { // 修改待审核
                    $('#checkStatus').val(4); // 修改不通过
                }
                vm.merchant.status = null;
                //获取商户名称信息
                vm.getUserInfo();
                // 获取当前商户社区信息
                vm.getCommunity();
                //加载省市区数据 d
                vm.getProvince();
                vm.getCity(vm.merchant.province);
                vm.getArea(vm.merchant.city);
                vm.getTown(vm.merchant.area);
                vm.getSalesManInfo(vm.merchant.salesman);
                if(vm.merchant.licence != null && vm.merchant.licence != 'undefined' && vm.merchant.licence != '' && vm.merchant.licence != 'none') {
                    $('#view').css('background', 'url(' + imageNginxPath + vm.merchant.licence + ')');
                }
                if(vm.merchant.idCardFacePhoto != null && vm.merchant.idCardFacePhoto != 'undefined' && vm.merchant.idCardFacePhoto != '' && vm.merchant.idCardFacePhoto != 'none') {
                    $('#view1').css('background', 'url(' + imageNginxPath + vm.merchant.idCardFacePhoto + ')');
                }
                if(vm.merchant.idCardBackPhoto != null && vm.merchant.idCardBackPhoto != 'undefined' && vm.merchant.idCardBackPhoto != '' && vm.merchant.idCardBackPhoto != 'none') {
                    $('#view2').css('background', 'url(' + imageNginxPath + vm.merchant.idCardBackPhoto + ')');
                }
                if(vm.merchant.avatar != null && vm.merchant.avatar != 'undefined' && vm.merchant.avatar != '' && vm.merchant.avatar != 'none') {
                    $('#view3').css('background', 'url(' + imageNginxPath + vm.merchant.avatar + ')');
                }

                if(vm.merchant.photos != null && vm.merchant.photos != 'undefined' && vm.merchant.photos != '' && vm.merchant.photos != 'none') {
                    var photosPath = vm.merchant.photos.split(',');
                    var photosEelements = $('.imgAll>ul');
                    photosEelements.empty();
                    for(var i = 0; i < photosPath.length; i++) {
                        photosEelements.append(
                            '<li data-delid="' + i + '" data-delname="' + imageNginxPath + photosPath[i] + '">' +
                                '<img src="' + imageNginxPath + photosPath[i] + '" alt="" class="imsg">'+
                            '</li>'
                        );
                    }
                    $('.imgAll>ul>li>.delImg').off().on('click',function(){
                        var src = $(this).prev().attr('src');
                        $.ajax({
                            url: baseURL + "operation/merchant/deleteImage",
                            data: {
                                "imageName": src.split('/')[4]
                            },
                            type: "post",
                            async: false,
                            success: function(r) {
                                deletePhotos(src);
                            }
                        });
                        $(this).parent().fadeOut('slow',function(){
                            $(this).remove();
                        });
                    });
                }
             });
        },
        reload: function (event) {

            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                 postData:{'merchantName':vm.q.merchantName,'status' : vm.q.status},
                 page:page
             }).trigger("reloadGrid");
              $("#text1").attr("status", "Y");
        },
        //获取客户经理信息
        getSalesManInfo: function(salesman) {
            //加载公司树
            $.get(baseURL + "sys/user/info/" + salesman, function(r){
                vm.merchant.salesManName = r.user.realName;
                $("#salesManName").val(r.user.realName);
            })
        },
        //加载省下拉框 d
        getProvince: function(){
            var parentCode=0;
            var group = $("#province");
            group.empty();
            var url = "sys/twbarea/infoList/"+parentCode;
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                success: function(r){
                    for(var i=0;i<r.length;i++) {
                        if(vm.merchant.province == r[i].areaCode) {
                            group.append("<option value='"+r[i].areaCode+"' selected>"+r[i].name+"</option>");
                        }
                    }
                }
            });
        },
        //加载市下拉框 d
        getCity: function(parentCode){
            var group = $("#city");
            group.empty();
            if(parentCode >0 && parentCode != null) {
                var url = "sys/twbarea/infoList/"+parentCode;
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    success: function(r){
                        for(var i=0;i<r.length;i++) {
                            if(vm.merchant.city == r[i].areaCode) {
                                group.append("<option value='"+r[i].areaCode+"' selected>"+r[i].name+"</option>");
                            }
                        }
                    }
                });
            }
        },
        //加载区下拉菜单 d
        getArea: function(parentCode){
            var group = $("#area");
            group.empty();
            if(parentCode >0 && parentCode != null) {
                group.empty();
                var url = "sys/twbarea/infoList/"+parentCode;
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    success: function(r){
                        for(var i=0;i<r.length;i++) {
                            if(vm.merchant.area == r[i].areaCode) {
                                group.append("<option value='"+r[i].areaCode+"' selected>"+r[i].name+"</option>");
                            }
                        }
                    }
                });
            }
        },
        //加载镇/街道下拉菜单 d
        getTown: function(parentCode){
            var group = $("#town");
            group.empty();
            if(parentCode >0 && parentCode != null) {
                var url = "sys/twbarea/infoList/"+parentCode;
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    success: function(r){
                        for(var i=0;i<r.length;i++) {
                            if(vm.merchant.town == r[i].areaCode) {
                                group.append("<option value='"+r[i].areaCode+"' selected>"+r[i].name+"</option>");
                            }
                        }
                    }
                });
            }
        },
        deptTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择公司",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#deptLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = dept_ztree.getSelectedNodes();
                    //选择上级公司
                    vm.merchant.agentId = node[0].deptId;
                    vm.merchant.agentName = node[0].name;
                    layer.close(index);
                    //隐藏div层
                    $("#deptLayer").hide();
                }
            });
        },
        merchantTypeTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择店铺分类",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#merchantTypeLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = merchantType_ztree.getSelectedNodes();
                    //选择上级店铺
                    vm.merchant.typeId = node[0].categoryId;
                    vm.merchant.typeName = node[0].name;
                    layer.close(index);
                    //隐藏div层
                    $("#merchantTypeLayer").hide();
                }
            });
        }
	}
});

