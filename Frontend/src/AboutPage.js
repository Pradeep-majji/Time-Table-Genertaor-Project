import React from 'react'
import IndexNavbar from './IndexNavbar'
import './ContactPage.css'
import { Link } from 'react-router-dom'

const AboutPage = () => {
  return (
    <div>
      <IndexNavbar/>
      <div className='container-1'>
      <div className='watermark-1'></div>
      <div className='text-content'>
        <h1>About Us</h1><div style={{height:'200px',width:'800px',marginLeft:'300px'}}>
        Sagi Rama Krishnam Raju Engineering College (SRKREC), established in 1980, is a self-financed academic institution of coeducation striving to provide a high quality technical education to engineering aspirants. Being one of the premier and well-established technical institutions of the country, it continues to render service to the nation and the world at large with its alumni holding highly prestigious positions and making substantial contribution
           <br /><a href='https://srkrec.edu.in/college_profile.php'>View More</a>
        </div></div>
    </div>
      </div>
  )
}

export default AboutPage

