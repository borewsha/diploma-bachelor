import React from 'react'
import CitySelect from './CitySelect'
import DateSelect from './DateSelect'
import AccommodationSelect from './AccommodationSelect'
import AttractionsSelect from './AttractionsSelect'
import SendButton from './SendButton'
import {Form, message} from 'antd'
import {createTravel} from 'slices/travelSlice'
import {useAppDispatch} from 'shared/hooks'
import {useNavigate} from 'react-router'

const CreateTravelForm = () => {
    const dispatch = useAppDispatch()
    const navigate = useNavigate()

    const onFinish = async (data: any) => {
        await dispatch(createTravel(data))
            .unwrap()
            .then(res => {
                if (res.status === 201) {
                    message.success('Путешествие успешно создано!')
                    navigate('/home')
                }
            })
            .catch(error => message.error(error.message + '!'))
    }

    const onFinishFailed = async () => {
        await message.error('Ошибка при заполнении формы!')
    }

    return (
        <Form name="createTravel" onFinish={onFinish} onFinishFailed={onFinishFailed} layout="vertical">
            <CitySelect/>
            <DateSelect/>
            <AccommodationSelect/>
            <AttractionsSelect/>
            <SendButton/>
        </Form>
    )
}

export default CreateTravelForm