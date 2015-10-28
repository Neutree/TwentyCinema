(function($,sr){

  // debouncing function from John Hann
  // http://unscriptable.com/index.php/2009/03/20/debouncing-javascript-methods/
  var debounce = function (func, threshold, execAsap) {
      var timeout;

      return function debounced () {
          var obj = this, args = arguments;
          function delayed () {
              if (!execAsap)
                  func.apply(obj, args);
              timeout = null;
          };

          if (timeout)
              clearTimeout(timeout);
          else if (execAsap)
              func.apply(obj, args);

          timeout = setTimeout(delayed, threshold || 100);
      };
  }
  // smartresize 
  jQuery.fn[sr] = function(fn){  return fn ? this.bind('resize', debounce(fn)) : this.trigger(sr); };

})(jQuery,'smartresize');




/////////////////////////////////////////
//banner
////////////////////////////////////////
var interval=setInterval(roll_img,3500); 
var roll_img_index=0;
var roll_img_title=new Array("分歧者2：绝地逃生","侏罗纪世界2015","末日崩塌");
var roll_img_introduction=new Array("一个选择  ---- 可以改造你也可以毁灭你","愚蠢的人类不长记性","一个地震震出了《2012》的感觉");
 var dir_flag=false;
function roll_img_introduction_ini(){
	document.getElementById("hot_film_title").innerHTML=roll_img_title[0];
	document.getElementById("hot_film_introduction").innerHTML=roll_img_introduction[0];
}
function roll_img_init(){
	jQuery(headImgSlidesRegion1).show();
	jQuery(headImgSlidesRegion2).show();
	jQuery(headImgSlidesRegion0).fadeIn(500);
}
function roll_img() 
{ 
	var _roll_img=document.getElementById("headImgSlidesRegion"+roll_img_index);
	if(!dir_flag){
		jQuery(_roll_img).hide(500);
		document.getElementById("hot_film_title").innerHTML=roll_img_title[roll_img_index+1];
		document.getElementById("hot_film_introduction").innerHTML=roll_img_introduction[roll_img_index+1];
		++roll_img_index;
		if(roll_img_index>=2){
			dir_flag=true;
			roll_img_index-=1;
		}
	}
	else {
		jQuery(_roll_img).show(500);
		document.getElementById("hot_film_title").innerHTML=roll_img_title[roll_img_index];
		document.getElementById("hot_film_introduction").innerHTML=roll_img_introduction[roll_img_index];
		--roll_img_index;
		if(roll_img_index<0){
			dir_flag=false;
			roll_img_index+=1;
		}
	}
	
}
function click_slide(){
	jQuery('#click_button_left').click(function(e){
		e.preventDefault();
		interval=window.clearInterval(interval);
		if(jQuery('#headImgSlidesRegion0').is(":hidden") && jQuery('#headImgSlidesRegion1').is(":hidden")){
			jQuery('#headImgSlidesRegion1').show(500);
			document.getElementById("hot_film_title").innerHTML=roll_img_title[1];
			document.getElementById("hot_film_introduction").innerHTML=roll_img_introduction[1];
		}
		else if(jQuery('#headImgSlidesRegion0').is(":hidden")&&jQuery('#headImgSlidesRegion1').is(":visible")){
			jQuery('#headImgSlidesRegion0').show(500);
			document.getElementById("hot_film_title").innerHTML=roll_img_title[0];
			document.getElementById("hot_film_introduction").innerHTML=roll_img_introduction[0];
		}
		else if(jQuery('#headImgSlidesRegion0').is(":visible")&&jQuery('#headImgSlidesRegion1').is(":visible")){
			jQuery('#headImgSlidesRegion0').hide(800);
			jQuery('#headImgSlidesRegion1').hide(800);
			document.getElementById("hot_film_title").innerHTML=roll_img_title[2];
			document.getElementById("hot_film_introduction").innerHTML=roll_img_introduction[2];
		}
		interval=setInterval(roll_img,3500); 
	});
	jQuery('#click_button_right').click(function(e){
		e.preventDefault();
		interval=window.clearInterval(interval);
		if(jQuery('#headImgSlidesRegion0').is(":hidden") && jQuery('#headImgSlidesRegion1').is(":hidden")){
			jQuery('#headImgSlidesRegion1').show(800);
			jQuery('#headImgSlidesRegion0').show(800);
			
			document.getElementById("hot_film_title").innerHTML=roll_img_title[0];
			document.getElementById("hot_film_introduction").innerHTML=roll_img_introduction[0];
		}
		else if(jQuery('#headImgSlidesRegion0').is(":hidden")&&jQuery('#headImgSlidesRegion1').is(":visible")){
			jQuery('#headImgSlidesRegion1').hide(500);
			document.getElementById("hot_film_title").innerHTML=roll_img_title[2];
			document.getElementById("hot_film_introduction").innerHTML=roll_img_introduction[2];
		}
		else if(jQuery('#headImgSlidesRegion0').is(":visible")&&jQuery('#headImgSlidesRegion1').is(":visible")){
			jQuery('#headImgSlidesRegion0').hide(500);
			document.getElementById("hot_film_title").innerHTML=roll_img_title[1];
			document.getElementById("hot_film_introduction").innerHTML=roll_img_introduction[1];
		}
		interval=setInterval(roll_img,3500); 
	});
}

///////////////////////////////////////////////
//   share
//   share
//////////////////////////////////////////////
//sina



/////////////////////////////////////////////
//ready(function()){};
////////////////////////////////////////////
jQuery(document).ready(function(){
	$wrapper = $('#wrapper');
	$drawerLeft = $('#drawer-left');

	///////////////////////////////
	// Set Home Slideshow Height
	///////////////////////////////

	function setHomeBannerHeight() {
		var windowHeight = jQuery(window).height();	
		jQuery('#header').height(windowHeight);
	}

	
	function hidebut(){
		jQuery(".btn1").click(function(){
			  jQuery(".myp").slideUp(500);
			  });
			  jQuery(".btn2").click(function(){
			  jQuery(".myp").slideDown(500);
			  });
	}
	///////////////////////////////
	// Center Home Slideshow Text
	///////////////////////////////

	function centerHomeBannerText() {
			var bannerText = jQuery('#header > .center');
//			var bannerTextTop = (jQuery('#header > .top').actual('height'))*2;
			var bannerTextTop = (jQuery(window).height()-485)/2;
			//alert("5555"+bannerTextTop);
			bannerText.css('padding-top', bannerTextTop+'px');		
			bannerText.show();
	}


	
	///////////////////////////////
	// SlideNav
	///////////////////////////////

	function setSlideNav(){
		jQuery(".toggleDrawer").click(function(e){
			//alert($wrapper.css('marginRight'));
			e.preventDefault();
			if($wrapper.css('marginLeft')=='0px'){
				$drawerLeft.animate({marginLeft : 0},200);
				$wrapper.animate({marginLeft : 300},200);
			}
			else{
				$drawerLeft.animate({marginLeft : -300},200);
				$wrapper.animate({marginLeft : 0},200);
			}
			
		})
	}

	function setHeaderBackground() {		
		var scrollTop = jQuery(window).scrollTop(); // our current vertical position from the top	
		
		if (scrollTop > 300 || jQuery(window).width() < 700) { 
			jQuery('#header .top').addClass('solid');
		} else {
			jQuery('#header .top').removeClass('solid');		
		}
	}


	///////////////////////////////
	// Initialize
	///////////////////////////////

	jQuery.noConflict();
	setHomeBannerHeight();
	centerHomeBannerText();//hidebut();
	setSlideNav();
	setHeaderBackground();
	roll_img_introduction_ini();
	roll_img_init();
	click_slide();
	hidebut();
	//Resize events
	jQuery(window).smartresize(function(){
		setHomeBannerHeight();
		centerHomeBannerText();
		setHeaderBackground();
		
	});

	
	
	//Set Down Arrow Button
	jQuery('#scrollToContent').click(function(e){
		e.preventDefault();
		jQuery.scrollTo("#hot", 500, { offset:-(jQuery('#header .top').height()*2), axis:'y' });
	});
	
	jQuery('nav > ul > li > a').click(function(e){
		e.preventDefault();
		jQuery.scrollTo(jQuery(this).attr('href'), 400, { offset:-(jQuery('#header .top').height()*2), axis:'y' });
	})

	jQuery(window).scroll( function() {
	   setHeaderBackground();
	});

});