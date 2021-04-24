<template>
  <d2-container>
    <el-dialog :title="title" :visible.sync="dialogFormTeacherVisible">
      <el-form :model="form" label-width="80px" :rules="rules" ref="ruleForm">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="form.nickname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="头像" prop="image">
          <!-- <el-input v-model="form.image" autocomplete="off"></el-input> -->
          <!-- <el-upload
            class="avatar-uploader"
            action="api//file/admin/upload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img v-if="imageUrl" :src="imageUrl" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload> -->
          <file :after-upload="afterUpload" :suffixs="'image/jpeg'"></file>
          <img v-if="imageUrl" :src="imageUrl" class="avatar" />
        </el-form-item>
        <el-form-item label="职位" prop="position">
          <el-input v-model="form.position" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="座右铭" prop="motto">
          <el-input v-model="form.motto" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="简介" prop="intro">
          <el-input v-model="form.intro" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm('ruleForm')">重置</el-button>
        <el-button @click="dialogFormTeacherVisible = false">取 消</el-button>
        <el-button type="primary" @click="save('ruleForm')">确 定</el-button>
      </div>
    </el-dialog>
    <template slot="header">
      <el-form :model="params">
        名称:<el-input
          v-model="params.name"
          style="width: 100px; margin-right: 5px"
        ></el-input>
        <el-button
          type="primary"
          v-on:click="getTableData"
          size="small"
          style="margin-right: 5px"
          >查询</el-button
        >
        <el-button
          type="primary"
          v-on:click="openTeacherHtml('ruleForm')"
          size="small"
          >新增讲师</el-button
        >
      </el-form>
    </template>
    <el-row>
      <el-col :span="6" v-for="teacher in tableData" :key="teacher.id">
        <el-card :body-style="{ padding: '5px' }">
          <img v-show="!teacher.image" src="./../../../../public/image/avator/avator1.jpg" class="image" />
          <img v-show="teacher.image" :src="teacher.image" class="image" />
          <div class="d2-text-center">
            <span class="" style="">{{ teacher.position }}</span>
          </div>

          <div style="padding: 14px" class="d2-text-center">
            <span>{{ teacher.name }}</span>
            <div class="bottom clearfix">
              <el-button
                @click="handleClickEdit(teacher)"
                type="text"
                size="small"
                >编辑</el-button
              >
              <el-button type="text" size="small" @click="del(teacher.id)"
                >删除</el-button
              >
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- <el-table
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
                    label="姓名"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="nickname"
                    label="昵称"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="image"
                    label="头像"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="position"
                    label="职位"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="motto"
                    label="座右铭"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="intro"
                    label="简介"
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
  </el-table> -->
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
  </d2-container>
</template>

<style scoped>
.pagestyle {
  float: right;
}
.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>

<script>
import File from "../../../components/file/index.vue";
export default {
  components:{File},
  name: "teacher",
  created() {
    this.getTableData();
  },
  data() {
    return {
      visible: false,
      title: "",
      ruleForm: {
        id: "",
        name: "",
        nickname: "",
        image: "",
        position: "",
        motto: "",
        intro: "",
      },
      rules: {
        id: [{ required: true, message: "请选择id", trigger: "change" }],
        name: [{ required: true, message: "请选择姓名", trigger: "change" }],
        nickname: [
          { required: true, message: "请选择昵称", trigger: "change" },
        ],
        image: [{ required: false, message: "请选择头像", trigger: "change" }],
        position: [
          { required: false, message: "请选择职位", trigger: "change" },
        ],
        motto: [
          { required: false, message: "请选择座右铭", trigger: "change" },
        ],
        intro: [{ required: false, message: "请选择简介", trigger: "change" }],
      },
      form: {
        id: "",
        name: "",
        nickname: "",
        image: "",
        position: "",
        motto: "",
        intro: "",
      },
      dialogFormTeacherVisible: false,
      formLabelWidth: "100px",
      total: 0,
      params: {
        page: 1,
        size: 5,
        name: "",
      },
      multipleSelection: [],
      tableData: [],
      imageUrl:"",
    };
  },
  methods: {
    openTeacherHtml(formName) {
      this.dialogFormTeacherVisible = true;
      this.title = "添加讲师";
      this.$nextTick(function () {
        this.$refs[formName].clearValidate();
      });
      for (let key in this.form) {
        this.form[key] = "";
      }
      this.imageUrl="";
      console.log("清空form==================");
      console.log(this.form);
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    save(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          const res = await this.$api.BUSINESS_TEACHER_ADD(this.form);
          if (res.code == 0) {
            this.$notify({
              title: "成功",
              message: res.msg,
              type: "success",
            });
            this.dialogFormTeacherVisible = false;
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
      this.dialogFormTeacherVisible = true;
      this.title = "修改讲师";
      this.form.id = row.id;
      this.form.name = row.name;
      this.form.nickname = row.nickname;
      //this.form.image = row.image;
      this.form.position = row.position;
      this.form.motto = row.motto;
      this.form.intro = row.intro;
      this.imageUrl = row.image;
      console.log("修改讲师 form: =======================");
      console.log(this.form);
    },
    del(id) {
      this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          const res = await this.$api.BUSINESS_TEACHER_DEL(id);
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
        const res = await this.$api.BUSINESS_TEACHER(this.params);
        console.log("分页查询讲师:");
        console.log(res);
        this.tableData = res.data.list;
        this.total = res.data.total;
      } catch (error) {
        console.log(error);
      }
    },
    afterUpload(resp){
      console.log(resp);
      this.imageUrl = resp.data;
      this.form.image = resp.data;
    }
    // handleAvatarSuccess(res, file) {
    //   console.log(res);
    //   this.imageUrl = res.data;
    //   this.form.image = res.data;
    // },
    // beforeAvatarUpload(file) {
    //   const isJPG = file.type === "image/jpeg";
    //   const isLt2M = file.size / 1024 / 1024 < 2;

    //   if (!isJPG) {
    //     this.$message.error("上传头像图片只能是 JPG 格式!");
    //   }
    //   if (!isLt2M) {
    //     this.$message.error("上传头像图片大小不能超过 2MB!");
    //   }
    //   return isJPG && isLt2M;
    // },
  },
};
</script>
