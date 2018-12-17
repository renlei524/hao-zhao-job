$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'operation/merchantproductcustomerlabel/list_check',
        datatype: "json",
        colModel: [			
			{ label: 'labelId', name: 'labelId', index: 'label_id', width: 50, key: true ,hidden: true},
			{ label: '商家名称', name: 'merchantName', index: 'merchant_id', width: 80 },
			{ label: '标签名称', name: 'labelName', index: 'label_name', width: 80 }, 			
			{ label: '排序号', name: 'sortNo', index: 'sort_no', width: 80 },
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 },
			{ label: '审核状态', name: 'status', index: 'status', width: 80, formatter: function(item, index){
                    if(item == 1){
                        return '<span class="label label-success">创建审核中</span>';
                    }
                    if(item == 2){
                        return '<span class="label label-danger">审核不通过</span>';
                    }
                    if(item == 3){
                        return '<span class="label label-primary">修改审核中</span>';
                    }
                    if(item == 4){
                        return '<span class="label label-danger">修改不通过</span>';
                    }
                }  }
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
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
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		tMerchantProductCustomerLabel: {remark: null,status: 2},
		q: {
            search: null,
            currency : null
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		update: function (event) {
			var labelId = getSelectedRow();
			if(labelId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            document.getElementById("remark").style.display  = "block"
            vm.getInfo(labelId)
		},
		saveOrUpdate: function (event) {
		    $("#labelCheck").blur();
            $("#labelCheck").attr("disabled", "disabled");
            if(vm.tMerchantProductCustomerLabel.status == 2 && (vm.tMerchantProductCustomerLabel.remark == null || vm.tMerchantProductCustomerLabel.remark == "")) {
                alert("审批不通过，意见必填！！");
                $("#labelCheck").removeAttr("disabled");
                return;
            }

			var url = "operation/merchantproductcustomerlabel/check";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.tMerchantProductCustomerLabel),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
					$("#labelCheck").removeAttr("disabled");
				}
			});
		},
		getInfo: function(labelId){
			$.get(baseURL + "operation/merchantproductcustomerlabel/info/"+labelId, function(r){
                vm.tMerchantProductCustomerLabel = r.merchantProductCustomerLabel;
                vm.tMerchantProductCustomerLabel.status = 2;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{
                    "search" :vm.q.search,"currency": vm.q.currency},
                page:page
            }).trigger("reloadGrid");
            vm.q.currency = null;
		}
	},
    updated: function() {
        $('input[type=radio][name=status]').change(function() {
             if (this.value == '2') {
                 $("#remark").css("display", "block");
             } else {
                $("#remark").css("display", "none");
                vm.tMerchantProductCustomerLabel.remark = "";
             }
         });
    }
});
