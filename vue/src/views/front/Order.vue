<template>
    <div>
        <div style="margin-top: 20px; margin-bottom: 20px">
            已选购
        </div>
      <el-table :data="findInsurance" stripe>
          <el-table-column prop="name" label="保险名称"></el-table-column>
          <el-table-column prop="price" label="保险价格"></el-table-column>
          <el-table-column prop="types" label="保险类型"></el-table-column>
          <el-table-column prop="description" label="保险描述"></el-table-column>
          <el-table-column label="操作" width="300" align="center">
              <template v-slot="scope">
                  <el-popconfirm
                          class="ml-5"
                          confirm-button-text='确定'
                          cancel-button-text ='我再想想'
                          icon="el-icon-info"
                          icon-color="red"
                          title="您确定删除吗？"
                          @confirm="del(scope.row.id)"
                  >
                      <el-button type="danger" slot="reference"><i class="el-icon-remove-outline"></i> 删除</el-button>
                  </el-popconfirm>
              </template>
          </el-table-column>
      </el-table>
    </div>
</template>

<script>
import insurance from "@/views/Insurance.vue";

export default {
    name: "Order",
    data() {
        return {
            findInsurance:[],
            form:{},
            name: '',
            types: '',
            price: null,
            description: '',
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            insurance: [],
            vis: false,
        }
    },
    created() {
        this.load()
    },
    methods: {
        load (){
            this.request.get('insurance/findInsurance/'  + this.user.id ).then(r => {
                this.findInsurance = r.data
                console.log(r.data)
                if (r.code === '200'){
                    this.vis = true
                }else {
                    this.$message.error("错误")
                }
            })
        },
        del(insuranceId){
            this.request.delete('insurance/deleteOrder/' + this.user.id + "/" + insuranceId).then(res => {
                console.log(res.data)
                if (res.code === '200'){
                    this.$message.success("删除成功！")
                    this.load()
                }else {
                    this.$message.error("删除失败")
                }
            })
        },
    }
}
</script>

<style scoped>
.avatar-uploader .el-upload:hover {
    border-color: #409EFF;
}
</style>
