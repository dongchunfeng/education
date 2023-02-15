<template>
  <div>
    <el-upload
      class="upload-demo"
      action="/api/file/admin/upload"
      :headers="headersObject"
      :show-file-list="false"
      :on-success="handleAvatarSuccess"
      :before-upload="beforeAvatarUpload"
    >
      <el-button size="small" type="primary">{{ text }}</el-button>
    </el-upload>
  </div>
</template>



<script>
import util from "@/libs/util";
export default {
  name: "bigFile",
  props: {
    text: {
      default: "上传大文件",
    },
    afterUpload: {
      type: Function,
      default: null,
    },
    suffixs: {
      default: "",
    },
  },
  data() {
    return {
      headersObject: {},
    };
  },
  methods: {
    handleAvatarSuccess(res, file) {
      console.log("上传文件成功:");
      console.log(res);
      this.afterUpload(res);
    },
    beforeAvatarUpload(file) {
      
      const suffixs = this.suffixs;
      const isJPG = file.type === suffixs;
      
      //const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("文件格式不正确,只支持上传" + suffixs);
      }
      //   if (!isLt2M) {
      //     this.$message.error("文件大小不能超过 2MB!");
      //   }

      //文件分片
      let shardSize = 20 * 1024 * 1024; //以20m为一个分片
      let shardIndex = 0;
      let shardStart = shardIndex * shardSize; //当前分片为起始位置
      let shardEnd = Math.min(file.size,shardStart + shardSize); //当前分片为结束位置
      let fileShard = file.slice(shardStart,shardEnd);
      file = fileShard;
      console.log(file);
      return isJPG;
    },
  },
  mounted() {
    const token = util.cookies.get("token");
    this.headersObject = { Authorization: token };
  },
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