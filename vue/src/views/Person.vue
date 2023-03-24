<template>
  <div>
<!--    <el-card style="width: 500px;" >
      <el-form label-width="80px" size="small" >
        <el-upload
            class="avatar-uploader"
            action="http://localhost:8088/file/upload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
        >
          <img v-if="form.avatarUrl" :src="form.avatarUrl" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>

        <el-form-item label="用户名">
          <el-input v-model="form.username" disabled autocomplete="off" style="width: 350px"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" autocomplete="off" style="width: 350px"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" autocomplete="off" style="width: 350px"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.phone" autocomplete="off" style="width: 350px"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input type="textarea" v-model="form.address" autocomplete="off" style="width: 350px"></el-input>
        </el-form-item>
        <el-form-item style="text-align: center">
          <el-button type="primary" @click="save" >确 定</el-button>
        </el-form-item>
      </el-form>
    </el-card>-->
    <el-row >
        <el-row  style="margin-top:10px; margin-left: 100px">
          <el-col :span="12">
            <el-card style="width: 500px;">
              <el-form label-width="80px" size="small" style="margin-top: 10px">
                <el-upload
                    class="avatar-uploader"
                    action="http://localhost:8088/file/upload"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                >
                  <img v-if="form.avatarUrl" :src="form.avatarUrl" class="avatar">
                  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>

                <el-form-item label="用户名">
                  <el-input v-model="form.username" disabled autocomplete="off" style="width: 350px"></el-input>
                </el-form-item>
                <el-form-item label="昵称">
                  <el-input v-model="form.nickname" autocomplete="off" style="width: 350px"></el-input>
                </el-form-item>
                <el-form-item label="邮箱">
                  <el-input v-model="form.email" autocomplete="off" style="width: 350px"></el-input>
                </el-form-item>
                <el-form-item label="电话">
                  <el-input v-model="form.phone" autocomplete="off" style="width: 350px"></el-input>
                </el-form-item>
                <el-form-item label="地址">
                  <el-input type="textarea" v-model="form.address" autocomplete="off" style="width: 350px"></el-input>
                </el-form-item>
                <div style="text-align: center">
                  <el-button type="primary" @click="save" >确 定</el-button>
                </div>
              </el-form>
            </el-card>
          </el-col>
          <el-col :span="12">
            <el-card style="width: 500px;">
              <div  style="text-align: center; margin-top: 10px">
                <h1>修改密码</h1>
              </div>
              <el-form label-width="80px" size="small" :model="form" :rules="rules" ref="pass" style="margin-top: 20px; margin-bottom: 20px">
                <el-form-item label="旧密码" prop="password">
                  <el-input  v-model="form.password" autocomplete="off" show-password style="width: 350px;"></el-input>
                </el-form-item>
                <el-form-item label="新密码" prop="password">
                  <el-input v-model="form.newPassword" autocomplete="off" show-password style="width: 350px"></el-input>
                </el-form-item>
                <el-form-item label="确认密码" prop="password">
                  <el-input v-model="form.confirmPassword" autocomplete="off" show-password style="width: 350px"></el-input>
                </el-form-item>
                <div style="text-align: center">
                  <el-button type="primary" @click="savePassword" >确 定</el-button>
                </div>
              </el-form>
            </el-card></el-col>
        </el-row>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "Person",
  data() {
    return {
      form: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      rules:{
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请确认密码', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ],
      },
    }
  },
  created() {
    this.getUser().then(res => {
      this.form = res
    })

    this.form.username = this.user.username
  },
  methods: {
    async getUser() {
      return (await this.request.get("/user/username/" + this.user.username)).data
    },
    save() {
      this.request.post("/user", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("保存成功")
          //触发父级更新User的方法

          this.$emit("refreshUser")

        } else {
          this.$message.error("保存失败")
        }
      })
    },
    handleAvatarSuccess(res) {
      this.form.avatarUrl = res
    },
    savePassword(){
      this.$refs.pass.validate((vaild) => {
        if (vaild) {
          if(this.form.newPassword !== this.form.confirmPassword){
            this.$message.error("两次密码输入不一致！")
            return false
          }
          this.request.post("/user/password", this.form).then(res => {
            if (res.code === '200') {
              this.$message.success("修改成功")
              this.$store.commit("logout")
            }else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    }
  }
}
</script>

<style>
.avatar-uploader {
  text-align: center;
  padding-bottom: 10px;
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
  font-size: 28px;
  color: #8c939d;
  width: 138px;
  height: 138px;
  line-height: 138px;
  text-align: center;
}
.avatar {
  width: 138px;
  height: 138px;
  display: block;
}
</style>
