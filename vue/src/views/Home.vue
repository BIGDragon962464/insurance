<template>
  <div>
      <el-row>
          <el-row style="margin-top:10px; margin-left: 50px">
              <el-col :span="24">
                  <el-card style="width: 1175px;height: 80px; text-align: center; margin-bottom: 50px;">
                      <div style="margin-top: 5px">
                          <h2>基于Spring Boot的保险业务管理系统设计与实现</h2>
                      </div>
                  </el-card>
              </el-col>
              <el-col :span="12">
                  <el-card style="width: 550px;height: 200px; text-align: center">
                      <div style="margin-top: 50px;">
                          <a style="font-size: 30px">制作人：曹子龙</a>
                      </div>
                  </el-card>
              </el-col>
              <el-col :span="12">
                  <el-card style="width: 550px;height: 200px; text-align: center">
                      <div style="margin-top: 5px">
                          <h2 style="margin-bottom: 5px">技术栈</h2>
                          <h1 style="font-weight:normal;">Java</h1>
                          <h1 style="font-weight:normal;">SpringBoot</h1>
                          <h1 style="font-weight:normal;">Vue</h1>
                          <h1 style="font-weight:normal;">Redis</h1>
                      </div>
                  </el-card>
              </el-col>
          </el-row>
      </el-row>
  </div>
</template>
<script>
import * as echarts from 'echarts'

export default {
  name: "Home",
  data(){
    return{

    }
  },
}
</script>

<style>

</style>