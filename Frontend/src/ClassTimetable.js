import {React,useState,useEffect} from 'react'
import { useLocation } from 'react-router-dom';
import LoginService from './LoginService';

export default function  ClassTimetable(){
  const { state } = useLocation();
  const [cid, setCid] = useState('');
  
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
  )
}