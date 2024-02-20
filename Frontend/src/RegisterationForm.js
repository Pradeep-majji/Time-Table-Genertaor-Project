import React, { useState } from 'react';
import './RegisterationForm.css';
import { useNavigate,Link } from 'react-router-dom';
import LoginService from './LoginService.js';


const RegistrationForm = () => {
  const [formData, setFormData] = useState({
    tname: '',
    temail: '',
    ttpassword: '',
    tid:'',
    tdesignation:'',
    tspecialisation:'',
    twpwh:0,
    verified:0
  });
  
  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };
  let navigate=useNavigate()
  const handleSubmit = async  (e) => {
    e.preventDefault();
    let result=await LoginService.addTeacher(formData)
    console.log(result)
    if(result.data==="OK"){
        alert('we sent request to admin for your conformation')
        if(localStorage.getItem('email'))  navigate('/adminaddteacher');
        else navigate('/');
      }
      else{
        alert('unable to register')
        if(localStorage.getItem('email'))  navigate('/adminaddteacher');
        else navigate('/');
      }   
  };
  

  return (
    <div  className='container '>
    <h2 className='register'><center>REGISTERATION FORM</center></h2>
      <form onSubmit={handleSubmit} className='register-form-group'>
        <div className='input'>
          <center>
          <label htmlFor="tname" className='label'> NAME:</label>
          <input className='input'
            type="text"
            id="tname"
            name="tname"
            value={formData.tname}
            onChange={handleChange}
            placeholder='Enter name'
          />
          </center>
        </div>
        <div>
          <center>
          <label htmlFor="temail" className='label'>Email:</label>
          <input className='input'
            type="email"
            id="temail"
            name="temail"
            value={formData.temail}
            onChange={handleChange}
            placeholder='Enter email'
          />
          </center>
        </div>
        <div>
          <center >
          <label htmlFor="tpassword" className='label'>Password:</label>
          <input className='input'
            type="password"
            id="tpassword"
            name="tpassword"
            value ={formData.tpassword}
            onChange={handleChange}
            placeholder='Enter password'
          />
          </center>
        </div>
        <div>
          <center >
          <label htmlFor="tid" className='label'>Teacher id:</label>
          <input className='input'
            type="text"
            id="tid"
            name="tid"
            value ={formData.tid}
            onChange={handleChange}
            placeholder='Enter your Teacher id'
          />
          </center>
        </div>
        <div>
          <center >
          <label htmlFor="tDesignation" className='label'>Designation</label>
          <input className='input'
            type="text"
            id="tDesignation"
            name="tDesignation"
            value ={formData.tDesignation}
            onChange={handleChange}
            placeholder='Enter your Designation'
          />
          </center>
        </div>
        <div>
          <center >
          <label htmlFor="tspecialisation" className='label'>Specialisation</label>
          <input className='input'
            type="input"
            id="tspecialisation"
            name="tspecialisation"
            value ={formData.tspecialisation}
            onChange={handleChange}
            placeholder='Enter your Specialisation'
          />
          </center>
        </div>
        <center>
        <button type="submit" className='kondal'>Register</button>
        </center>
      </form>
      <h6>if already registered <Link to="/login">Click Me to login</Link></h6>
    </div>
  );
};

export default RegistrationForm;