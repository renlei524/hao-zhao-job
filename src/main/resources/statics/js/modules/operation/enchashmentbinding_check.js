$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'operation/enchashmentBindingCheck/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true ,hidden:true},
            // { label: '1:用户提现 2:商户提现', name: 'objectType', index: 'object_type', width: 80 },
            { label: '商户名称', name: 'objectName', width: 80 },

            { label: '银行卡号', name: 'account', index: 'account', width: 80 },
            // { label: '开户银行', name: 'bank', index: 'bank', width: 80 },
            // { label: '真实姓名', name: 'realName', index: 'real_name', width: 80 },
            { label: '开户行', name: 'subbranch', width: 80 },
            { label: '开户名', name: 'accountName', index: 'account_name', width: 80 },
            // { label: '银联号', name: 'bankUnionId', index: 'bank_union_id', width: 80 }
            { label: '是否是默认提现账号', name: 'isDefault', index: 'is_default', width: 80 , formatter: function(item, index){
                    if(item === 1){
                        return '<span class="label label-primary">是</span>';
                    }
                    if(item === 0){
                        return '<span class="label label-success">否</span>';
                    }
                }},
            { label: '状态', name: 'status', index: 'status', width: 80 , formatter: function(item, index){
                    if(item === 1){
                        return '<span class="label label-primary">正常</span>';
                    }
                    if(item === 2){
                        return '<span class="label label-success">创建审核中</span>';
                    }
                    if(item === 3){
                        return '<span class="label label-warning">审核不通过</span>';
                    }
                    if(item === 4){
                        return '<span class="label label-warning">修改审核中</span>';
                    }
                    if(item === 5){
                        return '<span class="label label-warning">修改不通过</span>';
                    }
                    if(item === 6){
                        return '<span class="label label-warning">禁用</span>';
                    }
                }}
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
		q:{
            objectName:null,
            status:0
        },
		enchashmentBindingCheck: {
		    checkStatus: 0
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		check: function(){
			var id = getSelectedRow();
			if(id == null){
                return ;
            }
            vm.showList = false;
            vm.title = "审核";
            $("#remark").css("display", "block");
			vm.getInfo(id);
		},
		saveOrUpdate: function (event) {
		    $("#bankCardCheck").blur();
            $("#bankCardCheck").attr("disabled", "disabled");

            if(vm.enchashmentBindingCheck.checkStatus == 0 && (vm.enchashmentBindingCheck.remark == null || vm.enchashmentBindingCheck.remark == "")) {
                alert("审批不通过，意见必填！！");
                $("#bankCardCheck").removeAttr("disabled");
                return;
            }
		    var url = "operation/enchashmentBindingCheck/check";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.enchashmentBindingCheck),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
					$("#bankCardCheck").removeAttr("disabled");
				}
			});
		},
		getInfo: function(id){
			$.get(baseURL + "operation/enchashmentBindingCheck/info/"+id, function(r){
                vm.enchashmentBindingCheck = r.enchashmentBindingCheck;
                vm.enchashmentBindingCheck.checkStatus = 0;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	},
	updated: function() {
	    $('input[type=radio][name=status]').change(function() {
            if (this.value == '0') {
                $("#remark").css("display", "block");
            } else {
                $("#remark").css("display", "none");
                vm.enchashmentBindingCheck.remark = null;
            }
        });
	}
});