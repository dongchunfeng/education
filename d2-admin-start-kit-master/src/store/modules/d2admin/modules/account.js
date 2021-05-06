import { Message, MessageBox } from 'element-ui'
import util from '@/libs/util.js'
import router from '@/router'
import api from '@/api'
// store
import store from '@/store/index'
import { menuHeader, menuAside } from '@/menu'
import { uniqueId } from 'lodash'
//import Tool from '../../../../../public/js/tool.js'

function supplementPath(menu) {
  if (Tool.isEmpty(menu) || menu == undefined) {
    return;
  }
  return menu.map(e => ({
    ...e,
    path: e.path || uniqueId('d2-menu-empty-'),
    ...e.children ? {
      children: supplementPath(e.children)
    } : {}
  }))
}

function userResource() {

  let loginUser = Tool.getCookie("d2admin-1.20.1-loginUser");
  //console.log(decodeURI(resources));
  if (Tool.isEmpty(loginUser) || loginUser == undefined) {
    return;
  }
  let resources = JSON.parse(loginUser);
  console.log("userResourcedasdsad==================");
  console.log(resources);
  //let resource = Tool.hasResource(resources.id);
  let menu = [];
  //menu.push({ path: '/index', title: '首页', icon: 'home' });

  //let menus = arraytotree(resources.resources);
  for (let index = 0; index < resources.resources.length; index++) {
    let parent = resources.resources[index];
    
    if (parent.parent === '') {
      if(parent.id==='00'){
        menu.splice(0,0,{
          title: parent.name,
          icon: 'home',
          path:parent.page
        });
        continue;
      }
      menu.push({
        id: parent.id,
        title: parent.name,
        icon: 'folder-o'
      });
      // for (let j = 0; j < resources.resources.length; j++) {
      //   let child = resources.resources[j];
      //   if (child.parent===parent.id) {
      //     if (Tool.isEmpty(parent.children)) {
      //       parent.children = [];
      //     }
      //     parent.children.push({
      //       path: child.page, title: child.name
      //     });
      //   }
      // }
    }
  }
  console.log(menu);
  for (let i = 0; i < menu.length; i++) {
    let menus = menu[i];
    for (let j = 0; j < resources.resources.length; j++) {
      let menu1 = resources.resources[j];
      if (menu1.parent === menus.id) {
        if (Tool.isEmpty(menus.children)) {
          menus.children = [];
        }
        menus.children.push({
          path: menu1.page, title: menu1.name
        });
      }
    }

  }

  return menu;

}

export default {
  namespaced: true,
  actions: {
    /**
     * @description 登录
     * @param {Object} context
     * @param {Object} payload loginName {String} 用户账号
     * @param {Object} payload password {String} 密码
     * @param {Object} payload route {Object} 登录成功后定向的路由对象 任何 vue-router 支持的格式
     */
    async login({ dispatch }, {
      loginName = '',
      password = '',
      code = '',
      remember = '',
      imageCodeToken = ''
    } = {}) {
      const res = await api.SYS_USER_LOGIN({ loginName, password, code, imageCodeToken })
      // 设置 cookie 一定要存 uuid 和 token 两个 cookie
      // 整个系统依赖这两个数据进行校验和存储
      // uuid 是用户身份唯一标识 用户注册的时候确定 并且不可改变 不可重复
      // token 代表用户当前登录状态 建议在网络请求中携带 token
      // 如有必要 token 需要定时更新，默认保存一天
      if (res.success) {
        let loginUser = res.data;
        if (remember) {
          let md5 = hex_md5(password);
          LocalStorage.set("loginUser", {
            loginName: loginUser.loginName,
            password: password,
            md5: md5
          });
        } else {
          LocalStorage.set("loginUser", null);
        }
        util.cookies.set('uuid', res.data.id)
        util.cookies.set('token', res.data.token)
        util.cookies.set("loginUser", JSON.stringify(res.data));
        //Tool.setCookie("loginUser",res.data,"d1");
        // 设置 vuex 用户信息
        await dispatch('d2admin/user/set', { name: res.data.loginName }, { root: true })
        // 用户登录后从持久化数据加载一系列的设置
        await dispatch('load')


      } else {
        Message.error(res.msg);
      }
    },
    /**
     * @description 注销用户并返回登录页面
     * @param {Object} context
     * @param {Object} payload confirm {Boolean} 是否需要确认
     */
    logout({ commit, dispatch }, { confirm = false } = {}) {
      /**
       * @description 注销
       */
      async function logout() {
        //
        let token = util.cookies.get("token");
        console.log("token==========");
        console.log(token);
        const res = await api.SYS_USER_LOGOUT(token);
        if (res.success) {
          // 删除cookie
          util.cookies.remove('token')
          util.cookies.remove('uuid')
          util.cookies.remove('loginUser')
          // 清空 vuex 用户信息
          await dispatch('d2admin/user/set', {}, { root: true })
          // 跳转路由
          router.push({ name: 'login' })
        }

      }
      // 判断是否需要确认
      if (confirm) {
        commit('d2admin/gray/set', true, { root: true })
        MessageBox.confirm('确定要注销当前用户吗', '注销用户', { type: 'warning' })
          .then(() => {
            commit('d2admin/gray/set', false, { root: true })
            logout()
          })
          .catch(() => {
            commit('d2admin/gray/set', false, { root: true })
            Message({ message: '取消注销操作' })
          })
      } else {
        logout()
      }
    },
    /**
     * @description 用户登录后从持久化数据加载一系列的设置
     * @param {Object} context
     */
    async load({ commit , dispatch }) {

      // 加载用户名
      await dispatch('d2admin/user/load', null, { root: true })
      // 加载主题
      await dispatch('d2admin/theme/load', null, { root: true })
      // 加载页面过渡效果设置
      await dispatch('d2admin/transition/load', null, { root: true })
      // 持久化数据加载上次退出时的多页列表
      await dispatch('d2admin/page/openedLoad', null, { root: true })
      // 持久化数据加载侧边栏配置
      await dispatch('d2admin/menu/asideLoad', null, { root: true })
      // 持久化数据加载全局尺寸
      await dispatch('d2admin/size/load', null, { root: true })
      // 持久化数据加载颜色设置
      await dispatch('d2admin/color/load', null, { root: true })
      let t = supplementPath(userResource());
      if (Tool.isEmpty(t) || t == undefined) {
        return;
      }
      commit('d2admin/search/init', t, { root: true })
      // 设置顶栏菜单
      commit('d2admin/menu/headerSet', t, { root: true })
      // 设置侧边栏菜单
      commit('d2admin/menu/asideSet', t, { root: true })

    }
  }
}
