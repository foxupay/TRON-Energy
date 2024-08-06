(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d22db0a"],{f923:function(e,t,a){"use strict";a.r(t);var l=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"app-container"},[a("el-form",{directives:[{name:"show",rawName:"v-show",value:e.showSearch,expression:"showSearch"}],ref:"queryForm",attrs:{model:e.queryParams,size:"small",inline:!0,"label-width":"68px"}},[a("el-form-item",{attrs:{label:"发送者",prop:"fromAddress"}},[a("el-input",{attrs:{placeholder:"请输入发送者地址",clearable:""},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleQuery(t)}},model:{value:e.queryParams.fromAddress,callback:function(t){e.$set(e.queryParams,"fromAddress",t)},expression:"queryParams.fromAddress"}})],1),a("el-form-item",{attrs:{label:"接收者",prop:"toAddress"}},[a("el-input",{attrs:{placeholder:"请输入接收者地址",clearable:""},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleQuery(t)}},model:{value:e.queryParams.toAddress,callback:function(t){e.$set(e.queryParams,"toAddress",t)},expression:"queryParams.toAddress"}})],1),a("el-form-item",{attrs:{label:"Hash",prop:"hash"}},[a("el-input",{attrs:{placeholder:"请输入Hash",clearable:""},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleQuery(t)}},model:{value:e.queryParams.hash,callback:function(t){e.$set(e.queryParams,"hash",t)},expression:"queryParams.hash"}})],1),a("el-form-item",{attrs:{label:"支付状态"}},[a("el-select",{attrs:{placeholder:"状态",clearable:""},model:{value:e.queryParams.status,callback:function(t){e.$set(e.queryParams,"status",t)},expression:"queryParams.status"}},[a("el-option",{attrs:{label:"请选择状态",value:""}}),a("el-option",{attrs:{label:"支付中",value:"1"}}),a("el-option",{attrs:{label:"支付成功",value:"2"}}),a("el-option",{attrs:{label:"支付失败",value:"3"}})],1)],1),a("el-form-item",{attrs:{label:"处理状态"}},[a("el-select",{attrs:{placeholder:"状态",clearable:""},model:{value:e.queryParams.handle,callback:function(t){e.$set(e.queryParams,"handle",t)},expression:"queryParams.handle"}},[a("el-option",{attrs:{label:"请选择状态",value:""}}),a("el-option",{attrs:{label:"待同步",value:"1"}}),a("el-option",{attrs:{label:"待处理",value:"2"}}),a("el-option",{attrs:{label:"已处理",value:"3"}}),a("el-option",{attrs:{label:"待人工确认",value:"4"}}),a("el-option",{attrs:{label:"交易无效",value:"5"}})],1)],1),a("el-form-item",[a("el-button",{attrs:{type:"primary",icon:"el-icon-search",size:"mini"},on:{click:e.handleQuery}},[e._v("搜索")]),a("el-button",{attrs:{icon:"el-icon-refresh",size:"mini"},on:{click:e.resetQuery}},[e._v("重置")])],1)],1),a("el-row",{staticClass:"mb8",attrs:{gutter:10}},[a("right-toolbar",{attrs:{showSearch:e.showSearch},on:{"update:showSearch":function(t){e.showSearch=t},"update:show-search":function(t){e.showSearch=t},queryTable:e.getList}})],1),a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],attrs:{data:e.transactionList}},[a("el-table-column",{attrs:{label:"编号",align:"center",prop:"id",width:"120"}}),a("el-table-column",{attrs:{label:"发送者",align:"left",width:"350"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-link",{on:{click:function(a){return e.openNewWindow("https://tronscan.org/#/address/"+t.row.fromAddress)}}},[e._v(" "+e._s(t.row.fromAddress)+" ")]),a("el-button",{directives:[{name:"clipboard",rawName:"v-clipboard:copy",value:t.row.fromAddress,expression:"scope.row.fromAddress",arg:"copy"},{name:"clipboard",rawName:"v-clipboard:success",value:e.copySuccess,expression:"copySuccess",arg:"success"}],attrs:{size:"mini",type:"text",icon:"el-icon-copy-document"}})]}}])}),a("el-table-column",{attrs:{label:"接收者",align:"left",width:"350"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-link",{on:{click:function(a){return e.openNewWindow("https://tronscan.org/#/address/"+t.row.toAddress)}}},[e._v(" "+e._s(t.row.toAddress)+" ")]),a("el-button",{directives:[{name:"clipboard",rawName:"v-clipboard:copy",value:t.row.toAddress,expression:"scope.row.toAddress",arg:"copy"},{name:"clipboard",rawName:"v-clipboard:success",value:e.copySuccess,expression:"copySuccess",arg:"success"}],attrs:{size:"mini",type:"text",icon:"el-icon-copy-document"}})]}}])}),a("el-table-column",{attrs:{label:"数量",align:"right",prop:"amount",width:"150"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(" "+e._s(t.row.amount)+" "),1===t.row.type?a("span",{staticStyle:{color:"orangered"}},[e._v("TRX")]):2===t.row.type?a("span",{staticStyle:{color:"orangered"}},[e._v("USDT")]):e._e()]}}])}),a("el-table-column",{attrs:{label:"Hash",align:"center",prop:"hash","show-overflow-tooltip":""},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-link",{attrs:{type:"primary"},on:{click:function(a){return e.openNewWindow("https://tronscan.org/#/transaction/"+t.row.hash)}}},[e._v(" "+e._s(t.row.hash)+" ")])]}}])}),a("el-table-column",{attrs:{label:"支付状态",align:"center",prop:"status"},scopedSlots:e._u([{key:"default",fn:function(t){return[1===t.row.status?a("el-tag",[e._v("支付中")]):2===t.row.status?a("el-tag",{attrs:{type:"success"}},[e._v("成功")]):3===t.row.status?a("el-tag",{attrs:{type:"danger"}},[e._v("失败")]):e._e()]}}])}),a("el-table-column",{attrs:{label:"处理状态",align:"center",prop:"status"},scopedSlots:e._u([{key:"default",fn:function(t){return[1===t.row.handle?a("el-tag",{attrs:{type:"warning"}},[e._v("待同步")]):e._e(),2===t.row.handle?a("el-tag",[e._v("待处理")]):e._e(),3===t.row.handle?a("el-tag",{attrs:{type:"success"}},[e._v("已处理")]):e._e(),5===t.row.handle||4===t.row.handle?a("el-tooltip",{attrs:{placement:"top"}},[a("div",{attrs:{slot:"content"},slot:"content"},[e._v(e._s(t.row.message))]),4===t.row.handle?a("el-tag",[e._v("待人工确认")]):e._e(),5===t.row.handle?a("el-tag",{attrs:{type:"danger"}},[e._v("交易无效")]):e._e()],1):e._e()]}}])}),a("el-table-column",{attrs:{label:"时间",align:"center",prop:"updateTime",width:"200"}}),a("el-table-column",{attrs:{label:"操作",align:"center","class-name":"small-padding"},scopedSlots:e._u([{key:"default",fn:function(t){return[2===t.row.handle&&2===t.row.status||4===t.row.handle?a("el-button",{attrs:{size:"mini",type:"text",icon:"el-icon-edit"},on:{click:function(a){return e.handleUpdate(t.row)}}},[e._v("处理 ")]):e._e()]}}])})],1),a("pagination",{directives:[{name:"show",rawName:"v-show",value:e.total>0,expression:"total>0"}],attrs:{total:e.total,page:e.queryParams.pageNum,limit:e.queryParams.pageSize},on:{"update:page":function(t){return e.$set(e.queryParams,"pageNum",t)},"update:limit":function(t){return e.$set(e.queryParams,"pageSize",t)},pagination:e.getList}}),a("el-dialog",{attrs:{title:e.title,visible:e.open,width:"500px","append-to-body":""},on:{"update:visible":function(t){e.open=t}}},[a("el-form",{ref:"form",attrs:{model:e.form,rules:e.rules,"label-width":"80px"}},[a("el-form-item",{attrs:{label:"状态",prop:"handle"}},[a("el-radio",{attrs:{label:"2"},model:{value:e.form.handle,callback:function(t){e.$set(e.form,"handle",t)},expression:"form.handle"}},[e._v("交易放行")]),a("el-radio",{attrs:{label:"5"},model:{value:e.form.handle,callback:function(t){e.$set(e.form,"handle",t)},expression:"form.handle"}},[e._v("交易取消")])],1)],1),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{attrs:{type:"primary"},on:{click:e.submitForm}},[e._v("确 定")]),a("el-button",{on:{click:e.cancel}},[e._v("取 消")])],1)],1)],1)},r=[],s=a("b775");function n(e){return Object(s["a"])({url:"/tron/transaction/list",method:"get",params:e})}function o(e){return Object(s["a"])({url:"/tron/transaction",method:"put",data:e})}var i=a("c38a"),u={name:"Transaction",data:function(){return{loading:!0,ids:[],single:!0,multiple:!0,showSearch:!0,total:0,transactionList:[],title:"",open:!1,queryParams:{pageNum:1,pageSize:10,orderByColumn:"id",isAsc:"descending",fromAddress:null,toAddress:null,amount:null,type:null,hash:null,status:null,handle:null},form:{},rules:{}}},created:function(){this.getList()},methods:{copySuccess:i["c"],openNewWindow:i["f"],getList:function(){var e=this;this.loading=!0,n(this.queryParams).then((function(t){e.transactionList=t.rows,e.total=t.total,e.loading=!1}))},cancel:function(){this.open=!1,this.reset()},reset:function(){this.form={id:null,fromAddress:null,toAddress:null,amount:null,type:null,hash:null,status:null,handle:null},this.resetForm("form")},handleQuery:function(){this.queryParams.pageNum=1,this.getList()},resetQuery:function(){this.queryParams={fromAddress:null,toAddress:null,amount:null,type:null,hash:null,status:null,handle:null},this.resetForm("queryForm"),this.handleQuery()},handleUpdate:function(e){this.reset(),this.form=e,this.open=!0,this.title="处理TRON交易"},submitForm:function(){var e=this;this.$refs["form"].validate((function(t){t&&o(e.form).then((function(t){e.$modal.msgSuccess("处理提交"),e.open=!1,e.getList()}))}))}}},c=u,d=a("2877"),p=Object(d["a"])(c,l,r,!1,null,null,null);t["default"]=p.exports}}]);