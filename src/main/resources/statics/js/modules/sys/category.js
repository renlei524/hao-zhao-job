var setting = {
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
var ztree;

var vm = new Vue({
    el:'#rrapp',
    data:{
        showList: true,
        title: null,
        merchantCategory:{
            parentName:null,
            parentId:0,
            name:null,
            categoryId:null
        }
    },
    methods: {
        getMerchantcategory: function(){
            //加载店铺分类树
            $.get(baseURL + "sys/merchantcategory/select", function(r){
                ztree = $.fn.zTree.init($("#merchantcategoryTree"), setting, r);
                var node = ztree.getNodeByParam("categoryId", vm.merchantCategory.categoryId);
                ztree.selectNode(node);
                vm.merchantCategory.parentName = node.name;
            })
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.merchantCategory={
                parentName:null,
                parentId:0,
                name:null,
                categoryId:null
            }
            vm.getMerchantcategory();
        },
        update: function () {
            var categoryId = getMerchantcategorytId();
            if(categoryId == null){
                return ;
            }
            vm.showList = false;
            vm.title = "修改";
            vm.getInfo(categoryId);

            vm.getMerchantcategory();
        },
        del: function () {
            var categoryId = getMerchantcategorytId();
            if(categoryId == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/merchantcategory/delete",
                    data: "categoryId=" + categoryId,
                    success: function(r){
                        if(r.code === 0){
                            alert('操作成功', function(){
                                vm.reload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getInfo: function(categoryId){
            $.get(baseURL + "sys/merchantcategory/info/"+categoryId, function(r){
                vm.merchantCategory = r.merchantCategory;
            });
        },
        saveOrUpdate: function (event) {
            var url = vm.merchantCategory.categoryId == null ? "sys/merchantcategory/save" : "sys/merchantcategory/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.merchantCategory),
                success: function(r){
                    if(r.code === 0){
                        alert('操作成功', function(){
                            vm.reload();
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        merchantcategoryTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择店铺分类",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#merchantcategoryLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级店铺
                    vm.merchantCategory.parentId = node[0].categoryId;
                    vm.merchantCategory.parentName = node[0].name;

                    layer.close(index);
                }
            });
        },
        reload: function () {
            vm.showList = true;
            Merchantcategory.table.refresh();
        }
    }
});

var Merchantcategory = {
    id: "merchantcategoryTable",
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Merchantcategory.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        // {title: '分类ID', field: 'categoryId', align: 'center', sortable: true, width: '180px'},
        {title: '店铺分类名', field: 'name', width: 60 },
        {title: '上级店铺分类名', field: 'parentName', align: 'center', sortable: true, width: '200px'},
        {title: '分类图片', field: 'categoryImg', width: 60 },
        {title: '排序', field: 'sort', width: 60 }]
    /*{title: '排序号', field: 'orderNum', align: 'center', valign: 'middle', sortable: true, width: '100px'}*/
    return columns;
};


function getMerchantcategorytId () {
    var selected = $('#merchantcategoryTable').bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        alert("请选择一条记录");
        return null;
    } else {
        return selected[0].id;
    }
}


$(function () {
    $.get(baseURL + "sys/merchantcategory/info", function(r){
        var colunms = Merchantcategory.initColumn();
        var table = new TreeTable(Merchantcategory.id, baseURL + "sys/merchantcategory/list", colunms);
        table.setRootCodeValue(r.categoryId);
        table.setExpandColumn(2);
        table.setIdField("categoryId");
        table.setCodeField("categoryId");
        table.setParentCodeField("parentId");
        table.setExpandAll(false);
        table.init();
        Merchantcategory.table = table;
    });
});
