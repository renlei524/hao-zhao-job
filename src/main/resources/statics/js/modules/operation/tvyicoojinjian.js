$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'operation/tvyicoojinjian/list',
        datatype: "json",
        colModel: [
            { label: '商户id', name: 'merchantId', width: 80 , hidden:true},
            { label: '商户名称', name: 'name',index: 'name',width: 80 },
            { label: '真实姓名', name: 'realname', index: 'realname', width: 80 },
            { label: '联系人手机号', name: 'mobile', index: 'mobile', width: 80 },
            { label: '身份证号', name: 'idNo', index: 'idNo', width: 80 },
            { label: '经营地址', name: 'address', index: 'address', width: 80 },
            { label: '联系邮箱', name: 'email', index: 'email', width: 80 },
            { label: '经营类别', name: 'category', index: 'category', width: 80 },
            { label: '营业执照类型', name: 'licenseType',index: 'licenseType',width: 80 }
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
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
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		tVyicooJinjian: {
		    name:'',
		    shortname:'',
		    realname:'',
		    province:"0",//省
            city:"0",//市
            area:"0",//区
            photos:'',
            avatar:null
		},
        //新增时联动菜单重置用省、市、区、街道 d
        selectedProvince: -1,
        selectedCity: -1,
        selectedArea: -1
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
            vm.title = "新增";
            vm.tVyicooJinjian = {merchantId: null,name:null,realname:null,mobile:null,idNo:null,email:null,category:null,
                licenseType:null, province: 1, city: 0, area: 0, town:0, isVoiceFunction: 0};
            vm.twbuser = {id: null,realName:null};

            $('#view,#view1,#view2,#view3,#view4,#view5').css('background', '').css("background", "url(/statics/img/default.png)");
            $('.imgAll>ul').empty();
            vm.getProvince();
		},
		update: function (event) {
			var type = getSelectedRow();
			if(type == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(type)
		},
		saveOrUpdate: function (event) {
		    vm.tVyicooJinjian.beginTime = $("#tVyicooJinjian-beginTime").val();
            vm.tVyicooJinjian.endTime = $("#tVyicooJinjian-endTime").val();
			var url = vm.tVyicooJinjian.type == null ? "operation/tvyicoojinjian/save" : "operation/tvyicoojinjian/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.tVyicooJinjian),
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
		del: function (event) {
			var types = getSelectedRows();
			if(types == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "operation/tvyicoojinjian/delete",
                    contentType: "application/json",
				    data: JSON.stringify(types),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		verification : function(){
            //商户名称
            if(vm.tVyicooJinjian.name == null || vm.tVyicooJinjian.name == "" ){
                alert("商户名称不能为空");
                return true;
            }else if(!( /^[\u4E00-\u9FA5\a-zA-Z0-9_]{1,16}$/.test(document.getElementById('shopName').value))){
                alert("请重新填写商户名称");
                return true;
            }

            //商户简称
            if(vm.tVyicooJinjian.shortname == null || vm.tVyicooJinjian.shortname == "" ){
                alert("商户名称不能为空");
                return true;
            }else if(!( /^[\u4E00-\u9FA5\a-zA-Z0-9_]{1,16}$/.test(document.getElementById('shortName').value))){
                alert("请重新填写商户名称");
                return true;
            }

            //真实姓名
            if(vm.tVyicooJinjian.realname == null || vm.tVyicooJinjian.realname == ""){
                alert("真实姓名不能为空");
                return true;
            }else if(!( /^[\u4E00-\u9FA5\a-zA-Z0-9_]{1,16}$/.test(document.getElementById('realName').value))){
                alert("请重新填写真实姓名");
                return true;
            }

            //联系电话
            if(vm.tVyicooJinjian.mobile == null || vm.tVyicooJinjian.mobile == ""){
                alert("联系人手机号不能为空");
                return true;
            }else if( !(/^1[34578]\d{9}$/.test(document.getElementById('Mobile').value))){
                alert("请重新填写联系人手机号");
                return true;
            }

            //身份证号
            if(vm.tVyicooJinjian.idNo == null || vm.tVyicooJinjian.idNo == ""){
                alert("身份证号码不能为空");
                return true;
            }else if(!(/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/.test(document.getElementById('idCard').value))){
                alert("请重新填写身份证号码");
                return true;
            }

            //省市区
            if(vm.tVyicooJinjian.province == null || vm.tVyicooJinjian.province == ""){
                alert("省不能为空");
                return true;
            }else if(vm.tVyicooJinjian.city == null || vm.tVyicooJinjian.city == ""){
                alert("市不能为空");
                return true;
            }
            else if(vm.tVyicooJinjian.area == null || vm.tVyicooJinjian.area == ""){
                alert("区不能为空");
                return true;
            }

            //经营地址
            if(vm.tVyicooJinjian.address == null || vm.tVyicooJinjian.address == ""){
                alert("省不能为空");
                return true;
            }

            //联系邮箱
            if(vm.tVyicooJinjian.email == null || vm.tVyicooJinjian.email == ""){
                alert("邮箱不能为空");
                return true;
            }else if(!(/\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}/.test(document.getElementById('Email').value))){
                alert("请重新填写邮箱");
                return true;
            }

            //经营类别
            if(vm.tVyicooJinjian.category == null || vm.tVyicooJinjian.category == ""){
                alert("经营类别不能为空");
                return true;
            }

            //营业执照类型
            if(vm.tVyicooJinjian.licenseType == null || vm.tVyicooJinjian.licenseType == ""){
                alert("营业执照类型不能为空");
                return true;
            }

            //营业执照编号
            if(vm.tVyicooJinjian.licenseNo == null || vm.tVyicooJinjian.licenseNo == ""){
                alert("营业执照编号不能为空");
                return true;
            }else if(!(/^\d$/.test(document.getElementById('LicenseNo').value))){

            }

            //营业执照类型
            if(vm.tVyicooJinjian.licenseAddress == null || vm.tVyicooJinjian.licenseAddress == ""){
                alert("营业执照类型不能为空");
                return true;
            }

            //注册号开始、结束日期
            if(vm.tVyicooJinjian.licenseStartDate == null || vm.tVyicooJinjian.licenseStartDate == ""){
                alert("注册号开始日期不能为空");
                return true;
            }else if(vm.tVyicooJinjian.licenseEndDate == null || vm.tVyicooJinjian.licenseEndDate == ""){
                alert("注册号结束日期不能为空");
                return true;
            }

            //开户支行ID
            if(vm.tVyicooJinjian.bankId == null || vm.tVyicooJinjian.bankId == ""){
                alert("开户支行ID不能为空");
                return true;
            }

            //开户支行所在城市编码
            if(vm.tVyicooJinjian.accountCity == null || vm.tVyicooJinjian.accountCity == ""){
                alert("开户支行所在城市编码不能为空");
                return true;
            }

            //开户支行行号
            if(vm.tVyicooJinjian.bankNo == null || vm.tVyicooJinjian.bankNo == ""){
                alert("开户支行行号不能为空");
                return true;
            }

            //开户名称
            if(vm.tVyicooJinjian.accountName == null || vm.tVyicooJinjian.accountName == ""){
                alert("开户名称不能为空");
                return true;
            }

            //银行预留手机号
            if(vm.tVyicooJinjian.accountMobile == null || vm.tVyicooJinjian.accountMobile == ""){
                alert("银行预留手机号不能为空");
                return true;
            }else if( !(/^1[34578]\d{9}$/.test(document.getElementById('AccountMobile').value))){
                alert("请重新填写银行预留手机号手机号");
                return true;
            }

            //公众号

            if(vm.tVyicooJinjian.status){
                if(vm.tVyicooJinjian.wxAppid == null || vm.tVyicooJinjian.wxAppid == ""){
                    alert("有公众号必填（公众号主体需同营业执照名称一致");
                    return true;
                }
                return true;
            }

            //商户微信号
            if(vm.tVyicooJinjian.wechatId == null || vm.tVyicooJinjian.wechatId == ""){
                alert("商户微信号不能为空");
                return true;
            }

            //法人银行卡号
            if(vm.tVyicooJinjian.bankcardNo == null || vm.tVyicooJinjian.bankcardNo == ""){
                alert("法人银行卡号不能为空");
                return true;
            }
            return false;

        },
		getInfo: function(type){
			$.get(baseURL + "operation/tvyicoojinjian/info/"+type, function(r){
                var imageNginxPath = r.imageNginxPath;
                vm.tVyicooJinjian = r.tVyicooJinjian;
                //加载省市区数据
                vm.getProvince();
                vm.getCity(vm.tVyicooJinjian.province);
                vm.getArea(vm.tVyicooJinjian.city);

                $("#tVyicooJinjian-beginTime").val(r.tVyicooJinjian.beginTime);
                $("#tVyicooJinjian-endTime").val(r.tVyicooJinjian.endTime);

            });
		},
		//加载省下拉框 d
        getProvince: function(){
            var areaCode=1;
            var group = $("#province");
            group.empty();
            var url = "operation/tvyicooarea/list/"+areaCode;
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                success: function(r){
                    group.append("<option value='1'>--请选择省--</option>");
                    for(var i=0;i<r.areaList.length;i++) {
                        if(vm.tVyicooJinjian.province == r.areaList[i].areaCode) {
                            group.append("<option value='"+r.areaList[i].areaCode+"' selected>"+r.areaList[i].areaName+"</option>");
                        } else {
                            group.append("<option value='"+r.areaList[i].areaCode+"'>"+r.areaList[i].areaName+"</option>");
                        }
                    }
                }
            });
        },
        //加载市下拉框 d
        getCity: function(areaCode){
            var group = $("#city");
            if (areaCode <=1 || areaCode == null) {
                group.empty();
                group.append("<option value='0'>--请选择市--</option>");
            }else {
                group.empty();
                var url = "operation/tvyicooarea/list/"+areaCode;
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    success: function(r){
                        group.empty();
                        group.append("<option value='0'>--请选择市--</option>");
                        for(var i=0;i<r.areaList.length;i++) {
                            if(vm.tVyicooJinjian.city == r.areaList[i].areaCode) {
                                group.append("<option value='"+r.areaList[i].areaCode+"' selected>"+r.areaList[i].areaName+"</option>");
                            } else {
                                group.append("<option value='"+r.areaList[i].areaCode+"'>"+r.areaList[i].areaName+"</option>");
                            }
                        }
                    }
                });
            }
        },
        //加载区下拉菜单
        getArea: function(areaCode){
            var group = $("#area");
            if (areaCode <=1 || areaCode == null) {
                group.empty();
                group.append("<option value='0'>--请选择区--</option>");
            }else {
                group.empty();
                var url = "operation/tvyicooarea/list/"+areaCode;
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    success: function(r){
                        group.empty();
                        group.append("<option value='0'>--请选择区--</option>");
                        for(var i=0;i<r.areaList.length;i++) {
                            if(vm.tVyicooJinjian.area == r.areaList[i].areaCode) {
                                group.append("<option value='"+r.areaList[i].areaCode+"' selected>"+r.areaList[i].areaName+"</option>");
                            } else {
                                group.append("<option value='"+r.areaList[i].areaCode+"'>"+r.areaList[i].areaName+"</option>");
                             }
                        }
                    }
                });
            }
        },
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		},
		saveOrUpdate: function (event) {
		    vm.tVyicooJinjian.licenseStartDate = $("#tVyicooJinjian-beginTime").val();
            vm.tVyicooJinjian.licenseEndDate = $("#tVyicooJinjian-endTime").val();
            $("#salesMan-window-close-button").blur();
            if(vm.verification()){
                return;
            }else{
                $("#salesMan-window-close-button").attr("disabled", "disabled");
                var url = vm.tVyicooJinjian.id == null ? "operation/tvyicoojinjian/save" : "operation/tvyicoojinjian/update";
                //getMerchantPhotos();
                var provinceName = null;
                var cityName = null;
                var areaName = null;

                if ($("#province").val() != 0) {
                    var provinceIndex=document.getElementById("province").selectedIndex;
                    provinceName = document.getElementById("province").options[provinceIndex].innerHTML;
                }
                if ($("#city").val() != 0) {
                    var cityIndex=document.getElementById("city").selectedIndex;
                    cityName = document.getElementById("city").options[cityIndex].innerHTML;
                }
                if ($("#area").val() != 0) {
                    var areaIndex=document.getElementById("area").selectedIndex;
                    areaName = document.getElementById("area").options[areaIndex].innerHTML;
                }
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.tVyicooJinjian),
                    success: function(r){
                        if(r.code === 0){
                            alert('操作成功', function(index){
                                vm.reload();
                            });
                        }else{
                            alert(r.msg);
                             $("#text1").attr("status", "Y");
                        }
                        $("#salesMan-window-close-button").removeAttr("disabled");
                    }
                });
            }

        },
	}
});
	//省选择触发事件 d
    $("#province").change(function(){
        vm.selectedProvince = 1;
        vm.selectedCity = 0;
        vm.selectedArea = 0;
        vm.selectedTown = 0;
        vm.selectedProvince =  $("#province").val();
        vm.getCity(vm.selectedProvince);
        vm.getArea(vm.selectedCity);
        vm.tVyicooJinjian.province = vm.selectedProvince;
        vm.tVyicooJinjian.city = vm.selectedCity;
        vm.tVyicooJinjian.area = vm.selectedArea;
    });

    //市 选择触发事件 d
    $("#city").change(function(){
        vm.selectedArea = 0;
        vm.selectedTown = 0;
        vm.selectedCity = $("#city").val();
        vm.getArea(vm.selectedCity);
        vm.tVyicooJinjian.city = vm.selectedCity;
        vm.tVyicooJinjian.area = vm.selectedArea;

    });

    //区选择触发事件 d
    $("#area").change(function(){
        vm.selectedTown = 0;
        vm.selectedArea = $("#area").val();
        vm.tVyicooJinjian.area = vm.selectedArea;
    });


    $('.tVyicooJinjian-beginTime, .tVyicooJinjian-endTime').datetimepicker({
            language: 'zh-CN',
            weekStart: 1,
            todayBtn:  1,
    		autoclose: 1,
    		todayHighlight: 1,
    		startView: 2,
    		forceParse: 0,
            showMeridian: 1,
        });

    layui.use('laydate', function(){
        var laydate_1 = layui.laydate;
        //执行一个laydate实例
        laydate_1.render({
            elem: '#tVyicooJinjian-beginTime' //指定元素
            ,/*type: 'datetime',*/
            value: '2017-12-01'
        });

        var laydate_2 = layui.laydate;
        //执行一个laydate实例
        laydate_2.render({
            elem: '#tVyicooJinjian-endTime' //指定元素
            ,/*type: 'datetime',*/
            value: '2029-12-01'
        });
    });