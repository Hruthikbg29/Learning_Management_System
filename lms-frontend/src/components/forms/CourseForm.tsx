import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import type { CourseRequest } from "../../services/course.service";
import type { Instructor } from "../../services/instructor.service";
import { instructorService } from "../../services/instructor.service";
import Button from "../ui/Button";

const schema = z.object({
  title:        z.string().min(1, "Title is required"),
  description:  z.string().min(1, "Description is required"),
  duration:     z.string().min(1, "Duration is required"),
  maxStudents:  z.coerce.number().min(1, "Must be at least 1"),
  instructorId: z.coerce.number().optional(),
});

type FormData = z.infer<typeof schema>;

interface Props {
  defaultValues?: Partial<CourseRequest>;
  onSubmit: (data: CourseRequest) => Promise<void>;
  onCancel: () => void;
  loading?: boolean;
}

const CourseForm = ({ defaultValues, onSubmit, onCancel, loading }: Props) => {
  const [instructors, setInstructors] = useState<Instructor[]>([]);

  const { register, handleSubmit, formState: { errors } } = useForm<FormData>({
    resolver: zodResolver(schema),
    defaultValues,
  });

  useEffect(() => {
    instructorService.getAll().then(res => {
      setInstructors(res.data.data ?? []);
    });
  }, []);

  const fieldClass = "w-full px-3 py-2.5 rounded-lg border border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-700 text-sm text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-blue-500 transition";
  const labelClass = "block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1";
  const errClass   = "text-red-500 text-xs mt-1";

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
      <div>
        <label className={labelClass}>Course Title</label>
        <input {...register("title")} className={fieldClass} placeholder="e.g. Java Full Stack Development" />
        {errors.title && <p className={errClass}>{errors.title.message}</p>}
      </div>
      <div>
        <label className={labelClass}>Description</label>
        <textarea
          {...register("description")}
          rows={3}
          className={fieldClass}
          placeholder="Course description..."
        />
        {errors.description && <p className={errClass}>{errors.description.message}</p>}
      </div>
      <div className="grid grid-cols-2 gap-4">
        <div>
          <label className={labelClass}>Duration</label>
          <input {...register("duration")} className={fieldClass} placeholder="e.g. 3 months" />
          {errors.duration && <p className={errClass}>{errors.duration.message}</p>}
        </div>
        <div>
          <label className={labelClass}>Max Students</label>
          <input {...register("maxStudents")} type="number" className={fieldClass} placeholder="30" />
          {errors.maxStudents && <p className={errClass}>{errors.maxStudents.message}</p>}
        </div>
      </div>
      <div>
        <label className={labelClass}>Instructor (Optional)</label>
        <select {...register("instructorId")} className={fieldClass}>
          <option value="">-- No Instructor --</option>
          {instructors.map(i => (
            <option key={i.id} value={i.id}>{i.name} — {i.specialization}</option>
          ))}
        </select>
      </div>
      <div className="flex gap-3 justify-end pt-2">
        <Button type="button" variant="secondary" onClick={onCancel}>Cancel</Button>
        <Button type="submit" loading={loading}>Save</Button>
      </div>
    </form>
  );
};

export default CourseForm;