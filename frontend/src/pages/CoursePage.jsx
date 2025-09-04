import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import api from "../services/api";
import LogoutButton from "../components/LogoutButton";

export default function CoursePage() {
  const { id } = useParams(); // courseId from route
  const [course, setCourse] = useState(null);
  const [contents, setContents] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [newContent, setNewContent] = useState({
    title: "",
    type: "",
    url: "",
  });

  const role = localStorage.getItem("role"); // check if Lecturer

  useEffect(() => {
    const fetchData = async () => {
      const token = localStorage.getItem("token");
      const headers = { Authorization: `Bearer ${token}` };

      try {
        const resCourse = await api.get(`/courses/${id}`, { headers });
        setCourse(resCourse.data);

        const resContents = await api.get(`/course-contents/course/${id}`, {
          headers,
        });
        setContents(resContents.data);
      } catch (err) {
        console.error("Failed to fetch course details:", err);
      }
    };

    fetchData();
  }, [id]);

  const handleAddContent = async () => {
    const token = localStorage.getItem("token");
    const headers = { Authorization: `Bearer ${token}` };

    try {
      await api.post(`/courses/${id}/contents`, newContent, { headers });
      alert("Content added successfully!");
      setShowModal(false);
      setNewContent({ title: "", type: "", url: "" });

      // refresh contents
      const resContents = await api.get(`/courses/${id}/contents`, { headers });
      setContents(resContents.data);
      // window.location.reload();
    } catch (err) {
      console.error("Failed to add content:", err);
      alert("Failed to add content");
    }
  };

  if (!course) {
    return <div className="p-8 text-gray-600">Loading course...</div>;
  }

  return (
    <div className="p-8 bg-gray-50 min-h-screen">
      {/* Course Details */}
      <div className="bg-white p-6 rounded-xl shadow-lg mb-8 flex justify-between items-center">
        <div>
          <h1 className="text-3xl font-bold text-blue-600 mb-2">
            {course.title}
          </h1>
          <p className="text-gray-600 mb-2">{course.description}</p>
          <p className="text-sm text-gray-500">Code: {course.code}</p>
          <p className="text-sm text-gray-500">Credits: {course.credits}</p>
        </div>
        <LogoutButton />
      </div>

      {/* Course Contents */}
      <div className="bg-white p-6 rounded-xl shadow-lg">
        <div className="flex justify-between items-center mb-4">
          <h2 className="text-2xl font-semibold text-blue-500">
            Course Contents
          </h2>
          {role === "LECTURER" && (
            <button
              onClick={() => setShowModal(true)}
              className="px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors"
            >
              + Add Content
            </button>
          )}
        </div>

        {contents.length === 0 ? (
          <p className="text-gray-500">
            No contents available for this course.
          </p>
        ) : (
          <ul className="space-y-4">
            {contents.map((content) => (
              <li
                key={content.id}
                className="p-4 border rounded-lg shadow-sm hover:shadow-md transition-shadow"
              >
                <p className="font-medium text-gray-800">{content.title}</p>
                <p className="text-sm text-gray-500">{content.type}</p>
                {content.url && (
                  <a
                    href={content.url}
                    target="_blank"
                    rel="noopener noreferrer"
                    className="text-blue-500 hover:underline text-sm"
                  >
                    View Content
                  </a>
                )}
              </li>
            ))}
          </ul>
        )}
      </div>

      {/* Add Content Modal */}
      {showModal && (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
          <div className="bg-white p-6 rounded-xl shadow-lg w-96">
            <h2 className="text-xl font-semibold mb-4 text-blue-600">
              Add Course Content
            </h2>

            <input
              type="text"
              placeholder="Title"
              value={newContent.title}
              onChange={(e) =>
                setNewContent({ ...newContent, title: e.target.value })
              }
              className="w-full mb-3 px-3 py-2 border rounded-lg"
            />

            {/* <input
              type="text"
              placeholder="Type (PDF, Video, etc.)"
              value={newContent.type}
              onChange={(e) =>
                setNewContent({ ...newContent, type: e.target.value })
              }
              className="w-full mb-3 px-3 py-2 border rounded-lg"
            /> */}

            <input
              type="text"
              placeholder="Content URL"
              value={newContent.url}
              onChange={(e) =>
                setNewContent({ ...newContent, url: e.target.value })
              }
              className="w-full mb-3 px-3 py-2 border rounded-lg"
            />

            <div className="flex justify-end gap-3">
              <button
                onClick={() => setShowModal(false)}
                className="px-4 py-2 bg-gray-300 rounded-lg hover:bg-gray-400"
              >
                Cancel
              </button>
              <button
                onClick={handleAddContent}
                className="px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600"
              >
                Save
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
