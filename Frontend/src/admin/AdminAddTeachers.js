import {React,useState,useEffect} from 'react'
import AdminNavbar from './AdminNavbar';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const AdminAddTeachers = () => {
  const [teachers, setTeachers] = useState([]);
  const [TTData, setTTData] = useState({
    tid: '',
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

  const navigate=useNavigate()
  const handleRegister = async () => {
    navigate('/register');
  };
  useEffect(() => {
    const fetchData = async () => {
      try {
        const result = await axios.get('http://localhost:8091/teacheruv');
        setTeachers(result.data);
      } catch (error) {
        console.error('Error fetching user data:', error);
      }
    };

    fetchData();
  }, []);

  const handleAccept = async (tid) => {
    let accept= await axios.put(`http://localhost:8091/teacheraccept/${tid}`); 
    if(accept.data==="OK"){
      setTTData({
        ...TTData,
        tid:tid,
      });
      let result1= await axios.post(`http://localhost:8091/addteachertt`,TTData)
            if(result1.data==="OK")   alert('teacher added successfully')
            else alert('object creation for teachertt failed')
           const result = await axios.get('http://localhost:8091/teacheruv');
          setTeachers(result.data);
     }
   else{
    alert('error in accepting the teacher data  , already accepted teacher with same id')
    navigate('/adminaddteacher')
   }
  };
  
  const handleDelete = async (tid) => {
    await axios.delete(`http://localhost:8091/teacherdelete/${tid}`);
    const result = await axios.get('http://localhost:8091/teacheruv');
    setTeachers(result.data);
  };
  return (
    <div>
        <AdminNavbar/>
        <div>
      <h1>Teachers List</h1>
      <table border="1" width="100%">
        <thead>
          <tr>
            <th>TID</th>
            <th>TName</th>
            <th>TDesignation</th>
            <th>TSpecialisation</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {teachers.map((teacher) => (
            <tr key={teacher.tid}>
              <td>{teacher.tid}</td>
              <td>{teacher.tname}</td>
              <td>{teacher.tdesignation}</td>
              <td>{teacher.tspecialisation}</td>
              <td>
                <button onClick={() => handleAccept(teacher.tid)}>Accept</button>
                <button onClick={() => handleDelete(teacher.tid)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
    <button onClick={handleRegister}>click to add teachers</button>
    </div>
  )
}

export default AdminAddTeachers;
