$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/notice/list',
        datatype: "json",
        colModel: [
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true },
			{ label: '标题', name: 'title', index: 'title', width: 80 },
			{ label: '内容', name: 'contents', index: 'content', width: 80 , formatter: function(value,options,row){
                    return '<button type="button" onclick="vm.viewTheDetails(this)" data-target="#myModal" class="merchant-code btn btn-info btn-xs" data-toggle="modal" data-target=".bs-example-modal-sm">查看内容详情</button>'
                }},
            { label: '内容', name: 'content', index: 'content', width: 80 ,hidden: true},
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 },
			{ label: '更新时间', name: 'updateTime', index: 'update_time', width: 80 },
			{ label: '发送时间', name: 'sendTime', index: 'send_time', width: 80 },
			{ label: '创建人', name: 'userName', width: 80 },
			{ label: '是否置顶', name: 'isTop', index: 'is_top', width: 80 , formatter: function(item, index){
                if(item === 0){
                    return '<span class="label label-primary">否</span>';
                }
                if(item === 1){
                    return '<span class="label label-success">是</span>';
                }
            }}
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
		tSysNotice: {
		    isTop: 0,
            sendTime: null,
            content: null
		},
        q:{
            title: null,
            isTop: -1
        }
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
            UM.getEditor('myEditor').setContent("", false);
            $("#publishTime").val("");
			vm.tSysNotice = {
			    isTop: 0
			};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
		    if(vm.tSysNotice.title == null || vm.tSysNotice.title == ""){
		        alert("标题不能为空！");
		        return;
            }
            if(vm.tSysNotice.title.length > 64){
                alert("标题长度限制64位！");
                return;
            }
		    $("#noticeSaveOrUpdate").blur();
            $("#noticeSaveOrUpdate").attr("disabled", "disabled");
			var url = vm.tSysNotice.id == null ? "sys/notice/save" : "sys/notice/update";
            vm.tSysNotice.content = UM.getEditor('myEditor').getContent();
            vm.tSysNotice.sendTime = $("#publishTime").val();
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.tSysNotice),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
                    $("#noticeSaveOrUpdate").removeAttr("disabled");
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
				    url: baseURL + "sys/notice/delete",
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
			$.get(baseURL + "sys/notice/info/"+id, function(r){
                vm.tSysNotice = r.sysNotice;
                UM.getEditor('myEditor').setContent(vm.tSysNotice.content, false);
                $("#publishTime").val(vm.tSysNotice.sendTime);
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{
                    "title":vm.q.title,
                    "isTop":vm.q.isTop
                },
                page:page
            }).trigger("reloadGrid");
		},
        viewTheDetails: function(e){
            // var data = $("#jqGrid").jqGrid('getRowData');  //获取所有行数据 会有个bug 少了最后一行
            //处理少一行bug
            // var allCountID = $("#jqGrid").jqGrid('getDataIDs'); //这里获取所有行 主键id 是全的
            //data.push($("#jqGrid").jqGrid('getRowData', allCountID[allCountID.length-1])); //单独用最后一个 ID 获取行数据 并 追加到 集合里
            var id = $(e).parent().parent().attr("id");
            var rowData = $("#jqGrid").jqGrid('getRowData', id);
		    alert(rowData.content);
		},
		isTopNum: function() {
		    $.get(baseURL + "sys/notice/isTopNum", function(r) {
                if(r.isTopNum >= 3) {
                    vm.tSysNotice.isTop = 0;
                    alert("已经有3条置顶数据！");
                }
		    });
		}
    }
});


layui.use('laydate', function(){
    var laydate = layui.laydate;

    //执行一个laydate实例
    laydate.render({
        elem: '#publishTime', //指定元素
        type: 'datetime',
        min: 0
    });
});

//判断标题
function checkTitle(){
    var phone = vm.tSysNotice.title;
    if(phone == "" || phone == null){
        $('#uidt1').html(null);
    }else if(phone.length > 64){
        $('#uidt1').html('*标题长度限制64位！');
    }else{
        $('#uidt1').html(null);
    }
}