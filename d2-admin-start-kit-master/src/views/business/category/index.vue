<template>
  <d2-container>
    <el-row>
      <el-col :span="12" class="d2-m3">
        <div class="grid-content bg-purple">
          <el-dialog :title="title" :visible.sync="dialogFormCategoryVisible">
            <el-form
              :model="form"
              label-width="80px"
              :rules="rules"
              ref="ruleForm"
            >
              <el-form-item label="父id" prop="parent">
                <el-input v-model="form.parent" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="名称" prop="name">
                <el-input v-model="form.name" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="顺序" prop="sort">
                <el-input v-model="form.sort" autocomplete="off"></el-input>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="resetForm('ruleForm')">重置</el-button>
              <el-button @click="dialogFormCategoryVisible = false"
                >取 消</el-button
              >
              <el-button type="primary" @click="save('ruleForm')"
                >确 定</el-button
              >
            </div>
          </el-dialog>
          <div class="d2-mb">
            <el-form :model="params">
              <!-- 名称:<el-input
                v-model="params.name"
                style="width: 100px; margin-right: 5px"
              ></el-input>
              <el-button
                type="primary"
                v-on:click="getTableData"
                size="small"
                style="margin-right: 5px"
                >查询</el-button
              > -->
              <el-button
                type="primary"
                v-on:click="openCategoryHtml('ruleForm')"
                size="small"
                >新增分类表</el-button
              >
            </el-form>
          </div>
          <el-table
            :data="level1"
            border
            style="width: 100%"
            @selection-change="handleSelectionChange"
            @cell-click="cellclick"
            :cell-style="tableCellStyle"
          >
            <el-table-column type="selection" width="55"> </el-table-column>
            <el-table-column prop="id" label="id" width="100">
            </el-table-column>
            <el-table-column prop="name" label="名称" width="100">
            </el-table-column>
            <el-table-column prop="sort" label="顺序" width="60">
            </el-table-column>

            <el-table-column label="操作" width="130">
              <template slot-scope="scope">
                <el-button
                  @click="onClickLevel1(scope.row)"
                  type="text"
                  size="small"
                  >二级分类</el-button
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
        </div>
      </el-col>
      <el-col :span="12">
        <div class="grid-content bg-purple-light">
          <el-dialog :title="title" :visible.sync="dialogFormCategoryVisible">
            <el-form
              :model="form"
              label-width="80px"
              :rules="rules"
              ref="ruleForm"
            >
              <el-form-item label="父id" prop="parent">
                <el-input v-model="form.parent" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="名称" prop="name">
                <el-input v-model="form.name" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="顺序" prop="sort">
                <el-input v-model="form.sort" autocomplete="off"></el-input>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="resetForm('ruleForm')">重置</el-button>
              <el-button @click="dialogFormCategoryVisible = false"
                >取 消</el-button
              >
              <el-button type="primary" @click="save('ruleForm')"
                >确 定</el-button
              >
            </div>
          </el-dialog>
          <div class="d2-mb">
            
              <!-- 名称:<el-input
                v-model="params1.name"
                style="width: 100px; margin-right: 5px"
              ></el-input>
              <el-button
                type="primary"
                v-on:click="getTableData"
                size="small"
                style="margin-right: 5px"
                >查询</el-button
              > -->
              <el-button
                type="primary"
                v-on:click="openCategoryHtml('ruleForm')"
                size="small"
                >新增分类表</el-button
              >
            
          </div>
          <el-table
            :data="level2"
            border
            style="width: 100%"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="55"> </el-table-column>
            <el-table-column prop="parent" label="父id" width="100">
            </el-table-column>
            <el-table-column prop="name" label="名称" width="100">
            </el-table-column>
            <el-table-column prop="sort" label="顺序" width="100">
            </el-table-column>

            <el-table-column label="操作" width="130">
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
        </div>
      </el-col>
    </el-row>
  </d2-container>
</template>

<style scoped>
.pagestyle {
  float: right;
}
</style>

<script>
export default {
  name: "category",
  created() {
    this.getTableData();
  },
  data() {
    return {
      active:{},
      visible: false,
      title: "",
      ruleForm: {
        id: "",
        parent: "",
        name: "",
        sort: "",
      },
      rules: {
        id: [{ required: false, message: "请选择ID", trigger: "change" }],
        parent: [{ required: true, message: "请选择父id", trigger: "change" }],
        name: [{ required: true, message: "请选择名称", trigger: "change" }],
        sort: [{ required: false, message: "请选择顺序", trigger: "change" }],
      },
      form: {
        id: "",
        parent: "",
        name: "",
        sort: "",
      },
      dialogFormCategoryVisible: false,
      formLabelWidth: "100px",
      total: 0,
      params: {
        name: "",
      },
      params1:{
        name:""
      },
      multipleSelection: [],
      tableData: [],
      categorys: [],
      level1: [],
      level2: [],
      row:'',
	    column:''
    };
  },
  methods: {
    openCategoryHtml(formName) {
      this.dialogFormCategoryVisible = true;
      this.title = "添加分类表";
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
          const res = await this.$api.BUSINESS_CATEGORY_ADD(this.form);
          if (res.code == 0) {
            this.$notify({
              title: "成功",
              message: res.msg,
              type: "success",
            });
            this.dialogFormCategoryVisible = false;
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
      this.dialogFormCategoryVisible = true;
      this.title = "修改分类表";
      this.form.id = row.id;
      this.form.parent = row.parent;
      this.form.name = row.name;
      this.form.sort = row.sort;
      console.log("修改分类表 form: =======================");
      console.log(this.form);
    },
    del(id) {
      this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          const res = await this.$api.BUSINESS_CATEGORY_DEL(id);
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
        const res = await this.$api.BUSINESS_CATEGORY(this.params);
        console.log("分页查询分类表:");
        console.log(res);
        this.categorys = res.data;

        this.level1 = [];
        //格式化树形
        for (let i = 0; i < this.categorys.length; i++) {
          let c = this.categorys[i];
          if (c.parent === "00000000") {
            this.level1.push(c);
            for (let j = 0; j < this.categorys.length; j++) {
              let child = this.categorys[j];
              if (child.parent === c.id) {
                //2级分类
                if (Tool.isEmpty(c.children)) {
                  c.children = [];
                }
                c.children.push(child);
              }
            }
          }
        }
      } catch (error) {
        console.log(error);
      }
    },
    onClickLevel1(category) {
      console.log(category);
      let _this = this;
      this.active = category;
      this.level2 = category.children;
    },
    cellclick (row, column, cell, event) {
        this.row = row
        this.column = column
    },
    tableCellStyle (row, rowIndex, column) {
      if (this.row && this.columnName) {}
      if (this.row === row.row) {
        //console.log("row====================");
        //console.log(row.row);
        if(!Tool.isEmpty(this.active) && row.row.id===this.active.id){
            return 'background-color:#d6e9c6;'
        }
        return ''
      } else {
        return ''
      }
    },
  },
};
</script>
