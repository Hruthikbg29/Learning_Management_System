import { useForm } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import type { InstructorRequest } from "../../services/instructor.service";
import Button from "../ui/Button";

const schema = z.object({
  name:           z.string().min(1, "Name is required"),
  email:          z.string().email("Invalid email"),
  phone:          z.string().regex(/^[0-9]{10}$/, "Phone must be 10 digits"),
  specialization: z.string().min(1, "Specialization is required"),
});

type FormData = z.infer<typeof schema>;

interface Props {
  defaultValues?: Partial<InstructorRequest>;
  onSubmit: (data: InstructorRequest) => Promise<void>;
  onCancel: () => void;
  loading?: boolean;
}

const InstructorForm = ({ defaultValues, onSubmit, onCancel, loading }: Props) => {
  const { register, handleSubmit, formState: { errors } } = useForm<FormData>({
    resolver: zodResolver(schema),
    defaultValues,
  });

  const fieldClass = "w-full px-3 py-2.5 rounded-lg border border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-700 text-sm text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-blue-500 transition";
  const labelClass = "block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1";
  const errClass   = "text-red-500 text-xs mt-1";

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
      <div>
        <label className={labelClass}>Full Name</label>
        <input {...register("name")} className={fieldClass} placeholder="Dr. John Smith" />
        {errors.name && <p className={errClass}>{errors.name.message}</p>}
      </div>
      <div>
        <label className={labelClass}>Email</label>
        <input {...register("email")} type="email" className={fieldClass} placeholder="instructor@lms.com" />
        {errors.email && <p className={errClass}>{errors.email.message}</p>}
      </div>
      <div>
        <label className={labelClass}>Phone</label>
        <input {...register("phone")} className={fieldClass} placeholder="10-digit phone number" />
        {errors.phone && <p className={errClass}>{errors.phone.message}</p>}
      </div>
      <div>
        <label className={labelClass}>Specialization</label>
        <input {...register("specialization")} className={fieldClass} placeholder="e.g. Java and Spring Boot" />
        {errors.specialization && <p className={errClass}>{errors.specialization.message}</p>}
      </div>
      <div className="flex gap-3 justify-end pt-2">
        <Button type="button" variant="secondary" onClick={onCancel}>Cancel</Button>
        <Button type="submit" loading={loading}>Save</Button>
      </div>
    </form>
  );
};

export default InstructorForm;