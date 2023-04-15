<template>
    <div>
        <div style="margin: 10px 0">
            <el-input style="width: 200px"  placeholder="请输入标题" suffix-icon="el-icon-search" v-model="name"></el-input>
            <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
            <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
        </div>


        <div style="margin: 10px 0">
            <el-button type="primary" @click="handleAdd"><i class="el-icon-circle-plus-outline"></i> 新增</el-button>
            <el-popconfirm
                    class="ml-5"
                    confirm-button-text='确定'
                    cancel-button-text ='我再想想吧'
                    icon="el-icon-info"
                    icon-color="red"
                    title="您确定批量删除吗？"
                    @confirm="delBatch"
            >
                <el-button type="danger" slot="reference"><i class="el-icon-remove-outline"></i> 批量删除</el-button>
            </el-popconfirm>

        </div>

        <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="id" label="ID" width="60"></el-table-column>
            <el-table-column prop="name" label="标题" width="100"></el-table-column>
            <el-table-column prop="content" label="内容">
                <template v-slot="scope">
                    <el-button type="primary" @click="view(scope.row.content)">查看富文本内容</el-button>
                </template>
            </el-table-column>
            <el-table-column prop="time" label="创建时间"></el-table-column>
            <el-table-column prop="user" label="发布人"></el-table-column>
            <el-table-column prop="img" label="公告封面">
                <template slot-scope="scope">
                    　<img style="width: 74px;height: 74px" :src="scope.row.img" class="img"/>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="200" align="center">
                <template v-slot="scope">
                    <el-button type="success" @click="handleEdit(scope.row)"><i class="el-icon-edit"></i> 编辑</el-button>
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
        <div style="padding: 10px 0">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="pageNum"
                    :page-sizes="[5, 8]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </div>

        <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="60%">
            <el-form label-width="80px" size="small">
                <el-form-item label="公告封面">
                    <el-upload
                        class="avatar-uploader"
                        action="http://localhost:8088/file/upload"
                        :show-file-list="false"
                        :on-success="handleImgUploadSuccess"
                    >
                        <img v-if="form.img" :src="form.img" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-form-item>
                <el-form-item label="标题">
                    <el-input v-model="form.name" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="内容">
                    <div id="richText"></div>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancel">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog title="富文本内容" :visible.sync="dialogFormVisible1" width="60%">
            <el-card>
                <div v-html="content"></div>
            </el-card>
        </el-dialog>
    </div>
</template>

<script>
import E from "wangeditor"
let editor;
export default {
    name: "Notice",
    data(){
        return{
            form:{},
            dialogFormVisible: false,
            dialogFormVisible1: false,
            content: '',
            multipleSelection: [],
            tableData: [],
            total: 0,
            pageNum: 1,
            pageSize: 5,
            roles: [],
            insurance: [],
            name: '',
        }
    },
    created() {
        this.load()
    },
    methods: {
        view(content){
            this.content = content
            this.dialogFormVisible1 = true
        },
        load(){
            this.request.get("/notice/page", {
                params:{
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    name: this.name,
                }
            }).then(res => {
                console.log(res.data)
                this.tableData = res.data.records
                this.total = res.data.total
            })
        },

        save(){
            const content = editor.txt.html()
            console.log(content)
            this.form.content = content
            this.request.post("/notice",this.form).then(res => {
                if (res.code === '200'){
                    this.$message.success("保存成功！")
                    this.dialogFormVisible = false
                    this.load()
                }else {
                    this.$message.error("保存失败")
                }
            })
        },
        cancel() {
            this.dialogFormVisible=false
            this.load()
        },

        handleAdd(){
            this.dialogFormVisible = true
            this.form = {img: ''}
            this.$nextTick(() => {
                if (!editor){
                    editor = new E("#richText")
                    editor.config.uploadImgServer = 'http://localhost:8088/file/uploadImg'
                    editor.config.uploadFileName = 'file'
                    editor.create()
                }
                editor.txt.html("")     //清除内容
                if (this.$refs.img){
                    this.$refs.img.clearFiles();
                }
                if (this.$refs.file){
                    this.$refs.file.clearFiles();
                }
            })
        },
        handleEdit(row){
            this.form = Object.assign({},row) // 将row拷贝到空对象中 解决没点确定数据改变的问题
            this.dialogFormVisible = true
            this.$nextTick(() => {
                if (!editor){
                    editor = new E("#richText")
                    editor.config.uploadImgServer = 'http://localhost:8088/file/uploadImg'
                    editor.config.uploadFileName = 'file'
                    editor.create()
                }
                editor.txt.html(this.form.content)
                if (this.$refs.img){
                    this.$refs.img.clearFiles();
                }
                if (this.$refs.file){
                    this.$refs.file.clearFiles();
                }
            })
        },
        del(id){
            this.request.delete("/notice/" + id).then(res => {
                if (res.code === '200'){
                    this.$message.success("删除成功！")
                    this.load()
                }else {
                    this.$message.error("删除失败")
                }
            })
        },

        handleSelectionChange(val){
            console.log(val)
            this.multipleSelection = val
        },
        delBatch(){
            let ids = this.multipleSelection.map(v => v.id)     // [{},{},{}] -> [1,2,3]
            this.request.post("/notice/del/batch",ids).then(res => {
                if (res.code === '200' ){
                    this.$message.success("批量删除成功！")
                    this.load()
                }else {
                    this.$message.error("删除失败")
                }
            })
        },
        reset(){
            this.name = ""
            this.load()
        },

        handleSizeChange(pageSize){
            console.log(pageSize)
            this.pageSize = pageSize
            this.load()
        },
        handleCurrentChange(pageNum){
            console.log(pageNum)
            this.pageNum = pageNum
            this.load()
        },
        handleImgUploadSuccess(res) {
            this.form.img = res
        },
    }
}
</script>

<style>
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
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
}
.avatar {
    width: 178px;
    height: 178px;
    display: block;
}
</style>