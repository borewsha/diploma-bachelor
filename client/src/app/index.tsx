import 'antd/dist/reset.css'
import Routing from 'pages'
import React, {FC} from 'react'
import {Provider} from 'react-redux'
import store from './store'

const App: FC = () => {
    return (
        <Provider store={store}>
            <Routing/>
        </Provider>
    )
}

export default App
