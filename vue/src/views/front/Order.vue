<template>
    <div>
        <div style="margin-top: 20px; margin-bottom: 20px">
            已选购
        </div>
      <el-table :data="findInsurance" border stripe>
          <el-table-column prop="name" label="保险名称"></el-table-column>
          <el-table-column prop="price" label="保险价格"></el-table-column>
          <el-table-column prop="types" label="保险类型"></el-table-column>
          <el-table-column prop="description" label="保险描述"></el-table-column>
      </el-table>
      <div style="margin-left: 1000px; margin-top: 20px">
          <el-button>结 算</el-button>
      </div>
    </div>
</template>

<script>
import insurance from "@/views/Insurance.vue";

export default {
    name: "Order",
    data() {
        return {
            tableData: [],
            form:{},
            name: '',
            types: '',
            price: null,
            multipleSelection: [],
            dialogFormVisible: false,
            pageNum: 1,
            pageSize: 5,
            total: 0,
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            insurance: [],
            vis: false,
            findInsurance:[],
        }
    },
    created() {
        this.load()
    },
    methods: {
        load (findInsurance){
            this.request.get('insurance/findInsurance/'  + this.user.id ).then(r => {
                //console.log(this.user.id)
                //console.log(r.data)
                this.findInsurance = r.data
                console.log(findInsurance)
                if (r.code === '200'){
                    this.vis = true
                }else {
                    this.$message.error("错误")
                }
            })
        },
    }
}
</script>

<style scoped>
.avatar-uploader {
    padding-bottom: 5px;
}
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}
.avatar-uploader .el-upload:hover {
    border-color: #409EFF;
}
.avatar-uploader-icon {
    font-size: 30px;
    color: #8c939d;
    width: 70px;
    height: 70px;
    line-height: 80px;
    text-align: center;
}
.img {
    width: 70px;
    height: 70px;
    display: block;
}
</style>
