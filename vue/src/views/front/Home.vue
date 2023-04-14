<template>
  <div>
    <div style="margin: 10px 0">
      <el-carousel height="300px" :interval="5000">
        <el-carousel-item v-for="item in imgs" :key="item">
          <img :src="item" alt="" style="width: 100%">
        </el-carousel-item>
      </el-carousel>
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
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      name: "",
    }
  },
  created() {
      this.load() //获取后台商品数据
  },
  methods: {
      load(){
      },
      handleSizeChange(pageSize) {
          console.log(pageSize)
          this.pageSize = pageSize
          this.load()
      },
      handleCurrentChange(pageNum) {
          console.log(pageNum)
          this.pageNum = pageNum
          this.load()
      },
  }
}
</script>

<style>

</style>