import Vue from 'vue'
import Router from 'vue-router'
const originalPush = Router.prototype.push
// 解决路由同一个进入同一个
// Router.prototype.push = function push(location) {
// 	return originalPush.call(this, location).catch(err => err)
// }
const errorPage = r => require.ensure([], () => r(require('@/components/errorPage')))
const index = r => require.ensure([], () => r(require('@/components/index')))
const login = r => require.ensure([], () => r(require('@/components/login')))
const orders = r => require.ensure([], () => r(require('@/components/orders')))
const record = r => require.ensure([], () => r(require('@/components/record')))
const energy = r => require.ensure([], () => r(require('@/components/energy')))
const my = r => require.ensure([], () => r(require('@/components/my')))
const appxz = r => require.ensure([], () => r(require('@/components/appxz')))
const apiDoc = r => require.ensure([], () => r(require('@/components/apiDoc')))



Vue.use(Router)
const router = new Router({
  mode: 'history',
  base: '/',
  routes: [
    {
      path: '*',
      component: errorPage
    },
    //主页
    {
      path: '/',
      name: 'index',
      component: index,
      meta: {
        title: "",
        keepAlive: false,
      }
    },
    {
      path: '/login',
      name: 'login',
      component: login,
      meta: {
        title: "",
        keepAlive: false,
      }
    },
    //租用记录
    {
      path: '/record',
      name: 'record',
      component: record,
      meta: {
        title: "",
        keepAlive: false,
      }
    },

    //租用能量
    {
      path: '/energy',
      name: 'energy',
      component: energy,
      meta: {
        title: "",
        keepAlive: false,
      }
    },
    //记录列表
    {
      path: '/orders',
      name: 'orders',
      component: orders,
      meta: {
        title: "",
        keepAlive: false,
      }
    },
    //个人中心
    {
      path: '/my',
      name: 'my',
      component: my,
      meta: {
        title: "",
        keepAlive: false,
      }
    },
    //app下载
    {
      path: '/appxz',
      name: 'appxz',
      component: appxz,
      meta: {
        title: "",
        keepAlive: false,
      }
    },
    {
      path: '/apiDoc',
      name: 'apiDoc',
      component: apiDoc,
      meta: {
        title: "",
        keepAlive: false,
      }
    },












  ]
})

// function getAbsolutePath() {
// 	let path = location.pathname
// 	return path.substring(0, path.lastIndexOf('/') + 1)

// }

//路由守卫
router.beforeEach((to, from, next) => {

  //设置标题
  const title = to.meta && to.meta.title;
  if (title) {
    document.title = title;
  }
  next();
  gtag('config', 'AW-11243053297', {'page_path': to.fullPath});
});

export default router
