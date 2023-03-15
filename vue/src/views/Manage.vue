<template>
  <div style="height: 100%">
    <el-container style="height: 100%;">
      <el-aside :width="sideWidth + 'px'" style="background-color: rgb(238, 241, 246); height:100vh">
        <Aside :is-collapse="isCollapse" :logoTextShow="logoTextShow"/>
      </el-aside>

      <el-container>
        <el-header style="border-bottom: 1px solid #ccc;">
          <Header :collapseBtnClass="collapseBtnClass" :collapse="collapse"/>
        </el-header>

        <el-main>
<!--          当前页面的子路由会在 router-view 里面展示-->
          <router-view />

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
      logoTextShow: true
    }
  },

  components: {
    Aside,
    Header
  },

  methods:{
    collapse(){   //点击收缩按钮触发
      this.isCollapse = !this.isCollapse
      if (this.isCollapse){
        this.sideWidth = 64
        this.collapseBtnClass = 'el-icon-s-unfold'
      }else {
        this.sideWidth = 200
        this.collapseBtnClass = 'el-icon-s-fold'
      }
    },
  }
}
</script>