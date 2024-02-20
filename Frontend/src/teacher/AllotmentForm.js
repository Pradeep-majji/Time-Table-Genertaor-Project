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
      <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', width: '300px' }}>
      <label style={{ marginBottom: '10px' }}>
        SID:
        <input
          type="text"
          name="sid"
          value={formData.sid}
          onChange={handleChange}
          style={{ padding: '8px', border: '1px solid #ccc', borderRadius: '5px', marginLeft: '10px', width: 'calc(100% - 20px)' }}
        />
      </label>
      <label style={{ marginBottom: '10px' }}>
        CID:
        <input
          type="text"
          name="cid"
          value={formData.cid}
          onChange={handleChange}
          style={{ padding: '8px', border: '1px solid #ccc', borderRadius: '5px', marginLeft: '10px', width: 'calc(100% - 20px)' }}
        />
      </label>
      <label style={{ marginBottom: '10px' }}>
        TID:
        <input
          type="text"
          name="tid"
          value={formData.tid}
          onChange={handleChange}
          style={{ padding: '8px', border: '1px solid #ccc', borderRadius: '5px', marginLeft: '10px', width: 'calc(100% - 20px)' }}
        />
      </label>
      <label style={{ marginBottom: '10px' }}>
        Sem:
        <input
          type="text"
          name="sem"
          value={formData.sem}
          onChange={handleChange}
          style={{ padding: '8px', border: '1px solid #ccc', borderRadius: '5px', marginLeft: '10px', width: 'calc(100% - 20px)' }}
        />
      </label>
      <label style={{ marginBottom: '10px' }}>
        Batch:
        <input
          type="text"
          name="batch"
          value={formData.batch}
          onChange={handleChange}
          style={{ padding: '8px', border: '1px solid #ccc', borderRadius: '5px', marginLeft: '10px', width: 'calc(100% - 20px)' }}
        />
      </label>
      <label style={{ marginBottom: '10px' }}>
        Type:
        <input
          type="text"
          name="type"
          value={formData.type}
          onChange={handleChange}
          style={{ padding: '8px', border: '1px solid #ccc', borderRadius: '5px', marginLeft: '10px', width: 'calc(100% - 20px)' }}
        />
      </label>
      <button
        type="submit"
        style={{ padding: '10px 20px', backgroundColor: '#2577cd', color: '#fff', border: 'none', borderRadius: '5px', cursor: 'pointer', marginTop: '20px' }}
      >
        Submit
      </button>
    </form>
    </div>
  );
};

export default AllotmentForm;
