import {React,useState,useEffect} from 'react'
import { useLocation } from 'react-router-dom';
import LoginService from './LoginService';
import axios from 'axios';

export default function  ClassTimetable(){
  const { state } = useLocation();
  const [cid, setCid] = useState('');
  const [subjects, setSubjects] = useState([]);
  const [classrooms, setClassrooms] = useState([]); 
  
  const [TTData, setTTData] = useState({
    cid: '',
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
    console.log("inside useeffect",cid)
    const params = new URLSearchParams(state);
    const cidParam = params.get('cid');
    if(cidParam){
      console.log("in side if with cid",cid)
      setCid(cidParam);
    const fetchData = async () => {
      try {
        console.log("inside in fetching data",cid);
        const result = await LoginService.getTT(cid);
        setTTData(result.data);
        console.log(TTData);
        const result1 = await axios.get('http://localhost:8091/subjectlist');
        const formattedSubjects = formatSubjects(result1.data);
        setSubjects(formattedSubjects);
        console.log(subjects);
        const result2 = await axios.get('http://localhost:8091/classrooms');
        const formattedClassrooms = formatClassrooms(result2.data);
        setClassrooms(formattedClassrooms);
        console.log(classrooms);
      } catch (error) {
        console.log("in fetching error")
        console.error('Error fetching user data:', error);
      }
    };
    fetchData();
    }
    else{
      alert("classroom is not alloted to any class");
    }
  } , [state,cid]);

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


  return (
    <div className="container mt-5">
      <h1 className="text-center">{TTData.cid}</h1>
      <div className="table-responsive">
        <table className="table table-bordered">
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
              <td>{getDisplayName(TTData.p1)}</td>
              <td>{getDisplayName(TTData.p1)}</td>
              <td>{getDisplayName(TTData.p2)}</td>
              <td>{getDisplayName(TTData.p2)}</td>
              <td>Break</td>
              <td>{getDisplayName(TTData.p3)}</td>
              <td>{getDisplayName(TTData.p3)}</td>
              <td>{getDisplayName(TTData.p4)}</td>
              <td>{getDisplayName(TTData.p4)}</td>
            </tr>
            <tr>
              <th>Tuesday</th>
              <td>{getDisplayName(TTData.p5)}</td>
              <td>{getDisplayName(TTData.p5)}</td>
              <td>{getDisplayName(TTData.p6)}</td>
              <td>{getDisplayName(TTData.p6)}</td>
              <td>Break</td>
              <td>{getDisplayName(TTData.p7)}</td>
              <td>{getDisplayName(TTData.p7)}</td>
              <td>{getDisplayName(TTData.p8)}</td>
              <td>{getDisplayName(TTData.p8)}</td>
            </tr>
            <tr>
              <th>Wednesday</th>
              <td>{getDisplayName(TTData.p9)}</td>
              <td>{getDisplayName(TTData.p9)}</td>
              <td>{getDisplayName(TTData.p10)}</td>
              <td>{getDisplayName(TTData.p10)}</td>
              <td>Break</td>
              <td>{getDisplayName(TTData.p11)}</td>
              <td>{getDisplayName(TTData.p11)}</td>
              <td>{getDisplayName(TTData.p12)}</td>
              <td>{getDisplayName(TTData.p12)}</td>
            </tr>
            <tr>
              <th>Thursday</th>
              <td>{getDisplayName(TTData.p13)}</td>
              <td>{getDisplayName(TTData.p13)}</td>
              <td>{getDisplayName(TTData.p14)}</td>
              <td>{getDisplayName(TTData.p14)}</td>
              <td>Break</td>
              <td>{getDisplayName(TTData.p15)}</td>
              <td>{getDisplayName(TTData.p15)}</td>
              <td>{getDisplayName(TTData.p16)}</td>
              <td>{getDisplayName(TTData.p16)}</td>
            </tr>
            <tr>
              <th>Friday</th>
              <td>{getDisplayName(TTData.p17)}</td>
              <td>{getDisplayName(TTData.p17)}</td>
              <td>{getDisplayName(TTData.p18)}</td>
              <td>{getDisplayName(TTData.p18)}</td>
              <td>Break</td>
              <td>{getDisplayName(TTData.p19)}</td>
              <td>{getDisplayName(TTData.p19)}</td>
              <td>{getDisplayName(TTData.p20)}</td>
              <td>{getDisplayName(TTData.p20)}</td>
            </tr>
            <tr>
              <th>Saturday</th>
              <td>{getDisplayName(TTData.p21)}</td>
              <td>{getDisplayName(TTData.p21)}</td>
              <td>{getDisplayName(TTData.p22)}</td>
              <td>{getDisplayName(TTData.p22)}</td>
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
  )
}