webpackJsonp([6],{ESII:function(e,t,i){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var s=i("RtM3"),n=i("Fd2+"),a={data:function(){return{userInfo:JSON.parse(localStorage.getItem("userInfo")),activeNav:0,orderList:[],showEquipmentPicker:!1,equipmentId:"",equipment:[],equipmentData:[]}},created:function(){document.title="订单详情",this.getUserOrder(),this.getEquipmentList()},methods:{selectedView:function(e){this.activeNav&&this.$set(this.orderList[e],"selected",!this.orderList[e].selected)},getEquipmentList:function(){var e=this;Object(s.g)({businessId:this.userInfo.businessId,type:1}).then(function(t){"000000"==t.code&&t.data.length>0&&(e.equipmentId=t.data[0].equipmentId,e.equipment=t.data.map(function(e){return e.equipmentId}),e.equipmentData=t.data)})},equipmentToggle:function(e){this.equipmentId=e,this.showEquipmentPicker=!1},operationEquipment:function(e,t){var i=this,a=this,r=0;this.orderList.map(function(e){e.selected&&r++}),console.log("this.orderList",this.orderList,r),1==t&&0==r||0==this.activeNav?n.c.fail("请选择要美力的套餐！"):n.a.confirm({title:"提示",message:"是否开始美力"}).then(function(){if(a.equipmentId){var r={};if(console.log(t),0==t)r={numberId:e.numberId,orderId:e.orderId,everyTime:e.everyTime,equipmentId:a.equipmentId};else if(1==t){var o=i.orderList.filter(function(e){return e.selected}),c=0;o.map(function(e){c+=e.everyTime}),r={numberId:o.filter(function(e){return e.selected}).map(function(e){return e.numberId}).join(","),orderId:o[0].orderId,everyTime:c,equipmentId:a.equipmentId}}Object(s.r)(r).then(function(e){"000000"==e.code?(n.c.success("美力开始！"),a.getUserOrder()):"000001"==e.code?n.c.fail("当前设备正在使用，请等待！"):n.c.fail(e.msg)})}else n.c.fail("未选择设备！")}).catch(function(){})},getUserOrder:function(){var e=this;Object(s.v)({userName:this.userInfo.userName,userPhone:this.userInfo.userPhone,businessId:this.userInfo.businessId}).then(function(t){(t.code="000000")&&(console.log("asdasddasasdasdasdasdsda",e.activeNav,t.data),0==e.activeNav?(t.data.consumed.map(function(e){return e.selected=!1}),e.orderList=t.data.consumed||[]):(t.data.notConsumed.map(function(e){return e.selected=!1}),e.orderList=t.data.notConsumed||[]))})},quitLogin:function(){localStorage.removeItem("userInfo"),this.$router.replace("/")},toggleActiveNav:function(e){this.activeNav=e,this.getUserOrder()}}},r={render:function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("div",{staticClass:"customer"},[i("div",{staticClass:"customer-header"},[i("div",{staticClass:"header-left"},[e._v("顾客姓名："+e._s(e.userInfo.userName))]),e._v(" "),i("div",{staticClass:"header-header-right"},[i("van-button",{attrs:{color:"linear-gradient(to right, #6F6F6F , #414141)",round:"",block:""},on:{click:e.quitLogin}},[e._v("退出登陆")])],1)]),e._v(" "),i("div",{staticClass:"header-equipment"},[i("span",[e._v("设 备：")]),e._v(" "),i("van-field",{attrs:{readonly:"",clickable:"","is-link":"",name:"picker",value:e.equipmentId,placeholder:"选择设备"},on:{click:function(t){e.showEquipmentPicker=!0}}}),e._v(" "),i("van-popup",{attrs:{position:"bottom"},model:{value:e.showEquipmentPicker,callback:function(t){e.showEquipmentPicker=t},expression:"showEquipmentPicker"}},[i("van-picker",{attrs:{title:"选择设备","show-toolbar":"",columns:e.equipment},on:{confirm:e.equipmentToggle,cancel:function(t){e.showEquipmentPicker=!1}}})],1)],1),e._v(" "),i("div",{staticClass:"line"}),e._v(" "),i("div",{staticClass:"customer-order"},[i("div",{staticClass:"order-header"},e._l(["已消耗","未消耗"],function(t,s){return i("div",{key:s,class:e.activeNav===s?"active_nav":"",on:{click:function(t){e.toggleActiveNav(s)}}},[e._v(e._s(t))])}),0),e._v(" "),i("div",{staticClass:"order-list"},e._l(e.orderList,function(t,s){return i("div",{key:s,class:"list-items "+(e.activeNav?t.selected?"selected":"no_selected":""),on:{click:function(t){e.selectedView(s)}}},[i("div",{staticClass:"items-left"},[i("div",{staticClass:"items-name"},[e._v(e._s(t.orderName))]),e._v(" "),i("div",{staticClass:"items-money"},[e._v("套餐金额："+e._s(t.price)+"元")]),e._v(" "),i("div",{staticClass:"items-type"},[e._v(e._s("0"==t.orderType?"固定模式":"自定义模式"))])]),e._v(" "),i("div",{staticClass:"items-right"},[i("div",{staticClass:"items-frequency"},[e._v("次数："+e._s(t.times)+"/"+e._s(t.totalTimes))]),e._v(" "),i("div",{staticClass:"items-date"},[e._v(e._s(t.createTime))]),e._v(" "),i("div",{staticStyle:{float:"right"}},[0!=t.parentId?i("van-tag",{attrs:{type:"danger"}},[e._v("送")]):e._e()],1)])])}),0)]),e._v(" "),i("div",{staticClass:"bottom-btn"},[i("div",{staticClass:"bottom-btn-view"},[i("van-button",{attrs:{color:"linear-gradient(to right, #6F6F6F , #414141)",round:"",block:""},on:{click:function(t){e.operationEquipment(e.el,1)}}},[e._v("我要美力")])],1)])])},staticRenderFns:[]};var o=i("VU/8")(a,r,!1,function(e){i("Ra/R")},null,null);t.default=o.exports},"Ra/R":function(e,t){}});
//# sourceMappingURL=6.34250ad9c749b355ea6e.js.map