import React, { useState } from 'react';
import AdminNavbar from './AdminNavbar';
import AdminService from './AdminService';
import { useNavigate} from "react-router-dom";
import axios from 'axios';

const  AdminGenerateTT = () => {
    const navigate=useNavigate();
    const [classroomList,setClassroomList]=useState([])
    const [teacherAllotedList,setTeacherAllotedList]=useState([])
    //for teacherlist storing data
    const [TTTData, setTTTData] = useState({
      tid: '',
      p1: '-',p2: '-',p3: '-',p4: '-', p5: '-',p6: '-',p7: '-',p8: '-',p9: '-',p10: '-',p11: '-',p12: '-',p13: '-',
      p14: '-',p15: '-',p16: '-',p17: '-',p18: '-',p19: '-',p20: '-',p21: '-',p22: '-'
    });
    //for student list storing data
    const [CTTData, setCTTData] = useState({
      cid: '',
      p1: '-',p2: '-',p3: '-',p4: '-', p5: '-',p6: '-',p7: '-',p8: '-',p9: '-',p10: '-',p11: '-',p12: '-',p13: '-',
      p14: '-',p15: '-',p16: '-',p17: '-',p18: '-',p19: '-',p20: '-',p21: '-',p22: '-'
    });

    //time table generator  
    const handleGenerate = async () => {
        try {
           await AdminService.generatett()
          //console.log('Timetable generated successfully:', response.data);
          alert('Timetable generated successfully!');
        } catch (error) {
          //console.error('Error generating timetable:', error);
          alert('Error generating timetable. Please try again.');
        }
        navigate('/admingeneratett')
      };


    const handleReset = async  () => {
        try {
          
                //-----------------resetting--------------------
                let classroomlist = await axios.get('http://localhost:8091/classrooms');
                let teacherallotedlist=await axios.get('http://localhost:8091/teacherv');
                if(classroomlist.data==="OK" && teacherallotedlist.data==="OK"){

                    //for every classroom which is in database
                    setClassroomList(classroomList.data)
                     classroomList.forEach(async (classroom) => {
                      try{
                          setCTTData({
                          ...CTTData,
                          cid:classroom.cid,
                                });
                        await axios.post(`http://localhost:8091/addclassroomtt`,CTTData)}
                        catch(error){
                          console.log(error)
                      }
                      });

                      //for every teacher who are verified in database by the admin
                      setTeacherAllotedList(teacherAllotedList.data)
                      teacherAllotedList.forEach(async (teacher) => {
                      try{
                            setTTTData({
                                ...TTTData,
                                tid:teacher.tid,
                              });
                              await axios.post(`http://localhost:8091/addteachertt`,TTTData) 
                            }
                            catch(error){
                              console.log(error)
                            }
                      });

        }
        alert('Timetable resetted successfully!');
        } catch (error) {
          //console.error('Error generating timetable:', error);
          alert('Error resetting timetable. Please try again.');
        }
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
    <h1>Time Table Preview </h1>
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
            <td>Period-1</td>
            <td>Period-2</td>
            <td>Break</td>
            <td>Period-3</td>
            <td>Period-4</td>
          </tr>
          <tr>
            <th>Tuesday</th>
            <td>Period-1</td>
            <td>Period-2</td>
            <td>Break</td>
            <td>Period-3</td>
            <td>Period-4</td>
          </tr>
          <tr>
            <th>Wednesday</th>
            <td>Period-1</td>
            <td>Period-2</td>
            <td>Break</td>
            <td>Period-3</td>
            <td>Period-4</td>
          </tr>
          <tr>
            <th>Thursday</th>
            <td>Period-1</td>
            <td>Period-2</td>
            <td>Break</td>
            <td>Period-3</td>
            <td>Period-4</td>
          </tr>
          <tr>
            <th>Friday</th>
            <td>Period-1</td>
            <td>Period-2</td>
            <td>Break</td>
            <td>Period-3</td>
            <td>Period-4</td>
          </tr>
          <tr>
            <th>Saturday</th>
            <td>Period-1</td>
            <td>Period-2</td>
            <td>Break</td>
            <td>Break</td>
            <td>Break</td>
          </tr>
          </tbody>
        </table>
    </div>
    </div>
    </div>
  )
}


export default AdminGenerateTT;