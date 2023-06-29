import {createAsyncThunk, createSlice} from '@reduxjs/toolkit'
import axios from 'shared/api'
import {getRefreshToken} from 'shared/helpers/jwt'
import {Auth, State, UserFormSignIn, UserFormSignUp} from 'shared/entities'

export const signUp = createAsyncThunk<Auth, UserFormSignUp, { rejectValue: Error }>(
    'auth/signUp',
    async (user, {rejectWithValue}) =>
        await axios.post('/registration', user)
            .then(response => (response.data) as Auth)
            .catch(error => rejectWithValue((error.response.data) as Error))
)

export const signIn = createAsyncThunk<Auth, UserFormSignIn, { rejectValue: Error }>(
    'auth/signIn',
    async (user, {rejectWithValue}) =>
        await axios.post('/login', user)
            .then(response => (response.data) as Auth)
            .catch(error => rejectWithValue((error.response.data) as Error))
)

export const refresh = createAsyncThunk<Auth, null, { rejectValue: Error }>(
    'auth/refresh',
    async (_, {rejectWithValue}) =>
        await axios.post('/refresh', {
            refreshToken: getRefreshToken()
        })
            .then(response => (response.data) as Auth)
            .catch(error => rejectWithValue((error.response.data) as Error))
)

const initialState: State = {
    data: null,
    isLoading: false,
    error: undefined
}

const authSlice = createSlice({
    name: 'auth',
    initialState,
    reducers: {},
    extraReducers: builder => {
        builder
            .addCase(signUp.pending, (state) => {
                state.isLoading = true
            })
            .addCase(signUp.fulfilled, (state, action) => {
                state.isLoading = false
            })
            .addCase(signUp.rejected, (state) => {
                state.isLoading = false
            })
            .addCase(signIn.pending, (state) => {
                state.isLoading = true
            })
            .addCase(signIn.fulfilled, (state, action) => {
                state.isLoading = false
                state.data = action.payload
            })
            .addCase(signIn.rejected, (state) => {
                state.isLoading = false
            })
    }
})

export default authSlice.reducer