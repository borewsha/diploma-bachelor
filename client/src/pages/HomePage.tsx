import {Button, Typography} from 'antd'
import React, {useState} from 'react'
import instance from 'shared/api'

type City = {
    id: number
    name: string
}

const Home = () => {
    const [cities, setCities] = useState<City[]>([])

    return (
        <div>
            <Typography.Title>Home Page!</Typography.Title>
            <Button onClick={async () => {
                const res = await instance.get('/cities')
                setCities(res.data.data)
            }}></Button>
            {
                cities.map(c => <div>{c.name}</div>)
            }
        </div>
    )
}

export default Home