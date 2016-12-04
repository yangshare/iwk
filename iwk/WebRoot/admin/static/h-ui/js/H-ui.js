
/* -----------H-ui前端框架-------------
* H-ui.js v3.0
* http://www.h-ui.net/
* Created & Modified by guojunhui
* Date modified 2016-01.08
*
* Copyright 2013-2016 北京颖杰联创科技有限公司 All rights reserved.
* Licensed under MIT license.
* http://opensource.org/licenses/MIT
*
*/
//tomcat访问路径
var tomcatUrl="http://127.0.0.1:8080/iwk/";
/*responsive-nav.min.js*/
!function(a,b,c){"use strict";var d=function(d,e){var f=!!b.getComputedStyle;f||(b.getComputedStyle=function(a){return this.el=a,this.getPropertyValue=function(b){var c=/(\-([a-z]){1})/g;return"float"===b&&(b="styleFloat"),c.test(b)&&(b=b.replace(c,function(){return arguments[2].toUpperCase()})),a.currentStyle[b]?a.currentStyle[b]:null},this});var g,h,i,j,k,l,m=function(a,b,c,d){if("addEventListener"in a)try{a.addEventListener(b,c,d)}catch(e){if("object"!=typeof c||!c.handleEvent)throw e;a.addEventListener(b,function(a){c.handleEvent.call(c,a)},d)}else"attachEvent"in a&&("object"==typeof c&&c.handleEvent?a.attachEvent("on"+b,function(){c.handleEvent.call(c)}):a.attachEvent("on"+b,c))},n=function(a,b,c,d){if("removeEventListener"in a)try{a.removeEventListener(b,c,d)}catch(e){if("object"!=typeof c||!c.handleEvent)throw e;a.removeEventListener(b,function(a){c.handleEvent.call(c,a)},d)}else"detachEvent"in a&&("object"==typeof c&&c.handleEvent?a.detachEvent("on"+b,function(){c.handleEvent.call(c)}):a.detachEvent("on"+b,c))},o=function(a){if(a.children.length<1)throw new Error("The Nav container has no containing elements");for(var b=[],c=0;c<a.children.length;c++)1===a.children[c].nodeType&&b.push(a.children[c]);return b},p=function(a,b){for(var c in b)a.setAttribute(c,b[c])},q=function(a,b){0!==a.className.indexOf(b)&&(a.className+=" "+b,a.className=a.className.replace(/(^\s*)|(\s*$)/g,""))},r=function(a,b){var c=new RegExp("(\\s|^)"+b+"(\\s|$)");a.className=a.className.replace(c," ").replace(/(^\s*)|(\s*$)/g,"")},s=function(a,b,c){for(var d=0;d<a.length;d++)b.call(c,d,a[d])},t=a.createElement("style"),u=a.documentElement,v=function(b,c){var d;this.options={animate:!0,transition:284,label:"Menu",insert:"before",customToggle:"",closeOnNavClick:!1,openPos:"relative",navClass:"nav-collapse",navActiveClass:"js-nav-active",jsClass:"js",init:function(){},open:function(){},close:function(){}};for(d in c)this.options[d]=c[d];if(q(u,this.options.jsClass),this.wrapperEl=b.replace("#",""),a.getElementById(this.wrapperEl))this.wrapper=a.getElementById(this.wrapperEl);else{if(!a.querySelector(this.wrapperEl))throw new Error("The nav element you are trying to select doesn't exist");this.wrapper=a.querySelector(this.wrapperEl)}this.wrapper.inner=o(this.wrapper),h=this.options,g=this.wrapper,this._init(this)};return v.prototype={destroy:function(){this._removeStyles(),r(g,"closed"),r(g,"opened"),r(g,h.navClass),r(g,h.navClass+"-"+this.index),r(u,h.navActiveClass),g.removeAttribute("style"),g.removeAttribute("aria-hidden"),n(b,"resize",this,!1),n(b,"focus",this,!1),n(a.body,"touchmove",this,!1),n(i,"touchstart",this,!1),n(i,"touchend",this,!1),n(i,"mouseup",this,!1),n(i,"keyup",this,!1),n(i,"click",this,!1),h.customToggle?i.removeAttribute("aria-hidden"):i.parentNode.removeChild(i)},toggle:function(){j===!0&&(l?this.close():this.open())},open:function(){l||(r(g,"closed"),q(g,"opened"),q(u,h.navActiveClass),q(i,"active"),g.style.position=h.openPos,p(g,{"aria-hidden":"false"}),l=!0,h.open())},close:function(){l&&(q(g,"closed"),r(g,"opened"),r(u,h.navActiveClass),r(i,"active"),p(g,{"aria-hidden":"true"}),h.animate?(j=!1,setTimeout(function(){g.style.position="absolute",j=!0},h.transition+10)):g.style.position="absolute",l=!1,h.close())},resize:function(){"none"!==b.getComputedStyle(i,null).getPropertyValue("display")?(k=!0,p(i,{"aria-hidden":"false"}),g.className.match(/(^|\s)closed(\s|$)/)&&(p(g,{"aria-hidden":"true"}),g.style.position="absolute"),this._createStyles(),this._calcHeight()):(k=!1,p(i,{"aria-hidden":"true"}),p(g,{"aria-hidden":"false"}),g.style.position=h.openPos,this._removeStyles())},handleEvent:function(a){var c=a||b.event;switch(c.type){case"touchstart":this._onTouchStart(c);break;case"touchmove":this._onTouchMove(c);break;case"touchend":case"mouseup":this._onTouchEnd(c);break;case"click":this._preventDefault(c);break;case"keyup":this._onKeyUp(c);break;case"focus":case"resize":this.resize(c)}},_init:function(){this.index=c++,q(g,h.navClass),q(g,h.navClass+"-"+this.index),q(g,"closed"),j=!0,l=!1,this._closeOnNavClick(),this._createToggle(),this._transitions(),this.resize();var d=this;setTimeout(function(){d.resize()},20),m(b,"resize",this,!1),m(b,"focus",this,!1),m(a.body,"touchmove",this,!1),m(i,"touchstart",this,!1),m(i,"touchend",this,!1),m(i,"mouseup",this,!1),m(i,"keyup",this,!1),m(i,"click",this,!1),h.init()},_createStyles:function(){t.parentNode||(t.type="text/css",a.getElementsByTagName("head")[0].appendChild(t))},_removeStyles:function(){t.parentNode&&t.parentNode.removeChild(t)},_createToggle:function(){if(h.customToggle){var b=h.customToggle.replace("#","");if(a.getElementById(b))i=a.getElementById(b);else{if(!a.querySelector(b))throw new Error("The custom nav toggle you are trying to select doesn't exist");i=a.querySelector(b)}}else{var c=a.createElement("a");c.innerHTML=h.label,p(c,{href:"#","class":"nav-toggle"}),"after"===h.insert?g.parentNode.insertBefore(c,g.nextSibling):g.parentNode.insertBefore(c,g),i=c}},_closeOnNavClick:function(){if(h.closeOnNavClick){var a=g.getElementsByTagName("a"),b=this;s(a,function(c){m(a[c],"click",function(){k&&b.toggle()},!1)})}},_preventDefault:function(a){return a.preventDefault?(a.stopImmediatePropagation&&a.stopImmediatePropagation(),a.preventDefault(),a.stopPropagation(),!1):void(a.returnValue=!1)},_onTouchStart:function(a){Event.prototype.stopImmediatePropagation||this._preventDefault(a),this.startX=a.touches[0].clientX,this.startY=a.touches[0].clientY,this.touchHasMoved=!1,n(i,"mouseup",this,!1)},_onTouchMove:function(a){(Math.abs(a.touches[0].clientX-this.startX)>10||Math.abs(a.touches[0].clientY-this.startY)>10)&&(this.touchHasMoved=!0)},_onTouchEnd:function(a){if(this._preventDefault(a),k&&!this.touchHasMoved){if("touchend"===a.type)return void this.toggle();var c=a||b.event;3!==c.which&&2!==c.button&&this.toggle()}},_onKeyUp:function(a){var c=a||b.event;13===c.keyCode&&this.toggle()},_transitions:function(){if(h.animate){var a=g.style,b="max-height "+h.transition+"ms";a.WebkitTransition=a.MozTransition=a.OTransition=a.transition=b}},_calcHeight:function(){for(var a=0,b=0;b<g.inner.length;b++)a+=g.inner[b].offsetHeight;var c="."+h.jsClass+" ."+h.navClass+"-"+this.index+".opened{max-height:"+a+"px !important} ."+h.jsClass+" ."+h.navClass+"-"+this.index+".opened.dropdown-active {max-height:9999px !important}";t.styleSheet?t.styleSheet.cssText=c:t.innerHTML=c,c=""}},new v(d,e)};"undefined"!=typeof module&&module.exports?module.exports=d:b.responsiveNav=d}(document,window,0);

/*判断浏览器*/
!function(){
	if (navigator.userAgent.match(/IEMobile\/10\.0/)) {
		var msViewportStyle = document.createElement("style")
		msViewportStyle.appendChild(
		document.createTextNode(
			"@-ms-viewport{width:auto!important}"
		)
	)
		document.getElementsByTagName("head")[0].appendChild(msViewportStyle);
	}
}();

/*placeholder 兼容性处理*/
!function(window, document, $) {
	var isInputSupported = 'placeholder' in document.createElement('input');
	var isTextareaSupported = 'placeholder' in document.createElement('textarea');
	var prototype = $.fn;
	var valHooks = $.valHooks;
	var propHooks = $.propHooks;
	var hooks;
	var placeholder;

	if (isInputSupported && isTextareaSupported) {
		placeholder = prototype.placeholder = function() {
			return this;
		};
		placeholder.input = placeholder.textarea = true;
	} else {
		placeholder = prototype.placeholder = function() {
			var $this = this;
			$this
				.filter((isInputSupported ? 'textarea' : ':input') + '[placeholder]')
				.not('.placeholder')
				.on({
					'focus.placeholder': clearPlaceholder,
					'blur.placeholder': setPlaceholder
				})
				.data('placeholder-enabled', true)
				.trigger('blur.placeholder');
			return $this;
		};
		placeholder.input = isInputSupported;
		placeholder.textarea = isTextareaSupported;
		hooks = {
			'get': function(element) {
				var $element = $(element);
				var $passwordInput = $element.data('placeholder-password');
				if ($passwordInput) {
					return $passwordInput[0].value;
				}
				return $element.data('placeholder-enabled') && $element.hasClass('placeholder') ? '' : element.value;
			},
			'set': function(element, value) {
				var $element = $(element);
				var $passwordInput = $element.data('placeholder-password');
				if ($passwordInput) {
					return $passwordInput[0].value = value;
				}
				if (!$element.data('placeholder-enabled')) {
					return element.value = value;
				}
				if (value == '') {
					element.value = value;
					if (element != safeActiveElement()) {
						setPlaceholder.call(element);
					}
				} else if ($element.hasClass('placeholder')) {
					clearPlaceholder.call(element, true, value) || (element.value = value);
				} else {
					element.value = value;
				}
				return $element;
			}
		};

		if (!isInputSupported) {
			valHooks.input = hooks;
			propHooks.value = hooks;
		}
		if (!isTextareaSupported) {
			valHooks.textarea = hooks;
			propHooks.value = hooks;
		}

		$(function() {
			$(document).delegate('form', 'submit.placeholder', function() {
				var $inputs = $('.placeholder', this).each(clearPlaceholder);
				setTimeout(function() {
					$inputs.each(setPlaceholder);
				}, 10);
			});
		});

		$(window).on('beforeunload.placeholder', function() {
			$('.placeholder').each(function() {
				this.value = '';
			});
		});
	}

	function args(elem) {
		var newAttrs = {};
		var rinlinejQuery = /^jQuery\d+$/;
		$.each(elem.attributes, function(i, attr) {
			if (attr.specified && !rinlinejQuery.test(attr.name)) {
				newAttrs[attr.name] = attr.value;
			}
		});
		return newAttrs;
	}

	function clearPlaceholder(event, value) {
		var input = this;
		var $input = $(input);
		if (input.value == $input.attr('placeholder') && $input.hasClass('placeholder')) {
			if ($input.data('placeholder-password')) {
				$input = $input.hide().next().show().attr('id', $input.removeAttr('id').data('placeholder-id'));
				if (event === true) {
					return $input[0].value = value;
				}
				$input.focus();
			} else {
				input.value = '';
				$input.removeClass('placeholder');
				input == safeActiveElement() && input.select();
			}
		}
	}

	function setPlaceholder() {
		var $replacement;
		var input = this;
		var $input = $(input);
		var id = this.id;
		if (input.value == '') {
			if (input.type == 'password') {
				if (!$input.data('placeholder-textinput')) {
					try {
						$replacement = $input.clone().prop('type','text');
					} catch(e) {
						$replacement = $('<input>').prop($.extend(args(this), { 'type': 'text' }));
					}
					$replacement
						.removeAttr('name')
						.data({
							'placeholder-password': $input,
							'placeholder-id': id
						})
						.bind('focus.placeholder', clearPlaceholder);
					$input
						.data({
							'placeholder-textinput': $replacement,
							'placeholder-id': id
						})
						.before($replacement);
				}
				$input = $input.removeAttr('id').hide().prev().attr('id', id).show();
			}
			$input.addClass('placeholder');
			$input[0].value = $input.attr('placeholder');
		} else {
			$input.removeClass('placeholder');
		}
	}
	function safeActiveElement() {
		try {
			return document.activeElement;
		} catch (exception) {}
	}
}(this, document, jQuery);

!function($) {
    $.extend({
        format : function(str, step, splitor) {
            str = str.toString();
            var len = str.length;
  
            if(len > step) {
                 var l1 = len%step, 
                     l2 = parseInt(len/step),
                     arr = [],
                     first = str.substr(0, l1);
                 if(first != '') {
                     arr.push(first);
                 };
                 for(var i=0; i<l2 ; i++) {
                     arr.push(str.substr(l1 + i*step, step));                                    
                 };
                 str = arr.join(splitor);
             };
             return str;
        }
    });
}(window.jQuery);

/* ========================================================================
 * Bootstrap: modal.js v3.3.0
 * http://getbootstrap.com/javascript/#modals
 * ========================================================================
 * Copyright 2011-2014 Twitter, Inc.
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 * ======================================================================== */
!function ($) {
	'use strict';	
	// MODAL CLASS DEFINITION
	// ======================	
	var Modal = function (element, options) {
	this.options        = options
	this.$body          = $(document.body)
	this.$element       = $(element)
	this.$backdrop      =
	this.isShown        = null
	this.scrollbarWidth = 0
	
	if (this.options.remote) {
		this.$element
		.find('.modal-content')
		.load(this.options.remote, $.proxy(function () {
			this.$element.trigger('loaded.bs.modal')
			}, this))
		}
	}
	
	Modal.VERSION  = '3.3.0'
	
	Modal.TRANSITION_DURATION = 300
	Modal.BACKDROP_TRANSITION_DURATION = 150
	
	Modal.DEFAULTS = {
		backdrop: true,
		keyboard: true,
		show: true
	}
	
	Modal.prototype.toggle = function (_relatedTarget) {
		return this.isShown ? this.hide() : this.show(_relatedTarget)
	}
	
	Modal.prototype.show = function (_relatedTarget) {
		var that = this
		var e    = $.Event('show.bs.modal', { relatedTarget: _relatedTarget })
		
		this.$element.trigger(e)
		
		if (this.isShown || e.isDefaultPrevented()) return
		
		this.isShown = true
		
		this.checkScrollbar()
		this.$body.addClass('modal-open')
		
		this.setScrollbar()
		this.escape()
		
		this.$element.on('click.dismiss.bs.modal', '[data-dismiss="modal"]', $.proxy(this.hide, this))
		
		this.backdrop(function () {
			var transition = $.support.transition && that.$element.hasClass('fade')
		
			if (!that.$element.parent().length) {
				that.$element.appendTo(that.$body) // don't move modals dom position
			}
		
			that.$element.show().scrollTop(0)
		
			if (transition) {
				that.$element[0].offsetWidth // force reflow
			}
		
			that.$element.addClass('in').attr('aria-hidden', false)
		
			that.enforceFocus()
		
			var e = $.Event('shown.bs.modal', { relatedTarget: _relatedTarget })
		
			transition ?
			that.$element.find('.modal-dialog') // wait for modal to slide in
			.one('bsTransitionEnd', function () {
			that.$element.trigger('focus').trigger(e)
			})
			.emulateTransitionEnd(Modal.TRANSITION_DURATION) :
			that.$element.trigger('focus').trigger(e)
		})
	}
	
	Modal.prototype.hide = function (e) {
		if (e) e.preventDefault()
	
		e = $.Event('hide.bs.modal')
	
		this.$element.trigger(e)
	
		if (!this.isShown || e.isDefaultPrevented()) return
	
		this.isShown = false
	
		this.escape()
	
		$(document).off('focusin.bs.modal')
	
		this.$element.removeClass('in').attr('aria-hidden', true).off('click.dismiss.bs.modal')
	
		$.support.transition && this.$element.hasClass('fade') ?
		this.$element.one('bsTransitionEnd', $.proxy(this.hideModal, this)).emulateTransitionEnd(Modal.TRANSITION_DURATION) : this.hideModal()
	}
	
	Modal.prototype.enforceFocus = function () {
		$(document)
		.off('focusin.bs.modal') // guard against infinite focus loop
		.on('focusin.bs.modal', $.proxy(function (e) {
			if (this.$element[0] !== e.target && !this.$element.has(e.target).length) {
				this.$element.trigger('focus')
			}
		}, this))
	}
	
	Modal.prototype.escape = function () {
		if (this.isShown && this.options.keyboard) {
			this.$element.on('keydown.dismiss.bs.modal', $.proxy(function (e) {
				e.which == 27 && this.hide()
			}, this))
		} else if (!this.isShown) {
			this.$element.off('keydown.dismiss.bs.modal')
		}
	}
	
	Modal.prototype.hideModal = function () {
		var that = this
		this.$element.hide()
		this.backdrop(function () {
			that.$body.removeClass('modal-open')
			that.resetScrollbar()
			that.$element.trigger('hidden.bs.modal')
		})
	}
	
	Modal.prototype.removeBackdrop = function () {
		this.$backdrop && this.$backdrop.remove()
		this.$backdrop = null
	}
	
	Modal.prototype.backdrop = function (callback) {
		var that = this
		var animate = this.$element.hasClass('fade') ? 'fade' : ''
	
		if (this.isShown && this.options.backdrop) {
			var doAnimate = $.support.transition && animate
	
			this.$backdrop = $('<div class="modal-backdrop ' + animate + '" />').prependTo(this.$element).on('click.dismiss.bs.modal', $.proxy(function (e) {
				if (e.target !== e.currentTarget) return
					this.options.backdrop == 'static'
					? this.$element[0].focus.call(this.$element[0])
					: this.hide.call(this)
				}, this))
	
			if (doAnimate) this.$backdrop[0].offsetWidth // force reflow

			this.$backdrop.addClass('in')	
			if (!callback) return	
			doAnimate ?
			this.$backdrop
			.one('bsTransitionEnd', callback)
			.emulateTransitionEnd(Modal.BACKDROP_TRANSITION_DURATION) :
			callback()	
		}
		else if (!this.isShown && this.$backdrop) {
			this.$backdrop.removeClass('in')
			var callbackRemove = function () {
				that.removeBackdrop()
				callback && callback()
			}
			$.support.transition && this.$element.hasClass('fade') ?
			this.$backdrop
			.one('bsTransitionEnd', callbackRemove)
			.emulateTransitionEnd(Modal.BACKDROP_TRANSITION_DURATION) :
			callbackRemove()

		} else if (callback) {
			callback()
		}
	}
	
	Modal.prototype.checkScrollbar = function () {
		this.scrollbarWidth = this.measureScrollbar()
	}

	Modal.prototype.setScrollbar = function () {
		var bodyPad = parseInt((this.$body.css('padding-right') || 0), 10)
		if (this.scrollbarWidth) this.$body.css('padding-right', bodyPad + this.scrollbarWidth)
	}

	Modal.prototype.resetScrollbar = function () {
		this.$body.css('padding-right', '')
	}
	
	Modal.prototype.measureScrollbar = function () { // thx walsh
		if (document.body.clientWidth >= window.innerWidth) return 0
		var scrollDiv = document.createElement('div')
		scrollDiv.className = 'modal-scrollbar-measure'
		this.$body.append(scrollDiv)
		var scrollbarWidth = scrollDiv.offsetWidth - scrollDiv.clientWidth
		this.$body[0].removeChild(scrollDiv)
		return scrollbarWidth
	}
	
	
		// MODAL PLUGIN DEFINITION
		// =======================
		
	function Plugin(option, _relatedTarget) {
		return this.each(function () {
		var $this   = $(this)
		var data    = $this.data('bs.modal')
		var options = $.extend({}, Modal.DEFAULTS, $this.data(), typeof option == 'object' && option)
		
		if (!data) $this.data('bs.modal', (data = new Modal(this, options)))
		if (typeof option == 'string') data[option](_relatedTarget)
			else if (options.show) data.show(_relatedTarget)
		})
	}
	
	var old = $.fn.modal
	
	$.fn.modal             = Plugin
	$.fn.modal.Constructor = Modal
	
	
	// MODAL NO CONFLICT
	// =================
	
	$.fn.modal.noConflict = function () {
		$.fn.modal = old
		return this
	}
	
	
	// MODAL DATA-API
	// ==============
	
	$(document).on('click.bs.modal.data-api', '[data-toggle="modal"]', function (e) {
		var $this   = $(this)
		var href    = $this.attr('href')
		var $target = $($this.attr('data-target') || (href && href.replace(/.*(?=#[^\s]+$)/, ''))) // strip for ie7
		var option  = $target.data('bs.modal') ? 'toggle' : $.extend({ remote: !/#/.test(href) && href }, $target.data(), $this.data())
	
	if ($this.is('a')) e.preventDefault()
	
	$target.one('show.bs.modal', function (showEvent) {
		if (showEvent.isDefaultPrevented()) return // only register focus restorer if modal will actually get shown
			$target.one('hidden.bs.modal', function () {
				$this.is(':visible') && $this.trigger('focus')
			})
		})
		Plugin.call($target, option, this)
	})
}(window.jQuery);

/* ========================================================================
 * Bootstrap: dropdown.js v3.3.0
 * http://getbootstrap.com/javascript/#dropdowns
 * ========================================================================
 * Copyright 2011-2014 Twitter, Inc.
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 * ======================================================================== */
!function ($) {
	'use strict';
	var backdrop = '.dropdown-backdrop';
	var toggle   = '[data-toggle="dropdown"]';
	var Dropdown = function (element) {
		$(element).on('click.bs.dropdown', this.toggle)
	}
	Dropdown.VERSION = '3.3.5';
	function getParent($this) {
		var selector = $this.attr('data-target');
		if (!selector) {
			selector = $this.attr('href');
			selector = selector && /#[A-Za-z]/.test(selector) && selector.replace(/.*(?=#[^\s]*$)/, ''); // strip for ie7
		}
		var $parent = selector && $(selector);
		return $parent && $parent.length ? $parent : $this.parent();
	}
	function clearMenus(e) {
		if (e && e.which === 3) return
		$(backdrop).remove();
		$(toggle).each(function () {
			var $this = $(this)
			var $parent = getParent($this)
			var relatedTarget = { relatedTarget: this }
			if (!$parent.hasClass('open')) return
			if (e && e.type == 'click' && /input|textarea/i.test(e.target.tagName) && $.contains($parent[0], e.target)) return
			$parent.trigger(e = $.Event('hide.bs.dropdown', relatedTarget));
			if (e.isDefaultPrevented()) return
			$this.attr('aria-expanded', 'false');
			$parent.removeClass('open').trigger('hidden.bs.dropdown', relatedTarget);
		});
	}
	Dropdown.prototype.toggle = function (e) {
		var $this = $(this)
		if ($this.is('.disabled, :disabled')) return
		var $parent  = getParent($this);
		var isActive = $parent.hasClass('open');
		clearMenus();
		if (!isActive) {
		if ('ontouchstart' in document.documentElement && !$parent.closest('.navbar-nav').length) {
			// if mobile we use a backdrop because click events don't delegate
			$(document.createElement('div')).addClass('dropdown-backdrop').insertAfter($(this)).on('click', clearMenus);
		}
		var relatedTarget = { relatedTarget: this }
		$parent.trigger(e = $.Event('show.bs.dropdown', relatedTarget));
		if (e.isDefaultPrevented()) return
			$this.trigger('focus').attr('aria-expanded', 'true');
			$parent.toggleClass('open').trigger('shown.bs.dropdown', relatedTarget);
		}
		return false
	}
	Dropdown.prototype.keydown = function (e) {
		if (!/(38|40|27|32)/.test(e.which) || /input|textarea/i.test(e.target.tagName)) return
		var $this = $(this)
		e.preventDefault()
		e.stopPropagation()
		if ($this.is('.disabled, :disabled')) return
		var $parent  = getParent($this);
		var isActive = $parent.hasClass('open');
		if (!isActive && e.which != 27 || isActive && e.which == 27) {
			if (e.which == 27) $parent.find(toggle).trigger('focus')
			return $this.trigger('click')
		}
		var desc = ' li:not(.disabled):visible a'
		var $items = $parent.find('.dropdown-menu' + desc)
		if (!$items.length) return
		var index = $items.index(e.target);
		if (e.which == 38 && index > 0)                 index--         // up
		if (e.which == 40 && index < $items.length - 1) index++         // down
		if (!~index)                                    index = 0
		$items.eq(index).trigger('focus');
	}
	function Plugin(option) {
		return this.each(function () {
			var $this = $(this);
			var data  = $this.data('bs.dropdown');
			if (!data){
				$this.data('bs.dropdown', (data = new Dropdown(this)));
			}
			if (typeof option == 'string'){
				data[option].call($this);
			}
		});
	}
	var old = $.fn.dropdown;
	$.fn.dropdown             = Plugin;
	$.fn.dropdown.Constructor = Dropdown;
	$.fn.dropdown.noConflict = function () {
		$.fn.dropdown = old;
		return this;
	}
	$(document).on('click.bs.dropdown.data-api', clearMenus).on('click.bs.dropdown.data-api', '.dropdown form', function (e) { e.stopPropagation() }).on('click.bs.dropdown.data-api', toggle, Dropdown.prototype.toggle).on('keydown.bs.dropdown.data-api', toggle, Dropdown.prototype.keydown).on('keydown.bs.dropdown.data-api', '.dropdown-menu', Dropdown.prototype.keydown);
}(window.jQuery);

/*type="password" 隐藏显示密码*/
!function ($) {
    $.fn.togglePassword = function( options ) {
        var s = $.extend( $.fn.togglePassword.defaults, options ),
        input = $( this );

        $( s.el ).on( s.ev, function() {
            "password" == $( input ).attr( "type" ) ?
                $( input ).attr( "type", "text" ) :
                $( input ).attr( "type", "password" );
        });
    };

    $.fn.togglePassword.defaults = {
        ev: "click"
    };
}(window.jQuery);

/* ========================================================================
 * Bootstrap: transition.js v3.3.0
 * http://getbootstrap.com/javascript/#transitions
 * ========================================================================
 * Copyright 2011-2014 Twitter, Inc.
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 * ======================================================================== */
+function ($) {
	'use strict';	
	function transitionEnd() {
		var el = document.createElement('bootstrap')
	
		var transEndEventNames = {
			WebkitTransition : 'webkitTransitionEnd',
			MozTransition    : 'transitionend',
			OTransition      : 'oTransitionEnd otransitionend',
			transition       : 'transitionend'
		}
	
		for (var name in transEndEventNames) {
			if (el.style[name] !== undefined) {
				return { end: transEndEventNames[name] }
			}
		}
		
		return false // explicit for ie8 (  ._.)
	}
	
	// http://blog.alexmaccaw.com/css-transitions
	$.fn.emulateTransitionEnd = function (duration) {
		var called = false
		var $el = this
		$(this).one('bsTransitionEnd', function () { called = true })
		var callback = function () { if (!called) $($el).trigger($.support.transition.end) }
		setTimeout(callback, duration)
		return this
	}
	
	$(function () {
		$.support.transition = transitionEnd()		
		if (!$.support.transition) return		
		$.event.special.bsTransitionEnd = {
			bindType: $.support.transition.end,
			delegateType: $.support.transition.end,
			handle: function (e) {
				if ($(e.target).is(this)) return e.handleObj.handler.apply(this, arguments)
			}
		}
	})
	
}(window.jQuery);

/* ========================================================================
 * Bootstrap: tooltip.js v3.3.0
 * http://getbootstrap.com/javascript/#tooltip
 * Inspired by the original jQuery.tipsy by Jason Frame
 * ========================================================================
 * Copyright 2011-2014 Twitter, Inc.
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 * ======================================================================== */
!function ($) {
	'use strict';
	
	// TOOLTIP PUBLIC CLASS DEFINITION
	// ===============================
	
	var Tooltip = function (element, options) {
		this.type       =
		this.options    =
		this.enabled    =
		this.timeout    =
		this.hoverState =
		this.$element   = null	
		this.init('tooltip', element, options)
	}

	Tooltip.VERSION  = '3.3.0'	
	Tooltip.TRANSITION_DURATION = 150

	Tooltip.DEFAULTS = {
		animation: true,
		placement: 'top',
		selector: false,
		template: '<div class="tooltip" role="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>',
		trigger: 'hover focus',
		title: '',
		delay: 0,
		html: false,
		container: false,
		viewport: {
			selector: 'body',
			padding: 0
		}
	}

	Tooltip.prototype.init = function (type, element, options) {
		this.enabled   = true
		this.type      = type
		this.$element  = $(element)
		this.options   = this.getOptions(options)
		this.$viewport = this.options.viewport && $(this.options.viewport.selector || this.options.viewport)
		
		var triggers = this.options.trigger.split(' ')
		
		for (var i = triggers.length; i--;) {
			var trigger = triggers[i]
			
			if (trigger == 'click') {
				this.$element.on('click.' + this.type, this.options.selector, $.proxy(this.toggle, this))
			} else if (trigger != 'manual') {
				var eventIn  = trigger == 'hover' ? 'mouseenter' : 'focusin'
				var eventOut = trigger == 'hover' ? 'mouseleave' : 'focusout'
			
				this.$element.on(eventIn  + '.' + this.type, this.options.selector, $.proxy(this.enter, this))
				this.$element.on(eventOut + '.' + this.type, this.options.selector, $.proxy(this.leave, this))
			}
		}
		
		this.options.selector ?
		(this._options = $.extend({}, this.options, { trigger: 'manual', selector: '' })) :
		this.fixTitle()
	}

	Tooltip.prototype.getDefaults = function () {
		return Tooltip.DEFAULTS
	}

	Tooltip.prototype.getOptions = function (options) {
		options = $.extend({}, this.getDefaults(), this.$element.data(), options)	
		if (options.delay && typeof options.delay == 'number') {
			options.delay = {
				show: options.delay,
				hide: options.delay
			}
		}	
		return options
	}

	Tooltip.prototype.getDelegateOptions = function () {
		var options  = {}
		var defaults = this.getDefaults()
	
	this._options && $.each(this._options, function (key, value) {
		if (defaults[key] != value) options[key] = value
	})
	
		return options
	}

	Tooltip.prototype.enter = function (obj) {
		var self = obj instanceof this.constructor ?
		obj : $(obj.currentTarget).data('bs.' + this.type)
		
		if (self && self.$tip && self.$tip.is(':visible')) {
			self.hoverState = 'in'
			return
		}
		
		if (!self) {
			self = new this.constructor(obj.currentTarget, this.getDelegateOptions())
			$(obj.currentTarget).data('bs.' + this.type, self)
		}
		
		clearTimeout(self.timeout)
		
		self.hoverState = 'in'
		
		if (!self.options.delay || !self.options.delay.show) return self.show()
		
		self.timeout = setTimeout(function () {
			if (self.hoverState == 'in') self.show()
		}, self.options.delay.show)
	}

	Tooltip.prototype.leave = function (obj) {
		var self = obj instanceof this.constructor ?
		obj : $(obj.currentTarget).data('bs.' + this.type)
	
		if (!self) {
			self = new this.constructor(obj.currentTarget, this.getDelegateOptions())
			$(obj.currentTarget).data('bs.' + this.type, self)
		}
	
		clearTimeout(self.timeout)
	
		self.hoverState = 'out'
	
		if (!self.options.delay || !self.options.delay.hide) return self.hide()
	
		self.timeout = setTimeout(function () {
			if (self.hoverState == 'out') self.hide()
		}, self.options.delay.hide)
	}

	Tooltip.prototype.show = function () {
		var e = $.Event('show.bs.' + this.type)		
		if (this.hasContent() && this.enabled) {
			this.$element.trigger(e)
		
			var inDom = $.contains(this.$element[0].ownerDocument.documentElement, this.$element[0])
			if (e.isDefaultPrevented() || !inDom) return
			var that = this			
			var $tip = this.tip()			
			var tipId = this.getUID(this.type)
		
			this.setContent()
			$tip.attr('id', tipId)
			this.$element.attr('aria-describedby', tipId)
		
			if (this.options.animation) $tip.addClass('fade')
					
			var placement = typeof this.options.placement == 'function' ?
			this.options.placement.call(this, $tip[0], this.$element[0]) :
			this.options.placement
		
			var autoToken = /\s?auto?\s?/i
			var autoPlace = autoToken.test(placement)
			if (autoPlace) placement = placement.replace(autoToken, '') || 'top'
		
			$tip.detach().css({ top: 0, left: 0, display: 'block' }).addClass(placement).data('bs.' + this.type, this)
		
			this.options.container ? $tip.appendTo(this.options.container) : $tip.insertAfter(this.$element)
		
			var pos          = this.getPosition()
			var actualWidth  = $tip[0].offsetWidth
			var actualHeight = $tip[0].offsetHeight
		
			if (autoPlace) {
				var orgPlacement = placement
				var $container   = this.options.container ? $(this.options.container) : this.$element.parent()
				var containerDim = this.getPosition($container)
		
				placement = placement == 'bottom' && pos.bottom + actualHeight > containerDim.bottom ? 'top'    :
				placement == 'top'    && pos.top    - actualHeight < containerDim.top    ? 'bottom' :
				placement == 'right'  && pos.right  + actualWidth  > containerDim.width  ? 'left'   :
				placement == 'left'   && pos.left   - actualWidth  < containerDim.left   ? 'right'  :
				placement
		
				$tip.removeClass(orgPlacement).addClass(placement)
			}
		
			var calculatedOffset = this.getCalculatedOffset(placement, pos, actualWidth, actualHeight)
		
			this.applyPlacement(calculatedOffset, placement)
			
			var complete = function () {
				var prevHoverState = that.hoverState
				that.$element.trigger('shown.bs.' + that.type)
				that.hoverState = null			
				if (prevHoverState == 'out') that.leave(that)
			}
		
			$.support.transition && this.$tip.hasClass('fade') ? $tip.one('bsTransitionEnd', complete).emulateTransitionEnd(Tooltip.TRANSITION_DURATION) : complete()
		}
	}

	Tooltip.prototype.applyPlacement = function (offset, placement) {
		var $tip   = this.tip()
		var width  = $tip[0].offsetWidth
		var height = $tip[0].offsetHeight
		
		// manually read margins because getBoundingClientRect includes difference
		var marginTop = parseInt($tip.css('margin-top'), 10)
		var marginLeft = parseInt($tip.css('margin-left'), 10)
		
		// we must check for NaN for ie 8/9
		if (isNaN(marginTop))  marginTop  = 0
		if (isNaN(marginLeft)) marginLeft = 0
		
		offset.top  = offset.top  + marginTop
		offset.left = offset.left + marginLeft
		
		// $.fn.offset doesn't round pixel values
		// so we use setOffset directly with our own function B-0
		$.offset.setOffset($tip[0], $.extend({
			using: function (props) {
				$tip.css({
				top: Math.round(props.top),
				left: Math.round(props.left)
				})
			}
		}, offset), 0)
		
		$tip.addClass('in')
		
		// check to see if placing tip in new offset caused the tip to resize itself
		var actualWidth  = $tip[0].offsetWidth
		var actualHeight = $tip[0].offsetHeight
		
		if (placement == 'top' && actualHeight != height) {
			offset.top = offset.top + height - actualHeight
		}
		
		var delta = this.getViewportAdjustedDelta(placement, offset, actualWidth, actualHeight)
		
		if (delta.left) offset.left += delta.left
		else offset.top += delta.top
		
		var isVertical          = /top|bottom/.test(placement)
		var arrowDelta          = isVertical ? delta.left * 2 - width + actualWidth : delta.top * 2 - height + actualHeight
		var arrowOffsetPosition = isVertical ? 'offsetWidth' : 'offsetHeight'
		
		$tip.offset(offset)
		this.replaceArrow(arrowDelta, $tip[0][arrowOffsetPosition], isVertical)
	}

	Tooltip.prototype.replaceArrow = function (delta, dimension, isHorizontal) {
		this.arrow().css(isHorizontal ? 'left' : 'top', 50 * (1 - delta / dimension) + '%').css(isHorizontal ? 'top' : 'left', '')
	}

	Tooltip.prototype.setContent = function () {
		var $tip  = this.tip()
		var title = this.getTitle()	
		$tip.find('.tooltip-inner')[this.options.html ? 'html' : 'text'](title)
		$tip.removeClass('fade in top bottom left right')
	}

	Tooltip.prototype.hide = function (callback) {
		var that = this
		var $tip = this.tip()
		var e    = $.Event('hide.bs.' + this.type)	
		function complete() {
			if (that.hoverState != 'in') $tip.detach()
			that.$element
			.removeAttr('aria-describedby')
			.trigger('hidden.bs.' + that.type)
			callback && callback()
		}	
		this.$element.trigger(e)	
		if (e.isDefaultPrevented()) return	
		$tip.removeClass('in')
	
		$.support.transition && this.$tip.hasClass('fade') ?
		$tip.one('bsTransitionEnd', complete).emulateTransitionEnd(Tooltip.TRANSITION_DURATION) : complete()	
		this.hoverState = null	
		return this
	}

	Tooltip.prototype.fixTitle = function () {
		var $e = this.$element
		if ($e.attr('title') || typeof ($e.attr('data-original-title')) != 'string') {
	  		$e.attr('data-original-title', $e.attr('title') || '').attr('title', '')
		}
	}

	Tooltip.prototype.hasContent = function () {
		return this.getTitle()
	}

	Tooltip.prototype.getPosition = function ($element) {
		$element   = $element || this.$element	
		var el     = $element[0]
		var isBody = el.tagName == 'BODY'	
		var elRect    = el.getBoundingClientRect()
		if (elRect.width == null) {
	  		// width and height are missing in IE8, so compute them manually; see https://github.com/twbs/bootstrap/issues/14093
	 		elRect = $.extend({}, elRect, { width: elRect.right - elRect.left, height: elRect.bottom - elRect.top })
		}
		var elOffset  = isBody ? { top: 0, left: 0 } : $element.offset()
		var scroll    = { scroll: isBody ? document.documentElement.scrollTop || document.body.scrollTop : $element.scrollTop() }
		var outerDims = isBody ? { width: $(window).width(), height: $(window).height() } : null	
		return $.extend({}, elRect, scroll, outerDims, elOffset)
	}

	Tooltip.prototype.getCalculatedOffset = function (placement, pos, actualWidth, actualHeight) {
		return placement == 'bottom' ? { top: pos.top + pos.height,   left: pos.left + pos.width / 2 - actualWidth / 2  } :
		placement == 'top'    ? { top: pos.top - actualHeight, left: pos.left + pos.width / 2 - actualWidth / 2  } :
		placement == 'left'   ? { top: pos.top + pos.height / 2 - actualHeight / 2, left: pos.left - actualWidth } :
		/* placement == 'right' */ { top: pos.top + pos.height / 2 - actualHeight / 2, left: pos.left + pos.width   }
	
	}

	Tooltip.prototype.getViewportAdjustedDelta = function (placement, pos, actualWidth, actualHeight) {
		var delta = { top: 0, left: 0 }
		if (!this.$viewport) return delta
		
		var viewportPadding = this.options.viewport && this.options.viewport.padding || 0
		var viewportDimensions = this.getPosition(this.$viewport)
		
		if (/right|left/.test(placement)) {
			var topEdgeOffset    = pos.top - viewportPadding - viewportDimensions.scroll
			var bottomEdgeOffset = pos.top + viewportPadding - viewportDimensions.scroll + actualHeight
			if (topEdgeOffset < viewportDimensions.top) { // top overflow
				delta.top = viewportDimensions.top - topEdgeOffset
			} else if (bottomEdgeOffset > viewportDimensions.top + viewportDimensions.height) { // bottom overflow
				delta.top = viewportDimensions.top + viewportDimensions.height - bottomEdgeOffset
			}
		} else {
			var leftEdgeOffset  = pos.left - viewportPadding
			var rightEdgeOffset = pos.left + viewportPadding + actualWidth
			if (leftEdgeOffset < viewportDimensions.left) { // left overflow
				delta.left = viewportDimensions.left - leftEdgeOffset
			} else if (rightEdgeOffset > viewportDimensions.width) { // right overflow
				delta.left = viewportDimensions.left + viewportDimensions.width - rightEdgeOffset
			}
		}	
		return delta
	}

	Tooltip.prototype.getTitle = function () {
		var title
		var $e = this.$element
		var o  = this.options	
		title = $e.attr('data-original-title') || (typeof o.title == 'function' ? o.title.call($e[0]) :  o.title)	
		return title
	}

	Tooltip.prototype.getUID = function (prefix) {
		do prefix += ~~(Math.random() * 1000000)
		while (document.getElementById(prefix))
		return prefix
	}

	Tooltip.prototype.tip = function () {
		return (this.$tip = this.$tip || $(this.options.template))
	}

	Tooltip.prototype.arrow = function () {
		return (this.$arrow = this.$arrow || this.tip().find('.tooltip-arrow'))
	}

	Tooltip.prototype.enable = function () {
		this.enabled = true
	}

	Tooltip.prototype.disable = function () {
		this.enabled = false
	}

	Tooltip.prototype.toggleEnabled = function () {
		this.enabled = !this.enabled
	}

	Tooltip.prototype.toggle = function (e) {
		var self = this
		if (e) {
			self = $(e.currentTarget).data('bs.' + this.type)
			if (!self) {
				self = new this.constructor(e.currentTarget, this.getDelegateOptions())
				$(e.currentTarget).data('bs.' + this.type, self)
			}
    	}
		self.tip().hasClass('in') ? self.leave(self) : self.enter(self)
	}

	Tooltip.prototype.destroy = function () {
		var that = this
		clearTimeout(this.timeout)
		this.hide(function () {
			that.$element.off('.' + that.type).removeData('bs.' + that.type)
		})
	}

	// TOOLTIP PLUGIN DEFINITION
	// =========================
	function Plugin(option) {
		return this.each(function () {
			var $this    = $(this)
			var data     = $this.data('bs.tooltip')
			var options  = typeof option == 'object' && option
			var selector = options && options.selector

			if (!data && option == 'destroy') return
			if (selector) {
				if (!data) $this.data('bs.tooltip', (data = {}))
				if (!data[selector]) data[selector] = new Tooltip(this, options)
			} else {
				if (!data) $this.data('bs.tooltip', (data = new Tooltip(this, options)))
			}
			if (typeof option == 'string') data[option]()
		})
	}

	var old = $.fn.tooltip
	$.fn.tooltip             = Plugin
	$.fn.tooltip.Constructor = Tooltip
	// TOOLTIP NO CONFLICT
	// ===================
	$.fn.tooltip.noConflict = function () {
		$.fn.tooltip = old
		return this
	}
}(window.jQuery);
$(function () {
	$("[data-toggle='tooltip']").tooltip();
});

/* ========================================================================
 * Bootstrap: popover.js v3.3.0
 * http://getbootstrap.com/javascript/#popovers
 * ========================================================================
 * Copyright 2011-2014 Twitter, Inc.
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 * ======================================================================== */
!function ($) {
	'use strict';	
	// POPOVER PUBLIC CLASS DEFINITION
	// ===============================	
	var Popover = function (element, options) {
		this.init('popover', element, options)
	}
	
	if (!$.fn.tooltip) throw new Error('Popover requires tooltip.js')	
	Popover.VERSION  = '3.3.0'	
	Popover.DEFAULTS = $.extend({}, $.fn.tooltip.Constructor.DEFAULTS, {
		placement: 'right',
		trigger: 'click',
		content: '',
		template: '<div class="popover" role="tooltip"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>'
	})
	
	
	// NOTE: POPOVER EXTENDS tooltip.js
	// ================================	
	Popover.prototype = $.extend({}, $.fn.tooltip.Constructor.prototype)
	
	Popover.prototype.constructor = Popover
	
	Popover.prototype.getDefaults = function () {
		return Popover.DEFAULTS
	}
	
	Popover.prototype.setContent = function () {
		var $tip    = this.tip()
		var title   = this.getTitle()
		var content = this.getContent()
	
		$tip.find('.popover-title')[this.options.html ? 'html' : 'text'](title)
		$tip.find('.popover-content').children().detach().end()[ // we use append for html objects to maintain js events
			this.options.html ? (typeof content == 'string' ? 'html' : 'append') : 'text'
		](content)
	
		$tip.removeClass('fade top bottom left right in')
	
		// IE8 doesn't accept hiding via the `:empty` pseudo selector, we have to do
		// this manually by checking the contents.
		if (!$tip.find('.popover-title').html()) $tip.find('.popover-title').hide()
	}
	
	Popover.prototype.hasContent = function () {
		return this.getTitle() || this.getContent()
	}
	
	Popover.prototype.getContent = function () {
		var $e = this.$element
		var o  = this.options
	
		return $e.attr('data-content')
		|| (typeof o.content == 'function' ?
		o.content.call($e[0]) :
		o.content)
	}
	
	Popover.prototype.arrow = function () {
		return (this.$arrow = this.$arrow || this.tip().find('.arrow'))
	}
	
	Popover.prototype.tip = function () {
		if (!this.$tip) this.$tip = $(this.options.template)
		return this.$tip
	}
	
	
	// POPOVER PLUGIN DEFINITION
	// =========================	
	function Plugin(option) {
		return this.each(function () {
			var $this    = $(this)
			var data     = $this.data('bs.popover')
			var options  = typeof option == 'object' && option
			var selector = options && options.selector
		
			if (!data && option == 'destroy') return
			if (selector) {
				if (!data) $this.data('bs.popover', (data = {}))
				if (!data[selector]) data[selector] = new Popover(this, options)
			} else {
				if (!data) $this.data('bs.popover', (data = new Popover(this, options)))
			}
			if (typeof option == 'string') data[option]()
		})
	}
	
	var old = $.fn.popover
	
	$.fn.popover             = Plugin
	$.fn.popover.Constructor = Popover
	
	
	// POPOVER NO CONFLICT
	// ===================
	
	$.fn.popover.noConflict = function () {
		$.fn.popover = old
		return this
	}
}(window.jQuery);
$(function () {
	$("[data-toggle='popover']").popover();
});

/* jQuery.Spinner 微调器*/
!function($) {
	$.fn.Spinner = function (opts) {	
		var defaults = {value:1, min:1, len:3, max:99}
		var options = $.extend(defaults, opts)
		var keyCodes = {up:38, down:40}
		return this.each(function() {		
			var a = $('<a></a>'); f(a,0,"decrease","-");	//加
			var c = $('<a></a>'); f(c,0,"increase","+");	//减
			var b = $('<input/>');f(b,1,"amount");cv(0);	//值
			
			$(this).append(a).append(b).append(c);
			a.click(function(){cv(-1)});
			b.keyup(function(){cv(0)});
			c.click(function(){cv(+1)});
			b.on('keyup paste change',function(e){
				e.keyCode==keyCodes.up&&cv(+1);
				e.keyCode==keyCodes.down&&cv(-1);
			});			
			function cv(n){
				b.val(b.val().replace(/[^\d]/g,''));
				bv=parseInt(b.val()||options.min)+n;
				bv>=options.min&&bv<=options.max&&b.val(bv);
				if(bv<=options.min){
					b.val(options.min);
					f(a,2,"disDe","decrease");
				}else{
					f(a,2,"decrease","disDe");
				}
				if(bv>=options.max){
					b.val(options.max);f(c,2,"disIn","Increase");
				}else{
					f(c,2,"increase","disIn");
				}
			}
			
		});

		function f(o,t,c,s){
			t==0&&o.addClass(c).attr("href","javascript:void(0)").append("<i></i>").find("i").append(s);
			t==1&&o.addClass(c).attr({"value":options.value,"autocomplete":"off","maxlength":options.len});
			t==2&&o.addClass(c).removeClass(s);
		}
	}	
}(window.jQuery);

/*添加收藏
<a title="收藏本站" href="javascript:;" onClick="addFavoritepage('H-ui前端框架','http://www.h-ui.net/');">收藏本站</a>
*/
/*收藏主站*/
/*function shoucang(name,site){
	$.addFavorite({
		name:name,
		site:site,
	});
}*/
!function ($) {
	$.addFavorite = function(obj) {
		obj.site=obj.site||window.location.href;
		obj.name=obj.name||document.title;	
		try{
			window.external.addFavorite(obj.site,obj.name);
		}
		catch(e){
			try{window.sidebar.addPanel(name,site,"");}
			catch(e){$.Huimodalalert("加入收藏失败，请使用Ctrl+D进行添加",2000);}
		}
	}
}(window.jQuery);

/*设为首页*/
!function ($) {
	$.setHome = function(obj){
		try{
			obj.style.behavior="url(#default#homepage)";
			obj.setHomePage(webSite);
		}
		catch(e){
			if(window.netscape){
				try {
					netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
					}
				catch(e){
					$.Huimodalalert("此操作被浏览器拒绝！\n请在浏览器地址栏输入\"about:config\"并回车\n然后将 [signed.applets.codebase_principal_support]的值设置为'true',双击即可。",2000);
				}
				var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefBranch);
				prefs.setCharPref('browser.startup.homepage',url);
			}
		}
	}
}(window.jQuery);

/*滚动*/
function marquee(height,speed,delay){
	var scrollT;
	var pause = false;
	var ScrollBox = document.getElementById("marquee");
	if(document.getElementById("holder").offsetHeight <= height) return;
	var _tmp = ScrollBox.innerHTML.replace('holder', 'holder2')
	ScrollBox.innerHTML += _tmp;
	ScrollBox.onmouseover = function(){pause = true}
	ScrollBox.onmouseout = function(){pause = false}
	ScrollBox.scrollTop = 0;
	function start(){
	    scrollT = setInterval(scrolling,speed);
	    if(!pause) ScrollBox.scrollTop += 2;
	}
	function scrolling(){
	    if(ScrollBox.scrollTop % height != 0){
	        ScrollBox.scrollTop += 2;
	        if(ScrollBox.scrollTop >= ScrollBox.scrollHeight/2) ScrollBox.scrollTop = 0;
	    }
		else{
	        clearInterval(scrollT);
	        setTimeout(start,delay);
	    }
	}
	setTimeout(start,delay);
}

/*左侧菜单-隐藏显示*/
function displaynavbar(obj){
	if($(obj).hasClass("open")){
		$(obj).removeClass("open");
		$("body").removeClass("big-page");
	}else{
		$(obj).addClass("open");
		$("body").addClass("big-page");
					
	}
}

/*Huiselect*/
!function ($) {
	$.Huiselect = function(divselectid,inputselectid) {
		var inputselect = $(inputselectid);
		$(divselectid+" cite").click(function(){
			var ul = $(divselectid+" ul");
			ul.slideToggle();
		});
		$(divselectid+" ul li a").click(function(){
			var txt = $(this).text();
			$(divselectid+" cite").html(txt);
			var value = $(this).attr("selectid");
			inputselect.val(value);
			$(divselectid+" ul").hide();
		});
		$(document).click(function(){$(divselectid+" ul").hide();});
	};
}(window.jQuery);

/*Huihover*/
!function ($) {
	$.Huihover = function(obj) {
		$(obj).hover(function(){
			$(this).addClass("hover");
		},function(){
			$(this).removeClass("hover");
		});
	}
}(window.jQuery);

/*Huifocusblur 得到失去焦点*/
!function ($) {
	$.Huifocusblur = function(obj) {
		$(obj).focus(function() {
			$(this).addClass("focus").removeClass("inputError");
		});
		$(obj).blur(function() {
			$(this).removeClass("focus");
		});
	}
}(window.jQuery);

/*Huitab 选项卡*/
!function ($) {
	$.Huitab =function(tabBar,tabCon,class_name,tabEvent,i){
		var $tab_menu=$(tabBar);
		// 初始化操作
		$tab_menu.removeClass(class_name);
		$(tabBar).eq(i).addClass(class_name);
		$(tabCon).hide();
		$(tabCon).eq(i).show();
		
		$tab_menu.on(tabEvent,function(){
			$tab_menu.removeClass(class_name);
			$(this).addClass(class_name);
			var index=$tab_menu.index(this);
			$(tabCon).hide();
			$(tabCon).eq(index).show();
		});
	}
}(window.jQuery);

/*折叠*/
!function ($) {
	$.Huifold = function(obj,obj_c,speed,obj_type,Event){
		if(obj_type == 2){
			$(obj+":first").find("b").html("-");
			$(obj_c+":first").show();
		}			
		$(obj).on(Event,function(){
			if($(this).next().is(":visible")){
				if(obj_type == 2){
					return false;
				}else{
					$(this).next().slideUp(speed).end().removeClass("selected");
					if($(this).find("b")){
						$(this).find("b").html("+");
					}
				}
			}
			else{
				if(obj_type == 3){
					$(this).next().slideDown(speed).end().addClass("selected");
					if($(this).find("b")){
						$(this).find("b").html("-");
					}
				}else{
					$(obj_c).slideUp(speed);
					$(obj).removeClass("selected");
					if($(this).find("b")){
						$(obj).find("b").html("+");
					}
					$(this).next().slideDown(speed).end().addClass("selected");
					if($(this).find("b")){
						$(this).find("b").html("-");
					}
				}
			}
		});
	}
}(window.jQuery);

/*Huitags*/
!function ($){
	$.Huitags = function(obj){
		$(obj).each(function(){
			var x = 9;
			var y = 0;
			var rand = parseInt(Math.random() * (x - y + 1) + y);
			$(this).addClass("tags"+rand);
		});
	}
}(window.jQuery);

/*返回顶部*/
var $backToTopEle = $('<a href="javascript:void(0)" class="tools-right toTop Hui-iconfont" title="返回顶部" alt="返回顶部" style="display:none">&#xe684;</a>').appendTo($("body")).click(function () {
    $("html, body").animate({
        scrollTop: 0
    }, 120);
});
var backToTopFun = function () {
    var st = $(document).scrollTop(),
        winh = $(window).height();
    (st > 0) ? $backToTopEle.show() : $backToTopEle.hide();
    /*IE6下的定位*/
    if (!window.XMLHttpRequest) {
        $backToTopEle.css("top", st + winh - 166);
    }
};

/*textarea 字数限制*/
!function ($){
	$.Huitextarealength =function(obj,maxlength){
		var v = $(obj).val();
		var l = v.length;
		if( l > maxlength){
			v = v.substring(0,maxlength);
			$(obj).val(v);
		}
		$(obj).parent().find(".textarea-length").text(v.length);
	}
}(window.jQuery);

/*Huimodalalert*/
!function ($){
	$.Huimodalalert = function (info,speed){
		if(speed==0||typeof(speed) == "undefined"){
			$(document.body).append(
				'<div id="modal-alert" class="modal modal-alert radius">'+
					'<div class="modal-alert-info">'+info+'</div>'+
					'<div class="modal-footer"> <button class="btn btn-primary radius" onClick="$.Huimodal_alert.hide()">确定</button></div>'+
				'</div>'
			);
			$("#modal-alert").fadeIn();
		}else{
			$(document.body).append(
				'<div id="modal-alert" class="modal modal-alert radius">'+
					'<div class="modal-alert-info">'+info+'</div>'+
				'</div>'
			);
			$("#modal-alert").fadeIn();
			setTimeout($.Huimodalalert.hide,speed);
		}	
	}
	$.Huimodalalert.hide=function () {
		$("#modal-alert").fadeOut("normal",function(){
			$("#modal-alert").remove();
		});
	}
}(window.jQuery);

!function ($){
	$.Huipreview = function(obj){
		/*图片预览*/
		$(obj).hover(
			function(){
				$(this).addClass("active");
				$("#tooltip-preview").remove();
				var winW=$(window).width();
				var winW5=winW/2;
				this.myTitle = this.title;
				this.title = "";
				var midimg = $(this).attr('data-preview');
				if(midimg ==''){return false;}
				else{
					var imgT=$(this).parents(".imgItem").offset().top;
					var imgL=$(this).parents(".imgItem").offset().left;	
					var imgW=$(this).parents(".imgItem").width();
					var imgH=$(this).parents(".imgItem").height();
					var ww=(imgL+imgW/2);
					if(ww < winW5){
						var tooltipLeft=(imgW+imgL)+"px";	
					}
					else{
						var tooltipRight=(winW-imgL)+"px";
					}
					var tooltip_keleyi_com = "<div id='tooltip-preview' style='top:"+ imgT +"px;right:"+ tooltipRight +";left:"+ tooltipLeft +"'><span id='tooltip-keleyi-div' class='loading' style='width:50px; height:50px'></span></div>";
					$("body").append(tooltip_keleyi_com);
					var midimgW = $(this).attr('data-width');
					var midimgH = $(this).attr('data-height');
					var imgTitle = this.myTitle ? "<br />" + this.myTitle + " 产品预览图" : "";
					/*图片预加载*/
					var image = new Image();/*创建一个Image对象*/
					image.onload = function () {
						if($('a.preview.active').attr('data-preview') == midimg){
							var midingW2 = this.width;
							var midingH2 = this.height;
							$("#tooltip-keleyi-div").css({"width":midingW2+"px","height":midingH2+"px"});
							$('#tooltip-keleyi-div').append(this);	
						}
					};
					image.src = midimg;
				}
			},
			function(){
				$(this).removeClass("active");
				this.title = this.myTitle;
				$("#tooltip-preview").remove();
			}
		);
	}
}(window.jQuery);

function stopDefault(e) {
	//阻止默认浏览器动作(W3C)
	if (e && e.preventDefault)
		e.preventDefault();
	//IE中阻止函数器默认动作的方式
	else
		window.event.returnValue = false;
	return false;
}

/*!
 * jQuery Cookie Plugin v1.4.1
 * https://github.com/carhartl/jquery-cookie
 *
 * Copyright 2006, 2014 Klaus Hartl
 * Released under the MIT license
 */
(function (factory) {
	if (typeof define === 'function' && define.amd) {
		// AMD (Register as an anonymous module)
		define(['jquery'], factory);
	} else if (typeof exports === 'object') {
		// Node/CommonJS
		module.exports = factory(require('jquery'));
	} else {
		// Browser globals
		factory(jQuery);
	}
}
(function ($) {
	var pluses = /\+/g;
	function encode(s) {
		return config.raw ? s : encodeURIComponent(s);
	}
	function decode(s) {
		return config.raw ? s : decodeURIComponent(s);
	}
	function stringifyCookieValue(value) {
		return encode(config.json ? JSON.stringify(value) : String(value));
	}
	function parseCookieValue(s) {
		if (s.indexOf('"') === 0) {
			// This is a quoted cookie as according to RFC2068, unescape...
			s = s.slice(1, -1).replace(/\\"/g, '"').replace(/\\\\/g, '\\');
		}

		try {
			// Replace server-side written pluses with spaces.
			// If we can't decode the cookie, ignore it, it's unusable.
			// If we can't parse the cookie, ignore it, it's unusable.
			s = decodeURIComponent(s.replace(pluses, ' '));
			return config.json ? JSON.parse(s) : s;
		} catch(e) {}
	}
	function read(s, converter) {
		var value = config.raw ? s : parseCookieValue(s);
		return $.isFunction(converter) ? converter(value) : value;
	}
	var config = $.cookie = function (key, value, options) {
		// Write
		if (arguments.length > 1 && !$.isFunction(value)) {
			options = $.extend({}, config.defaults, options);

			if (typeof options.expires === 'number') {
				var days = options.expires, t = options.expires = new Date();
				t.setMilliseconds(t.getMilliseconds() + days * 864e+5);
			}

			return (document.cookie = [
				encode(key), '=', stringifyCookieValue(value),
				options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
				options.path    ? '; path=' + options.path : '',
				options.domain  ? '; domain=' + options.domain : '',
				options.secure  ? '; secure' : ''
			].join(''));
		}
		// Read
		var result = key ? undefined : {},
			// To prevent the for loop in the first place assign an empty array
			// in case there are no cookies at all. Also prevents odd result when
			// calling $.cookie().
			cookies = document.cookie ? document.cookie.split('; ') : [],
			i = 0,
			l = cookies.length;
		for (; i < l; i++) {
			var parts = cookies[i].split('='),
				name = decode(parts.shift()),
				cookie = parts.join('=');
			if (key === name) {
				// If second argument (value) is a function it's a converter...
				result = read(cookie, value);
				break;
			}
			// Prevent storing a cookie that we couldn't decode.
			if (!key && (cookie = read(cookie)) !== undefined) {
				result[name] = cookie;
			}
		}
		return result;
	};
	config.defaults = {};
	$.removeCookie = function (key, options) {
		// Must not alter options, thus extending a fresh object...
		$.cookie(key, '', $.extend({}, options, { expires: -1 }));
		return !$.cookie(key);
	};
}));


/*设置cookie*/
function setCookie(name, value, Days){
	if(Days == null || Days == ''){
		Days = 300;
	}
	var exp  = new Date();
	exp.setTime(exp.getTime() + Days*24*60*60*1000);
	document.cookie = name + "="+ escape (value) + "; path=/;expires=" + exp.toGMTString();
}

/*获取cookie*/
function getCookie(name) {
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
    if(arr=document.cookie.match(reg))
        return unescape(arr[2]); 
    else 
        return null; 
}


$(function(){
	/*****表单*****/
    $.Huifocusblur(".input-text,.textarea");
	/*按钮loading*/
	$('.btn-loading').click(function () {
		var $btn = $(this);
		var btnval = $btn.val();
		$btn.addClass("disabled").val("loading").attr("disabled","disabled");
		setTimeout(function(){
			$btn.removeClass("disabled").val(btnval).removeAttr("disabled");
		}, 3000);
	});	
	/**/
	$.Huiselect("#divselect","#inputselect");

	/*全选*/
	$("table thead th input:checkbox").on("click" , function(){
		$(this).closest("table").find("tr > td:first-child input:checkbox").prop("checked",$("table thead th input:checkbox").prop("checked"));
    });
	
    /*上传*/
    $(document).on("change",".input-file",function(){
		var uploadVal=$(this).val();
		$(this).parent().find(".upload-url").val(uploadVal).focus().blur();
	});
	
	/*下拉菜单*/
	$(document).on("mouseenter",".dropDown",function(){
		$(this).addClass("hover");
	});
	$(document).on("mouseleave",".dropDown",function(){
		$(this).removeClass("hover");
	});
	$(document).on("mouseenter",".dropDown_hover",function(){
		$(this).addClass("open");
	});
	$(document).on("mouseleave",".dropDown_hover",function(){
		$(this).removeClass("open");
	});
	$(document).on("click",".dropDown-menu li a",function(){
		$(".dropDown").removeClass('open');
	});
	$(document).on("mouseenter",".menu > li",function(){
		$(this).addClass("open");
	});
	$(document).on("mouseleave",".menu > li",function(){
		$(this).removeClass("open");
	});
	
	/*tag标签*/
	$.Huitags(".tags a");

		
	/*对联广告*/
	var dual = $(".dual");
	var dual_close = $("a.dual_close");	
	var screen_w = screen.width;
	if(screen_w>1024){dual.show();}
	$(window).scroll(function(){
		var scrollTop = $(window).scrollTop();
		dual.stop().animate({top:scrollTop+260});
	});
	dual_close.click(function(){
		$(this).parent().hide();
		return false;
	});

	/*顶部展开定时自动关闭广告*/ 
	$("#banner").slideDown("slow");
	
	/*Huialert*/
	$.Huihover('.Huialert i');
	$(".Huialert i").on("click",function(){
		var Huialert = $(this).parents(".Huialert");
		Huialert.fadeOut("normal",function(){
		  Huialert.remove();
		});
	});

	/*tag标签*/
	var time1;
	$(".Hui-tags-lable").show();
	$(".Hui-tags-input").val("");
	$(document).on("blur",".Hui-tags-input",function(){
		time1 = setTimeout(function(){
			$(this).parents(".Hui-tags").find(".Hui-tags-list").slideUp();
		}, 400);
	});
	$(document).on("focus",".Hui-tags-input",function(){
		clearTimeout(time1);
	});
	$(document).on("click",".Hui-tags-input",function(){
		$(this).find(".Hui-tags-input").focus();
		$(this).find(".Hui-tags-list").slideDown();
	});
	function gettagval(obj){
		var str ="";
		var token =$(obj).parents(".Hui-tags").find(".Hui-tags-token");
		if(token.length<1){
			$(obj).parents(".Hui-tags").find(".Hui-tags-val").val("");
			return false;
		}
		for(var i = 0;i< token.length;i++){
			str += token.eq(i).text() + ",";
			$(obj).parents(".Hui-tags").find(".Hui-tags-val").val(str);
		}
	}
	$(document).on("keydown",".Hui-tags-input",function(event){
		$(this).next().hide();
		var v = $(this).val().replace(/\s+/g, "");
		var reg=/^,|,$/gi;
		v=v.replace(reg,"");
		v=$.trim(v);
		var token =$(this).parents(".Hui-tags").find(".Hui-tags-token");
		if(v!=''){
			if(event.keyCode==13||event.keyCode==108||event.keyCode==32){
				$('<span class="Hui-tags-token">'+v+'</span>').insertBefore($(this).parents(".Hui-tags").find(".Hui-tags-iptwrap"));
				$(this).val("");
				gettagval(this);
			}
		}else{
			if(event.keyCode==8){
				if(token.length>=1){
					$(this).parents(".Hui-tags").find(".Hui-tags-token:last").remove();
					gettagval(this);
				}
				else{
					$(this).parents(".Hui-tags").find(".Hui-tags-lable").show();
					return false;
				}
				
			}
		}	
	});
	
	$(document).on("click",".Hui-tags-has span",function(){
		var taghasV = $(this).text();
		taghasV=taghasV.replace(/(^\s*)|(\s*$)/g,"");
		$('<span class="Hui-tags-token">'+taghasV+'</span>').insertBefore($(this).parents(".Hui-tags").find(".Hui-tags-iptwrap"));
		gettagval(this);
		$(this).parents(".Hui-tags").find(".Hui-tags-input").focus();
	});
	$(document).on("click",".Hui-tags-token",function(){
		var token =$(this).parents(".Hui-tags").find(".Hui-tags-token");
		var it = $(this).parents(".Hui-tags");
		$(this).remove();
		switch(token.length){
			case 1 : it.find(".Hui-tags-lable").show();
			break;
		}
		var str ="";
		var token =it.find(".Hui-tags-token");
		if(token.length<1){
			it.find(".Hui-tags-val").val("");
			return false;
		}
		for(var i = 0;i< token.length;i++){
			str += token.eq(i).text() + ",";
			it.find(".Hui-tags-val").val(str);
		}		
	});
});

function displayimg(){
	$("#banner").slideUp(1000,function(){
		$("#top").slideDown(1000);
	});
}
setTimeout("displayimg()",4000);