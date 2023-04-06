const ACCESS_TOKEN = 'accessToken'
const REFRESH_TOKEN = 'refreshToken'

export const setAccessToken = (accessToken: string) => {
  window.localStorage.setItem(ACCESS_TOKEN, accessToken)
}

export const getAccessToken = () => window.localStorage.getItem(ACCESS_TOKEN)

export const removeAccessToken = () => {
    window.localStorage.removeItem(ACCESS_TOKEN)
}

export const setRefreshToken = (refreshToken: string) => {
    window.localStorage.setItem(REFRESH_TOKEN, refreshToken)
}

export const getRefreshToken = () => window.localStorage.getItem(REFRESH_TOKEN)

export const removeRefreshToken = () => {
    window.localStorage.removeItem(REFRESH_TOKEN)
}