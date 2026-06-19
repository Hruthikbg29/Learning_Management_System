import { useEffect, useState } from "react";
import { Plus, Pencil, Trash2 } from "lucide-react";
import toast from "react-hot-toast";
import type { Instructor } from "../services/instructor.service";
import { instructorService } from "../services/instructor.service";
import DataTable from "../components/ui/DataTable";
import Modal from "../components/ui/Modal";
import Button from "../components/ui/Button";
import InstructorForm from "../components/forms/InstructorForm";

const InstructorsPage = () => {
  const [instructors, setInstructors] = useState<Instructor[]>([]);
  const [loading,     setLoading]     = useState(true);
  const [modalOpen,   setModalOpen]   = useState(false);
  const [editing,     setEditing]     = useState<Instructor | null>(null);
  const [saving,      setSaving]      = useState(false);
  const [deleting,    setDeleting]    = useState<number | null>(null);

  const fetchInstructors = async () => {
    setLoading(true);
    try {
      const res = await instructorService.getAll();
      setInstructors(res.data.data ?? []);
    } catch {
      toast.error("Failed to load instructors");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { fetchInstructors(); }, []);

  const handleSave = async (data: any) => {
    setSaving(true);
    try {
      if (editing) {
        await instructorService.update(editing.id, data);
        toast.success("Instructor updated successfully!");
      } else {
        await instructorService.create(data);
        toast.success("Instructor created successfully!");
      }
      setModalOpen(false);
      setEditing(null);
      fetchInstructors();
    } catch (err: any) {
      toast.error(err.response?.data?.message || "Something went wrong");
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = async (id: number) => {
    if (!window.confirm("Are you sure you want to delete this instructor?")) return;
    setDeleting(id);
    try {
      await instructorService.delete(id);
      toast.success("Instructor deleted successfully");
      fetchInstructors();
    } catch {
      toast.error("Failed to delete instructor");
    } finally {
      setDeleting(null);
    }
  };

  const columns = [
    { header: "#",               accessor: "id"             as keyof Instructor },
    { header: "Name",            accessor: "name"           as keyof Instructor },
    { header: "Email",           accessor: "email"          as keyof Instructor },
    { header: "Phone",           accessor: "phone"          as keyof Instructor },
    { header: "Specialization",  accessor: "specialization" as keyof Instructor },
    {
      header: "Actions",
      accessor: (row: Instructor) => (
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
          <h1 className="text-2xl font-bold text-gray-900 dark:text-white">Instructors</h1>
          <p className="text-gray-500 dark:text-gray-400 text-sm mt-1">
            Manage all instructors
          </p>
        </div>
        <Button
          icon={<Plus size={16} />}
          onClick={() => { setEditing(null); setModalOpen(true); }}
        >
          Add Instructor
        </Button>
      </div>

      <div className="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6">
        <DataTable data={instructors} columns={columns} loading={loading} />
      </div>

      <Modal
        isOpen={modalOpen}
        onClose={() => { setModalOpen(false); setEditing(null); }}
        title={editing ? "Edit Instructor" : "Add New Instructor"}
      >
        <InstructorForm
          defaultValues={editing ?? undefined}
          onSubmit={handleSave}
          onCancel={() => { setModalOpen(false); setEditing(null); }}
          loading={saving}
        />
      </Modal>
    </div>
  );
};

export default InstructorsPage;