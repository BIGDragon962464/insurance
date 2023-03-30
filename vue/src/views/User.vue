<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px"  placeholder="请输入用户名" suffix-icon="el-icon-search" v-model="username"></el-input>
      <el-input class="ml-5" style="width: 200px"  placeholder="请输入昵称" suffix-icon="el-icon-info" v-model="nickname"></el-input>
      <el-input class="ml-5" style="width: 200px"  placeholder="请输入地址" suffix-icon="el-icon-position" v-model="address"></el-input>
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
      <el-upload action="http://localhost:8088/user/import" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block">
        <el-button type="primary" class="ml-5">导入 <i class="el-icon-bottom"></i></el-button>
      </el-upload>


      <el-button type="primary" @click="exp" class="ml-5">导出 <i class="el-icon-top"></i></el-button>

    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="username" label="用户名" width="140"></el-table-column>
      <el-table-column prop="role" label="角色">
        <template slot-scope="scope">
          <el-tag type="primary" v-if="scope.row.role === 'ROLE_ADMIN'">管理员</el-tag>
          <el-tag type="success" v-if="scope.row.role === 'ROLE_USER'">用户</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="nickname" label="姓名" width="120"></el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>
      <el-table-column prop="phone" label="电话"></el-table-column>
      <el-table-column prop="address" label="地址"></el-table-column>
      <el-table-column label="操作" width="300" align="center">
        <template v-slot="scope">
<!--          <el-button type="primary" @click="lookInsurance(scope.row.insurance)" ><i class="el-icon-document"></i> 查看保险</el-button>-->
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

    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px" size="small">
        <el-form-item label="用户名">
          <el-input v-model="form.username" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="角色">
          <el-select clearable v-model="form.role" placeholder="请选择角色" style="width: 100%">
            <el-option v-for="item in roles" :key="item.name" :label="item.name" :value="item.flag"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.phone" auto-complete="off"></el-input>
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


<!--    <el-dialog title="购物车" :visible.sync="vis" width="50%">
      <el-table :data="insurance" border stripe>
        <el-table-column prop="name" label="保险名称"></el-table-column>
        <el-table-column prop="price" label="保险价格"></el-table-column>
        <el-table-column prop="img" label="保险图片"></el-table-column>
        <el-table-column prop="types" label="保险类型"></el-table-column>
        <el-table-column prop="description" label="保险描述"></el-table-column>
      </el-table>
    </el-dialog>-->
  </div>
</template>

<script>
export default {
  name: "User",
  data(){
    return{
      username: "",
      nickname: "",
      address: "",
      form:{},
      dialogFormVisible: false,
      multipleSelection: [],
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 8,
      roles: [],
      /*insurance: [],
      vis: false,*/
    }
  },
  created() {
    this.load()
  },
  methods: {
    /*lookInsurance(insurance){
      console.log(insurance)
      this.insurance = insurance
      this.vis = true
    },*/
    load(){
      //请求分页查询
      /*fetch("http://localhost:8088/user/page?pageNum= "+ this.pageNum+"&pageSize=" + this.pageSize + "&username=" + this.username)
          .then(res =>res.json()).then(res => {
        console.log(res)
        this.tableData = res.data
        this.total = res.total
      })*/
      this.request.get("/user/page", {
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username,
          nickname: this.nickname,
          address: this.address
        }
      }).then(res => {
        console.log(res.data)

        this.tableData = res.data.records
        this.total = res.data.total
      })

      this.request.get("/role").then(res => {
        this.roles=res.data
      })

    },

    save(){
      this.request.post("/user",this.form).then(res => {
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
      this.form = {}
    },
    handleEdit(row){
      //this.form = row
      this.form = Object.assign({},row) // 将row拷贝到空对象中 解决没点确定数据改变的问题
      this.dialogFormVisible = true
    },
    del(id){
      this.request.delete("/user/" + id).then(res => {
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
      this.request.post("/user/del/batch",ids).then(res => {
        if (res.code === '200' ){
          this.$message.success("批量删除成功！")
          this.load()
        }else {
          this.$message.error("删除失败")
        }
      })
    },

    handleExcelImportSuccess() {
      this.$message.success("导入成功")
      this.load()
    },

    exp() {
      window.open("http://localhost:8088/user/export")
    },

    reset(){
      this.username = ""
      this.nickname = ""
      this.address = ""
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
  }
}
</script>

<style>
.headerBg {
  background: #efefef !important;
}
</style>