import axios from 'axios';
class LoginService
{
    static verifyTeacher(uname,password)
    {
        return axios.get(`http://localhost:8091/verifyteacher/${uname}/${password}`);
    }
    static addTeacher(formData)
    {
        return axios.post('http://localhost:8091/addteacher', formData)
    }
    static verifyAdmin(uname,password)
    {  
        return axios.get(`http://localhost:8091/verifyadmin/${uname}/${password}`);
    }
    static getTT(cid)
    {  
        return axios.get(`http://localhost:8091/classroomtt/${cid}`);
    }
    static getallotedclasses()
    {  
        return axios.get(`http://localhost:8091/classroomslist`);
    }
}
export default LoginService;