<template>
  <div>
    <div style="margin: 10px 0">
      <el-input class="ml-5" style="width: 200px"  placeholder="请输入保险名称" suffix-icon="el-icon-info" v-model="name"></el-input>
      <el-input class="ml-5" style="width: 200px"  placeholder="种类" suffix-icon="el-icon-position" v-model="types"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd"v-if="user.role === 'ROLE_ADMIN'"><i class="el-icon-circle-plus-outline"></i> 新增</el-button>
      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定批量删除这些数据吗？"
          @confirm="delBatch"
      >
        <el-button type="danger" slot="reference" v-if="user.role === 'ROLE_ADMIN'">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"   @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="name" label="保险名称" width="200"></el-table-column>
      <el-table-column prop="img" label="保险图片">
        <template slot-scope="scope">
          　<img style="width: 74px;height: 74px" :src="scope.row.img" class="img"/>
        </template>
      </el-table-column>
        <el-table-column prop="description" label="内容">
            <template v-slot="scope">
                <el-button type="primary" @click="view(scope.row.description)">查看富文本内容</el-button>
            </template>
        </el-table-column>
      <el-table-column prop="price" label="保险价格" width="150"></el-table-column>
      <el-table-column prop="types" label="保险类型" >
        <template slot-scope="scope" >
          <el-tag type="primary" v-if="scope.row.types === '人身意外险'">人身意外险</el-tag>
          <el-tag type="success" v-if="scope.row.types === '机动车辆险'">机动车辆险</el-tag>
          <el-tag type="info" v-if="scope.row.types === '医疗养老险'">医疗养老险</el-tag>
          <el-tag type="warning" v-if="scope.row.types === '工伤责任险'">工伤责任险</el-tag>
            <el-tag type="danger" v-if="scope.row.types === '财产保障险'">财产保障险</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作"  width="300" align="center">
        <template slot-scope="scope">
          <el-button type="primary" @click="buyInsurance(scope.row.id)" v-if="user.role === 'ROLE_USER'"><i class="el-icon-edit"></i>加入购物车</el-button>
          <el-button type="success" v-if="user.role === 'ROLE_ADMIN'" @click="handleEdit(scope.row)"><i class="el-icon-edit"></i>编辑</el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="del(scope.row.id)"
          >
            <el-button type="danger" slot="reference" v-if="user.role === 'ROLE_ADMIN'">删除 <i class="el-icon-remove-outline"></i></el-button>
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
    <el-dialog title="保险信息" :visible.sync="dialogFormVisible" width="50%">
      <el-form label-width="80px" size="small">
        <el-form-item label="保险名称">
          <el-input v-model="form.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="保险图片">
          <el-upload
              class="avatar-uploader"
              :action="'http://' + serverIp + ':8088/file/upload'"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
          >
            <img v-if="form.img" :src="form.img" class="img">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="保险价格">
          <el-input v-model="form.price" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述">
            <div id="richText"></div>
        </el-form-item>
        <el-form-item label="特色">
            <el-upload
                    class="avatar-uploader"
                    :action="'http://' + serverIp + ':8088/file/upload'"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess1"
            >
                <img v-if="form. characteristic" :src="form.characteristic" class="img">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
        </el-form-item>
        <el-form-item label="理赔流程">
            <el-upload
                    class="avatar-uploader"
                    :action="'http://' + serverIp + ':8088/file/upload'"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess2"
            >
                <img v-if="form. claims" :src="form.claims" class="img">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
        </el-form-item>
        <el-form-item label="保险种类">
          <el-select clearable v-model="form.types" placeholder="请选择" style="width: 100%">
            <el-option v-for="item in types" :key="item.types" :label="item.types" :value="item.flag"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
      <el-dialog title="富文本内容" :visible.sync="dialogFormVisible1" width="60%">
          <el-card>
              <div v-html="description"></div>
          </el-card>
      </el-dialog>
  </div>
</template>

<script>
import insurance from "@/views/Insurance.vue";
import {serverIp} from "../../public/config";
import E from "wangeditor";
let editor;

export default {
  name: "Insurance",
  data() {
    return {
      serverIp: serverIp,
      tableData: [],
      form:{},
      name: '',
      types: '',
      price: null,
      multipleSelection: [],
      dialogFormVisible: false,
      dialogFormVisible1: false,
      pageNum: 1,
      pageSize: 5,
      total: 0,
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      insurance: [],
      vis: false,
      findInsurance:[],
        description: '',
    }
  },
  created() {
    this.load()
  },
  methods: {
      view(description){
          this.description = description
          this.dialogFormVisible1 = true
      },
    load() {
      this.request.get("/insurance/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
          types: this.types,
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },

      handleAdd(){
          this.dialogFormVisible = true
          this.form = {img: ''}
          this.$nextTick(() => {
              if (!editor){
                  editor = new E("#richText")
                  editor.config.uploadImgServer = `http://${serverIp}:8088/file/uploadImg`
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
    save(){
      const description = editor.txt.html()
      console.log(description)
      this.form.content = description
      this.request.post("/insurance",this.form).then(res => {
        if (res.code === '200'){
          this.$message.success("保存成功！")
          this.dialogFormVisible = false
        }else {
          this.$message.error("保存失败")
        }
        this.load()
      })
    },
    cancel() {
      this.dialogFormVisible=false
      this.load()
    },
    buyInsurance(insuranceId){
        this.request.post('/orders/' + insuranceId).then(res => {
            if (res.code === '200') {
                this.$message.success("已加入订单，请前往支付")
            }else {
                this.$message.error(res.msg)
            }
        })
    },
      handleEdit(row){
          this.form = Object.assign({},row) // 将row拷贝到空对象中 解决没点确定数据改变的问题
          this.dialogFormVisible = true
          this.$nextTick(() => {
              if (!editor){
                  editor = new E("#richText")
                  editor.config.uploadImgServer = `http://${serverIp}:8088/file/uploadImg`
                  editor.config.uploadFileName = 'file'
                  editor.create()
              }
              editor.txt.html(this.form.description)
              if (this.$refs.img){
                  this.$refs.img.clearFiles();
              }
              if (this.$refs.file){
                  this.$refs.file.clearFiles();
              }
          })
      },
    changeEnable(row) {
      this.request.post("/insurance/update", row).then(res => {
        if (res.code === '200') {
          this.$message.success("操作成功")
        }
      })
    },
    del(id) {
      this.request.delete("/insurance/" + id).then(res => {
        if (res.code === '200') {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error("删除失败")
        }
      })
    },
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },
    delBatch() {
      let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
      this.request.post("/insurance/del/batch", ids).then(res => {
        if (res.code === '200') {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error("批量删除失败")
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
    handleFileUploadSuccess(res) {
      this.$message.success("上传成功！")
      console.log(res)
      this.load()
    },
    download(url) {
      window.open(url)
    },
    handleAvatarSuccess(res) {
      this.form.img = res
    },
    handleAvatarSuccess1(res) {
        this.form.characteristic = res
    },
    handleAvatarSuccess2(res) {
        this.form.claims = res
    },
    lookInsurance (){
        this.request.get('insurance/findInsurance/'  + this.user.id ).then(r => {
            this.findInsurance = r.data
            if (r.code === '200'){
                this.vis = true
            }else {
                this.$message.error("错误")
            }
        })
    },
    type (){
      this.request.get("/types").then(res => {
          this.types=res.data
      })
    },
  }
}
</script>

<style>
.avatar-uploader {
  padding-bottom: 5px;
}
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
  font-size: 30px;
  color: #8c939d;
  width: 70px;
  height: 70px;
  line-height: 80px;
  text-align: center;
}
.img {
  width: 70px;
  height: 70px;
  display: block;
}
</style>
