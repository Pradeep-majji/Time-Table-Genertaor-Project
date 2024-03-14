import {React,useState,useEffect} from 'react'
import axios from 'axios';
import TeacherNavbar from './TeacherNavbar'
import './Teacher.css'

export default function  TeacherTimeTable(){
   // const [TeacherTT, setTeacherTT] = useState([]);
  const [classrooms, setClassrooms] = useState([]); 
  const [classroomDict, setClassroomDict] = useState({});
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
        const result1 = await axios.get('http://localhost:8091/classrooms');
        setClassrooms(result1.data);
        const formattedClassroomDict = formatClassroomData(result1.data);
        setClassroomDict(formattedClassroomDict);
      } catch (error) {
        console.error('Error fetching user data:', error);
      }
    };

    fetchData();
  }, []);
  const formatClassroomData = (data) => {
    const formattedDict = {};
    data.forEach((classroom) => {
      formattedDict[classroom.cid] = { csem: classroom.csem, cname: classroom.cname };
    });
    return formattedDict;
  };

  return (
    <div>
        <TeacherNavbar/>    
        <div className='container-2'>
      <div className='watermark'></div>
      <div className='text-content'>
      <div>
      <h1>{TTData.tid} teacher details</h1>
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
              <td>{classroomDict[TTData.p1] && classroomDict[TTData.p1].csem} - {classroomDict[TTData.p1] && classroomDict[TTData.p1].cname}-{TTData.p1}</td>
              <td>{classroomDict[TTData.p1] && classroomDict[TTData.p1].csem} - {classroomDict[TTData.p1] && classroomDict[TTData.p1].cname}-{TTData.p1}</td>
              <td>{classroomDict[TTData.p2] && classroomDict[TTData.p2].csem} - {classroomDict[TTData.p2] && classroomDict[TTData.p2].cname}-{TTData.p2}</td>
              <td>{classroomDict[TTData.p2] && classroomDict[TTData.p2].csem} - {classroomDict[TTData.p2] && classroomDict[TTData.p2].cname}-{TTData.p2}</td>
              <td>Break</td>
              <td>{classroomDict[TTData.p3] && classroomDict[TTData.p3].csem} - {classroomDict[TTData.p3] && classroomDict[TTData.p3].cname}-{TTData.p3}</td>
              <td>{classroomDict[TTData.p3] && classroomDict[TTData.p3].csem} - {classroomDict[TTData.p3] && classroomDict[TTData.p3].cname}-{TTData.p3}</td>
              <td>{classroomDict[TTData.p4] && classroomDict[TTData.p4].csem} - {classroomDict[TTData.p4] && classroomDict[TTData.p4].cname}-{TTData.p4}</td>
              <td>{classroomDict[TTData.p4] && classroomDict[TTData.p4].csem} - {classroomDict[TTData.p4] && classroomDict[TTData.p4].cname}-{TTData.p4}</td>
            </tr>
            <tr>
            <th>Tuesday</th>
              <td>{classroomDict[TTData.p5] && classroomDict[TTData.p5].csem} - {classroomDict[TTData.p5] && classroomDict[TTData.p5].cname}-{TTData.p5}</td>
              <td>{classroomDict[TTData.p5] && classroomDict[TTData.p5].csem} - {classroomDict[TTData.p5] && classroomDict[TTData.p5].cname}-{TTData.p5}</td>
              <td>{classroomDict[TTData.p6] && classroomDict[TTData.p6].csem} - {classroomDict[TTData.p6] && classroomDict[TTData.p6].cname}-{TTData.p6}</td>
              <td>{classroomDict[TTData.p6] && classroomDict[TTData.p6].csem} - {classroomDict[TTData.p6] && classroomDict[TTData.p6].cname}-{TTData.p6}</td>
              <td>Break</td>
              <td>{classroomDict[TTData.p7] && classroomDict[TTData.p7].csem} - {classroomDict[TTData.p7] && classroomDict[TTData.p7].cname}-{TTData.p7}</td>
              <td>{classroomDict[TTData.p7] && classroomDict[TTData.p7].csem} - {classroomDict[TTData.p7] && classroomDict[TTData.p7].cname}-{TTData.p7}</td>
              <td>{classroomDict[TTData.p8] && classroomDict[TTData.p8].csem} - {classroomDict[TTData.p8] && classroomDict[TTData.p8].cname}-{TTData.p8}</td>
              <td>{classroomDict[TTData.p8] && classroomDict[TTData.p8].csem} - {classroomDict[TTData.p8] && classroomDict[TTData.p8].cname}-{TTData.p8}</td>
            </tr>
            <tr>
            <th>Wednesday</th>
              <td>{classroomDict[TTData.p9] && classroomDict[TTData.p9].csem} - {classroomDict[TTData.p9] && classroomDict[TTData.p9].cname}-{TTData.p9}</td>
              <td>{classroomDict[TTData.p9] && classroomDict[TTData.p9].csem} - {classroomDict[TTData.p9] && classroomDict[TTData.p9].cname}-{TTData.p9}</td>
              <td>{classroomDict[TTData.p10] && classroomDict[TTData.p10].csem} - {classroomDict[TTData.p10] && classroomDict[TTData.p10].cname}-{TTData.p10}</td>
              <td>{classroomDict[TTData.p10] && classroomDict[TTData.p10].csem} - {classroomDict[TTData.p10] && classroomDict[TTData.p10].cname}-{TTData.p10}</td>
              <td>Break</td>
              <td>{classroomDict[TTData.p11] && classroomDict[TTData.p11].csem} - {classroomDict[TTData.p11] && classroomDict[TTData.p11].cname}-{TTData.p11}</td>
              <td>{classroomDict[TTData.p11] && classroomDict[TTData.p11].csem} - {classroomDict[TTData.p11] && classroomDict[TTData.p11].cname}-{TTData.p11}</td>
              <td>{classroomDict[TTData.p12] && classroomDict[TTData.p12].csem} - {classroomDict[TTData.p12] && classroomDict[TTData.p12].cname}-{TTData.p12}</td>
              <td>{classroomDict[TTData.p12] && classroomDict[TTData.p12].csem} - {classroomDict[TTData.p12] && classroomDict[TTData.p12].cname}-{TTData.p12}</td>
            </tr>
            <tr>
            <th>Thursday</th>
              <td>{classroomDict[TTData.p13] && classroomDict[TTData.p13].csem} - {classroomDict[TTData.p13] && classroomDict[TTData.p13].cname}-{TTData.p13}</td>
              <td>{classroomDict[TTData.p13] && classroomDict[TTData.p13].csem} - {classroomDict[TTData.p13] && classroomDict[TTData.p13].cname}-{TTData.p9}</td>
              <td>{classroomDict[TTData.p14] && classroomDict[TTData.p14].csem} - {classroomDict[TTData.p14] && classroomDict[TTData.p14].cname}-{TTData.p14}</td>
              <td>{classroomDict[TTData.p14] && classroomDict[TTData.p14].csem} - {classroomDict[TTData.p14] && classroomDict[TTData.p14].cname}-{TTData.p14}</td>
              <td>Break</td>
              <td>{classroomDict[TTData.p15] && classroomDict[TTData.p15].csem} - {classroomDict[TTData.p15] && classroomDict[TTData.p15].cname}-{TTData.p15}</td>
              <td>{classroomDict[TTData.p15] && classroomDict[TTData.p15].csem} - {classroomDict[TTData.p15] && classroomDict[TTData.p15].cname}-{TTData.p15}</td>
              <td>{classroomDict[TTData.p16] && classroomDict[TTData.p16].csem} - {classroomDict[TTData.p16] && classroomDict[TTData.p16].cname}-{TTData.p16}</td>
              <td>{classroomDict[TTData.p16] && classroomDict[TTData.p16].csem} - {classroomDict[TTData.p16] && classroomDict[TTData.p16].cname}-{TTData.p16}</td>
            </tr>
            <tr>
            <th>Friday</th>
              <td>{classroomDict[TTData.p17] && classroomDict[TTData.p17].csem} - {classroomDict[TTData.p17] && classroomDict[TTData.p17].cname}-{TTData.p17}</td>
              <td>{classroomDict[TTData.p17] && classroomDict[TTData.p17].csem} - {classroomDict[TTData.p17] && classroomDict[TTData.p17].cname}-{TTData.p17}</td>
              <td>{classroomDict[TTData.p18] && classroomDict[TTData.p18].csem} - {classroomDict[TTData.p18] && classroomDict[TTData.p18].cname}-{TTData.p18}</td>
              <td>{classroomDict[TTData.p18] && classroomDict[TTData.p18].csem} - {classroomDict[TTData.p18] && classroomDict[TTData.p18].cname}-{TTData.p18}</td>
              <td>Break</td>
              <td>{classroomDict[TTData.p19] && classroomDict[TTData.p19].csem} - {classroomDict[TTData.p19] && classroomDict[TTData.p19].cname}-{TTData.p19}</td>
              <td>{classroomDict[TTData.p19] && classroomDict[TTData.p19].csem} - {classroomDict[TTData.p19] && classroomDict[TTData.p19].cname}-{TTData.p19}</td>
              <td>{classroomDict[TTData.p20] && classroomDict[TTData.p20].csem} - {classroomDict[TTData.p20] && classroomDict[TTData.p20].cname}-{TTData.p20}</td>
              <td>{classroomDict[TTData.p20] && classroomDict[TTData.p20].csem} - {classroomDict[TTData.p20] && classroomDict[TTData.p20].cname}-{TTData.p20}</td>
            </tr>
          <tr>
            <th>Saturday</th>
            <td>{classroomDict[TTData.p21] && classroomDict[TTData.p21].csem} - {classroomDict[TTData.p21] && classroomDict[TTData.p21].cname}-{TTData.p21}</td>
            <td>{classroomDict[TTData.p21] && classroomDict[TTData.p21].csem} - {classroomDict[TTData.p21] && classroomDict[TTData.p21].cname}-{TTData.p21}</td>
            <td>{classroomDict[TTData.p18] && classroomDict[TTData.p22].csem} - {classroomDict[TTData.p22] && classroomDict[TTData.p22].cname}-{TTData.p22}</td>
            <td>{classroomDict[TTData.p22] && classroomDict[TTData.p22].csem} - {classroomDict[TTData.p22] && classroomDict[TTData.p22].cname}-{TTData.p22}</td>
            <td>Break</td>
            <td>--</td>
            <td>--</td>
            <td>--</td>
            <td>--</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    </div>
    </div>
  )
}