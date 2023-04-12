<template>
  <div>
    <div style="margin: 10px 0">
      <el-carousel height="300px" :interval="5000">
        <el-carousel-item v-for="item in imgs" :key="item">
          <img :src="item" alt="" style="width: 100%">
        </el-carousel-item>
      </el-carousel>
    </div>

    <div style="margin: 10px 0">
      <el-row :gutter="5">
        <el-col :span="6" v-for="item in files" :key="item.id" style="margin-bottom: 20px">
          <el-card>
          <img :src="item.url" alt="" style="width: 100%;">
          <div style="padding: 10px 0;" ><span>{{ item.name }}</span></div>
          <div style="padding-top: 10px"><el-button type="primary">购买</el-button></div></el-card>
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
      imgs: [
          'https://tse3-mm.cn.bing.net/th/id/OIP-C.ktbvDl6mV2lBIaqAxZ1-UQHaEr?w=284&h=180&c=7&r=0&o=5&dpr=1.4&pid=1.7',
          'https://tse3-mm.cn.bing.net/th/id/OIP-C.e8WdxKkSLsxHeRA-iE6PFAHaDw?pid=ImgDet&rs=1',

      ],
      files: []
    }
  },
  /*created() {
    this.request.get("/insurance/front/all").then(res => {
      console.log(res.data)
      this.files = res.data.filter(v => v.type ==='png' || v.type === 'webp' || v.type === 'jpg')
    })
  },*/
  methods: {}
}
</script>

<style>

</style>