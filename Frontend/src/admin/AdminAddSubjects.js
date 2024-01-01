import {React,useState,useEffect} from 'react'
import axios from 'axios';
import AdminNavbar from './AdminNavbar';
import { useNavigate } from 'react-router-dom';

const AdminAddSubjects = () => {
  const [teachers, setTeachers] = useState([]);
  const navigate=useNavigate()
  const handleRegister = async () => {
    navigate('/addsubject');
  };
  useEffect(() => {
    const fetchData = async () => {
      try {
        const result = await axios.get('http://localhost:8091/subjectlist');
        //console.log(result.data)
        setTeachers(result.data);
      } catch (error) {
        console.error('Error fetching user data:', error);
      }
    };

    fetchData();
  }, []);
  return (
    <div>
        <AdminNavbar/>
        <h1>Subjects List</h1>
        <table border="1" width="100%">
        <thead>
          <tr>
            <th>SID</th>
            <th>SName</th>
            <th>SType</th>
          </tr>
        </thead>
        <tbody>
          {teachers.map((teacher) => (
            <tr key={teacher.sid}>
              
              <td>{teacher.sid}</td>
              <td>{teacher.sname}</td>
              <td>{teacher.stype}</td>
            </tr>
          ))}
        </tbody>
      </table>
      <button onClick={handleRegister}>click to go to add subjects</button>
    </div>
  )
}

export default AdminAddSubjects;