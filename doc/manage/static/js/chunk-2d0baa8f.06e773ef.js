(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d0baa8f"],{"37c8":function(e,t,a){"use strict";a.r(t);var l=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"app-container"},[a("el-form",{directives:[{name:"show",rawName:"v-show",value:e.showSearch,expression:"showSearch"}],ref:"queryForm",attrs:{model:e.queryParams,size:"small",inline:!0,"label-width":"100px"}},[a("el-form-item",{attrs:{label:"用户编号",prop:"userId"}},[a("el-input",{staticStyle:{width:"150px"},attrs:{placeholder:"请输入用户编号",clearable:""},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleQuery(t)}},model:{value:e.queryParams.userId,callback:function(t){e.$set(e.queryParams,"userId",t)},expression:"queryParams.userId"}})],1),a("el-form-item",{attrs:{label:"能量通道",prop:"channel"}},[a("el-input",{staticStyle:{width:"150px"},attrs:{placeholder:"请输入通道标识",clearable:""},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleQuery(t)}},model:{value:e.queryParams.channel,callback:function(t){e.$set(e.queryParams,"channel",t)},expression:"queryParams.channel"}})],1),a("el-form-item",{attrs:{label:"订单号",prop:"orderNo"}},[a("el-input",{staticStyle:{width:"150px"},attrs:{placeholder:"请输入订单号",clearable:""},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleQuery(t)}},model:{value:e.queryParams.orderNo,callback:function(t){e.$set(e.queryParams,"orderNo",t)},expression:"queryParams.orderNo"}})],1),a("el-form-item",{attrs:{label:"接收方地址",prop:"receiveAddress"}},[a("el-input",{staticStyle:{width:"150px"},attrs:{placeholder:"请输入接收方地址",clearable:""},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleQuery(t)}},model:{value:e.queryParams.receiveAddress,callback:function(t){e.$set(e.queryParams,"receiveAddress",t)},expression:"queryParams.receiveAddress"}})],1),a("el-form-item",{attrs:{label:"支付状态"}},[a("el-select",{staticStyle:{width:"150px"},attrs:{placeholder:"状态",clearable:""},model:{value:e.queryParams.payStatus,callback:function(t){e.$set(e.queryParams,"payStatus",t)},expression:"queryParams.payStatus"}},[a("el-option",{attrs:{label:"请选择状态",value:""}}),a("el-option",{attrs:{label:"未支付",value:"1"}}),a("el-option",{attrs:{label:"支付成功",value:"2"}}),a("el-option",{attrs:{label:"支付取消",value:"3"}}),a("el-option",{attrs:{label:"已退款",value:"4"}})],1)],1),a("el-form-item",{attrs:{label:"代理状态"}},[a("el-select",{staticStyle:{width:"150px"},attrs:{placeholder:"状态",clearable:""},model:{value:e.queryParams.leaseStatus,callback:function(t){e.$set(e.queryParams,"leaseStatus",t)},expression:"queryParams.leaseStatus"}},[a("el-option",{attrs:{label:"请选择状态",value:""}}),a("el-option",{attrs:{label:"未代理",value:"1"}}),a("el-option",{attrs:{label:"安排中",value:"2"}}),a("el-option",{attrs:{label:"代理成功",value:"3"}}),a("el-option",{attrs:{label:"代理失败",value:"4"}})],1)],1),a("el-form-item",[a("el-button",{attrs:{type:"primary",icon:"el-icon-search",size:"mini"},on:{click:e.handleQuery}},[e._v("搜索")]),a("el-button",{attrs:{icon:"el-icon-refresh",size:"mini"},on:{click:e.resetQuery}},[e._v("重置")])],1)],1),a("el-row",{staticClass:"mb8",attrs:{gutter:10}},[a("right-toolbar",{attrs:{showSearch:e.showSearch},on:{"update:showSearch":function(t){e.showSearch=t},"update:show-search":function(t){e.showSearch=t},queryTable:e.getList}})],1),a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],attrs:{data:e.tradeList},on:{"selection-change":e.handleSelectionChange}},[a("el-table-column",{attrs:{fixed:"",label:"用户编号",align:"center",prop:"userId","show-overflow-tooltip":""}}),a("el-table-column",{attrs:{label:"通道标识",align:"center",prop:"channel",width:"200"}}),a("el-table-column",{attrs:{label:"订单号",align:"center",prop:"orderNo",width:"200"}}),a("el-table-column",{attrs:{label:"接收者",align:"left",width:"400"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-link",{on:{click:function(a){return e.openNewWindow("https://tronscan.org/#/address/"+t.row.receiveAddress)}}},[e._v(" "+e._s(t.row.receiveAddress)+" ")]),a("el-button",{directives:[{name:"clipboard",rawName:"v-clipboard:copy",value:t.row.receiveAddress,expression:"scope.row.receiveAddress",arg:"copy"},{name:"clipboard",rawName:"v-clipboard:success",value:e.copySuccess,expression:"copySuccess",arg:"success"}],attrs:{size:"mini",type:"text",icon:"el-icon-copy-document"}})]}}])}),a("el-table-column",{attrs:{label:"资源数量",align:"center",prop:"resourceValue"}}),a("el-table-column",{attrs:{label:"能量单价",align:"center",width:"150"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(" "+e._s(t.row.priceInSun)+" SUN ")]}}])}),a("el-table-column",{attrs:{label:"租赁时长",align:"center",prop:"leaseDuration"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(" "+e._s(t.row.leaseDuration)+" "+e._s(1===t.row.leaseDurationType?"小时":"天")+" ")]}}])}),a("el-table-column",{attrs:{label:"支付金额",align:"center",width:"300"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(" "+e._s(t.row.payTrx)+" TRX / "+e._s(t.row.payUsdt)+" USDT ")]}}])}),a("el-table-column",{attrs:{label:"收款地址",align:"center",width:"350"},scopedSlots:e._u([{key:"default",fn:function(t){return t.row.payAddress?[a("el-link",{on:{click:function(a){return e.openNewWindow("https://tronscan.org/#/address/"+t.row.payAddress)}}},[e._v(" "+e._s(t.row.payAddress)+" ")]),a("el-button",{directives:[{name:"clipboard",rawName:"v-clipboard:copy",value:t.row.payAddress,expression:"scope.row.payAddress",arg:"copy"},{name:"clipboard",rawName:"v-clipboard:success",value:e.copySuccess,expression:"copySuccess",arg:"success"}],attrs:{size:"mini",type:"text",icon:"el-icon-copy-document"}})]:void 0}}],null,!0)}),a("el-table-column",{attrs:{label:"支付方式",align:"center",prop:"payWay"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(" "+e._s(1===t.row.payWay?"账户余额":2===t.row.payWay?"地址余额":3===t.row.payWay?"链上支付":"未知")+" ")]}}])}),a("el-table-column",{attrs:{label:"支付时间",align:"center",prop:"payTime",width:"200"}}),a("el-table-column",{attrs:{label:"代理时间",align:"center",prop:"leaseTime",width:"200"}}),a("el-table-column",{attrs:{label:"代理Hash",align:"center",prop:"hash","show-overflow-tooltip":""},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-link",{attrs:{type:"primary"},on:{click:function(a){return e.openNewWindow("https://tronscan.org/#/transaction/"+t.row.frozenTxId)}}},[e._v(" "+e._s(t.row.frozenTxId)+" ")])]}}])}),a("el-table-column",{attrs:{label:"到期时间",align:"center",prop:"expireTime",width:"200"}}),a("el-table-column",{attrs:{fixed:"right",label:"支付状态",align:"center",prop:"payStatus"},scopedSlots:e._u([{key:"default",fn:function(t){return[1===t.row.payStatus?a("el-tag",{attrs:{type:"warning"}},[e._v("未支付")]):e._e(),2===t.row.payStatus?a("el-tag",{attrs:{type:"success"}},[e._v("支付成功")]):e._e(),3===t.row.payStatus?a("el-tag",{attrs:{type:"info"}},[e._v("支付取消")]):e._e(),4===t.row.payStatus?a("el-tag",{attrs:{type:"danger"}},[e._v("已退款")]):e._e()]}}])}),a("el-table-column",{attrs:{fixed:"right",label:"代理状态",align:"center",prop:"leaseStatus"},scopedSlots:e._u([{key:"default",fn:function(t){return[1===t.row.leaseStatus?a("el-tag",{attrs:{type:"info"}},[e._v("未代理")]):e._e(),2===t.row.leaseStatus?a("el-tag",[e._v("安排中")]):e._e(),3===t.row.leaseStatus?a("el-tag",{attrs:{type:"success"}},[e._v("代理成功")]):e._e(),4===t.row.leaseStatus?a("el-tooltip",{attrs:{placement:"top",effect:"light"}},[a("div",{attrs:{slot:"content"},slot:"content"},[e._v(e._s(t.row.message))]),a("el-tag",{attrs:{type:"danger"}},[e._v("代理失败")])],1):e._e()]}}])})],1),a("pagination",{directives:[{name:"show",rawName:"v-show",value:e.total>0,expression:"total>0"}],attrs:{total:e.total,page:e.queryParams.pageNum,limit:e.queryParams.pageSize},on:{"update:page":function(t){return e.$set(e.queryParams,"pageNum",t)},"update:limit":function(t){return e.$set(e.queryParams,"pageSize",t)},pagination:e.getList}})],1)},r=[],n=a("5530"),s=(a("d81d"),a("b775"));function o(e){return Object(s["a"])({url:"/lease/trade/list",method:"get",params:e})}function i(e){return Object(s["a"])({url:"/lease/trade/"+e,method:"get"})}function u(e){return Object(s["a"])({url:"/lease/trade",method:"post",data:e})}function c(e){return Object(s["a"])({url:"/lease/trade",method:"put",data:e})}function d(e){return Object(s["a"])({url:"/lease/trade/"+e,method:"delete"})}var p=a("c38a"),m={name:"Trade",data:function(){return{loading:!0,ids:[],single:!0,multiple:!0,showSearch:!0,total:0,tradeList:[],title:"",open:!1,queryParams:{pageNum:1,pageSize:10,userId:null,orderNo:null,receiveAddress:null,payStatus:null,leaseStatus:null},form:{},rules:{}}},created:function(){this.getList()},methods:{copySuccess:p["c"],openNewWindow:p["f"],getList:function(){var e=this;this.loading=!0,o(this.queryParams).then((function(t){e.tradeList=t.rows,e.total=t.total,e.loading=!1}))},cancel:function(){this.open=!1,this.reset()},reset:function(){this.form={id:null,userId:null,orderNo:null,tradeNo:null,receiveAddress:null,leaseType:null,resourceValue:null,leaseDurationType:null,leaseDuration:null,priceInSun:null,payAmount:null,payTime:null,leaseTime:null,expireTime:null,frozenTxId:null,unfreezeTxId:null,payStatus:null,leaseStatus:null,createTime:null,updateTime:null},this.resetForm("form")},handleQuery:function(){this.queryParams.pageNum=1,this.getList()},resetQuery:function(){this.queryParams={pageNum:1,pageSize:10,userId:null,orderNo:null,receiveAddress:null,payStatus:null,leaseStatus:null},this.resetForm("queryForm"),this.handleQuery()},handleSelectionChange:function(e){this.ids=e.map((function(e){return e.id})),this.single=1!==e.length,this.multiple=!e.length},handleAdd:function(){this.reset(),this.open=!0,this.title="添加能量租用订单"},handleUpdate:function(e){var t=this;this.reset();var a=e.id||this.ids;i(a).then((function(e){t.form=e.data,t.open=!0,t.title="修改能量租用订单"}))},submitForm:function(){var e=this;this.$refs["form"].validate((function(t){t&&(null!=e.form.id?c(e.form).then((function(t){e.$modal.msgSuccess("修改成功"),e.open=!1,e.getList()})):u(e.form).then((function(t){e.$modal.msgSuccess("新增成功"),e.open=!1,e.getList()})))}))},handleDelete:function(e){var t=this,a=e.id||this.ids;this.$modal.confirm('是否确认删除能量租用订单编号为"'+a+'"的数据项？').then((function(){return d(a)})).then((function(){t.getList(),t.$modal.msgSuccess("删除成功")})).catch((function(){}))},handleExport:function(){this.download("lease/trade/export",Object(n["a"])({},this.queryParams),"trade_".concat((new Date).getTime(),".xlsx"))}}},y=m,h=a("2877"),f=Object(h["a"])(y,l,r,!1,null,null,null);t["default"]=f.exports}}]);