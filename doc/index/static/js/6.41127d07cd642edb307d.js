webpackJsonp([6],{"4v50":function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var s=a("TQvf"),i=a.n(s),n=a("T/+H"),l=(a("mw3O"),{data:()=>({loading:!0,total:0,postList:[],pageNum:1,pageSize:10,currentPage:0}),components:{myheader:n.a},methods:{handleSizeChange(t){this.pageNum=1,this.pageSize=t,this.getList()},handleCurrentChange(t){this.pageNum=t,this.getList()},handleUpdate(t){var e="https://tronscan.org/#/transaction/"+t;window.open(e),clearInterval(this.timer),this.timer=null},copy(){let t=new i.a("#copyBtn");t.on("success",e=>{this.$message({message:"复制成功",type:"success"}),t.destroy()}),t.on("error",e=>{this.$message({message:"复制失败",type:"warning"}),t.destroy()})},getList(){this.loading=!0;var t={pageNum:this.pageNum,pageSize:this.pageSize};this.$axios({method:"GET",url:"/api/app/lease/list",params:t,headers:{"Content-Type":"application/x-www-form-urlencoded",Authorization:this.getCookie("nltoken")}}).then(t=>{"200"==t.data.code?(this.loading=!1,this.postList=t.data.rows,this.total=t.data.total):(this.loading=!1,this.$message.error(t.data.msg))}).catch(t=>{console.log(t)})}},mounted(){this.$refs.gaibian.genggai("4")},created:function(){this.$nextTick(()=>{this.getList()})},filters:{time:function(t){return 1==t.leaseDurationType?t.leaseDuration+"时":2==t.leaseDurationType?t.leaseDuration+"天":void 0},dizhi:function(t){return t.substring(0,5)+"..."+t.slice(-5)},zhuangtai:function(t){return 1==t.payStatus?"待支付":3==t.payStatus?"支付失败":4==t.payStatus?"已退款":1==t.leaseStatus?"未处理":2==t.leaseStatus?"安排中":3==t.leaseStatus?"租用成功":4==t.leaseStatus?"租用失败":void 0}}}),o={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticStyle:{background:"#e7f0f9","min-height":"100%"}},[a("myheader",{ref:"gaibian",staticClass:"gongyong"}),t._v(" "),a("div",{staticStyle:{height:"60px",width:"100%"}}),t._v(" "),a("div",{staticClass:"d1"},[a("el-table",{attrs:{data:t.postList}},[a("el-table-column",{attrs:{label:"订单进度",align:"center",prop:"status",width:"100px"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-tag",{attrs:{effect:"dark"}},[t._v("\n            "+t._s(t._f("zhuangtai")(e.row))+"\n          ")])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"资源类型",align:"center",prop:"status",width:"100px"}},[[a("el-tag",{attrs:{type:"danger"}},[t._v("能量")])]],2),t._v(" "),a("el-table-column",{attrs:{label:"租用时间",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-tag",[t._v(" "+t._s(t._f("time")(e.row)))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"完成数量",align:"center",width:"150px"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-tag",{attrs:{type:"warning"}},[t._v(t._s(e.row.resourceValue))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"消费TRX",align:"center",width:"150px"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(" "+t._s(e.row.payTrx)+"TRX ")]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"接收地址",align:"center",width:"330"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("div",{staticStyle:{display:"flex","justify-content":"flex-end","align-items":"center",color:"#1787ff"}},[a("i",{staticClass:"el-icon-document-copy",staticStyle:{"font-size":"16px",color:"#1787ff"},attrs:{id:"copyBtn","data-clipboard-text":e.row.receiveAddress,"data-clipboard-action":"copy","data-clipboard-target":"#copyTarget"},on:{click:t.copy}}),t._v(" "),a("div",{staticStyle:{"margin-left":"5px"},attrs:{id:"copyTarget"}},[t._v("\n              "+t._s(e.row.receiveAddress)+"\n            ")])])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"创建时间",align:"center",prop:"createTime"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("span",[t._v(t._s(e.row.createTime))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"回收时间",align:"center",prop:"status"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("span",[t._v(t._s(e.row.expireTime))])]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"代理Hash",align:"center","class-name":"small-padding fixed-width",width:"150","show-overflow-tooltip":""},scopedSlots:t._u([{key:"default",fn:function(e){return[null!=e.row.frozenTxId&&""!=e.row.frozenTxId?a("el-button",{attrs:{size:"mini",type:"text"},on:{click:function(a){return t.handleUpdate(e.row.frozenTxId)}}},[t._v(t._s(e.row.frozenTxId)+"\n          ")]):t._e()]}}])})],1)],1),t._v(" "),a("div",{staticClass:"d2"},[0==t.postList.length?a("div",{staticStyle:{"font-size":"12px",color:"#646566","text-align":"center","padding-top":"20px"}},[t._v("\n      暂无数据\n    ")]):t._e(),t._v(" "),t._l(t.postList,function(e){return a("div",{key:e.id,staticClass:"d3"},[a("div",{staticClass:"d4"},[a("div",{staticClass:"d5"},[t._v("订单进度")]),t._v(" "),a("div",{staticClass:"d6",staticStyle:{color:"#05c160"}},[t._v(t._s(t._f("zhuangtai")(e)))])]),t._v(" "),t._m(0,!0),t._v(" "),a("div",{staticClass:"d4"},[a("div",{staticClass:"d5"},[t._v("租用时间")]),t._v(" "),a("div",{staticClass:"d6",staticStyle:{color:"#1787ff"}},[t._v(t._s(t._f("time")(e)))])]),t._v(" "),a("div",{staticClass:"d4"},[a("div",{staticClass:"d5"},[t._v("完成数量")]),t._v(" "),a("div",{staticClass:"d6",staticStyle:{color:"#ff8d1a"}},[t._v(t._s(e.resourceValue))])]),t._v(" "),a("div",{staticClass:"d4"},[a("div",{staticClass:"d5"},[t._v("消费TRX")]),t._v(" "),a("div",{staticClass:"d6",staticStyle:{color:"#1787ff"}},[t._v(t._s(e.payTrx)+"TRX")])]),t._v(" "),a("div",{staticClass:"d4"},[a("div",{staticClass:"d5"},[t._v("接收地址")]),t._v(" "),a("div",{staticClass:"d6"},[a("div",{staticStyle:{display:"flex","justify-content":"flex-end","align-items":"center",color:"#1787ff"}},[a("i",{staticClass:"el-icon-document-copy",staticStyle:{"font-size":"16px",color:"#1787ff"},attrs:{id:"copyBtn","data-clipboard-text":e.receiveAddress,"data-clipboard-action":"copy","data-clipboard-target":"#copyTarget"},on:{click:t.copy}}),t._v(" "),a("div",{staticStyle:{"margin-left":"5px"},attrs:{id:"copyTarget"}},[t._v("\n              "+t._s(t._f("dizhi")(e.receiveAddress))+"\n            ")])])])]),t._v(" "),a("div",{staticClass:"d4"},[a("div",{staticClass:"d5"},[t._v("创建时间")]),t._v(" "),a("div",{staticClass:"d6"},[t._v(t._s(e.createTime))])]),t._v(" "),a("div",{staticClass:"d4"},[a("div",{staticClass:"d5"},[t._v("回收时间")]),t._v(" "),a("div",{staticClass:"d6"},[t._v(t._s(e.expireTime))])]),t._v(" "),a("div",{staticClass:"d4"},[a("div",{staticClass:"d5"},[t._v("代理Hash")]),t._v(" "),null!=e.frozenTxId&&""!=e.frozenTxId?a("div",{staticClass:"d6"},[a("span",{staticStyle:{color:"#1787ff"},on:{click:function(a){return t.handleUpdate(e.frozenTxId)}}},[t._v("查看")])]):t._e()])])})],2),t._v(" "),t.total>0?a("div",{staticClass:"d7"},[a("el-pagination",{attrs:{background:"",layout:"prev, pager, next",total:t.total,"pager-count":5},on:{"size-change":t.handleSizeChange,"current-change":t.handleCurrentChange}})],1):t._e()],1)},staticRenderFns:[function(){var t=this.$createElement,e=this._self._c||t;return e("div",{staticClass:"d4"},[e("div",{staticClass:"d5"},[this._v("资源类型")]),this._v(" "),e("div",{staticClass:"d6",staticStyle:{color:"#f3455a"}},[this._v("能量")])])}]};var r=a("VU/8")(l,o,!1,function(t){a("Jbjs")},"data-v-9882d3f2",null);e.default=r.exports},Jbjs:function(t,e){}});
//# sourceMappingURL=6.41127d07cd642edb307d.js.map