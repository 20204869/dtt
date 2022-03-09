import router from './router'
import store from './store'
import {Message} from 'element-ui'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import {getToken} from '@/utils/auth'
import {getUtlParam} from "@/api/redirect";

NProgress.configure({showSpinner: false})

const whiteList = ['/login', '/auth-redirect', '/bind', '/register']

router.beforeEach((to, from, next) => {
  NProgress.start()
  if (getToken()) {
    to.meta.title && store.dispatch('settings/setTitle', to.meta.title)
    /* has token*/
    if (to.path === '/login') {
      next({path: '/'})
      NProgress.done()
    } else {
      if (store.getters.roles.length === 0) {
        // 判断当前用户是否已拉取完user_info信息
        store.dispatch('GetInfo').then(() => {
          store.dispatch('GenerateRoutes').then(accessRoutes => {
            // 根据roles权限生成可访问的路由表
            router.addRoutes(accessRoutes) // 动态添加可访问路由表
            next({...to, replace: true}) // hack方法 确保addRoutes已完成
          })
        }).catch(err => {
          store.dispatch('LogOut').then(() => {
            Message.error(err)
             next({ path: '/' })
          })
        })
      } else {
        next()
      }
    }
  } else {
    // 没有token
    if (whiteList.indexOf(to.path) !== -1) {
      // 在免登录白名单，直接进入
      next()
    } else {
      if (getUtlParam(window.location.href).userName && getUtlParam(window.location.href).password) {
        store.dispatch("Login", {
          username: getUtlParam(window.location.href).userName,
          password: getUtlParam(window.location.href).password,
          rememberMe: false,
          code: "",
          uuid: ""
        }).then(() => {
          if (store.getters.roles.length === 0) {
            // 判断当前用户是否已拉取完user_info信息
            store.dispatch('GetInfo').then(() => {
              store.dispatch('GenerateRoutes').then(accessRoutes => {
                // 根据roles权限生成可访问的路由表
                router.addRoutes(accessRoutes) // 动态添加可访问路由表
                next('/') // hack方法 确保addRoutes已完成
              })
            }).catch(err => {
              store.dispatch('LogOut').then(() => {
                Message.error(err)
                 next({ path: '/' })
              })
            })
          } else {
            next()
          }
          router.push({ path: "/" }).catch(()=>{});
        })
      } else {
         const fullPath = 'https://sso-test.cangoonline.net/?redirectUrl=http://localhost:8099&appId=appManager#/login'
       // const fullPath = 'https://sso.cangoonline.net/?redirectUrl=http://datamanager.cangoonline.net/datamanager&appId=dataManager#/login'
        console.log(fullPath)
        next(false)
        window.location.href = fullPath
        NProgress.done()
      }
      // next(`/login?redirect=${to.fullPath}`) // 否则全部重定向到登录页
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})
