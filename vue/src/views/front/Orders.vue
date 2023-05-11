<template>
    <div>
        <div style="width: 1000px;margin-left: 110px">
            <el-tabs v-model="activeName" @tab-click="handleClick">
                <el-tab-pane label="全部" name="first">
                    <div style="margin-top: 20px; width: 1000px">
                        <el-row :gutter="5">
                            <el-col :span="24" v-for="item in tableData" :key="item.id" style="margin-bottom: 20px">
                                <el-card class="card">
                                    <div style="font-size: 18px;color: #ec5a00; float: right;">{{item.state}}</div>
                                    <div style="float: left; width: 260px;height: 260px">
                                        <img :src="item.img" alt="" style="width: 95%;">
                                    </div>
                                    <div style="float: left; margin-left: 10px;" >
                                        <span style="color: #8c939d;font-size: 10px">订单号：{{ item.no }}</span>
                                        <br><br><br><br><br>
                                        <span style="font-size: 30px; margin-left: 120px">{{ item.name }}</span>
                                        <br><br><br><br><br><br><br><br>
                                        <span style="color: #8c939d;font-size: 10px;">创建时间：{{ item.time }}</span>
                                    </div>
                                    <div style="float: left; margin-left: 10px;margin-top: 80px" >

                                    </div>
                                    <div style="float: right; margin-top: 140px;">
                                        <span style="font-size: 14px; color: #8c939d" v-if="item.state === '已支付'">支持三天内无理由退货 </span>
                                        <span style="font-size: 17px" v-if="item.state === '待支付'">待支付: </span>
                                        <span style="font-size: 17px" v-if="item.state === '已支付'">已支付: </span>
                                        <span style="font-size: 25px; color: red">￥ {{ item.total }}</span>
                                    </div>
                                    <div style="text-align: right; margin-top: 180px; margin-right: 40px">
                                        <a style="margin-right: 20px; font-size: 12px; cursor: pointer" @click="order(item)">订单详情</a>
                                        <a style="margin-right: 20px; font-size: 12px; cursor: pointer" @click="del(item.id)" v-if="item.state === '待支付'">取消订单</a>
                                        <el-button type="primary" style="border-radius: 20px; font-size: 17px; width: 80px; height: 40px" @click="payInsurance(item)" v-if="item.state === '待支付'">支 付</el-button>
                                        <a style="margin-right: 20px; font-size: 12px; cursor: pointer" @click="del(item.id)" v-if="item.state === '已支付'">删除订单</a>
                                        <el-button type="danger" style="border-radius: 20px; font-size: 17px; width: 80px; height: 40px" @click="returnPay(item)" v-if="item.state === '已支付'">退款</el-button>
                                        <a style="margin-right: 20px; font-size: 13px; cursor: pointer" @click="del(item.id)" v-if="item.state === '已退款'">删除订单</a>
                                    </div>
                                </el-card>
                            </el-col>
                        </el-row>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="待支付" name="second">
                    <div style="margin-top: 20px; width: 1000px">
                        <el-row :gutter="5">
                            <el-col :span="24" v-for="item in tableData" :key="item.id" style="margin-bottom: 20px">
                                <div v-if="item.state === '待支付'">
                                    <el-card class="card">
                                        <div style="font-size: 18px;color: #ec5a00; float: right;">{{item.state}}</div>
                                        <div style="float: left; width: 260px;height: 260px">
                                            <img :src="item.img" alt="" style="width: 95%;">
                                        </div>
                                        <div style="float: left; margin-left: 10px;" >
                                            <span style="color: #8c939d;font-size: 10px">订单号：{{ item.no }}</span>
                                            <br><br><br><br><br>
                                            <span style="font-size: 30px; margin-left: 120px">{{ item.name }}</span>
                                            <br><br><br><br><br><br><br><br>
                                            <span style="color: #8c939d;font-size: 10px;">创建时间：{{ item.time }}</span>
                                        </div>
                                        <div style="float: left; margin-left: 10px;margin-top: 80px" >

                                        </div>
                                        <div style="float: right; margin-top: 140px;">
                                            <span style="font-size: 14px; color: #8c939d" v-if="item.state === '已支付'">支持三天内无理由退货 </span>
                                            <span style="font-size: 17px" v-if="item.state === '待支付'">待支付: </span>
                                            <span style="font-size: 17px" v-if="item.state === '已支付'">已支付: </span>
                                            <span style="font-size: 25px; color: red">￥ {{ item.total }}</span>
                                        </div>
                                        <div style="text-align: right; margin-top: 180px; margin-right: 40px">
                                            <a style="margin-right: 20px; font-size: 12px; cursor: pointer" @click="order(item)">订单详情</a>
                                            <a style="margin-right: 20px; font-size: 12px; cursor: pointer" @click="del(item.id)" v-if="item.state === '待支付'">取消订单</a>
                                            <el-button type="primary" style="border-radius: 20px; font-size: 17px; width: 80px; height: 40px" @click="payInsurance(item)" v-if="item.state === '待支付'">支 付</el-button>
                                            <a style="margin-right: 20px; font-size: 12px; cursor: pointer" @click="del(item.id)" v-if="item.state === '已支付'">删除订单</a>
                                            <el-button type="danger" style="border-radius: 20px; font-size: 17px; width: 80px; height: 40px" @click="returnPay(item)" v-if="item.state === '已支付'">退款</el-button>
                                            <a style="margin-right: 20px; font-size: 13px; cursor: pointer" @click="del(item.id)" v-if="item.state === '已退款'">删除订单</a>
                                        </div>
                                    </el-card>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="已支付" name="third">
                    <div style="margin-top: 10px; width: 1000px">
                        <el-row :gutter="5">
                            <el-col :span="24" v-for="item in tableData" :key="item.id" style="margin-bottom: 20px">
                                <div v-if="item.state === '已支付'">
                                    <el-card class="card">
                                        <div style="font-size: 18px;color: #ec5a00; float: right;">{{item.state}}</div>
                                        <div style="float: left; width: 260px;height: 260px">
                                            <img :src="item.img" alt="" style="width: 95%;">
                                        </div>
                                        <div style="float: left; margin-left: 10px;" >
                                            <span style="color: #8c939d;font-size: 10px">订单号：{{ item.no }}</span>
                                            <br><br><br><br><br>
                                            <span style="font-size: 30px; margin-left: 120px">{{ item.name }}</span>
                                            <br><br><br><br><br><br><br><br>
                                            <span style="color: #8c939d;font-size: 10px;">创建时间：{{ item.time }}</span>
                                        </div>
                                        <div style="float: left; margin-left: 10px;margin-top: 80px" >

                                        </div>
                                        <div style="float: right; margin-top: 140px;">
                                            <span style="font-size: 14px; color: #8c939d" v-if="item.state === '已支付'">支持三天内无理由退货 </span>
                                            <span style="font-size: 17px" v-if="item.state === '待支付'">待支付: </span>
                                            <span style="font-size: 17px" v-if="item.state === '已支付'">已支付: </span>
                                            <span style="font-size: 25px; color: red">￥ {{ item.total }}</span>
                                        </div>
                                        <div style="text-align: right; margin-top: 180px; margin-right: 40px">
                                            <a style="margin-right: 20px; font-size: 12px; cursor: pointer" @click="order(item)">订单详情</a>
                                            <a style="margin-right: 20px; font-size: 12px; cursor: pointer" @click="del(item.id)" v-if="item.state === '待支付'">取消订单</a>
                                            <el-button type="primary" style="border-radius: 20px; font-size: 17px; width: 80px; height: 40px" @click="payInsurance(item)" v-if="item.state === '待支付'">支 付</el-button>
                                            <a style="margin-right: 20px; font-size: 12px; cursor: pointer" @click="del(item.id)" v-if="item.state === '已支付'">删除订单</a>
                                            <el-button type="danger" style="border-radius: 20px; font-size: 17px; width: 80px; height: 40px" @click="returnPay(item)" v-if="item.state === '已支付'">退款</el-button>
                                            <a style="margin-right: 20px; font-size: 13px; cursor: pointer" @click="del(item.id)" v-if="item.state === '已退款'">删除订单</a>
                                        </div>
                                    </el-card>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="已退款" name="forth">
                    <div style="margin-top: 10px; width: 1000px">
                        <el-row :gutter="5">
                            <el-col :span="24" v-for="item in tableData" :key="item.id" style="margin-bottom: 20px">
                                <div v-if="item.state === '已退款'">
                                    <el-card class="card">
                                        <div style="font-size: 18px;color: #ec5a00; float: right;">{{item.state}}</div>
                                        <div style="float: left; width: 260px;height: 260px">
                                            <img :src="item.img" alt="" style="width: 95%;">
                                        </div>
                                        <div style="float: left; margin-left: 10px;" >
                                            <span style="color: #8c939d;font-size: 10px">订单号：{{ item.no }}</span>
                                            <br><br><br><br><br>
                                            <span style="font-size: 30px; margin-left: 120px">{{ item.name }}</span>
                                            <br><br><br><br><br><br><br><br>
                                            <span style="color: #8c939d;font-size: 10px;">创建时间：{{ item.time }}</span>
                                        </div>
                                        <div style="float: left; margin-left: 10px;margin-top: 80px" >

                                        </div>
                                        <div style="float: right; margin-top: 140px;">
                                            <span style="font-size: 14px; color: #8c939d" v-if="item.state === '已支付'">支持三天内无理由退货 </span>
                                            <span style="font-size: 17px" v-if="item.state === '待支付'">待支付: </span>
                                            <span style="font-size: 17px" v-if="item.state === '已支付'">已支付: </span>
                                            <span style="font-size: 25px; color: red">￥ {{ item.total }}</span>
                                        </div>
                                        <div style="text-align: right; margin-top: 180px; margin-right: 40px">
                                            <a style="margin-right: 20px; font-size: 12px; cursor: pointer" @click="order(item)">订单详情</a>
                                            <a style="margin-right: 20px; font-size: 12px; cursor: pointer" @click="del(item.id)" v-if="item.state === '待支付'">取消订单</a>
                                            <el-button type="primary" style="border-radius: 20px; font-size: 17px; width: 80px; height: 40px" @click="payInsurance(item)" v-if="item.state === '待支付'">支 付</el-button>
                                            <a style="margin-right: 20px; font-size: 12px; cursor: pointer" @click="del(item.id)" v-if="item.state === '已支付'">删除订单</a>
                                            <el-button type="danger" style="border-radius: 20px; font-size: 17px; width: 80px; height: 40px" @click="returnPay(item)" v-if="item.state === '已支付'">退款</el-button>
                                            <a style="margin-right: 20px; font-size: 13px; cursor: pointer" @click="del(item.id)" v-if="item.state === '已退款'">删除订单</a>
                                        </div>
                                    </el-card>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                </el-tab-pane>
            </el-tabs>
        </div>


        <el-dialog title="订单详情" custom-class="dialog" :visible.sync="dialogFormVisible" width="30%">
            <el-form label-width="100px" size="small">
                <el-form-item label="订单号 ">
                    <a>{{ form.no }}</a>
                </el-form-item>
                <el-form-item label="购险人  ">
                    <a>{{ form.username }}</a>
                </el-form-item>
                <el-form-item label="保险类型  ">
                    <a>{{ form.type }}</a>
                </el-form-item>
                <el-form-item label="名称  ">
                    <a>{{ form.name }}</a>
                </el-form-item>
                <el-form-item label="图片 ">
                    <div style="float: left; width: 150px;height: 150px">
                        <img :src="form.img" alt="" style="width: 95%;">
                    </div>
                </el-form-item>
                <el-form-item label="下单时间  ">
                    <a>{{ form.time }}</a>
                </el-form-item>
                <el-form-item label="支付状态  ">
                    <a>{{ form.state }}</a>
                </el-form-item>
                <el-form-item label="订单总价  ">
                    <a>{{ form.total }}</a>
                </el-form-item>
                <el-form-item label="支付时间  ">
                    <a>{{ form.paymentTime }}</a>
                </el-form-item>
                <el-form-item label="支付宝流水号  ">
                    <a>{{ form.alipayNo }}</a>
                </el-form-item>
                <el-form-item label="退款时间  ">
                    <a>{{ form.returnTime }}</a>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button style="height: 35px" @click="dialogFormVisible = false">好 的</el-button>
            </div>
        </el-dialog>
    </div>
</template>


<script>
import insurance from "@/views/Insurance.vue";
import {serverIp} from "../../../public/config";

export default {
    name: "Orders",
    data() {
        return {
            activeName: 'first',
            dialogFormVisible: false,
            tableData: [],
            form: [],
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
        load1() {
            this.request.get("/orders/page", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    state: '未支付'
                }
            }).then(res => {
                this.tableData = res.data.records
                this.total = res.data.total
            })
        },
        load2() {
            this.request.get("/orders/page", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    state: '已支付'
                }
            }).then(res => {
                this.tableData = res.data.records
                this.total = res.data.total
            })
        },
        load3() {
            this.request.get("/orders/page", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    state: '已退款'
                }
            }).then(res => {
                this.tableData = res.data.records
                this.total = res.data.total
            })
        },
        order(item){
            this.form = item
            this.dialogFormVisible = true
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
                    this.$message.success("取消成功")
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
            this.pageSize = pageSize
            this.load()
        },
        handleCurrentChange(pageNum) {
            this.pageNum = pageNum
            this.load()
        },
        handleSelectionChange(val) {
            this.multipleSelection = val
        },
        handleClick(){
        }
    }
}
</script>

<style scoped>
</style>