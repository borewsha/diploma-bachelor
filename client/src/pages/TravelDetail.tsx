import React, {useState} from 'react'
import {Calendar, Typography} from 'antd'
import dayjs, {Dayjs} from 'dayjs'
import Map from 'entities/Map'
import {Place} from 'shared/entities'
import {useAppSelector} from 'shared/hooks'

const TravelDetail = () => {
    const getListData = (value: Dayjs) => {
        let listData: Place[] | undefined
        listData = travel?.days.filter(day => value.toString() === day.date.toString())[0]?.places

        return listData || []
    }

    const [way, setWay] = useState<any>([])
    const travel = useAppSelector(state => state.travel.data)

    const dateCellRender = (value: Dayjs) => {
        const listData = getListData(value)
        return (
            <div className="events">
                {listData.map((item, index) => (
                    <li key={item.osmId}>
                        <Typography.Text
                            ellipsis
                        >{item.name ? item.name + ' (' + item.address + ')' : item.address}
                        </Typography.Text>
                    </li>
                ))}
            </div>
        )
    }

    return (
        <div style={{display: 'flex'}}>
            <div style={{width: '50%', padding: 16}}>
                <Typography.Title>{travel?.city.name}</Typography.Title>
                <Calendar
                    dateCellRender={dateCellRender}
                    // @ts-ignore
                    validRange={travel?.dates.map(date => dayjs(date))}
                    onSelect={date => setWay(getListData(date))}
                />
            </div>
            <div style={{width: '50%'}}>
                <div style={{height: window.innerHeight - 64, width: '100%'}}>
                    <Map
                        style={{width: '100%', height: '100%', position: 'relative'}}
                        // @ts-ignore
                        center={[travel?.accommodation.lat, travel?.accommodation.lon]}
                        zoom={13}
                        accommodation={travel?.accommodation}
                        places={way}
                        routePoints={[travel?.accommodation, ...way, travel?.accommodation]}
                    />
                </div>
            </div>
        </div>
    )
}

export default TravelDetail