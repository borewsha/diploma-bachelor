import React, {useEffect, useState} from 'react'
import {Calendar, ConfigProvider, Typography} from 'antd'
import dayjs, {Dayjs} from 'dayjs'
import Map from 'components/Map/Map'
import {useAppDispatch, useAppSelector} from 'shared/hooks'
import {useLocation} from 'react-router'
import {RightCircleTwoTone} from '@ant-design/icons'
import {Place} from 'shared/entities'
import './TravelDetail.modules.css'
import {selectWay} from 'slices/travelSlice'
import {setCenter} from 'slices/mapSlice'
import ru from 'antd/locale/ru_RU'
import 'dayjs/locale/ru';
dayjs.locale('ru');

const TravelDetail = () => {
    const {pathname} = useLocation()

    const [way, setWay] = useState<{
        places: Place[], ways: {
            type: 'car' | 'foot',
            points: [number, number][]
        }[]
    } | null>(null)

    const isLoading = useAppSelector(state => state.travel.isLoading)

    useEffect(() => {
        // @ts-ignore
        // dispatch(getTravel(pathname.split('/').at(-1)))
        dispatch(setCenter([travel?.accommodation.lat, travel?.accommodation.lon]))
    }, [])

    const travel = useAppSelector(state => state.travel.data)
    const selectedWay = useAppSelector(state => state.travel.selectedWay)
    const zoom = useAppSelector(state => state.map.zoom)
    const center = useAppSelector(state => state.map.center)

    const dispatch = useAppDispatch()

    const getDay = (value: Dayjs) => {
        let day = travel?.days.filter(day => dayjs(`${value.year()}-${value.month() + 1}-${value.date()}`).toString() === dayjs(day.date).toString())

        // @ts-ignore
        return !!day[0] ? day[0] : undefined
    }

    const dateCellRender = (value: Dayjs) => {
        const day = getDay(value)

        if (isLoading) {
            return null
        }

        return (
            <div style={{overflow: 'hidden'}}>
                {day?.places.map((item, index) => (
                    <li key={'item.id' + index}>
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
                <Typography.Title>Владивосток</Typography.Title>
                <ConfigProvider locale={ru}>
                <Calendar
                    dateCellRender={dateCellRender}
                    // @ts-ignore
                    validRange={[dayjs(travel.dates[0]), dayjs(travel.dates[1]).add(1, 'days')]}
                    onSelect={date => {
                        dispatch(selectWay(0))
                        dispatch(setCenter([travel?.accommodation.lat, travel?.accommodation.lon]))
                        let data = getDay(date)
                        if (!!data?.places.length) {
                            setWay({
                                // @ts-ignore
                                places: [travel?.accommodation, ...data.places, travel?.accommodation],
                                ways: data.ways
                            })
                        } else {
                            setWay({places: [], ways: []})
                        }
                    }}
                    mode='month'
                /></ConfigProvider>
            </div>
            <div style={{width: '100%'}}>
                <div style={{height: window.innerHeight - 64, width: '100%'}}>
                    <div style={{
                        position: 'absolute',
                        top: 10 + 64,
                        right: 10,
                        width: 380,
                        background: 'white',
                        zIndex: 1000,
                        borderRadius: 8,
                        border: '2px solid #33333340',
                        backgroundClip: 'padding-box',
                        padding: 8,
                        overflowY: 'auto'
                    }}>
                        <Typography.Title level={3}>Маршрут</Typography.Title>
                        {
                            !!way?.places.length && way.places.map((w: any, i: number) => {
                                if (i + 1 < way.places.length) {
                                    return <div
                                        style={{
                                            border: '1px solid #ddd',
                                            padding: 8,
                                            borderRadius: 8,
                                            marginBottom: 8,
                                            display: 'grid',
                                            justifyItems: 'center',
                                            alignItems: 'center',
                                            gridTemplateColumns: '1fr auto 1fr',
                                            gridTemplateRows: '1fr',
                                            gap: 6,
                                            cursor: 'pointer',
                                            backgroundColor: selectedWay === i ? '#ddd' : ''
                                        }}
                                        className="selectRoute"
                                        onClick={e => {
                                            dispatch(selectWay(i))
                                            dispatch(setCenter(way?.ways[i].points[0]))
                                        }}
                                        key={w.id}
                                    >
                                        <div>{w.name ? w.name + ' (' + w.address + ')' : w.address}</div>
                                        <RightCircleTwoTone style={{fontSize: 24}}/>
                                        <div>{way.places[i + 1].name ? way.places[i + 1].name + ' (' + way.places[i + 1].address + ')' : way.places[i + 1].address}</div>
                                    </div>
                                }

                                return null
                            })
                        }
                        {
                            !way?.places?.length &&
                            <Typography.Paragraph>На эту дату нет маршрута. Выберите другой день.</Typography.Paragraph>
                        }
                    </div>
                    <div style={{
                        position: 'absolute',
                        top: 10 + 64,
                        right: 380 + 20,
                        background: 'white',
                        zIndex: 1000,
                        borderRadius: 8,
                        border: '2px solid #33333340',
                        backgroundClip: 'padding-box',
                        padding: 8,
                        overflowY: 'auto'
                    }}>
                        <div style={{display: 'flex', alignItems: 'center', marginBottom: 10}}>
                            <img style={{marginRight: 8}} src="https://cdn-icons-png.flaticon.com/512/5604/5604658.png" alt="" width={32}/>
                            <div style={{width: 40, height: 0, border: '3px dashed red', opacity: 0.7}}></div>
                        </div>
                        <div style={{display: 'flex', alignItems: 'center'}}>
                            <img style={{marginRight: 8}} src="https://cdn-icons-png.flaticon.com/512/846/846296.png" alt="" width={32}/>
                            <div style={{width: 40, height: 0, border: '3px dashed blue', opacity: 0.7}}></div>
                        </div>
                    </div>
                    <Map
                        style={{width: '100%', height: '100%', position: 'relative'}}
                        center={center}
                        zoom={zoom < 10 ? 15 : zoom}
                        accommodation={travel?.accommodation}
                        places={way?.places}
                        routePoints={way?.ways}
                    />
                </div>
            </div>
        </div>
    )
}

export default TravelDetail