<template>
    <div>
        <div style="margin: 10px 0">
            <el-input class="ml-5" style="width: 200px"  placeholder="请输入保险名称" suffix-icon="el-icon-info" v-model="name"></el-input>
            <el-input class="ml-5" style="width: 200px"  placeholder="购险人" suffix-icon="el-icon-position" v-model="username"></el-input>
            <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
            <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
        </div>
        <el-table :data="tableData" stripe :header-cell-class-name="'headerBg'">
            <el-table-column prop="no" label="订单号" width="200"></el-table-column>
            <el-table-column prop="username" label="购险人" width="100"></el-table-column>
            <el-table-column prop="name" label="名称"></el-table-column>
            <el-table-column prop="time" label="下单时间"></el-table-column>
            <el-table-column prop="state" label="支付状态" width="100"></el-table-column>
            <el-table-column prop="total" label="订单总价" ></el-table-column>
            <el-table-column prop="paymentTime" label="支付时间" ></el-table-column>
            <el-table-column prop="alipayNo" label="支付宝流水号" ></el-table-column>
            <el-table-column prop="returnTime" label="退款时间"></el-table-column>
            <el-table-column label="操作"  width="300" align="center">
                <template slot-scope="scope">
                    <el-button type="primary" @click="payInsurance(scope.row)" :disabled="scope.row.state !== '待支付'"><i class="el-icon-edit"></i>支 付</el-button>
                    <el-button type="danger" @click="returnPay(scope.row)" :disabled="scope.row.state !== '已支付'">退款</el-button>
                    <el-popconfirm
                        class="ml-5"
                        confirm-button-text='确定'
                        cancel-button-text='我再想想'
                        icon="el-icon-info"
                        icon-color="red"
                        title="您确定删除吗？"
                        @confirm="del(scope.row.id)"
                    >
                        <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
        <div style="padding: 10px 0">
            <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="pageNum"
                :page-sizes="[2, 5, 8]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
        </div>
    </div>
</template>


<script>
import insurance from "@/views/Insurance.vue";
import {serverIp} from "../../public/config";

export default {
    name: "Orders",
    data() {
        return {
            username: [],
            tableData: [],
            name: '',
            types: '',
            price: null,
            multipleSelection: [],
            pageNum: 1,
            pageSize: 5,
            total: 0,
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            insurance: [],
        }
    },
    created() {
        this.load()
    },
    methods: {
        payInsurance(row){
            //得到一个url ，就是支付宝支付界面的url
            window.open(`http://${serverIp}:8088/alipay/pay?subject=${row.name}&traceNo=${row.no}&totalAmount=${row.total}`);
        },
        returnPay(row) {
            const url = `http://${serverIp}:8088/alipay/return?totalAmount=${row.total}&alipayTraceNo=${row.alipayNo}&traceNo=${row.no}`
            this.request.get(url).then(res => {
                if(res.code === '200') {
                    this.$message.success("退款成功")
                }  else {
                    this.$message.error("购买已过三天，退款失败")
                }
                this.load()
            })
        },
        load() {
            this.request.get("/orders/page", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    username: this.username,
                    name: this.name
                }
            }).then(res => {
                this.tableData = res.data.records
                this.total = res.data.total

            })
        },

        reset(){
            this.name = ""
            this.username = ""
            this.load()
        },
        del(id) {
            this.request.delete("/orders/" + id).then(res => {
                if (res.code === '200') {
                    this.$message.success("删除成功")
                    this.load()
                } else {
                    this.$message.error("删除失败")
                }
            })
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

<style >
.dialog{
    border-radius: 10px
}
</style>


<!--
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
-->
