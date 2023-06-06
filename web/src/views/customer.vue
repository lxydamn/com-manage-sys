<template>
  <div class="goods-record-box">
    
    <div class="shop-container">
      <div class="shop-content">
        <ul class="shop-items">
          <li class="shop-item" v-for="item in shopGoods">
            <span style="width: 50%;">{{ item.coName}}</span>
            <span style="width: 40%;">{{ item.coNum }}</span>
            <a-button style="" shape="circle" @click="handleSub(item)">
              <template #icon><MinusCircleOutlined /></template>
            </a-button>
          </li>
        </ul>
  
      </div>
      <div class="shop-btn">
        <span>合记：{{count}}元</span> 
        <a-popconfirm title="确认下单" @confirm="confirm" @cancel="cancel">
          <a-button type="primary" style="width: 10rem;">下单</a-button>
        </a-popconfirm>
      </div>
    </div>
    

    <div class="goods-container">
      <div class="goods-content">
        <div class="goods-item" v-for="item in dataSource">
          <img :src="item.coPhoto" alt="商品图片"/>
          <div class="goods-info" >
            <p>{{item.coName}}</p>
            <p>库存：{{ item.coNum }}</p>
            <a-button shape="circle" @click="handleAdd(item)">
              <template #icon><PlusCircleOutlined /></template>
            </a-button>
          </div>
        </div>
      </div>
    </div>
    <div class="record-container">
      <div class="search-bar">
        <a-input-search
          v-model:value="goodsSerach"
          placeholder="输入订单编号"
          enter-button="Search"
          size="large"
          @search="onGoodsSearch"
          @change="recoverGoodsData"
         />
      </div>
      <div class="record-content">
        <ul class="order-items">
          <li class="order-item" v-for="item in orderSource">
            {{ item.cusNo }}
          </li>
        </ul>
      </div>
    </div>
  </div>

  <a-modal
      v-model:visible="modalVisible"
      title="输入购买数量"
      centered
      @ok="handleOK"
      @cancle="handleCancle"
    >
    <a-input-number style="margin: 15px; width:300px" v-model:value="input" placeholder="购买数量" />
  </a-modal>
</template>

<script lang="ts">
import axios from 'axios';
import { Ref, defineComponent, ref } from 'vue';
import {PlusCircleOutlined, MinusCircleOutlined} from '@ant-design/icons-vue';
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

interface Order {
  ordNo:string;
  cusNo:string;
  ordTime:string;
  ordSumPrise:number;
}

interface ShopGoods {
  coNo:String;
  coName:String;
  coNum:number;
  coPrise:number;
}

export default defineComponent({
  components: {
    PlusCircleOutlined,
    MinusCircleOutlined,
  },

  setup() {
    const userStore = useUserStore()
    let count = ref(0.0)
    let modalVisible = ref(false)
    let input = ref('') 
    let coNo = ref('')
    let coName = ref('')
    let coPrise = ref('')

    const orderSource: Ref<Order []> = ref([]);
    const shopGoods: Ref<ShopGoods []> = ref([]);
    const dataSource: Ref<DataItem[]> = ref([]);
    const copySource: Ref<DataItem[]> = ref([]);

    const clearSumbit = () => {
      shopGoods.value = []
    }

    const confirm = () => {

      if (shopGoods.value.length == 0) {
        notification.warn({
          message:'下单失败',
          description:'未选商品，无效下单'
        })

        return
      }

      axios({
        url:'http://localhost:8080/api/customer/submit',
        method:'POST',
        params: {
          cusNo:userStore.id,
          coms:JSON.stringify(shopGoods.value)
        }
      })
        .then((resp) => {
          const data = resp.data

          if (data.error_info === 'success') {

            notification.success({
              message:'下单成功',
            })
            clearSumbit()
          } 
        })
        .catch(() => {

            notification.error({
              message:'下单失败',
              description:'部分商品库存不足'
            })
        })
      
      return new Promise(resolve => {
        setTimeout(() => resolve(true), 1000);
      });
    };

    const cancel = (e: MouseEvent) => {
      console.log(e);
    };

    const goodsSerach = ref('')

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
              url:"http://localhost:8080/api/customer/shop/record",
              method:'GET',
              params: {
                cusNo:userStore.id,
              }
      })
        .then((resp) => {
            console.log(resp.data)
            orderSource.value = resp.data
        })
    
    }
    freshData()

    const handleSub = (item : any) => {
      for (let i of shopGoods.value) {
        if (i.coNo == item.coNo) {
          i.coNum --
          count.value = count.value - item.coPrise
          if (i.coNum == 0) {
            shopGoods.value = shopGoods.value.filter(it => it.coNo != i.coNo)
          }

          break
        }
      }
    }

    const handleAdd = (item:any) => {
      modalVisible.value = true
      coNo.value = item.coNo
      coName.value = item.coName
      coPrise.value = item.coSalePrise
    }

    const handleOK = () => {
      modalVisible.value = false
      
      let hasNo = false;
      for (let i of shopGoods.value) {
        if (i.coNo == coNo.value) {
          i.coNum += parseInt(input.value)
          hasNo = true
          break
        }
      }

      if (!hasNo) {
          shopGoods.value.push({
          coNo:coNo.value,
          coNum:parseInt(input.value),
          coName:coName.value,
          coPrise:parseFloat(coPrise.value),
        })
        count.value += parseFloat(coPrise.value) * parseInt(input.value)
      } 
      
      handleCancle()

    }

    const recoverGoodsData = (e:any) => {
      if (e.data == null) {
        dataSource.value = copySource.value;
      }
    }

    const onGoodsSearch = () => {
      dataSource.value = dataSource.value.filter(item => item.coName.includes(goodsSerach.value))
    }
    
    const handleCancle = () => {
      input.value = ''
      coNo.value = ''  
    }


    return {
      goodsSerach,
      onGoodsSearch,
      dataSource,
      orderSource,
      handleSub,
      columns,
      handleAdd,
      confirm,
      cancel,
      count,
      input,
      handleOK,
      modalVisible,
      handleCancle,
      recoverGoodsData,
      shopGoods,
    }
  }
})

</script>

<style scoped>
  .shop-btn {
    margin-bottom: 0.3rem;
    padding: 0.5rem;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .shop-items {
    list-style: none;
    padding: 0.2rem;
  }
  .shop-item {
    height: 2.5rem;
    transition: 0.2s;
    line-height: 2.5rem;
    padding: 0.25rem;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .shop-item:hover {
    height: 3rem;
    line-height: 3rem;
    background-color: #d3e3f4;
  }
  .shop-content {
    height: 30rem;
    border-radius: 5px;
    overflow: hidden auto;
  }
  .shop-container {
    width: 20%;
    display: flex;
    margin-right: 1rem;
    flex-direction: column;
    justify-content: space-between;
    background-color: #fff;
  }
  .goods-record-box {
    display: flex;
    padding: 1rem;
  }

  .goods-container {
    width: 60%;
  }

  .goods-item {
    height: 12rem;
    width: 12rem;
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
    background-color: #FFF;
    border-radius: 0.5rem;
    height: 40rem;
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
    height: 36.5rem;
    overflow: hidden auto;
  }
  .record-container {
    margin-left: 1rem;
    width: 20%;
  }
</style>