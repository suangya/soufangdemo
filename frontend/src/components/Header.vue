<script setup>
import {getUserInfo} from "../utils/http";
import {reactive} from "vue";
const info = reactive({
  id: -1,
  name: '',
  email: '',
  avatar: '',
  authorities: [],
})
getUserInfo().then(resp => {
  if (resp.code != 0) {
    return
  }
  const {id,name,email,avatar,authorities} = resp.data
  info.id = id
  info.name = name
  info.email = email
  info.avatar = avatar
  info.authorities = authorities
})
</script>
<template>
  <header className="header">
    <img alt="soufang logo" className="logo" src="@/assets/logo.svg"/>
    <nav className="header-nav">
      <RouterLink to="/">首页</RouterLink>
      <RouterLink to="/rent">租房</RouterLink>
      <RouterLink to="/person" v-if="info.id!=-1">个人中心</RouterLink>
    </nav>
    <RouterLink to="/login" class="login-btn" v-if="info.id==-1">登陆</RouterLink>
  </header>
</template>
<style>
.header {
  padding: 20px 200px;
  line-height: 40px;
}

.logo {
  float: left;
  height: 40px;
  width: 40px;
  margin: auto;
}

.header-nav {
  margin-left: 30px;
  display: inline-block;
}

.header-nav a {
  display: inline-block;
  padding: 0 20px;
}

.login-btn {
  float: right;
  padding: 0 20px;
}
</style>