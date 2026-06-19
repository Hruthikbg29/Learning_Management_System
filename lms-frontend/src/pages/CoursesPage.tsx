import { useEffect, useState } from "react";
import { Plus, Pencil, Trash2, Search } from "lucide-react";
import toast from "react-hot-toast";
import type { Course } from "../services/course.service";
import { courseService } from "../services/course.service";
import DataTable from "../components/ui/DataTable";
import Modal from "../components/ui/Modal";
import Button from "../components/ui/Button";
import CourseForm from "../components/forms/CourseForm";

const CoursesPage = () => {
  const [courses,   setCourses]   = useState<Course[]>([]);
  const [loading,   setLoading]   = useState(true);
  const [modalOpen, setModalOpen] = useState(false);
  const [editing,   setEditing]   = useState<Course | null>(null);
  const [saving,    setSaving]    = useState(false);
  const [deleting,  setDeleting]  = useState<number | null>(null);
  const [keyword,   setKeyword]   = useState("");

  const fetchCourses = async () => {
    setLoading(true);
    try {
      const res = await courseService.getAll();
      setCourses(res.data.data ?? []);
    } catch {
      toast.error("Failed to load courses");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { fetchCourses(); }, []);

  const handleSearch = async () => {
    if (!keyword.trim()) { fetchCourses(); return; }
    setLoading(true);
    try {
      const res = await courseService.search(keyword);
      setCourses(res.data.data ?? []);
    } catch {
      toast.error("Search failed");
    } finally {
      setLoading(false);
    }
  };

  const handleSave = async (data: any) => {
    setSaving(true);
    try {
      if (editing) {
        await courseService.update(editing.id, data);
        toast.success("Course updated successfully!");
      } else {
        await courseService.create(data);
        toast.success("Course created successfully!");
      }
      setModalOpen(false);
      setEditing(null);
      fetchCourses();
    } catch (err: any) {
      toast.error(err.response?.data?.message || "Something went wrong");
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = async (id: number) => {
    if (!window.confirm("Are you sure you want to delete this course?")) return;
    setDeleting(id);
    try {
      await courseService.delete(id);
      toast.success("Course deleted successfully");
      fetchCourses();
    } catch {
      toast.error("Failed to delete course");
    } finally {
      setDeleting(null);
    }
  };

  const columns = [
    { header: "#",           accessor: "id"             as keyof Course },
    { header: "Title",       accessor: "title"          as keyof Course },
    { header: "Duration",    accessor: "duration"       as keyof Course },
    { header: "Max",         accessor: "maxStudents"    as keyof Course },
    { header: "Instructor",  accessor: (row: Course) => row.instructorName ?? "—" },
    {
      header: "Actions",
      accessor: (row: Course) => (
        <div className="flex gap-2">
          <button
            onClick={() => { setEditing(row); setModalOpen(true); }}
            className="p-1.5 text-blue-500 hover:bg-blue-50 dark:hover:bg-blue-900/20 rounded-lg transition"
          >
            <Pencil size={15} />
          </button>
          <button
            onClick={() => handleDelete(row.id)}
            disabled={deleting === row.id}
            className="p-1.5 text-red-500 hover:bg-red-50 dark:hover:bg-red-900/20 rounded-lg transition"
          >
            <Trash2 size={15} />
          </button>
        </div>
      ),
    },
  ];

  return (
    <div>
      <div className="flex items-center justify-between mb-6">
        <div>
          <h1 className="text-2xl font-bold text-gray-900 dark:text-white">Courses</h1>
          <p className="text-gray-500 dark:text-gray-400 text-sm mt-1">
            Manage all available courses
          </p>
        </div>
        <Button
          icon={<Plus size={16} />}
          onClick={() => { setEditing(null); setModalOpen(true); }}
        >
          Add Course
        </Button>
      </div>

      {/* Search Bar */}
      <div className="flex gap-3 mb-4">
        <div className="relative flex-1 max-w-sm">
          <Search size={16} className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" />
          <input
            type="text"
            placeholder="Search courses..."
            value={keyword}
            onChange={(e) => setKeyword(e.target.value)}
            onKeyDown={(e) => e.key === "Enter" && handleSearch()}
            className="w-full pl-9 pr-4 py-2 rounded-lg border border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-700 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
        </div>
        <Button variant="secondary" onClick={handleSearch}>Search</Button>
        <Button variant="ghost" onClick={() => { setKeyword(""); fetchCourses(); }}>Clear</Button>
      </div>

      <div className="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6">
        <DataTable data={courses} columns={columns} loading={loading} />
      </div>

      <Modal
        isOpen={modalOpen}
        onClose={() => { setModalOpen(false); setEditing(null); }}
        title={editing ? "Edit Course" : "Add New Course"}
        size="lg"
      >
        <CourseForm
          defaultValues={editing ? {
            title:        editing.title,
            description:  editing.description,
            duration:     editing.duration,
            maxStudents:  editing.maxStudents,
            instructorId: editing.instructorId ?? undefined,
          } : undefined}
          onSubmit={handleSave}
          onCancel={() => { setModalOpen(false); setEditing(null); }}
          loading={saving}
        />
      </Modal>
    </div>
  );
};

export default CoursesPage;