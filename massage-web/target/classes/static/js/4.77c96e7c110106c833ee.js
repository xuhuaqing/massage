webpackJsonp([4],{"2Wzm":function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var s=a("RtM3"),i=a("Fd2+"),r={data:function(){return{userPhone:"",orderList:[],searchName:"",loading:!1,finished:!1,currentPage:1,total:0,pageSize:5,userId:this.$route.query.userId,searchType:"1"}},created:function(){this.userPhone=JSON.parse(localStorage.getItem("userInfo")).userPhone,this.getOrderAll()},methods:{remarksMore:function(t){this.$set(this.orderList[t],"status",0===this.orderList[t].status?1:0)},quit:function(){localStorage.removeItem("userInfo"),this.$router.replace("/")},getOrderAll:function(){var t=this;i.c.loading({duration:0,forbidClick:!0,message:"加载中...."});Object(s.i)({page:this.currentPage,pageSize:this.pageSize,userId:this.userId,projectName:"2"===this.searchType?this.searchName:"",orderName:"1"===this.searchType?this.searchName:""}).then(function(e){i.c.clear(),"0"==e.code&&e.data.length>0&&(e.data.map(function(t,e){t.status=0}),t.orderList=e.data)})},currentPageChange:function(){this.getOrderAll()}}},n={render:function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"allOrder"},[s("div",{staticClass:"distribution-nav"},[s("div",{staticClass:"header-name"},[t._v("经销商ID: "+t._s(t.userPhone))]),t._v(" "),s("div",{staticClass:"distribution-quit"},[s("img",{attrs:{src:a("6hUe"),alt:""},on:{click:function(e){t.quit()}}})])]),t._v(" "),s("div",{staticClass:"line"}),t._v(" "),s("div",{staticClass:"search-toggle"},[s("van-radio-group",{attrs:{direction:"horizontal"},model:{value:t.searchType,callback:function(e){t.searchType=e},expression:"searchType"}},[s("van-radio",{attrs:{name:"1"}},[t._v("订单名称")]),t._v(" "),s("van-radio",{attrs:{name:"2"}},[t._v("项目名称")])],1)],1),t._v(" "),s("div",{staticClass:"allOrder-search"},[s("div",{staticClass:"search-view"},[s("img",{attrs:{src:a("i0iQ"),alt:""}}),t._v(" "),s("input",{directives:[{name:"model",rawName:"v-model",value:t.searchName,expression:"searchName"}],attrs:{type:"text",placeholder:"搜索"+("1"===t.searchType?"订单名称":"2"===t.searchType?"项目名称":"")},domProps:{value:t.searchName},on:{input:function(e){e.target.composing||(t.searchName=e.target.value)}}})]),t._v(" "),s("div",{staticClass:"search-btn"},[s("van-button",{attrs:{color:"linear-gradient(to right, #6F6F6F , #414141)",round:"",block:""},on:{click:function(e){t.getOrderAll()}}},[t._v("搜索")])],1)]),t._v(" "),t.orderList.length>0?s("div",{staticClass:"order-list"},t._l(t.orderList,function(e,a){return s("div",{key:e.id,staticClass:"order-items"},[s("div",{staticClass:"items-content"},[s("div",[t._v("\n          "+t._s(e.orderName)+"\n          "),0!=e.parentId?s("van-tag",{attrs:{type:"danger"}},[t._v("送")]):t._e()],1),t._v(" "),s("div",[t._v(t._s(e.createTime))])]),t._v(" "),s("div",{staticClass:"items-content"},[s("div",[t._v("套餐金额："+t._s(e.price)+"元")]),t._v(" "),s("div",[t._v("剩余次数："+t._s(e.times)+"/"+t._s(e.totalTimes))])]),t._v(" "),s("div",{staticClass:"items-content"},[s("div",[s("van-button",{attrs:{color:"linear-gradient(to right, #6F6F6F , #414141)",round:"",block:""}},[t._v(t._s(0==e.orderType?"固定模式":"自定义模式"))])],1),t._v(" "),s("div",[t._v(t._s(e.userName))])]),t._v(" "),s("div",{staticClass:"items-remarks"},[s("div",{class:0==e.status?"yichu":""},[t._v("\n          备注："+t._s(e.remarks||"无备注")+"\n        ")]),t._v(" "),s("img",{attrs:{src:0===e.status?"../../../static/shouqi.png":"../../../static/zhankai.png",alt:""},on:{click:function(e){t.remarksMore(a)}}})])])}),0):s("van-empty",{attrs:{description:"暂无数据"}}),t._v(" "),s("van-pagination",{attrs:{"total-items":t.total,"items-per-page":5},on:{change:t.currentPageChange},model:{value:t.currentPage,callback:function(e){t.currentPage=e},expression:"currentPage"}})],1)},staticRenderFns:[]};var o=a("VU/8")(r,n,!1,function(t){a("3zIx")},"data-v-f6d29ed4",null);e.default=o.exports},"3zIx":function(t,e){},i0iQ:function(t,e){t.exports="data:image/png;base64,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"}});
//# sourceMappingURL=4.77c96e7c110106c833ee.js.map