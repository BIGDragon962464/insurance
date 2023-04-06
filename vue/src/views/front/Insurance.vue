<template>
    <div>
        <div style="margin: 10px 0">
            <el-input class="ml-5" style="width: 200px"  placeholder="请输入保险名称" suffix-icon="el-icon-info" v-model="name"></el-input>
            <el-input class="ml-5" style="width: 200px"  placeholder="种类" suffix-icon="el-icon-position" v-model="types"></el-input>
            <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
            <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
        </div>
        <el-table :data="tableData" stripe :header-cell-class-name="'headerBg'">
            <el-table-column prop="name" label="保险名称" width="200"></el-table-column>
            <el-table-column prop="img" label="保险图片">
                <template slot-scope="scope">
                    　<img :src="scope.row.img" class="img"/>
                </template>
            </el-table-column>
            <el-table-column prop="description" label="描述"></el-table-column>
            <el-table-column prop="price" label="保险价格" width="150"></el-table-column>
            <el-table-column prop="types" label="保险类型" >
                <template slot-scope="scope">
                    <el-tag type="primary" v-if="scope.row.types === '人身意外险'">人身意外险</el-tag>
                    <el-tag type="success" v-if="scope.row.types === '机动车辆险'">机动车辆险</el-tag>
                    <el-tag type="info" v-if="scope.row.types === '医疗养老险'">医疗养老险</el-tag>
                    <el-tag type="warning" v-if="scope.row.types === '工伤责任险'">工伤责任险</el-tag>
                    <el-tag type="warning" v-if="scope.row.types === '财产保障险'">财产保障险</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作"  width="300" align="center">
                <template slot-scope="scope">
                    <el-button type="primary"   @click="buyInsurance(scope.row.id)"><i class="el-icon-edit"></i>购买</el-button>
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

export default {
    name: "Insurance",
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
        load() {
            this.request.get("/insurance/page", {
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

            })
        },
        buyInsurance(insuranceId){
            this.request.post('/insurance/buyInsurance/' + this.user.id + "/" + insuranceId).then(res => {
                if (res.code === '200') {
                    this.$message.success("已购买")
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
