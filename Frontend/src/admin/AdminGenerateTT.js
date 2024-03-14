import React, { useState,useEffect } from 'react';
import AdminNavbar from './AdminNavbar';
import AdminService from './AdminService';
import { useNavigate} from "react-router-dom";
import axios from 'axios';
import LoginService from '../LoginService';

const  AdminGenerateTT = () => {
  
  const [TTData, setTTData] = useState([]);
  const [subjects, setSubjects] = useState([]);
  const [classrooms, setClassrooms] = useState([]); 

  useEffect(() => {
    const fetchData = async () => {
      try {
        console.log("inside in fetching data");
        const result = await LoginService.getClassroomsTT();
        setTTData(result.data);
        console.log(TTData)
        const result1 = await axios.get('http://localhost:8091/subjectlist');
        const formattedSubjects = formatSubjects(result1.data);
        setSubjects(formattedSubjects);
        const result2 = await axios.get('http://localhost:8091/classrooms');
        const formattedClassrooms = formatClassrooms(result2.data);
        setClassrooms(formattedClassrooms);
        console.log(classrooms);
      } catch (error) {
        console.error('Error fetching user data:', error);
      }
    };
    fetchData();
  } , []);

  useEffect(() => {
    console.log("TTData: dis[playing", TTData[1]);
  }, [TTData]);


  const formatSubjects = (subjectsList) => {
    const formattedDict = {};
    subjectsList.forEach((subject) => {
      formattedDict[subject.sid] = subject.sname;
    });
    return formattedDict;
  };
  const formatClassrooms = (classesList) => {
    const formattedDict = {};
    classesList.forEach((classes) => {
      formattedDict[classes.cid] = classes.cname;
    });
    return formattedDict;
  };

  const getDisplayName = (period) => {
    const id = period.split(':')[0];
    if (id.startsWith('c')) {
      // Display class name
      return classrooms[id] || '';
    } else {
      // Display subject name
      return subjects[id] || '';
    }
  };

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
          <tbody>
          <tr>
              <th>Monday</th>
              <td>{getDisplayName(weekData.p1)}</td>
              <td>{getDisplayName(weekData.p1)}</td>
              <td>{getDisplayName(weekData.p2)}</td>
              <td>{getDisplayName(weekData.p2)}</td>
              <td>Break</td>
              <td>{getDisplayName(weekData.p3)}</td>
              <td>{getDisplayName(weekData.p3)}</td>
              <td>{getDisplayName(weekData.p4)}</td>
              <td>{getDisplayName(weekData.p4)}</td>
            </tr>
            <tr>
              <th>Tuesday</th>
              <td>{getDisplayName(weekData.p5)}</td>
              <td>{getDisplayName(weekData.p5)}</td>
              <td>{getDisplayName(weekData.p6)}</td>
              <td>{getDisplayName(weekData.p6)}</td>
              <td>Break</td>
              <td>{getDisplayName(weekData.p7)}</td>
              <td>{getDisplayName(weekData.p7)}</td>
              <td>{getDisplayName(weekData.p8)}</td>
              <td>{getDisplayName(weekData.p8)}</td>
            </tr>
            <tr>
              <th>Wednesday</th>
              <td>{getDisplayName(weekData.p9)}</td>
              <td>{getDisplayName(weekData.p9)}</td>
              <td>{getDisplayName(weekData.p10)}</td>
              <td>{getDisplayName(weekData.p10)}</td>
              <td>Break</td>
              <td>{getDisplayName(weekData.p11)}</td>
              <td>{getDisplayName(weekData.p11)}</td>
              <td>{getDisplayName(weekData.p12)}</td>
              <td>{getDisplayName(weekData.p12)}</td>
            </tr>
            <tr>
              <th>Thursday</th>
              <td>{getDisplayName(weekData.p13)}</td>
              <td>{getDisplayName(weekData.p13)}</td>
              <td>{getDisplayName(weekData.p14)}</td>
              <td>{getDisplayName(weekData.p14)}</td>
              <td>Break</td>
              <td>{getDisplayName(weekData.p15)}</td>
              <td>{getDisplayName(weekData.p15)}</td>
              <td>{getDisplayName(weekData.p16)}</td>
              <td>{getDisplayName(weekData.p16)}</td>
            </tr>
            <tr>
              <th>Friday</th>
              <td>{getDisplayName(weekData.p17)}</td>
              <td>{getDisplayName(weekData.p17)}</td>
              <td>{getDisplayName(weekData.p18)}</td>
              <td>{getDisplayName(weekData.p18)}</td>
              <td>Break</td>
              <td>{getDisplayName(weekData.p19)}</td>
              <td>{getDisplayName(weekData.p19)}</td>
              <td>{getDisplayName(weekData.p20)}</td>
              <td>{getDisplayName(weekData.p20)}</td>
            </tr>
            <tr>
              <th>Saturday</th>
              <td>{getDisplayName(weekData.p21)}</td>
              <td>{getDisplayName(weekData.p21)}</td>
              <td>{getDisplayName(weekData.p22)}</td>
              <td>{getDisplayName(weekData.p22)}</td>
              <td>Break</td>
              <td>--</td>
              <td>--</td>
              <td>--</td>
              <td>--</td>
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