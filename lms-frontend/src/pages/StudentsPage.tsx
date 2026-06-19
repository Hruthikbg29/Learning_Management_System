import { useEffect, useState } from "react";
import { Plus, Pencil, Trash2 } from "lucide-react";
import toast from "react-hot-toast";
import type { Student } from "../services/student.service";
import { studentService } from "../services/student.service";
import DataTable from "../components/ui/DataTable";
import Modal from "../components/ui/Modal";
import Button from "../components/ui/Button";
import StudentForm from "../components/forms/StudentForm";

const StudentsPage = () => {
  const [students, setStudents]     = useState<Student[]>([]);
  const [loading, setLoading]       = useState(true);
  const [modalOpen, setModalOpen]   = useState(false);
  const [editing, setEditing]       = useState<Student | null>(null);
  const [saving, setSaving]         = useState(false);
  const [deleting, setDeleting]     = useState<number | null>(null);

  const fetchStudents = async () => {
    setLoading(true);
    try {
      const res = await studentService.getAll();
      setStudents(res.data.data ?? []);
    } catch {
      toast.error("Failed to load students");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { fetchStudents(); }, []);

  const handleSave = async (data: any) => {
    setSaving(true);
    try {
      if (editing) {
        await studentService.update(editing.id, data);
        toast.success("Student updated!");
      } else {
        await studentService.create(data);
        toast.success("Student created!");
      }
      setModalOpen(false);
      setEditing(null);
      fetchStudents();
    } catch (err: any) {
      toast.error(err.response?.data?.message || "Something went wrong");
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = async (id: number) => {
    if (!window.confirm("Delete this student?")) return;
    setDeleting(id);
    try {
      await studentService.delete(id);
      toast.success("Student deleted");
      fetchStudents();
    } catch {
      toast.error("Failed to delete");
    } finally {
      setDeleting(null);
    }
  };

  const columns = [
    { header: "#",      accessor: "id" as keyof Student },
    { header: "Name",   accessor: "name" as keyof Student },
    { header: "Email",  accessor: "email" as keyof Student },
    { header: "Phone",  accessor: "phone" as keyof Student },
    {
      header: "Actions",
      accessor: (row: Student) => (
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
      {/* Header */}
      <div className="flex items-center justify-between mb-6">
        <div>
          <h1 className="text-2xl font-bold text-gray-900 dark:text-white">Students</h1>
          <p className="text-gray-500 dark:text-gray-400 text-sm mt-1">
            Manage all enrolled students
          </p>
        </div>
        <Button
          icon={<Plus size={16} />}
          onClick={() => { setEditing(null); setModalOpen(true); }}
        >
          Add Student
        </Button>
      </div>

      {/* Table */}
      <div className="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6">
        <DataTable data={students} columns={columns} loading={loading} />
      </div>

      {/* Modal */}
      <Modal
        isOpen={modalOpen}
        onClose={() => { setModalOpen(false); setEditing(null); }}
        title={editing ? "Edit Student" : "Add New Student"}
      >
        <StudentForm
          defaultValues={editing ?? undefined}
          onSubmit={handleSave}
          onCancel={() => { setModalOpen(false); setEditing(null); }}
          loading={saving}
        />
      </Modal>
    </div>
  );
};

export default StudentsPage;