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
          <template v-if="column.dataIndex === 'braPhoto'">
              <img :src="record.braPhoto" alt="品牌图片" style="width: 30px; height:30px;"/>
          </template>
          <template v-if="column.dataIndex === 'operation'">
              <a-popconfirm v-if="dataSource.length" title="Sure to delete?" @confirm="onDelete(record.braName)">
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

        <div class="input-foreign" >
          <span>商品编号</span>
            <a-select
            ref="select"
            style="width: 35%;"
            v-model:value="inputComSpe.coNo"
            :options="coNoOption"
          ></a-select>

          <span>规格编号</span>
          
            <a-select
            ref="select"
            style="width: 35%;"
            v-model:value="inputComSpe.speNo"
            :options="speNoOption"
          ></a-select>
        </div>
      <a-input style="margin: 15px;" v-model:value="inputComSpe.comSpeDescription" placeholder="规格描述" />
  </a-modal>
  
</template>
<script lang="ts">
import { defineComponent, reactive, ref } from 'vue';
import type { Ref } from 'vue';
import { CheckOutlined, EditOutlined} from '@ant-design/icons-vue';
import axios from 'axios';
import { SelectProps, notification } from 'ant-design-vue';

interface DataItem {
  coNo: string;
  speNo: string;
  comSpeDescription: string;
}

export default defineComponent({
  components: {
      CheckOutlined,
      EditOutlined,
  },
  setup() {
      const columns = [
          {
              title: '商品编号',
              dataIndex: 'coNo',
          },
          {
              title: '规格编号',
              dataIndex: 'speNo',
          },
          {
              title: '规格描述',
              dataIndex: 'comSpeDescription',
          },
          {
              title:'操作',
              dataIndex:'operation'
          }
      ];
        
      const coNoOption: Ref<SelectProps['options']> = ref([]);
      const speNoOption: Ref<SelectProps['options']> = ref([]);

      const inputComSpe = reactive({
          coNo:'',
          speNo:'',
          comSpeDescription:''
      })

      let modalVisible = ref(false)

      const dataSource: Ref<DataItem[]> = ref([]);
      const copySource: Ref<DataItem[]> = ref([]);
      

      const recoverData = (e:any) => {
          console.log(e)
          if (e.data == null) {
              dataSource.value = copySource.value
          }
      }

      const freshData = () => {
          
          axios ({
              url:"http://localhost:8080/api/comspe/get/all",
              method:'GET',
          })
              .then((resp) => {
                  console.log(resp.data)
                  dataSource.value = resp.data
                  copySource.value = resp.data
              })

          axios ({
              url:"http://localhost:8080/api/commodity/get/all",
              method:'GET',
          })
              .then((resp) => {
                  const data = resp.data;
                  for (let i of data) {
                    coNoOption.value?.push({
                      value:i.coNo,
                      label:i.coNo,
                    })
                  }
              })

          axios ({
              url:"http://localhost:8080/api/specification/get/all",
              method:'GET',
          })
              .then((resp) => {
                  const data = resp.data;
                  for (let i of data) {
                    speNoOption.value?.push({
                      value:i.speNo,
                      label:i.speNo,
                    })
                  }
              })
          
      }

      freshData()

      let isModify = ref(false)
      let searchValue = ref('')


      const onSearch = () => {
           dataSource.value = dataSource.value.filter(item => item.comSpeDescription.includes(searchValue.value))
      }

      const onMofify = (record: any) => {

          modalVisible.value = true
          isModify.value = true
          inputComSpe.coNo = record.coNo
          inputComSpe.speNo = record.speNo
          inputComSpe.comSpeDescription = record.comSpeDescription
      }

      const handleOk = () => {

          if (isModify.value) {
              handleModify()
          } else {
              handleAdd()
          }
      }
      const onDelete = (braName: string) => {
          console.log('delete', braName)
      };
      const cleanInput = () => {
          isModify.value = false  
          inputComSpe.coNo = ''
          inputComSpe.speNo = ''
          inputComSpe.comSpeDescription = ''
      }

      const checkInput = () => {
          if ( 
            inputComSpe.comSpeDescription.length == 0
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
              url:'http://localhost:8080/api/comspe/update',
              method:'POST',
              params: {
                  coNo:inputComSpe.coNo,
                  speNo:inputComSpe.speNo,
                  comSpeDescription: inputComSpe.comSpeDescription,
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
                      cleanInput()
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
              url:'http://localhost:8080/api/comspe/insert',
              method:'POST',
              params:{
                  coNo:inputComSpe.coNo,
                  speNo:inputComSpe.speNo,
                  comSpeDescription:inputComSpe.comSpeDescription,
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
          isModify,
          inputComSpe,
          modalVisible,
          searchValue,
          cleanInput,
          onMofify,
          recoverData,
          coNoOption,
          speNoOption,
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
