import {React,useState,useEffect} from 'react'
import IndexNavbar from './IndexNavbar';
import LoginService from './LoginService';
import { useNavigate } from 'react-router-dom';

const HomePage = () => {

  const [userData, setUserData] = useState([]);
  const navigate=useNavigate();
  const handleGenerateTT=(cid)=>{
    navigate('/classtimetable',{ state: { cid } })
  }
  useEffect(() => {
    const fetchData = async () => {
      try {
        const result = await LoginService.getallotedclasses();
        setUserData(result.data);
      } catch (error) {
        console.error('Error fetching user data:', error);
      }
    };

    fetchData();
  }, []);

  return <div>
        <IndexNavbar/>
        <div>
      <h1>CLASSES List</h1>
      <table border="1" width="100%">
        <thead>
          <tr>
            <th>CID</th>
            <th>TType</th>
            <th>TBatch</th>
            <th>TSemister</th>
            <th>Get Time Table</th>
          </tr>
        </thead>
        <tbody>
          {userData.map((teacher) => (
            <tr key={teacher.cid}>
              <td>{teacher.cid}</td>
              <td>{teacher.ctype}</td>
              <td>{teacher.cbatch}</td>
              <td>{teacher.csem}</td>
              <td>
                <button onClick={() => handleGenerateTT(teacher.cid)}>TIME TABLE</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>   
    </div>            
      
};

export default HomePage;


