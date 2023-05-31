<template>

    <a-row >
        <a-col :span="4" style="align-items: center;">
            <svg t="1684248586760" class="icon" viewBox="0 0 1373 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="16751" width="64" height="64"><path d="M1199.519599 477.820944V454.543042A454.202958 454.202958 0 0 0 322.907891 288.758962 368.472149 368.472149 0 0 0 347.889054 1022.296739h738.079806a283.876848 283.876848 0 0 0 113.550739-544.475795z" fill="#24D6ED" p-id="16752"></path><path d="M1169.996407 506.208628V482.930727a454.202958 454.202958 0 0 0-752.273649-340.652218 454.202958 454.202958 0 0 0-95.950374 147.615961c-6.245291 0-11.922828 2.838768-18.168119 4.542029a454.202958 454.202958 0 0 0 517.791372 631.342111 286.715617 286.715617 0 0 0 113.550739 98.22139H1085.96886a283.876848 283.876848 0 0 0 185.087705-70.401458 283.876848 283.876848 0 0 0-101.060158-445.686652z" fill="#1EB6C1" p-id="16753"></path><path d="M1199.519599 477.820944V454.543042a454.202958 454.202958 0 1 0-349.736277 441.712376A283.876848 283.876848 0 1 0 1199.519599 477.820944z" fill="#22E6EF" p-id="16754"></path></svg>
            <span style="font-size: 20px; font-weight:900; color:dimgray; ">
                Hadoop云盘系统
            </span>
           
        </a-col>
        <a-col :span="20">
        <a-menu style="height: 64px;" v-model:selectedKeys="current" mode="horizontal">
            <a-menu-item key="mail">
                <template #icon>
                <mail-outlined />
                </template>
                Navigation One
            </a-menu-item>
            <a-menu-item key="app" disabled>
                <template #icon>
                <appstore-outlined />
                </template>
                Navigation Two
            </a-menu-item>
            </a-menu>   
        </a-col>

      </a-row>
    
  </template>
  <script lang="ts">
  import { defineComponent, ref } from 'vue';
  import { MailOutlined, AppstoreOutlined, SettingOutlined } from '@ant-design/icons-vue';
  export default defineComponent({
    components: {
      MailOutlined,
      AppstoreOutlined,
      SettingOutlined,
    },
    setup() {
      const current = ref<string[]>(['mail']);
      return {
        current,
      };
    },
  });
  </script>
  
  