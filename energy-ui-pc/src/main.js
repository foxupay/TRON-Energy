// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import promise from 'es6-promise';
promise.polyfill();
import axios from 'axios'
Vue.prototype.$axios = axios
axios.defaults.baseURL = "http://" + window.location.host
Vue.config.productionTip = false

import 'viewerjs/dist/viewer.css'
import Viewer from 'v-viewer'
Vue.use(Viewer)

import Element from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(Element, { size: 'small', zIndex: 3000 });
import VueClipboard from 'vue-clipboard2'
import { store } from '@/store/store'
Vue.use(VueClipboard)
import MetaInfo from 'vue-meta-info'
Vue.use(MetaInfo)

Vue.prototype.setcookie = function (name, value) {
	//设置名称为name,值为value的Cookie
	var expdate = new Date();   //初始化时间
	expdate.setTime(expdate.getTime() + 24 * 7 * 60 * 60 * 1000);   //时间单位毫秒 过期时间30分钟
	document.cookie = name + "=" + escape(value) + ";expires=" + expdate.toGMTString() + ";path=/";
}
Vue.prototype.delcookie = function (name) {
	//设置名称为name,值为value的Cookie
	var expdate = new Date();   //初始化时间
	expdate.setTime(-1);   //时间单位毫秒 过期时间30分钟
	document.cookie = name + "=" + '' + ";expires=" + expdate.toGMTString() + ";path=/";
}
Vue.prototype.getCookie = function (c_name) {
	//判断document.cookie对象里面是否存有cookie
	if (document.cookie.length > 0) {
		var c_start = document.cookie.indexOf(c_name + "=")
		//如果document.cookie对象里面有cookie则查找是否有指定的cookie，如果有则返回指定的cookie值，如果没有则返回空字符串
		if (c_start != -1) {
			c_start = c_start + c_name.length + 1
			var c_end = document.cookie.indexOf(";", c_start)
			if (c_end == -1) c_end = document.cookie.length
			return unescape(unescape(document.cookie.substring(c_start, c_end)))
		}
	}
	return ""
}


import VideoPlayer from 'vue-video-player'
require('video.js/dist/video-js.css')
require('vue-video-player/src/custom-theme.css')
import 'videojs-contrib-hls' //单是 RTMP 的话不需要第三方库，如果是 HLS 的话需要引入videojs-contrib-hls，看具体情况而定。
Vue.use(VideoPlayer);





new Vue({
	store: store,
	el: '#app',
	router,

	components: { App },
	template: '<App/>',
	methods: {
		getCookie(c_name) {
			//判断document.cookie对象里面是否存有cookie
			if (document.cookie.length > 0) {
				var c_start = document.cookie.indexOf(c_name + "=")
				//如果document.cookie对象里面有cookie则查找是否有指定的cookie，如果有则返回指定的cookie值，如果没有则返回空字符串
				if (c_start != -1) {
					c_start = c_start + c_name.length + 1
					var c_end = document.cookie.indexOf(";", c_start)
					if (c_end == -1) c_end = document.cookie.length
					return unescape(unescape(document.cookie.substring(c_start, c_end)))
				}
			}
			return ""
		},
		//清除所有cookie
		clearAllCookie() {
			var date = new Date();
			date.setTime(date.getTime() - 10000);
			var keys = document.cookie.match(/[^ =;]+(?=\=)/g);
			if (keys) {
				for (var i = keys.length; i--;) {
					document.cookie = keys[i] + "=0; expire=" + date.toGMTString() + "; path=/";
				}
			}
		}

	},
	created: function () {
		// 添加请求拦截器
		axios.interceptors.request.use((config) => {
			var urls = document.domain;
			if (urls != 'localhost') {
				var protocol = window.location.protocol;
				config.baseURL = protocol + "//" + urls
			}
			let token = this.getCookie("nltoken")
			if (token) {
				config.headers.accessToken = token;
			}
			return config;
		}, function (error) {
			// 对请求错误做些什么
			return Promise.reject(error);
		});

		//发送请求之后
		axios.interceptors.response.use(response => {
			if (response.data.code == "9999" || response.data.code == "1313") {
				// this.clearAllCookie()
				//  this.$message({
				//       showClose: true,
				//       message: '登录已失效',
				//       onClose:()=>{
				// 				localStorage.clear();

				// 				  this.$router.replace({name:'login'})			          	

				//       }
				//    });   				
			}

			return response;
		}, error => {
			return { data: { error: error } }; //返回一个空对象，主要是防止控制台报错
		});
	},
	mounted() {
		document.dispatchEvent(new Event('render-event'))
	}

})
