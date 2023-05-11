<template>
  <div>
      <div style="width: 1200px; margin: 0 0 0 0; margin: auto">
        <el-carousel height="220px" :interval="5000" > <!--加 type="card" 会变成三图叠加两层的样式 -->
          <el-carousel-item v-for="item in imgs1" :key="item">
            <img :src="item" alt="" style="width: 100%">
          </el-carousel-item>
        </el-carousel>
      </div>
      <div style="margin: 50px 0">
          <el-row>
              <el-col :span="12">
                  <div style="margin-top: 30px;margin-left: 20px; width: 520px; height: 400px">
                      <el-carousel height="360px" width="500px" :interval="5000">
                          <el-carousel-item v-for="item in imgs2" :key="item">
                              <img :src="item" alt="" style="width: 100%">
                          </el-carousel-item>
                      </el-carousel>
                  </div>
              </el-col>
              <el-col :span="12">
                  <el-card style="margin: 10px 0; width: 570px; height: 400px">
                      <div style="padding-bottom: 20px; font-size: 20px">系 统 公 告</div>
                      <el-collapse accordion v-model="activeNames" v-for="(item, index) in notice">
                          <el-collapse-item :title="item.name" :name="index + ''">
                              <template slot="title">
                                  <span style="">{{item.time}}</span>
                                  <span style="font-size: 15px; margin-left: 20px">{{item.name}}</span>
                              </template>
  <!--                            <div style="padding: 10px 0; width: 100px;height: 100px"><el-image :src="item.img"></el-image></div>-->
                              <div v-html="item.content"></div>
                          </el-collapse-item>
                      </el-collapse>
                  </el-card>
              </el-col>
          </el-row>
      </div>

      <div class="why-choose-contanir">
          <h2>为什么选择龙保险？</h2>
          <div class="why-choose-main clearfix">
              <div class="why-choose-items">
                  <div class="why-choose-imgbox">
                      <img src="https://www.cpiccdn.com/images/v2/Face1.png" alt="保险专家">
                  </div>
                  <div class="why-choose-textbox">
                      <span>0.2年经验</span>
                      <h4>专注做您的保险专家</h4>
                      <p>自创立以来专注保险主业，目前实现全保险牌照布局</p>
                  </div>
              </div>
              <div class="why-choose-items">
                  <div class="why-choose-imgbox">
                      <img src="https://www.cpiccdn.com/images/v2/Face3.png" alt="满足您所有的保险需求">
                  </div>
                  <div class="why-choose-textbox">
                      <span>完善服务</span>
                      <h4>满足您所有的保险需求</h4>
                      <p>以作为一家负责任的保险公司为使命，坚持以客户需求为导向</p>
                  </div>
              </div>
              <div class="why-choose-items">
                  <div class="why-choose-imgbox">
                      <a href="//one.cpic.com.cn/o2o/module/businessList.html?hit=ShouyeWsmxztbRy&amp;youbu=80wry" target="_blank">
                          <img src="https://www.cpiccdn.com/images/v2/Face.png" alt="">
                      </a>
                  </div>
                  <div class="why-choose-textbox">
                      <span>专业人员</span>
                      <h4>强大的专业能力为您服务</h4>
                      <p>遵循专业化原则，致力于提供一流及高效的客户服务</p>
                  </div>
              </div>
          </div>
      </div>


      <div class="tj3-til"></div>
      <div style="margin-top: 50px">
          <el-row :gutter="5">
              <el-col :span="5" v-for="item in tableData" :key="item.id" style="margin-bottom: 20px; margin-left: 35px">
                  <el-card style="width: 250px; height: 300px">
                      <div style="width: 210px;height: 210px">
                          <img :src="item.img" alt="" @click="open(item)" style="width: 100%;">
                      </div>
                      <div style="margin-top: 20px" >
                          <span style="font-size: 18px; float: left">{{ item.name }}</span>
                          <span style="font-size: 18px; color: red; float: right">￥{{ item.price }}</span>
                      </div>
                  </el-card>
              </el-col>
          </el-row>
      </div>

      <el-dialog title="保险详情" custom-class="dialog" :visible.sync="dialogFormVisible" width="50%" :close-on-click-modal="false">
          <div style="margin-left: 5px">
              <el-tabs v-model="activeName" @tab-click="handleClick">
                  <el-tab-pane label="保险详情" name="first">
                      <div style="margin-top: 10px; margin-bottom: 10px">
                          {{ form.description }}
                      </div>
                      <hr style="background-color: rgba(196,196,196,0.33); border:none; height: 1px">
                      <div style="height: 20px; margin-top: 5px; margin-bottom: 5px">
                          <a style="float: left">保险期限</a>
                          <div style="margin-right: 20px;float: right">
                              {{ form.time }}
                          </div>
                      </div>
                      <hr style="background-color: rgba(196,196,196,0.33); border:none; height: 1px">
                      <div style="height: 20px; margin-top: 5px; margin-bottom: 5px">
                          <a style="margin-top: 10px">保险费用</a>
                          <div style="margin-right: 20px;float: right">
                              {{ form.price }}元
                          </div>
                      </div>
                  </el-tab-pane>
                  <el-tab-pane label="保险特色" name="second">
                      <img :src="form.characteristic" alt="" style="width: 100%;">
                  </el-tab-pane>
                  <el-tab-pane label="理赔流程" name="third">
                      <img :src="form.claims" alt="" style="width: 100%;">
                  </el-tab-pane>
              </el-tabs>
          </div>
          <div style="margin-top: 20px;margin-left: 600px"><el-button style="border-radius: 25px; height: 50px;width: 100px;font-size: 20px" type="primary" @click="buyInsurance(form.id)">购买</el-button></div>
      </el-dialog>
  </div>
</template>

<script>
import router, {setRoutes} from "@/router";

export default {
  name: "FrontHome",
  data(){
    return{
      activeNames: ['-1'],
      imgs1: [
          'https://www.chinalife.com.cn/chinalife/resource/cms/2023/04/img_pc_site/2023041215482547911.jpg',
          'https://www.chinalife.com.cn/chinalife/resource/cms/2022/10/2022102712554748465.jpg',
          //require('../../assets/img1.png'),
      ],
      imgs2: [
          'https://tse1-mm.cn.bing.net/th/id/OIP-C.n0JA6GN4skcrkYfsBu4jsgHaEK?w=295&h=180&c=7&r=0&o=5&dpr=1.3&pid=1.7',
          'https://tse2-mm.cn.bing.net/th/id/OIP-C.KMh0t8-Q4H8QVD3lBXjKHgHaE8?pid=ImgDet&rs=1',
          //require('../../assets/img1.png'),
      ],
      notice: [],
      content: '',
      tableData: [],
        dialogFormVisible: false,
        name: '',
        pageNum: 1,
        pageSize: 4,
        price: 0,
        types: '',
        activeName: 'first',
        form: [],

    }
  },
  created() {
      this.request.get("/notice").then(res =>{
          this.notice = res.data.splice(0, 5)
      })
      this.load()
  },
  methods: {
      load() {
          this.request.get("/insurance/page1", {
              params: {
                  pageNum: this.pageNum,
                  pageSize: this.pageSize,
                  name: this.name,
                  price: this.price,
                  types: this.types,
              }
          }).then(res => {
              this.tableData = res.data.records
              console.log(this.tableData)
          })
      },
      open(item){
          this.dialogFormVisible = true
          this.form = item
      },
      handleClick(){
      },

  }
}
</script>

<style scoped>
.why-choose-contanir {
    width: 1148px;
    margin: 0 auto;
    overflow: hidden;
    padding-bottom: 60px;
}
.why-choose-contanir h2 {
    margin-top: 60px;
    font-size: 35px;
    text-align: center;
    margin-bottom: 80px;
    color: #303234;
    font-weight: 500;
    letter-spacing: 2px;
    line-height: 50px;
}
.why-choose-imgbox {
    width: 120px;
    height: 120px;
    margin: 0 auto 52px;
}
.why-choose-imgbox img {
    height: 100%;
    width: 100%;
    border-radius: 50%;
}
.why-choose-textbox {
    width: 280px;
    text-align: center;
    margin: 0 auto;
}
.why-choose-textbox span {
    font-size: 14px;
    opacity: .8;
    color: #7a7a7a;
    letter-spacing: 1.75px;
}
.why-choose-textbox h4 {
    margin-top: 10px;
    font-size: 16px;
    font-weight: 500;
    color: #303234;
    letter-spacing: 2px;
}
.why-choose-textbox p {
    margin-top: 14px;
    font-size: 16px;
    color: #494b4e;
    line-height: 26px;
    letter-spacing: 2px;
}
.why-choose-contanir .why-choose-items {
    width: 33.33%;
    float: left;
}
.clearfix:after, .layout:after, .clf:after {
    visibility: hidden;
    display: block;
    font-size: 0;
    content: '.';
    clear: both;
    height: 0;
}

.clearfix:after {
    visibility: hidden;
    display: block;
    font-size: 0;
    content: '.';
    clear: both;
    height: 0;
}
.clearfix:after {
    visibility: hidden;
    display: block;
    font-size: 0;
    content: '.';
    clear: both;
    height: 0;
}
.clearfix:after {
    content: "";
    display: block;
    height: 0;
    clear: both;
}
.tj3-til {
    width: 499px;
    height: 38px;
    margin: 50px auto;
    background: url(./img/tj3Til.png) center center/100% 100% no-repeat;
}
</style>