import { useEffect, useState } from "react";
import api from "../services/api";
import { Link } from "react-router-dom";
import LogoutButton from "../components/LogoutButton";

export default function LecturerDashboard() {
  const [allCourses, setAllCourses] = useState([]);
  const [myCourses, setMyCourses] = useState([]);
  const [activeTab, setActiveTab] = useState("my"); // 'my' or 'all'
  const [isModalOpen, setIsModalOpen] = useState(false);

  // Form fields
  const [code, setCode] = useState("");
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [credits, setCredits] = useState("");

  const token = localStorage.getItem("token");
  const headers = { Authorization: `Bearer ${token}` };

  useEffect(() => {
    const fetchCourses = async () => {
      try {
        const resAll = await api.get("/courses", { headers });
        setAllCourses(resAll.data);

        const resMine = await api.get("/courses/by-lecturer", { headers });
        setMyCourses(resMine.data);
      } catch (err) {
        console.error(err);
      }
    };

    fetchCourses();
  }, []);

  const handleCreateCourse = async (e) => {
    e.preventDefault();
    try {
      await api.post(
        "/courses",
        { code, title, description, credits: Number(credits) },
        { headers }
      );
      alert("Course created successfully!");
      setIsModalOpen(false);
      setCode("");
      setTitle("");
      setDescription("");
      setCredits("");

      //   // Refresh courses
      //   const resMine = await api.get("/courses/my", { headers });
      //   setMyCourses(resMine.data);
      //   const resAll = await api.get("/courses", { headers });
      //   setAllCourses(resAll.data);
      window.location.reload();
    } catch (err) {
      console.error(err);
      alert("Failed to create course");
    }
  };

  const renderCourses = (courses) => (
    <ul>
      {courses.map((c) => (
        <li
          key={c.id}
          className="border p-4 mb-3 rounded-lg bg-white shadow hover:shadow-lg transition-shadow"
        >
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
        </li>
      ))}
    </ul>
  );

  return (
    <div className="p-8 bg-gray-50 min-h-screen">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-4xl font-bold mb-6 text-blue-500">
          Lecturer Dashboard
        </h1>
        <LogoutButton />
      </div>

      {/* Tabs */}
      <div className="flex gap-4 mb-6">
        <button
          onClick={() => setActiveTab("my")}
          className={`px-4 py-2 rounded-lg font-semibold ${
            activeTab === "my"
              ? "bg-blue-500 text-white shadow-lg"
              : "bg-white text-blue-500 border border-blue-500 hover:bg-blue-100"
          } transition-colors`}
        >
          My Courses
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

      {/* Create Course Button */}
      <button
        onClick={() => setIsModalOpen(true)}
        className="mb-6 px-5 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors"
      >
        + Create Course
      </button>

      {/* Courses */}
      {activeTab === "my"
        ? renderCourses(myCourses)
        : renderCourses(allCourses)}

      {/* Modal */}
      {isModalOpen && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white rounded-xl p-6 w-96 relative">
            <button
              onClick={() => setIsModalOpen(false)}
              className="absolute top-3 right-3 text-gray-500 hover:text-gray-800 font-bold"
            >
              ×
            </button>
            <h2 className="text-2xl font-bold mb-4 text-blue-500">
              Create New Course
            </h2>
            <form onSubmit={handleCreateCourse} className="flex flex-col gap-3">
              <input
                type="text"
                placeholder="Course Code"
                className="w-full p-2 border rounded"
                value={code}
                onChange={(e) => setCode(e.target.value)}
                required
              />
              <input
                type="text"
                placeholder="Course Title"
                className="w-full p-2 border rounded"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                required
              />
              <textarea
                placeholder="Course Description"
                className="w-full p-2 border rounded"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                required
              />
              <input
                type="number"
                placeholder="Credits"
                className="w-full p-2 border rounded"
                value={credits}
                onChange={(e) => setCredits(e.target.value)}
                required
              />
              <button
                type="submit"
                className="w-full bg-blue-500 text-white p-2 rounded hover:bg-blue-600 transition-colors"
              >
                Create
              </button>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}
