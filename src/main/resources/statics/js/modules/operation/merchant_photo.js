// 营业执照
updeta_img("#clipArea",350,500,"#file_new","#view","#clipBtn",$('.cover-wrap'));
// 身份证正面
updeta_img("#clipArea1",428,321,"#file_new1","#view1","#clipBtn1",$('.cover-wrap1'));
// 身份证背面
updeta_img("#clipArea2",428,321,"#file_new2","#view2","#clipBtn2",$('.cover-wrap2'));
//店铺头像
updeta_img("#clipArea3",280,200,"#file_new3","#view3","#clipBtn3",$('.cover-wrap3'));
// 截图插件

function updeta_img(clipArea,num_1,num_2,file, view, ok, class_name){
    var src;
    new bjj.PhotoClip(clipArea, {
        size: [num_1,num_2],// 截取框的宽和高组成的数组。默认值为[260,260]
        outputSize: [600, 600], // 输出图像的宽和高组成的数组。默认值为[0,0]，表示输出图像原始大小
        //outputType: "jpg", // 指定输出图片的类型，可选 "jpg" 和 "png" 两种种类型，默认为 "jpg"
        file: file, // 上传图片的<input type="file">控件的选择器或者DOM对象
        view: view, // 显示截取后图像的容器的选择器或者DOM对象
        ok: ok, // 确认截图按钮的选择器或者DOM对象
        loadStart: function() {
            // 开始加载的回调函数。this指向 fileReader 对象，并将正在加载的 file 对象作为参数传入
            class_name.fadeIn();
            $('.phone-max-box').fadeIn();
            //console.log("照片读取中");
        },
        loadComplete: function() {
             src = $(view).css('backgroundImage');

             // 加载完成的回调函数。this指向图片对象，并将图片地址作为参数传入
            //console.log("照片读取完成");
        },
        loadError: function(event) {}, // 加载失败的回调函数。this指向 fileReader 对象，并将错误事件的 event 对象作为参数传入
        clipFinish: function(dataURL) {
             // 裁剪完成的回调函数。this指向图片对象，会将裁剪出的图像数据DataURL作为参数传入
            class_name.fadeOut();
            $(view).css('background-size','100% 100%');
            /*if(typeof(src) != 'undefined' && src != null && src != '' && src != 'none') {
                $.ajax({
                    url: baseURL + "operation/merchant/deleteImage",
                    data: {
                        "imageName": src.split('\"')[1].split('/')[4]
                    },
                    type: "post",
                    async: false,
                    success: function(r) {

                    }
                });
            }*/
            if(clipArea == "#clipArea") {
                $.ajax({
                    url: baseURL + "operation/merchant/uploadImageBase64",
                    data: {
                        "imageBase64": dataURL.split(',')[1]
                    },
                    type: "post",
                    success: function(r) {
                        vm.merchant.licence = r.path;
                        $(view).css('background', 'url(' + r.imageNginxPath + vm.merchant.licence + ')');
                    }
                });
            } else if(clipArea == "#clipArea1") {
                $.ajax({
                    url: baseURL + "operation/merchant/uploadImageBase64",
                    data: {
                        "imageBase64": dataURL.split(',')[1]
                    },
                    type: "post",
                    success: function(r) {
                        vm.merchant.idCardFacePhoto = r.path;
                        $(view).css('background', 'url(' + r.imageNginxPath + vm.merchant.idCardFacePhoto + ')');
                    }
                });
            } else if(clipArea == "#clipArea2") {
                $.ajax({
                    url: baseURL + "operation/merchant/uploadImageBase64",
                    data: {
                        "imageBase64": dataURL.split(',')[1]
                    },
                    type: "post",
                    success: function(r) {
                        vm.merchant.idCardBackPhoto = r.path;
                        $(view).css('background', 'url(' + r.imageNginxPath + vm.merchant.idCardBackPhoto + ')');
                    }
                });
            } else if(clipArea == "#clipArea3") {
                $.ajax({
                    url: baseURL + "operation/merchant/uploadImageBase64",
                    data: {
                        "imageBase64": dataURL.split(',')[1]
                    },
                    type: "post",
                    success: function(r) {
                        vm.merchant.avatar = r.path;
                        $(view).css('background', 'url(' + r.imageNginxPath + vm.merchant.avatar + ')');
                    }
                });
            }
        }
    });
}

$(".close-btn").click(function(){
   $('.phone-max-box').fadeOut();
   $('.cover').fadeOut();
})


~(function(win){
	var htmls = '<input type="file" name="" id="" class="imgFiles" style="display: none" accept="image/gif,image/jpeg,image/jpg,image/png,image/svg" multiple>'+
				'<div class="header">'+
				'    <span class="imgTitle">'+
				'       '+
				'        <b>'+
				'            '+
				'        </b>'+
				'    </span>'+
				'    <span class="imgClick">'+
				'    </span>'+
				'    <span class="imgcontent">'+
				'        请上传'+
				'    </span>'+
				'</div>'+
				'<div class="imgAll">'+
				'    <ul>'+
				'    </ul>'+
				'</div>';
	var ImgUploadeFiles = function(obj,fn){
		var _this = this;
		this.bom = document.querySelector(obj);

		if(fn) fn.call(_this);
		this.ready();

	};
	ImgUploadeFiles.prototype = {
		init : function(o){
			this.MAX = o.MAX || 5;
			this.callback = o.callback;
			this.MW = o.MW || 10000;
			this.MH = o.MH || 10000;
		},
		ready : function(){
			var _self = this;
			this.dom = document.createElement('div');
			this.dom.className = 'imgFileUploade';
			this.dom.innerHTML = htmls;
			this.bom.appendChild(this.dom);
			this.files = this.bom.querySelector('.imgFiles');
			this.fileClick = this.bom.querySelector('.imgClick');
			this.fileBtn(this.fileClick,this.files);
			this.imgcontent = this.bom.querySelector('.imgcontent');
			this.imgcontent.innerHTML = '最多上传<b style="color:red">'+this.MAX+'</b>张'+_self.MW+' * '+_self.MH+'像素的图片';

		},
		fileBtn : function(c,f){
			var _self = this;
			var _imgAll = $(c).parent().parent().find('.imgAll ul');
			$(c).off().on('click',function(){
				$(f).click();

				$(f).off().on('change',function(){
					var _this = this;
					_private.startUploadImg(_imgAll,_this.files,_self.MAX,_self.callback,_self.MW,_self.MH);
				});
			});
		}
	};
	var _dataArr = [];
	var _private = {
		startUploadImg : function(o,files,MAX,callback,W,H){
			_dataArr.length = 0;
			var _this = this;
			var fileImgArr = [];

			if(files.length > MAX ){
				alert('不能大于'+MAX+'张');
				return false;
			};
			var lens = $(o).find('li').length ;
			if(lens >= MAX ){
				alert('不能大于'+MAX+'张');
				return false;
			};

			for(var i=0,file;file=files[i++];){
				var reader = new FileReader();
				reader.onload = (function(file){
					return function(ev){
						var image = new Image();
						image.onload=function(){
					          var width = image.width;
					          var height = image.height;

					        fileImgArr.push({
								fileSrc : ev.target.result,
								fileName : file.name,
								fileSize : file.size,
								height : height,
								width : width
							});
					    };
					     image.src= ev.target.result;


					};
				})(file);
				reader.readAsDataURL(file);
			}
			//创建分时函数
			var imgTimeSlice = _this.timeChunk(fileImgArr,function(file){
				if(file.width > W || file.height > H){
	            	alert('图片不能大于'+W+'*'+H+'像素');
	            	return false;
	            }
				//调用图片类
				var up = new ImgFileupload(o,file.fileName,file.fileSrc,file.fileSize,callback);
				up.init();
			},1);
			imgTimeSlice(); //调用分时函数
		},
		timeChunk : function(arr, fn, count) {
            var obj, t;
            var len = arr.length;
            var start = function() {
                for (var i = 0; i < Math.min(count || 1, arr.length); i++) {
                    var obj = arr.shift();
                    fn(obj)
                }
            };
            return function() {
                t = setInterval(function() {
                    if (arr.length === 0) {
                        return clearInterval(t);
                    }
                    start();
                },200)
            }
        }
	};

	var ImgFileupload = function(b,imgName,imgSrc,imgSize,callback){
		this.b = b;
		this.imgName = imgName;
		this.imgSize = imgSize;
		this.imgSrc = imgSrc;
		this.callback = callback;
	};
	var _delId = 1; //删除id用于判断删除个数
	ImgFileupload.prototype.init =function() {
		_delId++;
		var _self = this;
		this.dom = document.createElement('li');
		this.dom.innerHTML =
							'    <img src="/statics/img/login.gif" alt=""  class="imsg">'+
							'    <i class="delImg">'+
							'        X'+
							'    </i>';
		$(this.dom).attr({'data-delId':_delId,'data-delName':this.imgName});
		$(this.b).append(this.dom);
		var _Img = new Image();
		/*_Img.src = $(this.dom).find('img').attr('data-src');
		_Img.onload = function(){
			$(_self.dom).find('img').attr('src',_Img.src);
		}*/
		_dataArr.push({'delId' :_delId,src :  this.imgSrc});
		_self.callback(_dataArr);
		// $(this.b).parent().parent().parent().attr('data-dataImgs',JSON.stringify(_dataArr));
		var _delAll = $(this.b).find('.delImg');
		for(var i=0;i<_delAll.length;i++){
			$(_delAll[i]).off().on('click',function(){
			    var src = $(this).prev().attr('src');
			    deletePhotos(src);
			    /*$.ajax({
                    url: baseURL + "operation/merchant/deleteImage",
                    data: {
                        "imageName": src.split('/')[4]
                    },
                    type: "post",
                    async: false,
                    success: function(r) {
                        deletePhotos(src);
                    }
                });*/
                $(this).parent().fadeOut('slow',function(){
                    $(this).remove();
                });
                var _deid = $(this).parent().attr('data-delId');
                for(var n=0;n<_dataArr.length;n++){
                    if(_dataArr[n].delId == _deid){
                        _dataArr.splice(n,1);
                    }
                }
                _self.callback(_dataArr);
                // $(this.b).parent().parent().parent().attr('data-dataImgs',JSON.stringify(_dataArr))
			});
		};
		var _Imgpreview = $(this.b).find('img');
		for(var k=0;k<_Imgpreview.length; k++){
			$(_Imgpreview[k]).off().on('click',function(){
				//console.log($(this).attr('src'));
			})
		}
	}

	win.ImgUploadeFiles = ImgUploadeFiles;
})(window);

var imgFile1 = new ImgUploadeFiles('.box2',function(e){
    this.init({
        MAX : 6,
        MH : 1800, //像素限制高度
        MW : 1900, //像素限制宽度
        callback : function(arr){
             $.ajax({
                url: baseURL + "operation/merchant/uploadImageBase64",
                data: {
                    "imageBase64": arr[0].src.split(',')[1]
                },
                type: "post",
                success: function(r) {
                    if(vm.merchant.photos != null && vm.merchant.photos != 'undefined' && vm.merchant.photos != '' && vm.merchant.photos != 'none') {
                        vm.merchant.photos = vm.merchant.photos + ',' + r.path;
                    } else {
                        vm.merchant.photos = r.path;
                    }
                    $(".imgAll").find("li[data-delid="+ arr[0].delId +"]").find(".imsg").attr("src",  r.imageNginxPath + r.path);
                }
            });
        }
    });
});

var deletePhotos = function(photo) {
    var photos = vm.merchant.photos.split(',');
    var index = photos.indexOf(photo);
    photos.splice(index, 1);
    vm.merchant.photos = photos.join(',');
}

/*
// base64图片上传到服务器
var uploadImageBase64 = function(imageBase64) {
    var path = null;
    $.ajax({
        url: baseURL + "operation/merchant/uploadImageBase64",
        data: {
            "imageBase64": imageBase64
        },
        type: "post",
        async: false,
        success: function(r) {
            path = r.path;
        }
    });
    return path;
}

// 删除服务器上的图片
var deleteImage = function(imageName) {
    $.ajax({
        url: baseURL + "operation/merchant/deleteImage",
        data: {
            "imageName": imageName
        },
        type: "post",
        async: false,
        success: function(r) {

        }
    });
}*/
