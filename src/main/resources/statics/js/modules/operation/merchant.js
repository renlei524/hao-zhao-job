$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'operation/merchant/list',
        datatype: "json",
        colModel: [
            { label: '商户id', name: 'id', width: 80 , hidden:true},
            { label: '分公司名称', name: 'agentName', width: 80 },
            { label: '商户收款码', name: 'merchantCode', index: 'merchant_code', width: 80,className:'merchant_code' },
            { label: '商户名称(店铺名称)', name: 'merchantName', index: 'merchant_name', width: 80 },
            { label: '商户分类', name: 'typeName', index: 'type_name', width: 80 },
            { label: '本店联系电话', name: 'telphone', index: 'telphone', width: 80 },
            { label: '合同编号', name: 'contractNumber', index: 'contract_number', width: 80 },
            { label: '商品个数', name: '', width: 80 },
            { label: '总收入', name: 'totalIncome', width: 80 , formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",",
                    decimalPlaces: 2}},
            { label: '所属区域', name: 'address', width: 80 },
            { label: '城市经理', name: 'salesmanName', width: 80 },
            { label: '城市经理电话', name: 'salesmanMobile', width: 80 },
            { label: '支付通道', name: 'payChannel', width: 80 , formatter: function(item, index){
                    if(item === 1){
                        return '<span class="label label-primary">原生通道</span>';
                    }
                    if(item === 2){
                        return '<span class="label label-success">微客</span>';
                    }
                    if(item === 3){
                        return '<span class="label label-warning">点点客</span>';
                    }
                }},
            { label: '状态', name: 'status',index: 'status', valign: 'middle', sortable: true, width: '80px', formatter: function(item, index){
                    if(item === 1){
                        return '<span class="label label-primary">创建审核中</span>';
                    }
                    if(item === 2){
                        return '<span class="label label-warning">创建不通过</span>';
                    }
                    if(item === 3){
                        return '<span class="label label-primary">修改审核中</span>';
                    }
                    if(item === 4){
                        return '<span class="label label-warning">修改不通过</span>';
                    }
                    if(item === 5){
                        return '<span class="label label-success">正常使用</span>';
                    }
                    if(item === 6){
                        return '<span class="label label-danger">禁用</span>';
                    }
                }},
            { label: '二维码', name: 'status',index: 'status',valign: 'middle', sortable: true, width: '80px', formatter: function(item, index){

                    return '<button type="button" class="merchant-code btn btn-info btn-xs" data-toggle="modal" data-target=".bs-example-modal-sm">查看</button><div id="add_content"></div>'
                }}
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList : [6,10,15,20,30,50],
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
            $(".merchant-code").click(function() {
                $('#add_content').html(
                   '<div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">'+
                   '<div class="modal-dialog modal-sm" role="document">'+
                   '<div class="modal-content">'+
                   '<div class="modal-header" style = "line-height: 0px;">'+
                   '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>'+
                   '<h4 class="modal-title" id="myDialogo">二维码</h4>'+
                   '</div>'+
                   '<div class="modal-body">'+
                   '<div class="erwei-code">'+
                   '<img src="" alt="" id="merchant_code_image">'+
                   '</div>'+
                   '</div>'+
                   '</div>'+
                   '</div>'+
                   '</div>'
                );
                var merchantCode = $(this).parent().parent().find("td").eq(4).html();
                $.ajax({
                    type: "post",
                    url: baseURL + "operation/merchant/showMerchantCodeImage",
                    data:{
                        "merchantCode": merchantCode
                    },
                    success: function(r) {
                        $("#merchant_code_image").attr("src", "data:image/gif;base64," + r.codeBase64);
                    }
                });



            });
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
});

//部门结构树
var dept_ztree;
var dept_setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "deptId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    }
};

//店铺分类结构树
var merchantType_ztree;
var merchantType_setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "categoryId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    }
};

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
        isReadOnly:null,
        q:{
            communityName:null,
            tWBUserName:null,
            status:0,
            contractNumber: null
        },
        merchant:{
            userId:0,
            status:0,
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
            salesManName:null,
            isVoiceFunction:1,
            contractNumber: null,
            wechatPublicNumber: null
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
        //新增商户方法。新增时入没有选择省市区某个下拉菜单，并且无验证，则将传入数据-1 d
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.merchant = {agentId: null,status:0,agentName:null,recommend:true,typeId:null,typeName:null,supportDispatching:0,
                payChannel:1,address:null,simpleAddress:null, province: -1, city: -1, area: -1, town: -1,
                commercialName:null, communityName:null, isVoiceFunction: 0,contractNumber:null,wechatPublicNumber :null};
            vm.twbuser = {id: null,realName:null};

            $('#view,#view1,#view2,#view3').css('background', '').css("background", "url(/statics/img/default.png)");
            $('.imgAll>ul').empty();
            vm.getDept();
            vm.getmerchantType();
            vm.getProvince();
            vm.getCity(-1);
            vm.getArea(-1);
            vm.getTown(-1);
            window.onload = function (){
              document.body.onkeydown=function(event){
                if(event.keyCode==13){
                    event.keyCod=0; return false;
                }
              }
            }
		},
		update: function (event) {

			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            vm.roleList = {};
            vm.isReadOnly = 'readonly';
            // 公司树
            vm.getDept();
            // 商品种类数
            vm.getmerchantType();
            // 获取当前商户信息
            vm.getInfo(id);
        },
        verification : function(){

            if(vm.merchant.commercialName == null || vm.merchant.commercialName == "" ){
                alert("商户名字不能为空");
                return true;
            }
            if(vm.merchant.loginUsername == null || vm.merchant.loginUsername == ""){
                alert("商户登录名不能为空");
                return true;
            }else if(!( /^[0-9]{11}$/.test(document.getElementById('loginName').value))){
                alert("请重新填写商户登录名");
                return true;
            }
            if(vm.merchant.agentName == null || vm.merchant.agentName == ""){
                alert("分公司不能为空");
                return true;
            }
            /*if(vm.merchant.contractNumber == null || vm.merchant.contractNumber == ""){
                alert("合同编号不能为空");
                return true;
            }*/
            if(vm.merchant.typeName == null || vm.merchant.typeName == ""){
                alert("商铺分类不能为空");
                return true;
            }
            if(vm.merchant.merchantName == null || vm.merchant.merchantName == ""){
                alert("店铺名称不能为空");
                return true;
            }else if(!( /^[\u4E00-\u9FA5\a-zA-Z0-9_]{1,16}$/.test(document.getElementById('merchantName').value))){
                 alert("请重新填写店铺名称");
                 return true;
             }
            if(vm.merchant.province == null || vm.merchant.province == ""){
                alert("商户地址不能为空");
                return true;
            }
            if(vm.merchant.city == null || vm.merchant.city == ""){
                alert("商户地址不能为空");
                return true;
            }
            if(vm.merchant.area == null || vm.merchant.area == ""){
                alert("商户地址不能为空");
                return true;
            }
            if(vm.merchant.town == null || vm.merchant.town == ""){
                alert("商户地址不能为空");
                return true;
            }
            if(vm.merchant.simpleAddress == null || vm.merchant.simpleAddress == ""){
                alert("详细地址不能为空");
                return true;
            }
            if(vm.merchant.latitude == null || vm.merchant.latitude == ""){
                alert("GPS定位不能为空");
                return true;
            }
            if(vm.merchant.longitude == null || vm.merchant.longitude == ""){
                alert("GPS定位不能为空");
                return true;
            }
            if(vm.merchant.contacts == null || vm.merchant.contacts == ""){
                alert("联系人不能为空");
                return true;
            }else if(!( /^[\u4E00-\u9FA5\a-zA-Z0-9_]{2,11}$/.test(document.getElementById('Contact').value))){
                alert("请重新输入联系人");
                return true;
            }
            if(vm.merchant.telphone == null || vm.merchant.telphone == ""){
                alert("本店联系电话不能为空");
                return true;
            }else if(!(/^1[34578]\d{9}$/.test(document.getElementById('Contact_num').value))){
                alert("请重新填写联系电话");
                return true;
            }
            if(vm.merchant.idCard == null || vm.merchant.idCard == ""){
                alert("身份证号码不能为空");
                return true;
            }else if(!(/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/.test(document.getElementById('idCard').value))){
                alert("请重新填写身份证号码");
                return true;
            }
            if(vm.merchant.communityName == null || vm.merchant.communityName == ""){
                alert("社区不能为空");
                return true;
            }
            if(vm.merchant.merchantCode == null || vm.merchant.merchantCode == ""){
                alert("收款码不能为空");
                return true;
            }/*else if(!( /^\d{8}$/.test(document.getElementById('merchantCode').value))){
                alert("请重新填写收款码");
                return true;
            }*/
            if(vm.merchant.merchantLimit == null || vm.merchant.merchantLimit == ""){
                alert("商户限额不能为空");
                return true;
            }else if(!( /^\d*$/.test(document.getElementById('merchantLimit').value))){
                alert("请重新填写商户限额");
                return true;
            }
            if(vm.merchant.serviceCharge == null || vm.merchant.serviceCharge == ""){
                alert("服务费不能为空");
                return true;
            }else if(!( /^\d*$/.test(document.getElementById('serviceCharge').value))){
                alert("请重新填写服务费");
                return true;
            }
            return false;
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
		saveOrUpdate: function (event) {

            $("#text1").blur();
            vm.merchant.latitude = $("#latY").val();
            vm.merchant.longitude = $("#lngX").val();
            vm.merchant.serviceCharge = $("#serviceCharge").val();
            if(vm.verification()){
                return;
            }else{
                $("#text1").attr("disabled", "disabled");
                var url = vm.merchant.id == null ? "operation/merchant/save" : "operation/merchant/update";
                //getMerchantPhotos();
                if ($("#province").val() != -1) {
                    var provinceIndex=document.getElementById("province").selectedIndex;
                    provinceName = document.getElementById("province").options[provinceIndex].innerHTML;
                    vm.merchant.address = provinceName;
                }
                if ($("#city").val() != -1) {
                    var cityIndex=document.getElementById("city").selectedIndex;
                    cityName = document.getElementById("city").options[cityIndex].innerHTML;
                    vm.merchant.address += cityName;
                }
                if ($("#area").val() != -1) {
                    var areaIndex=document.getElementById("area").selectedIndex;
                    areaName = document.getElementById("area").options[areaIndex].innerHTML;
                    vm.merchant.address += areaName;
                }
                if ($("#town").val() != -1) {
                    var townIndex=document.getElementById("town").selectedIndex;
                    townName = document.getElementById("town").options[townIndex].innerHTML;
                    vm.merchant.address += townName;
                }
                vm.merchant.address += vm.merchant.simpleAddress;

                vm.merchant.payChannel = $("input[name='payChannel']:checked").val();
                vm.merchant.supportDispatching = $("input[name='supportDispatching']:checked").val();
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
                        $("#text1").removeAttr("disabled");
                    }
                });
            }

		},
		stop: function(event) {
		    var ids = getSelectedRows();
            if(ids == null){
                return ;
            }
            for(var index in ids) {
                if($("#" + ids[index]).find("td").eq(9).attr("title") != "正常使用") {
                    alert("只能禁用正常使用的商户！");
                    return;
                }
            }
            confirm('确定要禁用选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "operation/merchant/stop",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function(r){
                        if(r.code == 0){
                            alert('禁用成功', function(index){
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
		},
		start: function(event) {
            var ids = getSelectedRows();
            if(ids == null){
                return ;
            }
            for(var index in ids) {
                if($("#" + ids[index] + ">td").eq(9).attr("title") != "禁用") {
                    alert("只能启用禁用的商户！");
                    return;
                }
            }
            confirm('确定要启用选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "operation/merchant/start",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function(r){
                        if(r.code == 0){
                            alert('启用成功', function(index){
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}

			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "operation/merchant/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('删除成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
        getInfo: function(id){
            $.get(baseURL + "operation/merchant/info/"+id, function(r){
                var imageNginxPath = r.imageNginxPath;
                vm.merchant = r.merchant;
                $("#latY").val(vm.merchant.latitude);
                $("#lngX").val(vm.merchant.longitude);
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
                                '<img src="' + imageNginxPath + photosPath[i] + '" alt="" class="imsg">' +
                                '<i class="delImg">X</i>' +
                            '</li>'
                        );
                    }
                    $('.imgAll>ul>li>.delImg').off().on('click',function(){
                        var src = $(this).prev().attr('src');
                        deletePhotos(src);
                        /*$.ajax({
                            url: baseURL + "operation/merchant/deleteImage",
                            data: {
                                "imageName": src.split('/')[4]
                            },
                            type: "post",
                            async: false,
                            success: function(r) {
                                deletePhotos(src);
                            }
                        });*/
                        $(this).parent().fadeOut('slow',function(){
                            $(this).remove();
                        });
                    });
                }

                //清空商户名字弹出框 查询关键字数据 d
                vm.q.tWBUserName = null;
                //清空社区弹出框 查询关键字数据 d
                vm.q.communityName = null;
                //加载商户名字弹出框数据 d
                commercialNameReload();
                //加载社区弹出框数据 d
                communityModalReload();
             });
        },
        //获取客户经理信息
        getSalesManInfo: function(salesman) {
            //加载公司树
            $.get(baseURL + "sys/user/info/" + salesman, function(r){
                vm.merchant.salesManName = r.user.realName;
                $("#salesManName").val(r.user.realName);
            })
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                 postData:{'merchantName':vm.q.merchantName,'status' : vm.q.status,'contractNumber': vm.q.contractNumber},
                 page:1
             }).trigger("reloadGrid");
             $("#text1").attr("status", "Y");
        },
        getDept: function(){
            //加载公司树
            $.get(baseURL + "sys/dept/list", function(r){
                dept_ztree = $.fn.zTree.init($("#deptTree"), dept_setting, r);
                var node = dept_ztree.getNodeByParam("deptId", vm.merchant.agentId);
                if(node != null){
                    dept_ztree.selectNode(node);
                    vm.merchant.deptName = node.name;
                }
            })
        },
        getmerchantType: function(){
            //加载店铺分类树
            $.get(baseURL + "sys/merchantcategory/list", function(r){
                merchantType_ztree = $.fn.zTree.init($("#merchantTypeTree"), merchantType_setting, r);
                var node = merchantType_ztree.getNodeByParam("categoryId", vm.merchant.typeId);
                if(node != null){
                    merchantType_ztree.selectNode(node);
                    vm.merchant.name = node.name;
                }
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
                    group.append("<option value='-1'>--请选择省--</option>");
                    for(var i=0;i<r.length;i++) {
                        if(vm.merchant.province == r[i].areaCode) {
                            group.append("<option value='"+r[i].areaCode+"' selected>"+r[i].name+"</option>");
                        } else {
                            group.append("<option value='"+r[i].areaCode+"'>"+r[i].name+"</option>");
                        }
                    }
                }
            });
        },
        //加载市下拉框 d
        getCity: function(parentCode){
            var group = $("#city");
            if (parentCode <=0 || parentCode == null) {
                group.empty();
                group.append("<option value='-1'>--请选择市--</option>");
            }else {
                group.empty();
                var url = "sys/twbarea/infoList/"+parentCode;
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    success: function(r){
                        group.empty();
                        group.append("<option value='-1'>--请选择市--</option>");
                        for(var i=0;i<r.length;i++) {
                            if(vm.merchant.city == r[i].areaCode) {
                                group.append("<option value='"+r[i].areaCode+"' selected>"+r[i].name+"</option>");
                            } else {
                                group.append("<option value='"+r[i].areaCode+"'>"+r[i].name+"</option>");
                            }
                        }
                    }
                });
            }
        },
        //加载区下拉菜单 d
        getArea: function(parentCode){
            var group = $("#area");
            if (parentCode <=0 || parentCode == null) {
                group.empty();
                group.append("<option value='-1'>--请选择区--</option>");
            }else {
                group.empty();
                var url = "sys/twbarea/infoList/"+parentCode;
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    success: function(r){
                        group.empty();
                        group.append("<option value='-1'>--请选择区--</option>");
                        for(var i=0;i<r.length;i++) {
                            if(vm.merchant.area == r[i].areaCode) {
                                group.append("<option value='"+r[i].areaCode+"' selected>"+r[i].name+"</option>");
                            } else {
                                group.append("<option value='"+r[i].areaCode+"'>"+r[i].name+"</option>");
                             }
                        }
                    }
                });
            }
        },
        //加载镇/街道下拉菜单 d
        getTown: function(parentCode){
            var group = $("#town");
            if (parentCode <=0 || parentCode == null) {
                group.empty();
                group.append("<option value='-1'>--请选择镇/街道--</option>");
            }else {
                group.empty();
                var url = "sys/twbarea/infoList/"+parentCode;
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    success: function(r){
                        group.empty();
                        group.append("<option value='-1'>--请选择镇/街道--</option>");
                        for(var i=0;i<r.length;i++) {
                            if(vm.merchant.town == r[i].areaCode) {
                                group.append("<option value='"+r[i].areaCode+"' selected>"+r[i].name+"</option>");
                            } else {
                                group.append("<option value='"+r[i].areaCode+"'>"+r[i].name+"</option>");
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

//省选择触发事件 d
$("#province").change(function(){
    vm.selectedProvince = -1;
    vm.selectedCity = -1;
    vm.selectedArea = -1;
    vm.selectedTown = -1;
    vm.selectedProvince =  $("#province").val();
    vm.getCity(vm.selectedProvince);
    vm.getArea(vm.selectedCity);
    vm.getTown(vm.selectedArea);
    vm.merchant.province = vm.selectedProvince;
    vm.merchant.city = vm.selectedCity;
    vm.merchant.area = vm.selectedArea;
    vm.merchant.town = vm.selectedTown;
});

//市 选择触发事件 d
$("#city").change(function(){
    vm.selectedArea = -1;
    vm.selectedTown = -1;
    vm.selectedCity = $("#city").val();
    vm.getArea(vm.selectedCity);
    vm.getTown(vm.selectedArea);
    vm.merchant.city = vm.selectedCity;
    vm.merchant.area = vm.selectedArea;
    vm.merchant.town = vm.selectedTown;
});

//区选择触发事件 d
$("#area").change(function(){
    vm.selectedTown = -1;
    vm.selectedArea = $("#area").val();
    vm.getTown(vm.selectedArea);
    vm.merchant.area = vm.selectedArea;
    vm.merchant.town = vm.selectedTown;
});

//查询  商户名字  弹出窗代码 d
$('#myModal').on('show.bs.modal', function () {
    $("#commercialName").jqGrid({
                url: baseURL + 'operation/twbuser/list',
                datatype: "json",
                colModel: [
                    { label: '用户id', name: 'id', hidden:true,fixed:true},
                    { label: '用户手机号码',fixed:true, name: 'mobile'},
                    { label: '用户真实姓名',fixed:true, name: 'realName',width: 160},
                    { label: '用户身份证号码',fixed:true, name: 'idCard' },
                    { label: '状态', name: 'status',width: 80 ,formatter: function(item, index){
                            if(item === 0){
                                return '<span class="label label-primary">正常</span>';
                            }
                            if(item === 1){
                                return '<span class="label label-success">异常</span>';
                            } if(item === 2){
                                return '<span class="label label-success">冻结</span>';
                            }}}
                ],
                viewrecords: true,
                height: 235,
                rowNum: 6,
                rowList : [6,10,15,20,30,50],
                rownumbers: false,
                rownumWidth: 45,
                autowidth:true,
                multiselect: true,
                pager: "#commercialNamePager",
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
                }
            });
});

//商户弹出窗关闭按钮代码 d
$("#window-close-button").click(function () {
    var rowKey = getSelecteRow();
    if(rowKey == null){
        return ;
    }
    var grid = $("#commercialName");
    var rowKey = grid.getGridParam("selrow");
    var tWBUserDate =  $("#commercialName").getRowData(rowKey);
    vm.merchant.userId = tWBUserDate.id;
    vm.merchant.commercialName = tWBUserDate.realName;
    $("#userName").val(tWBUserDate.realName);
    $('#myModal').modal('hide');
});

//商户弹出窗查询按钮代码 d
$("#commercialNameReload").click(function () {
    commercialNameReload();
});

//商户弹出框重加载代码 d
function commercialNameReload() {
    var page = $("#commercialName").jqGrid('getGridParam','page');
    $("#commercialName").jqGrid('setGridParam',{
        postData:{'realName':vm.q.tWBUserName},
        page:1
    }).trigger("reloadGrid");
}
//商户弹出窗查询选择条件为一条限制 d
function getSelecteRow() {
    var grid = $("#commercialName");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
        alert("请选择一条记录");
        return ;
    }
    var selectedIDs = grid.getGridParam("selarrrow");
    if(selectedIDs.length > 1){
        alert("只能选择一条记录");
        return ;
    }
    return selectedIDs[0];
}

$("#userName").click(function () {
    $("#qTWBUserNameSearch").val(null);
    commercialNameReload();
});

//社区  弹出窗代码 d
$('#communityModal').on('shown.bs.modal', function () {
// $(function () {
    $("#community").jqGrid({
        url: baseURL + 'community/community/list',
        datatype: "json",
        colModel: [
            { label: '社区ID', name: 'deptId', hidden:true},
            { label: '社区名称', name: 'name' },
            { label: '负责人', name: 'leader'},
            { label: '负责人电话', name: 'leaderTel' },
            { label: '状态', name: 'status',width: 50, formatter: function(item, index){
                    if(item === 0){
                        return '<span class="label label-primary">禁用</span>';
                    }
                    if(item === 1){
                        return '<span class="label label-success">正常</span>';
                    }}}
        ],
        viewrecords: true,
        height: 235,
        rowNum: 6,
        rowList : [10,15,20,30,50],
        rownumbers: false,
        rownumWidth: 35,
        autowidth:true,
        multiselect: true,
        pager: "#communityPager",
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
        }
    });
});

//社区 弹出 窗关闭按钮代码 d
$("#community-window-close-button").click(function () {
    var rowKey = getCommunitySelecteRow();
    if(rowKey == null){
        return ;
    }
    var grid = $("#community");
    var rowKey = grid.getGridParam("selrow");
    var communityDate =  $("#community").getRowData(rowKey);
    vm.merchant.communityId = communityDate.deptId;
    vm.merchant.communityName = communityDate.name;
    $("#communityName").val(communityDate.name);
    $('#communityModal').modal('hide');

});

//社区 弹出 窗查询按钮代码 d
$("#communityModalReload").click(function () {
    communityModalReload();
});

//社区弹出框重加载代码 d
function communityModalReload() {
    var page = $("#community").jqGrid('getGridParam','page');
    $("#community").jqGrid('setGridParam',{
        postData:{'communityName':vm.q.communityName},
        page:1
    }).trigger("reloadGrid");
}

//社区  弹出窗查询选择条件为一条限制 d
function getCommunitySelecteRow() {
    var grid = $("#community");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
        alert("请选择一条记录");
        return ;
    }
    var selectedIDs = grid.getGridParam("selarrrow");
    if(selectedIDs.length > 1){
        alert("只能选择一条记录");
        return ;
    }
    return selectedIDs[0];
}

$("#communityName").click(function () {
    $("#qCommunityNameSearch").val(null);
    communityModalReload();
});

//查询  客户经理  弹出窗代码 d
$('#salesManModal').on('show.bs.modal', function () {
    $("#salesMan").jqGrid({
        url: baseURL + 'sys/user/list',
        datatype: "json",
        colModel: [
            { label: '用户ID', name: 'userId', index: "user_id", width: 45, key: true, hidden:true},
            // { label: '账号', name: 'userName', width: 75 },
            { label: '员工姓名', name: 'realName', width: 75 },
            { label: '所属公司', name: 'deptName', sortable: false, width: 120 },
            { label: '邮箱', name: 'email', width: 140 },
            { label: '手机号', name: 'mobile', width: 130 },
            { label: '状态', name: 'status', width: 60, formatter: function(value, options, row){
                    return value === 0 ?
                        '<span class="label label-danger">禁用</span>' :
                        '<span class="label label-success">正常</span>';
                }}
            // { label: '创建时间', name: 'createTime', index: "create_time", width: 85,formatter:function(value,options,row){
            //         return new Date(value).Format('yyyy-MM-dd HH:mm:ss');}}
        ],
        viewrecords: true,
        height: 235,
        rowNum: 6,
        rowList : [6,10,15,20,30,50],
        rownumbers: false,
        rownumWidth: 45,
        autowidth:true,
        multiselect: true,
        pager: "#salesManPager",
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
            $("#salesMan").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
            //删除第一条admin数据
            $("#salesMan").jqGrid("delRowData",1);
        },
    });
});

//客户经理弹出窗关闭按钮代码 d
$("#salesMan-window-close-button").click(function () {

    var rowKey = getSalesManSelecteRow();
    if(rowKey == null){
        return ;
    }
    var grid = $("#salesMan");
    var rowKey = grid.getGridParam("selrow");
    var salesManDate =  $("#salesMan").getRowData(rowKey);
    vm.merchant.salesman = salesManDate.userId;
    vm.merchant.salesManName = salesManDate.realName;
    $("#salesManName").val(salesManDate.realName);
    $('#salesManModal').modal('hide');
});

//客户经理弹出窗查询按钮代码 d
$("#salesManNameSearch").click(function () {
    salesManNameSearchReload();
});

//客户经理弹出框重加载代码 d
function salesManNameSearchReload() {
    var page = $("#salesMan").jqGrid('getGridParam','page');
    var salesManNameSearch = $("#qSalesManNameSearch").val();
    $("#salesMan").jqGrid('setGridParam',{
        postData:{'userName':salesManNameSearch},
        page:1
    }).trigger("reloadGrid");
}
//客户经理弹出窗查询选择条件为一条限制 d
function getSalesManSelecteRow() {
    var grid = $("#salesMan");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
        alert("请选择一条记录");
        return ;
    }
    var selectedIDs = grid.getGridParam("selarrrow");
    if(selectedIDs.length > 1){
        alert("只能选择一条记录");
        return ;
    }
    return selectedIDs[0];
}
//客户经理弹窗数据清空 d
$("#salesManName").click(function () {
    $("#qSalesManNameSearch").val(null);
    salesManNameSearchReload();
});

//判断商户登录名
function checkloginName(){
    var loginName = document.getElementById('loginName').value;
    if(loginName == "" || loginName == null){
        return document.getElementById('uidt').innerHTML = '';
    } else if(!(/^1[34578]\d{9}$/.test(loginName))){
        return document.getElementById('uidt').innerHTML = '*请输入手机号';
    } else{
        return document.getElementById('uidt').innerHTML = '';
    }
}


//判断用户名称
function checkName(){
    var phone = document.getElementById('Contact').value;
    if(phone == "" || phone == null){
        return document.getElementById('uidt1').innerHTML = '';
    }else if(!( /^[\u4E00-\u9FA5\a-zA-Z0-9_]{2,11}$/.test(phone))){
      return document.getElementById('uidt1').innerHTML = '*不能含有特殊字符';
    }else{
        return document.getElementById('uidt1').innerHTML = '';
    }
}



//判断电话号码
function checkPhone(){
    var phone = document.getElementById('Contact_num').value;
    if(phone == "" || phone == null){
        return document.getElementById('uidt2').innerHTML = '';
    }else if(!(/^1[34578]\d{9}$/.test(phone))){
        return document.getElementById('uidt2').innerHTML = '*请输入正确的手机号';
    }else{
        return document.getElementById('uidt2').innerHTML = '';
    }
 }

//判断身份证号
function checkidCard(){
    var idCard = document.getElementById('idCard').value;
    if(idCard == "" || idCard == null){
        return document.getElementById('uidt3').innerHTML = '';
    }else if(!(/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/.test(idCard))){
      return document.getElementById('uidt3').innerHTML = '*请输入正确的身份证号';
    }else{
        return document.getElementById('uidt3').innerHTML = '';
    }
}

//判断商户名称
function checkmerchantName(){
    var merchantName = document.getElementById('merchantName').value;
    if(merchantName == "" || merchantName == null){
        return document.getElementById('uidt4').innerHTML = '';
    }else if(!( /^[\u4E00-\u9FA5\a-zA-Z0-9_]{1,16}$/.test(merchantName))){
        return document.getElementById('uidt4').innerHTML = '*不能含有特殊字符';
    }else{
         return document.getElementById('uidt4').innerHTML = '';
    }
}
//判断收款码
function checkmerchantCode(){
    var merchantCode = document.getElementById('merchantCode').value;
    if(merchantCode == "" || merchantCode == null){
        return document.getElementById('uidt5').innerHTML = '';
    }/*else if(!( /^\d{8}$/.test(merchantCode))){
        return document.getElementById('uidt5').innerHTML = '*输入8位收款码';
    }*/else{
        var url = "operation/merchant/infoByCode/"+merchantCode;
        $.ajax({
            type: "POST",
            url: baseURL + url,
            contentType: "application/json",
            success: function(r){
                if (r.merchantCode != null){
                    if(vm.merchant.id != r.merchantCode.id){
                        return $('#uidt5').html('当前收款码已存在！');
                    }else {
                        return $('#uidt5').html(null);
                    }
                }else {
                    return $('#uidt5').html(null);
                }
            }
        });
    }
}
//判断商户限额
function checkmerchantLimit(){
    var merchantLimit = document.getElementById('merchantLimit').value;
    if(merchantLimit == "" || merchantLimit == null){
        return document.getElementById('uidt6').innerHTML = '';
    }else if(!( /^\d*$/.test(merchantLimit))){
        return document.getElementById('uidt6').innerHTML = '*只能输入数字';
    }else{
        return document.getElementById('uidt6').innerHTML = '';
    }
}
//判断服务费
function checkserviceCharge(){
    var serviceCharge = document.getElementById('serviceCharge').value;
    if(serviceCharge == "" || serviceCharge == null){
        return document.getElementById('uidt7').innerHTML = '';
    }else if(!( /^\d*$/.test(serviceCharge))){
      return document.getElementById('uidt7').innerHTML = '*只能输入数字';
    }else{
        return document.getElementById('uidt7').innerHTML = '';
    }
}

$("#merchantCode").blur(function(){
    checkmerchantCode();
});
