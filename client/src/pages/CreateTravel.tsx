import React from 'react'
import {Form, message, Typography} from 'antd'
import 'dayjs/locale/ru'
import {useAppDispatch, useAppSelector} from 'shared/hooks'
import {createTravel} from 'features/travelSlice'
import CitySelect from 'features/createTravel/form/CitySelect'
import CreateTravelMap from 'widgets/createTravelMap/CreateTravelMap'
import AccommodationSelect from 'features/createTravel/form/AccommodationSelect'
import AttractionsSelect from 'features/createTravel/form/AttractionsSelect'
import DateSelect from '../features/createTravel/form/DateSelect'
import SendButton from '../features/createTravel/form/SendButton'

const CreateTravel = () => {
    const dispatch = useAppDispatch()

    const isLoading = useAppSelector(state => state.city.isLoading)

    const onFinish = async (data: any) => {
        await dispatch(createTravel(data))
    }

    const onFinishFailed = async () => {
        await message.error('Ошибка при заполнении формы!')
    }

    return (
        <div style={{display: 'flex'}}>
            <div style={{
                padding: 16,
                width: '50%',
                overflowY: 'auto',
                height: window.innerHeight - 64
            }}>
                <Typography.Title>Создать поездку</Typography.Title>
                <Form name="createTravel" onFinish={onFinish} onFinishFailed={onFinishFailed} layout="vertical">
                    <Form.Item
                        name="city"
                        label="Город"
                        rules={[
                            {
                                required: true,
                                message: 'Заполните поле'
                            }
                        ]}
                    >
                        <CitySelect/>
                    </Form.Item>
                    <Form.Item
                        name="dates"
                        label="Даты поездки"
                        rules={[
                            {
                                required: true,
                                message: 'Заполните поле'
                            }
                        ]}
                    >
                        <DateSelect/>
                    </Form.Item>
                    <Form.Item
                        name="accommodation"
                        label="Ночлег"
                        rules={[
                            {
                                required: true,
                                message: 'Заполните поле'
                            }
                        ]}
                    >
                        <AccommodationSelect/>
                    </Form.Item>
                    <Form.Item
                        name="attractions"
                        label="Места для посещения"
                        rules={[
                            {
                                required: true,
                                message: 'Заполните поле'
                            }
                        ]}
                    >
                        <AttractionsSelect/>
                    </Form.Item>
                    <SendButton/>
                </Form>
            </div>
            <div style={{height: window.innerHeight - 64, width: '100%'}}>
                <CreateTravelMap/>
            </div>
        </div>
    )
}

export default CreateTravel