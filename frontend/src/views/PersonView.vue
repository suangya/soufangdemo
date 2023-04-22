<script setup>
import {getUserInfo} from "@/utils/http";
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
  <div>用户名： {{info.name}}</div>
</template>