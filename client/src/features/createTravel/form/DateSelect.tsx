import React from 'react'
import dayjs from 'dayjs'
import {DatePicker} from 'antd'

const DateSelect = () => {
    return (
        <DatePicker.RangePicker
            style={{width: '100%'}}
            placeholder={['Начало', 'Конец']}
            disabledDate={date => date < dayjs(new Date()).subtract(1, 'day')}
        />
    )
}

export default DateSelect