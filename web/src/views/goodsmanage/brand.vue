<template>
    <div class="tool-bar">
        <a-button size="large" style="width: 15%; margin-right:20px" class="editable-add-btn" @click="handleAdd">Add</a-button>
        <a-input-search
          v-model:value="searchValue"
          placeholder="input search text"
          enter-button="Search"
          size="large"
          @search="onSearch"
        />
    </div>
    
    <a-table bordered :pagination="{ pageSize : 6}" :data-source="dataSource" :columns="columns" style="margin: 8px;">
        <template #bodyCell="{ column, record }">
            <template v-if="column.dataIndex === 'operation'">
                <a-popconfirm v-if="dataSource.length" title="Sure to delete?" @confirm="onDelete(record.key)">
                    <a>Delete</a>
                </a-popconfirm>
            </template>
        </template>
    </a-table>
</template>
<script lang="ts">
import { computed, defineComponent, reactive, ref } from 'vue';
import type { Ref, UnwrapRef } from 'vue';
import { CheckOutlined, EditOutlined } from '@ant-design/icons-vue';
import { cloneDeep } from 'lodash-es';

interface DataItem {
    key: string;
    name: string;
    age: number;
    address: string;
}

export default defineComponent({
    components: {
        CheckOutlined,
        EditOutlined,
    },
    setup() {
        const columns = [
            {
                title: 'name',
                dataIndex: 'name',
            },
            {
                title: 'age',
                dataIndex: 'age',
            },
            {
                title: 'address',
                dataIndex: 'address',
            },
            {
                title: 'operation',
                dataIndex: 'operation',
            },
        ];
        const dataSource: Ref<DataItem[]> = ref([
            {
                key: '0',
                name: 'Edward King 0',
                age: 32,
                address: 'London, Park Lane no. 0',
            },
            {
                key: '1',
                name: 'Edward King 1',
                age: 32,
                address: 'London, Park Lane no. 1',
            },
        ]);
        let searchValue = ref('')
        const onSearch = () => {

        }
        const count = computed(() => dataSource.value.length + 1);
        const editableData: UnwrapRef<Record<string, DataItem>> = reactive({});

        const edit = (key: string) => {
            editableData[key] = cloneDeep(dataSource.value.filter(item => key === item.key)[0]);
        };
        const save = (key: string) => {
            Object.assign(dataSource.value.filter(item => key === item.key)[0], editableData[key]);
            delete editableData[key];
        };

        const onDelete = (key: string) => {
            dataSource.value = dataSource.value.filter(item => item.key !== key);
        };
        const handleAdd = () => {
            const newData = {
                key: `${count.value}`,
                name: `Edward King ${count.value}`,
                age: 32,
                address: `London, Park Lane no. ${count.value}`,
            };
            dataSource.value.push(newData);
        };

        return {
            columns,
            onDelete,
            handleAdd,
            dataSource,
            editableData,
            searchValue,
            onSearch,
            count,
            edit,
            save,
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
  