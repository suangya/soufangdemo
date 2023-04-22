<script setup>
import {reactive} from "vue";
import {login} from "@/utils/http";
import {ElMessage} from "element-plus";

const form = reactive({
  username: '',
  password: '',
})

const onSubmit = () => {
  login(form).then(resp => {
    if (resp.code != 0) {
      ElMessage({
        showClose: true,
        message: '账户名或密码错误',
        type: 'error',
      })
    }
  })
}
</script>
<template>
  <el-form :model="form" label-width="120px">
    <el-form-item label="用户名">
      <el-input v-model="form.username" placeholder="用户名/邮箱/手机号" type="text"/>
    </el-form-item>
    <el-form-item label="密码：">
      <el-input v-model="form.password" type="password"/>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">登陆</el-button>
    </el-form-item>
  </el-form>
</template>