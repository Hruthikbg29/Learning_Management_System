import { useEffect, useState } from "react";
import { Plus, Trash2 } from "lucide-react";
import toast from "react-hot-toast";
import type { Enrollment } from "../services/enrollment.service";
import { enrollmentService } from "../services/enrollment.service";
import DataTable from "../components/ui/DataTable";
import Modal from "../components/ui/Modal";
import Button from "../components/ui/Button";
import EnrollmentForm from "../components/forms/EnrollmentForm";

const statusColors: Record<string, string> = {
  ACTIVE:    "bg-green-100 text-green-700 dark:bg-green-900/30 dark:text-green-400",
  COMPLETED: "bg-blue-100 text-blue-700 dark:bg-blue-900/30 dark:text-blue-400",
  DROPPED:   "bg-red-100 text-red-700 dark:bg-red-900/30 dark:text-red-400",
};

const EnrollmentsPage = () => {
  const [enrollments, setEnrollments] = useState<Enrollment[]>([]);
  const [loading,     setLoading]     = useState(true);
  const [modalOpen,   setModalOpen]   = useState(false);
  const [saving,      setSaving]      = useState(false);
  const [deleting,    setDeleting]    = useState<number | null>(null);

  const fetchEnrollments = async () => {
    setLoading(true);
    try {
      const res = await enrollmentService.getAll();
      setEnrollments(res.data.data ?? []);
    } catch {
      toast.error("Failed to load enrollments");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { fetchEnrollments(); }, []);

  const handleEnroll = async (data: any) => {
    setSaving(true);
    try {
      await enrollmentService.enroll(data);
      toast.success("Student enrolled successfully!");
      setModalOpen(false);
      fetchEnrollments();
    } catch (err: any) {
      toast.error(err.response?.data?.message || "Enrollment failed");
    } finally {
      setSaving(false);
    }
  };

  const handleStatusChange = async (id: number, status: string) => {
    try {
      await enrollmentService.updateStatus(id, status);
      toast.success(`Status updated to ${status}`);
      fetchEnrollments();
    } catch {
      toast.error("Failed to update status");
    }
  };

  const handleCancel = async (id: number) => {
    if (!window.confirm("Cancel this enrollment? Status will be set to DROPPED.")) return;
    setDeleting(id);
    try {
      await enrollmentService.cancel(id);
      toast.success("Enrollment cancelled");
      fetchEnrollments();
    } catch {
      toast.error("Failed to cancel enrollment");
    } finally {
      setDeleting(null);
    }
  };

  const columns = [
    { header: "#",        accessor: "id"          as keyof Enrollment },
    { header: "Student",  accessor: "studentName" as keyof Enrollment },
    { header: "Course",   accessor: "courseTitle" as keyof Enrollment },
    {
      header: "Status",
      accessor: (row: Enrollment) => (
        <select
          value={row.status}
          onChange={(e) => handleStatusChange(row.id, e.target.value)}
          className={`text-xs font-medium px-2 py-1 rounded-full border-0 cursor-pointer ${statusColors[row.status]}`}
        >
          <option value="ACTIVE">ACTIVE</option>
          <option value="COMPLETED">COMPLETED</option>
          <option value="DROPPED">DROPPED</option>
        </select>
      ),
    },
    {
      header: "Enrolled On",
      accessor: (row: Enrollment) =>
        new Date(row.enrolledAt).toLocaleDateString("en-IN", {
          day: "2-digit", month: "short", year: "numeric"
        }),
    },
    {
      header: "Actions",
      accessor: (row: Enrollment) => (
        <button
          onClick={() => handleCancel(row.id)}
          disabled={deleting === row.id}
          className="p-1.5 text-red-500 hover:bg-red-50 dark:hover:bg-red-900/20 rounded-lg transition"
          title="Cancel enrollment"
        >
          <Trash2 size={15} />
        </button>
      ),
    },
  ];

  return (
    <div>
      <div className="flex items-center justify-between mb-6">
        <div>
          <h1 className="text-2xl font-bold text-gray-900 dark:text-white">Enrollments</h1>
          <p className="text-gray-500 dark:text-gray-400 text-sm mt-1">
            Manage student course enrollments
          </p>
        </div>
        <Button
          icon={<Plus size={16} />}
          onClick={() => setModalOpen(true)}
        >
          Enroll Student
        </Button>
      </div>

      {/* Status Summary */}
      <div className="grid grid-cols-3 gap-4 mb-6">
        {(["ACTIVE", "COMPLETED", "DROPPED"] as const).map((status) => (
          <div key={status} className="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-4 text-center">
            <p className="text-2xl font-bold text-gray-900 dark:text-white">
              {enrollments.filter(e => e.status === status).length}
            </p>
            <span className={`text-xs font-medium px-2 py-0.5 rounded-full ${statusColors[status]}`}>
              {status}
            </span>
          </div>
        ))}
      </div>

      <div className="bg-white dark:bg-gray-800 rounded-xl border border-gray-200 dark:border-gray-700 p-6">
        <DataTable data={enrollments} columns={columns} loading={loading} />
      </div>

      <Modal
        isOpen={modalOpen}
        onClose={() => setModalOpen(false)}
        title="Enroll Student into Course"
      >
        <EnrollmentForm
          onSubmit={handleEnroll}
          onCancel={() => setModalOpen(false)}
          loading={saving}
        />
      </Modal>
    </div>
  );
};

export default EnrollmentsPage;