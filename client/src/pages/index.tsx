import SignInPage from 'pages/auth/SignInPage'
import SignUpPage from 'pages/auth/SignUpPage'
import React, {FC} from 'react'
import {BrowserRouter, Route, Routes} from 'react-router-dom'
import {getAccessToken} from 'shared/helpers/jwt'
import Hello from './HelloPage'
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
            path: '/hello',
            element: <Hello/>
        }
    ]

    const authorizedNavigation = [
        {
            path: '/home',
            element: <Home/>
        }
    ]

    const isAuth = getAccessToken()

    return (
        <BrowserRouter>
            <Routes>
                {
                    isAuth
                    && authorizedNavigation.map(({path, element}) =>
                        <Route key={path} path={path} element={element}/>)
                }
                {
                    !isAuth
                    && unauthorizedNavigation.map(({path, element}) =>
                        <Route key={path} path={path} element={element}/>)
                }
                <Route path="*" element={<Hello/>}/>
            </Routes>
        </BrowserRouter>
    )
}

export default Routing