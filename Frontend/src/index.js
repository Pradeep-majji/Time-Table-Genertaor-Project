import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import { BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import reportWebVitals from './reportWebVitals';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';


import LoginForm from './LoginForm';
import RegisterationForm from './RegisterationForm.js';
import HomePage from './HomePage';
import AboutPage from './AboutPage';
import ContactPage from './ContactPage';

import ClassTimetable from './ClassTimetable.js';

import TeacherHome from './teacher/TeacherHome.js';
import TeacherAllotment from './teacher/TeacherAllotment.js';
import TeacherTimetable from './teacher/TeacherTimetable.js';

import AdminHome from './admin/AdminHome.js';
import AdminAddTeachers  from './admin/AdminAddTeachers.js';
import AdminAddSubjects  from './admin/AdminAddSubjects.js';
import AdminAddClassrooms from './admin/AdminAddClassrooms.js';
import AdminAllotment from './admin/AdminAllotment.js';
import AdminGenerateTT from './admin/AdminGenerateTT.js';

import SubjectsForm from './admin/SubjectsForm.js';
import ClassroomForm from './admin/ClassroomForm.js';
import ClassroomUpdateForm from './admin/ClassroomUpdateForm.js';
import AllotmentForm from './teacher/AllotmentForm.js';

import LogoutForm  from './LogoutForm.js';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
     <Router>
      <div>
        <Routes>

          <Route path="/login" element={<LoginForm />} />
          <Route path="/aboutpage" element={<AboutPage />} />
          <Route path="/register" element={<RegisterationForm />} />
          <Route path="/" element={<HomePage />} />
          <Route path="/contact" element={<ContactPage />} />
          
          
          <Route path="/classtimetable" element={<ClassTimetable />} />
          <Route path="/allotment" element={<AllotmentForm />} />

          <Route path="/teacherhome" element={<TeacherHome />} />
          <Route path="/teacherAllotment" element={<TeacherAllotment />} />
          <Route path="/teachertimetable" element={<TeacherTimetable />} />


          <Route path="/adminhome" element={<AdminHome />} />
          <Route path="/adminaddteacher" element={<AdminAddTeachers />} />
          <Route path="/adminaddsubject" element={<AdminAddSubjects />} />
          <Route path="/adminaddclassroom" element={<AdminAddClassrooms />} />
          <Route path="/adminallotment" element={<AdminAllotment />} />
          <Route path="/admingeneratett" element={<AdminGenerateTT />} />

          <Route path="/addsubject" element={<SubjectsForm />} />         
          <Route path="/addclassroom" element={<ClassroomForm />} />         
          <Route path="/updateclassroom" element={<ClassroomUpdateForm />} />
          <Route path="/allotment" element={<AllotmentForm />} />

          <Route path="/logout" element={<LogoutForm />} />
          
        </Routes>
      </div>
  </Router>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
