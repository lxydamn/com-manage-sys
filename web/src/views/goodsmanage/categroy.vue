<template>
  <div class="tool-bar">
      <a-button size="large" style="width: 15%; margin-right:20px" class="editable-add-btn" @click="modalVisible=true">添加</a-button>
      <a-input-search
        v-model:value="searchValue"
        placeholder="输入内容"
        enter-button="Search"
        size="large"
        @search="onSearch"
        @change="recoverData"
      />
  </div>
  
  <a-table bordered :pagination="{ pageSize : 6}" :data-source="dataSource" :columns="columns" style="margin: 8px;">
      <template #bodyCell="{ column, record }">
          <template v-if="column.dataIndex === 'catPhoto'">
              <img :src="record.catPhoto" alt="分类图片" style="width: 30px; height:30px;"/>
          </template>
          <template v-if="column.dataIndex === 'operation'">
              <a-popconfirm v-if="dataSource.length" title="Sure to delete?" @confirm="onDelete(record.catName)">
                  <a>删除</a>
              </a-popconfirm>
                <span style="margin: 15px;">
                  <a @click="onMofify(record)">编辑</a>
                </span>

          </template>
      </template>
  </a-table>

  <a-modal
    v-model:visible="modalVisible"
    :maskClosable="false"
    title="输入添加品牌信息"
    centered
    @ok="handleOk"
    @cancel="cleanInput"
  >
      <a-input style="margin: 15px;" v-model:value="inputCategory.catName" placeholder="分类名" />
      <a-input style="margin: 15px;" v-model:value="inputCategory.catPhoto" placeholder="分类图片" />
  </a-modal>
  
</template>
<script lang="ts">
import { defineComponent, reactive, ref } from 'vue';
import type { Ref } from 'vue';
import { CheckOutlined, EditOutlined} from '@ant-design/icons-vue';
import axios from 'axios';
import { notification } from 'ant-design-vue';

interface DataItem {
  catName: string;
  catPhoto: string;
}

export default defineComponent({
  components: {
      CheckOutlined,
      EditOutlined,
  },
  setup() {
      const columns = [
          {
              title: '分类名称',
              dataIndex: 'catName',
          },
          {
              title: '品牌图片',
              dataIndex: 'catPhoto',
          },
          {
              title:'操作',
              dataIndex:'operation'
          }
      ];
      const inputCategory = reactive({
          catName:'',
          catPhoto:''
      })
      let modalVisible = ref(false)
      const dataSource: Ref<DataItem[]> = ref([]);
      const copySource: Ref<DataItem[]> = ref([]);
      
      const freshData = () => {
          
          axios ({
              url:"http://localhost:8080/api/category/get/all",
              method:'GET',
          })
              .then((resp) => {
                  console.log(resp.data)
                  dataSource.value = resp.data
                  copySource.value = resp.data
              })
          
      }

      freshData()
      let oldCatName=''
      let isModify = ref(false)
      let searchValue = ref('')

      const onSearch = () => {
       dataSource.value =  dataSource.value.filter(item => item.catName.includes(searchValue.value))
      }

      const recoverData = (e:any) => {
        if (e.data == null) {
          dataSource.value = copySource.value
        }
      }

      const onMofify = (record: any) => {

          modalVisible.value = true
          isModify.value = true
          oldCatName = record.catName
          inputCategory.catName = record.catName
          inputCategory.catPhoto = record.catPhoto
      }

      const handleOk = () => {

          if (isModify.value) {
              handleModify()
          } else {
              handleAdd()
          }
      }
      const onDelete = (catName: string) => {
          console.log('delete', catName)
          axios({
              url:'http://localhost:8080/api/category/delete',
              method:'POST',
              params: {
                  catName:catName,
              }
          })
              .then((resp) => {
                  const data = resp.data
                  if (data.error_info === 'success') {
                      notification.success({
                          message:'删除成功',
                      })
                      freshData()
                  } else {
                      notification.error({
                          message:'删除失败',
                          description:data.error_info,
                      })
                  }
              })

      };
      const cleanInput = () => {
          isModify.value = false  
          inputCategory.catName = ''
          inputCategory.catPhoto = ''
          oldCatName = ''
      }

      const checkInput = () => {
          if ( 
              inputCategory.catName.length == 0 
              || inputCategory.catPhoto.length == 0 
          ) return false

          return true
      }

      const handleModify = () => {

          if (!checkInput()) {
              notification.warn({
                  message:"输入信息不能为空"
              })

              return
          }

          axios({
              url:'http://localhost:8080/api/category/update',
              method:'POST',
              params: {
                  oldCatName:oldCatName,
                  newCatName:inputCategory.catName,
                  catPhoto: inputCategory.catPhoto,
              }
          })
              .then((resp) => {
                  const data = resp.data

                  if( data. error_info === 'success') {
                      notification.success({
                          message:'修改成功'
                      })
                      isModify.value = false
                      modalVisible.value = false
                      oldCatName =''
                      freshData()
                  } else {
                      notification.error({
                          message:'修改失败',
                          description:data.error_info
                      })
                  }
              })
      }
      const handleAdd = () => {

          if (!checkInput()) {
              notification.warn({
                  message:"输入信息不能为空"
              })

              return
          }

          modalVisible.value = false
          axios({
              url:'http://localhost:8080/api/category/insert',
              method:'POST',
              params:{
                  catName:inputCategory.catName,
                  catPhoto:inputCategory.catPhoto,
              }
          })
              .then((resp) => {
                  const data = resp.data

                  if (data.error_info === 'success') {
                      notification.success({
                          message:'添加成功',
                      })
                      freshData()
                  } else {
                      notification.error({
                          message:'添加失败',
                          description:data.error_info,
                      })
                  }

                  cleanInput()
              })
      };

      return {
          columns,
          onDelete,
          handleOk,
          dataSource,
          inputCategory,
          modalVisible,
          searchValue,
          recoverData,
          cleanInput,
          onMofify,
          onSearch,
      };
  },
});
</script>

<style scoped>
  .tool-bar {
      display: flex;
      padding: 15px;
  }
  .content {
      padding: 10px;
  }
</style>
