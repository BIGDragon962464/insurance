<template>
  <div>
    <el-row :gutter="20" style="margin-bottom: 60px">
      <el-col :span="6">
        <el-card style="color: #409EFF">
          <div><i class="el-icon-user-solid" /> 用户总数</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            1000
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #F56C6C">
          <div>销售总量</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            1000 ￥
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #E6A23C">
          <div>用户总数</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            1000
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #67C23A">
          <div>用户总数</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            1000
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>






<!--      <div id="main" style="height: 450px; width: 500px"></div>-->
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