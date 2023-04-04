<template>
  <div>
    ” {{user.nickname}}  “  的订单管理
  </div>
</template>

<script>
export default {
  name: "Order",
  data() {
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
    }
  },
}
</script>

<style scoped>

</style>