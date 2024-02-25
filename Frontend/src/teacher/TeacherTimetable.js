import {React,useState,useEffect} from 'react'
import axios from 'axios';
import TeacherNavbar from './TeacherNavbar'
import './Teacher.css'

export default function  TeacherTimeTable(){
   // const [TeacherTT, setTeacherTT] = useState([]);
    const [TTData, setTTData] = useState({
      tid: '',
      p1: '',
      p2: '',
      p3: '',
      p4: '',
      p5: '',
      p6: '',
      p7: '',
      p8: '',
      p9: '',
      p10: '',
      p11: '',
      p12: '',
      p13: '',
      p14: '',
      p15: '',
      p16: '',
      p17: '',
      p18: '',
      p19: '',
      p20: '',
      p21: '',
      p22: ''
    });

  useEffect(() => {
    const fetchData = async () => {
      try {
        const email=localStorage.getItem('email')
        const rtid= await axios.get(`http://localhost:8091/teacherid/${email}`);
        //console.log(rtid.data)
        const result = await axios.get(`http://localhost:8091/teachertt/${rtid.data.tid}`);
        //console.log(result.data)
        setTTData(result.data);
      } catch (error) {
        console.error('Error fetching user data:', error);
      }
    };

    fetchData();
  }, []);
  return (
    <div>
        <TeacherNavbar/>    
        <div className='container-2'>
      <div className='watermark'></div>
      <div className='text-content'>
      <div>
      <h1>{TTData.tid} teacher details</h1>
      <table border="1" >
          <thead>
            <tr>
              <th>Days/Periods</th>
              <th>PERIOD-1</th>
              <th>PERIOD-2</th>
              <th>Break</th>
              <th>PERIOD-3</th>
              <th>PERIOD-4</th>
            </tr>
          </thead>
          <tbody><tr>
            <th>Monday</th>
            <td>{TTData.p1}</td>
            <td>{TTData.p2}</td>
            <td>Break</td>
            <td>{TTData.p3}</td>
            <td>{TTData.p4}</td>
          </tr>
          <tr>
            <th>Tuesday</th>
            <td>{TTData.p5}</td>
            <td>{TTData.p6}</td>
            <td>Break</td>
            <td>{TTData.p7}</td>
            <td>{TTData.p8}</td>
          </tr>
          <tr>
            <th>Wednesday</th>
            <td>{TTData.p9}</td>
            <td>{TTData.p10}</td>
            <td>Break</td>
            <td>{TTData.p11}</td>
            <td>{TTData.p12}</td>
          </tr>
          <tr>
            <th>Thursday</th>
            <td>{TTData.p13}</td>
            <td>{TTData.p14}</td>
            <td>Break</td>
            <td>{TTData.p15}</td>
            <td>{TTData.p16}</td>
          </tr>
          <tr>
            <th>Friday</th>
            <td>{TTData.p17}</td>
            <td>{TTData.p18}</td>
            <td>Break</td>
            <td>{TTData.p19}</td>
            <td>{TTData.p20}</td>
          </tr>
          <tr>
            <th>Saturday</th>
            <td>{TTData.p21}</td>
            <td>{TTData.p22}</td>
            <td>Break</td>
            <td>Free</td>
            <td>Free</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    </div>
    </div>
  )
}