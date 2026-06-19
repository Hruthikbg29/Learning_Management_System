import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Users, GraduationCap, BookOpen, ClipboardList } from "lucide-react";
import StatCard from "../components/ui/StatCard";
import { studentService } from "../services/student.service";
import { instructorService } from "../services/instructor.service";
import { courseService } from "../services/course.service";
import { enrollmentService } from "../services/enrollment.service";
import { useAuthStore } from "../store/authStore";

const DashboardPage = () => {
  const { username } = useAuthStore();
  const navigate = useNavigate();
  const [stats, setStats] = useState({
    students: 0, instructors: 0, courses: 0, enrollments: 0
  });
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchStats = async () => {
      try {
        const [s, i, c, e] = await Promise.all([
          studentService.getAll(),
          instructorService.getAll(),
          courseService.getAll(),
          enrollmentService.getAll(),
        ]);
        setStats({
          students:    s.data.data?.length ?? 0,
          instructors: i.data.data?.length ?? 0,
          courses:     c.data.data?.length ?? 0,
          enrollments: e.data.data?.length ?? 0,
        });
      } catch (err) {
        console.error(err);
      } finally {
        setLoading(false);
      }
    };
    fetchStats();
  }, []);

  const quickActions = [
    { label: "Add Student",    path: "/students"    },
    { label: "Add Instructor", path: "/instructors" },
    { label: "Create Course",  path: "/courses"     },
    { label: "Enroll Student", path: "/enrollments" },
  ];

  return (
    <div>
      {/* Header */}
      <div className="mb-8">
        <h1 className="text-2xl font-bold text-gray-900 dark:text-white">
          Welcome back, {username}! 👋
        </h1>
        <p className="text-gray-500 dark:text-gray-400 mt-1">
          Here's what's happening in your LMS today.
        </p>
      </div>

      {/* Stat Cards */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        <StatCard
          title="Total Students"
          value={loading ? "..." : stats.students}
          icon={Users}
          color="blue"
          subtitle="Registered students"
        />
        <StatCard
          title="Instructors"
          value={loading ? "..." : stats.instructors}
          icon={GraduationCap}
          color="green"
          subtitle="Active instructors"
        />
        <StatCard
          title="Courses"
          value={loading ? "..." : stats.courses}
          icon={BookOpen}
          color="purple"
          subtitle="Available courses"
        />
        <StatCard
          title="Enrollments"
          value={loading ? "..." : stats.enrollments}
          icon={ClipboardList}
          color="orange"
          subtitle="Total enrollments"
        />
      </div>

      {/* Quick Actions */}
      <div className="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6">
        <h2 className="font-semibold text-gray-900 dark:text-white mb-4">
          Quick Actions
        </h2>
        <div className="grid grid-cols-2 md:grid-cols-4 gap-3">
          {quickActions.map(({ label, path }) => (
            <button
              key={path}
              onClick={() => navigate(path)}
              className="flex items-center justify-center py-3 px-4 bg-blue-50 dark:bg-blue-900/20 text-blue-600 dark:text-blue-400 rounded-lg text-sm font-medium hover:bg-blue-100 dark:hover:bg-blue-900/40 transition"
            >
              {label}
            </button>
          ))}
        </div>
      </div>
    </div>
  );
};

export default DashboardPage;