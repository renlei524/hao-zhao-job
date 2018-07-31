$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'operation/tvyicoojinjian/list',
        datatype: "json",
        colModel: [
            { label: '主键id', name: 'id', width: 80 , hidden:true},
            /*{ label: '商户id', name: 'merchantId', width: 80 , hidden:true},*/
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
	    q:{
            name:'',
            mobile:'',
            realname:'',
            category:'',
            categoryName:'',
            status:0
            },
            roleList:{},
		    showList: true,
            title: null,
            msgName:'',
            msgShortName:'',
            msgRealName:'',
            msgTelPhone:'',
            msgIdCard:'',
            msgEmail:'',
            msgBankNo:'',
            msgBankCardNo:'',
            msgAccountMobile:'',
            tVyicooJinjian: {
                id:'',
            //  merchantId:'',
                username:'',
                type:'',
                name:'',
                mobile:'',
                shortname:'',
                realname:'',
                status:0,
                licenseType:0,
                gbProvinceNo:'',//省
                gbCityNo:'',//市
                gbDistrictNo:'',//区
                mchId:null,
                idFrontPic:null,
                licencePic:null,
                idBackPic:null,
                bankCardPic:null,
                extraPic1:null,
                extraPic2:null

		    },
		    tWBUser:{
                userId:null,
                realName:null
            },
        //新增时联动菜单重置用省、市、区、街道 d
        selectedProvince: -1,
        selectedCity: -1,
        selectedArea: -1
	},
	methods: {
	    checkName: function() {
            if(vm.tVyicooJinjian.name == "" || vm.tVyicooJinjian.name == null){
                this.msgName = "*商户名称不能为空";
            }else if(!( /^[\u4E00-\u9FA5\a-zA-Z0-9_]{4,16}$/.test(document.getElementById('shopName').value))){
                this.msgName = "*商户名称格式不正确";
            }else{
                return this.msgName ="";
            }
        },
        checkShortName: function() {
            if(vm.tVyicooJinjian.shortname == "" || vm.tVyicooJinjian.shortname == null){
                this.msgShortName = "*商户简称不能为空";
            }else if(!( /^[\u4E00-\u9FA5\a-zA-Z0-9_]{4,16}$/.test(document.getElementById('shortName').value))){
                this.msgShortName = "*商户简称格式不正确";
            }else{
                return this.msgShortName ="";
            }
        },
        checkRealName: function() {
            if(vm.tVyicooJinjian.realname == "" || vm.tVyicooJinjian.realname == null){
                this.msgRealName = "*商户真实姓名不能为空";
            }else if(!( /^[\u4E00-\u9FA5\a-zA-Z0-9_]{4,16}$/.test(document.getElementById('realName').value))){
                this.msgRealName = "*商户真实姓名格式不正确";
            }else{
                return this.msgRealName ="";
            }
        },
        checkTelPhone: function() {
            if(vm.tVyicooJinjian.mobile == "" || vm.tVyicooJinjian.mobile == null){
                this.msgTelPhone = "*联系人手机号不能为空";
            }else if(!( /^1[0-9]{10}$/.test(document.getElementById('Mobile').value))){
                this.msgTelPhone = "*联系人手机号格式不正确";
            }else{
                return this.msgTelPhone ="";
            }
        },
        checkIdCard: function() {
            if(vm.tVyicooJinjian.idNo == "" || vm.tVyicooJinjian.idNo == null){
                this.msgIdCard = "*身份证号不能为空";
            }else if(!(/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/.test(document.getElementById('idCard').value))){
                this.msgIdCard = "*身份证号格式不正确";
            }else{
                return this.msgIdCard ="";
            }
        },
        checkEmail: function() {
            if(vm.tVyicooJinjian.email == "" || vm.tVyicooJinjian.email == null){
                this.msgEmail = "*联系邮箱不能为空";
            }else if(!( /\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}/.test(document.getElementById('Email').value))){
                this.msgEmail = "*邮箱格式不正确";
            }else{
                return this.msgEmail ="";
            }
        },
        checkBankNo: function() {
            if(vm.tVyicooJinjian.bankNo == "" || vm.tVyicooJinjian.bankNo == null){
                this.msgBankNo = "*开户支行行号不能为空";
            }else if(!(/^([1-9]{1})(\d{14}|\d{18})$/.test(document.getElementById('BankNo').value))){
                this.msgBankNo = "*开户支行行号格式不正确";
            }else{
                return this.msgBankNo ="";
            }
        },
        checkBankcardNo: function() {
            if(vm.tVyicooJinjian.bankcardNo == "" || vm.tVyicooJinjian.bankcardNo == null){
                this.msgBankCardNo = "*开户支行行号不能为空";
            }else if(!(/^([1-9]{1})(\d{14}|\d{18})$/.test(document.getElementById('BankcardNo').value))){
                this.msgBankCardNo = "*开户支行行号格式不正确";
            }else{
                return this.msgBankCardNo ="";
            }
        },
        checkAccountMobile: function() {
            if(vm.tVyicooJinjian.accountMobile == "" || vm.tVyicooJinjian.accountMobile == null){
                this.msgAccountMobile = "*银行预留手机号不能为空";
            }else if(!( /^1[0-9]{10}$/.test(document.getElementById('AccountMobile').value))){
                this.msgAccountMobile = "*银行预留手机号格式不正确";
            }else{
                return this.msgAccountMobile ="";
            }
        },
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
            vm.title = "新增";
            vm.tVyicooJinjian = {merchantId: null,username:null,name:null,realname:null,mobile:null,idNo:null,email:null,category:null,
                licenseType:null, gbProvinceNo: 1, gbCityNo: 0, gbDistrictNo: 0, licenseType:0,bankcardNo:null, isVoiceFunction: 0};
            vm.twbuser = {id: null,realName:null};

            $('#view,#view1,#view2,#view3,#view4,#view5').css('background', '').css("background", "url(/statics/img/default.png)");
            $('.imgAll>ul').empty();
            $("#userName").val(null);
            vm.getProvince();
            vm.getCity(-1);
            vm.getArea(-1);

		},
		update: function (event) {
		    var id = getSelectedRow();
                if(id == null){
                    return ;
                }

             vm.showList = false;
             vm.title = "修改";
             vm.getInfo(id);

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
		verification : function(){
		    //系统商户
            /*if(vm.tVyicooJinjian.type == null || vm.tVyicooJinjian.type == ""){
                alert("系统商户不能为空");
                return true;
            }*/
            //商户名称
            if(vm.tVyicooJinjian.name == null || vm.tVyicooJinjian.name == "" ){
                alert("商户名称不能为空");
                return true;
            }else if(!( /^[\u4E00-\u9FA5\a-zA-Z0-9_]{1,16}$/.test(document.getElementById('shopName').value))){
                alert("请重新填写商户名称");
                return true;
            }

            //商户简称
            if(vm.tVyicooJinjian.shortname == null || vm.tVyicooJinjian.shortname == "" ){
                alert("商户名称不能为空");
                return true;
            }else if(!( /^[\u4E00-\u9FA5\a-zA-Z0-9_]{1,16}$/.test(document.getElementById('shortName').value))){
                alert("请重新填写商户名称");
                return true;
            }

            //真实姓名
            if(vm.tVyicooJinjian.realname == null || vm.tVyicooJinjian.realname == ""){
                alert("真实姓名不能为空");
                return true;
            }else if(!( /^[\u4E00-\u9FA5\a-zA-Z0-9_]{1,16}$/.test(document.getElementById('realName').value))){
                alert("请重新填写真实姓名");
                return true;
            }

            //联系电话
            if(vm.tVyicooJinjian.mobile == null || vm.tVyicooJinjian.mobile == ""){
                alert("联系人手机号不能为空");
                return true;
            }else if( !(/^1[34578]\d{9}$/.test(document.getElementById('Mobile').value))){
                alert("请重新填写联系人手机号");
                return true;
            }

            //身份证号
            if(vm.tVyicooJinjian.idNo == null || vm.tVyicooJinjian.idNo == ""){
                alert("身份证号码不能为空");
                return true;
            }else if(!(/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/.test(document.getElementById('idCard').value))){
                alert("请重新填写身份证号码");
                return true;
            }

            //省市区
            var ProvinceNo = $("#province").val();
            var CityNo = $("#city").val();
            var DistrictNo = $("#area").val();
            if(ProvinceNo == null || ProvinceNo == ""){
                alert("省不能为空");
                return true;
            }
            if(CityNo == null || CityNo == ""){
                alert("市不能为空");
                return true;
            }
            if(DistrictNo == null || DistrictNo == ""){
                alert("区不能为空");
                return true;
            }

            //经营地址
            if(vm.tVyicooJinjian.address == null || vm.tVyicooJinjian.address == ""){
                alert("省不能为空");
                return true;
            }

            //联系邮箱
            if(vm.tVyicooJinjian.email == null || vm.tVyicooJinjian.email == ""){
                alert("邮箱不能为空");
                return true;
            }else if(!(/\w[-\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\.)+[A-Za-z]{2,14}/.test(document.getElementById('Email').value))){
                alert("请重新填写邮箱");
                return true;
            }

            //经营类别
            /*if(vm.tVyicooJinjian.category == null || vm.tVyicooJinjian.category == ""){
                alert("经营类别不能为空");
                return true;
            }*/

            //营业执照类型
            if(vm.tVyicooJinjian.licenseType == null || vm.tVyicooJinjian.licenseType == ""){
                alert("营业执照类型不能为空");
                return true;
            }

            //营业执照编号
            if(vm.tVyicooJinjian.licenseNo == null || vm.tVyicooJinjian.licenseNo == ""){
                alert("营业执照编号不能为空");
                return true;
            }else if(!(/^\d$/.test(document.getElementById('LicenseNo').value))){

            }

            //营业执照类型
            if(vm.tVyicooJinjian.licenseAddress == null || vm.tVyicooJinjian.licenseAddress == ""){
                alert("营业执照类型不能为空");
                return true;
            }

            //注册号开始、结束日期
            if(vm.tVyicooJinjian.licenseStartDate == null || vm.tVyicooJinjian.licenseStartDate == ""){
                alert("注册号开始日期不能为空");
                return true;
            }else if(vm.tVyicooJinjian.licenseEndDate == null || vm.tVyicooJinjian.licenseEndDate == ""){
                alert("注册号结束日期不能为空");
                return true;
            }

            //开户支行ID
            if(vm.tVyicooJinjian.bankId == null || vm.tVyicooJinjian.bankId == ""){
                alert("开户支行ID不能为空");
                return true;
            }

            //开户支行所在城市编码
            if(vm.tVyicooJinjian.accountCity == null || vm.tVyicooJinjian.accountCity == ""){
                alert("开户支行所在城市编码不能为空");
                return true;
            }

            //开户支行行号
            if(vm.tVyicooJinjian.bankNo == null || vm.tVyicooJinjian.bankNo == ""){
                alert("开户支行行号不能为空");
                return true;
            }

            //开户名称
            if(vm.tVyicooJinjian.accountName == null || vm.tVyicooJinjian.accountName == ""){
                alert("开户名称不能为空");
                return true;
            }

            //银行预留手机号
            if(vm.tVyicooJinjian.accountMobile == null || vm.tVyicooJinjian.accountMobile == ""){
                alert("银行预留手机号不能为空");
                return true;
            }else if( !(/^1[34578]\d{9}$/.test(document.getElementById('AccountMobile').value))){
                alert("请重新填写银行预留手机号手机号");
                return true;
            }

            //公众号

            if(vm.tVyicooJinjian.selfAppid == "1"){
                if(vm.tVyicooJinjian.wxAppid == null || vm.tVyicooJinjian.wxAppid == ""){
                    alert("有公众号必填（公众号主体需同营业执照名称一致");
                    return true;
                }
                return true;
            }

            //商户微信号
            if(vm.tVyicooJinjian.wechatId == null || vm.tVyicooJinjian.wechatId == ""){
                alert("商户微信号不能为空");
                return true;
            }

            //法人银行卡号
            if(vm.tVyicooJinjian.bankcardNo == null || vm.tVyicooJinjian.bankcardNo == ""){
                alert("法人银行卡号不能为空");
                return true;
            }

            //照片

            return false;

        },
        //获取商户名字信息 d
        getUserInfo:function(userId) {
            // var id = vm.tVyicooJinjian.merch;
            $.get(baseURL + "operation/twbuser/info/"+userId, function(r){
                vm.tWBUser = r.tWbUser;
                vm.tVyicooJinjian.username = r.tWbUser.realName;
                $("#userName").val(vm.tVyicooJinjian.username);
            });
        },
        //加载经营类型信息
        getbCategory:function() {
            var id = vm.tVyicooJinjian.bCategoryId;
            $.get(baseURL + "operation/tvyicoojinjian/info/"+id, function(r){
                vm.tVyicooJinjian.category = r.category.name;
                $("#categoryName").val(vm.tVyicooJinjian.category);
            });
        },
		getInfo: function(merchantId){
			$.get(baseURL + "operation/tvyicoojinjian/info/"+merchantId, function(r){
                var imageNginxPath = r.imageNginxPath;
                vm.tVyicooJinjian = r.tVyicooJinjian;
                //加载省市区数据
                vm.getProvince();
                vm.getCity(vm.tVyicooJinjian.gbCityNo);
                vm.getArea(vm.tVyicooJinjian.gbDistrictNo);
                vm.getUserInfo(vm.tVyicooJinjian.merchantId)
                vm.tVyicooJinjian.licenseStartDate = $("#tVyicooJinjian-beginTime").val();
                vm.tVyicooJinjian.licenseEndDate = $("#tVyicooJinjian-endTime").val();

                /*BussinessCategoryReload();*/

                if(vm.tVyicooJinjian.licensePic  != null && vm.tVyicooJinjian.licensePic  != 'undefined' && vm.tVyicooJinjian.licensePic  != '' && vm.tVyicooJinjian.licensePic  != 'none') {
                    $('#view').css('background', 'url(' + r.imageNginxPath + vm.tVyicooJinjian.licensePic  + ')');
                }
                if(vm.tVyicooJinjian.idFrontPic  != null && vm.tVyicooJinjian.idFrontPic  != 'undefined' && vm.tVyicooJinjian.idFrontPic  != '' && vm.tVyicooJinjian.idFrontPic  != 'none') {
                    $('#view1').css('background', 'url(' + r.imageNginxPath + vm.tVyicooJinjian.idFrontPic  + ')');
                }
                if(vm.tVyicooJinjian.idBackPic != null && vm.tVyicooJinjian.idBackPic != 'undefined' && vm.tVyicooJinjian.idBackPic != '' && vm.tVyicooJinjian.idBackPic != 'none') {
                    $('#view2').css('background', 'url(' + r.imageNginxPath + vm.tVyicooJinjian.idBackPic + ')');
                }
                if(vm.tVyicooJinjian.bankcardPic != null && vm.tVyicooJinjian.bankcardPic != 'undefined' && vm.tVyicooJinjian.bankcardPic != '' && vm.tVyicooJinjian.bankcardPic != 'none') {
                    $('#view3').css('background', 'url(' + r.imageNginxPath + vm.tVyicooJinjian.bankcardPic + ')');
                }
                if(vm.tVyicooJinjian.shopPic != null && vm.tVyicooJinjian.shopPic != 'undefined' && vm.tVyicooJinjian.shopPic != '' && vm.tVyicooJinjian.shopPic != 'none') {
                    $('#view4').css('background', 'url(' + r.imageNginxPath + vm.tVyicooJinjian.shopPic + ')');
                }
                if(vm.tVyicooJinjian.extraPic2 != null && vm.tVyicooJinjian.extraPic2 != 'undefined' && vm.tVyicooJinjian.extraPic2 != '' && vm.tVyicooJinjian.extraPic2 != 'none') {
                    $('#view5').css('background', 'url(' + r.imageNginxPath + vm.tVyicooJinjian.extraPic2 + ')');
                }
                if(vm.tVyicooJinjian.extraPic1 != null && vm.tVyicooJinjian.extraPic1 != 'undefined' && vm.tVyicooJinjian.extraPic1 != '' && vm.tVyicooJinjian.extraPic1 != 'none') {
                    var photosPath = vm.tVyicooJinjian.extraPic1.split(',');
                    var photosEelements = $('.imgAll>ul');
                    photosEelements.empty();
                    for(var i = 0; i < photosPath.length; i++) {
                        photosEelements.append(
                            '<li data-delid="' + i + '" data-delname="' + r.imageNginxPath + photosPath[i] + '">' +
                                '<img src="' + r.imageNginxPath + photosPath[i] + '" alt="" class="imsg">' +
                                '<i class="delImg">X</i>' +
                            '</li>'
                        );
                    }
                    $('.imgAll>ul>li>.delImg').off().on('click',function(){
                        var src = $(this).prev().attr('src');
                        deletePhotos(src);
                        $(this).parent().fadeOut('slow',function(){
                            $(this).remove();
                        });
                    })
                };
		    })
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
                        if(vm.tVyicooJinjian.gbProvinceNo == r.areaList[i].areaCode) {
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
                            if(vm.tVyicooJinjian.gbCityNo == r.areaList[i].areaCode) {
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
                            if(vm.tVyicooJinjian.gbDistrictNo == r.areaList[i].areaCode) {
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
		},
		saveOrUpdate: function (event) {
		    vm.tVyicooJinjian.licenseStartDate = $("#tVyicooJinjian-beginTime").val();
            vm.tVyicooJinjian.licenseEndDate = $("#tVyicooJinjian-endTime").val();
            $("#salesMan-window-close-button").blur();
            if(vm.verification()){
                return;
            }else{
                $("#salesMan-window-close-button").attr("disabled", "disabled");
                var url = vm.tVyicooJinjian.mchId == null ? "operation/tvyicoojinjian/save" : "operation/tvyicoojinjian/update";
                //getMerchantPhotos();
                var provinceName = null;
                var cityName = null;
                var areaName = null;

                if ($("#province").val() != 0) {
                    var provinceIndex=document.getElementById("province").selectedIndex;
                    provinceName = document.getElementById("province").options[provinceIndex].innerHTML;
                }
                if ($("#city").val() != 0) {
                    var cityIndex=document.getElementById("city").selectedIndex;
                    cityName = document.getElementById("city").options[cityIndex].innerHTML;
                }
                if ($("#area").val() != 0) {
                    var areaIndex=document.getElementById("area").selectedIndex;
                    areaName = document.getElementById("area").options[areaIndex].innerHTML;
                }

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
                             $("#text1").attr("status", "Y");
                        }
                        $("#salesMan-window-close-button").removeAttr("disabled");
                    }
                });
            }

        },
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
        vm.tVyicooJinjian.gbProvinceNo = vm.selectedProvince;
        vm.tVyicooJinjian.gbCityNo = vm.selectedCity;
        vm.tVyicooJinjian.gbDistrictNo = vm.selectedArea;
    });

    //市 选择触发事件 d
    $("#city").change(function(){
        vm.selectedArea = 0;
        vm.selectedTown = 0;
        vm.selectedCity = $("#city").val();
        vm.getArea(vm.selectedCity);
        vm.tVyicooJinjian.gbCityNo = vm.selectedCity;
        vm.tVyicooJinjian.gbDistrictNo = vm.selectedArea;

    });

    //区选择触发事件 d
    $("#area").change(function(){
        vm.selectedTown = 0;
        vm.selectedArea = $("#area").val();
        vm.tVyicooJinjian.gbDistrictNo = vm.selectedArea;
    });


    $('.tVyicooJinjian-beginTime, .tVyicooJinjian-endTime').datetimepicker({
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
        var laydate_1 = layui.laydate;
        //执行一个laydate实例
        laydate_1.render({
            elem: '#tVyicooJinjian-beginTime' //指定元素
            ,/*type: 'datetime',*/
            value: '2017-12-01'
        });

        var laydate_2 = layui.laydate;
        //执行一个laydate实例
        laydate_2.render({
            elem: '#tVyicooJinjian-endTime' //指定元素
            ,/*type: 'datetime',*/
            value: '2029-12-01'
        });
    });

    //查询  商户名字  弹出窗代码 d
    $('#myModal').on('show.bs.modal', function () {
        $("#commercialName").jqGrid({
                    url: baseURL + 'operation/twbuser/list',
                    datatype: "json",
                    colModel: [
                        { label: '用户id', name: 'id', hidden:true,fixed:true},
                        { label: '用户手机号码',fixed:true, name: 'mobile'},
                        { label: '用户真实姓名',fixed:true, name: 'realName',width: 160},
                        { label: '用户身份证号码',fixed:true, name: 'idCard' },
                        { label: '状态', name: 'status',width: 80 ,formatter: function(item, index){
                                if(item === 0){
                                    return '<span class="label label-primary">正常</span>';
                                }
                                if(item === 1){
                                    return '<span class="label label-success">异常</span>';
                                } if(item === 2){
                                    return '<span class="label label-success">冻结</span>';
                                }}}
                    ],
                    viewrecords: true,
                    height: 235,
                    rowNum: 6,
                    rowList : [6,10,15,20,30,50],
                    rownumbers: false,
                    rownumWidth: 45,
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
        // vm.tVyicooJinjian.userId = tWBUserDate.id;
        vm.tVyicooJinjian.merchantId = tWBUserDate.id;
        vm.tVyicooJinjian.id = tWBUserDate.id;
        $("#userName").val(tWBUserDate.realName);
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
            postData:{'realName':vm.q.tWBUserName},
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

    $("#userName").click(function () {
        $("#qTWBUserNameSearch").val(null);
        commercialNameReload();
    });


    //经营类型  弹出窗代码
    $('#bCategory').on('shown.bs.modal', function () {
    // $(function () {
        $("#category").jqGrid({
            url: baseURL + 'operation/tvyicoocategory/list',
            datatype: "json",
            colModel: [
                { label: '经营类别ID', name: 'bCateId', hidden:true},
                { label: '经营类别', name: 'category' },

            ],
            viewrecords: true,
            height: 235,
            rowNum: 6,
            rowList : [10,15,20,30,50],
            rownumbers: false,
            rownumWidth: 35,
            autowidth:true,
            multiselect: true,
            pager: "#categoryPager",
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

    //经营类型 弹出 窗关闭按钮代码 d
    $("#category-window-close-button").click(function () {
        var rowKey = getCategorySelecteRow();
        if(rowKey == null){
            return ;
        }
        var grid = $("#category");
        var rowKey = grid.getGridParam("selrow");
        var categoryDate =  $("#category").getRowData(rowKey);
        // vm.tVyicooJinjian.bCategoryId = categoryDate.id;
        vm.tVyicooJinjian.category = categoryDate.category;
        $("#categoryNameVal").val(categoryDate.category);
        $('#bCategory').modal('hide');

    });

    //经营类型 弹出 窗查询按钮代码 d
    $("#bCategoryReload").click(function () {
        bCategoryReload();
    });

    //经营类型 d
    function bCategoryReload() {
        var page = $("#category").jqGrid('getGridParam','page');
        $("#category").jqGrid('setGridParam',{
            postData:{'categoryName':vm.q.categoryName},
            page:1
        }).trigger("reloadGrid");
    }

    //经营类型  弹出窗查询选择条件为一条限制 d
    function getCategorySelecteRow() {
        var grid = $("#category");
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

    $("#categoryNameVal").click(function () {
        bCategoryReload();
        $("#bCategoryVal").val(null);

    });