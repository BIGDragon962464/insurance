<template>
    <div>
        <div style="width: 1200px; height: 300px;margin-top: 20px">

        </div>
        <div style="margin-left: 220px;margin-top: 20px">
            <el-row :gutter="5">
                <el-col :span="24" v-for="item in tableData" :key="item.id" style="margin-bottom: 20px">
                    <el-card style="width: 800px; height: 300px">
                        <div style="float: left; width: 260px;height: 260px"><img :src="item.img" alt="" style="width: 100%;"></div>
                        <div style="float: left;margin-left: 130px;margin-top: 65px" >
                            <span style="font-size: 25px">{{ item.name }}</span>
                            <div style="font-size: 20px; color: red;margin-top: 20px">￥ {{item.price}}</div>
                            <div style="margin-top: 20px"><el-button size="medium" type="primary" @click="buyInsurance(item.id)">购买</el-button></div>
                            <div style="margin-top: 20px"><el-button size="medium" type="primary" @click="">查看详情</el-button></div>
                        </div>
                    </el-card>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
import insurance from "@/views/Insurance.vue";

export default {
    name: "Insurance",
    data() {
        return {
            types: '',
            price: 0,
            multipleSelection: [],
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            insurance: [],
            tableData: [],
            total: 0,
            pageNum: 1,
            pageSize: 10,
            name: "",
        }
    },
    created() {
        this.load()
    },
    methods: {
        load() {
            this.request.get("/insurance/page1", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    name: this.name,
                    price: this.price,
                }
            }).then(res => {
                this.tableData = res.data.records
                this.total = res.data.total

            })
        },
        buyInsurance(insuranceId){
            this.request.post('/orders/' + insuranceId ).then(res => {
                if (res.code === '200') {
                    this.$message.success("已加入订单，请前往支付")
                }else {
                    this.$message.error(res.msg)
                }
            })
        },
        reset(){
            this.name = ""
            this.types = ""
            this.price = ""
            this.load()
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

<style scoped>
</style>
