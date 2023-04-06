import {Typography} from 'antd'
import React from 'react'

const Hello = () => {
    return (
        <div style={{
            display: 'flex',
            height: '100vh',
            justifyContent: 'center',
            alignItems: 'center',
            flexDirection: 'column'
        }}>
            <Typography.Title>Страница-презентация</Typography.Title>
        </div>
    )
}

export default Hello