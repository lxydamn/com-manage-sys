<template>

    <div class="login-box">
        <a-form
        :model="formState"
        name="normal_login"
        class="login-form"
        @finish="onFinish"
        @finishFailed="onFinishFailed"
      >

        <a-form-item
            label="用户账号"
            name="id"
            :rules="[{ required: true, message: '不能输入空用户账号' }]"
      >
        <a-input size="large"  v-model:value="formState.id">
          <template #prefix>
            <UserOutlined class="site-form-item-icon" />
          </template>
        </a-input>
      </a-form-item>
  
      <a-form-item
        label="用户密码"
        name="password"
        :rules="[{ required: true, message: '不能输入空密码' }]"
      >
        <a-input-password size="large" v-model:value="formState.password">
          <template #prefix>
            <LockOutlined class="site-form-item-icon" />
          </template>
        </a-input-password>
      </a-form-item>

      <a-form-item>
        <a-checkbox v-model:checked="is_supplier">供货商请选</a-checkbox>
      </a-form-item>
      
        <div class="login-btn-box">
            <a-form-item >
                <a-button type="primary" html-type="submit" class="login-form-button">
                  登录
                </a-button>
              </a-form-item>
      
              <a-form-item>
                  <a-button  type="primary" class="login-form-button">
                    <RouterLink to="register">
                        注册
                    </RouterLink>
                    
                  </a-button>
            </a-form-item>
        </div>
       
      </a-form>

    </div>
    
  </template>
  <script lang="ts">
  import { defineComponent, reactive, ref } from 'vue';
  import { useUserStore } from '../store/user';
  import axios from 'axios'
  import {notification} from 'ant-design-vue';
  import { UserOutlined, LockOutlined } from '@ant-design/icons-vue';
import router from '../router';
  interface FormState {
    id: string;
    password: string;
  }
  const userStore = useUserStore()
  export default defineComponent({
    components: {
      UserOutlined,
      LockOutlined,
    },
    setup() {

      const formState = reactive<FormState>({
        id: '',
        password: '',
      });

      

      const onFinish = (values: any) => {
        axios ({
          url:"http://localhost:8080/api/customer/login",
          method:'POST',
          params: {
            id: values.id,
            password: values.password
          }
        })
        .then((resp) => {
          const data = resp.data;
          if (data.error_info === 'success') {
              userStore.$patch({
                id: values.id,
                username: data.username,
                is_login:true,
              })

              router.push("index")
          } else {
            notification.error({
              message:'登录遇到错误',
              description:data.error_info
            })
          }
        })
        
      };
  
      const onFinishFailed = (errorInfo: any) => {
        console.log('Failed:', errorInfo);
      };

      return {
        formState,
        onFinish,
        onFinishFailed,
        is_supplier:ref(false),
      };
    },
  });
  </script>
  <style scoped>
    .login-box {
        height: 100vh;
        display: flex;
        align-items: center;
        justify-content: center;
        background-image: linear-gradient(to top, #accbee 0%, #e7f0fd 100%);
    }
    .login-form {
        width: 350px;
        display: flex;
        flex-direction: column;
        background-color: #fff;
        padding: 20px;
        border-radius: 10px;
    }
    .login-form-button {
        width: 120px;
        margin: 20px;
    }

    .login-btn-box {
        display: flex;
    }
  </style>
  