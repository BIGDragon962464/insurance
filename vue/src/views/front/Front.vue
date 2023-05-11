<template>
  <div>
      <el-container style="min-height: 100%">
          <el-header>
              <!--  头部  -->
              <div style="display: flex; height: 61px; line-height: 60px; border-bottom: 1px solid rgba(238,238,238,0)">
                  <div class="pointer" style="width: 200px; display: flex; padding-left: 30px" @click="upload">
                      <div style="width: 40px; margin-top: 7px">
                          <img  src="../../assets/logo.png" alt="" style="width: 30px; position: relative; top: 5px; right: 0">
                      </div>
                      <div style="flex: 1; font-size: 18px; font-family: 幼圆,serif; font-weight: bolder">龙保险</div>
                  </div>
                  <div style="flex: 1">
                      <el-menu :default-active="'1'" class="el-menu-demo" mode="horizontal" router>
                          <el-menu-item index="/front/home">首页</el-menu-item>
                          <el-menu-item index="/front/insurance">产品信息</el-menu-item>
                          <el-submenu index="/front/insurance">
                              <template slot="title">产品分类</template>
                              <el-menu-item index="/front/renshen">人身意外险</el-menu-item>
                              <el-menu-item index="/front/car">机动车辆险</el-menu-item>
                              <el-menu-item index="/front/yiliao">医疗养老险</el-menu-item>
                              <el-menu-item index="/front/zeren">工伤责任险</el-menu-item>
                              <el-menu-item index="/front/caichan">财产保障险</el-menu-item>
                          </el-submenu>
                          <el-menu-item index="/front/order">订单管理</el-menu-item>
                          <el-menu-item index="/front/claims">申请理赔</el-menu-item>
                          <el-menu-item index="/front/about">关于我们</el-menu-item>
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
                              <el-dropdown-menu slot="dropdown" style="width: 100px; text-align: center">
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
          </el-header>

          <!--     主体     -->
          <el-main>
              <div style="width: 1200px; height: 100vh; margin: 0 auto">
                  <router-view />
              </div>
          </el-main>

          <!--     底部     -->
          <el-footer style="background-color: #b20404; width: 100%; height: 130px">
              <div style="text-align: center; color: white; font-size: 18px; width: 100% ; height: 100px; margin-top: 30px">
                  <a>基于Spring Boot的保险业务管理系统设计与实现</a>
                  <br>
                  <a>制作人：曹子龙</a>
                  <br>
                  <a>技术栈：</a>
                  <a class="cancelUnder" href="https://www.runoob.com/java/java-tutorial.html" target="_blank">Java</a>、
                  <a class="cancelUnder" href="https://www.runoob.com/w3cnote/basic-knowledge-summary-of-spring.html" target="_blank">SpringBoot</a>、
                  <a class="cancelUnder" href="https://www.runoob.com/vue3/vue3-tutorial.html" target="_blank">Vue</a>、
                  <a class="cancelUnder" href="https://www.runoob.com/redis/redis-tutorial.html" target="_blank">Redis</a>
              </div>
          </el-footer>
      </el-container>


    <div>

    </div>
  </div>
</template>

<script>
import router, {resetRouter, setRoutes} from "@/router";

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
      location.reload()
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