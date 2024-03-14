import axios from "axios";
class AdminService
{
    static addTeacher(formData)
    {
        return axios.post('http://localhost:8091/addteacher', formData)
    }
    static addSubject(formData)
    {
        return axios.post('http://localhost:8091/addsubject', formData)
    }
    static addClassroom(formData)
    {
        return axios.post('http://localhost:8091/addclassroom', formData)
    }
    static updateClassroom(formData)
    {
        return axios.post('http://localhost:8091/updateclassroom', formData)
    }
    static generatett()
    {
        return axios.get(`http://localhost:8091/generatett`)
    }
    static resettt()
    {
        return axios.get(`http://localhost:8091/resettt`)
    }
    /*static resettt()
    {   
        let classroomlist = await axios.get('http://localhost:8091/classrooms');
        let teacherallotedlist=await axios.get('http://localhost:8091/teacherv');
        if(classroomlist.data==="OK" && teacherallotedlist.data==="OK"){

            //for every classroom which is in database
            const [CTTData, setCTTData] = useState({
                cid: '',
                p1: '-',p2: '-',p3: '-',p4: '-', p5: '-',p6: '-',p7: '-',p8: '-',p9: '-',p10: '-',p11: '-',p12: '-',p13: '-',
                p14: '-',p15: '-',p16: '-',p17: '-',p18: '-',p19: '-',p20: '-',p21: '-',p22: '-'
              });
            classroomList.forEach((cid) => {
                setCTTData({
                    ...CTTData,
                    cid:cid,
                  });
                  await axios.post(`http://localhost:8091/addclassroomtt`,CTTData)
              });

            //for every teacher who are verified in database by the admin
            const [TTTData, setTTTData] = useState({
                tid: '',
                p1: '-',p2: '-',p3: '-',p4: '-', p5: '-',p6: '-',p7: '-',p8: '-',p9: '-',p10: '-',p11: '-',p12: '-',p13: '-',
                p14: '-',p15: '-',p16: '-',p17: '-',p18: '-',p19: '-',p20: '-',p21: '-',p22: '-'
              });
            teacherAllotedList.forEach((tid) => {
                setTTTData({
                    ...TTTData,
                    tid:tid,
                  });
                  await axios.post(`http://localhost:8091/addteachertt`,TTTData) 
              });

        }
    }*/
    static verifyClassroom(cid)
    {
        return axios.get(`http://localhost:8091/verifyclassrooms/${cid}`)
    }

}
export default AdminService;