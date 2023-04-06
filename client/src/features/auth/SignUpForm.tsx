import {LockOutlined} from '@ant-design/icons'
import {Button, Form, Input, message, Typography} from 'antd'
import {signUp} from './authSlice'
import {UserFormSignUp} from './types'
import React, {FC} from 'react'
import {useNavigate} from 'react-router'
import {Link} from 'react-router-dom'
import {useAppDispatch, useAppSelector} from 'shared/hooks'

const SignUpForm: FC = () => {
    const dispatch = useAppDispatch()
    const navigate = useNavigate()

    const isLoading = useAppSelector(state => state.auth.isLoading)

    const onFinish = async (user: UserFormSignUp) => {
        dispatch(signUp(user))
            .unwrap()
            .then(() => {
                message.success('Вы успешно зарегистрировались!')
                navigate('/sign-in')
            })
            .catch(error => {
                message.error(error.message + '!')
            })
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
            <Typography.Title level={2}>Регистрация</Typography.Title>
            <Form
                style={{width: 300, marginTop: 10}}
                onFinish={onFinish}
                onFinishFailed={onFinishFailed}
            >
                <Form.Item
                    name="fullName"
                    rules={[
                        {
                            required: true,
                            whitespace: true,
                            message: 'Поле должно быть заполнено!'
                        }
                    ]}
                >
                    <Input placeholder="Имя"/>
                </Form.Item>
                <Form.Item
                    name="email"
                    rules={[
                        {
                            required: true,
                            whitespace: true,
                            message: 'Поле должно быть заполнено!'
                        },
                        {
                            type: 'email',
                            message: 'Почта введена неверно!'
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
                            message: 'Поле должно быть заполнено!'
                        },
                        {
                            min: 8,
                            message: 'Минимальная длина пароля 8 символов!'
                        },
                        {
                            max: 30,
                            message: 'Максимальная длина пароля 30 символов!'
                        }
                    ]}
                >
                    <Input.Password placeholder="Пароль"/>
                </Form.Item>
                <Form.Item>
                    <Button
                        type="primary"
                        style={{width: '100%', marginBottom: 10}}
                        htmlType="submit"
                        loading={isLoading}
                    >Зарегистрироваться</Button>
                    <Link to="/sign-in">Уже есть аккаунт? Войти!</Link>
                </Form.Item>
            </Form>
        </div>
    )
}

export default SignUpForm