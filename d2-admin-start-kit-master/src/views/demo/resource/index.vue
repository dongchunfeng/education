<template>
  <d2-container>
    <el-dialog :title="title" :visible.sync="dialogFormResourceVisible">
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
</el-dialog>
    <template slot="header">
      <el-form :model="params">
      昵称:<el-input v-model="params.name" style="width:100px;margin-right:5px"></el-input>
      <el-button type="primary" v-on:click="getTableData" size="small" style="margin-right:5px">查询</el-button>
      <el-button type="primary" v-on:click="openResourceHtml('ruleForm')" size="small">新增资源</el-button>
    </el-form>
    </template>
    <el-table
    :data="tableData"
    border
    style="width: 100%"
    @selection-change="handleSelectionChange">
    <el-table-column
      type="selection"
      width="55">
    </el-table-column>
            <el-table-column
                    prop="id"
                    label="id"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="name"
                    label="名称"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="page"
                    label="页面"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="request"
                    label="请求"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="parent"
                    label="父id"
                    width="100">
            </el-table-column>

    <el-table-column
      label="操作"
      width="100">
      <template slot-scope="scope">
        <el-button @click="handleClickEdit(scope.row)" type="text" size="small">编辑</el-button>
        <el-button type="text" size="small" @click="del(scope.row.id)">删除</el-button>
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
      :total="total">
    </el-pagination>
  </d2-container>
</template>

<style scoped>
.pagestyle{
  float: right;
}
</style>

<script>
export default {
    name: 'resource',
    created(){
      this.getTableData();
    },
     data() {
      return {
        visible: false,
        title:"",
        ruleForm: {
            "id":'',
            "name":'',
            "page":'',
            "request":'',
            "parent":'',
        },
        rules: {
            "id": [
                { required: true, message: '请选择id', trigger: 'change' }
            ],
            "name": [
                { required: false, message: '请选择名称', trigger: 'change' }
            ],
            "page": [
                { required: false, message: '请选择页面', trigger: 'change' }
            ],
            "request": [
                { required: false, message: '请选择请求', trigger: 'change' }
            ],
            "parent": [
                { required: false, message: '请选择父id', trigger: 'change' }
            ],
        },
        form:{
            "id":'',
            "name":'',
            "page":'',
            "request":'',
            "parent":'',
        },
        dialogFormResourceVisible:false,
        formLabelWidth: '100px',
        total: 0,
        params: {
          page: 1,
          size: 5,
          "name":''
        },
        multipleSelection: [],
        tableData: []
      }
    },
    methods: {
      openResourceHtml(formName){
        this.dialogFormResourceVisible = true;
        this.title="添加资源";
        this.$nextTick(function(){
          this.$refs[formName].clearValidate()
        })
        for(let key in this.form){
          this.form[key]=""
        }
        console.log("清空form==================");
        console.log(this.form);
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      save(formName){

        this.$refs[formName].validate(async (valid) => {
          if (valid) {
            const res = await this.$api.system_RESOURCE_ADD(this.form);
          if(res.code==0){
            this.$notify({
            title: '成功',
            message: res.msg,
            type: 'success'
          });
          this.dialogFormResourceVisible = false;
          this.resetForm(formName);
          this.getTableData();
          }else{
            this.$notify.error({
                title: '错误',
                message: res.msg
              });
          }
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      handleClickEdit(row) {
        console.log(row);
        this.dialogFormResourceVisible = true;
        this.title="修改资源";
          this.form.id = row.id;
          this.form.name = row.name;
          this.form.page = row.page;
          this.form.request = row.request;
          this.form.parent = row.parent;
        console.log("修改资源 form: =======================");
        console.log(this.form);
      },
      del(id){
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(async () => {
          const res = await this.$api.system_RESOURCE_DEL(id);
          if(res.code==0){
              this.$notify({
              title: '成功',
              message: res.msg,
              type: 'success'
            });
            this.getTableData();
          }else{
            this.$notify.error({
                  title: '错误',
                  message: res.msg
                });
          }
        }).catch(() => {

        });

      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
        console.log(this.multipleSelection);
      },
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
        this.params.size=val;
        this.getTableData();
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
        this.params.page=val;
        this.getTableData();
      },
      async getTableData () {
        try {
          const res = await this.$api.system_RESOURCE(this.params);
          console.log("分页查询资源:");
          console.log(res);
          this.tableData = res.data.list;
          this.total = res.data.total;
        } catch (error) {
          console.log(error)
        }
      }
    }
  }
</script>
