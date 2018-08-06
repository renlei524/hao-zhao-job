$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'operation/twbcard/list',
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', index: 'id', width: 80 ,hidden:true},
            { label: '优惠券名称', name: 'cardContent', index: 'card_content', width: 80 },
			{ label: '发行方类型', name: 'creatorType', width: 80, formatter: function(value, options, row){
                    return value === 1 ?
                        '<span class="label label-primary">平台</span>' :
                        '<span class="label label-success">商户</span>';
                }},
			// { label: '创建人/发行人', name: 'creatorId', index: 'creator_id', width: 80 },
			{ label: '可使用开始时间', name: 'beginTime', index: 'begin_time', width: 80 }, 			
			{ label: '卡券过期时间', name: 'endTime', index: 'end_time', width: 80 }, 			
			{ label: '卡卷数量', name: 'cardNum', index: 'card_num', width: 80 }, 			
			{ label: '剩余卡券数量', name: 'leftNum', index: 'left_num', width: 80 }, 			
			{ label: '用户最多持有张数', name: 'limitNum', index: 'limit_num', width: 80 },
			{ label: '使用说明', name: 'description', index: 'description', width: 80 },
			{ label: '状态', name: 'status', width: 80, formatter: function(value, options, row){
                    return value === 1 ?
                        '<span class="label label-success">未删除</span>' :
                        '<span class="label label-danger">已删除</span>';
                }},
			{ label: '发行时间', name: 'createDate', index: 'create_date', width: 80 },
			{ label: '卡券种类', name: 'type',index: 'type', align: 'center', valign: 'middle', sortable: true, width: '80px', formatter: function(item, index){
                    if(item === 0){
                        return '<span class="label label-primary">代金卷</span>';
                    }
                    if(item === 1){
                        return '<span class="label label-success">折扣券</span>';
                    }
                    if(item === 2){
                        return '<span class="label label-warning">满减券</span>';
                    }
                }},
			{ label: '折扣', name: 'discount', index: 'discount', width: 80 },
			{ label: '满足金额', name: 'fullMoney', index: 'full_money', width: 80 },
			{ label: '卡券金额', name: 'cardMoney', index: 'card_money', width: 80 }
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
        tWbCard :{
            cardContent: null
		},
		q:{
            cardContent :null,
            startTime:null,
            endTime:null,
            creatorType:-1,
            type:-1
		},
		showList: true,
		title: null,
		tWbCard: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.tWbCard = {
                creatorType:1,
                type:0,
                status:1
            };
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
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
            $("#text1").blur();
            $("#text1").attr("disabled", "disabled");
			vm.tWbCard.beginTime = $("#tWbCard-beginTime").val();
            vm.tWbCard.endTime = $("#tWbCard-endTime").val();
            vm.tWbCard.createDate = $("#tWbCard-createDate").val();
			var url = vm.tWbCard.id == null ? "operation/twbcard/save" : "operation/twbcard/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.tWbCard),
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
				    url: baseURL + "operation/twbcard/delete",
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
			$.get(baseURL + "operation/twbcard/info/"+id, function(r){
                vm.tWbCard = r.tWbCard;
                $("#tWbCard-beginTime").val(r.tWbCard.beginTime);
                $("#tWbCard-endTime").val(r.tWbCard.endTime);
                $("#tWbCard-createDate").val(r.tWbCard.createDate);
            });
		},
		reload: function (event) {
			vm.showList = true;
            var time = $("#startTime-endTime").val().split(" - ");
            vm.q.startTime = time[0];
            vm.q.endTime = time[1];
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{'cardContent': vm.q.cardContent,//优惠劵名称
                    "endTime": vm.q.endTime, //开始时间
                    "startTime": vm.q.startTime, //结束时间
                    "creatorType": vm.q.creatorType, //发行方类型
                    "type": vm.q.type //卡券种类
                    },
                page:1
            }).trigger("reloadGrid");
              $("#text1").attr("status", "Y");
		}
	}
});

$('.tWbCard-beginTime, .tWbCard-endTime').datetimepicker({
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

    var laydate = layui.laydate;

    //执行一个laydate实例
    laydate.render({
        elem: '#startTime-endTime' //指定元素
        ,range: true
        ,max: 0
    });

    var laydate_1 = layui.laydate;
    //执行一个laydate实例
    laydate_1.render({
        elem: '#tWbCard-beginTime' //指定元素
        ,type: 'datetime'
    });

    var laydate_2 = layui.laydate;
    //执行一个laydate实例
    laydate_2.render({
        elem: '#tWbCard-endTime' //指定元素
        ,type: 'datetime'
    });

    var laydate_3 = layui.laydate;
    //执行一个laydate实例
    laydate_3.render({
        elem: '#tWbCard-createDate' //指定元素
        ,type: 'datetime'
    });
});
