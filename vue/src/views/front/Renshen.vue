<template>
    <div>
        <div style="margin-left: 100px;margin-top: 20px">
            <el-row :gutter="5">
                <el-col :span="24" v-for="item in tableData" :key="item.id" style="margin-bottom: 20px">
                    <el-card class="card">
                        <div style="float: left; width: 260px;height: 260px">
                            <img :src="item.img" alt="" style="width: 100%;">
                        </div>
                        <div style="float: left; margin-left: 10px;" >
                            <br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
                            <span style="color: #8c939d;font-size: 15px; margin-left: 10px">由龙保险提供</span>
                        </div>
                        <div style="float: left;margin-left: 120px;margin-top: 50px" >
                            <span style="font-size: 25px">{{ item.name }}</span>
                            <div style="font-size: 30px; color: red;margin-top: 20px">￥ {{item.price}}</div>
                            <div style="margin-top: 20px"><el-button style="border-radius: 25px; height: 50px;width: 120px;font-size: 20px" type="primary" @click="open(item)">查看详情</el-button></div>
                        </div>
                    </el-card>
                </el-col>
            </el-row>
        </div>

        <el-dialog title="保险详情" custom-class="dialog" :visible.sync="dialogFormVisible" width="50%" :close-on-click-modal="false">
            <div style="margin-left: 5px">
                <el-tabs v-model="activeName" @tab-click="handleClick">
                    <el-tab-pane label="保险详情" name="first">
                        <div style="margin-top: 10px; margin-bottom: 10px">
                            {{ form.description }}
                        </div>
                        <hr style="background-color: rgba(196,196,196,0.33); border:none; height: 1px">
                        <div style="height: 20px; margin-top: 5px; margin-bottom: 5px">
                            <a style="float: left">保险期限</a>
                            <div style="margin-right: 20px;float: right">
                                {{ form.time }}
                            </div>
                        </div>
                        <hr style="background-color: rgba(196,196,196,0.33); border:none; height: 1px">
                        <div style="height: 20px; margin-top: 5px; margin-bottom: 5px">
                            <a style="margin-top: 10px">保险费用</a>
                            <div style="margin-right: 20px;float: right">
                                {{ form.price }}元
                            </div>
                        </div>
                    </el-tab-pane>
                    <el-tab-pane label="保险特色" name="second">
                        <img :src="form.characteristic" alt="" style="width: 100%;">
                    </el-tab-pane>
                    <el-tab-pane label="理赔流程" name="third">
                        <img :src="form.claims" alt="" style="width: 100%;">
                    </el-tab-pane>
                </el-tabs>
            </div>
            <div style="margin-top: 20px;margin-left: 600px"><el-button style="border-radius: 25px; height: 50px;width: 100px;font-size: 20px" type="primary" @click="buyInsurance(form.id)">购买</el-button></div>
        </el-dialog>
    </div>
</template>

<script>
import insurance from "@/views/Insurance.vue";

export default {
    name: "Insurance",
    data() {
        return {
            activeName: 'first',
            dialogFormVisible: false,
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
            form: [],
        }
    },
    created() {
        this.load()
    },
    methods: {
        load() {
            this.request.get("/insurance/renshen", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                }
            }).then(res => {
                this.tableData = res.data.records
            })
        },
        buyInsurance(insuranceId){
            if (this.user.trueName === null || this.user.trueId === null || this.user.trueName === "" || this.user.trueId === "") {
                this.$message.error("请先前往实名")
            }else {
                this.request.post('/orders/' + insuranceId).then(res => {
                    if (res.code === '200') {
                        this.$notify({
                            title: '成功加入订单',
                            message: '请前往支付',
                            type: 'success'
                        });
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
        open(item){
            this.dialogFormVisible = true
            this.form = item
        },
        handleClick(){
        }
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
