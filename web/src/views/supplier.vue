<template>
  <div class="goods-record-box">
    <div class="goods-container">
      <div class="search-bar">
        <a-input-search
          v-model:value="goodsSerach"
          placeholder="输入商品名称"
          enter-button="Search"
          size="large"
          @search="onGoodsSearch"
          @change="recoverGoodsData"
      />
      </div>
      <div class="goods-content">
        <div class="goods-item" v-for="item in dataSource">
          <img :src="item.coPhoto" alt="商品图片"/>
          <div class="goods-info" >
            <p>{{item.coName}}</p>
            <p>库存：{{ item.coNum }}</p>
            <a-button type="primary" shape="circle" @click="handleAdd(item.coNo)">
              <template #icon><PlusCircleOutlined /></template>
            </a-button>
          </div>
        </div>
      </div>
    </div>
    <div class="record-container">
      <div class="search-bar">
        <a-input-search
          v-model:value="recordSerach"
          placeholder="输入供货商品名称"
          enter-button="Search"
          size="large"
          @search="onRecordSearch"
          @change="recoverRecordData"
        />
      </div>
      <div class="record-content">
        <a-table
          bordered 
          :pagination="{ pageSize : 6}" 
          :data-source="recordSource" 
          :columns="columns"
          style="margin: 8px;"
        />
      </div>
    </div>
  </div>
  <a-modal
      v-model:visible="modalVisible"
      title="输入供货数量"
      centered
      @ok="handleOK"
      @cancle="handleCancle"
    >
    <a-input-number style="margin: 15px; width:300px" v-model:value="input" placeholder="供货数量" />
  </a-modal>
</template>

<script lang="ts">
import axios from 'axios';
import { Ref, defineComponent, ref } from 'vue';
import {PlusCircleOutlined} from '@ant-design/icons-vue';
import { useUserStore } from '../store/user';
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

interface RecordItem {
  coNo:string;
  suNo:string;
  ordGoNo:string;
  ordGoNum:string;
  ordGoTime:string;
}

export default defineComponent({


  components: {
    PlusCircleOutlined,
  },

  setup() {
    let modalVisible = ref(false)
    let input = ref('') 
    let coNo = ref('')

    const dataSource: Ref<DataItem[]> = ref([]);
    const copySource: Ref<DataItem[]> = ref([]);

    const recordSource: Ref<RecordItem[]> = ref([]);
    const recordCopy: Ref<RecordItem[]> = ref([]);

    const recordSerach = ref('')
    const goodsSerach = ref('')
    const userStore = useUserStore()

    const columns = [
      
          {
              title:'单号',
              dataIndex:'ordGoNo',
          },
          {
              title: '商品编号',
              dataIndex: 'coNo',
          },
          {
              title:'数量',
              dataIndex:'ordGoNum',
          },
          {
              title:'时间',
              dataIndex:'ordGoTime',
          }
      ];
    

    const freshData = () => {
      axios ({
              url:"http://localhost:8080/api/commodity/get/all",
              method:'GET',
      })
        .then((resp) => {
            dataSource.value = resp.data
            copySource.value = resp.data
        })

      axios ({
              url:"http://localhost:8080/api/supplier/record",
              method:'GET',
      })
        .then((resp) => {
            console.log(resp.data)
            recordSource.value = resp.data
            recordCopy.value = resp.data
        })
    
    }
    freshData()

    const handleAdd = (no:string) => {
      modalVisible.value = true
      coNo.value = no
    }

    const handleOK = () => {
      modalVisible.value = false

      axios({
        url:'http://localhost:8080/api/supplier/add/num',
        method:'POST',
        params: {
          supplier: userStore.id,
          coNo:coNo.value,
          coNum: input.value,
        }
      })
        .then((resp) => {
          const data = resp.data;
          if (data.error_info == 'success') {
            notification.success({
              message:'供货成功',
            })
          }
          freshData()
          handleCancle()
        })
      
    }

    const recoverRecordData = (e:any) => {
      if (e.data == null) {
        recordSource.value = recordCopy.value;
      }
    }

    const recoverGoodsData = (e:any) => {
      if (e.data == null) {
        dataSource.value = copySource.value;
      }
    }

    const onGoodsSearch = () => {
      dataSource.value = dataSource.value.filter(item => item.coName.includes(goodsSerach.value))
    }

    const onRecordSearch = () => {
      recordSource.value = recordSource.value.filter(item => item.coNo.includes(recordSerach.value))
    }
    const handleCancle = () => {
      input.value = ''
      coNo.value = ''  
    }


    return {
      recordSerach,
      goodsSerach,
      onRecordSearch,
      onGoodsSearch,
      dataSource,
      columns,
      handleAdd,
      input,
      handleOK,
      modalVisible,
      handleCancle,
      recordSource,
      recoverRecordData,
      recoverGoodsData,
    }
  }


})
</script>

<style scoped>
  .goods-record-box {
    display: flex;
    padding: 1rem;
  }

  .goods-container {
    width: 60%;
  }

  .goods-item {
    height: 13rem;
    width: 13rem;
    margin: 1rem;
    text-align: center;
  }
  .goods-info p {
    display: flex;
    align-items: center;
    margin: 0;
  }
  
  .goods-info {
    display: flex;
    justify-content: space-around;
  }
  .goods-item:hover {
    border: 0.2rem solid #97cafd;
  }
  .goods-item img {
    width: 80%;
    height: 80%;
    transition: 0.2s;
  }
  .goods-content {
    display: grid;
    grid-template-columns: repeat(4,  25%);
    margin-top: 1rem;
    background-color: #FFF;
    border-radius: 0.5rem;
    height: 35rem;
    overflow: hidden auto;
  }
  .record-item {
    margin: 0.5rem;
    height: 2.5rem;
    border-radius: 5px;
  }
  .record-item:hover {
    background-color: #d0e7ff;
    color: #000;
  }
  .record-content ul {
    list-style: none;
    margin: 0.5rem;
    padding: 0;
  }

  .record-content {
    margin-top: 1rem;
    background-color: #FFF;
    border-radius: 0.5rem;
    height: 35rem;
    overflow: hidden auto;
  }
  .record-container {
    margin-left: 1rem;
    width: 40%;
  }
</style>