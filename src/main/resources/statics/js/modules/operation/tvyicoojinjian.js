$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'operation/tvyicoojinjian/list',
        datatype: "json",
        colModel: [			
			{ label: '商户类型', name: 'type', index: 'type', width: 50 },
			{ label: '商户名称', name: 'name', index: 'name', width: 80 }, 			
			{ label: '商户简称（4-15字）', name: 'shortname', index: 'shortname', width: 80 }, 			
			{ label: '商户真实姓名', name: 'realname', index: 'realname', width: 80 }, 			
			{ label: '联系人手机号', name: 'mobile', index: 'mobile', width: 80 }, 			
			{ label: '身份证号', name: 'idNo', index: 'id_no', width: 80 }, 			
			{ label: '省', name: 'gbProvinceNo', index: 'gb_province_no', width: 80 }, 			
			{ label: '市', name: 'gbCityNo', index: 'gb_city_no', width: 80 }, 			
			{ label: '区', name: 'gbDistrictNo', index: 'gb_district_no', width: 80 }, 			
			{ label: '经营地址', name: 'address', index: 'address', width: 80 }, 			
			{ label: '联系邮箱', name: 'email', index: 'email', width: 80 }, 			
			{ label: '经营类别', name: 'category', index: 'category', width: 80 }, 			
			{ label: '营业执照类型', name: 'licenseType', index: 'license_type', width: 80 }, 			
			{ label: '营业执照编号', name: 'licenseNo', index: 'license_no', width: 80 }, 			
			{ label: '营业执照地址', name: 'licenseAddress', index: 'license_address', width: 80 }, 			
			{ label: '注册号开始日期 （例：2017-12-01）', name: 'licenseStartDate', index: 'license_start_date', width: 80 }, 			
			{ label: '注册号结束日期 （例：2029-12-01 永久为-1）', name: 'licenseEndDate', index: 'license_end_date', width: 80 }, 			
			{ label: '开户支行ID', name: 'bankId', index: 'bank_id', width: 80 }, 			
			{ label: '开户支行所在城市编码', name: 'accountCity', index: 'account_city', width: 80 }, 			
			{ label: '开户支行行号', name: 'bankNo', index: 'bank_no', width: 80 }, 			
			{ label: '帐户类型 0：对私 1：对公', name: 'accountType', index: 'account_type', width: 80 }, 			
			{ label: '开户名称', name: 'accountName', index: 'account_name', width: 80 }, 			
			{ label: '银行预留手机号', name: 'accountMobile', index: 'account_mobile', width: 80 }, 			
			{ label: '有无公众号 1：有公众号 2：无公众号', name: 'selfAppid', index: 'self_appid', width: 80 }, 			
			{ label: '有公众号必填（公众号主体需同营业执照名称一致）', name: 'wxAppid', index: 'wx_appid', width: 80 }, 			
			{ label: '商户微信号', name: 'wechatId', index: 'wechat_id', width: 80 }, 			
			{ label: '法人银行卡号', name: 'bankcardNo', index: 'bankcard_no', width: 80 }, 			
			{ label: '营业执照照片', name: 'licensePic', index: 'license_pic', width: 80 }, 			
			{ label: '法人身份证正面照片', name: 'idFrontPic', index: 'id_front_pic', width: 80 }, 			
			{ label: '法人身份证反面照片', name: 'idBackPic', index: 'id_back_pic', width: 80 }, 			
			{ label: '对私：法人结算银行卡照片 对公：开户许可证', name: 'bankcardPic', index: 'bankcard_pic', width: 80 }, 			
			{ label: '店铺门头照片', name: 'shopPic', index: 'shop_pic', width: 80 }, 			
			{ label: '经营场所内照片', name: 'extraPic1', index: 'extra_pic1', width: 80 }, 			
			{ label: '收银台招牌照片', name: 'extraPic2', index: 'extra_pic2', width: 80 }, 			
			{ label: '支付方式配置json串', name: 'payment', index: 'payment', width: 80 }, 			
			{ label: '返回结果状态。0：成功', name: 'status', index: 'status', width: 80 }, 			
			{ label: '返回信息', name: 'msg', index: 'msg', width: 80 }, 			
			{ label: '商户id', name: 'merchantId', index: 'merchant_id', width: 80 }			
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
		    province:"",//省
            city:"",//市
            area:""//区
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
			vm.tVyicooJinjian = {

			};
			 vm.getProvince();
			 vm.getCity(-1);
             vm.getArea(-1);
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
		getInfo: function(type){
			$.get(baseURL + "operation/tvyicoojinjian/info/"+type, function(r){
                vm.tVyicooJinjian = r.tVyicooJinjian;
                //加载省市区数据
                vm.getProvince();
                vm.getCity(vm.tVyicooJinjian.province);
                vm.getArea(vm.tVyicooJinjian.city);
            });
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
                        if(vm.tVyicooJinjian.province == r[i].areaCode) {
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
                            if(vm.tVyicooJinjian.city == r[i].areaCode) {
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
                            if(vm.tVyicooJinjian.area == r[i].areaCode) {
                                group.append("<option value='"+r[i].areaCode+"' selected>"+r[i].name+"</option>");
                            } else {
                                group.append("<option value='"+r[i].areaCode+"'>"+r[i].name+"</option>");
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
        vm.tVyicooJinjian.province = vm.selectedProvince;
        vm.tVyicooJinjian.city = vm.selectedCity;
        vm.tVyicooJinjian.area = vm.selectedArea;
    });

    //市 选择触发事件 d
    $("#city").change(function(){
        vm.selectedArea = -1;
        vm.selectedTown = -1;
        vm.selectedCity = $("#city").val();
        vm.getArea(vm.selectedCity);
        vm.tVyicooJinjian.city = vm.selectedCity;
        vm.tVyicooJinjian.area = vm.selectedArea;

    });

    //区选择触发事件 d
    $("#area").change(function(){
        vm.selectedTown = -1;
        vm.selectedArea = $("#area").val();
        vm.tVyicooJinjian.area = vm.selectedArea;
    });