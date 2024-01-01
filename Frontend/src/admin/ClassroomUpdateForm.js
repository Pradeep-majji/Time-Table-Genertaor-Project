import {React,useState} from 'react'
import axios from 'axios';
import { useNavigate ,useLocation} from 'react-router-dom';

const ClassroomUpdateForm = () => 
{   const { state } = useLocation();
    const cid = state && state.cid;
    const [formData, setFormData] = useState({
        csem: '',
        ctype: '',
        cbatch: '',
      });
      
    
    const navigate=useNavigate();
      const handleFormSubmit = async (e) => {
        e.preventDefault();
    
        try {
          // Send a PUT request to update the classroom using the form data
          console.log(cid)
          let result=await axios.put(`http://localhost:8091/updateclassroom/${cid}/${formData.csem}/${formData.ctype}/${formData.cbatch}`);
          if (result.data==="OK") {
             alert("updated successfully");
            //else alert("object of time table for classroom is not created")
            navigate('/adminaddclassroom')
            }
          else {alert("not updated");
                navigate('/updateclassroom')}
        } 
          catch (error) {
          console.error('Error updating classroom:', error);
        }
      };

  return (
    <div><div>
    <h2>Update Classroom</h2>
    <form onSubmit={handleFormSubmit}>
      <label>
        Semester:
        <input
          type="text"
          name="csem"
          id="csem"
          value={formData.csem}
          onChange={(e) => setFormData({ ...formData, csem: e.target.value })}
        />
      </label>
      <br />

      <label>
        Type:
        <input
          type="text"
          name="ctype"
          id="ctype"
          value={formData.ctype}
          onChange={(e) => setFormData({ ...formData, ctype: e.target.value })}
        />
      </label>
      <br />

      <label>
        Batch:
        <input
          type="text"
          name="cbatch"
          id="cbatch"
          value={formData.cbatch}
          onChange={(e) => setFormData({ ...formData, cbatch: e.target.value })}
        />
      </label>
      <br />

      <button type="submit">Submit</button>
    </form>
  </div></div>
  )
}

export default ClassroomUpdateForm