<template>
  <div>
    <ul v-for="product in products">
      <li>{{ product.type }}</li>
      <li>{{ product.name }}</li>
      <li>{{ product.price }}</li>
      <li>{{ product.qt }}</li>
    </ul>
  </div>
</template>


<script setup>
import { onBeforeMount } from "vue";
//import Loading from "../components/Loading.vue";
// nu am reusit sa pun cu v-if intr-un div parinte la acel v-for div cu conditia "products.length"

const products = [];

function fetchData() {
  fetch("http://localhost:3000/products")
    .then((res) => res.json())
    .then((data) =>
      data.forEach((dataObj) => {
        products.push(dataObj);
      })
    )
    .catch((err) => console.log(err.mesage));
};

onBeforeMount(() => {
  fetchData();
  console.log(products);
});
</script>
