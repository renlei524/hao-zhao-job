 var Lat;var Lng;
 var map = new AMap.Map("container", {
        resizeEnable: true
    });
    function geocoder(address) {
    var returnstr;
        var geocoder = new AMap.Geocoder({
        });
        //地理编码,返回地理编码结果
        geocoder.getLocation(address, function (status, result) {
            returnstr=result.geocodes[0].location.lng+","+result.geocodes[0].location.lat;
            document.getElementById("test").src="https://m.amap.com/picker/?key=d8217b980985c67038f3dd4423a2c9d9&center="+returnstr;
                $("#iframe-demo").fadeIn();
//            Lat=result[0].location.lat;
//            Lng=result[0].location.lng;
        });
    }
    function addMarker(i, d) {
        var marker = new AMap.Marker({
            map: map,
            position: [ d.location.getLng(),  d.location.getLat()]
        });
        var infoWindow = new AMap.InfoWindow({
            content: d.formattedAddress,
            offset: {x: 0, y: -30}
        });
        marker.on("mouseover", function(e) {
            infoWindow.open(map, marker.getPosition());
        });
    }
$('.layui-p').click(function(){
    var province = $('#province option:selected').text();
    var city = $('#city option:selected').text();
    var area = $('#area option:selected').text();
    var detail_site = $('#detail_site').val();
    var address = province + city + area + detail_site;
    var center=geocoder(address);
})
$('.btn-close').click(function(){
    $("#iframe-demo").hide();
})




  var iframe = document.getElementById('test').contentWindow;
            document.getElementById('test').onload = function () {
                iframe.postMessage('hello', 'https://m.amap.com/picker/');
            };
            window.addEventListener("message", function (e) {
           debugger;
            	var longitude = JSON.stringify(e.data.location).split(',')[0].split('"')[1]
            	var latitude = JSON.stringify(e.data.location).split(',')[1].split('"')[0];
                console.log(longitude + latitude);

            }, false);
            if (document.readyState == "complete") //当页面加载状态
            {
                alert(123)
            }


  	var mock = {
  		log: function(result) {
  			window.parent.setIFrameResult('log', result);
  		}
  	}
  	console = mock;
  	window.Konsole = {
  		exec: function(code) {
  			code = code || '';
  			try {
  				var result = window.eval(code);
  				window.parent.setIFrameResult('result', result);
  			} catch (e) {
  				window.parent.setIFrameResult('error', e);
  			}
  		}
  	}
  	//子页面赋值方法
  	function setvaluetoinput(lng,lat)
  	{
  	$("#lngX").val(lng);
  	$("#latY").val(lat);
  	$("#iframe-demo").hide();
  	}