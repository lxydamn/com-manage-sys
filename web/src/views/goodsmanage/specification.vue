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
            <template v-if="column.dataIndex === 'operation'">
                <a-popconfirm v-if="dataSource.length" title="Sure to delete?" @confirm="onDelete(record.speNo)">
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
        <a-input style="margin: 15px;" v-model:value="inputSpecification.speNo" placeholder="规格编号" />
        <a-input style="margin: 15px;" v-model:value="inputSpecification.speName" placeholder="规格名称" />
    </a-modal>
    
</template>
<script lang="ts">
import { defineComponent, reactive, ref } from 'vue';
import type { Ref } from 'vue';
import { CheckOutlined, EditOutlined} from '@ant-design/icons-vue';
import axios from 'axios';
import { notification } from 'ant-design-vue';

interface DataItem {
    speNo: string;
    speName: string;
}

export default defineComponent({
    components: {
        CheckOutlined,
        EditOutlined,
    },
    setup() {
        const columns = [
            {
                title: '规格编号',
                dataIndex: 'speNo',
            },
            {
                title: '规格名称',
                dataIndex: 'speName',
            },
            {
                title:'操作',
                dataIndex:'operation'
            }
        ];
        const inputSpecification = reactive({
            speNo:'',
            speName:''
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
                url:"http://localhost:8080/api/specification/get/all",
                method:'GET',
            })
                .then((resp) => {
                    console.log(resp.data)
                    dataSource.value = resp.data
                    copySource.value = resp.data
                })
            
        }

        freshData()

        let oldSpeNo=''
        let isModify = ref(false)
        let searchValue = ref('')


        const onSearch = () => {
             dataSource.value = dataSource.value.filter(item => item.speNo.includes(searchValue.value))
        }

        const onMofify = (record: any) => {

            modalVisible.value = true
            isModify.value = true
            oldSpeNo = record.speNo
            inputSpecification.speNo = record.speNo
            inputSpecification.speName = record.speName
        }

        const handleOk = () => {

            if (isModify.value) {
                handleModify()
            } else {
                handleAdd()
            }
        }
        const onDelete = (SpeNo: string) => {
            console.log('delete', SpeNo)
            // axios({
            //     url:'http://localhost:8080/api/Specification/delete',
            //     method:'POST',
            //     params: {
            //         SpeNo:SpeNo,
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
            inputSpecification.speNo = ''
            inputSpecification.speName = ''
            oldSpeNo = ''
        }

        const checkInput = () => {
            if ( 
                inputSpecification.speNo.length == 0 
                || inputSpecification.speName.length == 0 
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
                url:'http://localhost:8080/api/specification/update',
                method:'POST',
                params: {
                    oldSpeNo:oldSpeNo,
                    newSpeNo:inputSpecification.speNo,
                    speName: inputSpecification.speName,
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
                        oldSpeNo = ''
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
                url:'http://localhost:8080/api/specification/insert',
                method:'POST',
                params:{
                    speNo:inputSpecification.speNo,
                    speName:inputSpecification.speName,
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
            inputSpecification,
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
    .tool-bar {
        display: flex;
        padding: 15px;
    }
    .content {
        padding: 10px;
    }
</style>
  