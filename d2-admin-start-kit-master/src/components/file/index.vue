<template>
  <div>
    <el-upload
      class="upload-demo"
      action="api/file/admin/upload"
      :show-file-list="false"
      :on-success="handleAvatarSuccess"
      :before-upload="beforeAvatarUpload"
    >
    <el-button size="small" type="primary">点击上传</el-button>
    </el-upload>
    
  </div>
</template>



<script>
export default {
  name: "file",
  props: {
      afterUpload:{
          type:Function,
          default:null
      },
      suffixs:{
          default: ""
      }
  },
  data() {
    return {};
  },
  methods: {
    handleAvatarSuccess(res, file) {
      console.log("上传文件成功:"+res);
      this.afterUpload(res);  
    },
    beforeAvatarUpload(file) {
      const suffixs = this.suffixs;
      const isJPG = file.type === suffixs;
      //const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("文件格式不正确,只支持上传"+suffixs);
      }
    //   if (!isLt2M) {
    //     this.$message.error("文件大小不能超过 2MB!");
    //   }
      return isJPG;
    },
  },
  mounted() {},
};
</script>


<style scoped>
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