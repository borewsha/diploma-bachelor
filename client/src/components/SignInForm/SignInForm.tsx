import {LockOutlined} from '@ant-design/icons'
import {Button, Form, Input, message, Typography} from 'antd'
import React, {FC} from 'react'
import {useNavigate} from 'react-router'
import {Link} from 'react-router-dom'
import {setAccessToken, setRefreshToken} from 'shared/helpers/jwt'
import {useAppDispatch, useAppSelector} from 'shared/hooks'
import {signIn} from 'slices/authSlice'
import {UserFormSignIn} from 'shared/entities'

const SignInForm: FC = () => {
    const dispatch = useAppDispatch()
    const navigate = useNavigate()

    const isLoading = useAppSelector(state => state.auth.isLoading)

    const onFinish = async (user: UserFormSignIn) => {
        await dispatch(signIn(user))
            .unwrap()
            .then(response => {
                if (response.accessToken && response.refreshToken) {
                    setAccessToken(response.accessToken)
                    setRefreshToken(response.refreshToken)
                }
                message.success('Вы успешно вошли в аккаунт!')
                navigate('/home')
                window.location.reload()
            })
            .catch(error => message.error(error.message + '!'))
    }

    const onFinishFailed = async () => {
        await message.error('Ошибка при заполнении формы!')
    }

    return (
        <div style={{
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            height: '100vh',
            flexDirection: 'column'
        }}>
            <LockOutlined style={{fontSize: 30}}/>
            <Typography.Title level={2}>Вход</Typography.Title>
            <Form
                style={{width: 300, marginTop: 10}}
                onFinish={onFinish}
                onFinishFailed={onFinishFailed}
            >
                <Form.Item
                    name="email"
                    rules={[
                        {
                            required: true,
                            whitespace: true,
                            message: 'Заполните поле'
                        }
                    ]}
                >
                    <Input placeholder="Почта"/>
                </Form.Item>
                <Form.Item
                    name="password"
                    rules={[
                        {
                            required: true,
                            whitespace: true,
                            message: 'Заполните поле'
                        }
                    ]}
                >
                    <Input.Password placeholder="Пароль"/>
                </Form.Item>
                <Form.Item>
                    <Button
                        type="primary"
                        style={{width: '100%', margin: '10px 0'}}
                        htmlType="submit"
                        loading={isLoading}
                    >Войти</Button>
                    <Link to="/sign-up">Еще нет аккаунта? Зарегистрироваться!</Link>
                </Form.Item>
            </Form>
        </div>
    )
}

export default SignInForm