<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px"  placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
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
<!--      <el-upload action="http://localhost:8088/user/import" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block">
        <el-button type="primary" class="ml-5">导入 <i class="el-icon-bottom"></i></el-button>
      </el-upload>


      <el-button type="primary" @click="exp" class="ml-5">导出 <i class="el-icon-top"></i></el-button>-->

    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="150"></el-table-column>
      <el-table-column prop="name" label="名称" width="200"></el-table-column>
      <el-table-column prop="flag" label="唯一标识" width="200">
        <template slot-scope="scope">
          <el-tag type="primary" v-if="scope.row.flag === 'ROLE_ADMIN'">管理员</el-tag>
          <el-tag type="success" v-if="scope.row.flag === 'ROLE_USER'">用户</el-tag>
          <el-tag type="danger" v-if="scope.row.flag === 'ROLE_AUDIT'">审核</el-tag>
          <el-tag type="warning" v-if="scope.row.flag === 'ROLE_SALES'">销售</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="description" label="描述" width="400"></el-table-column>
      <el-table-column label="操作" align="center">
        <template v-slot="scope">
          <el-button type="info" @click="selectMenu(scope.row)"><i class="el-icon-menu"> 分配菜单</i></el-button>
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
<!--    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>-->

    <el-dialog title="角色信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px" size="small">
        <el-form-item label="名称">
          <el-input v-model="form.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="唯一标识">
          <el-input v-model="form.flag" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="菜单分配" :visible.sync="menuDialogVisible" width="30%">
      <el-tree
          :props="props"
          :data="menuData"
          ref="tree"
          show-checkbox
          node-key="id"
          :default-expanded-keys="expends"
          :default-checked-keys="checks">
        <span class="custom-tree-node" slot-scope="{ node, data }">
          <span><i :class="data.icon"></i> {{ data.name }}</span>
        </span>
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="menuDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveRoleMenu">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "Role",
  data(){
    return{
      name: "",
      form:{},
      dialogFormVisible: false,
      menuDialogVisible: false,
      multipleSelection: [],
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      menuData: [],
      props: {
        label: 'name',
      },
      expends: [],
      checks: [],
      roleId: 0,
      roleFlag: '',
    }
  },
  created() {
    this.load()
  },
  methods: {
    load(){
      this.request.get("/role/page", {
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
      this.request.post("/role",this.form).then(res => {
        if (res.code === '200'){
          this.$message.success("保存成功！")
          this.dialogFormVisible = false
          this.load()
        }else {
          this.$message.error("保存失败")
        }
      })
    },
    saveRoleMenu(){
      this.request.post("/role/roleMenu/" + this.roleId, this.$refs.tree.getCheckedKeys()).then(res => {
        if (res.code === '200'){
          this.$message.success("绑定成功")
          this.menuDialogVisible = false

          //操作管理员角色后需要重新登录
          if (this.roleFlag === 'ROLE_ADMIN'){
            this.$store.commit("logout")
          }

        }else {
          this.$message.error(res.message)
        }
      })
      /*console.log(this.$refs.tree.getCheckedKeys())*/
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
      this.request.delete("/role/" + id).then(res => {
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
      this.request.post("/role/del/batch",ids).then(res => {
        if (res.code === '200'){
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
    selectMenu(role){
      this.menuDialogVisible = true
      this.roleId = role.id
      this.roleFlag = role.flag

      //请求菜单数据
      this.request.get("/menu").then(res => {
        this.menuData = res.data

        //把后台返回的菜单数据处理成id数组
        this.expends = this.menuData.map(v => v.id)
      })

      this.request.get("/role/roleMenu/" + role.id).then(res => {
        this.checks = res.data

        this.request.get("/menu/ids").then(r => {
          //先渲染弹窗里的元素
          this.menuDialogVisible = true;

          const ids = r.data
          ids.forEach(id => {
            if (!this.checks.includes(id)) {
              //this.$refs.tree.setChecked(id,false)
              this.$refs.tree.getCheckedKeys().concat(this.$refs.tree.getHalfCheckedKeys())
            }
          })
        })
      })
    },

  }
}
</script>

<style>
.headerBg {
  background: #efefef !important;
}
</style>