$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'operation/twbuser/list',
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', index: 'id', hidden:true },
			{ label: '用户手机号码', name: 'mobile', index: 'mobile'},
			{ label: '用户真实姓名', name: 'realName', index: 'real_name'},
			{ label: '用户身份证号码', name: 'idCard', index: 'id_card'},
			{ label: '状态', name: 'status', index: 'status',valign: 'middle',formatter: function(item, index){
                    if(item === 0){
                        return '<span class="label label-primary">正常</span>';
                    }
                    if(item === 1){
                        return '<span class="label label-success">异常</span>';
                    }
                    if(item === 2){
                        return '<span class="label label-warning">冻结</span>';
                    }
                }},
			{ label: '创建时间', name: 'createTime', index: 'create_time',formatter:function(value,options,row){
                    return new Date(value).Format('yyyy-MM-dd HH:mm:ss');}},
			{ label: '更新时间', name: 'updateTime', index: 'update_time',formatter:function(value,options,row){
                    return new Date(value).Format('yyyy-MM-dd HH:mm:ss');}},
            { label: '消费总额', name: '', index: ''},
            { label: '最近消费时间', name: '', index: ''},
            { label: '最近消费区域', name: '', index: ''},
            { label: '城市经理', name: '', index: ''},
            { label: '城市经理电话', name: '', index: ''}
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

Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}



var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		tWbUser: {realName:null,mobile:null,idCard:null,},
        q:{
            realName:null,
        }
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.tWbUser = {status:0};
			var tips = document.getElementById('err-plchod');
			tips.innerHTML = '';
			var tips1 = document.getElementById('err-plchod1');
			tips1.innerHTML = '';
			var tips2 = document.getElementById('err-plchod2');
            tips2.innerHTML = '';
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
        verification : function(){
            if(vm.tWbUser.mobile == null || vm.tWbUser.mobile == ""){
                alert("用户手机号不能为空");
                return true;
            }
            if(vm.tWbUser.mobile.length != 11 ){
                alert("请输入正确的手机号(11位)");
                return true;
            }
            var reg=/^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/;
            if(!reg.test(vm.tWbUser.mobile)){
                alert("对不起，您输入的电话号码格式不正确!");
                return true;
            }
            if(vm.tWbUser.realName == null || vm.tWbUser.realName == ""){
                alert("用户真实姓名不能为空");
                return true;
            }
            if(vm.tWbUser.idCard == null || vm.tWbUser.idCard == ""){
                alert("用户身份证号码不能为空");
                return true;
            }
            //身份证有15位和18位
            var reg=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;
            if(!reg.test(vm.tWbUser.idCard)){
                alert("对不起，您输入的身份证号码格式不正确!");
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
			var url = vm.tWbUser.id == null ? "operation/twbuser/save" : "operation/twbuser/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.tWbUser),
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
				    url: baseURL + "operation/twbuser/delete",
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
			$.get(baseURL + "operation/twbuser/info/"+id, function(r){
                vm.tWbUser = r.tWbUser;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{'realName':vm.q.realName},
                page:1
            }).trigger("reloadGrid");
             $("#text1").attr("status", "Y");
		}
	}
});


 //判断用户名为空
 function userName(){
     var text = document.getElementById('userMobile').value;
     var tips = document.getElementById('err-plchod');
     if(text == "" || text == null){
         tips.innerHTML = '*请输入电话号码';
     }else if(!(/^1[34578]\d{9}$/.test(text))){
         tips.innerHTML = '*请输入正确的电话号码';
     }else{
         tips.innerHTML = '';
     }
 }

 //判断真是姓名为空
 function checkrealName(){
     var text = document.getElementById('real_Name').value;
     var tips1 = document.getElementById('err-plchod1');
     if(text == "" || text == null){
         tips1.innerHTML = '*请输入真实姓名';
     }else if(!( /^[\u4E00-\u9FA5\a-zA-Z0-9_]{2,11}$/.test(text))){
          tips1.innerHTML = '*不能含有特殊字符';
     }else{
         tips1.innerHTML = '';
     }


 }
 //判断身份证为空
 function checkidCard(){
     var text = document.getElementById('id_Card').value;
     var tips2 = document.getElementById('err-plchod2');
     if(text == "" || text == null){
         tips2.innerHTML = '*请输入身份证号';
     }else if(!(/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/).test(text)){
         tips2.innerHTML = '*请输入正确的身份证号';
     }else{
         tips2.innerHTML = '';
     }
 }






