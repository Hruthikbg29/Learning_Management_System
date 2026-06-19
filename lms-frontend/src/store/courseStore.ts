import { create } from "zustand";
import type { Course } from "../services/course.service";
import { courseService } from "../services/course.service";

interface CourseState {
  courses:      Course[];
  loading:      boolean;
  error:        string | null;
  fetchCourses: () => Promise<void>;
  clearError:   () => void;
}

export const useCourseStore = create<CourseState>((set) => ({
  courses: [],
  loading: false,
  error:   null,

  fetchCourses: async () => {
    set({ loading: true, error: null });
    try {
      const res = await courseService.getAll();
      set({ courses: res.data.data ?? [], loading: false });
    } catch (err: any) {
      set({
        error:   err.response?.data?.message || "Failed to fetch courses",
        loading: false,
      });
    }
  },

  clearError: () => set({ error: null }),
}));