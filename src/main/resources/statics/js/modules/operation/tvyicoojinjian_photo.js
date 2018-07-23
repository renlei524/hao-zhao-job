// 营业执照照片
updeta_img("#clipArea1",428,321,"#file_new1","#view1","#clipBtn1",$('.cover-wrap1'));
//身份证正面


//身份证反面

//结算银行卡照片

//店铺头像

//经营场所照片

//收银台招牌照片
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
            if(clipArea == "#clipArea1") {
//                $.ajax({
//                    url: baseURL + "operation/merchant/uploadImageBase64",
//                    data: {
//                        "imageBase64": dataURL.split(',')[1]
//                    },
//                    type: "post",
//                    success: function(r) {
//                        vm.merchant.licence = r.path;
//                        $(view).css('background', 'url(' + r.imageNginxPath + vm.merchant.licence + ')');
//                    }
//                });
            }
        }
    });
}

$(".close-btn").click(function(){
   $('.phone-max-box').fadeOut();
   $('.cover').fadeOut();
})