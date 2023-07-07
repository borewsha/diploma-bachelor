import SignInPage from 'pages/SignInPage'
import SignUpPage from 'pages/SignUpPage'
import React, {FC} from 'react'
import {BrowserRouter, Navigate, Route, Routes} from 'react-router-dom'
import {getAccessToken} from 'shared/helpers/jwt'
import Home from './HomePage'

const Routing: FC = () => {
    const unauthorizedNavigation = [
        {
            path: '/sign-up',
            element: <SignUpPage/>
        },
        {
            path: '/sign-in',
            element: <SignInPage/>
        },
        {
            path: '*',
            element: <SignInPage/>
        }
    ]

    const authorizedNavigation = [
        {
            path: '/home/*',
            element: <Home/>
        },
        {
            path: '*',
            element: <Navigate to="/home"/>
        }
    ]

    const isAuth = getAccessToken()

    return (
        <BrowserRouter>
            <Routes>
                {
                    !!isAuth
                    && authorizedNavigation.map(({path, element}) =>
                        <Route key={path} path={path} element={element}/>)
                }
                {
                    !isAuth
                    && unauthorizedNavigation.map(({path, element}) =>
                        <Route key={path} path={path} element={element}/>)
                }
            </Routes>
        </BrowserRouter>
    )
}

export default Routing