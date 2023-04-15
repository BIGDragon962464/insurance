<template>
  <div>
      <div style="width: 1200px; margin: 0 0 0 0; margin: auto">
        <el-carousel height="200px" :interval="5000">
          <el-carousel-item v-for="item in imgs" :key="item">
            <img :src="item" alt="" style="width: 100%">
          </el-carousel-item>
        </el-carousel>
      </div>
      <div style="margin: 10px 0">
          <el-row>
              <el-col :span="12">
                  <div style="margin: 10px 0; width: 600px;height: 400px">

                  </div>
              </el-col>
              <el-col :span="12">
                  <el-card style="margin: 10px 0; width: 600px; height: 400px">
                      <div style="padding-bottom: 20px; font-size: 20px">系 统 公 告</div>
                      <el-collapse accordion v-model="activeNames" v-for="(item, index) in notice">
                          <el-collapse-item :title="item.name" :name="index + ''">
                              <template slot="title">
                                  <span style="">{{item.time}}</span>
                                  <span style="font-size: 15px; margin-left: 20px">{{item.name}}</span>
                              </template>
  <!--                            <div style="padding: 10px 0; width: 100px;height: 100px"><el-image :src="item.img"></el-image></div>-->
                              <div v-html="item.content"></div>
                          </el-collapse-item>
                      </el-collapse>
                  </el-card>
              </el-col>
          </el-row>
      </div>
  </div>
</template>

<script>
export default {
  name: "FrontHome",
  data(){
    return{
      activeNames: ['-1'],
      imgs: [
          'https://tse3-mm.cn.bing.net/th/id/OIP-C.e8WdxKkSLsxHeRA-iE6PFAHaDw?pid=ImgDet&rs=1',
      ],
      notice: [],
      content: '',
      tableData: [],
    }
  },
  created() {
      this.request.get("/notice").then(res =>{
          this.notice = res.data.splice(0, 5)
      })
  },
  methods: {
  }
}
</script>

<style>

</style>