(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d22daf1"],{f919:function(t,e,a){"use strict";a.r(e);var r=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("el-dialog",{attrs:{title:"余额明细",visible:t.visible,width:"1000px",top:"5vh","append-to-body":""},on:{"update:visible":function(e){t.visible=e}}},[a("el-row",{staticClass:"mb8",attrs:{gutter:10}},[a("right-toolbar",{attrs:{search:!1},on:{queryTable:t.getList}})],1),a("el-row",[a("el-table",{ref:"table",attrs:{data:t.userList,height:"500px"}},[a("el-table-column",{attrs:{label:"订单号",prop:"orderNo"}}),a("el-table-column",{attrs:{label:"操作前(TRX)",prop:"beforeBalance"}}),a("el-table-column",{attrs:{label:"操作数量(TRX)",prop:"amount"}}),a("el-table-column",{attrs:{label:"操作后(TRX)",prop:"afterBalance"}}),a("el-table-column",{attrs:{label:"操作时间",prop:"createAt"}})],1),a("pagination",{directives:[{name:"show",rawName:"v-show",value:t.total,expression:"total"}],attrs:{total:t.total,page:t.queryParams.pageNum,limit:t.queryParams.pageSize},on:{"update:page":function(e){return t.$set(t.queryParams,"pageNum",e)},"update:limit":function(e){return t.$set(t.queryParams,"pageSize",e)},pagination:t.getList}})],1)],1)},s=[],i=(a("a9e3"),a("b775"));function l(t){return Object(i["a"])({url:"/user/log/list",method:"get",params:t})}var n={dicts:["sys_normal_disable"],props:{userId:{type:[Number,String]}},data:function(){return{visible:!1,userIds:[],total:0,userList:[],queryParams:{pageNum:1,pageSize:10,orderByColumn:"id",isAsc:"descending",userId:void 0}}},methods:{show:function(){this.userList=[],this.getList(),this.visible=!0},initUserId:function(t){this.queryParams.userId=t},getList:function(){var t=this;l(this.queryParams).then((function(e){t.userList=e.rows,t.total=e.total}))},handleQuery:function(){this.queryParams.pageNum=1,this.getList()},resetQuery:function(){this.resetForm("queryForm"),this.handleQuery()}}},o=n,u=a("2877"),p=Object(u["a"])(o,r,s,!1,null,null,null);e["default"]=p.exports}}]);