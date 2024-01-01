import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const AllotmentForm = () => {
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
      <h1>Allotment Form</h1>
      <form onSubmit={handleSubmit}>
        <label>
          SID:
          <input type="text" name="sid"  value={formData.sid} onChange={handleChange} />
        </label>
        <br />

        <label>
          CID:
          <input type="text" name="cid" value={formData.cid} onChange={handleChange} />
        </label>
        <br />

        <label>
          TID:
          <input type="text" name="tid" value={formData.tid} onChange={handleChange} />
        </label>
        <br />

        <label>
          Sem:
          <input type="text" name="sem" value={formData.sem} onChange={handleChange} />
        </label>
        <br />

        <label>
          Batch:
          <input type="text" name="batch" value={formData.batch} onChange={handleChange} />
        </label>
        <br />

        <label>
          Type:
          <input type="text" name="type" value={formData.type} onChange={handleChange} />
        </label>
        <br />

        <button type="submit">Submit</button>
      </form>
    </div>
  );
};

export default AllotmentForm;
