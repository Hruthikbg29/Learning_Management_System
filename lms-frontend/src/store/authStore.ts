import { create } from "zustand";
import { persist } from "zustand/middleware";

const TOKEN_KEY = "lms_token";

interface AuthState {
  token: string | null;
  username: string | null;
  role: string | null;
  isAuthenticated: boolean;
  setAuth: (token: string, username: string, role: string) => void;
  logout: () => void;
}

export const useAuthStore = create<AuthState>()(
  persist(
    (set) => ({
      token: null,
      username: null,
      role: null,
      isAuthenticated: false,

      setAuth: (token, username, role) => {
        localStorage.setItem(TOKEN_KEY, token);  // ✅ THIS LINE saves it separately
        set({ token, username, role, isAuthenticated: true });
      },

      logout: () => {
        localStorage.removeItem(TOKEN_KEY);
        set({ token: null, username: null, role: null, isAuthenticated: false });
      },
    }),
    { name: "auth-storage" }
  )
);