import React, { useState, useEffect } from 'react';
import axios from 'axios';
import AdminNavbar from './AdminNavbar';
import { useNavigate } from 'react-router-dom';

const AdminAddClassrooms = () => {
  const [classrooms, setClassrooms] = useState([]); // To store the classroom ID being updated
  

  const navigate = useNavigate();

  const handleRegister = () => {
    navigate('/addclassroom');
  };

  const handleUpdate = async (cid) => {
    navigate('/updateclassroom',{ state: { cid } });
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const result = await axios.get('http://localhost:8091/classrooms');
        setClassrooms(result.data);
      } catch (error) {
        console.error('Error fetching classroom data:', error);
      }
    };

    fetchData();
  }, []);

  return (
    <div>
      <AdminNavbar />
      <div className='container-3'>
      <div className='watermark-3'></div>
      <div className='text-content'>
      <div>
      <h1>Classrooms List</h1>
      <button onClick={handleRegister} style={{background:'transparent',marginBottom:'20px'}}>add classroom</button>
      <table border="1">
        <thead>
          <tr>
            <th>ID</th>
            <th>Type</th>
            <th>Batch</th>
            <th>Semester</th>
            <th>UPDATE</th>
          </tr>
        </thead>
        <tbody>
          {classrooms.map((classroom) => (
            <tr key={classroom.cid}>
              <td>{classroom.cid}</td>
              <td>{classroom.ctype}</td>
              <td>{classroom.cbatch}</td>
              <td>{classroom.csem}</td>
              <td>
                <button onClick={() => handleUpdate(classroom.cid)}>UPDATE</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
    </div>
    </div>
    </div>
  );
};

export default AdminAddClassrooms;
