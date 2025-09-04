import { useEffect, useState } from "react";
import api from "../services/api";
import { Link } from "react-router-dom";
import LogoutButton from "../components/LogoutButton";

export default function StudentDashboard() {
  const [allCourses, setAllCourses] = useState([]);
  const [enrolledCourses, setEnrolledCourses] = useState([]);
  const [activeTab, setActiveTab] = useState("enrolled"); // 'enrolled' or 'all'

  useEffect(() => {
    const fetchCourses = async () => {
      const token = localStorage.getItem("token");
      const headers = { Authorization: `Bearer ${token}` };

      try {
        const resAll = await api.get("/courses", { headers });
        setAllCourses(resAll.data);

        const resEnrolled = await api.get("/registrations/student/allCourses", {
          headers,
        });

        const coursesOnly = resEnrolled.data.map((reg) => reg.course);
        setEnrolledCourses(coursesOnly);

        // setEnrolledCourses(resEnrolled.data);
      } catch (err) {
        console.error(err);
      }
    };

    fetchCourses();
  }, []);

  const handleEnroll = async (courseId) => {
    const token = localStorage.getItem("token");
    const headers = { Authorization: `Bearer ${token}` };

    try {
      await api.post(
        `/registrations/enroll?courseId=${courseId}`,
        {},
        { headers }
      );
      alert("Enrolled successfully!");
      // refresh enrolled courses
      const resEnrolled = await api.get("/registrations/my", { headers });
      setEnrolledCourses(resEnrolled.data);
    } catch (err) {
      console.error(err);
      alert("Failed to enroll");
    }
  };

  const renderCourses = (courses, showEnroll = false) => (
    <ul>
      {courses.map((c) => (
        <li
          key={c.id}
          className="border p-4 mb-3 rounded-lg bg-white shadow hover:shadow-lg transition-shadow flex justify-between items-center"
        >
          <div>
            {/* <h3 className="font-semibold text-gray-800">{c.title}</h3> */}
            <Link
              to={`/courses/${c.id}`}
              className="font-medium text-gray-800 hover:underline"
            >
              {c.title}
            </Link>
            <p className="text-gray-600">{c.description}</p>
            <p className="text-sm text-gray-500">
              Code: {c.code} | Credits: {c.credits}
            </p>
          </div>
          {showEnroll && (
            <button
              onClick={() => handleEnroll(c.id)}
              className="px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors"
            >
              Enroll
            </button>
          )}
        </li>
      ))}
    </ul>
  );

  return (
    <div className="p-8 bg-gray-50 min-h-screen">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-4xl font-bold mb-6 text-blue-500">
          Student Dashboard
        </h1>
        <LogoutButton />
      </div>
      {/* Tabs */}
      <div className="flex gap-4 mb-6">
        <button
          onClick={() => setActiveTab("enrolled")}
          className={`px-4 py-2 rounded-lg font-semibold ${
            activeTab === "enrolled"
              ? "bg-blue-500 text-white shadow-lg"
              : "bg-white text-blue-500 border border-blue-500 hover:bg-blue-100"
          } transition-colors`}
        >
          My Enrolled Courses
        </button>
        <button
          onClick={() => setActiveTab("all")}
          className={`px-4 py-2 rounded-lg font-semibold ${
            activeTab === "all"
              ? "bg-blue-500 text-white shadow-lg"
              : "bg-white text-blue-500 border border-blue-500 hover:bg-blue-100"
          } transition-colors`}
        >
          All Courses
        </button>
      </div>

      {/* Courses */}
      {activeTab === "enrolled"
        ? renderCourses(enrolledCourses)
        : renderCourses(allCourses, true)}
    </div>
  );
}
