import React, { useState,useEffect } from 'react';
import AdminNavbar from './AdminNavbar';
import AdminService from './AdminService';
import { useNavigate} from "react-router-dom";
import axios from 'axios';
import LoginService from '../LoginService';

const  AdminGenerateTT = () => {
  
  const [TTData, setTTData] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        console.log("inside in fetching data");
        const result = await LoginService.getClassroomsTT();
        setTTData(result.data);
        console.log(TTData)
        //console.log(result.data)
      } catch (error) {
        console.error('Error fetching user data:', error);
      }
    };
    fetchData();
  } , []);

  useEffect(() => {
    console.log("TTData: dis[playing", TTData[1]);
  }, [TTData]);


    const navigate=useNavigate();

    //time table generator  
    const handleGenerate = async () => {
      const res= await axios.get('http://localhost:8091/admin')
                console.log(res.data)
                if(res.data!=="OK")
                { 
                    try {
                        await AdminService.generatett()
                        //console.log('Timetable generated successfully:', response.data);
                        await axios.put('http://localhost:8091/admingenerate');
                        alert('Timetable generated successfully!');
                      } catch (error) {
                        //console.error('Error generating timetable:', error);
                        alert('Error generating timetable. Please try again.');
                      }
                      navigate('/admingeneratett')
                }
                else{
                  alert('time table already generated')
                }
            };


    const handleReset = async  () => {
        try {
                const res= await axios.get('http://localhost:8091/admin')
                console.log(res.data)
                if(res.data==="OK")
                {
                  await AdminService.resettt();
                  await axios.put('http://localhost:8091/adminreset');
                  alert('Timetable resetted successfully!');

                  }
                  else{
                    alert('already resetted')
                  }
             }
     catch (error) {
        alert('Error resetting timetable. Please try again.'); }
        navigate('/admingeneratett')
    };
  return (
    <div>
        <AdminNavbar/>
      <div className='container-3'>
      <div className='watermark-3'></div>
      <div className='text-content'>
      <div>
        <button className="btn btn-primary" onClick={handleGenerate} style={{marginTop:'20px',marginRight:'30px'}}>
        Generate
      </button>
      <button className="btn btn-danger" onClick={handleReset} style={{marginTop:'20px',marginRight:'30px'}}>
        Reset
      </button>
    </div>
    <br/>
    {TTData.map((weekData, index) => (
          <div key={index}>
            
            <h2>{weekData.cid}</h2>
            <table border='1'>
          <thead>
            <tr>
              <th>Days/Periods</th>
              <th>PERIOD-1</th>
              <th>PERIOD-2</th>
              <th>PERIOD-3</th>
              <th>PERIOD-4</th>
              <th>Break</th>
              <th>PERIOD-5</th>
              <th>PERIOD-6</th>
              <th>PERIOD-7</th>
              <th>PERIOD-8</th>
            </tr>
          </thead>
          <tbody><tr>
            <th>Monday</th>
            <td>{weekData.p1}</td>
            <td>{weekData.p1}</td>
            <td>{weekData.p2}</td>
            <td>{weekData.p2}</td>
            <td>Break</td>
            <td>{weekData.p3}</td>
            <td>{weekData.p3}</td>
            <td>{weekData.p4}</td>
            <td>{weekData.p4}</td>
          </tr>
          <tr>
            <th>Tuesday</th>
            <td>{weekData.p5}</td>
            <td>{weekData.p5}</td>
            <td>{weekData.p6}</td>
            <td>{weekData.p6}</td>
            <td>Break</td>
            <td>{weekData.p7}</td>
            <td>{weekData.p7}</td>
            <td>{weekData.p8}</td>
            <td>{weekData.p8}</td>
          </tr>
          <tr>
            <th>Wednesday</th>
            <td>{weekData.p9}</td>
            <td>{weekData.p9}</td>
            <td>{weekData.p10}</td>
            <td>{weekData.p10}</td>
            <td>Break</td>
            <td>{weekData.p11}</td>
            <td>{weekData.p11}</td>
            <td>{weekData.p12}</td>
            <td>{weekData.p12}</td>
          </tr>
          <tr>
            <th>Thursday</th>
            <td>{weekData.p13}</td>
            <td>{weekData.p13}</td>
            <td>{weekData.p14}</td>
            <td>{weekData.p14}</td>
            <td>Break</td>
            <td>{weekData.p15}</td>
            <td>{weekData.p15}</td>
            <td>{weekData.p16}</td>
            <td>{weekData.p16}</td>
          </tr>
          <tr>
            <th>Friday</th>
            <td>{weekData.p17}</td>
            <td>{weekData.p17}</td>
            <td>{weekData.p18}</td>
            <td>{weekData.p18}</td>
            <td>Break</td>
            <td>{weekData.p19}</td>
            <td>{weekData.p19}</td>
            <td>{weekData.p20}</td>
            <td>{weekData.p20}</td>
          </tr>
          <tr>
            <th>Saturday</th>
            <td>{weekData.p21}</td>
            <td>{weekData.p21}</td>
            <td>{weekData.p22}</td>
            <td>{weekData.p22}</td>
            <td>Break</td>
            <td>Free</td>
            <td>Free</td>
            <td>Free</td>
            <td>Free</td>
          </tr>
          </tbody>
        </table>
        <br/>
          </div>
        ))}</div>
    </div>
    </div>
  )
}


export default AdminGenerateTT;