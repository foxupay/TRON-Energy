(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-ed37dce4"],{"15cd":function(e,t,n){},9429:function(e,t,n){"use strict";n.r(t);var r=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",[n("div",{staticClass:"user-info-head",on:{click:function(t){return e.editCropper()}}},[n("img",{staticClass:"img-circle img-lg",attrs:{src:e.options.img,title:"点击上传头像"}})]),n("el-dialog",{attrs:{title:e.title,visible:e.open,width:"800px","append-to-body":""},on:{"update:visible":function(t){e.open=t},opened:e.modalOpened,close:e.closeDialog}},[n("el-row",[n("el-col",{style:{height:"350px"},attrs:{xs:24,md:12}},[e.visible?n("vue-cropper",{ref:"cropper",attrs:{img:e.options.img,info:!0,autoCrop:e.options.autoCrop,autoCropWidth:e.options.autoCropWidth,autoCropHeight:e.options.autoCropHeight,fixedBox:e.options.fixedBox,outputType:e.options.outputType},on:{realTime:e.realTime}}):e._e()],1),n("el-col",{style:{height:"350px"},attrs:{xs:24,md:12}},[n("div",{staticClass:"avatar-upload-preview"},[n("img",{style:e.previews.img,attrs:{src:e.previews.url}})])])],1),n("br"),n("el-row",[n("el-col",{attrs:{lg:2,sm:3,xs:3}},[n("el-upload",{attrs:{action:"#","http-request":e.requestUpload,"show-file-list":!1,"before-upload":e.beforeUpload}},[n("el-button",{attrs:{size:"small"}},[e._v(" 选择 "),n("i",{staticClass:"el-icon-upload el-icon--right"})])],1)],1),n("el-col",{attrs:{lg:{span:1,offset:2},sm:2,xs:2}},[n("el-button",{attrs:{icon:"el-icon-plus",size:"small"},on:{click:function(t){return e.changeScale(1)}}})],1),n("el-col",{attrs:{lg:{span:1,offset:1},sm:2,xs:2}},[n("el-button",{attrs:{icon:"el-icon-minus",size:"small"},on:{click:function(t){return e.changeScale(-1)}}})],1),n("el-col",{attrs:{lg:{span:1,offset:1},sm:2,xs:2}},[n("el-button",{attrs:{icon:"el-icon-refresh-left",size:"small"},on:{click:function(t){return e.rotateLeft()}}})],1),n("el-col",{attrs:{lg:{span:1,offset:1},sm:2,xs:2}},[n("el-button",{attrs:{icon:"el-icon-refresh-right",size:"small"},on:{click:function(t){return e.rotateRight()}}})],1),n("el-col",{attrs:{lg:{span:2,offset:6},sm:2,xs:2}},[n("el-button",{attrs:{type:"primary",size:"small"},on:{click:function(t){return e.uploadImg()}}},[e._v("提 交")])],1)],1)],1)],1)},i=[],o=(n("b0c0"),n("4360")),s=n("7e79"),a=n("c0c7"),u=n("ed08"),c={components:{VueCropper:s["VueCropper"]},data:function(){return{open:!1,visible:!1,title:"修改头像",options:{img:o["a"].getters.avatar,autoCrop:!0,autoCropWidth:200,autoCropHeight:200,fixedBox:!0,outputType:"png",filename:"avatar"},previews:{},resizeHandler:null}},methods:{editCropper:function(){this.open=!0},modalOpened:function(){var e=this;this.visible=!0,this.resizeHandler||(this.resizeHandler=Object(u["b"])((function(){e.refresh()}),100)),window.addEventListener("resize",this.resizeHandler)},refresh:function(){this.$refs.cropper.refresh()},requestUpload:function(){},rotateLeft:function(){this.$refs.cropper.rotateLeft()},rotateRight:function(){this.$refs.cropper.rotateRight()},changeScale:function(e){e=e||1,this.$refs.cropper.changeScale(e)},beforeUpload:function(e){var t=this;if(-1==e.type.indexOf("image/"))this.$modal.msgError("文件格式错误，请上传图片类型,如：JPG，PNG后缀的文件。");else{var n=new FileReader;n.readAsDataURL(e),n.onload=function(){t.options.img=n.result,t.options.filename=e.name}}},uploadImg:function(){var e=this;this.$refs.cropper.getCropBlob((function(t){var n=new FormData;n.append("avatarfile",t,e.options.filename),Object(a["n"])(n).then((function(t){e.open=!1,e.options.img="/api"+t.imgUrl,o["a"].commit("SET_AVATAR",e.options.img),e.$modal.msgSuccess("修改成功"),e.visible=!1}))}))},realTime:function(e){this.previews=e},closeDialog:function(){this.options.img=o["a"].getters.avatar,this.visible=!1,window.removeEventListener("resize",this.resizeHandler)}}},l=c,d=(n("d749"),n("2877")),p=Object(d["a"])(l,r,i,!1,null,"07624ce5",null);t["default"]=p.exports},c0c7:function(e,t,n){"use strict";n.d(t,"h",(function(){return o})),n.d(t,"f",(function(){return s})),n.d(t,"a",(function(){return a})),n.d(t,"k",(function(){return u})),n.d(t,"c",(function(){return c})),n.d(t,"i",(function(){return l})),n.d(t,"b",(function(){return d})),n.d(t,"g",(function(){return p})),n.d(t,"l",(function(){return f})),n.d(t,"m",(function(){return m})),n.d(t,"n",(function(){return h})),n.d(t,"e",(function(){return g})),n.d(t,"j",(function(){return b})),n.d(t,"d",(function(){return _}));var r=n("b775"),i=n("c38a");function o(e){return Object(r["a"])({url:"/system/user/list",method:"get",params:e})}function s(e){return Object(r["a"])({url:"/system/user/"+Object(i["g"])(e),method:"get"})}function a(e){return Object(r["a"])({url:"/system/user",method:"post",data:e})}function u(e){return Object(r["a"])({url:"/system/user",method:"put",data:e})}function c(e){return Object(r["a"])({url:"/system/user/"+e,method:"delete"})}function l(e,t){var n={userId:e,password:t};return Object(r["a"])({url:"/system/user/resetPwd",method:"put",data:n})}function d(e,t){var n={userId:e,status:t};return Object(r["a"])({url:"/system/user/changeStatus",method:"put",data:n})}function p(){return Object(r["a"])({url:"/system/user/profile",method:"get"})}function f(e){return Object(r["a"])({url:"/system/user/profile",method:"put",data:e})}function m(e,t){var n={oldPassword:e,newPassword:t};return Object(r["a"])({url:"/system/user/profile/updatePwd",method:"put",params:n})}function h(e){return Object(r["a"])({url:"/system/user/profile/avatar",method:"post",data:e})}function g(e){return Object(r["a"])({url:"/system/user/authRole/"+e,method:"get"})}function b(e){return Object(r["a"])({url:"/system/user/authRole",method:"put",params:e})}function _(){return Object(r["a"])({url:"/system/user/deptTree",method:"get"})}},d749:function(e,t,n){"use strict";n("15cd")},ed08:function(e,t,n){"use strict";n.d(t,"b",(function(){return r})),n.d(t,"e",(function(){return i})),n.d(t,"c",(function(){return o})),n.d(t,"a",(function(){return s})),n.d(t,"f",(function(){return a})),n.d(t,"d",(function(){return u}));n("53ca"),n("d9e2"),n("a630"),n("a15b"),n("d81d"),n("14d9"),n("fb6a"),n("b64b"),n("d3b7"),n("4d63"),n("c607"),n("ac1f"),n("2c3e"),n("00b4"),n("25f0"),n("6062"),n("3ca3"),n("466d"),n("5319"),n("159b"),n("ddb0"),n("c38a");function r(e,t,n){var r,i,o,s,a,u=function u(){var c=+new Date-s;c<t&&c>0?r=setTimeout(u,t-c):(r=null,n||(a=e.apply(o,i),r||(o=i=null)))};return function(){for(var i=arguments.length,c=new Array(i),l=0;l<i;l++)c[l]=arguments[l];o=this,s=+new Date;var d=n&&!r;return r||(r=setTimeout(u,t)),d&&(a=e.apply(o,c),o=c=null),a}}function i(e,t){for(var n=Object.create(null),r=e.split(","),i=0;i<r.length;i++)n[r[i]]=!0;return t?function(e){return n[e.toLowerCase()]}:function(e){return n[e]}}var o="export default ",s={html:{indent_size:"2",indent_char:" ",max_preserve_newlines:"-1",preserve_newlines:!1,keep_array_indentation:!1,break_chained_methods:!1,indent_scripts:"separate",brace_style:"end-expand",space_before_conditional:!0,unescape_strings:!1,jslint_happy:!1,end_with_newline:!0,wrap_line_length:"110",indent_inner_html:!0,comma_first:!1,e4x:!0,indent_empty_lines:!0},js:{indent_size:"2",indent_char:" ",max_preserve_newlines:"-1",preserve_newlines:!1,keep_array_indentation:!1,break_chained_methods:!1,indent_scripts:"normal",brace_style:"end-expand",space_before_conditional:!0,unescape_strings:!1,jslint_happy:!0,end_with_newline:!0,wrap_line_length:"110",indent_inner_html:!0,comma_first:!1,e4x:!0,indent_empty_lines:!0}};function a(e){return e.replace(/( |^)[a-z]/g,(function(e){return e.toUpperCase()}))}function u(e){return/^[+-]?(0|([1-9]\d*))(\.\d+)?$/g.test(e)}}}]);