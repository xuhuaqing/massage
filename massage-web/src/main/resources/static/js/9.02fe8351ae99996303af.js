webpackJsonp([9],{I4hK:function(e,t,i){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var s=i("Xxa5"),r=i.n(s),o=i("exGp"),a=i.n(o),c=i("RtM3"),n=i("Fd2+"),l={data:function(){return{teacherId:"",business:"",businessId:"",businessList:[],businessData:[],equipmentList:[],equipmentListData:[],showPicker1:!1,showPicker2:!1,projectList:{name:"",phone:"",projectName:"",projectNum:"",projectTime:"",projectMoney:"",remarks:"",give:!1,giveOrderName:"",giveTotalTimes:"",giveEveryTime:"",giveProjectTime:""},experience:!1,isBtnStatus:null}},created:function(){document.title="老师自定义模式",this.teacherId=JSON.parse(localStorage.getItem("userInfo")).userPhone,this.business=JSON.parse(localStorage.getItem("userInfo")).businessId,this.openEquipmentList()},methods:{quit:function(){localStorage.removeItem("userInfo"),this.$router.replace("/")},openEquipmentList:function(){var e=this.business;this.getEquipmentList({businessId:e,type:1})},getEquipmentList:function(e){var t=this;Object(c.f)(e).then(function(e){"000000"==e.code&&(t.equipmentListData=e.data.length>0?e.data:[],t.equipmentList=e.data.length>0?e.data.map(function(e){return e.equipmentId}):[],t.teacherPickerLoading=!1)})},businessToggle:function(e){var t="";this.businessData.map(function(i,s){i.businessName===e&&(t=i.businessId)}),this.business=e,this.businessId=t,this.showPicker1=!1},equipmentToggle:function(e){var t=this;return a()(r.a.mark(function i(){var s;return r.a.wrap(function(i){for(;;)switch(i.prev=i.next){case 0:return n.c.loading({duration:0,forbidClick:!0,message:"操作中"}),t.equipmentId=e,i.next=4,Object(c.s)({equipmentId:e,type:t.isBtnStatus});case 4:s=i.sent,n.c.clear(),"000000"===s.code?n.c.success("操作成功"):n.c.fail(s.msg),t.showPicker2=!1;case 8:case"end":return i.stop()}},i,t)}))()},addOrder:function(){var e=this,t={};if(this.experience){if(!this.projectList.name)return void n.c.fail("请输入用户姓名");if(!this.projectList.phone)return void n.c.fail("请输入用户手机号");t={userName:this.projectList.name,userPhone:this.projectList.phone,experience:1,teacherType:JSON.parse(localStorage.getItem("userInfo")).teacherType,equipmentId:this.equipmentId,teacherId:this.teacherId,orderType:1,businessId:this.business,give:0}}else{if(!this.projectList.name)return void n.c.fail("请输入用户姓名");if(!this.projectList.phone)return void n.c.fail("请输入用户手机号");if(!this.projectList.projectName)return void n.c.fail("请输入项目名称");if(!this.projectList.projectNum)return void n.c.fail("请输入项目次数");if(!this.projectList.projectTime)return void n.c.fail("请输入项目时长");if(!this.projectList.projectMoney)return void n.c.fail("请输入项目金额");(t={orderName:this.projectList.projectName,orderType:1,price:this.projectList.projectMoney,totalTimes:this.projectList.projectNum,businessId:this.business,userName:this.projectList.name,userPhone:this.projectList.phone,everyTime:this.projectList.projectTime,projectTime:this.projectList.projectNum*this.projectList.projectTime,equipmentId:this.equipmentId,teacherId:this.teacherId,remarks:this.projectList.remarks,teacherType:JSON.parse(localStorage.getItem("userInfo")).teacherType}).give=this.projectList.give?1:0}if(this.projectList.give){if(!this.projectList.giveOrderName)return void n.c.fail("请输入项目次数");if(!this.projectList.giveTotalTimes)return void n.c.fail("请输入项目次数");if(!this.projectList.giveEveryTime)return void n.c.fail("请输入项目时长");t.giveOrderName=this.projectList.giveOrderName,t.giveTotalTimes=+this.projectList.giveTotalTimes,t.giveEveryTime=+this.projectList.giveEveryTime,t.giveProjectTime=this.projectList.giveTotalTimes*this.projectList.giveEveryTime}JSON.parse(localStorage.getItem("userInfo"));Object(c.r)(t).then(function(t){"000000"==t.code?(n.c.success("购买成功！"),e.projectList={name:"",phone:"",projectName:"",projectNum:"",projectTime:"",projectMoney:"",projectList:""}):n.c.fail(t.msg)})}}},p={render:function(){var e=this,t=e.$createElement,s=e._self._c||t;return s("div",{staticClass:"teacher"},[s("div",{staticClass:"add-nav"},[s("div",{staticClass:"add-order"},[s("van-button",{attrs:{plain:"",hairline:"",type:"primary",round:"",block:""},on:{click:function(t){e.$router.push("allOrder")}}},[e._v("所有订单")])],1),e._v(" "),s("div",{staticClass:"add-btn"},[s("van-button",{attrs:{plain:"",hairline:"",type:"primary",round:"",block:""},on:{click:function(t){e.showPicker2=!0,e.isBtnStatus=0}}},[e._v("到店")]),e._v(" "),s("van-button",{attrs:{plain:"",hairline:"",type:"primary",round:"",block:""},on:{click:function(t){e.showPicker2=!0,e.isBtnStatus=1}}},[e._v("离店")]),e._v(" "),s("van-popup",{attrs:{position:"bottom"},model:{value:e.showPicker2,callback:function(t){e.showPicker2=t},expression:"showPicker2"}},[s("van-picker",{attrs:{"show-toolbar":"",columns:e.equipmentList},on:{confirm:e.equipmentToggle,cancel:function(t){e.showPicker2=!1}}})],1)],1),e._v(" "),s("div",{staticClass:"add-quit"},[s("img",{attrs:{src:i("6hUe"),alt:""},on:{click:function(t){e.quit()}}})])]),e._v(" "),s("div",{staticClass:"add-equipment"},[s("div",{staticClass:"add_equipment-form"},[s("div",{staticClass:"form-items"},[s("div",{staticClass:"form-title"},[e._v("老师ID")]),e._v(" "),s("van-cell-group",[s("van-field",{attrs:{disabled:!0,placeholder:"请输入老师ID"},model:{value:e.teacherId,callback:function(t){e.teacherId=t},expression:"teacherId"}})],1)],1)])]),e._v(" "),s("div",{staticClass:"project-message"},[s("div",{staticClass:"form-items"},[s("div",{staticClass:"form-title"},[e._v("体 验 订 单")]),e._v(" "),s("van-cell-group",[s("van-checkbox",{model:{value:e.experience,callback:function(t){e.experience=t},expression:"experience"}},[e._v("是否为体验订单")])],1)],1),e._v(" "),s("div",{staticClass:"form-items"},[s("div",{staticClass:"form-title"},[e._v("姓        名")]),e._v(" "),s("van-cell-group",[s("van-field",{attrs:{placeholder:"请输入姓名"},model:{value:e.projectList.name,callback:function(t){e.$set(e.projectList,"name",t)},expression:"projectList.name"}})],1)],1),e._v(" "),s("div",{staticClass:"form-items"},[s("div",{staticClass:"form-title"},[e._v("手 机 尾 号")]),e._v(" "),s("van-cell-group",[s("van-field",{attrs:{type:"tel",maxlength:"4",placeholder:"请输入手机尾号后4位"},model:{value:e.projectList.phone,callback:function(t){e.$set(e.projectList,"phone",t)},expression:"projectList.phone"}})],1)],1),e._v(" "),e.experience?e._e():[s("div",{staticClass:"form-items"},[s("div",{staticClass:"form-title"},[e._v("项 目 名 称")]),e._v(" "),s("van-cell-group",[s("van-field",{attrs:{placeholder:"请输入项目名称"},model:{value:e.projectList.projectName,callback:function(t){e.$set(e.projectList,"projectName",t)},expression:"projectList.projectName"}})],1)],1),e._v(" "),s("div",{staticClass:"form-items"},[s("div",{staticClass:"form-title"},[e._v("项 目 次 数")]),e._v(" "),s("van-cell-group",[s("van-field",{attrs:{type:"digit",placeholder:"请输入项目次数"},model:{value:e.projectList.projectNum,callback:function(t){e.$set(e.projectList,"projectNum",t)},expression:"projectList.projectNum"}})],1)],1),e._v(" "),s("div",{staticClass:"form-items"},[s("div",{staticClass:"form-title"},[e._v("项 目 时 长")]),e._v(" "),s("van-cell-group",[s("van-field",{attrs:{type:"digit",placeholder:"请输入项目时长"},model:{value:e.projectList.projectTime,callback:function(t){e.$set(e.projectList,"projectTime",t)},expression:"projectList.projectTime"}})],1)],1),e._v(" "),s("div",{staticClass:"form-items"},[s("div",{staticClass:"form-title"},[e._v("项 目 金 额")]),e._v(" "),s("van-cell-group",[s("van-field",{attrs:{type:"number",placeholder:"请输入项目金额"},model:{value:e.projectList.projectMoney,callback:function(t){e.$set(e.projectList,"projectMoney",t)},expression:"projectList.projectMoney"}})],1)],1),e._v(" "),s("div",{staticClass:"form-textarea"},[s("div",{staticClass:"form-title"},[e._v("备        注")]),e._v(" "),s("van-cell-group",[s("van-field",{attrs:{rows:"3",autosize:"",type:"textarea",maxlength:"100",placeholder:"请输入备注"},model:{value:e.projectList.remarks,callback:function(t){e.$set(e.projectList,"remarks",t)},expression:"projectList.remarks"}})],1)],1),e._v(" "),s("div",{staticClass:"form-items"},[s("div",{staticClass:"form-title"},[e._v("项 目 优 惠")]),e._v(" "),s("van-cell-group",[s("van-checkbox",{model:{value:e.projectList.give,callback:function(t){e.$set(e.projectList,"give",t)},expression:"projectList.give"}},[e._v("送")])],1)],1),e._v(" "),s("div",{directives:[{name:"show",rawName:"v-show",value:e.projectList.give,expression:"projectList.give"}]},[s("div",{staticClass:"form-items"},[s("div",{staticClass:"form-title"},[e._v("项 目 名 称")]),e._v(" "),s("van-cell-group",[s("van-field",{attrs:{placeholder:"请输入项目名称"},model:{value:e.projectList.giveOrderName,callback:function(t){e.$set(e.projectList,"giveOrderName",t)},expression:"projectList.giveOrderName"}})],1)],1),e._v(" "),s("div",{staticClass:"form-items"},[s("div",{staticClass:"form-title"},[e._v("项 目 次 数")]),e._v(" "),s("van-cell-group",[s("van-field",{attrs:{type:"digit",placeholder:"请输入项目次数"},model:{value:e.projectList.giveTotalTimes,callback:function(t){e.$set(e.projectList,"giveTotalTimes",t)},expression:"projectList.giveTotalTimes"}})],1)],1),e._v(" "),s("div",{staticClass:"form-items"},[s("div",{staticClass:"form-title"},[e._v("项 目 时 长")]),e._v(" "),s("van-cell-group",[s("van-field",{attrs:{type:"digit",placeholder:"请输入项目时长"},model:{value:e.projectList.giveEveryTime,callback:function(t){e.$set(e.projectList,"giveEveryTime",t)},expression:"projectList.giveEveryTime"}})],1)],1)]),e._v(" "),s("div",{staticClass:"form-date"},[e._v("\n        总时长："+e._s(e.projectList.projectNum*e.projectList.projectTime)+"分钟\n      ")])]],2),e._v(" "),s("div",{staticClass:"custom-btn"},[s("Debounce",{attrs:{time:500,isDebounce:""}},[s("van-button",{attrs:{color:"linear-gradient(to right, #6F6F6F , #414141)",round:"",block:""},on:{click:e.addOrder}},[e._v("确定")])],1)],1)])},staticRenderFns:[]};var v=i("VU/8")(l,p,!1,function(e){i("W6/B")},null,null);t.default=v.exports},"W6/B":function(e,t){}});
//# sourceMappingURL=9.02fe8351ae99996303af.js.map