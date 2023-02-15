<template>
  <d2-container>
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
                    prop="path"
                    label="相对路径"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="name"
                    label="文件名"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="suffix"
                    label="后缀"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="size"
                    label="大小"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="use"
                    label="用途"
                    width="100">
            </el-table-column>

    <!-- <el-table-column
      label="操作"
      width="100">
      <template slot-scope="scope">
        <el-button @click="handleClickEdit(scope.row)" type="text" size="small">编辑</el-button>
        <el-button type="text" size="small" @click="del(scope.row.id)">删除</el-button>
      </template>
    </el-table-column> -->
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
    name: 'file',
    created(){
      this.getTableData();
    },
     data() {
      return {
        visible: false,
        title:"",
        ruleForm: {
            "id":'',
            "path":'',
            "name":'',
            "suffix":'',
            "size":'',
            "use":'',
            "createAt":'',
            "updateAt":'',
        },
        rules: {
            "id": [
                { required: false, message: '请选择id', trigger: 'change' }
            ],
            "path": [
                { required: false, message: '请选择相对路径', trigger: 'change' }
            ],
            "name": [
                { required: false, message: '请选择文件名', trigger: 'change' }
            ],
            "suffix": [
                { required: false, message: '请选择后缀', trigger: 'change' }
            ],
            "size": [
                { required: false, message: '请选择大小', trigger: 'change' }
            ],
            "use": [
                { required: false, message: '请选择用途', trigger: 'change' }
            ],
            "createAt": [
                { required: false, message: '请选择创建时间', trigger: 'change' }
            ],
            "updateAt": [
                { required: false, message: '请选择更新时间', trigger: 'change' }
            ],
        },
        form:{
            "id":'',
            "path":'',
            "name":'',
            "suffix":'',
            "size":'',
            "use":'',
            "createAt":'',
            "updateAt":'',
        },
        dialogFormFileVisible:false,
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
      openFileHtml(formName){
        this.dialogFormFileVisible = true;
        this.title="添加文件";
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
            const res = await this.$api.file_FILE_ADD(this.form);
          if(res.code==0){
            this.$notify({
            title: '成功',
            message: res.msg,
            type: 'success'
          });
          this.dialogFormFileVisible = false;
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
        this.dialogFormFileVisible = true;
        this.title="修改文件";
          this.form.id = row.id;
          this.form.path = row.path;
          this.form.name = row.name;
          this.form.suffix = row.suffix;
          this.form.size = row.size;
          this.form.use = row.use;
          this.form.createAt = row.createAt;
          this.form.updateAt = row.updateAt;
        console.log("修改文件 form: =======================");
        console.log(this.form);
      },
      del(id){
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(async () => {
          const res = await this.$api.file_FILE_DEL(id);
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
          const res = await this.$api.file_FILE(this.params);
          console.log("分页查询文件:");
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
