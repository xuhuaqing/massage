webpackJsonp([6],{"1jdd":function(e,t){},g9i5:function(e,t,i){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var r=i("RtM3"),a=i("Fd2+"),n={data:function(){return{userPhone:"",orderList:[],searchName:"",loading:!1,finished:!1,currentPage:1,total:0,pageSize:5}},created:function(){this.userPhone=JSON.parse(localStorage.getItem("userInfo")).userPhone,this.getOrderAll()},methods:{quit:function(){localStorage.removeItem("userInfo"),this.$router.replace("/")},getOrderAll:function(){var e=this;a.c.loading({duration:0,forbidClick:!0,message:"加载中...."});Object(r.h)({page:this.currentPage,pageSize:this.pageSize,type:1,agentId:JSON.parse(localStorage.getItem("userInfo")).provincialId}).then(function(t){a.c.clear(),"0"==t.code&&t.data.length>0&&(e.orderList=t.data)})},currentPageChange:function(){this.getOrderAll()}}},s={render:function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",{staticClass:"distribution"},[r("div",{staticClass:"distribution-nav"},[r("div",{staticClass:"distribution-order"},[r("van-button",{attrs:{plain:"",hairline:"",type:"primary",round:"",block:""},on:{click:function(t){e.$router.push("teacherList")}}},[e._v("老师列表")])],1),e._v(" "),r("div",{staticClass:"header-name"},[e._v("经销商ID: "+e._s(e.userPhone))]),e._v(" "),r("div",{staticClass:"distribution-quit"},[r("img",{attrs:{src:i("6hUe"),alt:""},on:{click:function(t){e.quit()}}})])]),e._v(" "),r("div",{staticClass:"line",staticStyle:{"margin-top":"30px"}}),e._v(" "),e.orderList.length>0?r("div",{staticClass:"order-list"},e._l(e.orderList,function(t){return r("div",{key:t.id,staticClass:"distribution-items",on:{click:function(i){e.$router.push({path:"teacherOrder",query:{userId:t.userId}})}}},[r("div",[r("div",[r("span",[e._v("店铺名称："+e._s(t.userName))])]),e._v(" "),r("div",[r("span",[e._v("店铺地址："+e._s(t.address))])]),e._v(" "),r("div",[r("span",[e._v("创建时间："+e._s(t.createTime))])])]),e._v(" "),r("div",[r("div",[r("span",[e._v("手机号："+e._s(t.phone))])]),e._v(" "),r("div",[r("span",[e._v("密码："+e._s(t.password))])])])])}),0):r("van-empty",{attrs:{description:"暂无数据"}}),e._v(" "),r("van-pagination",{attrs:{"total-items":e.total,"items-per-page":5},on:{change:e.currentPageChange},model:{value:e.currentPage,callback:function(t){e.currentPage=t},expression:"currentPage"}})],1)},staticRenderFns:[]};var o=i("VU/8")(n,s,!1,function(e){i("1jdd")},null,null);t.default=o.exports}});
//# sourceMappingURL=6.cefb0452db354f4e1ec8.js.map