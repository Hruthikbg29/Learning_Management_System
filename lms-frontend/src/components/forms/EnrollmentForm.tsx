import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import type { EnrollmentRequest } from "../../services/enrollment.service";
import type { Student } from "../../services/student.service";
import type { Course } from "../../services/course.service";
import { studentService } from "../../services/student.service";
import { courseService } from "../../services/course.service";
import Button from "../ui/Button";

const schema = z.object({
  studentId: z.coerce.number().min(1, "Please select a student"),
  courseId:  z.coerce.number().min(1, "Please select a course"),
});

type FormData = z.infer<typeof schema>;

interface Props {
  onSubmit: (data: EnrollmentRequest) => Promise<void>;
  onCancel: () => void;
  loading?: boolean;
}

const EnrollmentForm = ({ onSubmit, onCancel, loading }: Props) => {
  const [students, setStudents] = useState<Student[]>([]);
  const [courses,  setCourses]  = useState<Course[]>([]);

  const { register, handleSubmit, formState: { errors } } = useForm<FormData>({
    resolver: zodResolver(schema),
  });

  useEffect(() => {
    studentService.getAll().then(res => setStudents(res.data.data ?? []));
    courseService.getAll().then(res => setCourses(res.data.data ?? []));
  }, []);

  const fieldClass = "w-full px-3 py-2.5 rounded-lg border border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-700 text-sm text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-blue-500 transition";
  const labelClass = "block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1";
  const errClass   = "text-red-500 text-xs mt-1";

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
      <div>
        <label className={labelClass}>Select Student</label>
        <select {...register("studentId")} className={fieldClass}>
          <option value="">-- Select a Student --</option>
          {students.map(s => (
            <option key={s.id} value={s.id}>{s.name} — {s.email}</option>
          ))}
        </select>
        {errors.studentId && <p className={errClass}>{errors.studentId.message}</p>}
      </div>
      <div>
        <label className={labelClass}>Select Course</label>
        <select {...register("courseId")} className={fieldClass}>
          <option value="">-- Select a Course --</option>
          {courses.map(c => (
            <option key={c.id} value={c.id}>{c.title} — {c.duration}</option>
          ))}
        </select>
        {errors.courseId && <p className={errClass}>{errors.courseId.message}</p>}
      </div>
      <div className="flex gap-3 justify-end pt-2">
        <Button type="button" variant="secondary" onClick={onCancel}>Cancel</Button>
        <Button type="submit" loading={loading}>Enroll Student</Button>
      </div>
    </form>
  );
};

export default EnrollmentForm;