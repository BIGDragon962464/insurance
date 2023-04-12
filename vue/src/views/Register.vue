<template>
  <div class="wrapper">
    <div style="margin: 175px auto; background-color: rgba(255,255,255,0.83); width: 350px; height: 350px; padding: 20px; border-radius: 20px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>注 册</b></div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input placeholder="请输入账号" size="medium" style="margin: 5px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input placeholder="请输入密码" size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input placeholder="请确认密码" size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="user.confirmPassword"></el-input>
        </el-form-item>
        <el-form-item style="margin: 10px 0; text-align: right">
          <el-button type="primary" size="small"  autocomplete="off" @click="register">注册</el-button>
          <el-button type="warning" size="small"  autocomplete="off" @click="$router.push('/login')">返回登陆</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>

<!--    <div class="box">
        <div class="content">
            <div class="login-wrapper">
                <h1>注册</h1>
                <div class="login-form">
                    <el-form :model="user" :rules="rules" ref="userForm">
                        <div class="username form-item">
                            <span>用户名</span>
                            <input type="text" class="input-item" v-model="user.username">
                        </div>
                        <div class="password form-item">
                            <span>密码</span>
                            <input type="password" class="input-item" v-model="user.password">
                        </div>
                        <div class="password form-item">
                            <span>确认密码</span>
                            <input type="password" class="input-item" v-model="user.confirmPassword">
                        </div>
                        <el-button class="login-btn" @click="register">注册并登录</el-button>
                        <a style="cursor:pointer; font-size: 13px; margin-left: 320px" @click="$router.push('/login')">返回登录</a>
                    </el-form>
                </div>
            </div>
        </div>
    </div>-->
</template>

<script>
import {setRoutes} from "@/router";

export default {
  name: "Login",
  data() {
    return {
      user: {},
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请确认密码', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ],
      }
    }
  },
  methods: {
    register() {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {  // 表单校验合法
          if (this.user.password !== this.user.confirmPassword){
            this.$message.error("两次输入密码不一致！")
            return false
          }
          this.request.post("/user/register", this.user).then(res => {
            if(res.code === '200') {
              this.$message.success("注册成功！")
              this.login()
            } else {
              this.$message.error(res.msg)
            }
          })
        } else {
          return false;
        }
      });
    },
  login() {
      this.$refs['userForm'].validate((valid) => {
          if (valid) {  // 表单校验合法
              this.request.post("/user/login", this.user).then(res => {
                  if(res.code === '200') {
                      localStorage.setItem("user",JSON.stringify(res.data)) //存储用户信息到浏览器
                      localStorage.setItem("menus",JSON.stringify(res.data.menus))
                      //动态设置当前用户路由
                      setRoutes()
                      this.$message.success("登录成功！")

                      /*if (res.data.role === 'ROLE_USER') {
                        this.$router.push("/front/home")
                      }else {
                        this.$router.push("/")
                      }*/
                      this.$router.push("/")
                  } else {
                      this.$message.error(res.msg)
                  }
              })
          } else {
              return false;
          }
      });
  }
  }
}
</script>

<style >
.wrapper {
  background: url("~@/assets/background.jpg");
  width:100%;
  height:100%;
  position:fixed;
  background-size:100% 120%;
}
</style>
