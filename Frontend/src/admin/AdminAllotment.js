import {React,useState,useEffect} from 'react'
import axios from 'axios';
import AdminNavbar from './AdminNavbar';
import { useNavigate } from 'react-router-dom';

const AdminAllotment = () => {
  const [allotments, setAllotments] = useState([]);
  const navigate=useNavigate()
  const handleRegister = async () => {
    navigate('/allotment');
  };
  useEffect(() => {
    const fetchData = async () => {
      try {
        const result = await axios.get('http://localhost:8091/allotmentuv');
        setAllotments(result.data);
        console.log(result.data)
        console.log(allotments);
      } catch (error) {
        console.error('Error fetching user data:', error);
      }
    };
    fetchData();
  },[]);
  
  const handleAccept = async (sid,cid,sem,batch) => {
    const accept = await axios.put(`http://localhost:8091/allotmentaccept/${sid}/${cid}/${sem}/${batch}`);
    if(accept.data==="OK")
    {const result = await axios.get('http://localhost:8091/allotmentuv');
    setAllotments(result.data);}
  };

  const handleDelete = async (sid,cid,sem,batch) => {
     const result= await axios.delete(`http://localhost:8091/allotmentdelete/${sid}/${cid}/${sem}/${batch}`);
     if(result.data==="OK") 
     {  alert("deleted successfully");
      navigate('/adminallotment')
    /*const result1 = await axios.get('http://localhost:8091 /allotmentuv');
  setAllotments(result1.data);*/}
  };
  return (
    <div>
        <AdminNavbar/>
      <div className='container-3'>
      <div className='watermark-3'></div>
      <div className='text-content'>
      <div>
        <h1>Allotments List</h1>
        <button onClick={handleRegister} style={{background:'transparent',marginBottom:'20px'}}>add allotment</button>
        <div>
        <table border="1" width="100%">
        <thead>
          <tr>
            <th>SID</th>
            <th>TID</th>
            <th>CID</th>
            <th>SEM</th>
            <th>Bacth</th>
            <th>type</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {allotments.map((allotment) => (
            <tr key={allotment.id.sid}>
              <td>{allotment.id.sid}</td>
              <td>{allotment.tid}</td>
              <td>{allotment.id.cid}</td>
              <td>{allotment.id.sem}</td>
              <td>{allotment.id.batch}</td>
              <td>{allotment.type}</td>
              <td>
                <button onClick={() => handleAccept(allotment.id.sid,allotment.id.cid,allotment.id.sem,allotment.id.batch)}>Accept</button>
                <button onClick={() => handleDelete(allotment.id.sid,allotment.id.cid,allotment.id.sem,allotment.id.batch)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
    </div>
    </div>
    </div>
    </div>

  )
}

export default AdminAllotment;