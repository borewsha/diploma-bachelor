import React from 'react'
import {Button} from 'antd'
import {useAppSelector} from 'shared/hooks'

const SendButton = () => {
    const isLoading = useAppSelector(state => state.travel.isLoading)
    const city = useAppSelector(state => state.travel.city)
    const cities = useAppSelector(state => state.city.data)

    return (
        <Button
            type="primary"
            style={{width: '100%', margin: '10px 0'}}
            htmlType="submit"
            loading={isLoading}
            disabled={!cities?.filter(c => c.name === city)[0]}
        >Создать</Button>
    )
}

export default SendButton