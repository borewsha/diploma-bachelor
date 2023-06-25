import React from 'react'
import {DatePicker, Form, message, Typography} from 'antd'
import 'dayjs/locale/ru'
import {useAppDispatch, useAppSelector} from 'shared/hooks'
import {createTravel} from 'features/travelSlice'
import CitySelect from 'features/createTravel/form/CitySelect'
import CreateTravelMap from 'widgets/createTravelMap/CreateTravelMap'
import AccommodationSelect from 'features/createTravel/form/AccommodationSelect'
import AttractionsSelect from 'features/createTravel/form/AttractionsSelect'
import SendButton from 'features/createTravel/form/SendButton'
import dayjs from 'dayjs'

const CreateTravel = () => {
    const dispatch = useAppDispatch()

    const city = useAppSelector(state => state.travel.city)

    const onFinish = async (data: any) => {
        console.log(data)
        await dispatch(createTravel({city}))
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
                    >
                        <CitySelect/>
                    </Form.Item>
                    <Form.Item
                        name="dates"
                        label="Даты поездки"
                    >
                        <DatePicker.RangePicker
                            style={{width: '100%'}}
                            placeholder={['Начало', 'Конец']}
                            disabledDate={date => date < dayjs(new Date()).subtract(1, 'day')}
                        />
                    </Form.Item>
                    <Form.Item
                        name="accommodation"
                        label="Ночлег"
                    >
                        <AccommodationSelect/>
                    </Form.Item>
                    <Form.Item
                        name="attractions"
                        label="Места для посещения"
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