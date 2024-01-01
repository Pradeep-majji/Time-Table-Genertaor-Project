import React, { useState } from 'react';
import '../RegisterationForm.css';
import { useNavigate} from 'react-router-dom';
import AdminService from './AdminService.js';
import axios from 'axios';


const ClassroomForm = () => {
  const [formData, setFormData] = useState({
    csem: '-',
    cid:'',
    ctype:'',
    cbatch:'-',
    alloted:'0',
    cwphw:0
  });
  
  const [TTData, setTTData] = useState({
    cid: '',
    p1: '-',
    p2: '-',
    p3: '-',
    p4: '-',
    p5: '-',
    p6: '-',
    p7: '-',
    p8: '-',
    p9: '-',
    p10: '-',
    p11: '-',
    p12: '-',
    p13: '-',
    p14: '-',
    p15: '-',
    p16: '-',
    p17: '-',
    p18: '-',
    p19: '-',
    p20: '-',
    p21: '-',
    p22: '-'
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

   // let result=await AdminService.verifyClassroom(formData.cid);
    //if(result.data!=="OK"){
    let result=await AdminService.addClassroom(formData)
    if(result.data==="OK"){
      setTTData({
        ...TTData,
        cid:formData.cid,
      });
      let result1= await axios.post(`http://localhost:8091/addclassroomtt`,TTData)
            if(result1.data==="OK")   alert('classroom added successfully')
            else alert('object creation for classroomtt failed')
    navigate('/adminaddclassroom')
    }
    else {
    alert('error in adding class')
    navigate('/adminaddclassrooms')
    }
    
   /* else{
      alert('classroom already exists')
      navigate('/adminaddclassrooms')
    }*/
  };
  

  return (
    <div  className='container '>
      <h2 className='register'><center>Classroom FORM</center></h2>
      <form onSubmit={handleSubmit} className='register-form-group'>
        <div>
          <center>
          <label htmlFor="cid" className='label'>ENTER CLASSROOM NUMBER</label>
          <input className='input'
            type="input"
            id="cid"
            name="cid"
            value={formData.cid}
            onChange={handleChange}
            placeholder='ENTER CLASSROOM NUMBER'
          />
          </center>
        </div>
        <div>
          <center >
          <label htmlFor="ctype" className='label'>classroom type:</label>
          <input className='input'
            type="text"
            id="ctype"
            name="ctype"
            value ={formData.ctype}
            onChange={handleChange}
            placeholder='Enter classroom type that is lab or theory'
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

export default ClassroomForm;