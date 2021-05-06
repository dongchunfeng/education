// import { uniqueId } from 'lodash'

// /**
//  * @description 给菜单数据补充上 path 字段
//  * @description https://github.com/d2-projects/d2-admin/issues/209
//  * @param {Array} menu 原始的菜单数据
//  */
// function supplementPath(menu) {
//   if (Tool.isEmpty(menu)) {
//     return;
//   }
//   return menu.map(e => ({
//     ...e,
//     path: e.path || uniqueId('d2-menu-empty-'),
//     ...e.children ? {
//       children: supplementPath(e.children)
//     } : {}
//   }))
// }

// function userResource() {

//   let loginUser = Tool.getCookie("d2admin-1.20.1-loginUser");
//   //console.log(decodeURI(resources));
//   if (Tool.isEmpty(loginUser)||loginUser==undefined) {
//     return;
//   }
//   let resources = JSON.parse(loginUser);
//   //console.log("userResourcedasdsad==================");
//   //console.log(resources);
//   //let resource = Tool.hasResource(resources.id);
//   let menu = [];
//   menu.push({ path: '/index', title: '首页', icon: 'home' });

//   //let menus = arraytotree(resources.resources);
//   for (let index = 0; index < resources.resources.length; index++) {
//     let parent = resources.resources[index];
//     if (parent.parent === '') {
//       menu.push({
//         id: parent.id,
//         title: parent.name,
//         icon: 'folder-o'
//       });
//       // for (let j = 0; j < resources.resources.length; j++) {
//       //   let child = resources.resources[j];
//       //   if (child.parent===parent.id) {
//       //     if (Tool.isEmpty(parent.children)) {
//       //       parent.children = [];
//       //     }
//       //     parent.children.push({
//       //       path: child.page, title: child.name
//       //     });
//       //   }
//       // }
//     }
//   }
//   console.log(menu);
//   for (let i = 0; i < menu.length; i++) {
//     let menus = menu[i];
//     for (let j = 0; j < resources.resources.length; j++) {
//       let menu1 = resources.resources[j];
//       if (menu1.parent === menus.id) {
//         if (Tool.isEmpty(menus.children)) {
//           menus.children = [];
//         }
//         menus.children.push({
//           path: menu1.page, title: menu1.name
//         });
//       }
//     }

//   }

//   return menu;

// }


// export const menuHeader = supplementPath([
//   { path: '/index', title: '首页', icon: 'home' },
//   {
//     title: '系统管理',
//     icon: 'folder-o',
//     children: [
//       { path: '/user', title: '用户管理' },
//       { path: '/resource', title: '资源管理' },
//       { path: '/role', title: '角色管理' }
//     ]
//   },
//   {
//     title: '业务管理',
//     icon: 'folder-o',
//     children: [
//       { path: '/category', title: '分类管理' },
//       { path: '/course', title: '课程管理' },
//       { path: '/lecturer', title: '讲师管理' },
//       // { path: '/chapter', title: '大章管理' },
//       // { path: '/section', title: '小节管理' }
//     ]
//   }
// ])

// //export const menuAside = supplementPath(userResource())

// export const menuAside = supplementPath([
//   { path: '/index', title: '首页', icon: 'home' },
//   {
//     title: '系统管理',
//     icon: 'folder-o',
//     children: [
//       { path: '/user', title: '用户管理' },
//       { path: '/resource', title: '资源管理' },
//       { path: '/role', title: '角色管理' }
//     ]
//   },
//   {
//     title: '业务管理',
//     icon: 'folder-o',
//     children: [
//       { path: '/category', title: '分类管理' },
//       { path: '/course', title: '课程管理' },
//       { path: '/lecturer', title: '讲师管理' },
//       // { path: '/chapter', title: '大章管理' },
//       // { path: '/section', title: '小节管理' }

//     ]
//   }
// ])
