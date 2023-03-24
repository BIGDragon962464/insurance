import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store";

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue')
  },
  {
    path: '*',
    name: '404',
    component: () => import('../views/404.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

//刷新页面会导致重置路由
export const setRouters = () => {
  const storeMenus = localStorage.getItem("menus");
  if (storeMenus){
    //瓶装动态路由
    const manageRoute ={ path: '/', name: 'Manage', component: () => import('../views/Manage.vue'), redirect: "/home", children: [] }
    const menus = JSON.parse(storeMenus)
    menus.forEach(item => {
      if (item.path){ //当且仅当path不为空时才去设置路由
        let itemMenu = {path: item.path.replace("/", ""), name: item.name, component: () => import('../views' + item.pagePath + '.vue')}
        manageRoute.children.push(itemMenu)
      } else if (item.children.length){
        item.children.forEach(item => {
          if (item.path){
            let itemMenu = {path: item.path.replace("/", ""), name: item.name, component: () => import('../views' + item.pagePath + '.vue')}
            manageRoute.children.push(itemMenu)
          }
          })
        }
      })
      //获取当前的路由对象名称数组
      const currentRoutes = router.getRoutes().map(v => v.name)
      if (!currentRoutes.includes('Manage')){
      //动态路由添加到现在的路由对象中去
      router.addRoute(manageRoute)
      }
  }
}

router.addRoute({
  path: '/',
  component: () => import('../views/Manage.vue'),
  redirect: "/home",
  children:  [
    {path: 'home', name: '首页', component: () => import('../views/Home.vue')},
    {path: 'user', name: '用户管理', component: () => import('../views/User.vue')},
    {path: 'role', name: '角色管理', component: () => import('../views/Role.vue')},
    {path: 'menu', name: '菜单管理', component: () => import('../views/Menu.vue')},
    {path: 'person', name: '个人信息', component: () => import('../views/Person.vue')},
    {path: 'file', name: '文件管理', component: () => import('../views/File.vue')}
  ]
},)

// 路由守卫
router.beforeEach((to, from, next) => {
  localStorage.setItem("currentPathName", to.name)  // 设置当前的路由名称，为了在Header组件中去使用
  store.commit("setPath")  // 触发store的数据更新
  next()  // 放行路由
})
export default router
