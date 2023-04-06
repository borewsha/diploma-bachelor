export type Auth = {
    accessToken: string | null
    refreshToken: string | null
}

export type UserFormSignUp = {
    email: string,
    password: string,
    fullName: string
}

export type UserFormSignIn = {
    email: string,
    password: string
}

export type State = {
    data: Auth | null
    isLoading: boolean
    error: string | undefined
}

export type Error = {
    message: string
    errors?: Array<{field: string, message: string}>
}