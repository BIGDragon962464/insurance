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
                         </el-form>
                     </div>
                 </el-tab-pane>
                 <el-tab-pane label="邮箱登录" name="second">
                     <div style="margin: 10px auto; background-color: #fff; width: 300px; height: 300px; padding: 20px; border-radius: 10px">
                         <el-form :model="user" :rules="rules1" ref="userForm">
                             <el-form-item prop="email">
                                 <el-input size="medium" style="margin: 5px 0" prefix-icon="el-icon-message" v-model="user.email"></el-input>
                             </el-form-item>
                             <el-form-item prop="code">
                                 <el-input size="medium" style="margin: 5px 0; width: 163px" prefix-icon="el-icon-lock" v-model="user.code"></el-input>
                                 <el-button type="primary" size="small" class="ml-5" @click="getEmailCode">获取验证码</el-button>
                             </el-form-item>
                             <el-form-item style="margin: 10px 0; text-align: right">
                                 <el-button type="primary" size="small"  autocomplete="off" @click="loginEmail">登录</el-button>
                             </el-form-item>
                         </el-form>
                     </div>
                 </el-tab-pane>
             </el-tabs>
         </div>
     </div>
<!--  登录样式2  -->
<!--    <div class="box">
        <div class="content">
            <div class="login-wrapper">
                <h1>登录</h1>
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
                        <el-button class="login-btn" @click="login">登 录</el-button>
                        <a style="cursor:pointer; font-size: 13px; margin-left: 300px" @click="$router.push('/register')">没有账号？</a>
                    </el-form>
                </div>
                <div class="divider">
                    <span class="line"></span>
                    <span class="divider-text">其他方式登录</span>
                    <span class="line"></span>
                </div>
                <div class="other-login-wrapper">
                        &lt;!&ndash;qq、微信登录&ndash;&gt;
&lt;!&ndash;                    <div class="other-login-item">
                        <img src="../assets/QQ.png" alt="">
                    </div>
                    <div class="other-login-item">
                        <img src="../assets/WeChat.png" alt="">
                    </div>&ndash;&gt;
                </div>
            </div>
        </div>
    </div>-->
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
        email: [
            { required: true, message: '请输入邮箱', trigger: 'blur' },
            { min: 1, max: 30, message: '长度在30个字符之内', trigger: 'blur' }
        ],
        code: [
            { min: 4, max: 4, message: '长度为4位', trigger: 'blur' }
        ],
      },
    rules1: {

    }
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
    getEmailCode(){
        if (!this.user.email){
            this.$message.warning("请输入邮箱")
            return
        }
        if (!/^\w+((.\w+)|(-\w+))@[A-Za-z0-9]+((.|-)[A-Za-z0-9]+).[A-Za-z0-9]+$/.test(this.user.email)){
            this.$message.warning("请输入正确的邮箱格式")
            return
        }
        this.request.get("/user/email/"  + this.user.email).then(res => {
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
