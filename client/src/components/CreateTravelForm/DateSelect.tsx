import React from 'react'
import dayjs from 'dayjs'
import {DatePicker, Form} from 'antd'

const DateSelect = () => {
    return (
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
    )
}

export default DateSelect