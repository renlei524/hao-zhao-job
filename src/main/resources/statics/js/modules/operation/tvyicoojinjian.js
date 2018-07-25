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
            vm.merchant = {agentId: null,status:0,agentName:null,recommend:true,typeId:null,typeName:null,supportDispatching:0,
                payChannel:1,address:null,simpleAddress:null, province: 1, city: 0, area: 0, town:0,
                commercialName:null, communityName:null, isVoiceFunction: 0};
            vm.twbuser = {id: null,realName:null};

            $('#view,#view1,#view2,#view3').css('background', '').css("background", "url(/statics/img/default.png)");
            vm.getProvince();
            window.onload = function (){
              document.body.onkeydown=function(event){
                if(event.keyCode==13){
                    event.keyCod=0; return false;
                }
              }
            }
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
		}
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