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
                    <el-button type="success" slot="reference" @click="vis"><i class="el-icon-remove-outline"></i> 理赔申请</el-button>
                </template>
            </el-table-column>
        </el-table>


        <el-dialog title="理赔信息" :visible.sync="dialogFormVisible" width="40%">
            <el-form label-width="80px">
                <el-form-item label="提交文件">
                    <el-upload
                            class="upload-demo"
                            drag
                            action="http://localhost:8088/file/upload"
                            multiple>
                        <i class="el-icon-upload"></i>
                        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                    </el-upload>
                </el-form-item>
                <el-form-item label="日期">
                    <el-date-picker
                            v-model="value1"
                            type="date"
                            placeholder="选择日期">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="地址">
                    <el-input v-model="form.address" auto-complete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancel">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import insurance from "@/views/Insurance.vue";

export default {
    name: "Claims",
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
            dialogFormVisible: false,
            value1: '',
        }
    },
    created() {
        this.load()
    },
    methods: {
        load (){
            this.request.get('insurance/findInsurance/'  + this.user.id ).then(r => {
                this.findInsurance = r.data
            })
        },
        vis(){
            this.dialogFormVisible = true
        },
        cancel() {
            this.dialogFormVisible=false
            this.load()
        },
    }
}
</script>

<style scoped>

</style>
