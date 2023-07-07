import React from 'react'
import {Button} from 'antd'
import {useAppSelector} from 'shared/hooks'

const SendButton = () => {
    const isLoading = useAppSelector(state => state.travel.isLoading)
    const city = useAppSelector(state => state.travel.city)
    const cities = useAppSelector(state => state.city.data)
    const accommodation = useAppSelector(state => state.travel.accommodation)
    const attractions = useAppSelector(state => state.travel.attractions)
    const dates = useAppSelector(state => state.travel.dates)

    return (
        <Button
            type="primary"
            style={{width: '100%', margin: '10px 0'}}
            htmlType="submit"
            loading={isLoading}
            disabled={!(!!cities?.filter(c => c.id === city)[0]
                && !!accommodation
                && !!attractions.length
                && !dates.length)}
        >Создать</Button>
    )
}

export default SendButton