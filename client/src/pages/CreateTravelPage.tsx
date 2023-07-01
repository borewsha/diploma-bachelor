import React from 'react'
import {Typography} from 'antd'
import 'dayjs/locale/ru'
import CreateTravelMap from 'components/CreateTravelMap/CreateTravelMap'
import CreateTravelForm from 'components/CreateTravelForm/CreateTravelForm'

const CreateTravelPage = () => {

    return (
        <div style={{display: 'flex'}}>
            <div style={{
                padding: 16,
                width: '50%',
                overflowY: 'auto',
                height: window.innerHeight - 64
            }}>
                <Typography.Title>Создать поездку</Typography.Title>
                <CreateTravelForm/>
            </div>
            <div style={{height: window.innerHeight - 64, width: '100%'}}>
                <CreateTravelMap/>
            </div>
        </div>
    )
}

export default CreateTravelPage