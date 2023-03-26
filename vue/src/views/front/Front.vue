<template>
  <div>
<!--  头部  -->
    <div style="display: flex; height: 61px; line-height: 60px; border-bottom: 1px solid rgba(238,238,238,0)">
      <div class="pointer" style="width: 200px; display: flex; padding-left: 30px" @click="upload">
        <div style="width: 60px">
          <img  src="../../assets/logo.png" alt="" style="width: 50px; position: relative; top: 5px; right: 0">
        </div>
        <div style="flex: 1">龙保险</div>
      </div>
      <div style="flex: 1">
<!--    导肮栏    -->
        <el-menu :default-active="'1'" class="el-menu-demo" mode="horizontal" router>
          <el-menu-item index="/front/home">首页</el-menu-item>
          <el-submenu index="2">
            <template slot="title">产品分类</template>
            <el-menu-item index="/front/item1">人身意外险</el-menu-item>
            <el-menu-item index="2-2">机动车辆险</el-menu-item>
            <el-menu-item index="2-3">医疗养老险</el-menu-item>
            <el-menu-item index="2-3">工伤责任险</el-menu-item>
            <el-submenu index="2-4">
              <template slot="title">其他</template>
              <el-menu-item index="2-4-1">选项1</el-menu-item>
              <el-menu-item index="2-4-2">选项2</el-menu-item>
              <el-menu-item index="2-4-3">选项3</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-menu-item index="/front/about">关于我们</el-menu-item>
          <el-menu-item index="/front/order">订单管理</el-menu-item>
          <el-menu-item index="/front/claims">申请理赔</el-menu-item>
          <el-menu-item index="/front/join">加入我们</el-menu-item>
        </el-menu>
      </div>
      <div style="width: 200px">
        <div v-if="!user.username" style="text-align: right; padding-right: 30px">
          <el-button @click="$router.push('/login')">登录</el-button>
          <el-button @click="$router.push('/register')">注册</el-button>
        </div>
        <div v-else>
          <el-dropdown style="width: 150px; cursor:pointer; text-align: right">
            <div style="display: inline-block">
              <img :src="user.avatarUrl" alt=""
                   style="width: 30px; height: 30px;border-radius: 50%;position: relative;top: 10px;right: 5px">
              <span>{{user.nickname}}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
            </div>
            <el-dropdown-menu slot="dropdown"style="width: 100px; text-align: center">
              <el-dropdown-item style="font-size: 14px;padding: 5px 0">
                <router-link to="/front/person" style="text-decoration:none">个人信息</router-link>
              </el-dropdown-item>
              <el-dropdown-item style="font-size: 14px;padding: 5px 0">
                <span style="text-decoration: none" @click="logout">退出</span>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </div>
    </div>
    <div style="width: 1200px; margin: 0 auto">
      <router-view />
    </div>
  </div>
</template>

<script>
export default {
  name: "Front",
  data(){
    return{
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
    }
  },
  created() {
  },
  methods: {
    logout(){
      this.$store.commit("logout")
      this.$message.success("退出成功！")
      this.$router.push("/front/home")
    },
    upload(){
      this.$router.push("/front/home")
    },
  }
}
</script>

<style>
.pointer{
  cursor: pointer;
}
</style>