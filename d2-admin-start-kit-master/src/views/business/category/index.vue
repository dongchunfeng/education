<template>
  <d2-container>
    <el-row>
      <el-col :span="11" class="d2-mr">
        <div class="grid-content bg-purple">
          <el-dialog :title="title" :visible.sync="dialogFormCategoryVisible">
            <el-form
              :model="form"
              label-width="80px"
              :rules="rules"
              ref="ruleForm"
            >
              <el-form-item label="父分类" prop="parent">
                <!-- <el-input v-model="form.parent" autocomplete="off"></el-input> -->
                <span> {{active.name || '无'}} </span>
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
                v-on:click="openCategoryHtml1('ruleForm')"
                size="small"
                >新增一级分类</el-button
              >
            </el-form>
          </div>
          <el-table
            id="categoryTableLeft"
            :data="level1"
            border
            style="width: 100%"
            @selection-change="handleSelectionChange"
            @row-click="rowclick"
            :row-style ="tableRowStyle"
            :row-class-name="tableRowClass"
            @cell-mouse-enter="tableCellMouseEnter"
          >
            <el-table-column type="selection" width="55"> </el-table-column>
            <el-table-column prop="id" label="id" width="100">
            </el-table-column>
            <el-table-column prop="name" label="名称" width="100">
            </el-table-column>
            <el-table-column prop="sort" label="顺序" width="60">
            </el-table-column>

            <el-table-column label="操作" width="100">
              <template slot-scope="scope">
                <!-- <el-button
                  @click="onClickLevel1(scope.row)"
                  type="text"
                  size="small"
                  >二级分类</el-button
                > -->
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
          <el-dialog :title="title" :visible.sync="dialogFormCategoryVisible1">
            <el-form
              :model="form"
              label-width="80px"
              :rules="rules"
              ref="ruleForm"
            >
              <el-form-item label="父分类" prop="parent">
                <!-- <el-input v-model="form.parent" autocomplete="off"></el-input> -->
                <span> {{active.name || '无'}} </span>
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
              <el-button @click="dialogFormCategoryVisible1 = false"
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
                v-on:click="openCategoryHtml2('ruleForm')"
                size="small"
                >新增二级分类</el-button
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
::v-deep .el-table tbody tr:hover > td {
      background-color: transparent;
 }
</style>

<script>
import $ from 'jquery'
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
      dialogFormCategoryVisible1: false,
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
    /**
     * 新增一级
     */
    openCategoryHtml1(formName) {
      this.dialogFormCategoryVisible = true;
      this.title = "添加分类表";
      this.active={};
      this.level2=[];
      
      this.$nextTick(function () {
        this.$refs[formName].clearValidate();
      });
      for (let key in this.form) {
        this.form[key] = "";
      }
      this.form.parent='00000000';
      console.log("清空form==================");
      console.log(this.form);
      
    },
    openCategoryHtml2(formName) {
      if(Tool.isEmpty(this.active)){
          this.$notify.error({
              title: "错误",
              message: "请先点击一级分类",
            });
            return;
      }
      this.dialogFormCategoryVisible1 = true;
      this.title = "添加二级分类表";
      
      this.$nextTick(function () {
        this.$refs[formName].clearValidate();
      });
      for (let key in this.form) {
        this.form[key] = "";
      }
      //this.form.parent='00000000';

      this.form.parent = this.active.id;

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
             this.dialogFormCategoryVisible1 = false;
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
      //this.form = $.extend({},row);
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
      let _this = this;
      try {
        const res = await this.$api.BUSINESS_CATEGORY(this.params);
        console.log("分页查询分类表:");
        console.log(res);
        _this.categorys = res.data;

        _this.level1 = [];
        //格式化树形
        for (let i = 0; i < _this.categorys.length; i++) {
            let c = _this.categorys[i];
            if (c.parent === '00000000') {
              _this.level1.push(c);
              for (let j = 0; j < _this.categorys.length; j++) {
                let child = _this.categorys[j];
                if (child.parent === c.id) {
                  if (Tool.isEmpty(c.children)) {
                    c.children = [];
                  }
                  c.children.push(child);
                }
              }
            }
          }

       _this.level2=[];
       setTimeout(() => {
         
         
         let j = $('tr.el-table__row.active');
         console.log(j);
         $('tr.el-table__row.active').click();
         //document.querySelectorAll('tr.el-table__row.active')[0].click();
        //  let styles = '';$('tr.el-table__row.active').trigger("click")
        //  for(let i=0;i<j.length;i++){
        //     styles = window.getComputedStyle(j[i]).getPropertyValue('background-color');
        //  }
        // if(Tool.isEmpty(styles)){
        //    return; 
        // }
        
        // for(let i=0;i<j.length;i++){
            
        // }
        
        
        
       }, 1000);


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
    rowclick (row, column, event) {
        this.row = row
        this.column = column
        this.active = row;
        console.log(row);
        console.log(event);
        this.level2 = row.children;
        setTimeout(() => {
          let j = $('tr.el-table__row.active');
          console.log(j[0]);
          //$(j[0]).trigger("click");
        }, 1000);
    },
    tableRowStyle (row, rowIndex) {
      if (this.row) {}
      if (this.row === row.row) {
        let stylejson = {};
        // console.log("row====================");
        // console.log(row.row);
        // console.log(this.active);
        if(!Tool.isEmpty(this.active) && row.row.id === this.active.id){
            stylejson.backgroundColor = '#d6e9c6';
            return stylejson
        }
        return ''
      } else {
        return ''
      }
    },
    tableRowClass(row, rowIndex){
      console.log("tableRowClass========================");
        console.log(row);
        console.log(this.active);
        console.log("this.active=======================");
        if(!Tool.isEmpty(this.active)){
            if(row.row.id===this.active.id){
                return "active";
            }
            
        }
        return "";
    },
    tableCellMouseEnter(row, column, cell, event){
        this.row = row
        this.column = column
        this.active = row;
        console.log(row);
        console.log(this.active);
        this.level2 = row.children;
    }
  },
};
</script>
