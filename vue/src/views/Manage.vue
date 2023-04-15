<template>
  <div style="height: 100%">
    <el-container style="height: 100%;">
        <el-aside :width="sideWidth + 'px'" style="background-color: rgb(238, 241, 246); height:100vh">
          <Aside :is-collapse="isCollapse" :logoTextShow="logoTextShow"/>
        </el-aside>
      <el-container>
        <el-header style="border-bottom: 1px solid #ccc;">
            <div style="float: left; margin-top: 20px">
                <span :class="collapseBtnClass" style="font-size: 20px; cursor: pointer" @click="collapse"></span>
            </div>
            <div style="">
                <Header :user = "user"/>
            </div>
        </el-header>

        <el-main>
<!--          当前页面的子路由会在 router-view 里面展示-->
          <router-view @refreshUser="getUser"/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>

import Aside from "@/components/Aside.vue";
import Header from "@/components/Header.vue";

export default {
  name:"home",
  data() {
    return {

      collapseBtnClass:'el-icon-s-fold',
      isCollapse:false,
      sideWidth:200,
      logoTextShow: true,
      user: {},
    }
  },

  components: {
    Aside,
    Header
  },
  created() {
    //从后台获取user数据
    this.getUser()
  },

  methods:{
    collapse(){   //点击收缩按钮触发
      this.isCollapse = !this.isCollapse
      if (this.isCollapse){
        this.sideWidth = 64
        this.collapseBtnClass = 'el-icon-s-unfold'
      }else {   //展开
        this.sideWidth = 180
        this.collapseBtnClass = 'el-icon-s-fold'
        this.logoTextShow = true
      }
    },
    getUser() {
      let username = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).username : ""
        //后台获取user数据
        this. request.get("/user/username/" + username).then(res =>{
          //重新赋值后台的最新user数据
          this.user = res.data
      })
    },
  }
}
</script>