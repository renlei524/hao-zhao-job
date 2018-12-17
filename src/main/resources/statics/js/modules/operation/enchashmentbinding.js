$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'operation/tenchashmentbinding/list',
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
                }},
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
		q:{
            objectName:null,
            status:0
        },
		tEnchashmentBinding: {
            objectType : 2,
            enchashmentType : 2,
            objectName : null,
            objectId :null,
            isDefault: 1,
            account : null,
            bank : null,
            subbranch : null,
            accountName : null,
            bankUnionId : null,
            subbranchName:null
		},
        q:{
            merchantName: null,
            objectName : null,
            value: null,
            status:0
        }
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.tEnchashmentBinding = { objectType : 2,enchashmentType : 2, isDefault: 1};
            vm.q.merchantName = null;
            $("#objectName").attr("disabled",false);
            window.onload = function (){
              document.body.onkeydown=function(event){
                if(event.keyCode==13){
                    event.keyCod=0; return false;
                }
              }
            };
        },
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            vm.q.merchantName = null;
            $("#objectName").attr("disabled",true);
            vm.getInfo(id)
		},
        verification : function(){
            if(vm.tEnchashmentBinding.objectName == null || vm.tEnchashmentBinding.objectName == ""){
                alert("商户名或用户名不能为空");
                return true;
            }
            if(vm.tEnchashmentBinding.account == null || vm.tEnchashmentBinding.account == ""){
                alert("银行卡号不能为空");
                return true;
            }
            /*if(vm.tEnchashmentBinding.account.length < 16){
                alert("请输入正确的银行卡号！");
                return true;
            }
            var reg=/^([1-9]{1})(\d{15}|\d{18})$/;
            if(!reg.test(vm.tEnchashmentBinding.account)){
                alert("请输入正确的银行卡号！");
                return true;
            }*/
            if(vm.tEnchashmentBinding.subbranch == null || vm.tEnchashmentBinding.subbranch == ""){
                alert("开户行不能为空");
                return true;
            }
            if(vm.tEnchashmentBinding.accountName == null || vm.tEnchashmentBinding.accountName == ""){
                alert("开户名不能为空");
                return true;
            }
            return false;
        },
		saveOrUpdate: function (event) {
            $("#text1").blur();
            //验证非空等
            if (vm.verification()) {
                return;
            }
            $("#text1").attr("disabled", "disabled");
			var url = vm.tEnchashmentBinding.id == null ? "operation/tenchashmentbinding/save" : "operation/tenchashmentbinding/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.tEnchashmentBinding),
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
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "operation/tenchashmentbinding/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
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
		getInfo: function(id){
			$.get(baseURL + "operation/tenchashmentbinding/info/"+id, function(r){
                vm.tEnchashmentBinding = r.tEnchashmentBinding;
            });
		},
        stop: function(event) {
            var ids = getSelectedRows();
            if(ids == null){
                return ;
            }
            for(var index in ids) {
                if($("#" + ids[index]).find("td").eq(8).attr("title") != "正常") {
                    alert("只能禁用正常使用的商户！");
                    return;
                }
            }
            confirm('确定要禁用选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "operation/tenchashmentbinding/stop",
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
                if($("#" + ids[index] + ">td").eq(8).attr("title") != "禁用") {
                    alert("只能启用禁用的商户！");
                    return;
                }
            }
            confirm('确定要启用选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "operation/tenchashmentbinding/start",
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
		reload: function (event) {

			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{
                    'merchantName':vm.q.objectName,
                    'status':vm.q.status
                },
                page:1
            }).trigger("reloadGrid");
             $("#text1").attr("status", "Y");

		}
	}
});


//查询  商户名字  弹出窗代码 d
$('#myModal').on('show.bs.modal', function () {
    $("#commercialName").jqGrid({
        url: baseURL + 'operation/merchant/list',
        datatype: "json",
        colModel: [
            { label: '商户id', name: 'id' , hidden:true},
            { label: '商户名称(店铺名称)', name: 'merchantName'},
            { label: '商户分类', name: 'typeName'},
            { label: '本店联系电话', name: 'telphone' },
            { label: '状态', name: 'status',index: 'status', align: 'center', valign: 'middle', sortable: true, formatter: function(item, index){
                    if(item === 1){
                        return '<span class="label label-primary">创建审核中</span>';
                    }
                    if(item === 2){
                        return '<span class="label label-success">创建不通过</span>';
                    }
                    if(item === 3){
                        return '<span class="label label-warning">修改审核中</span>';
                    }
                    if(item === 4){
                        return '<span class="label label-warning">修改不通过</span>';
                    }
                    if(item === 5){
                        return '<span class="label label-warning">正常使用</span>';
                    }
                    if(item === 6){
                        return '<span class="label label-warning">禁用</span>';
                    }
                }}
        ],
        viewrecords: true,
        height: 235,
        rowNum: 6,
        rowList : [6,15,20,30,50],
        rownumbers: false,
        rownumWidth: 50,
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
    vm.tEnchashmentBinding.objectId = tWBUserDate.id;
    vm.tEnchashmentBinding.objectName = tWBUserDate.merchantName;
    $("#objectName").val(tWBUserDate.merchantName);
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
        postData:{'merchantName':vm.q.merchantName},
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

$("#objectName").click(function () {
    vm.q.merchantName = null;
    commercialNameReload();
});




//开户行 弹出窗代码 d
$('#communityModal').on('shown.bs.modal', function () {
// $(function () {
    $("#community").jqGrid({
        url: baseURL + 'sys/dict/listBank',
        datatype: "json",
        colModel: [
            { label: '开户行code', name: 'code', hidden:true},
            { label: '开户行', name: 'value' },
        ],
        viewrecords: true,
        height: 235,
        rowNum: 6,
        rowList : [6,15,20,30,50],
        rownumbers: false,
        rownumWidth: 50,
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

//开户行 弹出 窗关闭按钮代码 d
$("#community-window-close-button").click(function () {
    var rowKey = getCommunitySelecteRow();
    if(rowKey == null){
        return ;
    }
    var grid = $("#community");
    var rowKey = grid.getGridParam("selrow");
    var communityDate =  $("#community").getRowData(rowKey);
    vm.tEnchashmentBinding.bankUnionId = communityDate.code;
    vm.tEnchashmentBinding.subbranch = communityDate.value;
    $("#subbranch").val(communityDate.value);
    $('#communityModal').modal('hide');

});

//开户行弹出 窗查询按钮代码 d
$("#communityModalReload").click(function () {
    communityModalReload();
});

//开户行弹出框重加载代码 d
function communityModalReload() {
    var page = $("#community").jqGrid('getGridParam','page');
    $("#community").jqGrid('setGridParam',{
        postData:{'value':vm.q.value},
        page:1
    }).trigger("reloadGrid");
}

//开户行  弹出窗查询选择条件为一条限制 d
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

$("#subbranch").click(function () {
    $("#value").val(null);
    vm.q.value = null;
    communityModalReload();
});

