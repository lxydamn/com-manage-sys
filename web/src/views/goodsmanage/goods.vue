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
          <template v-if="column.dataIndex === 'coPhoto'">
              <img :src="record.coPhoto" alt="商品图片" style="width: 30px; height:30px;"/>
          </template>
          <template v-if="column.dataIndex === 'operation'">
              <a-popconfirm v-if="dataSource.length" title="Sure to delete?" @confirm="onDelete(record.coNo)">
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
    title="输入添加商品信息"
    style="top:20px"
    @ok="handleOk"
    @cancel="cleanInput"
  >
      <a-input style="margin-bottom: 10px;" v-model:value="inputCommodity.coNo" placeholder="商品编号" />
      <div class="input-foreign">
          <span>商品品牌</span>
          <a-select
          ref="select"
          style="width: 30%;"
          v-model:value="inputCommodity.braName"
          :options="braOption"
        ></a-select>
          <span>商品类别</span>
          <a-select
          ref="select"
          style="width: 30%;"
          v-model:value="inputCommodity.catName"
          :options="catOption"
        ></a-select>
      </div>
  
      <a-input style="margin-bottom: 10px;" v-model:value="inputCommodity.coName" placeholder="商品名称" />
      <a-input style="margin-bottom: 10px;" v-model:value="inputCommodity.coType" placeholder="商品型号" />
      <a-input style="margin-bottom: 10px;" v-model:value="inputCommodity.coJl" placeholder="计量单位" />
      <div class="a-input-prise-box">
        <a-input-number  id="inputNumber" v-model:value="inputCommodity.coMarketProse" placeholder="市场价" />
        <a-input-number  id="inputNumber" v-model:value="inputCommodity.coSalePrise"  placeholder="售价" />
        <a-input-number  id="inputNumber" v-model:value="inputCommodity.coCostPrise"  placeholder="成本价"/>
      </div>
      <a-input style="margin-bottom: 10px;" v-model:value="inputCommodity.coPhoto" placeholder="商品图片" />
      <a-input style="margin-bottom: 10px;" v-model:value="inputCommodity.coIntroduce" placeholder="商品介绍" />
  </a-modal>
  
</template>
<script lang="ts">
import type { SelectProps } from 'ant-design-vue';
import { defineComponent, reactive, ref } from 'vue';
import type { Ref } from 'vue';
import { CheckOutlined, EditOutlined} from '@ant-design/icons-vue';
import axios from 'axios';
import { notification } from 'ant-design-vue';

interface DataItem {
  coNo: string;
  braName:string;
  catName:string;
  coName: string;
  coType:string;
  coJl:string;
  coMarketProse:number;
  coSalePrise:number;
  coCostPrise:number;
  coPhoto:string;
  coIntroduce:string;
  coNum:number;
}

export default defineComponent({
  components: {
      CheckOutlined,
      EditOutlined,
  },
  setup() {
      const columns = [
          {
              title: '编号',
              dataIndex: 'coNo',
          },
          {
              title: '品牌',
              dataIndex: 'braName',
          },
          {
              title: '分类',
              dataIndex: 'catName',
          },
          {
              title: '名称',
              dataIndex: 'coName',
          },
          {
              title:'型号',
              dataIndex:'coType',
          },
          {
              title:'单位',
              dataIndex:'coType',
          },
          {
              title:'市场价',
              dataIndex:'coMarketProse',
          },
          {
              title:'售价',
              dataIndex:'coSalePrise',
          },
          {
              title:'成本',
              dataIndex:'coCostPrise',
          },
          {
              title:'图片',
              dataIndex:'coPhoto',
          },
          {
              title:'介绍',
              dataIndex:'coIntroduce',
          },
          {
              title:'库存',
              dataIndex:'coNum',
          },
          {
              title:'操作',
              dataIndex:'operation',
          },
      ];
      const inputCommodity = reactive({
        coNo: '',
        braName:'',
        catName:'',
        coName: '',
        coType:'',
        coJl:'',
        coMarketProse:'',
        coSalePrise:'',
        coCostPrise:'',
        coPhoto:'',
        coIntroduce:'',
        coNum:''
      })

      let modalVisible = ref(false)

      const dataSource: Ref<DataItem[]> = ref([]);
      const copySource: Ref<DataItem[]> = ref([]);

      const braOption: Ref<SelectProps['options']> = ref([]);
      const catOption: Ref<SelectProps['options']> = ref([]);

      const recoverData = (e:any) => {
          console.log(e)
          if (e.data == null) {
              dataSource.value = copySource.value
          }
      }

      const freshData = () => {
          
          axios ({
              url:"http://localhost:8080/api/commodity/get/all",
              method:'GET',
          })
              .then((resp) => {
                  console.log(resp.data)
                  dataSource.value = resp.data
                  copySource.value = resp.data
              })
          
          axios ({
              url:"http://localhost:8080/api/brand/get/all",
              method:'GET',
          })
              .then((resp) => {
                  const data = resp.data;
                  for (let i of data) {
                    braOption.value?.push({
                      value:i.braName,
                      label:i.braName,
                    })
                  }
              })

          axios ({
              url:"http://localhost:8080/api/category/get/all",
              method:'GET',
          })
              .then((resp) => {
                  const data = resp.data;
                  for (let i of data) {
                    catOption.value?.push({
                      value:i.catName,
                      label:i.catName,
                    })
                  }
              })
      }

      freshData()

      let oldCoNo = ''
      let isModify = ref(false)
      let searchValue = ref('')


      const onSearch = () => {
           dataSource.value = dataSource.value.filter(item => item.coName.includes(searchValue.value))
      }

      const onMofify = (record: any) => {

          modalVisible.value = true
          isModify.value = true
          oldCoNo = record.coNo
          inputCommodity.coNo = record.coNo
          inputCommodity.braName = record.braName
          inputCommodity.catName = record.catName
          inputCommodity.coName = record.coName
          inputCommodity.coType = record.coType
          inputCommodity.coJl = record.coJl
          inputCommodity.coMarketProse = record.coMarketProse
          inputCommodity.coSalePrise = record.coSalePrise
          inputCommodity.coCostPrise = record.coCostPrise
          inputCommodity.coPhoto=record.coPhoto
          inputCommodity.coIntroduce = record.coIntroduce
          inputCommodity.coNum = record.coNum
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
          // axios({
          //     url:'http://localhost:8080/api/brand/delete',
          //     method:'POST',
          //     params: {
          //         braName:braName,
          //     }
          // })
          //     .then((resp) => {
          //         const data = resp.data
          //         if (data.error_info === 'success') {
          //             notification.success({
          //                 message:'删除成功',
          //             })
          //             freshData()
          //         } else {
          //             notification.error({
          //                 message:'删除失败',
          //                 description:data.error_info,
          //             })
          //         }
          //     })

      };
      const cleanInput = () => {
          isModify.value = false  
          inputCommodity.coNo = ''
          inputCommodity.braName = ''
          inputCommodity.catName = ''
          inputCommodity.coName = ''
          inputCommodity.coType = ''
          inputCommodity.coJl = ''
          inputCommodity.coMarketProse = ''
          inputCommodity.coSalePrise = ''
          inputCommodity.coCostPrise = ''
          inputCommodity.coPhoto=''
          inputCommodity.coIntroduce = ''
          inputCommodity.coNum = ''
          oldCoNo = ''
      }

      const checkInput = () => {
        console.log(inputCommodity)
          if ( 
              inputCommodity.coNo.length == 0 
              || inputCommodity.braName.length == 0 
              || inputCommodity.catName.length == 0 
              || inputCommodity.coName.length == 0 
              || inputCommodity.coType.length == 0 
              || inputCommodity.coJl.length == 0 
              || inputCommodity.coMarketProse.length == 0 
              || inputCommodity.coSalePrise.length == 0 
              || inputCommodity.coCostPrise.length == 0 
              || inputCommodity.coPhoto.length == 0
              || inputCommodity.coIntroduce.length == 0 
              || inputCommodity.coIntroduce.length == 0 
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
              url:'http://localhost:8080/api/commodity/update',
              method:'POST',
              params: {
                  oldCoNo:oldCoNo,
                  newCoNo:inputCommodity.coNo,
                  braName:inputCommodity.braName,
                  catName:inputCommodity.catName,
                  coName:inputCommodity.coName,
                  coType:inputCommodity.coType,
                  coJl:inputCommodity.coJl,
                  coMarketProse:inputCommodity.coMarketProse,
                  coSalePrise:inputCommodity.coSalePrise,
                  coCostPrise:inputCommodity.coCostPrise,
                  coPhoto:inputCommodity.coPhoto,
                  coIntroduce:inputCommodity.coIntroduce,
                  coNum:inputCommodity.coNum,
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
                      oldCoNo = ''
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
              url:'http://localhost:8080/api/commodity/insert',
              method:'POST',
              params:{
                  coNo:inputCommodity.coNo,
                  braName:inputCommodity.braName,
                  catName:inputCommodity.catName,
                  coName:inputCommodity.coName,
                  coType:inputCommodity.coType,
                  coJl:inputCommodity.coJl,
                  coMarketProse:inputCommodity.coMarketProse,
                  coSalePrise:inputCommodity.coSalePrise,
                  coCostPrise:inputCommodity.coCostPrise,
                  coPhoto:inputCommodity.coPhoto,
                  coIntroduce:inputCommodity.coIntroduce,
                  coNum:0,
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
          catOption,
          braOption,
          inputCommodity,
          modalVisible,
          searchValue,
          cleanInput,
          onMofify,
          recoverData,
          onSearch,
      };
  },
});
</script>

<style scoped>
  .a-input-prise-box {
    display: flex;
    justify-content: space-between;
    margin-bottom: 10px;
  }
 
  
  .tool-bar {
      display: flex;
      padding: 15px;
  }
  .content {
      padding: 10px;
  }
</style>
