import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './assets/gloable.css';
import request from "@/utils/request";
// px2rem 自适应
import 'lib-flexible'

Vue.config.productionTip = false

Vue.use(ElementUI,{size:"mini"});

Vue.prototype.request = request

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

