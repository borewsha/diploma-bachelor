import React, {useEffect} from 'react'
import {Card, Col, Row, Typography} from 'antd'
import {Link} from 'react-router-dom'
import {useAppDispatch, useAppSelector} from 'shared/hooks'
import {getMyTravels, getTravel} from 'slices/travelSlice'

const MyTravels = () => {
    const dispatch = useAppDispatch()

    const formatter = new Intl.DateTimeFormat('ru', {
        month: 'long',
        day: 'numeric'
    })

    useEffect(() => {
        dispatch(getMyTravels())
    }, [])

    const trips = useAppSelector(state => state.travel.myTravels)

    return (
        <div style={{padding: 16, display: 'flex', alignItems: 'center', justifyContent: 'center', overflowY: 'auto'}}>
            <div style={{maxWidth: 1400, width: '100%'}}>
                <Typography.Title>Мои путешествия</Typography.Title>
                <Row gutter={[16, 16]}>
                    {
                        !!trips && trips?.map(data =>
                            <Col
                                key={data.id}
                                xs={{span: 24}}
                                sm={{span: 12}}
                                md={{span: 8}}
                                lg={{span: 6}}
                            >
                                <Link
                                    to={`/home/travel/${data.id}`}
                                >
                                    <Card
                                        bordered
                                        hoverable
                                        cover={<img
                                            style={{objectFit: 'cover', height: 250}}
                                            src={'http://localhost:8080/api/images/' + data.cityImageId}
                                            alt={data.cityName}/>
                                    }
                                    >
                                        <Card.Meta
                                            title={data.cityName}
                                            description={`${formatter.format(Date.parse(data.dates[0]))} - ${formatter.format(Date.parse(data.dates[1]))}`}
                                        />
                                    </Card>
                                </Link>
                            </Col>
                        )
                    }
                    {
                        !trips.length && <h2>Путешествий еще нет...</h2>
                    }
                </Row>
            </div>
        </div>
    )
}

export default MyTravels