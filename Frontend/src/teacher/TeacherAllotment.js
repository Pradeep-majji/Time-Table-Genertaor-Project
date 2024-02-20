import TeacherNavbar from './TeacherNavbar';
import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './Teacher.css'

const TeacherAllotment = () => {
  const [formData, setFormData] = useState({
    sid: '',
    cid: '',
    tid: '',
    sem: '',
    batch: '',
    type: '',
    placed:0,
    verified:0

  });
  const navigate=useNavigate();
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const allotmentId= {
      sid: formData.sid,
      cid: formData.cid,
      sem: formData.sem,
      batch: formData.batch,
    }
    const allotmentData = {
      id : {
        sid: formData.sid,
        cid: formData.cid,
        sem: formData.sem,
        batch: formData.batch,
      },
      type: formData.type,
      tid: formData.tid,
      placed: formData.placed,
      verified: formData.verified,
    };


    const isAllotted = await  axios.post(`http://localhost:8091/verifyallotment`,allotmentId);
    
    if (isAllotted.data==="OK") {
      alert('Allotment already exists for this student and course combination.');
      navigate('/teacherallotment');
    }
    try {
      console.log(formData)
      let result = await axios.post('http://localhost:8091/addallotment',allotmentData);
      //console.log('Allotment submitted successfully:', response.data);
      if(result.data==="OK"){
      alert('allotment is under review of admin...')
      navigate('/teacherallotment');
      }
      else{
      alert('allotment not submitted')
      navigate('/teacherallotment');
      }
    } catch (error) {
      console.error('Error submitting allotment:', error);
    }
  };
  return (

    <div>
        <TeacherNavbar/>    
        <div className='container-2'>
      <div className='watermark'></div>
      <div className='text-content'>
      <h1>Allotment Form</h1>
          <form onSubmit={handleSubmit} className='form-container'>
            <label>
              <span>SID:</span>
              <input
                type="text"
                name="sid"
                value={formData.sid}
                onChange={handleChange}
              />
            </label>
            <label>
              <span>CID:</span>
              <input
                type="text"
                name="cid"
                value={formData.cid}
                onChange={handleChange}
              />
            </label>
            <label>
              <span>TID:</span>
              <input
                type="text"
                name="tid"
                value={formData.tid}
                onChange={handleChange}
              />
            </label>
            <label>
              <span>Sem:</span>
              <input
                type="text"
                name="sem"
                value={formData.sem}
                onChange={handleChange}
              />
            </label>
            <label>
              <span>Batch:</span>
              <input
                type="text"
                name="batch"
                value={formData.batch}
                onChange={handleChange}
              />
            </label>
            <label>
              <span>Type:</span>
              <input
                type="text"
                name="type"
                value={formData.type}
                onChange={handleChange}
              />
            </label>
            <button type="submit">Submit</button>
          </form>
      </div>
      </div>
      </div>
    
    
  )
}

export default TeacherAllotment;