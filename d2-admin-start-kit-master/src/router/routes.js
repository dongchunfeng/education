import layoutHeaderAside from '@/layout/header-aside'

// 由于懒加载页面太多的话会造成webpack热更新太慢，所以开发环境不使用懒加载，只有生产环境使用懒加载
const _import = require('@/libs/util.import.' + process.env.NODE_ENV)

/**
 * 在主框架内显示
 */
const frameIn = [
  {
    path: '/',
    redirect: { name: 'index' },
    component: layoutHeaderAside,
    children: [
      // 首页
      {
        path: 'index',
        name: 'index',
        meta: {
          auth: true
        },
        component: _import('system/index')
      },
      // 演示页面
      {
        path: 'user',
        name: 'user',
        meta: {
          title: '用户管理',
          auth: true
        },
        component: _import('demo/user')
      },
      {
        path: 'resource',
        name: 'resource',
        meta: {
          title: '资源管理',
          auth: true
        },
        component: _import('demo/resource')
      },
      {
        path: 'role',
        name: 'role',
        meta: {
          title: '角色管理',
          auth: true
        },
        component: _import('demo/role')
      },
      {
        path: 'category',
        name: 'category',
        meta: {
          title: '分类管理',
          auth: true
        },
        component: _import('business/category')
      },
      {
        path: 'course',
        name: 'course',
        meta: {
          title: '课程管理',
          auth: true
        },
        component: _import('business/course')
      },
      {
        path: 'lecturer',
        name: 'lecturer',
        meta: {
          title: '讲师管理',
          auth: true
        },
        component: _import('business/lecturer')
      },
      {
        path: 'chapter',
        name: 'chapter',
        meta: {
          title: '大章管理',
          auth: true
        },
        component: _import('business/chapter')
      },
      {
        path: 'section',
        name: 'sections',
        meta: {
          title: '小节管理',
          auth: true
          
        },
        component: _import('business/section')
      },
      {
        path: 'file',
        name: 'file',
        meta: {
          title: '文件管理',
          auth: true
          
        },
        component: _import('file')
      },
      // 系统 前端日志
      {
        path: 'log',
        name: 'log',
        meta: {
          title: '前端日志',
        },
        component: _import('system/log')
      },
      // 刷新页面 必须保留
      {
        path: 'refresh',
        name: 'refresh',
        hidden: true,
        component: _import('system/function/refresh')
      },
      // 页面重定向 必须保留
      {
        path: 'redirect/:route*',
        name: 'redirect',
        hidden: true,
        component: _import('system/function/redirect')
      }
    ]
  }
]

/**
 * 在主框架之外显示
 */
const frameOut = [
  // 登录
  {
    path: '/login',
    name: 'login',
    component: _import('system/login')
  }
]

/**
 * 错误页面
 */
const errorPage = [
  {
    path: '*',
    name: '404',
    component: _import('system/error/404')
  }
]

// 导出需要显示菜单的
export const frameInRoutes = frameIn

// 重新组织后导出
export default [
  ...frameIn,
  ...frameOut,
  ...errorPage
]
