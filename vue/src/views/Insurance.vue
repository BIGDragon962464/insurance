<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px"  placeholder="请输入保险类型" suffix-icon="el-icon-search" v-model="types"></el-input>
      <el-input class="ml-5" style="width: 200px"  placeholder="请输入保险名称" suffix-icon="el-icon-info" v-model="name"></el-input>
      <el-input class="ml-5" style="width: 200px"  placeholder="价格" suffix-icon="el-icon-position" v-model="price"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-upload action="http://localhost:8088/file/upload"
                 :show-file-list="false"
                 :on-success="handleFileUploadSuccess"
                 style="display: inline-block"
      >
        <el-button type="primary" class="ml-5">上传文件 <i class="el-icon-top"></i></el-button>
      </el-upload>
      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定批量删除这些数据吗？"
          @confirm="delBatch"
      >
        <el-button type="danger" slot="reference">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="name" label="保险名称"></el-table-column>
      <el-table-column prop="price" label="保险价格"></el-table-column>
      <el-table-column prop="img" label="保险图片"></el-table-column>
      <el-table-column prop="types" label="保险类型" :filters="[{ text: '人身意外险', value: '人身意外险' },
                       { text: '机动车辆险', value: '机动车辆险' },
                       { text: '医疗养老险', value: '医疗养老险' },
                       { text: '工商责任险', value: '工商责任险' },]"
                       :filter-method="filterTag"
                       filter-placement="bottom-end"

      ></el-table-column>
      <el-table-column label="操作"  width="200" align="center">
        <template slot-scope="scope">
          <el-button type="success"><i class="el-icon-edit"></i>编辑</el-button>
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
export default {
  name: "File",
  data() {
    return {
      tableData: [],
      name: '',
      types: '',
      price: null,
      multipleSelection: [],
      pageNum: 1,
      pageSize: 8,
      total: 0
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
          types: this.types,
          price: this.price,
        }
      }).then(res => {

        this.tableData = res.data.records
        this.total = res.data.total

      })
    },
    /*beforeAvatarUpload(res) {
      const isLt2M = res.size / 1024 / 1024 < 2;
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isLt2M;
    },*/
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
    filterTag(value, row) {
      return row.tag === value;
    },
  }
}
</script>

<style scoped>

</style>
