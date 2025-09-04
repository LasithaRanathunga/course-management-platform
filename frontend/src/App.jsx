import "./App.css";

import { BrowserRouter, Routes, Route } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import CoursePage from "./pages/CoursePage";
import LecturerDashboard from "./pages/LecturerDashbord";
import StudentDashboard from "./pages/StudentDashboard";
// import EnrollmentsPage from "./pages/EnrollmentsPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        {/* <Route path="/courses" element={<CoursesPage />} /> */}
        <Route path="/lecturer-dashboard" element={<LecturerDashboard />} />
        <Route path="/student-dashboard" element={<StudentDashboard />} />
        <Route path="/courses/:id" element={<CoursePage />} />
        {/* <Route path="/enrollments" element={<EnrollmentsPage />} /> */}
      </Routes>
    </BrowserRouter>
  );
}

export default App;
