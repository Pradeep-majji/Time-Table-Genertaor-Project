import React from 'react'
import AdminNavbar from './AdminNavbar';
import './Admin.css'
const AdminHome = () => {
  return (
    <div>
        <AdminNavbar/>
      <div className='container-3'>
      <div className='watermark-3'></div>
      <div className='text-content'>
        Admin Home
      </div>
      </div>
    </div>
  )
}

export default AdminHome;