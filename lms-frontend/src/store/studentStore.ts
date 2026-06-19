import { create } from "zustand";

import type { Student } from "../services/student.service";
import { studentService } from "../services/student.service";

interface StudentState {
  students:      Student[];
  loading:       boolean;
  error:         string | null;
  fetchStudents: () => Promise<void>;
  clearError:    () => void;
}

export const useStudentStore = create<StudentState>((set) => ({
  students: [],
  loading:  false,
  error:    null,

  fetchStudents: async () => {
    set({ loading: true, error: null });
    try {
      const res = await studentService.getAll();
      set({ students: res.data.data ?? [], loading: false });
    } catch (err: any) {
      set({
        error:   err.response?.data?.message || "Failed to fetch students",
        loading: false,
      });
    }
  },

  clearError: () => set({ error: null }),
}));