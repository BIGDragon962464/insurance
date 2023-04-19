<template><!--    <div>
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
    </div>-->

    <div>
        <br><br>
        <div style="margin: 10px 0">
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
        </div>
        <!--        <div style="margin: 10px 0">
                    <el-input class="ml-5" style="width: 200px"  placeholder="请输入保险名称" suffix-icon="el-icon-info" v-model="name"></el-input>
                    <el-input class="ml-5" style="width: 200px"  placeholder="种类" suffix-icon="el-icon-position" v-model="types"></el-input>
                    <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
                    <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
                </div>-->
        <el-table :data="tableData"  :header-cell-class-name="'headerBg'" size="medium" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="no" label="订单号" width="120"></el-table-column>
            <el-table-column prop="username" label="购险人" width="100"></el-table-column>
            <el-table-column prop="name" label="名称"></el-table-column>
            <el-table-column prop="time" label="下单时间"></el-table-column>
            <el-table-column prop="state" label="支付状态" width="100"></el-table-column>
            <el-table-column prop="total" label="订单总价" ></el-table-column>
            <el-table-column prop="paymentTime" label="支付时间" ></el-table-column>
            <el-table-column prop="alipayNo" label="支付宝流水号" ></el-table-column>
            <el-table-column label="操作"  width="300" align="center">
                <template slot-scope="scope">
                    <el-button type="primary" @click="payInsurance(scope.row)"><i class="el-icon-edit"></i>支 付</el-button>
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
