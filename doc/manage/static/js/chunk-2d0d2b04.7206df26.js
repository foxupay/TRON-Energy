(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d0d2b04"],{"5a24":function(e,t,n){"use strict";n.r(t);var a=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"app-container"},[n("el-form",{directives:[{name:"show",rawName:"v-show",value:e.showSearch,expression:"showSearch"}],ref:"queryForm",attrs:{model:e.queryParams,size:"small",inline:!0,"label-width":"68px"}},[n("el-form-item",{attrs:{label:"地址",prop:"address"}},[n("el-input",{attrs:{placeholder:"请输入地址",clearable:""},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleQuery(t)}},model:{value:e.queryParams.address,callback:function(t){e.$set(e.queryParams,"address",t)},expression:"queryParams.address"}})],1),n("el-form-item",{attrs:{label:"用户ID",prop:"userId"}},[n("el-input",{attrs:{placeholder:"请输入用户ID",clearable:""},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleQuery(t)}},model:{value:e.queryParams.userId,callback:function(t){e.$set(e.queryParams,"userId",t)},expression:"queryParams.userId"}})],1),n("el-form-item",{attrs:{label:"类型"}},[n("el-select",{attrs:{placeholder:"类型",clearable:""},model:{value:e.queryParams.type,callback:function(t){e.$set(e.queryParams,"type",t)},expression:"queryParams.type"}},[n("el-option",{attrs:{label:"请选择状态",value:""}}),n("el-option",{attrs:{label:"固定",value:"2"}}),n("el-option",{attrs:{label:"共享",value:"1"}})],1)],1),n("el-form-item",[n("el-button",{attrs:{type:"primary",icon:"el-icon-search",size:"mini"},on:{click:e.handleQuery}},[e._v("搜索")]),n("el-button",{attrs:{icon:"el-icon-refresh",size:"mini"},on:{click:e.resetQuery}},[e._v("重置")])],1)],1),n("el-row",{staticClass:"mb8",attrs:{gutter:10}},[n("right-toolbar",{attrs:{showSearch:e.showSearch},on:{"update:showSearch":function(t){e.showSearch=t},"update:show-search":function(t){e.showSearch=t},queryTable:e.getList}})],1),n("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],attrs:{data:e.addressList},on:{"sort-change":e.sortChange}},[n("el-table-column",{attrs:{label:"地址",align:"left",width:"380"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("el-link",{on:{click:function(n){return e.openNewWindow("https://tronscan.org/#/address/"+t.row.address)}}},[e._v(" "+e._s(t.row.address)+" ")]),e._v(" "),n("el-button",{directives:[{name:"clipboard",rawName:"v-clipboard:copy",value:t.row.address,expression:"scope.row.address",arg:"copy"},{name:"clipboard",rawName:"v-clipboard:success",value:e.copySuccess,expression:"copySuccess",arg:"success"}],attrs:{size:"mini",type:"text",icon:"el-icon-copy-document"}}),n("el-button",{attrs:{size:"mini",type:"text",icon:"el-icon-refresh-right"},on:{click:function(n){return e.handleSync(t.row.address)}}})]}}])}),n("el-table-column",{attrs:{label:"类型",align:"center",prop:"type"},scopedSlots:e._u([{key:"default",fn:function(t){return[1===t.row.type?n("span",[e._v("共享")]):e._e(),2===t.row.type?n("span",[e._v("固定("+e._s(t.row.userId)+")")]):e._e()]}}])}),n("el-table-column",{attrs:{label:"TRX",align:"center",prop:"amtTrx",sortable:"custom"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(" "+e._s(t.row.amtTrx)+" "),t.row.amtTrx>5?n("el-button",{attrs:{size:"mini",type:"text"},on:{click:function(n){return e.handleCollect(t.row.address,1)}}},[e._v("归集 ")]):e._e()]}}])}),n("el-table-column",{attrs:{label:"USDT",align:"center",prop:"amtUsdt",sortable:""},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(" "+e._s(t.row.amtUsdt)+" "),t.row.amtUsdt>0?n("el-button",{attrs:{size:"mini",type:"text"},on:{click:function(n){return e.handleCollect(t.row.address,2)}}},[e._v("归集 ")]):e._e()]}}])}),n("el-table-column",{attrs:{label:"启用",align:"center",prop:"enable"},scopedSlots:e._u([{key:"default",fn:function(t){return[1===t.row.enable?n("span",[n("el-button",{attrs:{size:"mini",type:"text"},on:{click:function(n){return e.handleActivate(t.row)}}},[e._v("激活地址 ")])],1):e._e(),2===t.row.enable?n("span",{staticStyle:{color:"green"}},[e._v("启用中")]):e._e(),3===t.row.enable?n("span",[e._v("已启用")]):e._e()]}}])}),n("el-table-column",{attrs:{label:"启用时间",align:"center",prop:"enableTime",width:"180"}}),n("el-table-column",{attrs:{label:"启用Hash",align:"center",prop:"enableHash","show-overflow-tooltip":""},scopedSlots:e._u([{key:"default",fn:function(t){return[n("el-link",{attrs:{type:"primary"},on:{click:function(n){return e.openNewWindow("https://tronscan.org/#/transaction/"+t.row.enableHash)}}},[e._v(" "+e._s(t.row.enableHash)+" ")])]}}])}),n("el-table-column",{attrs:{label:"使用状态",align:"center",prop:"used"},scopedSlots:e._u([{key:"default",fn:function(t){return[1===t.row.type?n("div",[1===t.row.used?n("span",[e._v("未使用")]):e._e(),2===t.row.used?n("span",{staticStyle:{color:"#1787ff"}},[e._v("使用中")]):e._e()]):e._e()]}}])}),n("el-table-column",{attrs:{label:"使用时间",align:"center",prop:"usedTime",width:"180"}})],1),n("pagination",{directives:[{name:"show",rawName:"v-show",value:e.total>0,expression:"total>0"}],attrs:{total:e.total,page:e.queryParams.pageNum,limit:e.queryParams.pageSize},on:{"update:page":function(t){return e.$set(e.queryParams,"pageNum",t)},"update:limit":function(t){return e.$set(e.queryParams,"pageSize",t)},pagination:e.getList}}),n("el-dialog",{attrs:{title:e.title,visible:e.open,width:"500px","append-to-body":""},on:{"update:visible":function(t){e.open=t}}},[n("el-form",{ref:"form",attrs:{model:e.form,rules:e.rules,"label-width":"80px"}},[n("el-form-item",{attrs:{label:"类型",prop:"type"}},[n("el-select",{staticStyle:{width:"240px"},attrs:{placeholder:"类型",clearable:""},model:{value:e.form.type,callback:function(t){e.$set(e.form,"type",t)},expression:"form.type"}},e._l(e.dict.type.sweep_type,(function(e){return n("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})})),1)],1)],1),n("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[n("el-button",{attrs:{type:"primary"},on:{click:e.submitForm}},[e._v("确 定")]),n("el-button",{on:{click:e.cancel}},[e._v("取 消")])],1)],1)],1)},r=[],s=(n("d81d"),n("b775"));function o(e){return Object(s["a"])({url:"/tron/address/list",method:"get",params:e})}function l(e){return Object(s["a"])({url:"/tron/address/"+e,method:"get"})}function i(e){return Object(s["a"])({url:"/tron/address",method:"post",data:e})}function u(e){return Object(s["a"])({url:"/tron/address",method:"put",data:e})}function c(e){return Object(s["a"])({url:"/tron/address/"+e,method:"delete"})}function d(e){return Object(s["a"])({url:"/tron/address/activate",method:"post",data:e})}function p(e){return Object(s["a"])({url:"/sweep/batch/sweep",method:"post",data:e})}function m(e){return Object(s["a"])({url:"/tron/address/syncBalance",method:"post",data:e})}var h=n("c38a"),f={name:"Address",dicts:["sweep_type"],data:function(){return{loading:!0,ids:[],single:!0,multiple:!0,showSearch:!0,total:0,addressList:[],title:"",open:!1,queryParams:{pageNum:1,pageSize:10,orderByColumn:"id",isAsc:"descending",address:null,type:null,userId:null},form:{},rules:{}}},created:function(){this.getList()},methods:{copySuccess:h["c"],openNewWindow:h["f"],getList:function(){var e=this;this.loading=!0,o(this.queryParams).then((function(t){e.addressList=t.rows,e.total=t.total,e.loading=!1}))},cancel:function(){this.open=!1,this.reset()},reset:function(){this.form={id:null,address:null,privateKey:null,enable:null,enableTime:null,enableHash:null,occupy:null,occupyTime:null,occupyUserId:null},this.resetForm("form")},handleQuery:function(){this.queryParams.pageNum=1,this.getList()},resetQuery:function(){this.queryParams={pageNum:1,pageSize:10,orderByColumn:"id",isAsc:"descending",address:null,type:null,userId:null},this.resetForm("queryForm"),this.handleQuery()},handleSelectionChange:function(e){this.ids=e.map((function(e){return e.id})),this.single=1!==e.length,this.multiple=!e.length},handleAdd:function(){this.reset(),this.open=!0,this.title="添加TRON地址"},handleUpdate:function(e){var t=this;this.reset();var n=e.id||this.ids;l(n).then((function(e){t.form=e.data,t.open=!0,t.title="修改TRON地址"}))},submitForm:function(){var e=this;this.$refs["form"].validate((function(t){t&&(null!=e.form.id?u(e.form).then((function(t){e.$modal.msgSuccess("修改成功"),e.open=!1,e.getList()})):i(e.form).then((function(t){e.$modal.msgSuccess("新增成功"),e.open=!1,e.getList()})))}))},handleDelete:function(e){var t=this,n=e.id||this.ids;this.$modal.confirm('是否确认删除TRON地址编号为"'+n+'"的数据项？').then((function(){return c(n)})).then((function(){t.getList(),t.$modal.msgSuccess("删除成功")})).catch((function(){}))},handleActivate:function(e){var t=this,n={address:e.address};d(n).then((function(e){t.$modal.msgSuccess("操作已提交，请稍后查看"),setTimeout((function(){t.getList()}),1e3)}))},handleCollect:function(e,t){var n=this,a={address:e,type:t};p(a).then((function(e){n.$modal.msgSuccess("操作已提交，请稍后查看"),setTimeout((function(){n.getList()}),1e3)}))},handleSync:function(e){var t=this,n={address:e};m(n).then((function(e){t.$modal.msgSuccess("操作已提交，请稍后查看"),setTimeout((function(){t.getList()}),1e3)}))},sortChange:function(e){e.order?(this.queryParams.isAsc=e.order,this.queryParams.orderByColumn=e.prop):(this.queryParams.orderByColumn="id",this.queryParams.isAsc="descending"),this.getList()}}},y=f,b=n("2877"),g=Object(b["a"])(y,a,r,!1,null,null,null);t["default"]=g.exports}}]);