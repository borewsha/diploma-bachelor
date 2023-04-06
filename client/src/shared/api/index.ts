import axios from 'axios'
import {API_PATH} from 'shared/helpers/envConst'
import {getAccessToken, getRefreshToken, setAccessToken, setRefreshToken} from 'shared/helpers/jwt'

const instance = axios.create({
    baseURL: API_PATH
})

instance.interceptors.request.use(config => {
    config.headers.Authorization = `Bearer ${getAccessToken()}`

    return config
})

instance.interceptors.response.use(config => config,
    async (error) => {
        const originalRequest = error.config

        if (error.response.status === 401 && error.config && !error.config._isRetry) {
            originalRequest._isRetry = true

            try {
                const response = await instance.post('/refresh', {
                    refreshToken: getRefreshToken()
                })
                setAccessToken(response.data.accessToken)
                setRefreshToken(response.data.refreshToken)

                return instance.request(originalRequest)
            } catch (e) {
                console.log('tryCatchError', e)
            }
        }

        throw error
    })

export default instance