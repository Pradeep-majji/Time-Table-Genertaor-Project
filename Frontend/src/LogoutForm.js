import React from 'react'
import { useNavigate } from 'react-router-dom'

const LogoutForm = () => {
  const navigate=useNavigate();
  const handleTOMain= () => {
    localStorage.removeItem('email');
    navigate('/');
  }
  return (
    <div>
      <h1>are you sure to logout</h1>
      <button onClick={handleTOMain}>click me to logout</button>  
    </div>
  )
}

export default LogoutForm;