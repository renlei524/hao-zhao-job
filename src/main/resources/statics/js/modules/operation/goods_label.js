$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'operation/merchantproductcustomerlabel/list',
        datatype: "json",
        colModel: [
            { label: 'labelId', name: 'labelId', index: 'label_id', width: 50, key: true ,hidden: true},
            { label: '商家名称', name: 'merchantName', index: 'merchant_id', width: 80 },
            { label: '标签名称', name: 'labelName', index: 'label_name', width: 80 },
            { label: '排序号', name: 'sortNo', index: 'sort_no', width: 80 },
            { label: '创建时间', name: 'createTime', index: 'create_time', width: 80 },
            { label: '审核状态', name: 'status', index: 'status', width: 80, formatter: function(item, index){
                    if(item == 5){
                        return '<span class="label label-success">正常使用</span>';
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
        multiselect: false,
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
        tMerchantProductCustomerLabel: {},
        q: {
            search: null,
            currency : null
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.tMerchantProductCustomerLabel = {};
        },
        update: function (event) {
            var labelId = getSelectedRow();
            if(labelId == null){
                return ;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(labelId)
        },
        saveOrUpdate: function (event) {
            $("#labelSaveOrUpdate").blur();
            $("#labelSaveOrUpdate").attr("disabled", "disabled");
            var url = vm.tMerchantProductCustomerLabel.labelId == null ? "operation/tmerchantproductcustomerlabel/save" : "operation/tmerchantproductcustomerlabel/update";
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
                    $("#labelSaveOrUpdate").removeAttr("disabled");
                }
            });
        },
        del: function (event) {
            var labelIds = getSelectedRows();
            if(labelIds == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "operation/tmerchantproductcustomerlabel/delete",
                    contentType: "application/json",
                    data: JSON.stringify(labelIds),
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
        getInfo: function(labelId){
            $.get(baseURL + "operation/merchantproductcustomerlabel/info/"+labelId, function(r){
                vm.tMerchantProductCustomerLabel = r.merchantProductCustomerLabel;
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
        },
        search: function (event) {
            vm.q.currency = "通用";
            vm.reload();
    }}
});