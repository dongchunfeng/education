<template>
  <d2-container>
    <el-dialog :title="title" :visible.sync="dialogFormRoleVisible">
      <el-form :model="form" label-width="80px" :rules="rules" ref="ruleForm">
        <el-form-item label="角色" prop="name">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="desc">
          <el-input v-model="form.desc" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm('ruleForm')">重置</el-button>
        <el-button @click="dialogFormRoleVisible = false">取 消</el-button>
        <el-button type="primary" @click="save('ruleForm')">确 定</el-button>
      </div>
    </el-dialog>
    <template slot="header">
      <el-button
        type="primary"
        v-on:click="openRoleHtml('ruleForm')"
        size="small"
        >新增角色</el-button
      >
    </template>
    <el-table
      :data="tableData"
      border
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"> </el-table-column>
      <el-table-column prop="id" label="id" width="100"> </el-table-column>
      <el-table-column prop="name" label="角色" width="100"> </el-table-column>
      <el-table-column prop="desc" label="描述" width="100"> </el-table-column>

      <el-table-column label="操作" width="100">
        <template slot-scope="scope">
          <el-button @click="editUser(scope.row)" type="text" size="small"
            >用户</el-button
          >
          <el-button @click="editResource(scope.row)" type="text" size="small"
            >资源</el-button
          >
          <el-button
            @click="handleClickEdit(scope.row)"
            type="text"
            size="small"
            >编辑</el-button
          >
          <el-button type="text" size="small" @click="del(scope.row.id)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      class="pagestyle"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="this.params.page"
      :page-sizes="[5, 10]"
      :page-size="this.params.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    >
    </el-pagination>

    <el-dialog :title="title" :visible.sync="dialogFormEditResourceVisible">
      <el-tree
        :props="defaultProps"
        :data="resourceList"
        :default-checked-keys="checkedKeys"
        show-checkbox
        node-key="id"
        ref="tree"
        :default-expand-all="true"
      >
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormEditResourceVisible = false"
          >取 消</el-button
        >
        <el-button type="primary" @click="saveResource()">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="title" :visible.sync="dialogFormEditUserVisible">
      <el-transfer
        v-model="roleUsers"
        :props="{
          key: 'id',
          label: 'name',
        }"
        :data="users"
      >
      </el-transfer>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormEditUserVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveUserRole()">确 定</el-button>
      </div>
    </el-dialog>
  </d2-container>
</template>

<style scoped>
.pagestyle {
  float: right;
}
</style>

<script>
import $ from "jquery";
export default {
  name: "role",
  created() {
    this.getTableData();
    this.getResourceList();
    // let loginUser = Tool.getCookie("d2admin-1.20.1-loginUser");
    // //console.log(decodeURI(resources));
    // let resources  = JSON.parse(loginUser);
    // console.log(resources);
  },
  data() {
    return {
      resourceList: [],
      resourceListAll: [],
      // 默认选中节点
      checkedKeys: [],
      defaultProps: {
        label: "name",
      },
      visible: false,
      title: "",
      ruleForm: {
        id: "",
        name: "",
        desc: "",
      },
      rules: {
        id: [{ required: false, message: "请选择id", trigger: "change" }],
        name: [{ required: false, message: "请选择角色", trigger: "change" }],
        desc: [{ required: false, message: "请选择描述", trigger: "change" }],
      },
      form: {
        id: "",
        name: "",
        desc: "",
      },
      dialogFormRoleVisible: false,
      dialogFormEditResourceVisible: false,
      dialogFormEditUserVisible: false,
      formLabelWidth: "100px",
      total: 0,
      params: {
        page: 1,
        size: 5,
        name: "",
      },
      multipleSelection: [],
      tableData: [],
      users: [],
      roleUsers: [],
    };
  },
  methods: {
    openRoleHtml(formName) {
      this.dialogFormRoleVisible = true;
      this.title = "添加角色";
      this.$nextTick(function () {
        this.$refs[formName].clearValidate();
      });
      for (let key in this.form) {
        this.form[key] = "";
      }
      console.log("清空form==================");
      console.log(this.form);
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    save(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          const res = await this.$api.system_ROLE_ADD(this.form);
          if (res.code == 0) {
            this.$notify({
              title: "成功",
              message: res.msg,
              type: "success",
            });
            this.dialogFormRoleVisible = false;
            this.resetForm(formName);
            this.getTableData();
          } else {
            this.$notify.error({
              title: "错误",
              message: res.msg,
            });
          }
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    handleClickEdit(row) {
      console.log(row);
      this.dialogFormRoleVisible = true;
      this.title = "修改角色";
      this.form.id = row.id;
      this.form.name = row.name;
      this.form.desc = row.desc;
      console.log("修改角色 form: =======================");
      console.log(this.form);
    },
    del(id) {
      this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          const res = await this.$api.system_ROLE_DEL(id);
          if (res.code == 0) {
            this.$notify({
              title: "成功",
              message: res.msg,
              type: "success",
            });
            this.getTableData();
          } else {
            this.$notify.error({
              title: "错误",
              message: res.msg,
            });
          }
        })
        .catch(() => {});
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(this.multipleSelection);
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.params.size = val;
      this.getTableData();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.params.page = val;
      this.getTableData();
    },
    async getTableData() {
      try {
        const res = await this.$api.system_ROLE(this.params);
        console.log("分页查询角色:");
        console.log(res);
        this.tableData = res.data.list;
        this.total = res.data.total;
      } catch (error) {
        console.log(error);
      }
    },
    async getResourceList() {
      const res = await this.$api.system_RESOURCE_ALL();
      const ress = await this.$api.system_RESOURCE({page:1,size:9999});
      console.log("获取资源树========");
      console.log(res);
      console.log(ress.data.list);
      this.resourceList = res;
      this.resourceListAll = ress.data.list;
    },
    editResource(role) {
      let _this = this;
      _this.dialogFormEditResourceVisible = true;
      _this.form.id = role.id;
      //有bug
      this.getResources(role.id);
    },
    async getResources(id) {
      let _this = this;
      const res = await this.$api.system_LIST_ROLERESOURCE(id);
      console.log("查询角色资源:");
      console.log(res.data);
      _this.checkedKeys = [];
      _this.$refs.tree.setCheckedKeys([]);

      if (Tool.isEmpty(res.data)) {
        return;
      }
      //_this.checkedKeys = ["01","0101","010101","010102","010103","0102","010201",]; 01 02
      for (let index = 0; index < res.data.length; index++) {
        const element = res.data[index];
        let node = this.$refs.tree.getNode(element.resourceId);
        this.$refs.tree.setChecked(node,true);
      }
      
      
    },
    //两数组去除重复数值
    mergeArray(arr1, arr2) {
      for (var i = 0; i < arr1.length; i++) {
        for (var j = 0; j < arr2.length; j++) {
          
          if (arr1[i].id === arr2[j].resourceId) {
            //arr2.splice(i, 1); //利用splice函数删除元素，从第i个位置，截取长度为1的元素
            
          }
        }
      }
      //alert(arr1.length)
      for (let index = 0; index < arr2.length; index++) {
        const element = arr2[index];
        this.checkedKeys.push(element.resourceId);
      }

      console.log(this.checkedKeys);
      return arr2;
    },
    async saveResource() {
      let roleResource = this.$refs.tree.getCheckedNodes(false, true);
      if (Tool.isEmpty(roleResource)) {
        this.$notify.error({
          title: "错误",
          message: "请选中一个资源",
        });
        return;
      }

      let resourceIds = [];
      for (let index = 0; index < roleResource.length; index++) {
        resourceIds.push(roleResource[index].id);
      }

      const res = await this.$api.system_SAVE_ROLERESOURCE({
        id: this.form.id,
        resourceIds: resourceIds,
      });
      console.log(res);
      if (res.success) {
        this.$notify({
          title: "成功",
          message: res.msg,
          type: "success",
        });
        this.dialogFormEditResourceVisible = false;
      }
    },
    editUser(role) {
      this.title = "关联用户角色";
      this.form = $.extend({}, role);
      console.log("editUser==========");
      console.log(this.form);
      this.listUser();
      this.dialogFormEditUserVisible = true;
      this.roleUsers = [];
    },
    async listUser() {
      this.users = [];
      const res = await this.$api.SYSTEM_USER_ALL();

      if (res.success) {
        for (let index = 0; index < res.data.length; index++) {
          const element = res.data[index];
          let id = element.id;
          let name = element.loginName;
          this.users.push({ id: id, name: name });
        }

        this.listRoleUser();
      }
    },
    async saveUserRole() {
      console.log(this.roleUsers);
      const res = await this.$api.system_SAVE_ROLEUSER({
        id: this.form.id,
        userIds: this.roleUsers,
      });

      if (res.success) {
        this.$notify({
          title: "成功",
          message: res.msg,
          type: "success",
        });
        this.dialogFormEditUserVisible = false;
      }
    },
    async listRoleUser() {
      const res = await this.$api.system_LIST_ROLEUSER(this.form.id);
      console.log("listRoleUser=========");
      console.log(res);
      if (res.success) {
        for (let index = 0; index < res.data.length; index++) {
          const element = res.data[index];
          this.roleUsers.push(element.userId);
        }
      }
    },
  },
};
</script>
