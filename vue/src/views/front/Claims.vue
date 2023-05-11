<template>
<!--    <div>
        <br><br>
        <div style="margin: 10px 0">
            <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
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
            <a style="margin-left: 900px; color: #409EFF; cursor: pointer" @click="download">点击这里下载模板</a>
        </div>

        <el-table :data="tableData"  :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="id" label="ID" width="80" sortable></el-table-column>
            <el-table-column prop="name" label="保险名称"></el-table-column>
            <el-table-column prop="user" label="所属人"></el-table-column>
            <el-table-column prop="" label="文件">
                <template slot-scope="scope">
                    <div style="color: #cccccc">文件暂不支持预览</div>
                </template>
            </el-table-column>
            <el-table-column prop="time" label="申请时间"></el-table-column>
            <el-table-column prop="state" label="理赔状态"></el-table-column>
            <el-table-column label="操作"  width="180" align="center">
                <template slot-scope="scope" v-if="scope.row.user === user.username || user.role === 'ROLE_ADMIN'">
                    <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
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
                :page-sizes="[2, 5, 10, 20]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
        </div>

        <el-dialog title="信息" :visible.sync="dialogFormVisible" width="30%" :close-on-click-modal="false">
            <el-form label-width="100px" size="small" style="width: 90%">
                <el-form-item label="保险名称">
                    <el-select clearable v-model="form.name" placeholder="请选择" style="width: 100%">
                        <el-option v-for="(item , index) in options" :label="item.name" :key="index.name" :value="item.name">
                            {{item.name}}
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="理赔文件">
                    <el-upload :action="'http://' + serverIp + ':8088/file/upload'" ref="img" :on-success="handleImgUploadSuccess">
                        <el-button size="small" type="primary">点击上传</el-button>
                    </el-upload>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>
    </div>-->
  <div>
      <div>
          <div style="margin-left: 120px; margin-top: 50px;">
              <el-card style="border-radius: 15px;height: 500px; width: 1000px;">
                  <el-button style="height: 150px;width: 250px; border-radius:30px; font-size: 30px; margin-top: 50px; margin-left: 50px" :plain="true" @click="load1">人身意外险</el-button>
                  <el-button style="height: 150px;width: 250px; border-radius:30px; font-size: 30px; margin-top: 50px; margin-left: 50px" :plain="true" @click="load2">机动车辆险</el-button>
                  <el-button style="height: 150px;width: 250px; border-radius:30px; font-size: 30px; margin-top: 50px; margin-left: 50px" :plain="true" @click="load3">医疗养老险</el-button>
                  <el-button style="height: 150px;width: 250px; border-radius:30px; font-size: 30px; margin-top: 50px; margin-left: 50px" :plain="true" @click="load4">工伤责任险</el-button>
                  <el-button style="height: 150px;width: 250px; border-radius:30px; font-size: 30px; margin-top: 50px; margin-left: 50px" :plain="true" @click="load5">财产保障险</el-button>
                  <a style="margin-left: 800px; color: #409EFF; cursor: pointer" @click="download">点击这里下载模板</a>
              </el-card>
          </div>
      </div>

      <div style="margin-top: 50px">
          <el-table :data="tableData"  :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">
              <el-table-column type="selection" width="55"></el-table-column>
              <el-table-column prop="id" label="ID" width="80" sortable></el-table-column>
              <el-table-column prop="name" label="保险名称"></el-table-column>
              <el-table-column prop="user" label="所属人"></el-table-column>
              <el-table-column prop="" label="文件">
                  <template slot-scope="scope">
                      <div style="color: #cccccc">文件暂不支持预览</div>
                  </template>
              </el-table-column>
              <el-table-column prop="time" label="申请时间"></el-table-column>
              <el-table-column prop="state" label="理赔状态"></el-table-column>
              <el-table-column label="操作"  width="180" align="center">
                  <template slot-scope="scope" v-if="scope.row.user === user.username || user.role === 'ROLE_ADMIN'">
                      <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
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
                      :page-sizes="[2, 5, 10, 20]"
                      :page-size="pageSize"
                      layout="total, sizes, prev, pager, next, jumper"
                      :total="total">
              </el-pagination>
          </div>
      </div>

      <el-dialog title="已购保险" custom-class="dialog" :visible.sync="dialogFormVisible" width="50%" :close-on-click-modal="false">
         <div style="margin-left: 15px;">
              <el-row :gutter="5">
                  <div style="margin-bottom: 20px; margin-left: 500px"><el-button style="border-radius: 30px; height: 50px;width: 150px;font-size: 20px" type="primary" @click="handleAdd">申请理赔</el-button></div>
                  <el-col :span="24" v-for="item in options" :key="item.id" style="margin-bottom: 20px">
                      <el-card style="width: 700px; height: 250px; border-radius: 15px">
                          <div style="float: left; width: 260px;height: 260px">
                              <img :src="item.img" alt="" style="width: 80%;">
                          </div>
                          <div style="float: left;margin-left: 125px;margin-top: 75px" >
                              <span style="font-size: 25px;">{{ item.name }}</span>
                          </div>
                      </el-card>
                  </el-col>
              </el-row>
          </div>
      </el-dialog>

      <el-dialog title="信息" custom-class="dialog" :visible.sync="dialogFormVisible1" width="30%" :close-on-click-modal="false">
          <el-form label-width="100px" size="small" style="width: 90%">
              <el-form-item label="保险名称">
                  <el-select clearable v-model="form.name" placeholder="请选择" style="width: 100%">
                      <el-option v-for="(item , index) in options" :label="item.name" :key="index.name" :value="item.name">
                          {{item.name}}
                      </el-option>
                  </el-select>
              </el-form-item>
              <el-form-item label="理赔文件">
                  <el-upload :action="'http://' + serverIp + ':8088/file/upload'" ref="img" :on-success="handleImgUploadSuccess">
                      <el-button size="small" type="primary">点击上传</el-button>
                  </el-upload>
              </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible1 = false">取 消</el-button>
              <el-button type="primary" @click="save">确 定</el-button>
          </div>
      </el-dialog>
  </div>

</template>

<script>
import {serverIp} from "../../../public/config";

export default {
    name: "Claims",
    data() {
        return {
            options:[],
            serverIp: serverIp,
            tableData: [],
            total: 0,
            pageNum: 1,
            pageSize: 10,
            name: "",
            form: {},
            dialogFormVisible: false,
            dialogFormVisible1: false,
            multipleSelection: [],
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            type: "",
        }
    },
    created() {
        this.load()
    },
    methods: {
        changeState(row, state) {
            this.form = JSON.parse(JSON.stringify(row))
            this.form.state = state;
            this.save();
        },
        load() {
            this.request.get("/claims/page", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    name: this.name,
                }
            }).then(res => {
                this.tableData = res.data.records
                this.total = res.data.total
            })
        },
        save() {
            this.request.post("/claims", this.form).then(res => {
                if (res.code === '200') {
                    this.$message.success("保存成功")
                    this.dialogFormVisible = false
                    this.load()
                } else {
                    this.$message.error("保存失败")
                }
            })
        },
        handleAdd() {
            this.dialogFormVisible1 = true
            this.form = {}
            this.$nextTick(() => {
                if(this.$refs.img) {
                    this.$refs.img.clearFiles();
                }
                if(this.$refs.file) {
                    this.$refs.file.clearFiles();
                }
            })
        },
        handleEdit(row) {
            this.form = JSON.parse(JSON.stringify(row))
            this.dialogFormVisible1 = true
            this.$nextTick(() => {
                if(this.$refs.img) {
                    this.$refs.img.clearFiles();
                }
                if(this.$refs.file) {
                    this.$refs.file.clearFiles();
                }
            })
        },
        del(id) {
            this.request.delete("/claims/" + id).then(res => {
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
            if (!this.multipleSelection.length) {
                this.$message.error("请选择需要删除的数据")
                return
            }
            let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
            this.request.post("/claims/del/batch", ids).then(res => {
                if (res.code === '200') {
                    this.$message.success("批量删除成功")
                    this.load()
                } else {
                    this.$message.error("批量删除失败")
                }
            })
        },
        reset() {
            this.name = ""
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
            this.form.file = res
        },
        handleImgUploadSuccess(res) {
            this.form.img = res
        },
        download() {
            window.open(`http://localhost:8088/file/242201ced2ed469881e4b6af36cdfaac.docx`)
        },

        exp() {
            window.open(`http://${serverIp}:8088/claims/export`)
        },
        handleExcelImportSuccess() {
            this.$message.success("导入成功")
            this.load()
        },
        load1(){
            this.dialogFormVisible = true
            this.request.get("/orders/getOrderInsurance" , {
                params: {
                    username: this.user.nickname,
                    type: "人身意外险"
                }
            }).then(res => {
                this.options = res.data
                console.log(this.type)
            })
        },
        load2(){
            this.dialogFormVisible = true
            this.request.get("/orders/getOrderInsurance" , {
                params: {
                    username: this.user.nickname,
                    type: "机动车辆险"
                }
            }).then(res => {
                this.options = res.data
            })
        },
        load3(){
            this.dialogFormVisible = true
            this.request.get("/orders/getOrderInsurance" , {
                params: {
                    username: this.user.nickname,
                    type: "医疗养老险"
                }
            }).then(res => {
                this.options = res.data
            })
        },
        load4(){
            this.dialogFormVisible = true
            this.request.get("/orders/getOrderInsurance" , {
                params: {
                    username: this.user.nickname,
                    type: "工伤责任险"
                }
            }).then(res => {
                this.options = res.data
            })
        },
        load5(){
            this.dialogFormVisible = true
            this.request.get("/orders/getOrderInsurance" , {
                params: {
                    username: this.user.nickname,
                    type: "财产保障险"
                }
            }).then(res => {
                this.options = res.data
            })
        },
    }
}
</script>


<style>
.dialog{
    border-radius: 10px
}
.card{
    width: 1000px;
    height: 300px;
    border-radius: 15px
}
.headerBg {
    background: #eee!important;
}
</style>
