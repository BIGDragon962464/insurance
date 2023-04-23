<template>
    <div>
        <br><br>
<!--        <div style="margin: 10px 0">
            <el-popconfirm
                class="ml-5"
                confirm-button-text='确定'
                cancel-button-text='我再想想'
                icon="el-icon-info"
                icon-color="red"
                title="您确定批量删除这些数据吗？"
                @confirm="delBatch"
            >
                <el-button type="danger" slot="reference" >批量删除 <i class="el-icon-remove-outline"></i></el-button>
            </el-popconfirm>
        </div>-->
        <div style="margin-top: 20px">
            <el-row :gutter="5">
                <el-col :span="24" v-for="item in tableData" :key="item.id" style="margin-bottom: 20px">
                    <el-card class="card">
                        <div style="font-size: 18px;color: #ec5a00; float: right;">{{item.state}}</div>
                        <div style="float: left; width: 260px;height: 260px">
                            <img :src="item.img" alt="" style="width: 95%;">
                        </div>
                        <div style="float: left; margin-left: 200px;margin-top: 50px" >
                            <span style="font-size: 25px">{{ item.name }}</span>
                        </div>
                        <div style="float: right; margin-top: 150px;">
                            <span style="font-size: 14px; color: #8c939d" v-if="item.state === '已支付'">支持三天内无理由退货 </span>
                            <span style="font-size: 17px" v-if="item.state === '待支付'">待支付: </span>
                            <span style="font-size: 17px" v-if="item.state === '已支付'">已支付: </span>
                            <span style="font-size: 25px; color: red">￥ {{ item.total }}</span>
                        </div>
                        <div style="text-align: right; margin-top: 190px; margin-right: 30px">
                            <el-button type="primary" style="border-radius: 20px; font-size: 17px; width: 80px; height: 40px" @click="payInsurance(item)" v-if="item.state === '待支付'">支 付</el-button>
                            <el-button type="danger" style="border-radius: 20px; font-size: 17px; width: 80px; height: 40px" @click="returnPay(item)" v-if="item.state === '已支付'">退款</el-button>
                            <el-button type="warning" style="border-radius: 20px; font-size: 17px; width: 100px; height: 40px" @click="payInsurance(item)" v-if="item.state === '已退款'">重新购买</el-button>
                        </div>
                    </el-card>
                </el-col>
            </el-row>
        </div>
<!--        <el-table :data="tableData"  :header-cell-class-name="'headerBg'" size="medium" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="no" label="订单号" width="120"></el-table-column>
            <el-table-column prop="username" label="购险人" width="100"></el-table-column>
            <el-table-column prop="name" label="名称"></el-table-column>
            <el-table-column prop="img" label="图片">
                <template slot-scope="scope">
                    　<img style="width: 74px;height: 74px" :src="scope.row.img" class="img" alt=""/>
                </template>
            </el-table-column>
            <el-table-column prop="time" label="下单时间"></el-table-column>
            <el-table-column prop="state" label="支付状态" width="100"></el-table-column>
            <el-table-column prop="total" label="订单总价" ></el-table-column>
            <el-table-column prop="paymentTime" label="支付时间" ></el-table-column>
            <el-table-column prop="alipayNo" label="支付宝流水号" width="140" ></el-table-column>
            <el-table-column prop="returnTime" label="退款时间"></el-table-column>
            <el-table-column label="操作"  width="280" align="center">
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
        </div>-->
    </div>
</template>


<script>
import insurance from "@/views/Insurance.vue";
import {serverIp} from "../../../public/config";

export default {
    name: "Orders",
    data() {
        return {
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
                    this.$message.error("退款失败")
                }
                this.load()
            })
        },
        load() {
            this.request.get("/orders/page", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                }
            }).then(res => {
                this.tableData = res.data.records
                this.total = res.data.total

            })
        },

        reset(){
            this.name = ""
            this.types = ""
            this.price = ""
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
        delBatch() {
            if (!this.multipleSelection.length) {
                this.$message.error("请选择需要删除的数据")
                return
            }
            let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
            this.request.post("/orders/del/batch", ids).then(res => {
                if (res.code === '200') {
                    this.$message.success("批量删除成功")
                    this.load()
                } else {
                    this.$message.error("批量删除失败")
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
        handleSelectionChange(val) {
            console.log(val)
            this.multipleSelection = val
        },
    }
}
</script>

<style scoped>
</style>