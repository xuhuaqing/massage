webpackJsonp([8],{"3BoL":function(e,t,s){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var a=s("RtM3"),r=s("Fd2+"),n={data:function(){return{userPhone:"",teacherList:[],searchName:"",userMessage:{userName:"",phone:"",password:"",type:2,teacherType:1,userId:JSON.parse(localStorage.getItem("userInfo")).userId,id:JSON.parse(localStorage.getItem("userInfo")).provincialId},messageStatus:"",currentPage:1,total:0,pageSize:5,phoneReg:/^1[3456789]\d{9}$/,loading:!1,finished:!1,show:!1,title:""}},created:function(){this.userPhone=JSON.parse(localStorage.getItem("userInfo")).userPhone,this.getTeacherAll()},methods:{quit:function(){localStorage.removeItem("userInfo"),this.$router.replace("/")},getTeacherAll:function(){var e=this;r.c.loading({duration:0,forbidClick:!0,message:"加载中...."});Object(a.h)({page:this.currentPage,pageSize:this.pageSize,type:2,teacherType:JSON.parse(localStorage.getItem("userInfo")).teacherType,userId:JSON.parse(localStorage.getItem("userInfo")).userId}).then(function(t){r.c.clear(),"0"==t.code&&t.data.length>0&&(e.teacherList=t.data,e.total=t.count)})},onSubmit:function(){var e=this;"添加老师信息"===this.title?Object(a.b)(this.userMessage).then(function(t){"0"==t.code?(e.show=!1,e.userMessage={userName:"",phone:"",password:"",type:2,teacherType:1,userId:JSON.parse(localStorage.getItem("userInfo")).userId,id:JSON.parse(localStorage.getItem("userInfo")).provincialId},r.c.success("添加成功"),e.getTeacherAll()):r.c.success(t.msg)}):"修改老师信息"===this.title&&Object(a.t)(this.userMessage).then(function(t){"0"==t.code?(e.show=!1,e.userMessage={userName:"",phone:"",password:"",type:2,teacherType:1,userId:JSON.parse(localStorage.getItem("userInfo")).userId,id:JSON.parse(localStorage.getItem("userInfo")).provincialId},r.c.success("修改成功"),e.getTeacherAll()):r.c.success(t.msg)})},currentPageChange:function(e){this.getTeacherAll()},addTeacher:function(){this.show=!0,this.title="添加老师信息"},deleteTeacher:function(e){var t=this;r.a.confirm({title:"提示",message:"是否要删除该老师"}).then(function(){Object(a.e)({userId:e}).then(function(e){"0"==e.code&&(r.c.success("删除成功"),t.getTeacherAll())})}).catch(function(){})},updateTeacher:function(e){this.userMessage=e,this.show=!0,this.title="修改老师信息"}}},i={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"distribution"},[a("div",{staticClass:"distribution-nav"},[a("div",{staticClass:"distribution-order"},[a("van-button",{attrs:{plain:"",hairline:"",type:"primary",round:"",block:""},on:{click:function(t){e.addTeacher()}}},[e._v("添加老师")])],1),e._v(" "),a("div",{staticClass:"distribution-quit"},[a("img",{attrs:{src:s("6hUe"),alt:""},on:{click:function(t){e.quit()}}})])]),e._v(" "),a("div",{staticClass:"line",staticStyle:{"margin-top":"30px"}}),e._v(" "),e.teacherList.length>0?a("div",{staticClass:"teacher-list"},e._l(e.teacherList,function(t,s){return a("div",{key:s,staticClass:"teacher-items"},[a("div",[a("div",{staticStyle:{"text-align":"left"}},[a("span",{staticClass:"items-title"},[e._v("姓名：")]),e._v(" "+e._s(t.userName)+"\n        ")]),e._v(" "),a("div",{staticStyle:{"text-align":"left"}},[a("span",{staticClass:"items-title"},[e._v("手机号：")]),e._v(" "+e._s(t.phone)+"\n        ")]),e._v(" "),a("div",{staticStyle:{"text-align":"left"}},[a("span",{staticClass:"items-title"},[e._v("密码：")]),e._v(" "+e._s(t.password)+"\n        ")])]),e._v(" "),a("div",[a("van-button",{attrs:{square:"",type:"danger",text:"删除"},on:{click:function(s){e.deleteTeacher(t.userId)}}}),e._v(" "),a("van-button",{attrs:{square:"",type:"primary",text:"修改"},on:{click:function(s){e.updateTeacher(t)}}})],1)])}),0):a("van-empty",{attrs:{description:"暂无数据"}}),e._v(" "),a("van-pagination",{attrs:{"total-items":e.total,"items-per-page":5},on:{change:e.currentPageChange},model:{value:e.currentPage,callback:function(t){e.currentPage=t},expression:"currentPage"}}),e._v(" "),a("van-popup",{attrs:{closeable:""},model:{value:e.show,callback:function(t){e.show=t},expression:"show"}},[a("div",{staticClass:"teacher-message"},[a("div",{staticClass:"teacher-message-title"},[e._v(e._s(e.title))]),e._v(" "),a("div",[a("van-form",{on:{submit:e.onSubmit}},[a("van-field",{attrs:{name:"用户名",label:"用户名",placeholder:"用户名",rules:[{required:!0,message:"请填写用户名"}]},model:{value:e.userMessage.userName,callback:function(t){e.$set(e.userMessage,"userName",t)},expression:"userMessage.userName"}}),e._v(" "),a("van-field",{attrs:{type:"phone",name:"手机号",label:"手机号",placeholder:"手机号",rules:[{pattern:e.phoneReg,message:"老师ID格式错误"}]},model:{value:e.userMessage.phone,callback:function(t){e.$set(e.userMessage,"phone",t)},expression:"userMessage.phone"}}),e._v(" "),a("van-field",{attrs:{type:"password",name:"密码",label:"密码",placeholder:"密码",rules:[{required:!0,message:"请填写密码"}]},model:{value:e.userMessage.password,callback:function(t){e.$set(e.userMessage,"password",t)},expression:"userMessage.password"}}),e._v(" "),a("div",{staticStyle:{margin:"16px"}},[a("van-button",{attrs:{round:"",block:"",type:"info","native-type":"submit"}},[e._v("\n              提交\n            ")])],1)],1)],1)])])],1)},staticRenderFns:[]};var c=s("VU/8")(n,i,!1,function(e){s("xExa")},null,null);t.default=c.exports},xExa:function(e,t){}});
//# sourceMappingURL=8.5092937e0d931f32d180.js.map