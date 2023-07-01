import React from 'react'
import {Layout, Menu, theme} from 'antd'
import {CompassOutlined, GlobalOutlined, PlusOutlined} from '@ant-design/icons'
import {Route, Routes, useNavigate} from 'react-router-dom'
import MyTravels from './MyTravels'
import CreateTravelPage from './CreateTravelPage'
import TravelDetail from './TravelDetail'
import MapPage from './MapPage'

const Home = () => {
    const navigate = useNavigate()

    const {
        token: {colorBgContainer}
    } = theme.useToken()

    const authorizedNavigation = [
        {
            path: '/',
            element: <MyTravels/>
        },
        {
            path: '/map',
            element: <MapPage/>
        },
        {
            path: '/create',
            element: <CreateTravelPage/>
        },
        {
            path: '/travel/:id',
            element: <TravelDetail/>
        }
    ]

    return (
        <Layout style={{height: '100vh'}}>
            <Layout.Header
                style={{
                    display: 'flex',
                    alignItems: 'center',
                    backgroundColor: colorBgContainer,
                    position: 'sticky',
                    top: 0,
                    zIndex: 1
                }}>
                <img height={30} src={require('shared/logo.png')} alt="Traveler"/>
                <Menu
                    theme="light"
                    style={{width: '100%', marginLeft: 20}}
                    mode="horizontal"
                    selectedKeys={[window.location.pathname.slice(5, window.location.pathname.length) || '/']}
                    onClick={e => navigate(`/home${e.key}`)}
                    items={[
                        {
                            key: '/',
                            icon: <CompassOutlined/>,
                            label: 'Мои путешествия'
                        },
                        {
                            key: '/map',
                            icon: <GlobalOutlined/>,
                            label: 'Карта'
                        },
                        {
                            key: '/create',
                            icon: <PlusOutlined/>,
                            label: 'Создать поездку'
                        }
                    ]}
                />
            </Layout.Header>
            <Layout.Content
                style={{background: colorBgContainer, overflow: 'hidden'}}
            >
                <Routes>
                    {
                        authorizedNavigation.map(({path, element}) =>
                            <Route key={path} path={path} element={element}/>)
                    }
                </Routes>
            </Layout.Content>
        </Layout>
    )
}

export default Home