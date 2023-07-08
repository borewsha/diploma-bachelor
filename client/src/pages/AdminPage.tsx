import React, {useEffect} from 'react'
import {Button, Table, Typography, Upload} from 'antd'
import {UploadOutlined} from '@ant-design/icons'
import {getAccessToken} from 'shared/helpers/jwt'
import {useAppDispatch, useAppSelector} from 'shared/hooks'
import {getCitiesAdm} from '../slices/citiesSlice'
import {City} from 'shared/entities'
import instance from '../shared/api'

const AdminPage = () => {
    const dispatch = useAppDispatch()

    useEffect(() => {
        dispatch(getCitiesAdm({page: 0, city: ''}))
    }, [])

    const data = useAppSelector(state => state.city.dataAdm)
    const isLoading = useAppSelector(state => state.city.isLoading)

    return (
        <div style={{display: 'flex', flexDirection: 'column', padding: 16}}>
            <Typography.Title>Города</Typography.Title>
            <Table
                columns={[
                    {
                        title: 'Город',
                        dataIndex: 'city',
                        key: 'city'
                    },
                    {
                        title: 'Картинка',
                        dataIndex: 'picture',
                        key: 'picture'
                    },
                    {
                        title: 'Загрузка',
                        dataIndex: 'download',
                        key: 'download'
                    }
                ]}
                dataSource={data.data.map((city: City) => ({
                    city: city.name,
                    picture: city.imageId
                        ? <img key={city.id} height={100} src={'http://localhost:8080/api/images/' + city.imageId}/>
                        : <div key={city.id}>Фото отсутствует</div>,
                    download: <Upload
                        key={city.id}
                        name="multipartFile"
                        action="http://localhost:8080/api/images"
                        headers={{
                            Authorization: `Bearer ${getAccessToken()}`
                        }}
                        onChange={info => {
                            if (info.file.status === 'done') {
                                instance.patch(`/cities/${city.id}`, {imageId: info.file.response.id})
                            }
                        }}
                    >
                        <Button icon={<UploadOutlined/>}>
                            Нажмите для загрузки
                        </Button>
                    </Upload>
                }))}
                pagination={{
                    current: data.current,
                    total: data.size,
                    showSizeChanger: false,
                    onChange: (page) => {
                        dispatch(getCitiesAdm({page, city: ''}))
                    }
                }}
                loading={isLoading}
                // @ts-ignore
                rowKey={(record, index) => record.city}
            />
        </div>
    )
}

export default AdminPage