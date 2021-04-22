<template>
  <d2-container>
    <el-dialog :title="title" :visible.sync="dialogFormCourseVisible">
      <el-form :model="form" label-width="80px" :rules="rules" ref="ruleForm">
        <el-form-item label="分类" prop="categorys">
          <el-tree
            :props="defaultProps"
            :data="categoryList"
            show-checkbox
            node-key="id"
            ref="tree"
            :default-expand-all="false"
            @check-change="handleCheckChange"
          >
          </el-tree>
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="概述" prop="summary">
          <el-input v-model="form.summary" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="时长" prop="time">
          <el-input v-model="form.time" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="价格(元)" prop="price">
          <el-input v-model="form.price" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="封面" prop="image">
          <el-input v-model="form.image" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="级别" prop="level">
          <el-select v-model="form.level" placeholder="请选择">
            <el-option
              v-for="item in COURSE_LEVEL"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="收费" prop="charge">
          <el-select v-model="form.charge" placeholder="请选择">
            <el-option
              v-for="item in COURSE_CHARGE"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择">
            <el-option
              v-for="item in COURSE_STATUS"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="报名数" prop="enroll">
          <el-input v-model="form.enroll" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="顺序" prop="sort">
          <el-input v-model="form.sort" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm('ruleForm')">重置</el-button>
        <el-button @click="dialogFormCourseVisible = false">取 消</el-button>
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
          v-on:click="openCourseHtml('ruleForm')"
          size="small"
          >新增课程表</el-button
        >
      </el-form>
    </template>

    <el-row>
      <el-col :span="6" v-for="o in tableData" :key="o.id">
        <el-card :body-style="{ padding: '0px' }">
          <img
            v-show="!o.image"
            src="./../../../../public/image/demo-course.jpg"
            class="image"
          />
          <img v-show="o.image" :src="o.image" class="image" />
          <div style="padding: 14px">
            <p style="color: #909399; font-size: 15px">
              <span v-if="o.level == 1">初级</span>
              <span v-if="o.level == 2">中级|</span>
              <span v-if="o.level == 3">高级|</span>
              <span v-if="o.charge == 'C'">收费|</span>
              <span v-if="o.charge == 'F'">免费|</span>
              <span v-if="o.status == 'P'">发布</span>
              <span v-if="o.status == 'D'">草稿</span>
            </p>
            <span style="color: #409eff; font-size: 25px; font-weight: 200">{{
              o.name
            }}</span>
            <p style="color: #409eff">{{ o.price }} 元</p>
            <div class="bottom clearfix">
              <time class="time">{{ o.summary }}</time>
              <p>
                <el-button-group style="margin-right: -3px">
                  <el-button type="primary" size="mini" round>{{
                    o.id
                  }}</el-button>
                  <el-button type="primary" size="mini" round
                    >排序:{{ o.sort }}</el-button
                  >
                  <el-button type="primary" size="mini" round
                    >时长:{{ o.time }}</el-button
                  >
                </el-button-group>
              </p>
              <span style="float: right">
                <el-button
                  @click="toChapter(o)"
                  type="success"
                  size="small"
                  plain
                  >大章</el-button
                >
                <el-button
                  @click="handleClickEdit(o)"
                  type="success"
                  size="small"
                  plain
                  >编辑</el-button
                >
                <el-button type="danger" size="small" @click="del(o.id)" plain
                  >删除</el-button
                >
              </span>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- <el-table
    :data="tableData"
    border
    style="width: 100%;display:none;"
    @selection-change="handleSelectionChange">
    <el-table-column
      fixed
      type="selection"
      width="55">
    </el-table-column>
            <el-table-column
                    prop="id"
                    label="ID"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="name"
                    label="名称"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="summary"
                    label="概述"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="time"
                    label="时长"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="price"
                    label="价格(元)"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="image"
                    label="封面"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="level"
                    label="级别"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="charge"
                    label="收费"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="status"
                    label="状态"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="enroll"
                    label="报名数"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="sort"
                    label="顺序"
                    width="100">
            </el-table-column>

    <el-table-column
      fixed="right"
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
  margin-top: 10px;
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
</style>

<script>
export default {
  name: "course",
  data() {
    return {
      categoryList: [],
      defaultProps: {
        label: "name",
      },
      COURSE_LEVEL: [
        { value: "1", label: "初级" },
        { value: "2", label: "中级" },
        { value: "3", label: "高级" },
      ],
      COURSE_CHARGE: [
        { value: "C", label: "收费" },
        { value: "F", label: "免费" },
      ],
      COURSE_STATUS: [
        { value: "P", label: "发布" },
        { value: "D", label: "草稿" },
      ],
      visible: false,
      title: "",
      ruleForm: {
        id: "",
        name: "",
        summary: "",
        time: "",
        price: "",
        image: "",
        level: "",
        charge: "",
        status: "",
        enroll: "",
        sort: "",
        createAt: "",
        updateAt: "",
      },
      rules: {
        id: [{ required: true, message: "请选择ID", trigger: "blur" }],
        name: [{ required: true, message: "请选择名称", trigger: "blur" }],
        summary: [
          { required: false, message: "请选择概述", trigger: "blur" },
        ],
        time: [{ required: false, message: "请选择时长", trigger: "blur" }],
        price: [
          { required: true, message: "请选择价格(元)", trigger: "blur" },
        ],
        image: [{ required: false, message: "请选择封面", trigger: "blur" }],
        level: [{ required: false, message: "请选择级别", trigger: "blur" }],
        charge: [{ required: false, message: "请选择收费", trigger: "blur" }],
        status: [{ required: false, message: "请选择状态", trigger: "blur" }],
        enroll: [
          { required: false, message: "请选择报名数", trigger: "blur" },
        ],
        sort: [{ required: false, message: "请选择顺序", trigger: "blur" }],
        createAt: [
          { required: false, message: "请选择创建时间", trigger: "blur" },
        ],
        updateAt: [
          { required: false, message: "请选择修改时间", trigger: "change" },
        ],
      },
      form: {
        id: "",
        name: "",
        summary: "",
        time: "",
        price: "",
        image: "",
        level: "",
        charge: "",
        status: "",
        enroll: "",
        sort: "",
        createAt: "",
        updateAt: "",
      },
      dialogFormCourseVisible: false,
      formLabelWidth: "100px",
      total: 0,
      params: {
        page: 1,
        size: 5,
        name: "",
      },
      multipleSelection: [],
      tableData: [],
    };
  },
  mounted() {
    this.getTableData();
    this.getCategoryList();
  },
  methods: {
    openCourseHtml(formName) {
      this.dialogFormCourseVisible = true;
      this.title = "添加课程表";
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
      
      let categorys = this.$refs.tree.getCheckedNodes();
      if(Tool.isEmpty(categorys)){
          this.$notify.error({
              title: "错误",
              message: '请选中一个分类',
            });
            return;
      }
      console.log("选中的Node================");
      console.log(categorys);
      this.form.categorys = categorys;
      this.$refs[formName].validate(async (valid) => {
        if (valid) {

          const res = await this.$api.BUSINESS_COURSE_ADD(this.form);
          if (res.code == 0) {
            this.$notify({
              title: "成功",
              message: res.msg,
              type: "success",
            });
            this.dialogFormCourseVisible = false;
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
      this.dialogFormCourseVisible = true;
      this.title = "修改课程表";
      this.form.id = row.id;
      this.form.name = row.name;
      this.form.summary = row.summary;
      this.form.time = row.time;
      this.form.price = row.price;
      this.form.image = row.image;
      this.form.level = row.level;
      this.form.charge = row.charge;
      this.form.status = row.status;
      this.form.enroll = row.enroll;
      this.form.sort = row.sort;
      this.form.createAt = row.createAt;
      this.form.updateAt = row.updateAt;
      console.log("修改课程表 form: =======================");
      console.log(this.form);
    },
    del(id) {
      this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          const res = await this.$api.BUSINESS_COURSE_DEL(id);
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
        const res = await this.$api.BUSINESS_COURSE(this.params);
        console.log("分页查询课程表:");
        console.log(res);
        this.tableData = res.data.list;
        this.total = res.data.total;
      } catch (error) {
        console.log(error);
      }
    },
    toChapter(course) {
      SessionStorage.set("course", course);
      this.$router.push("/chapter");
    },
    handleCheckChange(data, checked, indeterminate) {
      console.log(data, checked, indeterminate);
    },
    handleNodeClick(data) {
      console.log(data);
    },
    async getCategoryList() {
      const res = await this.$api.BUSINESS_CATEGORY();
      console.log("查询分类列表");
      console.log(res);

      this.categoryList = this.arraytotree(res.data);
    },
    //数组转化为树
    arraytotree(arr) {
      var top = [],
        sub = [],
        tempObj = {};

      arr.forEach(function (item) {
        if (item.parent === "00000000") {
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
