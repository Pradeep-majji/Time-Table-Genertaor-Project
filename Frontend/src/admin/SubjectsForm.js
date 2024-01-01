import React, { useState } from 'react';
import '../RegisterationForm.css';
import { useNavigate } from 'react-router-dom';
import AdminService from './AdminService.js';

const SubjectsForm = () => {
  const [formData, setFormData] = useState({
    sname: '',
    sid:'',
    stype:'',
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
   
    let result=await AdminService.addSubject(formData)
    console.log(result)
    if(result==="OK"){
    alert('subject added successfully')
    navigate('/adminaddsubject')
    }
    else{
    alert('subject already exists')
    navigate('/adminaddsubject')
    }
  };
  

  return (
    <div  className='container '>
      <h2 className='register'><center>Subjects FORM</center></h2>
      <form onSubmit={handleSubmit} className='register-form-group'>
        <div className='input'>
          <center>
          <label htmlFor="sname" className='label'> NAME:</label>
          <input className='input'
            type="text"
            id="sname"
            name="sname"
            value={formData.sname}
            onChange={handleChange}
            placeholder='Enter name of subject'
          />
          </center>
        </div>
        <div>
          <center>
          <label htmlFor="sid" className='label'>ENTER SUBJECT CODE OR ID</label>
          <input className='input'
            type="text"
            id="sid"
            name="sid"
            value={formData.sid}
            onChange={handleChange}
            placeholder='ENTER SUBJECT CODE OR ID'
          />
          </center>
        </div>
        <div>
          <center >
          <label htmlFor="stype" className='label'>subject type:</label>
          <input className='input'
            type="text"
            id="stype"
            name="stype"
            value ={formData.stype}
            onChange={handleChange}
            placeholder='Enter subject type that is lab or theory'
          />
          </center>
        </div>
        <center>
        <button type="submit" className='kondal'>ADD</button>
        </center>
      </form>
     </div>
  );
};

export default SubjectsForm;