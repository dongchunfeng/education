<template>
  <d2-container>
    <el-dialog :title="title" :visible.sync="dialogForm${Domain}Visible">
  <el-form :model="form" label-width="80px" :rules="rules" ref="ruleForm">
      <#list fieldList as field>
          <#if field.name!='id' && field.nameHump!='createAt'&&field.nameHump!='updateAt'>
              <el-form-item label="${field.nameCn}" prop="${field.nameHump}">
              <#if field.enums>
                      <el-select v-model="form.${field.nameHump}" placeholder="请选择">
                          <el-option
                                  v-for="item in ${field.enumsConst}"
                                  :key="item.value"
                                  :label="item.label"
                                  :value="item.value">
                          </el-option>
                      </el-select>

              <#else>
              <el-input v-model="form.${field.nameHump}" autocomplete="off"></el-input>
              </#if>
              </el-form-item>
          </#if>
      </#list>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="resetForm('ruleForm')">重置</el-button>
    <el-button @click="dialogForm${Domain}Visible = false">取 消</el-button>
    <el-button type="primary" @click="save('ruleForm')">确 定</el-button>
  </div>
</el-dialog>
    <template slot="header">
      <el-form :model="params">
      ${searchname}:<el-input v-model="params.${Searchname}" style="width:100px;margin-right:5px"></el-input>
      <el-button type="primary" v-on:click="getTableData" size="small" style="margin-right:5px">查询</el-button>
      <el-button type="primary" v-on:click="open${Domain}Html('ruleForm')" size="small">新增${tableNameCn}</el-button>
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
    <#list fieldList as field>
        <#if field.nameHump!='createAt' && field.nameHump!='updateAt'>
            <el-table-column
                    prop="${field.nameHump}"
                    label="${field.nameCn}"
                    width="100">
            </el-table-column>
        </#if>
    </#list>

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
    name: '${domain}',
    created(){
      this.getTableData();
    },
     data() {
      return {
        visible: false,
        title:"",
        ruleForm: {
          <#list fieldList as field>
            "${field.nameHump}":'',
          </#list>
        },
        rules: {
          <#list fieldList as field >
            "${field.nameHump}": [
                { required: false, message: '请选择${field.nameCn}', trigger: 'change' }
            ],
          </#list>
        },
        form:{
            <#list fieldList as field>
            "${field.nameHump}":'',
            </#list>
        },
        dialogForm${Domain}Visible:false,
        formLabelWidth: '100px',
        total: 0,
        params: {
          page: 1,
          size: 5,
          "${Searchname}":''
        },
        multipleSelection: [],
        tableData: []
      }
    },
    methods: {
      open${Domain}Html(formName){
        this.dialogForm${Domain}Visible = true;
        this.title="添加${tableNameCn}";
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
            const res = await this.$api.BUSINESS_${DOMAIN}_ADD(this.form);
          if(res.code==0){
            this.$notify({
            title: '成功',
            message: res.msg,
            type: 'success'
          });
          this.dialogForm${Domain}Visible = false;
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
        this.dialogForm${Domain}Visible = true;
        this.title="修改${tableNameCn}";
        <#list fieldList as field>
          this.form.${field.nameHump} = row.${field.nameHump};
        </#list>
        console.log("修改${tableNameCn} form: =======================");
        console.log(this.form);
      },
      del(id){
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(async () => {
          const res = await this.$api.BUSINESS_${DOMAIN}_DEL(id);
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
          const res = await this.$api.BUSINESS_${DOMAIN}(this.params);
          console.log("分页查询${tableNameCn}:");
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
