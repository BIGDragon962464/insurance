<template>
  <!--  登录样式1  -->
     <div class="wrapper" >
         <div style="margin: 175px auto; background: white; width: 400px;height: 300px; border-radius: 10px; overflow: hidden">
             <el-tabs type="card" v-model="activeName" >
                 <el-tab-pane label="账号密码登录" name="first">
                     <div style="margin: 10px auto; background-color: #fff; width: 300px; height: 350px; padding: 20px; border-radius: 10px">
                         <el-form :model="user" :rules="rules" ref="userForm">
                             <el-form-item prop="username">
                                 <el-input size="medium" style="margin: 5px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
                             </el-form-item>
                             <el-form-item prop="password">
                                 <el-input size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
                             </el-form-item>
<!--                             <el-form-item>
                                 <div style="display: flex">
                                     <el-input size="medium" v-model="code" style="width: 200px"></el-input>
                                     <span @click="refreshCode" style="cursor: pointer; flex: 1">
                                         <identify :identifyCode="identifyCode"></identify>
                                     </span>
                                 </div>
                             </el-form-item>-->
                             <el-form-item style="margin: 10px 0; text-align: right">
                                 <el-button type="warning" size="small"  autocomplete="off" @click="$router.push('/register')">注册</el-button>
                                 <el-button type="primary" size="small"  autocomplete="off" @click="login">登录</el-button>
                             </el-form-item>
                             <el-form-item>
                                 <el-button type="text" size="small" style="margin-left: 200px" autocomplete="off" @click="handlePass">找回密码</el-button>
                             </el-form-item>
                         </el-form>
                     </div>
                 </el-tab-pane>
                 <el-tab-pane label="邮箱登录" name="second">
                     <div style="margin: 10px auto; background-color: #fff; width: 300px; height: 300px; padding: 20px; border-radius: 10px">
                         <el-form :model="user" :rules="rules" ref="userForm">
                             <el-form-item prop="email">
                                 <el-input size="medium" style="margin: 5px 0" prefix-icon="el-icon-message" v-model="user.email"></el-input>
                             </el-form-item>
                             <el-form-item prop="code">
                                 <el-input size="small" style="margin: 5px 0; width: 163px" prefix-icon="el-icon-lock" v-model="user.code"></el-input>
                                 <el-button type="primary" size="small" class="ml-5" @click="sendEmailCode(1)">获取验证码</el-button>
                             </el-form-item>
                             <el-form-item style="margin: 10px 0; text-align: right">
                                 <el-button type="primary" size="small"  autocomplete="off" @click="loginEmail">登录</el-button>
                             </el-form-item>
                             <el-form-item>
                                 <el-button type="text" size="small" style="margin-left: 200px" autocomplete="off" @click="handlePass">找回密码</el-button>
                             </el-form-item>
                         </el-form>
                     </div>
                 </el-tab-pane>
             </el-tabs>
         </div>

         <el-dialog title="找回密码" :visible.sync="dialogFormVisible" width="30%" style="border-radius: 20px" append-to-body>
             <el-form label-width="100px" size="small">
                 <el-form-item label="邮箱">
                     <el-input size="medium" style="width: 295px" v-model="pass.email" auto-complete="off"></el-input>
                 </el-form-item>
                 <el-form-item label="验证码">
                     <el-input size="medium" style="width: 180px" v-model="pass.code"></el-input>
                     <el-button type="primary" size="medium" class="ml-5" @click="sendEmailCode(2)">获取验证码</el-button>
                 </el-form-item>
             </el-form>
             <div slot="footer" class="dialog-footer">
                 <el-button @click="dialogFormVisible = false">取 消</el-button>
                 <el-button type="primary" @click="passwordBack">重置密码</el-button>
             </div>
         </el-dialog>
     </div>
</template>

<script>
import router, {setRoutes} from "@/router";
import identify from '../components/identify.vue'

export default {
  name: "Login",
  components: { identify },
  data() {
    return {
      activeName: 'first',
      user: {},
      code:'',
      //图片验证码
      identifyCode: '',
      //验证码规则
      identifyCodes: '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ',
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
        ],
      },
      dialogFormVisible: false,
      pass: {},
    }
  },
  /*mounted(){
      this.identifyCode = ''
      this.makeCode(this.identifyCodes, 4)
  },*/
  methods: {
    router() {
      return router
    },
    login() {
      /*if (this.code !== this.identifyCode.toLowerCase()){
          this.$message({
              type: "error",
              message: "验证码错误"
          })
          return;
      }*/
      this.$refs['userForm'].validate((valid) => {
        if (valid) {  // 表单校验合法
          this.request.post("/user/login", this.user).then(res => {
            if(res.code === '200') {
              localStorage.setItem("user",JSON.stringify(res.data)) //存储用户信息到浏览器
              localStorage.setItem("menus",JSON.stringify(res.data.menus))
              //动态设置当前用户路由
              setRoutes()
              this.$message.success("登录成功！")

              if (res.data.role === 'ROLE_USER') {
                this.$router.push("/front/home")
              }else {
                this.$router.push("/")
              }
            } else {
              this.$message.error(res.msg)
            }
          })
        } else {
          return false;
        }
      });
    },
    loginEmail() {
      if (!this.user.email){
          this.$message.warning("请输入邮箱")
          return
      }
      if (!this.user.code){
          this.$message.warning("请输入验证码")
          return;
      }
      this.request.post("/user/loginEmail", this.user).then(res => {
        if(res.code === '200') {
            localStorage.setItem("user",JSON.stringify(res.data)) //存储用户信息到浏览器
            localStorage.setItem("menus",JSON.stringify(res.data.menus))
            //动态设置当前用户路由
            setRoutes()
            this.$router.push("/")
            this.$message.success("登录成功！")
        } else {
            this.$message.error(res.msg)
        }
      })
    },
    sendEmailCode(type){
      let email;
      if (type === 1){
          email = this.user.email
      }else if (type === 2){
          email = this.pass.email
      }
      if (!email){
          this.$message.warning("请输入邮箱")
          return
      }
      if (!/^\w+((.\w+)|(-\w+))@[A-Za-z0-9]+((.|-)[A-Za-z0-9]+).[A-Za-z0-9]+$/.test(email)){
          this.$message.warning("请输入正确的邮箱格式")
          return
      }
      this.request.get("/user/email/"  + email + "/" + type).then(res => {
          if (res.code === '200'){
              this.$message.success("发送成功")
          }else {
              this.$message.error(res.msg)
          }
      })
    },
    refreshCode () {
        this.identifyCode = ''
        this.makeCode(this.identifyCodes, 4)
    },
    makeCode (data, len) {
        for (let i = 0; i < len; i++) {
            this.identifyCode += data[this.randomNum(0, data.length - 1)]
        }
    },
    randomNum (min, max) {
        max = max + 1
        return Math.floor(Math.random() * (max - min) + min)
    },
    handlePass(){
      this.dialogFormVisible = true;
      this.pass = {}
    },
    passwordBack(){
      this.request.put("/user/reset", this.pass).then(res => {
          if (res.code === '200'){
              this.$message.success("重置密码成功")
              this.dialogFormVisible = false
          }else {
              this.$message.error(res.msg)
          }
      })
    },
  },
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
