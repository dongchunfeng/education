<template>
  <d2-container>
    <!-- <el-dialog :title="title" :visible.sync="dialogFormResourceVisible">
  <el-form :model="form" label-width="80px" :rules="rules" ref="ruleForm">
              <el-form-item label="名称" prop="name">
              <el-input v-model="form.name" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="页面" prop="page">
              <el-input v-model="form.page" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="请求" prop="request">
              <el-input v-model="form.request" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="父id" prop="parent">
              <el-input v-model="form.parent" autocomplete="off"></el-input>
              </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="resetForm('ruleForm')">重置</el-button>
    <el-button @click="dialogFormResourceVisible = false">取 消</el-button>
    <el-button type="primary" @click="save('ruleForm')">确 定</el-button>
  </div>
</el-dialog> -->
    
      <!-- <el-form :model="params">
      昵称:<el-input v-model="params.name" style="width:100px;margin-right:5px"></el-input>
      <el-button type="primary" v-on:click="getTableData" size="small" style="margin-right:5px">查询</el-button>
      <el-button type="primary" v-on:click="openResourceHtml('ruleForm')" size="small">新增资源</el-button>
    </el-form> -->
    <el-row>
      <el-col :span="10">
        <el-input
        type="textarea"
        :autosize="{ minRows: 7, maxRows: 10}"
        placeholder="请输入内容"
        v-model="textarea"
      >
      </el-input>
      <el-button type="primary" v-on:click="save()" size="small">保存</el-button>
      </el-col>
      <el-col :span="10">
        <el-tree
            :props="defaultProps"
            :data="resourceList"
            node-key="id"
            ref="tree"
            :default-expand-all="true"
          >
          </el-tree>
      </el-col>
    </el-row>
    
    <!-- <el-table
      :data="tableData"
      border
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"> </el-table-column>
      <el-table-column prop="id" label="id" width="100"> </el-table-column>
      <el-table-column prop="name" label="名称" width="100"> </el-table-column>
      <el-table-column prop="page" label="页面" width="100"> </el-table-column>
      <el-table-column prop="request" label="请求" width="100">
      </el-table-column>
      <el-table-column prop="parent" label="父id" width="100">
      </el-table-column>

      <el-table-column label="操作" width="100">
        <template slot-scope="scope">
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
    </el-pagination> -->
  </d2-container>
</template>

<style scoped>
.pagestyle {
  float: right;
}
</style>

<script>
export default {
  name: "resource",
  created() {
    this.getTableData();


  },
  data() {
    return {
      defaultProps: {
        label: "name",
      },
      resourceList:[],
      visible: false,
      title: "",
      ruleForm: {
        id: "",
        name: "",
        page: "",
        request: "",
        parent: "",
      },
      rules: {
        id: [{ required: true, message: "请选择id", trigger: "change" }],
        name: [{ required: false, message: "请选择名称", trigger: "change" }],
        page: [{ required: false, message: "请选择页面", trigger: "change" }],
        request: [
          { required: false, message: "请选择请求", trigger: "change" },
        ],
        parent: [{ required: false, message: "请选择父id", trigger: "change" }],
      },
      form: {
        id: "",
        name: "",
        page: "",
        request: "",
        parent: "",
      },
      dialogFormResourceVisible: false,
      formLabelWidth: "100px",
      total: 0,
      params: {
        page: 1,
        size: 5,
        name: "",
      },
      multipleSelection: [],
      tableData: [],
      textarea:"",
    };
  },
  methods: {
    
    openResourceHtml(formName) {
      this.dialogFormResourceVisible = true;
      this.title = "添加资源";
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
    async save() {
      if(Tool.isEmpty(this.textarea)){
          this.$notify.error({
              title: "错误",
              message: '资源不能为空',
            });
            return;
      }
      let json  = JSON.parse(this.textarea);
      const res = await this.$api.system_RESOURCE_ADD(json);
          if (res.code == 0) {
            this.$notify({
              title: "成功",
              message: res.msg,
              type: "success",
            });
            this.dialogFormResourceVisible = false;
            this.getTableData();
          } else {
            this.$notify.error({
              title: "错误",
              message: res.msg,
            });
          }
    },
    handleClickEdit(row) {
      console.log(row);
      this.dialogFormResourceVisible = true;
      this.title = "修改资源";
      this.form.id = row.id;
      this.form.name = row.name;
      this.form.page = row.page;
      this.form.request = row.request;
      this.form.parent = row.parent;
      console.log("修改资源 form: =======================");
      console.log(this.form);
    },
    del(id) {
      this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          const res = await this.$api.system_RESOURCE_DEL(id);
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
        const res = await this.$api.system_RESOURCE_ALL();
        console.log("分页查询资源:");
        console.log(res);
        //this.resourceList = this.arraytotree(res);
        this.resourceList = res;
        console.log(this.resourceList);
        
      } catch (error) {
        console.log(error);
      }
    },
    //数组转化为树
    arraytotree(arr) {
      var top = [],
        sub = [],
        tempObj = {};

      arr.forEach(function (item) {
        if (item.parent === "") {
          // 顶级分类
          top.push(item);
        } else {
          sub.push(item); // 其他分类
        }
        item.children = []; // 默然添加children属性
        tempObj[item.id] = item; // 用当前分类的id做key，存储在tempObj中
      });

      sub.forEach(function (item) {
        // 取父级
        var parent = tempObj[item.parent] || { children: [] };
        // 把当前分类加入到父级的children中
        parent.children.push(item);
      });

      return top;
    },
  },
};
</script>
