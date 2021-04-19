<template>
  <d2-container>
    <el-dialog :title="title" :visible.sync="dialogFormChapterVisible">
  <el-form :model="form" label-width="80px" :rules="rules" ref="ruleForm">
    <el-form-item label="名称" prop="name" required>
      <el-input v-model="form.name" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="课程" prop="courseId">
      
      {{course.name}}
      
    </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="resetForm('ruleForm')">重置</el-button>
    <el-button @click="dialogFormChapterVisible = false">取 消</el-button>
    <el-button type="primary" @click="save('ruleForm')">确 定</el-button>
  </div>
</el-dialog>
    <template slot="header">
      <p >
        <router-link to="/course" style="color:#409EFF;font-size:25px;">
        <i class="el-icon-caret-right"></i>
          {{course.name}}
        </router-link>
      </p>
      <el-form :model="params">
      名称:<el-input v-model="params.daname" style="width:100px;margin-right:5px"></el-input>
      <el-button type="primary" v-on:click="getTableData" size="small" style="margin-right:5px">查询</el-button>
      <el-button type="primary" v-on:click="openChapterHtml('ruleForm')" style="margin-right:5px" size="small">新增大章</el-button>
      <router-link to="/course">
        <el-button type="primary" v-on:click="openChapterHtml('ruleForm')" size="small">返回课程</el-button>
      </router-link>
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
      fixed
      prop="id"
      label="id"
      width="100">
    </el-table-column>
    <el-table-column
      prop="name"
      label="大章名称"
      width="120">
    </el-table-column>
    <el-table-column
      prop="courseId"
      label="课程"
      width="100">
    </el-table-column>
    <el-table-column
      fixed="right"
      label="操作"
      width="120">
      <template slot-scope="scope">
        <el-button @click="toSections(scope.row)" type="text" size="small">小节</el-button>
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
    name: 'chapter',
    mounted(){
        let course = SessionStorage.get("course") || {};
        // if(Tool.isEmpty(course)){
        //     this.$router.push("/course");
        // }
        this.course = course;
        this.getTableData();
    },
    created(){
      
    },
     data() {
      return {
        course:{},
        visible: false,
        title:"",
        ruleForm: {
          name: '',
          courseId: ''
        },
        rules: {
          name:[
            { required: true, message: '请输入名称', trigger: 'blur' },
            { min: 3, max: 7, message: '长度在 3 到 5 个字符', trigger: 'blur' }
          ]
        },
        form:{
          id:"",
          name:"",
          courseId:""
        },
        dialogFormChapterVisible:false,
        formLabelWidth: '100px',
        total: 0,
        params: {
          page: 1,
          size: 5,
          daname: '',
          courseId: '',
        },
        multipleSelection: [],
        tableData: []
      }
    },
    methods: {
      toSections(chapter){
          SessionStorage.set("chapter",chapter);
          this.$router.push("/section");
      },
      openChapterHtml(formName){
        this.dialogFormChapterVisible = true;
        this.title="添加大章";
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
            this.form.courseId = this.course.id;
            console.log("保存时赋值===============");
            console.log(this.form.courseId);
            const res = await this.$api.BUSINESS_CHAPTER_ADD(this.form);
          if(res.code==0){
            this.$notify({
            title: '成功',
            message: res.msg,
            type: 'success'
          });
          this.dialogFormChapterVisible = false;
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
        this.dialogFormChapterVisible = true;
        this.title="修改大章";
        this.form.id = row.id;
        this.form.name = row.name;
        this.form.courseId = row.courseId;
        console.log("修改大章 form: =======================");
        console.log(this.form);
      },
      del(id){
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(async () => {
          const res = await this.$api.BUSINESS_CHAPTER_DEL(id);
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
          this.params.courseId = this.course.id;
          const res = await this.$api.BUSINESS_CHAPTER(this.params);
          console.log("分页查询大章:");
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
