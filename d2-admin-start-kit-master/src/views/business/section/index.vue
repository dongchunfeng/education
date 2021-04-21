<template>
  <d2-container>
    <el-dialog :title="title" :visible.sync="dialogFormSectionVisible">
  <el-form :model="form" label-width="80px" :rules="rules" ref="ruleForm">
              <el-form-item label="标题" prop="title">
                  <el-input v-model="form.title" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="课程" prop="courseId" v-if="course">
                  {{course.name}}
                  <!-- <el-input v-model="form.courseId" autocomplete="off"></el-input> -->
              </el-form-item>
              <el-form-item label="课程" prop="courseId" v-if="!course">
                  <el-input v-model="form.courseId" autocomplete="off" disabled></el-input>
              </el-form-item>
              <el-form-item label="大章" prop="chapterId" v-if="chapter">
                 {{chapter.name}}
                  <!-- <el-input v-model="form.chapterId" autocomplete="off"></el-input> -->
              </el-form-item>
              <el-form-item label="大章" prop="chapterId" v-if="!chapter">
                  <el-input v-model="form.chapterId" autocomplete="off" disabled></el-input>
              </el-form-item>
              <el-form-item label="视频" prop="video">
                  <el-input v-model="form.video" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="时长" prop="time">
                  <el-input v-model="form.time" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="收费" prop="charge">
                  <el-select v-model="form.charge" placeholder="请选择">
                    <el-option
                    v-for="item in charge1"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                    </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="顺序" prop="sort">
                  <el-input v-model="form.sort" autocomplete="off"></el-input>
              </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="resetForm('ruleForm')">重置</el-button>
    <el-button @click="dialogFormSectionVisible = false">取 消</el-button>
    <el-button type="primary" @click="save('ruleForm')">确 定</el-button>
  </div>
</el-dialog>
    <template slot="header">
      <p >
        <router-link to="/course" style="color:#409EFF;font-size:25px;">
        <i class="el-icon-caret-right"></i>
          {{course.name}}
        </router-link>
        <router-link to="/chapter" style="color:#409EFF;font-size:25px;">
        <i class="el-icon-caret-right"></i>
          :&nbsp;{{chapter.name}}
        </router-link>
      </p>
      <el-form :model="params">
      标题:<el-input v-model="params.title" style="width:100px;margin-right:5px"></el-input>
      <el-button type="primary" v-on:click="getTableData" size="small" style="margin-right:5px">查询</el-button>
      <el-button type="primary" v-on:click="openSectionHtml('ruleForm')" size="small">新增小节</el-button>
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
                    label="ID"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="title"
                    label="标题"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="courseId"
                    label="课程"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="chapterId"
                    label="大章"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="video"
                    label="视频"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="time"
                    label="时长"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="charge"
                    label="收费"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="sort"
                    label="顺序"
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
    name: 'sections',
    mounted(){
      let chapter = SessionStorage.get("chapter")||{};
      let course = SessionStorage.get("course")||{};
      if (Tool.isEmpty(course) || Tool.isEmpty(chapter)) {
        _this.$router.push("/course");
      }
      this.chapter = chapter;
      this.course = course;
      this.getTableData();
    },
    created(){
      
    },
     data() {
      return {
        course:{},
        chapter:{},
        visible: false,
        title:"",
        charge1:[{
          value: 'C',
          label: '收费'
        },
        {
          value: 'F',
          label: '免费'
        }
        ],
        ruleForm: {
            "title":'',
            "courseId":'',
            "chapterId":'',
            "video":'',
            "time":'',
            "charge":'',
            "sort":'',
            "createAt":'',
            "updateAt":'',
        },
        rules: {
            "id": [
                { required: true, message: '请选择ID', trigger: 'change' }
            ],
            "title": [
                { required: true, message: '请选择标题', trigger: 'change' }
            ],
            "courseId": [
                { required: false, message: '请选择课程', trigger: 'change' }
            ],
            "chapterId": [
                { required: false, message: '请选择大章', trigger: 'change' }
            ],
            "video": [
                { required: false, message: '请选择视频', trigger: 'change' }
            ],
            "time": [
                { required: false, message: '请选择时长', trigger: 'change' }
            ],
            "charge": [
                { required: false, message: '请选择收费', trigger: 'change' }
            ],
            "sort": [
                { required: false, message: '请选择顺序', trigger: 'change' }
            ]
        },
        form:{
            "id":'',
            "title":'',
            "courseId":'',
            "chapterId":'',
            "video":'',
            "time":'',
            "charge":'',
            "sort":'',
            "createAt":'',
            "updateAt":'',
        },
        dialogFormSectionVisible:false,
        formLabelWidth: '100px',
        total: 0,
        params: {
          page: 1,
          size: 5,
          "title":'',
          courseId:'',
          chapterId:''
        },
        multipleSelection: [],
        tableData: []
      }
    },
    methods: {
      openSectionHtml(formName){
        this.dialogFormSectionVisible = true;
        this.title="添加小节";
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
            this.form.chapterId = this.chapter.id;
            const res = await this.$api.BUSINESS_SECTION_ADD(this.form);
          if(res.code==0){
            this.$notify({
            title: '成功',
            message: res.msg,
            type: 'success'
          });
          this.dialogFormSectionVisible = false;
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
        this.dialogFormSectionVisible = true;
        this.title="修改小节";
          this.form.id = row.id;
          this.form.title = row.title;
          this.form.courseId = row.courseId;
          this.form.chapterId = row.chapterId;
          this.form.video = row.video;
          this.form.time = row.time;
          if(row.charge=="收费"){
              this.form.charge = 'C';
          }else{
              this.form.charge = 'F';
          }
          
          this.form.sort = row.sort;
          this.form.createAt = row.createAt;
          this.form.updateAt = row.updateAt;
        console.log("修改小节 form: =======================");
        console.log(this.form);
      },
      del(id){
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(async () => {
          const res = await this.$api.BUSINESS_SECTION_DEL(id);
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
          this.params.chapterId = this.chapter.id;
          const res = await this.$api.BUSINESS_SECTION(this.params);
          console.log("分页查询小节:");
          console.log(res);
          var resList = res.data.list;
          for(var section of resList){
            if(section.charge=='C'){
                section.charge = "收费";
            }else if(section.charge=='F'){
                section.charge = "免费";
            }
          }
          this.tableData = resList;
          
          this.total = res.data.total;
        } catch (error) {
          console.log(error)
        }
      }
    }
  }
</script>
