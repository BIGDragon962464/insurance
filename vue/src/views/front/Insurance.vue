<template>
    <div>
<!--        <div style="width: 1200px; margin-bottom: 40px">
            <el-carousel height="220px" :interval="5000" > &lt;!&ndash;加 type="card" 会变成三图叠加两层的样式 &ndash;&gt;
                <el-carousel-item v-for="item in imgs1" :key="item">
                    <img :src="item" alt="" style="width: 100%">
                </el-carousel-item>
            </el-carousel>
        </div>-->
        <div style="margin-left: 120px; margin-top: 20px;">
            <el-card style="border-radius: 15px;height: 120px; width: 1000px;">
                    <div style="margin-top: 22px; margin-left: 30px; font-size: 22px; float:left;">按险种：</div>
                    <el-button class="typesButton" type="error" :plain="true" @click="load">全部</el-button>
                    <el-button class="typesButton" type="error" :plain="true" @click="load1">人身意外险</el-button>
                    <el-button class="typesButton" type="error" :plain="true" @click="load2">机动车辆险</el-button>
                    <el-button class="typesButton" type="error" :plain="true" @click="load3">医疗养老险</el-button>
                    <el-button class="typesButton" type="error" :plain="true" @click="load4">工伤责任险</el-button>
                    <el-button class="typesButton" type="error" :plain="true" @click="load5">财产保障险</el-button>
            </el-card>
        </div>
        <div style="margin-left: 120px;margin-top: 20px">
            <el-row :gutter="5">
                <el-col :span="24" v-for="item in tableData" :key="item.id" style="margin-bottom: 20px">
                    <el-card class="card">
                        <div style="float: left; width: 260px;height: 260px">
                            <img :src="item.img" alt="" style="width: 100%;">
                        </div>
                        <div style="float: left;margin-left: 250px;margin-top: 50px" >
                            <span style="font-size: 25px">{{ item.name }}</span>
                            <div style="font-size: 30px; color: red;margin-top: 20px">￥ {{item.price}}</div>
                            <div style="margin-top: 20px"><el-button style="border-radius: 25px; height: 50px;width: 100px;font-size: 20px" type="primary" @click="buyInsurance(item.id)">购买</el-button></div>
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
                    types: this.types,
                }
            }).then(res => {
                this.tableData = res.data.records
                this.total = res.data.total
                console.log(this.user)
            })
        },
        load1() {
            this.request.get("/insurance/page1", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    name: this.name,
                    price: this.price,
                    types: '人身意外险',
                }
            }).then(res => {
                this.tableData = res.data.records
                this.total = res.data.total
            })
        },
        load2() {
            this.request.get("/insurance/page1", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    name: this.name,
                    price: this.price,
                    types: '机动车辆险',
                }
            }).then(res => {
                this.tableData = res.data.records
                this.total = res.data.total

            })
        },
        load3() {
            this.request.get("/insurance/page1", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    name: this.name,
                    price: this.price,
                    types: '医疗养老险',
                }
            }).then(res => {
                this.tableData = res.data.records
                this.total = res.data.total

            })
        },
        load4() {
            this.request.get("/insurance/page1", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    name: this.name,
                    price: this.price,
                    types: '工伤责任险',
                }
            }).then(res => {
                this.tableData = res.data.records
                this.total = res.data.total

            })
        },
        load5() {
            this.request.get("/insurance/page1", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    name: this.name,
                    price: this.price,
                    types: '财产保障险',
                }
            }).then(res => {
                this.tableData = res.data.records
                this.total = res.data.total

            })
        },
        buyInsurance(insuranceId){
            if (this.user.trueName === null || this.user.trueId === null || this.user.trueName === "" || this.user.trueId === "") {
                this.$message.error("请先前往实名")
            }else {
                this.request.post('/orders/' + insuranceId).then(res => {
                    if (res.code === '200') {
                        this.$message.success("已加入订单，请前往支付")
                    } else {
                        this.$message.error(res.msg)
                    }
                })
            }
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
.card{
    width: 1000px;
    height: 300px;
    border-radius: 15px
}
.card:hover {
    box-shadow: 0 5px 16px 0 rgba(0,0,0,0.24),0 17px 50px 0 rgba(0,0,0,0.19);
}
</style>
