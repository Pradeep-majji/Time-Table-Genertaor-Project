import React, { useState } from 'react';
import { Form, Button } from 'react-bootstrap';
import {Link, useNavigate} from "react-router-dom";
import './LoginForm.css';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import LoginService from './LoginService';



const LoginForm = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [role, setRole] = useState('');
  const navigate=useNavigate();
 
  const handleSubmit = async (e) => {
    e.preventDefault()
    console.log(e)
    if(role==="teacher"){
    let result=await LoginService.verifyTeacher(email,password);
    if(result.data==="OK")
    {  localStorage.setItem('email',email)
        navigate('/teacherhome')
    }
    else
    {   toast.error('Invalid username or password', {
      position: toast.POSITION.TOP_CENTER,
      autoClose: 2000,
      hideProgressBar: true,
    });
        //alert('invalid username or password')
        navigate('/login')
    }
    }
    else{
      let result=await LoginService.verifyAdmin(email,password);
    if(result.data==="OK")
    {  localStorage.setItem('email',email)
        navigate('/adminhome')
    }
    else
    {   toast.error('Invalid username or password', {
      position: toast.POSITION.TOP_CENTER,
      autoClose: 2000,
      hideProgressBar: true,
    });
        //alert('invalid username or password')
        navigate('/login')
    }
    }

  };

  return (
    <>
    <div className='container'>
      <ToastContainer position="top-center"
        autoClose={2000}
        newestOnTop={false}
        closeOnClick
        pauseOnFocusLoss
        draggable
        pauseOnHover/>
    <h1 className='hello'><center>LOGIN FORM</center></h1>
    <Form  className='login-form'>
      <Form.Group controlId="formEmail" className='form-group'>
        <Form.Label className='form-label'>EMAIL</Form.Label>
        <Form.Control
          type="input"
          placeholder="Enter Email"
          value={email}
          name="email"
          onChange={(e) => setEmail(e.target.value)}
          className='form-control'
        />
      </Form.Group>

      <Form.Group controlId="formPassword" className='form-group'>
        <Form.Label className='form-label'>Password</Form.Label>
        <Form.Control
          type="password"
          placeholder="Enter password"
          value={password}
          name="password"
          onChange={(e) => setPassword(e.target.value)}
          className='form-control'
        />
      </Form.Group>
      
      <Form.Group controlId="formRole" className='form-group'>
      <Form.Label className='form-label'>Role</Form.Label>
      <Form.Control
        as="select"
        value={role}
        name="role"
        onChange={(e) => setRole(e.target.value)}
        className='form-control'
       >
        <option value="admin">ADMIN</option>
        <option value="teacher">TEACHER</option>
      </Form.Control>
      </Form.Group>

      <Button variant="primary" type="button" className="login-button" onClick={handleSubmit}>
        LOGIN
      </Button>
    </Form>
    <h6>if not user <Link to="/register">Click Me to Register If You are Teacher</Link></h6>
    </div>
    </>
  )
}
export default LoginForm;
