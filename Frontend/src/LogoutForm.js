import React from 'react'
import { useNavigate } from 'react-router-dom'

const LogoutForm = () => {
  const navigate=useNavigate();
  const handleTOMain= () => {
    localStorage.removeItem('email');
    navigate('/');
  }
  return (
    <div style={{display: 'flex',flexDirection: 'column',justifyContent: 'center',alignItems: 'center',marginTop:'300px'}}>
      <h1>are you sure to logout</h1>
      <button onClick={handleTOMain} className="btn btn-danger">click me to logout</button>  
    </div>
  )
}

export default LogoutForm;