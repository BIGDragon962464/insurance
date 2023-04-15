<template>
  <el-menu :default-openeds="['1', '3']" style="min-height: 100vh; overflow-x: hidden"
           background-color="#409EFF"
           active-text-color="#606266"
           text-color="#fff"
           :collapse-transition="false"
           :collapse="isCollapse"
           router
  >
    <div style="height: 60px; line-height: 60px; text-align: center">
      <img src="../assets/logo1.png" alt="" style="width: 20px ;position: relative;top: 5px;margin-right: 5px;">
      <b style="color: white" v-show="logoTextShow"></b>
    </div>
    <div v-for="item in menus" :key="item.id">
      <div v-if="item.path">
        <el-menu-item :index="item.path">
          <i :class="item.icon" style="color: white; margin-right: 18px"></i>
          <span slot="title">{{ item.name }}</span>
        </el-menu-item>
      </div>
      <div v-else>
        <el-submenu :index="item.id+''">
          <template slot="title">
            <i :class="item.icon" style="color: white; margin-right: 18px"></i>
            <span slot="title">{{ item.name }}</span>
          </template>
          <div v-for="subItem in item.children" :key="subItem.id">
            <el-menu-item :index="subItem.path">
              <i :class="subItem.icon" style="color: white; margin-right: 18px"></i>
              <span slot="title">{{ subItem.name }}</span>
            </el-menu-item>
          </div>
        </el-submenu>
      </div>
    </div>
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props: {
    isCollapse: Boolean,
    logoTextShow: Boolean,
  },
  data(){
    return{
      menus: localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")) : []
    }
  },
}
</script>

<style scoped>

</style>